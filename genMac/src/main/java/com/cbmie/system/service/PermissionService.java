package com.cbmie.system.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.system.dao.PermissionDao;
import com.cbmie.system.entity.Permission;

/**
 * 权限service
 */
@Service
@Transactional(readOnly=true)
public class PermissionService extends BaseService<Permission, Integer>{
	
	@Autowired
	private PermissionDao permissionDao;
	
	@Override
	public HibernateDao<Permission, Integer> getEntityDao() {
		return permissionDao;
	}
	
	/**
	 * 添加菜单基础操作
	 * @param pid 菜单id
	 * @param pName 菜单权限标识名
	 */
	@Transactional(readOnly = false)
	public void addBaseOpe(Integer pid,String pClassName){
		List<Permission> pList=new ArrayList<Permission>();
		pList.add(new Permission(pid, "添加", "O", "", "sys:"+pClassName+":add"));
		pList.add(new Permission(pid, "删除", "O", "", "sys:"+pClassName+":delete"));
		pList.add(new Permission(pid, "修改", "O", "", "sys:"+pClassName+":update"));
		pList.add(new Permission(pid, "查看", "O", "", "sys:"+pClassName+":view"));
		
		//添加没有的基本操作
		List<Permission> existPList=getMenuOperation(pid);
		for(Permission permission:pList){
			boolean exist=false;
			for(Permission existPermission:existPList){
				if(permission.getPermCode().equals(existPermission.getPermCode())){
					exist=true;
					break;
				}else{
					exist=false;
				}
			}
			if(!exist)
				save(permission);
		}
	}
	
	/**
	 * 获取角色拥有的权限集合
	 * @param userId
	 * @return 结果集合
	 */
	public List<Permission> getPermissions(Integer userId){
		return permissionDao.findPermissions(userId);
	}
	
	/**
	 * 获取角色拥有的菜单
	 * @param userId
	 * @return 菜单集合
	 */
	public List<Permission> getMenus(Integer userId){
		List<Permission> all = permissionDao.findMenus(userId);
		List<Permission> result = new ArrayList<Permission>();
		List<Permission> rootP = new ArrayList<Permission>();
		for(Permission p : all){
			if(p.getPid()==null && "1".equals(p.getEffective())){
				rootP.add(p);
			}
		}
		all.removeAll(rootP);
		result.addAll(rootP);
		getSonP(all,result,rootP);
		return result;
	}
	
	/**
	 * 获取所有菜单
	 * @return 菜单集合
	 */
	public List<Permission> getMenus(){
		return permissionDao.findMenus();
	}
	
	/**
	 * 获取菜单下的操作
	 * @param pid
	 * @return 操作集合
	 */
	public List<Permission> getMenuOperation(Integer pid){
		return permissionDao.findMenuOperation(pid);
	}
	
	/**
	 * 获取菜单及操作
	 * @param 
	 * @return 
	 */
	public List<Permission> getAllEffectiveP(){
		List<Permission> all = permissionDao.getAll();
		List<Permission> result = new ArrayList<Permission>();
		List<Permission> rootP = new ArrayList<Permission>();
		for(Permission p : all){
			if(p.getPid()==null && "F".equals(p.getType()) && "1".equals(p.getEffective())){
				rootP.add(p);
			}
		}
		all.removeAll(rootP);
		result.addAll(rootP);
		getSonP(all,result,rootP);
		return result;
	}
	
	private void getSonP(List<Permission> all,List<Permission> result,List<Permission> parents){
		List<Permission> sons = new ArrayList<Permission>();
		try{
			for(Permission p : parents){
				for(Permission a : all){
					if(a.getPid()!=null && p.getId().intValue()==a.getPid().intValue() && "1".equals(a.getEffective())){
						sons.add(a);
					}
				}
			}
			if(sons.size()>0){
				all.removeAll(sons);
				result.addAll(sons);
				getSonP(all, result, sons);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
