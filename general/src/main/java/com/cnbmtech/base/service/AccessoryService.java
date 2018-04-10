package com.cnbmtech.base.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.cnbmtech.base.dao.AccessoryDao;
import com.cnbmtech.base.dao.BaseDao;
import com.cnbmtech.base.entity.Accessory;
import com.cnbmtech.base.entity.Result;

/**
 * 附件service
 */
@Service
public class AccessoryService extends BaseService<Accessory, Long> {

	/**
	 * 对不同操作系统的路径支持
	 */
	private static String SYSTEM_SEPARATOR = System.getProperty("file.separator");

	@Autowired
	private AccessoryDao accessoryDao;

	@Override
	public BaseDao<Accessory, Long> getEntityDao() {
		return accessoryDao;
	}

	/**
	 * 上传图片
	 * 
	 * @param request
	 * @param callAccId
	 *            调用上传类 的id
	 * @param callAccEntity
	 *            调用上传类的 class
	 * @param accId 
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public Result avatarUpload(HttpServletRequest request, Long callAccId, String callAccEntity, Long accId)
			throws IllegalStateException, IOException {
		Accessory accessory = new Accessory();
		Result result = new Result();
		String FileDir = SYSTEM_SEPARATOR + "uploadfile" + SYSTEM_SEPARATOR;
		String totalPath = request.getSession().getServletContext().getRealPath("/");
		totalPath = totalPath.substring(0, totalPath.lastIndexOf(SYSTEM_SEPARATOR));
		// 文件存储路径
		File dir = new File(totalPath + FileDir + "avatar" + SYSTEM_SEPARATOR);
		if (!dir.exists())
			dir.mkdirs();
		System.out.println("头像保存位置：：：" + dir.getPath());
		String contentType = request.getContentType();
		if (contentType.indexOf("multipart/form-data") >= 0) {
			// 取服务器时间+8位随机码作为部分文件名，确保文件名无重复。
			String fileName = "" + System.currentTimeMillis();
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();
			File targetFile = new File(dir.getPath() + SYSTEM_SEPARATOR + fileName + ".jpg");
			// 此处取第一个编辑后头像 key : "__avatar1"
			MultipartFile multipartFile = map.get("__avatar1").get(0);
			multipartFile.transferTo(targetFile);
			// 保存信息到数据库
			String realName = multipartFile.getOriginalFilename();
			accessory.setAccSrc(dir.getPath());
			accessory.setAccId(Long.parseLong(fileName));
			accessory.setAccRealName(realName);
			accessory.setAccExtension(".jpg");
			// accessory.setAccAuthor(currentUser.getLoginName());
			accessory.setAccParentId(callAccId.toString());
			accessory.setAccParentEntity(callAccEntity);
			if (accId != null) {
				Accessory accessoryOld = accessoryDao.findByAccId(accId);
				accessoryDao.delete(accessoryOld);
			}
			accessoryDao.save(accessory);
			result.sourceUrl = fileName;
		}
		result.success = true;
		result.msg = "Success!";
		return result;
	}

	
	/**
	 * 显示图片
	 * @param accId
	 * @return
	 * @throws IOException
	 * @throws FileUploadException
	 */
	public ResponseEntity<byte[]> avatarView(Long accId) throws IOException, FileUploadException {
		Accessory accessory = new Accessory();
		accessory = accessoryDao.findByAccId(accId);
		// 用户设置的源文件的路径
		String path = accessory.getAccSrc() + SYSTEM_SEPARATOR + accessory.getAccId() + ".jpg";
		// 创建文件对象
		BufferedImage image = ImageIO.read(new FileInputStream(path));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", out);// 将BufferedImage转成out输出流
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(out.toByteArray(), headers, HttpStatus.CREATED);
	}
}
