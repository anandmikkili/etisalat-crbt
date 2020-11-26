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
import com.sixdee.etisalat.crbt.model.CategoryIdMapping;
import com.sixdee.etisalat.crbt.model.LoadingStatus;
import com.sixdee.etisalat.crbt.model.RecommendedTunesLoad;
import com.sixdee.etisalat.crbt.model.RecommendedTunesLoadCK;
import com.sixdee.etisalat.crbt.model.RptToneDetails;
import com.sixdee.etisalat.crbt.service.CrbtDataSourceDaoImpl;
import com.sixdee.etisalat.crbt.utils.DataParser;

/**
 * @author ananda.rao
 * @Date 27-07-2019
 *
 */
public class NewReleasesRecommendor implements TuneRecommendor {

	public static Logger logger = LoggerFactory.getLogger(NewReleasesRecommendor.class);

	public void recommendTunes(String mdn, List<String> preferred_catids, List<String> purchased_tunes,
			CrbtDataSourceDaoImpl crbtDataSourceDaoImpl) {
		List<String> new_release_cat_ids = null;
		List<RptToneDetails> new_release_tunes = null;
		Set<RptToneDetails> reco_new_release_tunes = null;
		Set<String> reco_tunes = null;
		/***
		 * ############# Flow for New Release ###########################
		 */
		try {
			// Step 1: Fetch Category Ids from CATAGORY_ID_MAPPING by sending parent id and
			// name
			new_release_cat_ids = new ArrayList<String>();
			if (null != preferred_catids && 0 < preferred_catids.size()) {
				for (String parentid : preferred_catids) {
					List<CategoryIdMapping> categoryIdMappings = crbtDataSourceDaoImpl.getCategoryMapping(parentid,
							CrbtConstants.CAT_NEW_RELEASES);
					if (0 < categoryIdMappings.size()) {
						for (CategoryIdMapping categoryIdMapping : categoryIdMappings) {
							if (null != categoryIdMapping.getCategoryId()) {
								new_release_cat_ids.add(categoryIdMapping.getCategoryId());
							}
						}
					}
				}
			}
			// Step 2: Fetch the tune ids from RPT_TONE_DETAILS by passing Subscriber
			// preferred Category Ids
			new_release_tunes = new ArrayList<RptToneDetails>();
			List<RptToneDetails> rptToneDetails = null;
			if (null != new_release_cat_ids && 0 < new_release_cat_ids.size()) {
				for (String catid : new_release_cat_ids) {
					rptToneDetails = crbtDataSourceDaoImpl.getRptToneDetails(catid);
					if (0 < rptToneDetails.size()) {
						new_release_tunes.addAll(rptToneDetails);
					}
				}
			} else {
				logger.info("No Category Ids present for subscriber which was preferred for NewReleases to recommend");
			}
			// Step 3: Remove Tune Ids which are already purchased or downloaded
			if (null != new_release_tunes && 0 < new_release_tunes.size() && !purchased_tunes.isEmpty()) {
				ListIterator<RptToneDetails> itr = new_release_tunes.listIterator();
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
			if (null != new_release_tunes && 0 < new_release_tunes.size()) {
				for (RptToneDetails details : new_release_tunes) {
					remaining_catids.add(details.getCategoryId());
				}
			}
			if (null !=remaining_catids && 0 < remaining_catids.size()) {
				if (20 > remaining_catids.size()) {
					int reco_count = (20 / remaining_catids.size());
					reco_new_release_tunes = new HashSet<RptToneDetails>();
					reco_tunes = new HashSet<String>();
					int count = 0;
					int total = 0;
					for (String catid : remaining_catids) {
						for (RptToneDetails details : new_release_tunes) {
							if (Integer.parseInt(catid) == Integer.parseInt(details.getCategoryId())) {
								if (count < reco_count && total < 20) {
									if (!reco_tunes.contains(details.getToneId())) {
										reco_new_release_tunes.add(details);
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
				} else if (20 <= remaining_catids.size()) {
					int reco_count = 1;
					reco_new_release_tunes = new TreeSet<RptToneDetails>();
					reco_tunes = new TreeSet<String>();
					int count = 0;
					int total = 0;
					for (String catid : remaining_catids) {
						for (RptToneDetails details : new_release_tunes) {
							if (Integer.parseInt(catid) == Integer.parseInt(details.getCategoryId())) {
								if (count < reco_count && total < 20) {
									if (!reco_tunes.contains(details.getToneId())) {
										reco_new_release_tunes.add(details);
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
			if (null != reco_new_release_tunes && 20 > reco_new_release_tunes.size()) {
				int required_count = (20 - reco_new_release_tunes.size());
				List<RptToneDetails> most_download_Counts = crbtDataSourceDaoImpl.getBestDownloadRptToneDetails();
				for (RptToneDetails details : most_download_Counts) {
					if (reco_tunes.contains(details.getToneId())) {

					} else {
						if (required_count != 0 && !purchased_tunes.contains(details.getToneId())) {
							reco_new_release_tunes.add(details);
							reco_tunes.add(details.getToneId());
							required_count--;
						} else {
							break;
						}
					}
				}
			} else if (null == reco_new_release_tunes) {
				reco_new_release_tunes = new TreeSet<RptToneDetails>();
				reco_tunes = new TreeSet<String>();
				int required_count = 20;
				List<RptToneDetails> most_download_Counts = crbtDataSourceDaoImpl.getBestDownloadRptToneDetails();
				for (RptToneDetails details : most_download_Counts) {
					if (reco_tunes.contains(details.getToneId())) {

					} else {
						if (required_count != 0 && !purchased_tunes.contains(details.getToneId())) {
							reco_new_release_tunes.add(details);
							reco_tunes.add(details.getToneId());
							required_count--;
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
			LoadingStatus loadingStatus = crbtDataSourceDaoImpl.getLoadingStatus(CrbtConstants.CAT_NEW_RELEASES,
					Long.parseLong(DataParser.getDateAsStringAsYyyyMMdd()));
			for (RptToneDetails details : reco_new_release_tunes) {
				recommendedTunesLoadCK = new RecommendedTunesLoadCK();
				recommendedTunesLoadCK.setPartitionId(String.valueOf(loadingStatus.getId()));
				recommendedTunesLoad = new RecommendedTunesLoad();
				recommendedTunesLoad.setRecoTunesLoadCk(recommendedTunesLoadCK);
				recommendedTunesLoad.setLookupId(loadingStatus.getId().longValue());
				recommendedTunesLoad.setMsisdn(mdn);
				recommendedTunesLoad.setPredictionDate(new Date());
				recommendedTunesLoad.setToneId(details.getToneId());
				recommendedTunesLoad.setCategoryId(details.getCategoryId());
				recommendedTunesLoad.setTypeId(CrbtConstants.CAT_NEW_RELEASES);
				recommendedTunesLoad.setCreatedOn(new Date());
				recommendedTunesLoad.setCreatedBy("");
				recommendedTunesLoad.setUpdatedBy("");
				recommendedTunesLoad.setUpdatedOn(new Date());

				crbtDataSourceDaoImpl.saveRecoTunesLoad(recommendedTunesLoad);
			}
		} catch (Exception e) {
			logger.error("Exception has been occured in method recommendTunes in NewReleases class: ", e);
		} finally {
			reco_tunes = null;
			reco_new_release_tunes = null;
			new_release_tunes = null;
			new_release_cat_ids = null;
		}
	}

}
