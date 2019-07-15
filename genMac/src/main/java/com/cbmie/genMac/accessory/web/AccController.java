package com.cbmie.genMac.accessory.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.genMac.accessory.entity.Accessory;
import com.cbmie.genMac.accessory.entity.Plupload;
import com.cbmie.genMac.accessory.service.AccessoryService;
import com.cbmie.genMac.accessory.utils.AccessoryUtil;
import com.cbmie.genMac.accessory.utils.PluploadUtil;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 附件controller
 */
@Controller
@RequestMapping("accessory")
public class AccController extends BaseController {
	/**
	 * 对不同操作系统的路径支持
	 */
	private static String SYSTEM_SEPARATOR = System.getProperty("file.separator"); 
	
	@Autowired
	private AccessoryService accessoryService;

	/**
	 * 获取所有附件json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public List<Accessory> accessoryList(HttpServletRequest request) {
		return accessoryService.search(PropertyFilter.buildFromHttpRequest(request));
	}
	
	/**
	 * 上传附件页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "toUpload/{pid}", method = RequestMethod.GET)
	public String toUpload(@PathVariable("pid") String pid) {
		return "accessory/uploader";
	}

	/**
	 * 上传附件
	 * 
	 * @param request
	 * @param id
	 */
	@ResponseBody
	@RequestMapping(value = "upload/{pid}/{entity}", method = RequestMethod.POST)
	public void upload(HttpServletRequest request, @PathVariable("pid") String pid, @PathVariable("entity") String entity) {
		Accessory accessory = new Accessory();
		Plupload plupload = new Plupload();
		String FileDir = "uploadfile" + SYSTEM_SEPARATOR;
		plupload.setRequest(request);
		// 文件存储路径
		File dir = new File(plupload.getRequest().getSession().getServletContext().getRealPath("/") + FileDir);
		try {
			// 上传文件
			accessory = PluploadUtil.upload(plupload, dir);
			// 判断文件是否上传成功（被分成块的文件是否全部上传完成）
			if (PluploadUtil.isUploadFinish(plupload)) {
				// System.out.println(plupload.getName() + "----");
			}
			// 保存信息到数据库
			if (accessory != null && accessory.getAccId() != null) {
				User currentUser = UserUtil.getCurrentUser();
				accessory.setAccAuthor(currentUser.getLoginName());
				accessory.setAccParentId(pid);
				accessory.setAccParentEntity(entity);
				accessoryService.save(accessory);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除附件
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws IOException {
		Accessory accessory = new Accessory();
		accessory = accessoryService.getAcc(id);
		accessoryService.delete(accessory.getId());
		AccessoryUtil.deleteFile(accessory);
		return "success";
	}

	/**
	 * 下载附件
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "download/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String download(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long id) {
		Accessory accessory = new Accessory();
		accessory = accessoryService.getAcc(id);
		// 中文文件名
		String fileName = accessory.getAccRealName();
		// 用户设置的源文件的路径
		String path = accessory.getAccSrc() + SYSTEM_SEPARATOR + accessory.getAccId();
		// 创建文件对象
		File file = new File(path);

		BufferedInputStream bin = null;
		BufferedOutputStream bos = null;
		if (file.exists()) {
			try {
				response.setContentType("application/x-download");
				// 这里文件名一定要转码，不然下载时会出问题。
				response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
				bin = new BufferedInputStream(new FileInputStream(file));
				bos = new BufferedOutputStream(response.getOutputStream());
				byte[] buff = new byte[1024 * 1024];
				int iSize;
				while (-1 != (iSize = bin.read(buff))) {
					bos.write(buff, 0, iSize);
				}
				bos.flush();
				bos.close();
				bin.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
