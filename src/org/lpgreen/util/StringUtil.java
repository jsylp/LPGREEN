package org.lpgreen.util;

import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;

public class StringUtil {
	// compare whether two strings are the same
	public static boolean equals(String s1, String s2) {
		String s1tmp = s1;
		if (s1tmp == null)
			s1tmp = "";
		String s2tmp = s2;
		if (s2tmp == null)
			s2tmp = "";
		return s1tmp.equals(s2tmp);
		/*
		if (s1 == null && s2 == null)
			return true;
		else if (s1 == null && s2 != null)
			return false;
		else if (s1 != null && s2 == null)
			return false;
		else
			return s1.equals(s2);
		*/			
	}
	
	public static boolean equals(DateTime dt1, DateTime dt2) {
		if (dt1 == null && dt2 == null)
			return true;
		else if (dt1 == null && dt2 != null)
			return false;
		else if (dt1 != null && dt2 == null)
			return false;
		else
			return dt1.equals(dt2);			
	}
	
	public static boolean equals(Duration d1, Duration d2) {
		if (d1 == null && d2 == null)
			return true;
		else if (d1 == null && d2 != null)
			return false;
		else if (d1 != null && d2 == null)
			return false;
		else
			return d1.equals(d2);			
	}
	
	public static boolean equals(UUID uuid1, UUID uuid2) {
		if (uuid1 == null && uuid2 == null)
			return true;
		else if (uuid1 == null && uuid2 != null)
			return false;
		else if (uuid1 != null && uuid2 == null)
			return false;
		else
			return uuid1.equals(uuid2);		
	}
	
	public static String getLineEnding() {
		return System.getProperty("line.separator");
	}
	
	// Get date time string from DateTime object
	public static String getDateTimeString(DateTime dateTime) {
		return DateTimeFormat.forPattern("EE MMM dd, YYYY KK:mma").
					withZone(DateTimeZone.forID("America/Los_Angeles")).
					print(dateTime);
	}
	
	// Get DateTime object from a string
	public static DateTime parseDateTimeFromString(String strDateTime) {
		if (strDateTime == null || strDateTime.isEmpty())
			return null;
		try {
			return DateTimeFormat.forPattern("EE MMM dd, YYYY KK:mma").
						withZone(DateTimeZone.forID("America/Los_Angeles")).
						parseDateTime(strDateTime);
		}
		catch (Exception e) {
			return null;
		}
	}
	
	// Date: YYYY-MM-dd
	// Time: HH:mm:ss (example: 14:20:00 for 2:20pm)
	public static DateTime parseUTCDateTimeFromString_HHmmss(String strDateTime) {
		if (strDateTime == null || strDateTime.isEmpty())
			return null;
		try {
			return DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss").
						withZone(DateTimeZone.UTC).
						parseDateTime(strDateTime);
		}
		catch (Exception e) {
			return null;
		}
	}

	// Get a Date Only date time string from DateTime object
	public static String getDateOnlyDateTimeString(DateTime dateTime) {
		return DateTimeFormat.forPattern("YYYY-MM-dd").
					withZone(DateTimeZone.forID("America/Los_Angeles")).
					print(dateTime);
	}
	
	// Get a Date Only DateTime object from a string
	public static DateTime parseDateOnlyDateTimeFromString(String strDateTime) {
		if (strDateTime == null || strDateTime.isEmpty())
			return null;
		try {
			return DateTimeFormat.forPattern("YYYY-MM-dd").
					withZone(DateTimeZone.forID("America/Los_Angeles")).
					parseDateTime(strDateTime);
		}
		catch (Exception e) {
			return null;
		}
	}
	
	// Pattern 1: Get date time string from DateTime object
	public static String getDateTimeString_hhmma(DateTime dateTime) {
		return DateTimeFormat.forPattern("YYYY-MM-dd hh:mma").
					withZone(DateTimeZone.forID("America/Los_Angeles")).
					print(dateTime);
	}
	
	// Pattern 1A: Get date time string from DateTime object with Day of the week
	public static String getDateTimeString_hhmma_week(DateTime dateTime) {
		return DateTimeFormat.forPattern("EEE YYYY-MM-dd hh:mma").
					withZone(DateTimeZone.forID("America/Los_Angeles")).
					print(dateTime);
	}
	
	// Pattern 1: Get DateTime object from a string
	public static DateTime parseDateTimeFromString_hhmma(String strDateTime) {
		if (strDateTime == null || strDateTime.isEmpty())
			return null;
		try {
			return DateTimeFormat.forPattern("YYYY-MM-dd hh:mma").
					withZone(DateTimeZone.forID("America/Los_Angeles")).
					parseDateTime(strDateTime);
		}
		catch (Exception e) {
			return null;
		}
	}
	
