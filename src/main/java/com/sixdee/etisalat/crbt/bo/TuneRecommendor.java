/**
 * 
 */
package com.sixdee.etisalat.crbt.bo;

import java.util.List;

import com.sixdee.etisalat.crbt.service.CrbtDataSourceDaoImpl;

/**
 * @author ananda.rao
 * @Date 27-07-2019
 */
public interface TuneRecommendor {
	
	void recommendTunes(String msisdn,List<String> preferred_catids,List<String> purchased_tunes, CrbtDataSourceDaoImpl crbtDataSourceDaoImpl) ;

}
