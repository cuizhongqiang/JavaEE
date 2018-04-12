package com.cnbmtech.cdwpcore.aaa.module.audit;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.cnbmtech.cdwpcore.aaa.module.account.AuthUtils;

//@Configuration
@Component
public class ShiroAuditorBean implements AuditorAware<String> {
    @Override
    public String getCurrentAuditor() {
    	return AuthUtils.username();
    }
}