	// Pattern 1: Get a Time Only string from DateTime object in the format: "T17:45:00"
	public static String getTimeOnlyDateTimeString_hhmma(DateTime dateTime) {
		String DateTimeString = DateTimeFormat.forPattern("YYYY-MM-dd hh:mma").
					withZone(DateTimeZone.forID("America/Los_Angeles")).
					print(dateTime);
		return DateTimeString.substring(DateTimeString.indexOf(" ")+1);
	}
	
	// Pattern 2: Get date time string from DateTime object
	public static String getDateTimeString_HHmmss(DateTime dateTime) {
		return DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss").
					withZone(DateTimeZone.forID("America/Los_Angeles")).
					print(dateTime);
	}
	
	// Pattern 2: Parse DateTime object from a string
	public static DateTime parseDateTimeFromString_HHmmss(String strDateTime) {
		if (strDateTime == null || strDateTime.isEmpty())
			return null;
		try {
			return DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss").
					withZone(DateTimeZone.forID("America/Los_Angeles")).
					parseDateTime(strDateTime);
		}
		catch (Exception e) {
			return null;
		}
	}

	// Pattern 2A: Get date time string up to second from DateTime object - without timezone
	public static String getDateTimeString_HHmmss_nozone(DateTime dateTime) {
		return DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss").print(dateTime);
	}

	// Pattern 2B: Get date time string up to fraction of second from DateTime object - without timezone
	public static String getDateTimeString_HHmmssSSS_nozone(DateTime dateTime) {
		return DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss.SSS").print(dateTime);
	}
	
	// Pattern 2C: Parse DateTime object from a string
	public static DateTime parseDateTimeFromString_HHmmss_nozone(String strDateTime) {
		if (strDateTime == null || strDateTime.isEmpty())
			return null;
		try {
			return DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss").
					parseDateTime(strDateTime);
		}
		catch (Exception e) {
			return null;
		}
	}

	
	// Pattern 2: Get a Time Only string from DateTime object in the format: "T17:45:00"
	public static String getTimeOnlyDateTimeString_HHmmss(DateTime dateTime) {
		String DateTimeString = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss").
					withZone(DateTimeZone.forID("America/Los_Angeles")).
					print(dateTime);
		return DateTimeString.substring(DateTimeString.indexOf(" ")+1);
	}
	
