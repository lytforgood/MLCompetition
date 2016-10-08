package safe.webshell;

import java.util.HashMap;
import java.util.Map;

public class QueryWebshellsimple {

	static double base_score = 1.9;
	static Map<String, Double> key_word = new HashMap<String, Double>() {
		private static final long serialVersionUID = 1L;

		{
			put("40eval", 2.0);
			put("@eval", 2.0);
			put("edoced_46", 2.0);
			put("base64_decode", 2.0);
			put("response.end", 2.0);
			put("array_map(", 2.0);
			put("09error_reporting", 2.0);
			put("execute(", 2.0);
			put("@ini_get", 2.0);
			put("chopper", 2.0);
			put("<html>", 2.0);
			put("ex\"%26chr(101)%26\"cute", 2.0);
			put("%u0065va", 2.0);
			put("%u00", 2.0);
			put("preg_replace", 2.0);
			put("ini_set", 2.0);
			put("0aset_time_limit", 2.0);
			put("$password", 2.0);
			put("action=file&dir", 2.0);
			put("&cwd=", 2.0);
			put("viewfile=", 2.0);
			put("action=file&", 2.0);
			put("view_writable=0&dir=", 2.0);
			put("action=editfile&", 2.0);
			put("showfiles=", 2.0);
			put("echo(base", 2.0);
			put("a=console&", 2.0);
			put("sqladmin", 2.0);
			put("action=delfile&", 2.0);
			put("action=copyfile&", 2.0);
			put("action=downfile&", 2.0);
			put("a=filesman&", 2.0);
			put("renamefile=", 2.0);
			put("print_r(", 2.0);
			put("action=shell", 2.0);
			put("&pfile=d", 2.0);
			put("edittype=edit&", 2.0);
			put("&dbhost=127.0.0.1", 2.0);
			put("dbtype=mysql&dbhost=&dbuser=&db", 2.0);
			put("newfile=", 2.0);
			put("a=filesman&", 2.0);
			put("renamefile=", 2.0);
			put("envlpass=", 2.0);
		}	
	};
	static Map<String, Double> KEY_Word = new HashMap<String, Double>() {
		private static final long serialVersionUID = 1L;

		{
//			put("40eval", 2.0);
//			put("@eval", 2.0);
//			put("edoced_46", 2.0);
//			put("base64_decode", 2.0);
//			put("response.end", 2.0);
//			put("array_map(", 2.0);
//			put("09error_reporting", 2.0);
//			put("execute(", 2.0);
//			put("@ini_get", 2.0);
//			put("chopper", 2.0);
//			put("<html>", 2.0);
//			put("ex\"%26chr(101)%26\"cute", 2.0);
//			put("%u0065va", 2.0);
//			put("%u00", 2.0);
//			put("preg_replace", 2.0);
//			put("ini_set", 2.0);
//			put("0aset_time_limit", 2.0);
//			put("$password", 2.0);
//			put("action=file&dir", 2.0);
//			put("&cwd=", 2.0);
//			put("viewfile=", 2.0);
//			put("action=file&", 2.0);
//			put("view_writable=0&dir=", 2.0);
//			put("action=editfile&", 2.0);
//			put("showfiles=", 2.0);
//			put("echo(base", 2.0);
//			put("a=console&", 2.0);
//			put("sqladmin", 2.0);
//			put("action=delfile&", 2.0);
//			put("action=copyfile&", 2.0);
//			put("action=downfile&", 2.0);
//			put("a=filesman&", 2.0);
//			put("renamefile=", 2.0);
//			put("print_r(", 2.0);
//			put("action=shell", 2.0);
//			put("&pfile=d", 2.0);
//			put("edittype=edit&", 2.0);
//			put("&dbhost=127.0.0.1", 2.0);
//			put("dbtype=mysql&dbhost=&dbuser=&db", 2.0);
//			put("newfile=", 2.0);
//			put("a=filesman&", 2.0);
//			put("renamefile=", 2.0);
//			put("envlpass=", 2.0);
//			
			put("System.Text.Encoding", 2.0);
			put("Execute+", 2.0);
			put("Execute(", 2.0);
			put("$_POST", 2.0);
			put("Response.Write", 2.0);
			put("Execute(", 2.0);
			put("Ev Al", 2.0);
			put("Ev al", 2.0);
			put("Request.Item", 2.0);
			put("Content-Disposition", 2.0);
			put("FilesTools", 2.0);
			put("z=D&z0", 2.0);
			put("CreateObject", 2.0);
			put("&z0=GB2312", 2.0);
			put("%3D%22_P", 2.0);
			put("%3D%22_%5Cx50", 2.0);
			put("%3d%22exe", 2.0);
			put("FolderPath=", 2.0);
			put("a=RC&p1=", 2.0);
			put("a=Php&c=", 2.0);
			put("a=Php&p1", 2.0);
			put("a=Php&c=", 2.0);
			put("z0=UTF-8&z1", 2.0);
			put("p2=ZWNoby", 2.0);
			put("z0=QHNldF9", 2.0);
			put("eVAl(", 2.0);
			put("a=Php&", 2.0);
			put("z0=UTF-8", 2.0);
			put("path=%25", 2.0);
			put("getdir=%2F", 2.0);
			put("terminalInput=C", 2.0);
//			put("system.text.encoding", 2.0);
//			put("execute+", 2.0);
//			put("execute(", 2.0);
//			put("$_post", 2.0);
//			put("response.write", 2.0);
//			put("ev al", 2.0);
//			put("request.item", 2.0);
//			put("content-disposition", 2.0);
//			put("filestools", 2.0);
//			put("z=d&z0", 2.0);
//			put("createobject", 2.0);
//			put("white=m&z0", 2.0);
//			put("&z0=gb2312", 2.0);
//			put("%3d%22_P", 2.0);
//			put("%3d%22exe", 2.0);
//			put("folderpath=", 2.0);
//			put("a=rc&p1=", 2.0);
//			put("a=php&c=", 2.0);
//			put("a=php&p1", 2.0);
//			put("a=php&c=", 2.0);
//			put("z0=utf-8&z1", 2.0);
//			put("p2=zwnoby", 2.0);
//			put("z0=qhnldf9", 2.0);
//			put("a=php&", 2.0);// a=Php& 全包含
//			put("z0=utf-8", 2.0);// (post_data like "%z0=UTF-8%" and
//									// char_matchcount(post_data,'&')<2)
//			put("path=%25", 2.0);
//			
//			put("getdir=%2F", 2.0);
//			put("delete=", 2.0);// /post_data like "delete=%"
//			put("terminalinput=c", 2.0);// post_data like "%terminalInput=C%"
		
//			put("execute", 0.1);
//			put("eval(", 0.1);
//			put("%5cx", 0.1);
//			put("%3d%22", 0.1);
//			put("echo(", 0.1);
//			put("echo()", 0.1);
//			put("&doing=login", 0.1);
//			put("&do=login", 0.1);
//			put("&action=login", 0.1);
//			put("&act=login", 0.1);
//			put("pass", 0.1);
			// put("eVAl(", 2.0);//eVAl(
			// put("z0", 1.5);
			// put("z1", 1.0);
			// put("z2", 1.0);
			// put("caidao", 1.8);
			// put("mb", 0.8);
			// put("hk715", 1.2);
			// put("xise", 1.5);
			// put("diaosi", 1.0);
		}
	};

