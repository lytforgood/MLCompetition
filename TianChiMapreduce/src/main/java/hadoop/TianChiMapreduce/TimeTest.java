package hadoop.TianChiMapreduce;

import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class TimeTest {

	public static void main(String[] args) throws ParseException {
//		String day = "20150730";
//		//String day2 = "20150727";
//		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
//		int num = -1;
//		Date dateTmp = df.parse(day);
		//算出两个日期相差几天
//		Date otherDateTmp = df.parse(day2);
//		if (dateTmp != null && otherDateTmp != null) {
//			long time = Math.abs(dateTmp.getTime() - otherDateTmp.getTime());
//			num = (int) (time / (24 * 60 * 60 * 1000));
//		}
//		System.out.println(num);
//		ArrayList recentplay_1=new ArrayList();
//		recentplay_1.add("1");
//		System.out.println(recentplay_1.get(0));
		
		
		//算出相差几天的日期
//		Calendar   rightNow   =   Calendar.getInstance(); 
//		rightNow.setTime(dateTmp); 
//		rightNow.add(Calendar.DATE,-90);//你要加减的日期   
//		Date   dt1=rightNow.getTime(); 
//		String   reStr=df.format(dt1); 
//		System.out.println(reStr);
		//datestr();
		usercountall();
	}
	
	private static void datestr() throws ParseException {
		int day[]={3,5,7,14,30,60,90};
		String dayx = "20150630";
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		int num = -1;
		Date dateTmp = df.parse(dayx);
		for (int i = 0; i < day.length; i++) {
			Calendar   rightNow   =   Calendar.getInstance(); 
			rightNow.setTime(dateTmp); 
			rightNow.add(Calendar.DATE,-day[i]);//你要加减的日期   
			Date   dt1=rightNow.getTime(); 
			String   reStr=df.format(dt1); 
			System.out.println("ds>="+reStr+" and ds<="+dayx);

		}
		
	}
	
	private static void replays() throws ParseException {
		int day[]={3,5,7,14,30,60,90};
		int biao[]={2,3,4,5,6,7,8};
		String dayx = "20150630";
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		int num = -1;
		Date dateTmp = df.parse(dayx);
		for (int i = 0; i < day.length; i++) {
			Calendar   rightNow   =   Calendar.getInstance(); 
			rightNow.setTime(dateTmp); 
			rightNow.add(Calendar.DATE,-day[i]);//你要加减的日期   
			Date   dt1=rightNow.getTime(); 
			String   reStr=df.format(dt1); 
			//System.out.println("ds>="+reStr+" and ds<="+dayx);
			String aString="create table if not  exists  "+"y"+"recentsongplay"+biao[i]+" as\r\n" + 
					"select  artist_id,sum(plays) as recentplay,\r\n" + 
					"floor(avg(plays)) as recentplayavg,\r\n" + 
					"floor(stddev(plays)) as recentplaystddev,\r\n" + 
					"min(plays) as recentplaymin,\r\n" + 
					"floor(median(plays)) as recentplaymedian  \r\n" + 
					"from aidnumxin where "+"ds>="+reStr+" and ds<="+dayx+"  group by artist_id;";
			System.out.println(aString);
		}
		
	}
	
	private static void replaysall() throws ParseException {
		int day[] = { 3, 5, 7, 14, 30, 60, 90 };
		int biao[] = { 2, 3, 4, 5, 6, 7, 8, 9 };
		String string[] = { "recentplay", "recentplayavg", "recentplaystddev",
				"recentplaymin", "recentplaymedian" };
		for (int i = 0; i < day.length; i++) {
			for (int j = 0; j < string.length; j++) {
				System.out.println("y"+"recentsongplay" + biao[i] + "."
						+ string[j] + " as " + string[j] + "_" + day[i] + ",");
			}

		}
		for (int i = 0; i < day.length; i++) {
				System.out.println("left outer join "+"y"+"recentsongplay" + biao[i] + "  on "+"y"+"recentsongplay1.artist_id="+"y"+"recentsongplay" + biao[i] + ".artist_id");

		}
	}
	
	private static void songcount() throws ParseException {
		int day[]={3,5,7,14,30,60,90};
		int biao[]={1,2,3,4,5,6,7};
		String dayx = "20150630";
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		int num = -1;
		Date dateTmp = df.parse(dayx);
		for (int i = 0; i < day.length; i++) {
			Calendar   rightNow   =   Calendar.getInstance(); 
			rightNow.setTime(dateTmp); 
			rightNow.add(Calendar.DATE,-day[i]);//你要加减的日期   
			Date   dt1=rightNow.getTime(); 
			String   reStr=df.format(dt1); 
			//System.out.println("ds>="+reStr+" and ds<="+dayx);
			
			String ss="create table if not  exists  "+"y"+"songcount"+biao[i]+" as\r\n" + 
					"select  artist_id,count(distinct song_id) as aid_sid_count from allsongxin where \r\n" + 
					"ds>="+reStr+" and ds<="+dayx+" group by artist_id;";
			System.out.println(ss);
		}
		
	}
	private static void songcountall() {
		int day[]={3,5,7,14,30,60,90};
    	int biao[]={1,2,3,4,5,6,7};
		String string[]={"aid_sid_count"};
		for (int i = 0; i < day.length; i++) {
			for (int j = 0; j < string.length; j++) {
				System.out.println("y"+"songcount"+biao[i]+"."+string[j]+" as "+string[j]+"_"+day[i]+",");
			}
			
		}
		String tmp="y";
		String ssString="case when floor("+tmp+"songcount1.aid_sid_count-"+tmp+"songcount2.aid_sid_count)>0 then floor("+tmp+"songcount1.aid_sid_count-"+tmp+"songcount2.aid_sid_count) else 0 end as new3,\r\n" + 
				"case when floor("+tmp+"songcount3.aid_sid_count-"+tmp+"songcount4.aid_sid_count)>0 then floor("+tmp+"songcount3.aid_sid_count-"+tmp+"songcount4.aid_sid_count) else 0 end as new7,\r\n" + 
				"case when floor("+tmp+"songcount4.aid_sid_count-"+tmp+"songcount5.aid_sid_count)>0 then floor("+tmp+"songcount4.aid_sid_count-"+tmp+"songcount5.aid_sid_count) else 0 end as new14\r\n" + 
				"from "+tmp+"songcount1 ";
		System.out.println(ssString);
		for (int i = 1; i < day.length; i++) {
			for (int j = 0; j < string.length; j++) {
				System.out.println("left outer join "+tmp+"songcount"+biao[i]+" on "+tmp+"songcount1.artist_id="+tmp+"songcount"+biao[i]+".artist_id");
			}
			
		}
	}
	

	private static void usercount() throws ParseException {
		int day[]={3,5,7,14,30,60,90};
		int biao[]={1,2,3,4,5,6,7};
		String dayx = "20150630";
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		int num = -1;
		Date dateTmp = df.parse(dayx);
		for (int i = 0; i < day.length; i++) {
			Calendar   rightNow   =   Calendar.getInstance(); 
			rightNow.setTime(dateTmp); 
			rightNow.add(Calendar.DATE,-day[i]);//你要加减的日期   
			Date   dt1=rightNow.getTime(); 
			String   reStr=df.format(dt1); 
			//System.out.println("ds>="+reStr+" and ds<="+dayx);
			String ss="create table if not  exists  "+"y"+"usercount"+biao[i]+" as\r\n" + 
					"select  artist_id,count(distinct song_id) as aid_uid_count from uidsidaidnumds where \r\n" + 
					"ds>="+reStr+" and ds<="+dayx+" group by artist_id;";
			System.out.println(ss);
		}
		
	}
	private static void usercountall() {
		int day[]={3,5,7,14,30,60,90};
    	int biao[]={1,2,3,4,5,6,7};
		String string[]={"aid_uid_count"};
		for (int i = 0; i < day.length; i++) {
			for (int j = 0; j < string.length; j++) {
				System.out.println("y"+"usercount"+biao[i]+"."+string[j]+" as "+string[j]+"_"+day[i]+",");
			}
			
		}
		String tmp="y";
		for (int i = 1; i < day.length; i++) {
			for (int j = 0; j < string.length; j++) {
				System.out.println("left outer join "+tmp+"usercount"+biao[i]+" on "+tmp+"usercount1.artist_id="+tmp+"usercount"+biao[i]+".artist_id");
			}
			
		}
		
		String  ad="create table if not  exists  "+tmp+"featuretest1 as\r\n" + 
				"select \r\n" + 
				""+tmp+"recentsongplayall.*,\r\n" + 
				""+tmp+"songcountall.aid_sid_count_3,\r\n" + 
				""+tmp+"songcountall.aid_sid_count_5,\r\n" + 
				""+tmp+"songcountall.aid_sid_count_7,\r\n" + 
				""+tmp+"songcountall.aid_sid_count_14,\r\n" + 
				""+tmp+"songcountall.aid_sid_count_30,\r\n" + 
				""+tmp+"songcountall.aid_sid_count_60,\r\n" + 
				""+tmp+"songcountall.aid_sid_count_90,\r\n" + 
				""+tmp+"songcountall.new3,\r\n" + 
				""+tmp+"songcountall.new7,\r\n" + 
				""+tmp+"songcountall.new14,\r\n" + 
				""+tmp+"usercountall.aid_uid_count_3,\r\n" + 
				""+tmp+"usercountall.aid_uid_count_5,\r\n" + 
				""+tmp+"usercountall.aid_uid_count_7,\r\n" + 
				""+tmp+"usercountall.aid_uid_count_14,\r\n" + 
				""+tmp+"usercountall.aid_uid_count_30,\r\n" + 
				""+tmp+"usercountall.aid_uid_count_60,\r\n" + 
				""+tmp+"usercountall.aid_uid_count_90\r\n" + 
				"from "+tmp+"recentsongplayall \r\n" + 
				"left outer join "+tmp+"songcountall  on "+tmp+"recentsongplayall.artist_id="+tmp+"songcountall.artist_id\r\n" + 
				"left outer join "+tmp+"usercountall  on "+tmp+"recentsongplayall.artist_id="+tmp+"usercountall.artist_id;";
 		
		System.out.println(ad);
	}
}
