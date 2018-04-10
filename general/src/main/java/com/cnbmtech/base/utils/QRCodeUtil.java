package com.cnbmtech.base.utils;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeUtil {
    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int WIDTH = 60;
    // LOGO高度
    private static final int HEIGHT = 60;

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private static BufferedImage createImage(String content, String imgPath, boolean needCompress) throws Exception {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }

        // 插入图片
        QRCodeUtil.insertImage(image, imgPath, needCompress);
        return image;
    }

    private static void insertImage(BufferedImage source, String imgPath, boolean needCompress) throws Exception {
        File file = new File(imgPath);
        if (!file.exists()) {
            System.err.println("" + imgPath + "   该文件不存在！");
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    public static void encode(String content, String imgPath, String destPath, boolean needCompress, String fileName)
            throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath, needCompress);
        mkdirs(destPath);
        String file = fileName + "." + FORMAT_NAME.toLowerCase();
        ImageIO.write(image, FORMAT_NAME, new File(destPath + File.separator + file));
    }

    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }
//    public static void main(String[] args) throws Exception {
//        String text = "http://www.baidu.com";
//        QRCodeUtil.encode(text, "D:/004.jpg", "D:", true, "exp");
//    }
    
    
//    public static void main(String[] args) {
//    	try {
//    	//1.下面是我测试用的一个format
//    	// BEGIN:VCARD
//    	// "VERSION:3.0
//    	// N:李德伟
//    	// EMAIL:1606841559@qq.com
//    	// TEL:12345678912
//    	// TEL;CELL:12345678912
//    	// ADR:山东济南齐鲁软件园
//    	// ORG:济南
//    	// TITLE:软件工程师
//    	// URL:http://blog.csdn.net/lidew521
//    	// NOTE:呼呼测试下吧。。。
//    	// END:VCARD"
//    	//2.根据测试结果推理
//    	//---------------------------------------
//    	//测试结果不加回车是不行的这样的话会出现问题
//    	//就是扫描出来以后会没有内容
//    	//这里可以看出,微信解析二维码的方式
//    	//-------------------------------------------------
//    	        //3.测试\n可以被二维码识别
//    	//  这里也是有原因的,因为微信扫描二维码后会进行二次加工,
//    	//  这里加工的时候,是用java代码的,因为是android系统,所以在
//    	//  java中的回车是\n,因此这里就要用\n来分割
//    	    String content = "BEGIN:VCARD\n" +
//    	    "VERSION:3.0\n" +
//    	    "N:李德伟\n" +
//    	    "EMAIL:1606841559@qq.com\n" +
//    	    "TEL:12345678912" +
//    	    "TEL;CELL:12345678912" +
//    	    "ADR:山东济南齐鲁软件园\n" +
//    	    "ORG:" +
//    	    "济南\n" +
//    	    "TITLE:软件工程师\n" +
//    	    "URL:http://blog.csdn.net/lidew521\n" +
//    	    "NOTE:呼呼测试下吧。。。\n" +
//    	    "END:VCARD";
//    	    String path = "D:";
//    	    
//    	    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
//    	    Map hints = new HashMap();
//    	    hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//    	    BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400,hints);
//    	    File file1 = new File(path,"名片.jpg"); 
//
//
//    	    MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
//    	    
//    	} catch (Exception e) {
//    	    e.printStackTrace();
//    	}
//    	}
    
    
    
}