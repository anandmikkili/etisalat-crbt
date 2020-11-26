/**
 * 
 */
package com.sixdee.etisalat.crbt.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sixdee.etisalat.crbt.config.CrbtConfig;

/**
 * @author ananda.rao
 * @Date 27-07-2019
 *
 */

public class CrbtContextInitializer {
	private static volatile ApplicationContext crbtApplicationContext;

	public static ApplicationContext getApplicationContext() {
		if (crbtApplicationContext == null) {
			synchronized (CrbtContextInitializer.class) {
				if (crbtApplicationContext == null) {
					crbtApplicationContext = new AnnotationConfigApplicationContext(new Class[] { CrbtConfig.class });
					
				}
			}
		}
		return crbtApplicationContext;
	}
}
