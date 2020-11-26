/**
 * 
 */
package com.sixdee.etisalat.crbt.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sixdee.etisalat.crbt.dao.CrbtDataSourceDao;
import com.sixdee.etisalat.crbt.model.CategoryIdMapping;
import com.sixdee.etisalat.crbt.model.LoadingStatus;
import com.sixdee.etisalat.crbt.model.RecommendedTunesLoad;
import com.sixdee.etisalat.crbt.model.RptToneDetails;

/**
 * @author ananda.rao
 * @Date 27-07-2019
 */

@Repository
public class CrbtDataSourceDaoImpl implements CrbtDataSourceDao {

	Logger logger = LoggerFactory.getLogger(CrbtDataSourceDaoImpl.class);

	@PersistenceContext(unitName = "CrbtPersistenceUnit")
	private EntityManager entityManager;

	/***
	 * 
	 * @param typeid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public LoadingStatus getLoadingStatus(String typeid, Long pdate) {
		LoadingStatus loadingStatus = null;
		if (null != typeid) {
			Query query = entityManager.createNativeQuery("SELECT * FROM LOADING_STATUS WHERE PREDICTION_DATE=:pdate AND TYPE_ID=:typeid");
			query.setParameter("pdate", pdate);
			query.setParameter("typeid", typeid);
			List<Object[]> status = query.getResultList();
			for (Object[] stat : status) {
				loadingStatus = new LoadingStatus();
				loadingStatus.setId(Integer.parseInt(String.valueOf(stat[0])));
				loadingStatus.setFileName(String.valueOf(stat[1]));
				loadingStatus.setTypeId(String.valueOf(stat[2]));
				loadingStatus.setPredictionDate(Long.parseLong(String.valueOf(stat[3])));
				loadingStatus.setStatus(Integer.parseInt(String.valueOf(stat[4])));
				if (stat[5] instanceof java.util.Date) {
					loadingStatus.setCreateDate((Date) stat[5]);
				}
				loadingStatus.setCreatedBy(String.valueOf(stat[6]));
				if (stat[7] instanceof java.util.Date) {
					loadingStatus.setUpdatedDate((Date) stat[7]);
				}
				loadingStatus.setUpdatedBy(String.valueOf(stat[8]));
			}
		} else {
			logger.info("The type id passed for fetching LoadingStatus is null or empty");

		}
		return loadingStatus;
	}
	

	/***
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CategoryIdMapping getCategoryMapping(String id) {
		CategoryIdMapping categoryMapping = null;
		if (null != id) {
			Query query = entityManager.createNativeQuery("SELECT * FROM CATAGORY_ID_MAPPING WHERE CATAGORY_ID=:id");
			query.setParameter("id", id);
			List<Object[]> categories = query.getResultList();
			for (Object[] cat : categories) {
				categoryMapping = new CategoryIdMapping();
				categoryMapping.setId(Integer.parseInt(String.valueOf(cat[0])));
				categoryMapping.setParentId(String.valueOf(cat[1]));
				categoryMapping.setCategoryId(String.valueOf(cat[2]));
				categoryMapping.setName(String.valueOf(cat[3]));
			}
			return categoryMapping;
		}
		return categoryMapping;
	}

	@SuppressWarnings("unchecked")
	public List<CategoryIdMapping> getCategoryMapping(String id, String name) {
		List<CategoryIdMapping> categoryMappings = new ArrayList<CategoryIdMapping>();
		CategoryIdMapping categoryIdMapping = null;
		if (null != id) {
			Query query = entityManager
					.createNativeQuery("SELECT * FROM CATAGORY_ID_MAPPING WHERE PARENT_ID=:parentid AND NAME=:name");
			query.setParameter("parentid", id);
			query.setParameter("name", name);
			List<Object[]> categories = query.getResultList();

			for (Object[] cat : categories) {
				categoryIdMapping = new CategoryIdMapping();
				categoryIdMapping.setId(Integer.parseInt(String.valueOf(cat[0])));
				categoryIdMapping.setParentId(String.valueOf(cat[1]));
				categoryIdMapping.setCategoryId(String.valueOf(cat[2]));
				categoryIdMapping.setName(String.valueOf(cat[3]));
				categoryMappings.add(categoryIdMapping);
			}
		}
		return categoryMappings;
	}
	
	/***
	 * 
	 * @param catid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RptToneDetails> getRptToneDetails(String catid) {
		List<RptToneDetails> rptToneDetails = new ArrayList<RptToneDetails>();
		RptToneDetails toneDetails = null;
		if (null != catid) {
			Query query = entityManager.createNativeQuery("SELECT * FROM RPT_TONE_DETAILS WHERE CATEGORY_ID=:catid");
			query.setParameter("catid", catid);
			List<Object[]> rptTones = query.getResultList();
			for (Object[] tones : rptTones) {
				toneDetails = new RptToneDetails();
				toneDetails.setId(Integer.parseInt(String.valueOf(tones[0])));
				if (tones[1] instanceof java.util.Date) {
					toneDetails.setDate((Date) tones[1]);
				}
				toneDetails.setToneId(String.valueOf(tones[2]));
				toneDetails.setCategoryId(String.valueOf(tones[3]));
				toneDetails.setToneName(String.valueOf(tones[4]));
				toneDetails.setTonePath(String.valueOf(tones[5]));
				toneDetails.setPreviewImage(String.valueOf(tones[6]));
				toneDetails.setDownloadCount(Integer.parseInt(String.valueOf(tones[7])));
				toneDetails.setLikeCount(Integer.parseInt(String.valueOf(tones[8])));
				toneDetails.setToneNameArabic(String.valueOf(tones[9]));
				toneDetails.setAlbumNameEnglish(String.valueOf(tones[10]));
				toneDetails.setAlbumNameArabic(String.valueOf(tones[11]));
				toneDetails.setArtisticNameEnglish(String.valueOf(tones[12]));
				toneDetails.setArtisticNameArabic(String.valueOf(tones[13]));
				if (tones[14] instanceof java.util.Date) {
					toneDetails.setPublishDate((Date) tones[14]);
				}
				toneDetails.setBaseCategory(String.valueOf(tones[15]));
				if (tones[16] instanceof java.util.Date) {
					toneDetails.setCreateDate((Date) tones[16]);
				}
				rptToneDetails.add(toneDetails);
			}

		} else {
			logger.info(
					"The method getRptToneDetails is returning empty list due to either category_id was null or not present in the table");
		}
		return rptToneDetails;
	}

	/***
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RptToneDetails> getBestDownloadRptToneDetails() {
		List<RptToneDetails> rptToneDetails = new ArrayList<RptToneDetails>();
		RptToneDetails toneDetails = null;

		Query query = entityManager.createNativeQuery("SELECT * FROM RPT_TONE_DETAILS ORDER BY DOWNLAOD_COUNT DESC LIMIT 40");
		List<Object[]> rptTones = query.getResultList();
		for (Object[] tones : rptTones) {
			toneDetails = new RptToneDetails();
			toneDetails.setId(Integer.parseInt(String.valueOf(tones[0])));
			if (tones[1] instanceof java.util.Date) {
				toneDetails.setDate((Date) tones[1]);
			}
			toneDetails.setToneId(String.valueOf(tones[2]));
			toneDetails.setCategoryId(String.valueOf(tones[3]));
			toneDetails.setToneName(String.valueOf(tones[4]));
			toneDetails.setTonePath(String.valueOf(tones[5]));
			toneDetails.setPreviewImage(String.valueOf(tones[6]));
			toneDetails.setDownloadCount(Integer.parseInt(String.valueOf(tones[7])));
			toneDetails.setLikeCount(Integer.parseInt(String.valueOf(tones[8])));
			toneDetails.setToneNameArabic(String.valueOf(tones[9]));
			toneDetails.setAlbumNameEnglish(String.valueOf(tones[10]));
			toneDetails.setAlbumNameArabic(String.valueOf(tones[11]));
			toneDetails.setArtisticNameEnglish(String.valueOf(tones[12]));
			toneDetails.setArtisticNameArabic(String.valueOf(tones[13]));
			if (tones[14] instanceof java.util.Date) {
				toneDetails.setPublishDate((Date) tones[14]);
			}
			toneDetails.setBaseCategory(String.valueOf(tones[15]));
			if (tones[16] instanceof java.util.Date) {
				toneDetails.setCreateDate((Date) tones[16]);
			}
			rptToneDetails.add(toneDetails);
		}
		return rptToneDetails;
	}

	@Transactional
	public boolean saveRecoTunesLoad(RecommendedTunesLoad recommendedTunesLoad) {

		if (null != recommendedTunesLoad && null != recommendedTunesLoad.getRecoTunesLoadCk()
				&& null != recommendedTunesLoad.getMsisdn()) {

			entityManager.persist(recommendedTunesLoad);
			logger.info("The RecommendedTunesLoad has been persisted for MSISDN: " + recommendedTunesLoad.getMsisdn());
			return true;
		} else {
			logger.info(
					"The method saveRecoTunesLoad is returning false due to either RecommendedTunesLoadCK was null or MSISDN set was null");
			return false;
		}
	}

}
