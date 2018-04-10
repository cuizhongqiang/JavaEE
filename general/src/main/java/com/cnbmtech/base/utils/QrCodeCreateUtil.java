package com.cnbmtech.base.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码生成和读的工具类
 *
 */
public class QrCodeCreateUtil {
	private static final String CHARSET = "utf-8";

	/**
	 * 生成包含字符串信息的二维码图片
	 * 
	 * @param outputStream
	 *            文件输出流路径
	 * @param content
	 *            二维码携带信息
	 * @param qrCodeSize
	 *            二维码图片大小
	 * @param imageFormat
	 *            二维码的格式
	 * @throws WriterException
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean createQrCode(OutputStream outputStream, String content, int qrCodeSize, String imageFormat)
			throws WriterException, IOException {
		// 设置二维码纠错级别ＭＡＰ
		Hashtable hints = new Hashtable();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		// 创建比特矩阵(位矩阵)的QR码编码的字符串
		BitMatrix byteMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hints);
		// 使BufferedImage勾画QRCode (matrixWidth 是行二维码像素点)
		int matrixWidth = byteMatrix.getWidth();
		BufferedImage image = new BufferedImage(matrixWidth - 200, matrixWidth - 200, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, matrixWidth, matrixWidth);
		// 使用比特矩阵画并保存图像
		graphics.setColor(Color.BLACK);
		for (int i = 0; i < matrixWidth; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				if (byteMatrix.get(i, j)) {
					graphics.fillRect(i - 100, j - 100, 1, 1);
				}
			}
		}
		return ImageIO.write(image, imageFormat, outputStream);
	}

	/**
	 * 
	 * @param path
	 *            保存文件夹
	 * @param name
	 *            保存名称
	 * @param content
	 *            内容
	 * @param qrCodeSize
	 *            二维码大小
	 * @throws WriterException
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public static void createQrCodeSimple(String path, String name, String content, int qrCodeSize)
			throws WriterException, IOException {
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		Map hints = new HashMap();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hints);
		File file1 = new File(path, name + ".jpg");
		MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
	}
	
	/** 
	    * 生成并下载二维码 
	    * @param value 二维码对应内容 
	    * @param width 二维码宽 
	    * @param height 二维码高 
	    * @param format  二维码格式 
	    * @return 
	    * @throws WriterException 
	    * @throws IOException 
	    */  
	   public static ResponseEntity<byte[]> getResponseEntity(String value,int width, int height,String format) throws WriterException, IOException {  
	       Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();  
	       hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
	       BitMatrix bitMatrix = new MultiFormatWriter().encode(value,  
	               BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵  
	       //将矩阵转为Image  
//	       MatrixToImageConfig config = new MatrixToImageConfig(0xFF000000,0x76b852);
//	       BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix,config);  
	       BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);  
	       ByteArrayOutputStream out = new ByteArrayOutputStream();  
	       ImageIO.write(image, format, out);//将BufferedImage转成out输出流  
	       HttpHeaders headers = new HttpHeaders();  
	       headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
	       return new ResponseEntity<byte[]>(out.toByteArray(),  
	               headers, HttpStatus.CREATED);
	   }  
	
	   
	   /** 
	    * 生成并下载二维码 
	    * @param value 二维码对应内容 
	    * @param width 二维码宽 
	    * @param height 二维码高 
	    * @param format  二维码格式 
	    * @return 
	    * @throws WriterException 
	    * @throws IOException 
	    */  
	   public static byte[] getImg4Excel(String value,int width, int height,String format) throws WriterException, IOException {  
	       Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();  
	       hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
	       BitMatrix bitMatrix = new MultiFormatWriter().encode(value,  
	               BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵  
	       //将矩阵转为Image  
	       BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);  
	       ByteArrayOutputStream out = new ByteArrayOutputStream();  
	       ImageIO.write(image, format, out);//将BufferedImage转成out输出流  ;
	       return out.toByteArray();
	   }  

	/**
	 * 读二维码并输出携带的信息
	 */
	public static void readQrCode(InputStream inputStream) throws IOException {
		// 从输入流中获取字符串信息
		BufferedImage image = ImageIO.read(inputStream);
		// 将图像转换为二进制位图源
		LuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		QRCodeReader reader = new QRCodeReader();
		Result result = null;
		try {
			result = reader.decode(bitmap);
		} catch (ReaderException e) {
			e.printStackTrace();
		}
		System.out.println(result.getText());
	}

	/**
	 * 测试代码
	 * 
	 * @throws WriterException
	 */
	public static void main(String[] args) throws IOException, WriterException {
		String ss = "BEGIN:VCARD\n" +
					"VERSION:3.0\n" +
					"N:蔺小朋\n" +
					"TEL;work:68738927\n"+
					"TEL:17332910812\n" +
					"EMAIL:proflier@163.com\n" +
					"ADR;WORK:;;街道地址AWAW;;;;中国" +
					"ORG:" + "北京\n" +
					"TITLE:软件工程师\n"+
					"URL:http://blog.csdn.net/test\n" +
					"END:VCARD";
		String path = "D:";
		String name = "qrcode";
//		String adress = "http://192.168.99.65:8080/staffInfor/toVisitor/3";
		createQrCodeSimple(path, name, ss, 900);
		readQrCode(new FileInputStream(new File("d:\\qrcode.jpg")));
	}

}