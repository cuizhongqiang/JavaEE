package com.cbmie.genMac.quartz;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cbmie.common.utils.DateUtils;
import com.cbmie.common.utils.Email;
import com.cbmie.common.utils.PropertiesLoader;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.genMac.logistics.entity.InvoiceReg;
import com.cbmie.genMac.logistics.service.InvoiceRegService;
import com.cbmie.system.entity.Inform;
import com.cbmie.system.entity.User;
import com.cbmie.system.service.InformService;
import com.cbmie.system.service.UserService;

public class InvoiceAcceptanceJob implements Job{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private InvoiceRegService invoiceRegService;
	
	private UserService userService;
	
	private InformService informService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Trigger trigger = context.getTrigger();
		String jobName = trigger.getJobKey().toString();
		int emails = 0;
		try {
			logger.debug("{} job start time:{}", new Object[]{jobName, DateUtils.getDateTime()});
			// 获取JobExecutionContext中的service对象
			SchedulerContext sc = context.getScheduler().getContext();
			invoiceRegService = (InvoiceRegService) sc.get("invoiceRegService");
			informService = (InformService) sc.get("informService");
			userService = (UserService) sc.get("userService");
			// 查出到单付汇的到单
			List<InvoiceReg> invoiceRegList = invoiceRegService.findInvoiceAcceptance();
			for (InvoiceReg invoiceReg : invoiceRegList) {
				int days = daysBetween(invoiceReg.getAcceptDate());
				// 获取业务员信息
				User user = userService.getUser(invoiceReg.getSalesman());
				// 以三种方式提醒（邮件、短信、通知）
				// 首先查看通知表里是否存在该进口合同的通知，存在则修改剩余天数，不存在则新增通知
				PropertiesLoader p = new PropertiesLoader("javaMail.properties");
				String subject = StringUtils.replacePlaceholder(p.getProperty("invoiceAcceptanceSubject"), invoiceReg.getInvoiceNo());// 标题
				String content = StringUtils.replacePlaceholder(p.getProperty("invoiceAcceptanceContent"), 
						new String[]{DateFormatUtils.format(invoiceReg.getAcceptDate(), "yyyy-MM-dd"), String.valueOf(days)});// 内容
				Inform inform = informService.findBusinessInform("InvoiceAcceptance", invoiceReg.getId());
				if (inform == null) {
					informService.insert("invoiceAcceptance", invoiceReg.getId(), user.getLoginName(), subject, content, days);
				} else {
					inform.setResidueDays(days);
					inform.setUpdateDate(new Date());
					informService.update(inform);
				}
				sendEmail(p, subject, content, user.getEmail());
			}
			emails = invoiceRegList.size();// 设置发送邮件个数
		} catch (Exception ex) {
			logger.error("{} job error", new Object[]{jobName, ex});
		} finally {
			logger.debug("{} job send {} emails end time:{}", new Object[]{jobName, emails, DateUtils.getDateTime()});
		}
	}
	
	/**
	 * 计算距离今天的天数 
	 */
	private int daysBetween (Date date) {
		Date now = new Date();
		return (int) ((date.getTime() - now.getTime()) / (1000 * 3600 * 24));
	}
	
	/**
	 * 发邮件
	 */
	private void sendEmail(PropertiesLoader p, String subject, String content, String emailaddr) {
		String mailhost = p.getProperty("ip");
		String mailfrom = p.getProperty("mailfrom");
		String mailusername = p.getProperty("username");
		String mailpassword = p.getProperty("password");

		Email sendemail = new Email();
		try {
			sendemail.sendMail(mailhost, mailfrom, emailaddr, subject, content, true, mailusername, mailpassword);
		} catch (SendFailedException se) {
			logger.error("Send email address errors", se);
		} catch (MessagingException me) {
			logger.error("Messaging exception", me);
		}
	}
	
}
