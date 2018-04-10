package com.cnbmtech.base.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.cnbmtech.general.entity.StaffInfor;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 名片生成
 * @author admin
 *
 */
public class ChartGraphicsUtils {
	
	/**
	 * 对不同操作系统的路径支持
	 */
	private static String SYSTEM_SEPARATOR = System.getProperty("file.separator");
	
	
	/**
	 * 
	 * @param staffInfor
	 * @return
	 * @throws IOException
	 * @throws BadHanyuPinyinOutputFormatCombination 
	 * @throws WriterException 
	 */
	public static ResponseEntity<byte[]> graphicsGeneration(StaffInfor staffInfor,HttpServletRequest request,String adress) throws IOException, BadHanyuPinyinOutputFormatCombination, WriterException {
		BufferedImage image = null;
		
		String src = request.getSession().getServletContext().getRealPath("/")+SYSTEM_SEPARATOR+"static"+SYSTEM_SEPARATOR+"images"+SYSTEM_SEPARATOR+"card.jpg";
		image = javax.imageio.ImageIO.read(new java.io.File(src));

		/** 绘制姓名 */
		drowString4Img(image, staffInfor.getRealName(), 59,95,"楷体", Font.BOLD, 40, true, 57, 60, 50, 143, Color.WHITE);
//		drowString4Img(image, staffInfor.getRealName(), 59,95,"黑体", Font.BOLD, 42, false, 0, 0, 0, 0, null);
		
		drowString4Img(image, ChineseTransPinyin.getUpEname(staffInfor.getRealName()), 59,128,Font.SANS_SERIF, Font.BOLD, 30, true, 57, 110, 22, 250, Color.WHITE);
		
		/**绘制职位*/
		drowString4Img(image, staffInfor.getTitle(), 56, 166,"楷体", Font.BOLD, 23, true, 56, 147, 22, 260, Color.WHITE);
		
		/**绘制联系方式*/
		//电话
		drowString4Img(image, staffInfor.getTelphone(), 90,  280,Font.MONOSPACED, Font.PLAIN, 18, false, 0, 0, 0, 0, null);
		//电话
		drowString4Img(image, staffInfor.getFax(), 260,  280,Font.MONOSPACED, Font.PLAIN, 18, false, 0, 0, 0, 0, null);
		//传真
		drowString4Img(image, staffInfor.getMobile(), 90,  310,Font.MONOSPACED, Font.PLAIN, 18, false, 0, 0, 0, 0, null);
		//邮箱
		drowString4Img(image, staffInfor.getEmail(), 260,  310,Font.MONOSPACED, Font.PLAIN, 18, false, 0, 0, 0, 0, null);
		
		/**绘制公司信息*/
//		drowString4Img(image, staffInfor.getCompName(), 60, H_title_null+H_name+H_company+H_mid_null+H_tel_phone+H_fax_mal-H_company/2+70,"宋体", Font.BOLD, 15);
		/**绘制网址*/
//		drowString4Img(image, staffInfor.getWebsite(), 300, H_title_null+H_name+H_company+H_mid_null+H_tel_phone+H_fax_mal-H_company/2+70,"宋体", Font.ITALIC, 18);
		/**绘制地址*/
//		drowString4Img(image, staffInfor.getAddress(), 60, H_title_null+H_name+H_company+H_mid_null+H_tel_phone+H_fax_mal+H_company-H_location/2,"宋体", Font.ITALIC, 10);
		
		 BitMatrix bitMatrix = new MultiFormatWriter().encode(adress,BarcodeFormat.QR_CODE, 150, 150);// 生成矩阵  
	     BufferedImage qrcodeimg = MatrixToImageWriter.toBufferedImage(bitMatrix);  
		
		 /**插入二维码*/
		 Graphics qrcodePic = image.getGraphics();
		 if(qrcodeimg!=null){
		 qrcodePic.drawImage(qrcodeimg, 570, 260, 140, 140, null);
		 qrcodePic.dispose();
		 }
		 
		ByteArrayOutputStream out = new ByteArrayOutputStream();  
		ImageIO.write(image, "png", out);// 将BufferedImage转成out输出流
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(out.toByteArray(), headers, HttpStatus.CREATED);

	}
	
	/**
	 * 
	 * @param image
	 * @param drowValue
	 * @param x
	 * @param y
	 * @param fontName
	 * @param fontStyle
	 * @param fontSize
	 * @return
	 */
	private static  BufferedImage drowString4Img(BufferedImage image,String drowValue,int x,int y,String fontName, int fontStyle, int fontSize,boolean haveBackground,int bX,int bY,int bH,int bW,Color color){
		if(drowValue!=null){
			Graphics g = image.createGraphics();
			if(haveBackground){
				//设置区域颜色
		        g.setColor(color);
		        //填充区域并确定区域大小位置
		        g.fillRect(bX, bY, bW, bH);
			}
			// 设置字体颜色，先设置颜色，再填充内容
			g.setColor(Color.BLACK);
			// 设置字体
			Font titleFont = new Font(fontName, fontStyle, fontSize);
			g.setFont(titleFont);
			g.drawString(drowValue, x, y);
			return image;
		}else{
			return null;
		}
		
	}
	
	
	/**
	 * @param fontFileName
	 *            第一个参数是外部字体名
	 * @param fontSize
	 *            第二个是字体大小
	 * @return
	 */
	public static Font loadFont(String fontFileName, float fontSize) {
		try {
			File file = new File(fontFileName);
			FileInputStream aixing = new FileInputStream(file);
			Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, aixing);
			Font dynamicFontPt = dynamicFont.deriveFont(fontSize);
			aixing.close();
			return dynamicFontPt;
		} catch (Exception e) {// 异常处理
			e.printStackTrace();
			return new java.awt.Font("宋体", Font.PLAIN, 14);
		}
	}
	

}