package com.petting.util;

import java.util.ArrayList;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtil {
	
	 public static boolean checkUnvalidatedValues(ArrayList<Object> list) {

	        for(int i=0; i<list.size(); i++) {
	            if(list.get(i).getClass().getName().equals("java.lang.String")) {
	                String tempStr = list.get(i).toString();
	                if (tempStr == null) {
//	                    log.info("Unvalidated Value Index (null) : " + i);
	                    return false;
	                }

	                if (tempStr.equals("")) {
//	                    log.info("Unvalidated Value Index (\"\") : " + i);
	                    return false;
	                }

	                if (tempStr.equals("null")) {
//	                    log.info("Unvalidated Value Index (\"null\") : " + i);
	                    return false;
	                }

	                if (tempStr.contains("<") || tempStr.contains(">") || tempStr.contains("'") || tempStr.contains("script")) {
//	                    log.info("Unvalidated Value Index (<>'script) : " + i);
	                    return false;
	                }
	            }

	            if (list.get(i).getClass().getName().equals("java.lang.Integer")) {
	                if (Integer.parseInt(list.get(i).toString()) == -999) {
//	                    log.info("Unvalidated Value Index (-999) : " + i);
	                    return false;
	                }
	            }
	        }

	        return true;
	    }

	public static Date generateExpiredDate(Long time) {
		Date date = new Date();
		date.setTime(date.getTime() + time); 
		return date;
	}
}
