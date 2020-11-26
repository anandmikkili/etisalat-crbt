/**
 * 
 */
package com.sixdee.etisalat.crbt.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ananda.rao
 * @Date 27-07-2019
 *
 */
@Entity
@Table(name="CATEGORY_ID_MAPPING")
public class CategoryIdMapping implements Serializable{
	
	private static final long serialVersionUID = 8374075742020544884L;

	static Logger logger = LoggerFactory.getLogger(CategoryIdMapping.class);
	
	@Id
	@Column(name="ID")
	private Integer id;
	@Column(name="PARENT_ID")
	private String parentId;
	@Column(name="CATAGORY_ID")
	private String categoryId;
	@Column(name="NAME")
	private String name;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
