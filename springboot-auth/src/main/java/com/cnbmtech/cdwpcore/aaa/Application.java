/**
 * @Description: TODO
 * @author zhengangwu
 */
package com.cnbmtech.cdwpcore.aaa;

import org.flowable.engine.IdentityService;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.cnbmtech.cdwpcore.aaa.jwt.JwtInfo;
import com.cnbmtech.cdwpcore.aaa.module.account.dao.AuthUserRepository;
import com.cnbmtech.cdwpcore.aaa.module.account.model.AuthUser;
import com.cnbmtech.cdwpcore.aaa.workflow.flowable.common.WorkflowUtils;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties(JwtInfo.class)
@EnableAutoConfiguration(exclude = { org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
		org.flowable.spring.boot.SecurityAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	InitializingBean initUsersAndGroups(final IdentityService identityService) {

		return new InitializingBean() {
			@Override
			public void afterPropertiesSet() throws Exception {

				// install groups & users
				identityService.deleteUser("markzgwu");
				identityService.deleteUser("wangsheng");
				identityService.deleteGroup("admin");
				identityService.deleteGroup("managers");

				if (identityService.createGroupQuery().groupName("admin").count() < 1) {
					Group group = identityService.newGroup("admin");
					identityService.saveGroup(group);
				}

				if (identityService.createGroupQuery().groupName("managers").count() < 1) {
					Group group = identityService.newGroup("managers");
					identityService.saveGroup(group);
				}

				{
					if (identityService.createUserQuery().userId("wangsheng").count() < 1) {
						User user1 = identityService.newUser("wangsheng");
						user1.setPassword("123456");
						identityService.saveUser(user1);
						identityService.createMembership("wangsheng", "managers");
						identityService.createMembership("wangsheng", "admin");
					}

				}

				{
					if (identityService.createUserQuery().userId("markzgwu").count() < 1) {
						User user1 = identityService.newUser("markzgwu");
						user1.setPassword("123456");
						identityService.saveUser(user1);
						identityService.createMembership("markzgwu", "managers");
						identityService.createMembership("markzgwu", "admin");
					}
				}
			}
		};
	}

	@Bean
	public CommandLineRunner initBean(AuthUserRepository authUserRepository) {

		return new CommandLineRunner() {
			@Override
			public void run(String... strings) throws Exception {
				WorkflowUtils.listProcessDefinitions();
				/// *

				{
					if (authUserRepository.findByUsername("xiaomi") == null) {
						final AuthUser user = new AuthUser();
						user.setUsername("xiaomi");
						user.setPassword("123456");
						user.setWorkflowrole("user");
						authUserRepository.save(user);
					}
				}

				{
					if (authUserRepository.findByUsername("wuzhengang") == null) {
						final AuthUser user = new AuthUser();
						user.setUsername("wuzhengang");
						user.setPassword("123456");
						user.setWorkflowrole("managers");
						authUserRepository.save(user);
					}
				}

				{
					if (authUserRepository.findByUsername("admin") == null) {
						final AuthUser user = new AuthUser();
						user.setUsername("admin");
						user.setPassword("123456");
						user.setWorkflowrole("managers");
						authUserRepository.save(user);
					}

				}
				// */
			}
		};
	}
}
