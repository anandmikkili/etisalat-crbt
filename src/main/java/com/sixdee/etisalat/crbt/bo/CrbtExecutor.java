/**
 * 
 */
package com.sixdee.etisalat.crbt.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.sixdee.etisalat.crbt.utils.CrbtContextInitializer;
import com.sixdee.etisalat.crbt.utils.DataParser;

/**
 * @author ananda.rao
 * @Date 27-07-2019
 */
public class CrbtExecutor {
	public static Logger logger = LoggerFactory.getLogger(CrbtExecutor.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = null;
		try {
			context = CrbtContextInitializer.getApplicationContext();
			CrbtTuneRecommendor crbtTuneRecommendor = new CrbtTuneRecommendor();
			crbtTuneRecommendor.recommend("/apps/crbtuser/CRBT_FILES/DynamicRecommendation/src/MSISDN_Details_"+DataParser.getPreviousDateAsString()+".csv");
			//crbtTuneRecommendor.recommend("C:\\Users\\ananda.rao\\Desktop\\Test.csv");
			logger.info(
					"The CRBT Tune recommendation has been completed for today at " + DataParser.getDateAsString());

		} catch (Exception e) {
			logger.error("Exception has occured in main method of CrbtExecutor class :", e);
		} finally {

		}

	}

}
