/**
 * 
 */
package com.sixdee.etisalat.crbt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ananda.rao
 * @Date 27-07-2019
 *
 */
@Configuration
@EnableTransactionManagement
@ImportResource({"file:///apps/crbtuser/RECO/crbt-config.xml"})
//@ImportResource("classpath:crbt-config.xml")
public class CrbtConfig {
	
}