	public static DateTime getDateFromYearMonthDay(int year, int month, int day) {
		if (year < 0 || month < 1 || month > 12 || day < 1 || day > 31)
			return null;
		
		String strDateTime = String.format("%04d-%02d-%02d", year, month, day);
		try {
			DateTime theDate = DateTimeFormat.forPattern("YYYY-MM-dd").
					withZone(DateTimeZone.forID("America/Los_Angeles")).
					parseDateTime(strDateTime);
			return theDate;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	// Get current date String
	public static String getCurrentDateString() {
		DateTime currentDateTime = DateTime.now();
		return getDateOnlyDateTimeString(currentDateTime);
	}

	// Get current time round down to nearest half hour: KK:mm:ss
	public static String getCurrentTimeStringRoundDownToHalfHour() {
		DateTime currentDateTime = DateTime.now();
		String strCurrTime = StringUtil.getTimeOnlyDateTimeString_HHmmss(currentDateTime);
		// Round down to nearest half hour
		String strCurrTimeHour = strCurrTime.substring(0, 2);
		String strCurrTimeMin = strCurrTime.substring(strCurrTime.indexOf(':')+1, strCurrTime.indexOf(':')+3);
		int min = 0;
		try {
			min = Integer.parseInt(strCurrTimeMin);
		}
		catch (Exception e) {}
		String strTime = null;
		if (min < 30)
			strTime = strCurrTimeHour + ":00:00";
		else
			strTime = strCurrTimeHour + ":30:00";
		return strTime;
	}
	
	// Get current time in the format: KK:mm:ss
	public static String getCurrentTimeString_HHmmss() {
		DateTime currentDateTime = DateTime.now();
		String strCurrTime = StringUtil.getTimeOnlyDateTimeString_HHmmss(currentDateTime);
		return strCurrTime;
	}	
	
	// Convert Date format from M/d/YYYY to YYYY-MM-dd
	public static String convertDateOnlyStringFromMdYYYY(String strDate) {
		// check if it is already in the right format YYYY-MM-dd
		if (strDate == null || strDate.isEmpty())
			return strDate;
		else if (strDate.matches("\\d{4}-\\d{2}-\\d{2}"))	// already in YYYY-MM-dd format
			return strDate;
		else if (strDate.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {	// in M/d/YYYY format
			String[] parts = strDate.split("/");
			StringBuffer sbDate = new StringBuffer();
			sbDate.append(parts[2]).append("-");
			if (parts[0].length() == 1)
				sbDate.append("0");
			sbDate.append(parts[0]).append("-");
			if (parts[1].length() == 1)
				sbDate.append("0");
			sbDate.append(parts[1]);
			return sbDate.toString();
		}
		else
			return null;
	}

	// Validate and standardize the input time string in the format of H:m:s into HH:mm:ss
	// If the input string is not in the format of H:m:s, it will return null
	public static String standardizeTimeString(String strTime) {
		
		String[] parts = strTime.split(":");
		if (parts.length != 3)
			return null;
		
		// validate and standardize HH
		int hour = 0;
		int min = 0;
		int sec = 0;
		try {
			hour = Integer.parseInt(parts[0]);
			if (hour < 0 || hour > 23)
				return null;
			min = Integer.parseInt(parts[1]);
			if (min < 0 || min > 59)
				return null;
			sec = Integer.parseInt(parts[2]);
			if (sec < 0 || sec > 59)
				return null;
		}
		catch (Exception e) {
			return null;
		}
		
		return(String.format("%02d:%02d:%02d", hour, min, sec));
	}
	
	// encode some special characters which break JSON data processing
	public static String encodeJSONString(String input) {
		if (input == null || input.isEmpty())
			return input;
		String pattern_newline_r = "\\r";
		String pattern_newline_n = "\\n";
		String pattern_quote1 = "'";
		String pattern_quote2 = "\"";
		String output = input.replaceAll(pattern_newline_r,  "\\\\r");
		output = output.replaceAll(pattern_newline_n,  "\\\\n");
		output = output.replaceAll(pattern_quote1,  "\\\\'");
		output = output.replaceAll(pattern_quote2,  "\\\\\"");
		return output;
	}
	
	// decode an encoded UTF-8 string into actual character string.
	// Example: &#36873;&#25321;&#19968;&#20010; for Chinese Select One
	public static String decode_utf8_old(String codes) {
		try {
			String tempStr = codes.replaceAll("&#", "");
			String[] arrayStrs = tempStr.split(";");
			char[] arrayChars = new char[arrayStrs.length];
			for (int i = 0; i < arrayStrs.length; i++) {
				String oneStr = arrayStrs[i];
				arrayChars[i] = (char)Integer.valueOf(oneStr).intValue();
			}
			String retString = new String(arrayChars);
			return retString;
		}
		catch (Exception e) {
			return codes;	// not able to decode it
		}
	}

	// decode an encoded UTF-8 string into actual character string. The string may have both ASCII and unicode.
	// Example: Select&#36873;&#25321; One&#19968;&#20010; for English and Chinese Select One.
	public static String decode_utf8(String codes) {
		StringBuffer sbDecodedStr = new StringBuffer();
		String remainingStr = codes;
		while (remainingStr.length() > 0) {
			// look for next &#
			int indexToken = remainingStr.indexOf("&#");
			if (indexToken == -1) {
				sbDecodedStr.append(remainingStr);
				break; // we are done
			}
			else {
				// look for next ;
				int indexTokenEnd = remainingStr.indexOf(";");
				if (indexTokenEnd == -1 || indexTokenEnd < indexToken) {
					sbDecodedStr.append(remainingStr);
					break; // we are done
				}
				else {
					// we found a &#ddddd; character. We need to decode it
					if (indexToken > 0) {
						sbDecodedStr.append(remainingStr.substring(0, indexToken));
					}
					String oneCode = remainingStr.substring(indexToken+2, indexTokenEnd);
					char c = (char)Integer.valueOf(oneCode).intValue();
					sbDecodedStr.append(c);
					remainingStr = remainingStr.substring(indexTokenEnd+1);
				}
			}
		}
		return sbDecodedStr.toString();
	}
	
	// Format a numeric number into 0000-000-001 format
	public static String getFormatedAccountNum(long accountNumValue) {
		StringBuffer sbNum = new StringBuffer(String.format("%010d", accountNumValue));
		sbNum.insert(4, '-');
		sbNum.insert(8, '-');
		return sbNum.toString();
	}
	
	// Get numeric value from a formated AccountNum
	public static long getAccountNumIntVal(String accountNumStr) throws Exception{
		String accountNumValStr = accountNumStr.replaceAll("-", "");
		try {
			long val = Long.valueOf(accountNumValStr).longValue();
			return val;
		}
		catch (Exception e) {
			throw e;
		}
	}
}
