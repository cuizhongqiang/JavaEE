package com.cbmie.genMac.agent.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;

/**
 * 代理进口补充
 * @author czq
 */
@Entity
@Table(name = "agent_import_supplement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class AgentImportSupplement extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -595260427222943439L;

	/**
	 * 合同名称
	 */
	private String contractName;
	
	/**
	 * 签订主体名称
	 */
	private String principalName;
	
	/**
	 * 协议编号
	 */
	private String agreementNo;
	
	/**
	 * 合同内容
	 */
	private String content;
	
	private Long pid;
	
	@Column(name = "CONTRACT_NAME")
	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	@Column(name = "PRINCIPAL_NAME")
	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	
	@Column(name = "AGREEMENT_NO")
	public String getAgreementNo() {
		return agreementNo;
	}

	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}

	@Column
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "PID")
	public Long getPid() {
		return this.pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}
	
}
