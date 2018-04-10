package com.cbmie.common.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
	
	/**发邮件
	 * @param 8个参数 host邮件服务器地址,from发件人邮箱,to收件人邮箱,subject邮件标题 ,content邮件内容,authentication身份验证true,username发件人用户名,password发件人密码
	 * @return 无返回值
	 */
    public void sendMail(String host, String from, String to, String subject,
            String content, boolean authentication, String username, String password) throws MessagingException {
       
        Properties props = System.getProperties();
        
        props.put("mail.smtp.host", host);
        if (!authentication) {
            props.put("mail.smtp.auth", "false");
        } else {
            props.put("mail.smtp.auth", "true");
        }
        
        Session session = Session.getDefaultInstance(props, null);
       
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setContent(content, "text/html;charset=gb2312");
        
        if (authentication) 
        {
            Transport smtp = null;
            try 
            {
                smtp = session.getTransport("smtp");
                smtp.connect(host, username, password);
                smtp.sendMessage(message, message.getAllRecipients());
            } catch (AddressException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            } finally {
                smtp.close();
            }
        } 
        else 
        {
            Transport.send(message);
        }
    }
}
