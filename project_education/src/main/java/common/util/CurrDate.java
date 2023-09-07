/**
 * Title:         CurrDate.java<p>
 * Description:   Get Current Date/Time<p>
 * author
 * Date:          2014-06-09
 */
package common.util;

import java.text.*;

public class CurrDate {
   private String csDate;        //(String) yyyy-mm-dd
   private String csDate2;       //(String) yyyymmdd
   private String csTime;        //(String) hh:mm:ss
   private String csTime2;       //(String) hhmmss
   private String csTime3;       // MiliSecond;(String) SSS
   private String csDateTime;    //
   private java.sql.Date cdDate; //
   private java.sql.Time ctTime; //

   /**
    *  Current Date, Time
    */
   public CurrDate() {
	  java.util.Date cdCurrent     = new java.util.Date();
	  SimpleDateFormat dateFormat  = new SimpleDateFormat("yyyy-MM-dd");
	  SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
	  SimpleDateFormat timeFormat  = new SimpleDateFormat("HH:mm:ss");
	  SimpleDateFormat timeFormat2 = new SimpleDateFormat("HHmmss");
	  SimpleDateFormat timeFormat3 = new SimpleDateFormat("SSSSS");
	  this.cdDate  = new java.sql.Date(cdCurrent.getTime());
	  this.ctTime  = new java.sql.Time(cdCurrent.getTime());
	  this.csDate  = dateFormat.format(cdCurrent);
	  this.csDate2 = dateFormat2.format(cdCurrent);
	  this.csTime  = timeFormat.format(cdCurrent);
	  this.csTime2 = timeFormat2.format(cdCurrent);
	  this.csTime3 = timeFormat3.format(cdCurrent);
	  this.csDateTime = this.csDate + " " + this.csTime;
   }

   /**
    * 현재날짜 스트링 리턴(yyyy-mm-dd)
    */
   public String getDateStr() {
	  return this.csDate;
   }

   /**
    * 	  현재날짜 스트링 리턴 (yyyymmdd)
    */
   public String getDateStr2() {
	  return this.csDate2;
   }

   /**
	 * 현재시간 스트링 리턴 (hh:mm:ss)
    */
   public String getTimeStr() {
	  return this.csTime;
   }

   /**
    *   현재시간 스트링 리턴 (hhmmss)
   */
   public String getTimeStr2() {
	  return this.csTime2;
   }

   /**
	 *  MiliSecond; String 리턴. (SSS)
   **/
   public String getTimeStr3() {
	  return this.csTime3;
   }

   /**
	*  현재 날짜와 시간 스트링 리턴
   **/
   public String getDateTimeStr() {
	  return this.csDateTime;
   }

   /**
	 * java.sql.Date 타입으로 현재 날짜 리턴
   **/
   public java.sql.Date getDate() {
	  return this.cdDate;
   }

   /**
	*  java.sql.Time 타입으로 현재 시간 리턴
   **/
   public java.sql.Time getTime() {
	  return this.ctTime;
   }
}