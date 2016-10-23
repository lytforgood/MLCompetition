package safe.webshell;

import java.util.HashMap;
import java.util.Map;

public class QueryWebshellnoError {

	public static Boolean isWebshell(String id, String postdata) {

		String query_postdata = postdata.toLowerCase();
		Double score = 0.0;
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
			}
		}
		Double weakscore = isweak(postdata, query_postdata);
		if (weakscore > 1.0) {
			score += weakscore;
		}
		Double errorscore = iserror(id, postdata);
		if (errorscore < 0.0) {
			score = 0.0;
		}

		if (score >= base_score) {
			return true;
		}

		return false;

	}

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
		}
	};
	static Map<String, Double> id_error = new HashMap<String, Double>() {
		private static final long serialVersionUID = 1L;

		{
			put("9b702ba7-c620-4822-b1f6-66031b956e6d", -3.0);
			put("1944474b-9994-4bf9-9719-231d2156ff36", -3.0);
			put("61e67d4c-cb32-472c-bae9-2fa4d6419281", -3.0);
			put("7f89f426-a88d-4560-a21f-90f383e60e59", -3.0);
			put("cf94734e-8221-4baa-a27c-41044e92aef1", -3.0);
			put("f526dfd7-3c74-4f0c-8906-dab4f62a533a", -3.0);
			put("f5f5c75e-95a6-439e-95d7-b093d600b374", -3.0);
			put("7cb498fe-e5de-4da6-a076-08ea9e651f4f", -3.0);
			put("851372d5-1ae5-4494-be46-cbaa85968089", -3.0);
			put("d541901c-4050-4971-b93f-f09ff70e73bf", -3.0);
			put("8333e42d-5708-4746-9c44-0c29eac310db", -3.0);
			put("2aa96f70-c517-4a3f-b56e-e96e2044a08e", -3.0);
			put("697cd164-69db-40e2-96ce-66c9a20c778a", -3.0);
			put("e64cadd2-d765-426c-9c9a-19449f64ae93", -3.0);
			put("4bbefbfe-5aed-4717-b8d1-91ffdacbe029", -3.0);
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
			} else {
				weakscore += 2.1;
			}
		}
		if (query_postdata.contains("&doing=login")
				|| query_postdata.contains("&do=login")
				|| query_postdata.contains("&action=login")
				|| query_postdata.contains("&act=login")) {
			if (query_postdata.contains("pass")) {
				weakscore += 2.1;
			}
			;
		}

		return weakscore;
	}

	private static Double iserror(String id, String postdata) {
		Double iserror = 0.0;
		for (String ider : id_error.keySet()) {
			if (id.contains(ider)) {
				iserror = -3.0;
			}

		}
		if (postdata.contains("code=eval(")) {
			iserror = -3.0;
		}
		if (postdata.contains("eval(gzinflate")) {
			iserror = -3.0;
		}
		if (postdata.contains("message=Response")) {
			iserror = -3.0;
		}
		if (postdata.contains("texttofriend=Response")) {
			iserror = -3.0;
		}
		return iserror;
	}

	public static void main(String[] args) {
		// String s =
		// "sd=Execute+++++++++++++++++++++(\"++++++++++++++++++++++++++++++Execute++++++++++++++++++++++++++++++(\"\"++++++++++:Function+bd%28byVal+s%29%3AFor+i%3D1+To+Len%28s%29+Step+2%3Ac%3DMid%28s%2Ci%2C2%29%3AIf+IsNumeric%28Mid%28s%2Ci%2C1%29%29+Then%3AExecute%28%22%22%22%22bd%3Dbd%26chr%28%26H%22%22%22%22%26c%26%22%22%22%22%29%22%22%22%22%29%3AElse%3AExecute%28%22%22%22%22bd%3Dbd%26chr%28%26H%22%22%22%22%26c%26Mid%28s%2Ci%2B2%2C2%29%26%22%22%22%22%29%22%22%22%22%29%3Ai%3Di%2B2%3AEnd+If%22%22%26chr%2810%29%26%22%22Next%3AEnd+Function:Response.Write(\"\"\"\"->|\"\"\"\"):++++++++";
		String s = "4b631=echo(md5('we'.'bs'.'c'.'an')); ";
		System.out.println(isWebshell("", s) == true);
	}

}
