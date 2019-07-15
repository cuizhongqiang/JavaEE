package com.cbmie.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.activiti.service.ActivitiIdentityService;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.common.utils.DateUtils;
import com.cbmie.common.utils.security.Digests;
import com.cbmie.common.utils.security.Encodes;
import com.cbmie.system.dao.UserDao;
import com.cbmie.system.entity.Organization;
import com.cbmie.system.entity.User;

/**
 * 用户service
 */
@Service
@Transactional
public class UserService extends BaseService<User, Integer> {
	
	/**加密方法*/
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;	//盐长度

	@Autowired
	ActivitiIdentityService activitiIdentityService;
	
	@Autowired
	private UserDao userDao;

	@Override
	public HibernateDao<User, Integer> getEntityDao() {
		return userDao;
	}

	/**
	 * 保存用户
	 * @param user
	 */
	@Transactional(readOnly=false)
	public void save(User user) {
		entryptPassword(user);
		user.setCreateDate(DateUtils.getSysTimestamp());
		userDao.save(user);
		
		activitiIdentityService.synActivitiUser(user);
	}
	
	public void update(User user){
		userDao.save(user);
		activitiIdentityService.synActivitiUser(user);
	}
	/**
	 * 修改密码
	 * @param user
	 */
	@Transactional(readOnly=false)
	public void updatePwd(User user) {
		entryptPassword(user);
		userDao.save(user);
	}
	
	/**
	 * 删除用户
	 * @param id
	 */
	@Transactional(readOnly=false)
	public void delete(Integer id){
		if(!isSupervisor(id)){
			String loginname = getUser(id).getLoginName();
			activitiIdentityService.deleteActivitiUser(loginname);
			userDao.delete(id);
		}
	}
	
	/**
	 * 按登录名查询用户
	 * @param loginName
	 * @return 用户对象
	 */
	public User getUser(String loginName) {
		return userDao.findUniqueBy("loginName", loginName);
	}
	
	/**
	 * 按id查询用户
	 * @param id
	 * @return 用户对象
	 */
	public User getUser(Integer id) {
		return userDao.findUniqueBy("id", id);
	}
	
	/**
	 * 判断是否超级管理员
	 * @param id
	 * @return boolean
	 */
	private boolean isSupervisor(Integer id) {
		return id == 1;
	}
	
	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(),salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}
	
	/**
	 * 验证原密码是否正确
	 * @param user
	 * @param oldPwd
	 * @return
	 */
	public boolean checkPassword(User user,String oldPassword){
		byte[] salt =Encodes.decodeHex(user.getSalt()) ;
		byte[] hashPassword = Digests.sha1(oldPassword.getBytes(),salt, HASH_INTERATIONS);
		if(user.getPassword().equals(Encodes.encodeHex(hashPassword))){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 修改用户登录
	 * @param user
	 */
	public void updateUserLogin(User user){
		user.setLoginCount((user.getLoginCount()==null?0:user.getLoginCount())+1);
		user.setPreviousVisit(user.getLastVisit());
		user.setLastVisit(DateUtils.getSysTimestamp());
		update(user);
		userDao.getSession().flush();
	}
	
	
	/**
	 *获取业务员信息（用户角色为业务员）
	 * @param num
	 * @return
	 */
	public List<User> getSupplier(String num) {
		return userDao.getSupplier(num);
	}
	
	/**
	 * 修改用户所在部门
	 */
	public void updateUserOrg(User user, Organization organization){
		user.setOrganization(organization);
		userDao.save(user);
		userDao.getSession().flush();
	}

}
