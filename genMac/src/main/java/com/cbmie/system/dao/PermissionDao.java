package com.cbmie.system.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.system.entity.Permission;


/**
 * 权限DAO
 */
@Repository
public class PermissionDao extends HibernateDao<Permission, Integer>{

	/**
	 * 查询用户拥有的权限
	 * @param userId 用户id
	 * @return 结果集合
	 */
	@SuppressWarnings("unchecked")
	public List<Permission> findPermissions(Integer userId){
		StringBuffer sb=new StringBuffer();
		sb.append("select p.* from permission p ");
		sb.append("INNER JOIN role_permission rp on p.ID=rp.PERMISSION_ID ");
		sb.append("INNER JOIN role r ON  r.id=rp.ROLE_ID ");
		sb.append("INNER JOIN user_role  ur ON ur.ROLE_ID =rp.ROLE_ID ");
		sb.append("INNER JOIN user u ON u.id = ur.USER_ID ");
		sb.append("where u.id=?0 order by p.sort");
		SQLQuery sqlQuery=createSQLQuery(sb.toString(), userId);
		sqlQuery.addEntity(Permission.class);
		//sqlQuery.setCacheable(true);
		return sqlQuery.list();
	}
	
	/**
	 * 查询所有的菜单
	 * @param userId
	 * @return 菜单集合
	 */
	@SuppressWarnings("unchecked")
	public List<Permission> findMenus(){
		StringBuffer sb=new StringBuffer();
		sb.append("select p.ID id,p.PID pid,p.NAME name,p.URL url,p.ICON icon,p.SORT sort,p.DESCRIPTION description,p.effective effective from permission p ");
		sb.append("where p.TYPE='F' order by p.sort");
		SQLQuery sqlQuery=createSQLQuery(sb.toString());
		sqlQuery.addScalar("id",StandardBasicTypes.INTEGER );
		sqlQuery.addScalar("pid", StandardBasicTypes.INTEGER);
		sqlQuery.addScalar("name",StandardBasicTypes.STRING);
		sqlQuery.addScalar("url",StandardBasicTypes.STRING);
		sqlQuery.addScalar("icon",StandardBasicTypes.STRING);
		sqlQuery.addScalar("sort",StandardBasicTypes.INTEGER);
		sqlQuery.addScalar("description",StandardBasicTypes.STRING);
		sqlQuery.addScalar("effective",StandardBasicTypes.STRING);
		sqlQuery.setResultTransformer(Transformers.aliasToBean(Permission.class));
		//sqlQuery.setCacheable(true);
		return sqlQuery.list();
	}
	
	
	/**
	 * 查询用户拥有的菜单权限
	 * @param userId
	 * @return 结果集合
	 */
	@SuppressWarnings("unchecked")
	public List<Permission> findMenus(Integer userId){
		StringBuffer sb=new StringBuffer();
		sb.append("select p.ID id,p.PID pid,p.NAME name,p.URL url,p.ICON icon,p.SORT sort,p.DESCRIPTION description,p.effective effective from permission p ");
		sb.append("INNER JOIN role_permission rp on p.ID=rp.PERMISSION_ID ");
		sb.append("INNER JOIN role r ON r.id=rp.ROLE_ID ");
		sb.append("INNER JOIN user_role ur ON ur.ROLE_ID =rp.ROLE_ID ");
		sb.append("INNER JOIN user u ON u.id = ur.USER_ID ");
		sb.append("where 1=1 ");
		sb.append("and u.id=?0 ");
		sb.append("and p.TYPE='F' ");
		sb.append("group by p.ID,p.PID,p.NAME,p.URL,p.ICON,p.SORT,p.DESCRIPTION ");
		sb.append("order by p.sort ");
		
		SQLQuery sqlQuery=createSQLQuery(sb.toString(), userId);
		sqlQuery.addScalar("id",StandardBasicTypes.INTEGER );
		sqlQuery.addScalar("pid", StandardBasicTypes.INTEGER);
		sqlQuery.addScalar("name",StandardBasicTypes.STRING);
		sqlQuery.addScalar("url",StandardBasicTypes.STRING);
		sqlQuery.addScalar("icon",StandardBasicTypes.STRING);
		sqlQuery.addScalar("sort",StandardBasicTypes.INTEGER);
		sqlQuery.addScalar("description",StandardBasicTypes.STRING);
		sqlQuery.addScalar("effective",StandardBasicTypes.STRING);
		sqlQuery.setResultTransformer(Transformers.aliasToBean(Permission.class));
		//sqlQuery.setCacheable(true);
		List<Permission> t = sqlQuery.list();
		return t;
	}
	
	/**
	 * 查询菜单下的操作权限
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Permission> findMenuOperation(Integer pid){
		StringBuffer sb=new StringBuffer();
		sb.append("select p.ID id,p.NAME name,p.URL url,p.PERM_CODE permCode,p.sort sort,p.DESCRIPTION description from permission p ");
		sb.append("where p.TYPE='O' and p.PID=?0 order by p.SORT");
		SQLQuery sqlQuery=createSQLQuery(sb.toString(),pid);
		sqlQuery.addScalar("id",StandardBasicTypes.INTEGER );
		sqlQuery.addScalar("name",StandardBasicTypes.STRING);
		sqlQuery.addScalar("url",StandardBasicTypes.STRING);
		sqlQuery.addScalar("permCode",StandardBasicTypes.STRING);
		sqlQuery.addScalar("sort",StandardBasicTypes.INTEGER);
		sqlQuery.addScalar("description",StandardBasicTypes.STRING);
		sqlQuery.setResultTransformer(Transformers.aliasToBean(Permission.class));
		//sqlQuery.setCacheable(true);
		return sqlQuery.list();
	}
	
	public  List<Permission> getEffectivePermission(){
		/*StringBuffer sb=new StringBuffer();
		sb.append("select p.* from permission p ");
		sb.append("INNER JOIN role_permission rp on p.ID=rp.PERMISSION_ID ");
		sb.append("INNER JOIN role r ON  r.id=rp.ROLE_ID ");
		sb.append("INNER JOIN user_role  ur ON ur.ROLE_ID =rp.ROLE_ID ");
		sb.append("INNER JOIN user u ON u.id = ur.USER_ID ");
		sb.append("where u.id=?0 order by p.sort");
		SQLQuery sqlQuery=createSQLQuery(sb.toString(), userId);
		sqlQuery.addEntity(Permission.class);
		//sqlQuery.setCacheable(true);
		return sqlQuery.list();*/
		return null;
	}
	
	public List<Permission> getAll(){
		try{
			String sql = "from Permission order by sort";
			List<Permission> result = this.createQuery(sql).list();
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
}