	private static Double isweak(String postdata, String query_postdata) {
		Double weakscore = 0.0;
		if (postdata.contains("Execute")) {
			if (postdata.contains("eval(")) {
				weakscore += 2.1;
			}
		}
		if (postdata.contains("%5Cx")) {
			if (postdata.contains("%3D%22")) {
				weakscore += 2.1;
			}
		}
		if (query_postdata.contains("echo(")) {
			if (query_postdata.contains("echo()")) {
			}else {
				weakscore += 2.1;
			}
		}
		if (query_postdata.contains("&doing=login") || query_postdata.contains("&do=login")
				|| query_postdata.contains("&action=login") || query_postdata.contains("&act=login")) {
			if (query_postdata.contains("pass")) {
				weakscore += 2.1;
			}
			;
		}

		return weakscore;
	}

	public static Boolean isWebshell(String postdata) {

		String query_postdata = postdata.toLowerCase();
		Double score = 0.0;
//		if (postdata.contains("eVAl(")) {
//			score += 2.0;
//
//		}
		if (postdata.startsWith("delete=")) {
			score += 2.0;
		}
		
		for (String kEY : KEY_Word.keySet()) {
			if (postdata.contains(kEY)) {
				score += KEY_Word.get(kEY);
			}

		}
		for (String key : key_word.keySet()) {
			if (query_postdata.contains(key)) {
				score += key_word.get(key);
				// System.out.println("key= "+ key + " and value= " +
				// key_word.get(key));
			}
//			Double weakscore = isweak(key, query_postdata);
		}
		Double weakscore = isweak(postdata, query_postdata);
		if (weakscore > 1.0) {
		// System.out.println(weakscore+key);
		// System.out.println(weakscore);
			score += weakscore;
		}
		if (score >= base_score) {
			return true;
		}
		return false;

	}

	public static void main(String[] args) {
		// String s =
		// "sd=Execute+++++++++++++++++++++(\"++++++++++++++++++++++++++++++Execute++++++++++++++++++++++++++++++(\"\"++++++++++:Function+bd%28byVal+s%29%3AFor+i%3D1+To+Len%28s%29+Step+2%3Ac%3DMid%28s%2Ci%2C2%29%3AIf+IsNumeric%28Mid%28s%2Ci%2C1%29%29+Then%3AExecute%28%22%22%22%22bd%3Dbd%26chr%28%26H%22%22%22%22%26c%26%22%22%22%22%29%22%22%22%22%29%3AElse%3AExecute%28%22%22%22%22bd%3Dbd%26chr%28%26H%22%22%22%22%26c%26Mid%28s%2Ci%2B2%2C2%29%26%22%22%22%22%29%22%22%22%22%29%3Ai%3Di%2B2%3AEnd+If%22%22%26chr%2810%29%26%22%22Next%3AEnd+Function:Response.Write(\"\"\"\"->|\"\"\"\"):++++++++";
		String s = "&doing=login pass";
		System.out.println(isWebshell(s) == true);
	}

}
