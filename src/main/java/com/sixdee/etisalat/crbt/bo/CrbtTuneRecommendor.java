/**
 * 
 */
package com.sixdee.etisalat.crbt.bo;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.sixdee.etisalat.crbt.constants.CrbtConstants;
import com.sixdee.etisalat.crbt.service.CrbtDataSourceDaoImpl;
import com.sixdee.etisalat.crbt.utils.CrbtContextInitializer;
import com.sixdee.etisalat.crbt.utils.DataParser;

/**
 * @author ananda.rao
 * @Date 27-07-2019
 *
 */
public class CrbtTuneRecommendor {

	public static Logger logger = LoggerFactory.getLogger(CrbtTuneRecommendor.class);

	public void recommend(String subscriberfile) {
		Reader reader = null;
		CSVParser csvParser = null;
		String msisdn = null;
		String preferred_Parent_Categories = null;
		String purchased_Tune_Ids = null;
		String liked_Tune_Ids = null;
		List<String> preferred_Parent_Categories_List = null;
		List<String> purchased_TuneId_List = null;
		List<String> purchased_Tunes = null;
		List<String> liked_TuneId_List = null;
		List<String> liked_Cat_Ids = null;
		Map<String, String> liked_TuneId_Map = null;
		ApplicationContext context = null;
		CrbtDataSourceDaoImpl crbtDataSourceDaoImpl = null;
		//List<RptToneDetails> tunesWithDownloadCount = null;

		try {
			context = CrbtContextInitializer.getApplicationContext();
			crbtDataSourceDaoImpl = context.getBean(CrbtDataSourceDaoImpl.class);
			reader = Files.newBufferedReader(Paths.get(subscriberfile));
			csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
			boolean header = true;
			for (CSVRecord csvRecord : csvParser) {
				if (header) {
					header = false;
					continue;
				} else {
					msisdn = csvRecord.get(0);
					logger.info("The recommendation processing is started for MSISDN: "+msisdn);
					preferred_Parent_Categories = csvRecord.get(1);
					purchased_Tune_Ids = csvRecord.get(2);
					liked_Tune_Ids = csvRecord.get(3);

					// Get Preferred Categories
					preferred_Parent_Categories_List = DataParser.parseString(CrbtConstants.SEPARATOR_COLON,
							preferred_Parent_Categories);

					// Get Purchased Tune IDs
					purchased_TuneId_List = DataParser.parseString(CrbtConstants.SEPARATOR_COLON, purchased_Tune_Ids);
					purchased_Tunes = new ArrayList<String>();
					if (0 < purchased_TuneId_List.size()) {
						for (String tuneId : purchased_TuneId_List) {
							String[] tune = tuneId.split(CrbtConstants.SEPARATOR_PIPE);
							purchased_Tunes.add(tune[1]);
							logger.info("The subscriber with MSISDN " + msisdn
									+ " has purchased the tune with tune id: " + tune[1]);
						}
					}

					// Get Liked Tune IDs
					liked_TuneId_List = DataParser.parseString(CrbtConstants.SEPARATOR_COLON, liked_Tune_Ids);
					liked_Cat_Ids = new ArrayList<String>();
					for (String tuneId : liked_TuneId_List) {
						String[] tune = tuneId.split(CrbtConstants.SEPARATOR_PIPE);
						liked_Cat_Ids.add(tune[0]); // Cat IDs of liked Tones
						logger.info("The subscriber with MSISDN " + msisdn + " has liked tune id:" + tune[1]
								+ " under category id: " + tune[0]);
					}

					liked_TuneId_Map = new HashMap<String, String>();
					for (String tuneId : liked_TuneId_List) {
						String[] tune = tuneId.split(CrbtConstants.SEPARATOR_PIPE);
						liked_TuneId_Map.put(tune[0], tune[1]);
					}

					// Fetch 20 Tunes with download_count in desc order
					//tunesWithDownloadCount = crbtDataSourceDaoImpl.getBestDownloadRptToneDetails();

					/****
					 * #################### New Releases ###########################
					 */
					logger.info("The New Releases Tunes recommendation processing is started for MSISDN: "+msisdn);
					new NewReleasesRecommendor().recommendTunes(msisdn, preferred_Parent_Categories_List,
							purchased_Tunes, crbtDataSourceDaoImpl);
					logger.info("The New Releases Tunes recommendation processing is ended for MSISDN: "+msisdn);

					/****
					 * #################### Trending ###########################
					 */
					logger.info("The Trending Tunes recommendation processing is started for MSISDN: "+msisdn);
					new TrendingRecommendor().recommendTunes(msisdn, preferred_Parent_Categories_List, purchased_Tunes,
							crbtDataSourceDaoImpl);
					logger.info("The Trending Tunes recommendation processing is ended for MSISDN: "+msisdn);
					/****
					 * #################### JUST FOR YOU ###########################
					 */
					logger.info("The Just For You Tunes recommendation processing is started for MSISDN: "+msisdn);
					new JustForYouRecommendor().recommendTunes(msisdn, liked_Cat_Ids, purchased_Tunes,
							crbtDataSourceDaoImpl);
					logger.info("The Just For You Tunes recommendation processing is ended for MSISDN: "+msisdn);

				}
			}
		} catch (IOException e) {
			logger.error("The exception has occured in method recommendCrbt in CrbtTuneRecommendor class: ", e);
		} catch (Exception e) {
			logger.error("The exception has occured in method recommendCrbt in CrbtTuneRecommendor class: ", e);
		} finally {
			preferred_Parent_Categories_List = null;
			purchased_TuneId_List = null;
			purchased_Tunes = null;
			liked_TuneId_List = null;
			liked_Cat_Ids = null;
			liked_TuneId_Map = null;
			liked_Tune_Ids = null;
			purchased_Tune_Ids = null;
			preferred_Parent_Categories = null;
			msisdn = null;
			csvParser = null;
			reader = null;
		}
	}

}
