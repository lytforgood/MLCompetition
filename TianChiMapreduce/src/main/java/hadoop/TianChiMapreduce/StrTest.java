package hadoop.TianChiMapreduce;

public class StrTest {
	public static void main(String[] args) {
		usercount2();
	}

	private static void bofangliuang() {
		int day[] = { 3, 5, 7, 14, 30, 60, 90 };
		int biao[] = { 2, 3, 4, 5, 6, 7, 8, 9 };
		String string[] = { "recentplay", "recentplayavg", "recentplaystddev",
				"recentplaymin", "recentplaymedian" };
		for (int i = 0; i < day.length; i++) {
			for (int j = 0; j < string.length; j++) {
				System.out.println("xrecentsongplay" + biao[i] + "."
						+ string[j] + " as " + string[j] + "_" + day[i] + ",");
			}

		}
	}

	private static void songcount() {
		int day[]={3,5,7,14,30,60,90};
    	int biao[]={1,2,3,4,5,6,7};
		String string[]={"aid_sid_count"};
		for (int i = 0; i < day.length; i++) {
			for (int j = 0; j < string.length; j++) {
				System.out.println("xsongcount"+biao[i]+"."+string[j]+" as "+string[j]+"_"+day[i]+",");
			}
			
		}
	}
	private static void songcount2() {
		int day[]={3,5,7,14,30,60,90};
    	int biao[]={1,2,3,4,5,6,7};
		String string[]={"aid_sid_count"};
		for (int i = 0; i < day.length; i++) {
			for (int j = 0; j < string.length; j++) {
				System.out.println("xsongcountall"+"."+string[j]+"_"+day[i]+",");
			}
			
		}
	}

	private static void usercount() {
		int day[] = { 3, 5, 7, 14, 30, 60, 90 };
		int biao[] = { 1, 2, 3, 4, 5, 6, 7 };
		String string[] = { "aid_uid_count" };
		for (int i = 0; i < day.length; i++) {
			for (int j = 0; j < string.length; j++) {
				System.out.println("xusercount" + biao[i] + "." + string[j]
						+ " as " + string[j] + "_" + day[i] + ",");
			}

		}
	}
	private static void usercount2() {
		int day[] = { 3, 5, 7, 14, 30, 60, 90 };
		int biao[] = { 1, 2, 3, 4, 5, 6, 7 };
		String string[] = { "aid_uid_count" };
		for (int i = 0; i < day.length; i++) {
			for (int j = 0; j < string.length; j++) {
				System.out.println("xusercountall"  + "."+ string[j] + "_" + day[i] + ",");
			}

		}
	}
}
