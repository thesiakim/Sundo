package common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import business.biz.Constants;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

@SuppressWarnings("all")
public class CommUtils {

	// 특정일(yyyyMMdd) 에서 주어진 일자만큼 더한 날짜를 계산한다.
	public static String addDate(String date, String gubn, int rday) {
		if (date == null)
			return null;

		if (date.length() < 8)
			return ""; // 최소 8 자리

		String time = "";

		TimeZone kst = TimeZone.getTimeZone("JST");
		TimeZone.setDefault(kst);

		Calendar cal = Calendar.getInstance(kst); // 현재

		int yyyy = Integer.parseInt(date.substring(0, 4));
		int mm = Integer.parseInt(date.substring(4, 6));
		int dd = Integer.parseInt(date.substring(6, 8));

		cal.set(yyyy, mm - 1, dd); // 카렌더를 주어진 date 로 세팅하고
		cal.add(Calendar.DATE, rday); // 그 날자에서 주어진 rday 만큼 더한다.

		String strYear = Integer.toString(cal.get(Calendar.YEAR)); // 년도
		String strMonth = Integer.toString(cal.get(Calendar.MONTH) + 1); // 월
		String strDay = Integer.toString(cal.get(Calendar.DAY_OF_MONTH)); // 일

		if (strMonth.length() < 2)
			strMonth = "0" + strMonth;
		if (strDay.length() < 2)
			strDay = "0" + strDay;

		time = strYear + gubn + strMonth + gubn + strDay;

		return time;
	}

	// adds days to calendar by n.
	public static Calendar addDays(Calendar cal, int n) {
		Calendar res = Calendar.getInstance();
		res.setTime(cal.getTime());
		res.add(Calendar.DATE, n);
		return res;
	}

	public static String addDays(String strDate, int n) {
		String res = "";
		Calendar cal = strToCalendar(strDate);
		if (cal != null) {
			res = calendarToStr(addDays(cal, n));
		}
		return res;
	}

	// adds months to calendar by n.
	public static Calendar addMonths(Calendar cal, int n) {
		Calendar res = Calendar.getInstance();
		res.setTime(cal.getTime());
		res.add(Calendar.MONTH, n);
		return res;
	}

	public static String addMonths(String strDate, int n) {
		String res = "";
		Calendar cal = strToCalendar(strDate);
		if (cal != null) {
			res = calendarToStr(addMonths(cal, n));
		}
		return res;
	}

	// convert Calendar to string with default pattern; yyyyMMdd is default
	// date-format !!!
	public static String calendarToStr(Calendar cal) {
		return dateFormat("yyyyMMdd", cal);
	}

	public static boolean compare(String dt1, String dt2, String oper) {
		if ("<".equals(oper)) {
			if (isLessThan(dt1, dt2))
				return true; // dt1 < dt2
		} else if (">".equals(oper)) {
			if (isLessThan(dt2, dt1))
				return true; // dt1 > dt2
		} else if ("<=".equals(oper)) {
			if (isLessEqual(dt1, dt2))
				return true; // dt1 <= dt2
		} else if (">=".equals(oper)) {
			if (isLessEqual(dt2, dt1))
				return true; // dt1 >= dt2
		}
		return false;
	}

	public static String concat(String str, String pos) {
		if (str != null && pos != null) {
			if (pos.length() > str.length()) {
				return str;
			}
			int cut = str.length() - pos.length();
			String pre = str.substring(0, cut);
			return pre + pos;
		}
		return null;
	}

	// [COMMON] insert sepeartion string(or char) into the string.
	// sepString("0123456789",'-',4,5) => "0123-456789".
	public static String concat(String str, String sep, int[] ind) {
		String res = "";
		int s1 = 0, s2 = 0;
		try {
			for (int i = 0; i < ind.length; i++) {
				s2 += ind[i];
				res += str.substring(s1, s2);
				if (s2 < str.length())
					res += sep;
				s1 = s2;
			}
		} catch (Exception e) {
			res = "";
		}
		if (s1 < str.length())
			res += str.substring(s1);
		return res;
	}

	public static String concatComa(String[] arr) {
		String str = "";
		if (arr != null) {
			int n = 0;
			for (int i = 0; i < arr.length; i++) {
				if (!empty(arr[i])) {
					if (n > 0)
						str += ",";
					str += "'" + arr[i] + "'";
					n++;
				}
			}
		}
		return str;
	}

	public static String convertCharset(String str, String enc1, String enc2) {
		try {
			return new String(str.getBytes(enc1), enc2);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	// YYYY-MM-DD ==> YYYYMMDD format으로
	public static String convertDate(String date) {
		return formatDate("yyyyMMdd", date);
	}

	public static double currStrToDouble(String str) {
		return currStrToDouble(str, 0);
	}

	// converts a string value with ',' char to double value. //
	public static double currStrToDouble(String str, double defaultValue) {
		String s = "";
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if ((c >= '0' && c <= '9') || (c == '.') || (c == '-')
					|| (c == '+')) {
				s += c;
			}
		}
		return strToDouble(s, defaultValue);
	}

	public static int currStrToInt(String str) {
		return currStrToInt(str, 0);
	}

	// converts a string value with ',' char to integer value. //
	public static int currStrToInt(String str, int defaultValue) {
		String s = "";
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if ((c >= '0' && c <= '9') || (c == '.') || (c == '-')
					|| (c == '+')) {
				s += c;
			}
		}
		return strToInt(s, defaultValue);
	}

