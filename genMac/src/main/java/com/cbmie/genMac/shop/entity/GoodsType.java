package com.cbmie.genMac.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cbmie.common.entity.BaseEntity;

/**
 */
@Entity
@Table(name = "goods_type")
public class GoodsType extends BaseEntity implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private Long pid;
	private String name;
	private String code;
//	@JsonIgnore
//	private Set<Goods> goodses = new HashSet<Goods>(0);

	@Column(name = "PID")
	public Long getPid() {
		return this.pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	@Column(name = "NAME")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CODE")
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "goodsType")
//	public Set<Goods> getGoodses() {
//		return this.goodses;
//	}
//
//	public void setGoodses(Set<Goods> goodses) {
//		this.goodses = goodses;
//	}

}