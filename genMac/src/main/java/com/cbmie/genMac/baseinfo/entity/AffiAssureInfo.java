package com.cbmie.genMac.baseinfo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;

/**
 * 关联单位__担保
 */
@Entity
@Table(name = "AFFI_ASSURE_INFO")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class AffiAssureInfo extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 个人保证
	 */
	private String guarantee;
	
	/**
	 * 质押
	 */
	private String pledge;
	
	/**
	 * 动产抵押
	 */
	private String chattel;
	
	/**
	 * 不动产抵押
	 */
	private String realEstate;
	
	private Long pid;

	@Column
	public String getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}

	@Column
	public String getPledge() {
		return pledge;
	}

	public void setPledge(String pledge) {
		this.pledge = pledge;
	}

	@Column
	public String getChattel() {
		return chattel;
	}

	public void setChattel(String chattel) {
		this.chattel = chattel;
	}

	@Column
	public String getRealEstate() {
		return realEstate;
	}

	public void setRealEstate(String realEstate) {
		this.realEstate = realEstate;
	}

	@Column
	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

}