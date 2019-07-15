package com.cbmie.activiti.web;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cbmie.activiti.entity.ModelBean;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.utils.Json;
import com.cbmie.common.web.BaseController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;  
  
/** 
 * 流程模型控制器 
 * 
 * @author henryyan 
 */  
@Controller  
@RequestMapping(value = "/model")  
public class ModelController extends BaseController { 
  
    protected Logger logger = LoggerFactory.getLogger(getClass());
  
    @Autowired  
    RepositoryService repositoryService;  

    /**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "activiti/modelList";
	}
    /** 
     * 模型列表 
     */  
    @RequestMapping(value="list",method = RequestMethod.GET)
	@ResponseBody
    public Map<String, Object> list(HttpServletRequest request) {
    	Page<Model> page = getPage(request);
    	ModelQuery query = repositoryService.createModelQuery();
		List<Model> list = query.listPage(page.getFirst() - 1, page.getPageSize());
		page.getResult().addAll(list);
		page.setTotalCount(query.count());
		return getEasyUIData(page);
	}
    
    
    /** 
     * 通过spring mvc 的rest地址打开bpmn编辑器 
     * @return 
     */  
    @RequestMapping("editor")  
    public ModelAndView getEditor(){  
        ModelAndView modelAndView = new ModelAndView("system/editor") ;  
        return modelAndView ;  
    }
    
    /** 
     * 流程模型创建页面跳转
     * @return 
     */  
    @RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(org.springframework.ui.Model model) {
		model.addAttribute("modelBean", new ModelBean());
		model.addAttribute("action", "create");
		return "activiti/modelForm";
	}
    
    /** 
     * 创建流程模型
     * @return 
     */  
    @RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid ModelBean modelBean, org.springframework.ui.Model model) throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();  
        ObjectNode editorNode = objectMapper.createObjectNode();  
        editorNode.put("id", "canvas");  
        editorNode.put("resourceId", "canvas");  
        ObjectNode stencilSetNode = objectMapper.createObjectNode();  
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");  
        editorNode.put("stencilset", stencilSetNode);  
        Model modelData = repositoryService.newModel();  

        ObjectNode modelObjectNode = objectMapper.createObjectNode();  
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, modelBean.getName());  
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);  
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, StringUtils.defaultString(modelBean.getDescription()));  
        modelData.setMetaInfo(modelObjectNode.toString());  
        modelData.setName(modelBean.getName());  
        modelData.setKey(StringUtils.defaultString(modelBean.getKey()));  

        //保存模型  
        repositoryService.saveModel(modelData);  
        repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
        
		return "success";
	}
  
  
    /** 
     * 根据Model部署流程 
     */  
    @ResponseBody  
    @RequestMapping("deploy")  
    public Json deploy(String modelId) {  
        Json json = new Json();  
        try {  
            Model modelData = repositoryService.getModel(modelId);  
            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));  
            byte[] bpmnBytes = null;  
  
            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);  
            bpmnBytes = new BpmnXMLConverter().convertToXML(model);  
  
            String processName = modelData.getName() + ".bpmn20.xml";  
            
            Deployment deployment = repositoryService.createDeployment()
            		.name(modelData.getName())
            		.addString(processName, new String(bpmnBytes,"UTF-8"))
            		.deploy();
            
            json.setMsg("部署成功，部署ID=" + deployment.getId());  
            json.setSuccess(true);  
        } catch (NullPointerException e) {  
            json.setMsg("未配置工作流");  
        } catch (Exception e) {  
            logger.error("根据模型部署流程失败：modelId={}", modelId, e);  
            json.setMsg("模型部署流程失败");  
        }  
        return json;  
    }  
  
    /** 
     * 导出model的xml文件 
     */  
    @RequestMapping("export")  
    public void export(String modelId, HttpServletResponse response) {  
        try {  
            Model modelData = repositoryService.getModel(modelId);  
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();  
            JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));  
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);  
            BpmnXMLConverter xmlConverter = new BpmnXMLConverter();  
            byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);  
  
            ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);  
            IOUtils.copy(in, response.getOutputStream());  
            String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";  
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);  
            response.flushBuffer();  
        } catch (Exception e) {  
            logger.error("导出model的xml文件失败：modelId={}", modelId, e);  
        }  
    }  
  
    @RequestMapping(value = "delete/{modelId}")  
    public String delete(@PathVariable("modelId") String modelId) {  
        repositoryService.deleteModel(modelId);  
        return "redirect:/workflow/model/list";  
    }  
  
}
