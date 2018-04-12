
/**  
* @Title: AbsAudit.java
* @Package com.cnbmtech.cdwpcore.aaa.module.audit
* @Description: TODO
* @author zhengangwu
* @date 2018年2月12日
* @version V1.0  
*/

package com.cnbmtech.cdwpcore.aaa.module.audit;

import java.time.ZonedDateTime;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.EntityListeners;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.cnbmtech.cdwpcore.aaa.module.account.AuthUtils;

/**
 * @ClassName: AbsAudit
 * @Description: TODO
 * @author zhengangwu
 * @date 2018年2月12日
 *
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)//选择继承策略
@DiscriminatorColumn(name="type")//配置鉴别器
@DiscriminatorValue("0")//设置鉴别器值
public abstract class AbsAudit {
	/**
	 * 创建时间
	 */
	@CreatedDate
	ZonedDateTime createTime = ZonedDateTime.now(); ;
	/**
	 * 创建人
	 */
	@CreatedBy
	String createBy;
	/**
	 * 修改时间
	 */
	@LastModifiedDate
	ZonedDateTime lastmodifiedTime = ZonedDateTime.now();
	/**
	 * 修改人
	 */
	@LastModifiedBy
	String lastmodifiedBy;

	public ZonedDateTime getCreateTime() {
		return createTime;
	}
	public void setCreateTime(ZonedDateTime createTime) {
		this.createTime = createTime;
	}
	public ZonedDateTime getLastmodifiedTime() {
		return lastmodifiedTime;
	}
	public void setLastmodifiedTime(ZonedDateTime lastmodifiedTime) {
		this.lastmodifiedTime = lastmodifiedTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getLastmodifiedBy() {
		return lastmodifiedBy;
	}
	public void setLastmodifiedBy(String lastmodifiedBy) {
		this.lastmodifiedBy = lastmodifiedBy;
	}

	@PrePersist
    public void prePersist() {
        final String createdByUser = AuthUtils.username();
        this.createBy = createdByUser;
        this.lastmodifiedBy = createdByUser;
    }

    @PreUpdate
    public void preUpdate() {
        String modifiedByUser = AuthUtils.username();
        this.lastmodifiedBy = modifiedByUser;
    }
	
}
