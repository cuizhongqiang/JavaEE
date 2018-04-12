/**
 * @Description: TODO
 * @author zhengangwu
 */
package com.cnbmtech.cdwpcore.aaa.module.manager;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Supplier {
	@Id
	@GeneratedValue
	private Long id;
	private String LIFNR;
	private String LAND1;
	private String NAME1;
	private String NAME2;
	private String SORTL;
	private String REGIO;
	private String ORT01;
	private String PSTLZ;
	private String STRAS;
	private String TELF1;
	private String TELFX;
	private String STCD1;
	private String KTOKK;
	private String ERDAT;
	private String ERNAM;
	
	/**
	* @return id
	*/
	
	public Long getId() {
		return id;
	}
	
	/**
	 * @param paramtheparamthe{bare_field_name} to set
	 */
	
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	* @return lIFNR
	*/
	
	public String getLIFNR() {
		return LIFNR;
	}
	
	/**
	 * @param paramtheparamthe{bare_field_name} to set
	 */
	
	public void setLIFNR(String lIFNR) {
		LIFNR = lIFNR;
	}
	
	/**
	* @return lAND1
	*/
	
	public String getLAND1() {
		return LAND1;
	}
	
	/**
	 * @param paramtheparamthe{bare_field_name} to set
	 */
	
	public void setLAND1(String lAND1) {
		LAND1 = lAND1;
	}
	
	/**
	* @return nAME1
	*/
	
	public String getNAME1() {
		return NAME1;
	}
	
	/**
	 * @param paramtheparamthe{bare_field_name} to set
	 */
	
	public void setNAME1(String nAME1) {
		NAME1 = nAME1;
	}
	
	/**
	* @return nAME2
	*/
	
	public String getNAME2() {
		return NAME2;
	}
	
	/**
	 * @param paramtheparamthe{bare_field_name} to set
	 */
	
	public void setNAME2(String nAME2) {
		NAME2 = nAME2;
	}
	
	/**
	* @return sORTL
	*/
	
	public String getSORTL() {
		return SORTL;
	}
	
	/**
	 * @param paramtheparamthe{bare_field_name} to set
	 */
	
	public void setSORTL(String sORTL) {
		SORTL = sORTL;
	}
	
	/**
	* @return rEGIO
	*/
	
	public String getREGIO() {
		return REGIO;
	}
	
	/**
	 * @param paramtheparamthe{bare_field_name} to set
	 */
	
	public void setREGIO(String rEGIO) {
		REGIO = rEGIO;
	}
	
	/**
	* @return oRT01
	*/
	
	public String getORT01() {
		return ORT01;
	}
	
	/**
	 * @param paramtheparamthe{bare_field_name} to set
	 */
	
	public void setORT01(String oRT01) {
		ORT01 = oRT01;
	}
	
	/**
	* @return pSTLZ
	*/
	
	public String getPSTLZ() {
		return PSTLZ;
	}
	
	/**
	 * @param paramtheparamthe{bare_field_name} to set
	 */
	
	public void setPSTLZ(String pSTLZ) {
		PSTLZ = pSTLZ;
	}
	
	/**
	* @return sTRAS
	*/
	
	public String getSTRAS() {
		return STRAS;
	}
	
	/**
	 * @param paramtheparamthe{bare_field_name} to set
	 */
	
	public void setSTRAS(String sTRAS) {
		STRAS = sTRAS;
	}
	
	/**
	* @return tELF1
	*/
	
	public String getTELF1() {
		return TELF1;
	}
	
	/**
	 * @param paramtheparamthe{bare_field_name} to set
	 */
	
	public void setTELF1(String tELF1) {
		TELF1 = tELF1;
	}
	
	/**
	* @return tELFX
	*/
	
	public String getTELFX() {
		return TELFX;
	}
	
	/**
	 * @param paramtheparamthe{bare_field_name} to set
	 */
	
	public void setTELFX(String tELFX) {
		TELFX = tELFX;
	}
	
	/**
	* @return sTCD1
	*/
	
	public String getSTCD1() {
		return STCD1;
	}
	
	/**
	 * @param paramtheparamthe{bare_field_name} to set
	 */
	
	public void setSTCD1(String sTCD1) {
		STCD1 = sTCD1;
	}
	
	/**
	* @return kTOKK
	*/
	
	public String getKTOKK() {
		return KTOKK;
	}
	
	/**
	 * @param paramtheparamthe{bare_field_name} to set
	 */
	
	public void setKTOKK(String kTOKK) {
		KTOKK = kTOKK;
	}
	
	/**
	* @return eRDAT
	*/
	
	public String getERDAT() {
		return ERDAT;
	}
	
	/**
	 * @param paramtheparamthe{bare_field_name} to set
	 */
	
	public void setERDAT(String eRDAT) {
		ERDAT = eRDAT;
	}
	
	/**
	* @return eRNAM
	*/
	
	public String getERNAM() {
		return ERNAM;
	}
	
	/**
	 * @param paramtheparamthe{bare_field_name} to set
	 */
	
	public void setERNAM(String eRNAM) {
		ERNAM = eRNAM;
	}


}