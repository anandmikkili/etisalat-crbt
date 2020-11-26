/**
 * 
 */
package com.sixdee.etisalat.crbt.model;

import java.io.Serializable;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * @author ananda.rao
 * @Date 27-07-2019
 */

@Embeddable
public class RecommendedTunesLoadCK implements Serializable{
	
	private static final long serialVersionUID = -3178814713861590804L;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID", nullable = false,updatable = false)
	private Integer id;
	@Column(name="PARTITION_ID" , nullable = false,updatable = false)
	private String partitionId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getPartitionId() {
		return partitionId;
	}
	public void setPartitionId(String partitionId) {
		this.partitionId = partitionId;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!RecommendedTunesLoadCK.class.isAssignableFrom(obj.getClass())) {
			return false;
		}

		final RecommendedTunesLoadCK other = (RecommendedTunesLoadCK) obj;
		if ((this.hashCode() == 0) ? (other.hashCode() != 0) : this.hashCode()!=other.hashCode()) {
			return false;
		}

		/*
		 * if (this.partitionId != other.partitionId) { return false; }
		 */

		return true;
	}

	@Override
	public int hashCode() {
		int hash = new Random().nextInt();
		hash = 53 * hash + (this.partitionId != null ? this.partitionId.hashCode() : 0);
		return hash;
	}

}
