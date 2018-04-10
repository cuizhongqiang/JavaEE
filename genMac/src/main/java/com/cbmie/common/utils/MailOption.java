package com.cbmie.common.utils;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

public class MailOption {
	
	public static final String SMTP = "mail.smtp.auth";
	public static final String SMTP_AUTH = "mail.smtp.auth";
	public static final String SMTP_HOST = "mail.smtp.host";	
	
	public static final String MAIL_FROM = "mail.from";	
	public static final String MAIL_FROM_USERNAME = "mail.from.user";
	public static final String MAIL_FROM_PASSWORD = "mail.from.password";
	
	public static final String MAIL_TO = "mail.to";
	
	public static final String MAIL_SUBJECT = "mail.subject";
	
	public static final String MAIL_CONTENT = "mail.content";
	
	public static final String MIMEME_TYPE_CHARSET = "MimeMessage.Content.Type";
	
	private final Map<String, Object> option;
	public static final MailOption EMPTY_OPTION = new MailOption(Collections.<String, Object> emptyMap());
	
	public MailOption() {
		this.option = new Hashtable<String, Object>();
	}
	
	public MailOption(Map<String, Object> option) {
		if (option == null) {
			throw new NullPointerException("option may not be null");
		}
		this.option = option;
	}
	
	public Object get(String key) {
		return option.get(key);
	}
	
	public Object get(String key, Object defaultValue) {
		Object value = get(key);
		return value == null ? defaultValue : value;
	}
	
	public String getString(String key) {
		return (String)get(key);
	}
	
	public String getString(String key, String defaultStr) {
		String value = getString(key);
		return value == null ? defaultStr : value;
	}
	
	public void set(String key, Object value) {
		option.put(key, value);
	}
	
	@Override
	public String toString() {
		return "MailOption{" + "option=" + option + '}';
	}
	
	
}
