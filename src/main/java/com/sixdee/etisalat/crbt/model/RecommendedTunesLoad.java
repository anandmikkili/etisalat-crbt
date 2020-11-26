/**
 * 
 */
package com.sixdee.etisalat.crbt.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ananda.rao
 * @Date 27-07-2019
 */
@Entity
@Table(name="RECOMMENDED_TUNES_LOAD")
public class RecommendedTunesLoad implements Serializable {
	
	private static final long serialVersionUID = 1263756414998898251L;

	static Logger logger = LoggerFactory.getLogger(RecommendedTunesLoad.class);
	
	@EmbeddedId
	private RecommendedTunesLoadCK recoTunesLoadCk;
	
	@Column(name="ID", insertable = false, updatable = false)
	private Integer id;
	@Column(name = "LOOKUP_ID")
	private Long lookupId;
	@Column(name="PARTITION_ID", insertable = false, updatable = false)
	private String partitionId;
	@Column(name="MSISDN")
	private String msisdn;
	@Column(name="PREDICTION_DATE")
	private Date predictionDate;
	@Column(name="TONE_ID")
	private String toneId;
	@Column(name="CATEGORY_ID")
	private String categoryId;
	@Column(name="TYPE_ID")
	private String typeId;
	@Column(name="CREATED_ON")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	@Column(name="CREATED_BY")
	private String createdBy;
	@Column(name="UPDATED_ON")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;
	@Column(name="UPDATED_BY")
	private String updatedBy;
	
	public RecommendedTunesLoadCK getRecoTunesLoadCk() {
		return recoTunesLoadCk;
	}
	public void setRecoTunesLoadCk(RecommendedTunesLoadCK recoTunesLoadCk) {
		this.recoTunesLoadCk = recoTunesLoadCk;
	}
	
	
	public Long getLookupId() {
		return lookupId;
	}
	public void setLookupId(Long lookupId) {
		this.lookupId = lookupId;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public Date getPredictionDate() {
		return predictionDate;
	}
	public void setPredictionDate(Date predictionDate) {
		this.predictionDate = predictionDate;
	}
	public String getToneId() {
		return toneId;
	}
	public void setToneId(String toneId) {
		this.toneId = toneId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
}
