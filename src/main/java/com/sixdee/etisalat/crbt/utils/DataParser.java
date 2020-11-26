/**
 * 
 */
package com.sixdee.etisalat.crbt.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @author ananda.rao
 * @Date 27-07-2019
 */
public class DataParser {

	/***
	 * 
	 * @param separator
	 * @param data
	 * @return
	 */
	public static List<String> parseString(String separator, String data) {
		List<String> parsedString = new ArrayList<String>();
		data = data.replaceAll("\\s+","");
		if (null != data && !data.isEmpty()) {
			parsedString = Arrays.asList(data.split(separator));
		}
		return parsedString;

	}

	/***
	 * 
	 * @return
	 */
	public static String getPreviousDateAsString() {
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime now = new DateTime();
		DateTime oneDayAgo = now.minusDays(1);
		return format.print(oneDayAgo);
	}
	
	/***
	 * 
	 * @return
	 */
	public static String getDateAsString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(new Date());
		return date;
	}
	
	public static String getDateAsStringAsYyyyMMdd() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String date = dateFormat.format(new Date());
		return date;
	}
	
	
}
