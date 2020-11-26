/**
 * 
 */
package com.sixdee.etisalat.crbt.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ananda.rao
 * @Date 27-07-2019
 *
 */
@Entity
@Table(name = "RPT_TONE_DETAILS")
public class RptToneDetails implements Serializable {

	private static final long serialVersionUID = -9211649507578054104L;

	static Logger logger = LoggerFactory.getLogger(RptToneDetails.class);

	@Id
	@Column(name = "ID")
	private Integer id;
	@Column(name = "DATE")
	private Date date;
	@Column(name = "TONE_ID")
	private String toneId;
	@Column(name = "CATEGORY_ID")
	private String categoryId;
	@Column(name = "TONE_NAME")
	private String toneName;
	@Column(name = "TONE_PATH")
	private String tonePath;
	@Column(name = "PREVIEW_IMAGE", length = 65535, columnDefinition = "TEXT")
	@Type(type = "text")
	private String previewImage;
	@Column(name = "DOWNLAOD_COUNT")
	private Integer downlaodCount;
	@Column(name = "LIKE_COUNT")
	private Integer likeCount;
	@Column(name = "TONE_NAME_ARABIC")
	private String toneNameArabic;
	@Column(name = "ALBUM_NAME_ENGLISH")
	private String albumNameEnglish;
	@Column(name = "ALBUM_NAME_ARABIC")
	private String albumNameArabic;
	@Column(name = "ARTIST_NAME_ENGLISH")
	private String artisticNameEnglish;
	@Column(name = "ARTIST_NAME_ARABIC")
	private String artisticNameArabic;
	@Column(name = "PUBLISH_DATE")
	private Date publishDate;
	@Column(name = "BASE_CATEGORY")
	private String baseCategory;
	@Column(name = "CREATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public String getToneName() {
		return toneName;
	}

	public void setToneName(String toneName) {
		this.toneName = toneName;
	}

	public String getTonePath() {
		return tonePath;
	}

	public void setTonePath(String tonePath) {
		this.tonePath = tonePath;
	}

	public String getPreviewImage() {
		return previewImage;
	}

	public void setPreviewImage(String previewImage) {
		this.previewImage = previewImage;
	}

	public Integer getDownloadCount() {
		return downlaodCount;
	}

	public void setDownloadCount(Integer downloadCount) {
		this.downlaodCount = downloadCount;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public String getToneNameArabic() {
		return toneNameArabic;
	}

	public void setToneNameArabic(String toneNameArabic) {
		this.toneNameArabic = toneNameArabic;
	}

	public String getAlbumNameEnglish() {
		return albumNameEnglish;
	}

	public void setAlbumNameEnglish(String albumNameEnglish) {
		this.albumNameEnglish = albumNameEnglish;
	}

	public String getAlbumNameArabic() {
		return albumNameArabic;
	}

	public void setAlbumNameArabic(String albumNameArabic) {
		this.albumNameArabic = albumNameArabic;
	}

	public String getArtisticNameEnglish() {
		return artisticNameEnglish;
	}

	public void setArtisticNameEnglish(String artisticNameEnglish) {
		this.artisticNameEnglish = artisticNameEnglish;
	}

	public String getArtisticNameArabic() {
		return artisticNameArabic;
	}

	public void setArtisticNameArabic(String artisticNameArabic) {
		this.artisticNameArabic = artisticNameArabic;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getBaseCategory() {
		return baseCategory;
	}

	public void setBaseCategory(String baseCategory) {
		this.baseCategory = baseCategory;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/*
	 * public int compareTo(RptToneDetails o) { if (this.id == o.id) return 0; else
	 * if (this.id > o.id) return 1; else return -1; }
	 */

}