	public static long currStrToLong(String str) {
		return currStrToLong(str, 0);
	}

	// converts a string value with ',' char to long-integer value. //
	public static long currStrToLong(String str, long defaultValue) {
		String s = "";
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if ((c >= '0' && c <= '9') || (c == '.') || (c == '-')
					|| (c == '+')) {
				s += c;
			}
		}
		return strToLong(s, defaultValue);
	}

	public static String cut(String str, int start, int len) {
		if (isGreaterThan(str, start + len)) {
			return str.substring(start, start + len);
		} else if (isGreaterThan(str, start)) {
			return str.substring(start, str.length());
		}
		return "";
	}

	// Byte 단위로 문자를 자른다.
	public static String cutByte(String s, int begin, int end) {
		int rlen = 0;
		int sbegin = 0;
		int send = 0;

		for (sbegin = 0; sbegin < s.length(); sbegin++) {
			if (s.charAt(sbegin) > 0x007f) {
				rlen += 2;
				if (rlen > begin) {
					rlen -= 2;
					break;
				}
			} else {
				rlen++;
				if (rlen > begin) {
					rlen--;
					break;
				}
			}
		}

		for (send = sbegin; send < s.length(); send++) {
			if (s.charAt(send) > 0x007f) {
				rlen += 2;
			} else {
				rlen++;
			}
			if (rlen > end)
				break;
		}
		return s.substring(sbegin, send);
	}

	public static double cutDouble(String str, int start, int len) {
		str = cut(str, start, len).trim();
		return toDouble(str);
	}

	public static int cutInt(String str, int start, int len) {
		str = cut(str, start, len).trim();
		return toInt(str);
	}

	public static long cutLong(String str, int start, int len) {
		str = cut(str, start, len).trim();
		return toLong(str);
	}

	public static String cutLower(String str, int start, int len) {
		return cut(str, start, len).toLowerCase();
	}

	public static String cutRight(String str, int cnt) {
		if (str != null) {
			int len = str.length();
			if (cnt > len) {
				return str;
			}
			return str.substring(len - cnt, len);
		}
		return null;
	}

	// Title Cut
	public static String cutTitle(String title, int len) {
		if (title != null && title.length() > len) {
			return title.substring(0, len) + "...";
		}
		return title;
	}

	public static String cutTrim(String str, int start, int len) {
		return cut(str, start, len).trim();
	}

	public static String cutUpper(String str, int start, int len) {
		return cut(str, start, len).toUpperCase();
	}

	// apply pattern to a Calendar
	public static String dateFormat(String pattern, Calendar cal) {
		String res;
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat(pattern, Locale.KOREA);
		res = formatter.format(cal.getTime());
		return res;
	}

	// apply pattern to a Date
	// y : year, M : month in year, d : day in month,
	// h : hour in am/pm (1~12), H : hour in day (0~23), m : minute in hour, s :
	// second in minute
	public static String dateFormat(String pattern, Date date) {
		String res;
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat(pattern, Locale.KOREA);
		res = formatter.format(date);
		return res;
	}

	// apply pattern to a String-date
	public static String dateFormat(String pattern, String strDate) {
		String res = "";
		Calendar cal = strToCalendar(strDate);
		if (cal != null) {
			res = dateFormat(pattern, cal);
		}
		return res;
	}

	// day difference between cal1 and cal2.
	public static int dayDiff(Calendar cal1, Calendar cal2) {
		long diff = cal2.getTime().getTime() - cal1.getTime().getTime();
		return (int) (diff / 24 / 60 / 60 / 1000);
	}

	public static int dayDiff(String strDate1, String strDate2) {
		int diff = dayDiff(strToCalendar(strDate2), strToCalendar(strDate1));
		return diff;
	}

	// days of month (daysOfMonth("2000-02-01") => 29)
	public static int daysOfMonth(Calendar cal) {
		Calendar nextMonth = addMonths(cal, 1);
		int yy = getYear(nextMonth);
		int mm = getMonth(nextMonth);
		return getDay(addDays(getCalendar(yy, mm, 1), -1));
	}

	public static int daysOfMonth(String strDate) {
		return daysOfMonth(strToCalendar(strDate));
	}

	// remove non-numeric char from a string.
	public static String delNonNum(String str) {
		String s = "";
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= '0' && c <= '9') {
				s += c;
			}
		}
		return s;
	}
	// remove non-numeric char from a string.
	public static String delNonDouble(String str) {
		String s = "";
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= '0' && c <= '9' || c == '.') {
				s += c;
			}
		}
		return s;
	}


	public static boolean empty(String str) {
		if (str == null || str.length() == 0)
			return true;
		return false;
	}

	public static String encode(String str, String from, String to) {
		String value = "";
		try {
			value = new String(str.getBytes(from), to);
		} catch (Exception e) {
			value = "";
		}
		return value;
	}

	public static boolean equal(String str1, String str2) {
		if (str1 == null && str2 == null)
			return true;
		if (str1 == null || str2 == null)
			return false;
		if (str1.equals(str2))
			return true;
		return false;
	}

	/**
	 * 입력받은 배열에 입력받은 문자열이 있는지 체크하여 있으면 TRUE를 반환한다.
	 *
	 * @param str String[]
	 * @param s String
	 * @return boolean
	 */
	public static boolean exist(String[] str, String s) {
		if (s != null && str != null && str.length > 0) {
			for (int i = 0; i < str.length; i++) {
				if (s.equals(str[i])) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 입력받은 배열에 입력받은 문자열이 있는지 체크하여 있으면 INDEX를 반환한다.
	 *
	 * @param str String[]
	 * @param s String
	 * @return boolean
	 */
	public static int existIndex(String[] str, String s) {
		if (s != null && str != null && str.length > 0) {
			for (int i = 0; i < str.length; i++) {
				if (s.equals(str[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	public static boolean existList(List list, String str) {
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				String org = (String) list.get(i);
				if (equal(org, str)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * [2016. 06. 28] 통화형식의 문자열로 변환하여 반환한다.
	 *
	 * @paramstrn String 타입의 변환할 숫자
	 * @return 통화형식으로 변환된 문자열 ( ex. 21000 → 21,000 )
	 */
	public static String formatCrrency(String str) {
		if (str == null || str.length() == 0)
			return "0";
		long n = 0;
		try {
			n = Long.parseLong(str);
		} catch (NumberFormatException e) {
			n = 0;
		}
		NumberFormat nf = NumberFormat.getNumberInstance();
		return nf.format(n);
	}

	public static String formatDate(String pattern, Calendar cal) {
		SimpleDateFormat f = new SimpleDateFormat(pattern, Locale.KOREA);
		return f.format(cal.getTime());
	}

	// apply pattern to a Date
	// y : year, M : month in year, d : day in month,
	// h : hour in am/pm (1~12), H : hour in day (0~23), m : minute in hour, s :
	// second in minute
	public static String formatDate(String pattern, Date d) {
		SimpleDateFormat f = new SimpleDateFormat(pattern, Locale.KOREA);
		return f.format(d);
	}

	public static String formatDate(String pattern, String strDate) {
		SimpleDateFormat f = new SimpleDateFormat(pattern, Locale.KOREA);
		Calendar cal = toCalendar(strDate);
		return f.format(cal.getTime());
	}

	// apply pattern to a number formatDouble("###,###', 123456) => "123,456"
	public static String formatDouble(String pattern, double value) {
		DecimalFormat df = new DecimalFormat(pattern);
		df.setParseIntegerOnly(true);
		return df.format(value);
	}

	public static String formatLongTime(long longTime, String pattern) {
		java.util.Date dateTime     = new java.util.Date();
		SimpleDateFormat sdf 		= new SimpleDateFormat(pattern);

		dateTime.setTime(longTime);

		return sdf.format(dateTime);
	}

	public static byte[] getAsciiBytes(String buf) {
		int size = buf.length();
		byte[] bytebuf = new byte[size];

		for (int i = 0; i < size; i++) {
			bytebuf[i] = (byte) buf.charAt(i);
		}
		return bytebuf;
	}

	public static String getAsciiString(byte[] bytebuf) {
		int size = bytebuf.length;
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < size; i++) {
			buf.append((char) bytebuf[i]);
		}
		return buf.toString();
	}

	public static Calendar getCalendar(int year, int month, int day) {
		Calendar cal = null;
		try {
			cal = Calendar.getInstance();
			cal.setLenient(false);
			cal.set(year, month - 1, day);
			cal.getTime();
		} catch (Exception e) {
			cal = null;
		}
		return cal;
	}

	// ============== Date & Calendar ==============//
	public static Calendar getCurCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		// Date current = new Date(System.currentTimeMillis() );
		return (cal);
	}

	public static Calendar getCurDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return (cal);
	}

	public static String getCurDateStr() {
		return (calendarToStr(getCurDate()));
	}

	// Current Date YYYYMMDD format Return
	public static String getCurDateString() {
		return formatDate("yyyyMMdd", getCurCalendar());
	}

	// Current Date YYYY-MM-DD HH24:MI:SS format Return
	public static String getCurrDateTime() {
		return formatDate("yyyy-MM-dd HH:mm:ss", getCurCalendar());
	}

	// Current Date YYYY-MM-DD HH24:MI:SS format Return
	public static String getCurrDateTime2() {
		return formatDate("yyyyMMddHHmmss", getCurCalendar());
	}

	// Current Day
	public static int getCurrDay() {
		return getDay(getCurCalendar());
	}

	// Current Year
	public static int getCurrYear() {
		return getYear(getCurCalendar());
	}

	// Current Hour
	public static String getCurrTime() {
		return formatDate("HH", getCurCalendar());
	}
	// Current Minute
	public static String getCurrMinute() {
		return formatDate("mm", getCurCalendar());
	}
	// Current Second
	public static String getCurrSecond() {
		return formatDate("ss", getCurCalendar());
	}

	// get day of month from calendar.
	public static int getDay(Calendar cal) {
		int res = 0;
		if (cal != null)
			res = cal.get(Calendar.DATE);
		return res;
	}

	public static int getDay(String strDate) {
		return (getDay(strToCalendar(strDate)));
	}

	// ( ex.20060101011330 -> 2006-01-01 01:13:30 )
	public static String formatDateTime(String str) {
		if (str == null)
			return "";
		String s = str.replaceAll("-", "");
		String ret = "";
		if (s.length() >= 6)
			ret = s.substring(0, 4) + "-" + s.substring(4, 6);
		if (s.length() >= 8)
			ret += "-" + s.substring(6, 8);
		if (s.length() >= 12) {
			ret += " " + s.substring(8, 10);
			ret += ":" + s.substring(10, 12);
		}
		if (s.length() >= 14)
			ret += ":" + s.substring(12, 14);
		return ret;
	}

	public static Calendar getFirstDate(Calendar cal) {
		return getCalendar(getYear(cal), getMonth(cal), 1);
	}

	public static Calendar getFirstDate(String strDate) {
		return getFirstDate(strToCalendar(strDate));
	}

	public static String getDayOfWeek(Calendar cal) {
		return String.valueOf(cal.get(Calendar.DAY_OF_WEEK));
	}

	public static String getDayOfWeek(String strDate) {
		return getDayOfWeek(strToCalendar(strDate));
	}

	public static String getFirstDateStr(Calendar cal) {
		return calendarToStr(getFirstDate(cal));
	}

	public static String getFirstDateStr(String strDate) {
		return calendarToStr(getFirstDate(strDate));
	}

	public static Calendar getLastDate(Calendar cal) {
		return getCalendar(getYear(cal), getMonth(cal), daysOfMonth(cal));
	}

	public static Calendar getLastDate(String strDate) {
		return getLastDate(strToCalendar(strDate));
	}

	public static String getLastDateStr(Calendar cal) {
		return calendarToStr(getLastDate(cal));
	}

	public static String getLastDateStr(String strDate) {
		return calendarToStr(getLastDate(strDate));
	}

	// Current Year (Param : Calendar )
	public static int getYear(Calendar cal) {
		int res = 0;
		if (cal != null)
			res = cal.get(Calendar.YEAR);
		return res;
	}

	// get month of year from calendar.
	public static int getMonth(Calendar cal) {
		int res = 0;
		if (cal != null)
			res = cal.get(Calendar.MONTH) + 1;
		return res;
	}

	public static boolean isGreaterEqual(String str, String org) {
		if (str != null && str.compareTo(org) >= 0)
			return true;
		return false;
	}

	public static boolean isGreaterThan(String str, int n) {
		if (str != null && str.length() >= n)
			return true;
		return false;
	}

	public static boolean isInt(String str) {
		boolean rtn = false;
		try {
			Integer.parseInt(str);
			rtn = true;
		} catch (Exception e) {
			rtn = false;
		}
		return rtn;
	}

	public static boolean isLessEqual(String str, String org) {
		if (str != null && str.compareTo(org) <= 0)
			return true;
		return false;
	}

	public static boolean isLessThan(String str, int n) {
		if (str != null && str.length() < n)
			return true;
		return false;
	}

	public static boolean isLessThan(String str, String org) {
		if (str != null && str.compareTo(org) < 0)
			return true;
		return false;
	}

	public static boolean isLong(String str) {
		boolean rtn = false;
		try {
			Long.parseLong(str);
			rtn = true;
		} catch (Exception e) {
			rtn = false;
		}
		return rtn;
	}

	public static boolean isNumeric(String str) {
		boolean rtn = false;
		try {
			Double.parseDouble(str);
			rtn = true;
		} catch (NumberFormatException e) {
			rtn = false;
		}
		return rtn;
	}

	// check if a string is valid integer.
	public static boolean isStrValidInt(String str) {
		boolean res = false;
		try {
			Integer.parseInt(str);
			res = true;
		} catch (Exception e) {
			res = false;
		}
		return res;
	}

	// check if a string is valid long-integer.
	public static boolean isStrValidLong(String str) {
		boolean res = false;
		try {
			Long.parseLong(str);
			res = true;
		} catch (Exception e) {
			res = false;
		}
		return res;
	}

	// change null string to blank string //
	public static String nvl(String str) {
		String value = "";
		if (str != null && str.length() > 0) {
			value = str;
		}
		return value;
	}

	public static String replace(String src, String find, String rep) {
		if (src == null)
			return null;
		if (find == null)
			return src;
		if (rep == null)
			rep = "";
		StringBuffer res = new StringBuffer();
		String sp[] = split(src, find);
		res.append(sp[0]);
		for (int i = 1; i < sp.length; i++) {
			res.append(rep);
			res.append(sp[i]);
		}
		return res.toString();
	}

	// insert sepeartion string(or char) into the string.
	// sepString("0123456789",'-',4,5) => "0123-456789".
	public static String sepString(String str, String sep, int[] ind) {
		String res = "";
		int s1 = 0, s2 = 0;
		try {
			for (int i = 0; i < ind.length; i++) {
				s2 += ind[i];
				res += str.substring(s1, s2);
				if (s2 < str.length())
					res += sep;
				s1 = s2;
			}
		} catch (Exception e) {
			res = "";
		}
		if (s1 < str.length())
			res += str.substring(s1);
		return res;
	}

	public static String[] split(String src, String delim) {
		if (src == null || delim == null)
			return null;
		ArrayList list = new ArrayList();
		int start = 0, last = 0;
		String term;
		while ((start = src.indexOf(delim, last)) > -1) {
			term = src.substring(last, start);
			list.add(term);
			last = start + delim.length();
		}
		term = src.substring(last, src.length());
		list.add(term);
		String[] res = new String[list.size()];
		list.toArray(res);
		return res;
	}

	// apply pattern to a string
	// strFormat("###,###", "123456") => "123,456"
	public static String strFormat(String pattern, String value) {
		DecimalFormat aFormatter = new DecimalFormat(pattern);
		Double v = new Double(0.);
		String res = "";
		try {
			v = Double.valueOf(value);
			res = aFormatter.format(v.doubleValue());
		} catch (Exception e) {
			res = "";
		}
		return (res);
	}

	// convert string-date to Calendar.
	public static Calendar strToCalendar(String strDate) {
		int y, m, d;
		Calendar cal = null;
		try {
			if (isStrValidInt(strDate)) { // YYYYMMDD Format.
				y = strToInt(strDate.substring(0, 4));
				m = strToInt(strDate.substring(4, 6));
				d = strToInt(strDate.substring(6, 8));
			} else { // YYYY-MM-DD Format.
				int i = 0;
				int j = 0;
				j = strDate.indexOf('-', i);
				y = currStrToInt(strDate.substring(i, j));
				i = ++j;
				j = strDate.indexOf('-', i);
				m = currStrToInt(strDate.substring(i, j));
				i = ++j;
				j = strDate.length();
				d = currStrToInt(strDate.substring(i, j));
			}
			cal = getCalendar(y, m, d);
			cal.getTime();
		} catch (Exception e) {
			cal = null;
		}
		return cal;
	}

	public static double strToDouble(String str) {
		return strToDouble(str, 0);
	}

	// converts string value to double value. //
	public static double strToDouble(String str, double defaultValue) {
		double res = defaultValue;
		try {
			if (str == null || str.length() <= 0) {
				res	= defaultValue;
			} else {
				res = Double.parseDouble(delNonDouble(nvlTrim(str, "0")));
			}
		} catch (Exception e) {
			res = defaultValue;
		}
		return res;
	}

	public static int strToInt(String str) {
		return strToInt(str, 0);
	}

	// converts string value to integer value. //
	public static int strToInt(String str, int defaultValue) {
		int res = 0;
		try {
			if (str == null || str.length() <= 0) {
				res = defaultValue;
			} else {
				res = Integer.parseInt(delNonNum(nvlTrim(str, "0")));
			}
		} catch (Exception e) {
			res = 0;
		}
		return res;
	}

	public static long strToLong(String str) {
		return strToLong(str, 0L);
	}

	// converts string value to long-integer value. //
	public static long strToLong(String str, long defaultValue) {
		long res = 0;
		try {
			if (str == null || str.length() <= 0) {
				res = defaultValue;
			} else {
				res = Long.parseLong(delNonNum(nvlTrim(str, "0")));
			}
		} catch (Exception e) {
			res = 0;
		}
		return res;
	}

	// gbn(ex ",") 으로 한라인 문자를 잘라서 배열에 저장함
	public static String[] toArray(String str, String gbn) {
		String tmp = "";
		String[] retVal;
		Vector v = new Vector();
		StringTokenizer st = new StringTokenizer(str, gbn);
		while (st.hasMoreTokens()) {
			tmp = st.nextToken().trim();
			v.addElement(tmp);
		}
		retVal = new String[v.size()];
		v.copyInto(retVal);
		return retVal;
	}

	public static boolean toBoolean(String str) {
		if ("true".equals(str)) {
			return true;
		}
		return false;
	}

	// convert string-date to Calendar.
	public static Calendar toCalendar(String strDate) {
		int y, m, d;
		Calendar cal = null;
		try {
			// default : YYYYMMDD Format, 그외 : YYYY-MM-DD Format
			if (!isInt(strDate)) {
				strDate = strDate.replaceAll("-", "");
			}
			y = cutInt(strDate, 0, 4);
			m = cutInt(strDate, 4, 2);
			d = cutInt(strDate, 6, 2);
			cal = getCalendar(y, m, d);
			cal.getTime();
		} catch (Exception e) {
			cal = null;
		}
		return cal;
	}

	public static double toDouble(BigDecimal obj) {
		if (obj == null)
			return 0;
		return obj.doubleValue();
	}

	public static double toDouble(String str) {
		return toDouble(str, 0);
	}

	public static double toDouble(String str, double def) {
		double rtn = def;
		try {
			rtn = Double.parseDouble(str);
		} catch (NumberFormatException e) {
			rtn = def;
		}
		return rtn;
	}

	// [ntarget]  내용보기에서 <BR>태그와 Space 처리
	public static String toHtml(String strOld) {
		String strNew = "";

		strNew = replaceSpace(replace(strOld, "\n", "<BR>"), "  ", "&nbsp;&nbsp;");
		return strNew;
	}

	// [ntarget]  자료 등록시 스페이스 처리를 &nbsp; 태그로 변환한다.
	public static String replaceSpace(String strOld, String strTok, String strNewTok) {
		String strNew = "";

		for(int i = 0, j = 0; (j = strOld.indexOf(strTok,i)) > -1 ; i = j+2) {
			strNew += strOld.substring(i,j) + strNewTok;
		}
		if (strOld.indexOf(strTok) > -1) {
			strNew += strOld.substring(strOld.lastIndexOf(strTok)+strTok.length(), strOld.length());
		}
		else strNew = strOld;
		return strNew;
	}


	public static int toInt(String str) {
		return toInt(str, 0);
	}

	public static int toInt(String str, int def) {
		int rtn = def;
		try {
			rtn = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			rtn = def;
		}
		return rtn;
	}

	public static long toLong(BigDecimal obj) {
		if (obj == null)
			return 0;
		return obj.longValue();
	}

	public static long toLong(String str) {
		return toLong(str, 0L);
	}

	public static long toLong(String str, long def) {
		long rtn = def;
		try {
			rtn = Long.parseLong(str);
		} catch (NumberFormatException e) {
			rtn = def;
		}
		return rtn;
	}

	public static String toString(HashMap map, String name) {
		if (map != null) {
			BigDecimal obj = (BigDecimal) map.get(name);
			if (obj != null) {
				return obj.toString();
			}
		}
		return "0";
	}

	public static String toUpper(String str) {
		if (str == null)
			return null;
		return str.toUpperCase();
	}

	public static String nvlTrim(String str) {
		return nvl(str).trim();
	}

	public static String nvlTrim(String str, String defaultStr) {
		String rtn = "";
		if (nvl(str).trim().equals(""))
			rtn = defaultStr;
		else
			rtn = nvl(str).trim();

		return rtn;
	}

	public static String decodeAjax(String s, String charSet) {
		if(s != null) {
			try {
				return URLDecoder.decode(s, charSet);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return s;
	}
	public static String encodeAjax(String s, String charSet) {
		if(s != null) {
			try {
				return URLEncoder.encode(s, charSet);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return s;
	}

	public static String[] decodeAjax(String[] s, String charSet) {

		String[] ss = null;

		if(s != null && s.length > 0) {
			ss = new String[s.length];

			try {
				for (int n = 0; n < s.length; n++) {
					ss[n] = URLDecoder.decode(s[n], charSet);
				}

				return ss;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return ss;
	}

	// [ntarget] 구분으로 오늘 날짜 가져오기 (한국)
	public static String getToday(String gubun) {
		java.util.Date cdCurrent     = new java.util.Date();
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
		String currDate = dateFormat2.format(cdCurrent);

		String strYear = currDate.substring(0,4);
		String strMonth = currDate.substring(4,6);;
		String strDay = currDate.substring(6,8);

		String strToday = strYear +gubun+ strMonth +gubun+ strDay;
		return strToday;
	}

	// [ntarget] Byte 단위로 문자를 자른다.
	public static String substringByte( String s, int begin, int end ) {
		int rlen = 0;
		int sbegin = 0;
		int send = 0;

		for ( sbegin = 0; sbegin < s.length(); sbegin++ ) {
			if ( s.charAt( sbegin ) > 0x007f ) {
				rlen += 2;
				if ( rlen > begin ) {
					rlen -= 2;
					break;
				}
			} else {
				rlen++;
				if ( rlen > begin ) {
					rlen--;
					break;
				}
			}
		}

		for ( send = sbegin; send < s.length(); send++ ) {
			if ( s.charAt( send ) > 0x007f ) {
				rlen += 2;
			} else {
				rlen++;
			}

			if ( rlen > end )
				break;
		}
		return s.substring( sbegin, send );
	}

	// [ntarget] substring(start, end) -> null check
	public static String substring (String str, int start, int end) {
		String rtnValue = "";

		str = nvlTrim(str);

		if (str.length() >= end) {
			rtnValue = str.substring(start,end);
		}

		return rtnValue;
	}

	// [ntarget] substring(start) -> null check
	public static String substring (String str, int start) {
		String rtnValue = "";

		str = nvlTrim(str);

		if (str.length() >= start) {
			rtnValue = str.substring(start);
		}

		return rtnValue;
	}

    public static String Plus(String A, String B) {
    	A = nvlTrim(A, "0");
    	B = nvlTrim(B, "0");
        BigDecimal B_A = new BigDecimal(A.trim());
        BigDecimal B_B = new BigDecimal(B.trim());
        return B_A.add(B_B).toString();
    }

    public static String Plus(String A, String B, int Scale) {
    	A = nvlTrim(A, "0");
    	B = nvlTrim(B, "0");
        BigDecimal B_A = new BigDecimal(A.trim());
        BigDecimal B_B = new BigDecimal(B.trim());

        B_A = B_A.add(B_B).setScale(Scale, 1);

        return B_A.toString();
    }

    public static String Minus(String A, String B) {
    	A = nvlTrim(A, "0");
    	B = nvlTrim(B, "0");
        BigDecimal B_A = new BigDecimal(A.trim());
        BigDecimal B_B = new BigDecimal(B.trim());

        B_A = B_A.add(B_B.negate());

        return B_A.toString();
    }

    public static String Minus(String A, String B, int Scale) {
    	A = nvlTrim(A, "0");
    	B = nvlTrim(B, "0");
        BigDecimal B_A = new BigDecimal(A.trim());
        BigDecimal B_B = new BigDecimal(B.trim());

        B_A = B_A.add(B_B.negate()).setScale(Scale, 1);

        return B_A.toString();
    }

    public static String Multiply(String A, String B) {
    	A = nvlTrim(A, "0");
    	B = nvlTrim(B, "0");
        BigDecimal B_A = new BigDecimal(A.trim());
        BigDecimal B_B = new BigDecimal(B.trim());

        B_A = B_A.multiply(B_B);

        return B_A.toString();
    }

    public static String Multiply(String A, String B, int Scale) {
    	A = nvlTrim(A, "0");
    	B = nvlTrim(B, "0");
        BigDecimal B_A = new BigDecimal(A.trim());
        BigDecimal B_B = new BigDecimal(B.trim());

        B_A = B_A.multiply(B_B).setScale(Scale, 1);

        return B_A.toString();
    }

    public static String Divide(String A, String B) {
    	A = nvlTrim(A, "0");
    	B = nvlTrim(B, "0");
        int Scale = 30;
        if(A.equals("0") || B.equals("0")) return "0";

        BigDecimal B_A = new BigDecimal(A.trim());
        BigDecimal B_B = new BigDecimal(B.trim());

        B_A = B_A.divide(B_B, Scale, 1);

        return B_A.toString();
    }

    public static String Divide(String A, String B, int Scale) {
    	A = nvlTrim(A, "0");
    	B = nvlTrim(B, "0");
    	if(A.equals("0") || B.equals("0")) return "0";
        // Scale이 3일 경우 소수 셋째자리까지보여준다.
        BigDecimal B_A = new BigDecimal(A.trim());
        BigDecimal B_B = new BigDecimal(B.trim());

        B_A = B_A.divide(B_B, Scale, 1);

        return B_A.toString();
    }

    public static String DivideR(String A, String B, int Scale) {
    	A = nvlTrim(A, "0");
    	B = nvlTrim(B, "0");
    	if(A.equals("0") || B.equals("0")) return "0";
    	// Scale이 3일 경우 소수 셋째자리까지보여준다.
    	BigDecimal B_A = new BigDecimal(A.trim());
    	BigDecimal B_B = new BigDecimal(B.trim());

    	B_A = B_A.divide(B_B, Scale, BigDecimal.ROUND_HALF_UP);	// 반올림

    	return B_A.toString();
    }

    // ** [sbb2.0-fix findbugs, cslee] Predictable Pseudo Random Number Generantor - 18.08.01
    // --> 사용되징 않는 소스 제거 조치
//	// [ntarget] 2007-11-02 Random값 가져오기
//	public static String getRandomString(int len) {
//		String randomStr = "abcdefghijklmnopqrstuvwxyz123456789";
//		StringBuffer strB = new StringBuffer();
//
//		Random random = new Random();
//
//		for (int i = 0; i < len; i++) {
//			int rdIdx = random.nextInt(35);
//			strB.append(substring(randomStr,rdIdx,rdIdx+1));
//		}
//
//		return strB.toString();
//	}

	/**
     * 비밀번호를 암호화로 encoding한 문자열을 return
     * @param passwd
     */
	public static String getPasswordEncodingString(String passwd) {
		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);

		return encoder.encodePassword(passwd, null);
    }


	/**
	 * 특정 문자열 갯수 구하기.
	 * ntarget : 2011-01-16
	 */
	public static int getStringCnt(String str, String fd) {
		Pattern pttn = Pattern.compile(fd);

		Matcher mat = pttn.matcher(str);
		int cnt = 0;
		for( int i = 0; mat.find(i); i = mat.end())
			cnt++;

		return cnt;
	}

	public static String toHexString(String text) {
		String toHexString = "";
		char [] chars = text.toCharArray();
		for(int i=0;i<chars.length;i++) {
			toHexString += Integer.toHexString((int)chars[i]);
		}
		return toHexString;
	}


    /**
     * 정수 값을 반환한다.
     *
     * @param key 키
     * @param defaultValue 기본 값
     * @return 값
     */
    public static int getInt(Object value, int defaultValue) {

        if (value == null) {
            return defaultValue;
        }
        if (value.toString().trim().length() == 0) {
            return defaultValue;
        }

        return Integer.parseInt(value.toString().trim(), 10);
    }

    /**
     * 문자 값을 반환한다.
     *
     * @param key 키
     * @param defaultValue 기본 값
     * @return 값
     */
    public static String getString(Object value, String defaultValue) {

        if (value == null) {
            return defaultValue;
        }
        if (value.toString().trim().length() == 0) {
            return defaultValue;
        }
        return value.toString().trim();
    }

	public static String rpad(String org, String pad, int len) {
		return pad(org, pad, len, 'R'); // AAA00000
	}

	public static String lpad(String org, String pad, int len) {
		return pad(org, pad, len, 'L'); // 00000AAA
	}

	public static String pad(String org, String pad, int len, char pos) {
		String s = org;
		while (s.length() < len) {
			if (pos == 'L')
				s = pad + s;
			else
				s = s + pad;
		}
		return s;
	}

	public static String downFileName(String userAgent, String filename) {
		String downFileName = "";

		try {
			// IE10 이하, IE11
			if (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1) {
				downFileName = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
			// CHROME
			} else if (userAgent.indexOf("Chrome") > -1) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < filename.length(); i++) {
					char c = filename.charAt(i);
					if (c > '~') {
						sb.append(URLEncoder.encode(""+c, "UTF-8"));
					} else {
						sb.append(c);
					}
				}
				downFileName	= sb.toString();
			// FireFox
			} else if (userAgent.indexOf("Firefox") > -1) {
				downFileName	= new String(filename.getBytes("UTF-8"), "ISO-8859-1");
			// Opera 및 기타.
			} else {
				downFileName	= new String(filename.getBytes("UTF-8"), "ISO-8859-1");
			}
		} catch (UnsupportedEncodingException e) {
			downFileName = filename;
		}
		return downFileName;
	}

	// 첨부파일 확장자 가능 체크
	public static boolean isAtthAllowedFileType(String fileNm, String allowedExtNms) {
		String extNm = fileNm.substring(fileNm.toLowerCase().lastIndexOf(".") + 1);

		extNm = extNm.toLowerCase();

	    if (allowedExtNms.indexOf(extNm) >= 0){
	    	return true;
	    }

		return false;
	}
	
	//거리 구하기
	public static double GetDistance( double a, double b , double c , double d )
	{
	    return Math.sqrt( ( c - a ) * ( c - a ) + ( d - b ) * ( d - b ) );
	}
	
	//최소값
	public static double GetMin(double x , double y)
	{
	    if ( x < y )
	        return x;

	    return y;
	}

	//최대값
	public static double GetMax(double x, double y)
	{
	    if ( x > y )
	        return x;

	    return y;
	}
	
	//평균값
	public static double average(List<Double> avg) {
	    double sum = 0.0;

	    for (int i = 0; i < avg.size(); i++)
	      sum += avg.get(i);

	    return sum / avg.size();
	}

	//BigDecimal 객체 캐스팅
	public static BigDecimal getBigDecimal(Object value) {
		BigDecimal ret = null;
		if (value != null) {
			if (value instanceof BigDecimal) {
				ret = (BigDecimal) value;
			} else if (value instanceof String) {
				ret = new BigDecimal((String) value);
			} else if (value instanceof BigInteger) {
				ret = new BigDecimal((BigInteger) value);
			} else if (value instanceof Number) {
				ret = new BigDecimal(((Number) value).doubleValue());
			} else {
				throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
			}
		}
		return ret;
	}

	//일치 횟수 조회
	public static int countContain(String strPassword, String strCheck) {
		int nCount = 0;

		for (int i = 0; i < strPassword.length(); i++) {
			if (strCheck.indexOf(strPassword.charAt(i)) > -1) {
				nCount++;
			}
		}
		return nCount;
	}

}
