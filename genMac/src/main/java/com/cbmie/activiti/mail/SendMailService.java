package com.cbmie.activiti.mail;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.activiti.entity.ApprovalOpinion;
import com.cbmie.common.utils.Email;
import com.cbmie.common.utils.PropertiesLoader;
import com.cbmie.system.service.UserService;

@Service
public class SendMailService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserService userService;

	public void sendMail(ApprovalOpinion approval, String summary) {
		if (approval.getEmail()) {
			String[] candidateUserIds = approval.getCandidateUserIds();
			if (candidateUserIds == null) {
				return;
			}
			for (String candidateUserId : candidateUserIds) {
				if (candidateUserId.length() == 0) {
					continue;
				}
				String subject = "有待办事宜：" + summary;
				String content = "待办事宜：" + summary;
				String email = userService.getUser(candidateUserId).getEmail();
				doSend(content, subject, email);
			}
		}
	}

	public void doSend(String content, String subject, String emailaddr) {
		PropertiesLoader p = new PropertiesLoader("javaMail.properties");
		String mailhost = p.getProperty("ip");
		String mailfrom = p.getProperty("mailfrom");
		String mailusername = p.getProperty("username");
		String mailpassword = p.getProperty("password");
		String pcURL = p.getProperty("pcURL");
		String mobileURL = p.getProperty("mobileURL");

		Email sendemail = new Email();
		try {
			sendemail.sendMail(mailhost, mailfrom, emailaddr, subject,
					content + " 请登录工作平台（" + pcURL + "）办理。\n 手机或pad请点击：" + mobileURL, true, mailusername, mailpassword);
		} catch (SendFailedException se) {
			logger.error("Send email address errors", se);
		} catch (MessagingException me) {
			logger.error("Messaging exception", me);
		}
	}
}
