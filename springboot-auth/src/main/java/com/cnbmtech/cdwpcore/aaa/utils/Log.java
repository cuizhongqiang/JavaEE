package com.cnbmtech.cdwpcore.aaa.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Log {
	default Logger getLog() {
		final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());
		return log;
	}
}
