/**
 * 
 */
package com.sixdee.etisalat.crbt.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sixdee.etisalat.crbt.constants.CrbtConstants;
import com.sixdee.etisalat.crbt.model.LoadingStatus;
import com.sixdee.etisalat.crbt.model.RecommendedTunesLoad;
import com.sixdee.etisalat.crbt.model.RecommendedTunesLoadCK;
import com.sixdee.etisalat.crbt.model.RptToneDetails;
import com.sixdee.etisalat.crbt.service.CrbtDataSourceDaoImpl;
import com.sixdee.etisalat.crbt.utils.DataParser;

/**
 * @author ananda.rao
 * @Date 27-07-2019
 */
public class JustForYouRecommendor implements TuneRecommendor {

	public static Logger logger = LoggerFactory.getLogger(JustForYouRecommendor.class);

	public void recommendTunes(String msisdn, List<String> liked_catids, List<String> purchased_tunes,
			CrbtDataSourceDaoImpl crbtDataSourceDaoImpl) {
		List<String> jfy_cat_ids = null;
		List<RptToneDetails> jfy_tunes = null;
		Set<RptToneDetails> reco_jfy_tunes = null;
		Set<String> reco_tunes = null;
		/***
		 * ############# Flow for JUST FOR YOU ###########################
		 */
		try {
			// Step 1: Fetch Category Ids from CATAGORY_ID_MAPPING by sending parent id and
			// name
			jfy_cat_ids = new ArrayList<String>(liked_catids);

			// Step 2: Fetch the tune ids from RPT_TONE_DETAILS by passing Subscriber
			// preferred Category Ids
			jfy_tunes = new ArrayList<RptToneDetails>();
			List<RptToneDetails> rptToneDetails = null;
			if (null != jfy_cat_ids && 0 < jfy_cat_ids.size()) {
				for (String catid : jfy_cat_ids) {
					rptToneDetails = crbtDataSourceDaoImpl.getRptToneDetails(catid);
					if (0 < rptToneDetails.size()) {
						jfy_tunes.addAll(rptToneDetails);
					}
				}
			} else {
				logger.info("No Category Ids present for subscriber which was Liked for Just For You to recommend");
			}
			// Step 3: Remove Tune Ids which are already purchased or downloaded
			if (null !=jfy_tunes && 0 < jfy_tunes.size()) {
				ListIterator<RptToneDetails> itr = jfy_tunes.listIterator();
				while (itr.hasNext()) {
					RptToneDetails details = itr.next();
					if (purchased_tunes.contains(details.getToneId())) {
						itr.remove();
					}

				}
			}

			// Step 4: Divide the tune details as per category to build selection with equal
			// distribution logic against each category id
			Set<String> remaining_catids = new TreeSet<String>();
			for (RptToneDetails details : jfy_tunes) {
				remaining_catids.add(details.getCategoryId());
			}
			if (null != remaining_catids && 0 < remaining_catids.size()) {
				if (10 > remaining_catids.size()) {
					int reco_count = (10 / remaining_catids.size());
					reco_jfy_tunes = new HashSet<RptToneDetails>();
					reco_tunes = new HashSet<String>();
					int count = 0;
					int total = 0;
					for (String catid : remaining_catids) {
						for (RptToneDetails details : jfy_tunes) {
							if (Integer.parseInt(catid) == Integer.parseInt(details.getCategoryId())) {
								if (count < reco_count && total < 10) {
									if (!reco_tunes.contains(details.getToneId())) {
										reco_jfy_tunes.add(details);
										reco_tunes.add(details.getToneId());
										total += 1;
										count += 1;
									}
								} else {
									count = 0;
									break;
								}
							}
						}
					}
				} else {
					int reco_count = 1;
					reco_jfy_tunes = new HashSet<RptToneDetails>();
					reco_tunes = new HashSet<String>();
					int count = 0;
					int total = 0;
					for (String catid : remaining_catids) {
						for (RptToneDetails details : jfy_tunes) {
							if (Integer.parseInt(catid) == Integer.parseInt(details.getCategoryId())) {
								if (count < reco_count && total < 10) {
									if (!reco_tunes.contains(details.getToneId())) {
										reco_jfy_tunes.add(details);
										reco_tunes.add(details.getToneId());
										count += 1;
										total += 1;
									}
								} else {
									count = 0;
									break;
								}
							}
						}
					}
				}

			} else {
				logger.info("No category ids are left to recommend as per the given input for the subscriber");
			}

			// Step 5: Check the need of adding tunes with highest down load count and add
			// if necessary
			if (null != reco_jfy_tunes && 10 > reco_jfy_tunes.size()) {
				int required_count = (10 - reco_jfy_tunes.size());
				List<RptToneDetails> most_download_Counts = crbtDataSourceDaoImpl.getBestDownloadRptToneDetails();
				for (RptToneDetails details : most_download_Counts) {
					if (reco_tunes.contains(details.getToneId())) {

					} else {
						if (required_count != 0 && !purchased_tunes.contains(details.getToneId())) {
							reco_jfy_tunes.add(details);
							reco_tunes.add(details.getToneId());
							required_count--;
						} else {
							break;
						}
					}
				}
			} else if (null == reco_jfy_tunes) {
				reco_jfy_tunes = new HashSet<RptToneDetails>();
				reco_tunes = new HashSet<String>();
				int required_count = 10;
				List<RptToneDetails> most_download_Counts = crbtDataSourceDaoImpl.getBestDownloadRptToneDetails();
				for (RptToneDetails details : most_download_Counts) {
					if (reco_tunes.contains(details.getToneId())) {

					} else {
						if (required_count != 0) {
							if (!purchased_tunes.contains(details.getToneId())) {
								reco_jfy_tunes.add(details);
								reco_tunes.add(details.getToneId());
								required_count--;
							}
						} else {
							break;
						}
					}
				}

			} else {
				logger.info(
						"No need to add any extra tunes from most downloads list as the size requirement is already met");
			}

			// Step 6: Save them to RECOMMENDED_TUNES_LOAD table
			RecommendedTunesLoad recommendedTunesLoad = null;
			RecommendedTunesLoadCK recommendedTunesLoadCK = null;
			LoadingStatus loadingStatus = crbtDataSourceDaoImpl.getLoadingStatus(CrbtConstants.CAT_JUSTFORYOU,
					Long.parseLong(DataParser.getDateAsStringAsYyyyMMdd()));
			for (RptToneDetails details : reco_jfy_tunes) {
				recommendedTunesLoadCK = new RecommendedTunesLoadCK();
				recommendedTunesLoadCK.setPartitionId(String.valueOf(loadingStatus.getId()));
				recommendedTunesLoad = new RecommendedTunesLoad();
				recommendedTunesLoad.setRecoTunesLoadCk(recommendedTunesLoadCK);
				recommendedTunesLoad.setLookupId(loadingStatus.getId().longValue());
				recommendedTunesLoad.setMsisdn(msisdn);
				recommendedTunesLoad.setPredictionDate(new Date());
				recommendedTunesLoad.setToneId(details.getToneId());
				recommendedTunesLoad.setCategoryId(details.getCategoryId());
				recommendedTunesLoad.setTypeId(CrbtConstants.CAT_JUSTFORYOU);
				recommendedTunesLoad.setCreatedOn(new Date());
				recommendedTunesLoad.setCreatedBy("");
				recommendedTunesLoad.setUpdatedBy("");
				recommendedTunesLoad.setUpdatedOn(new Date());

				crbtDataSourceDaoImpl.saveRecoTunesLoad(recommendedTunesLoad);
			}
		} catch (Exception e) {
			logger.error("Exception has been occured in method recommendTunes in JustForYouRecommendor class: ", e);
		} finally {
			reco_tunes = null;
			reco_jfy_tunes = null;
			jfy_tunes = null;
			jfy_cat_ids = null;
		}

	}

}
