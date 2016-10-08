package safe.webshell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class QueryWebshell {
	
static double thres_hold = 2.0;
	
    static Map<String, Double> sensitive_key = new HashMap<String, Double>() {
		private static final long serialVersionUID = 97314362015453784L;

	{
        put("z", 1.0);
        put("z0", 1.5);
        put("z1", 1.0);
        put("z2", 1.0);
        put("caidao", 1.8);
        put("mb", 0.8);
        put("hk715", 1.2);
        put("xise", 1.5);
        put("diaosi", 1.0);
    }};


	public static Boolean isWebshell(String postdata) {
		List<Pair<byte[], byte[]>> plist = QueryPostData.query_data(postdata.getBytes());
		for (Pair<byte[], byte[]> p : plist) {
			
			
    		byte[] key = Unquote.unquote(p.first);	    		
    		byte[] value = Unquote.unquote(p.second);
    		System.out.println(key);
			System.out.println(value);
    		if (score(key) >= thres_hold) {
    			return true;
    		}
    		if (score_key(key) + score(value) >= thres_hold) {
    			return true;
    		}
    	}
		return false;
		
	}
	   public static double scorePhp_one(byte[] payload) {
	    	double score = new PhpScore(payload).score();
	    	byte[] payload_base64 = Base64.decode_base64(payload);
	    	double score_base64_decoded = new PhpScore(payload_base64).score();
	    	if (score_base64_decoded > 0.8) {
	    		score_base64_decoded += 0.3;
	    	}
	    	return score > score_base64_decoded ? score : score_base64_decoded;
	    }
		
	    public static double scorePhp(byte[] payload) {
//	    	String s = new String(payload);
	    	double score = scorePhp_one(payload);
	    	/*if (s.indexOf('"') + 1 < payload.length) {
	        	double tscore = scorePhp_one(s.substring(s.indexOf('"') + 1).getBytes());
	        	score = score > tscore ? score : tscore;
	    	}
	    	if (s.indexOf('\'') + 1 < payload.length) {
	        	double tscore = scorePhp_one(s.substring(s.indexOf('\'') + 1).getBytes());
	        	score = score > tscore ? score : tscore;
	    	}*/
	    	return score;
	    }
	    
	    public static double scoreAsp(byte[] payload) {
	    	double score = new AspScore(payload).score();
	    	byte[] payload_base64 = Base64.decode_base64(payload);
//	    	byte[] payload_without_op = AspScore.filter_strop(payload);
//	    	double score_without_op = new AspScore(payload_without_op).score();
	    	double score_base64_decoded = new AspScore(payload_base64).score();
	    	if (score_base64_decoded > 0.8) {
	    		score_base64_decoded += 0.3;
	    	}
	    	score = score > score_base64_decoded ? score : score_base64_decoded;
	    	return score;
	    }

	    public static double score_key(byte[] payload) {
	    	String s = new String(payload).toLowerCase();
	        if (sensitive_key.containsKey(s)) {
	            return sensitive_key.get(s);
	        }
	        double score = 0.0;
	        //将字符串转换为字符数组-然后判断是否是字母 数字 连接符
	        for (char c: s.toCharArray()) {
	        	if (!Character.isLetter(c) 
	        			&& !Character.isDigit(c)
	        			&& c != '_'
	        			&& c != '$') {
	        		score -= 0.5;
	        	}
	        }
	        return score;
	    }
	    
	    public static double score(byte[] payload) {
	    	//return WebshellTokenizer.scoreTokens(new String(payload));
	    	double score_php = scorePhp(payload);
	    	double score_asp = scoreAsp(payload);
	    	return score_php > score_asp ? score_php : score_asp;
	    }
	    public static void main(String[] args) {
	    String	s = "sd=Execute++++++++++++++++++++++++++++++(\"++++++++++++++++++++++++++++++Execute++++++++++++++++++++++++++++++(\"\"++++++++++:Function+bd%28byVal+s%29%3AFor+i%3D1+To+Len%28s%29+Step+2%3Ac%3DMid%28s%2Ci%2C2%29%3AIf+IsNumeric%28Mid%28s%2Ci%2C1%29%29+Then%3AExecute%28%22%22%22%22bd%3Dbd%26chr%28%26H%22%22%22%22%26c%26%22%22%22%22%29%22%22%22%22%29%3AElse%3AExecute%28%22%22%22%22bd%3Dbd%26chr%28%26H%22%22%22%22%26c%26Mid%28s%2Ci%2B2%2C2%29%26%22%22%22%22%29%22%22%22%22%29%3Ai%3Di%2B2%3AEnd+If%22%22%26chr%2810%29%26%22%22Next%3AEnd+Function:Response.Write(\"\"\"\"->|\"\"\"\"):++++++++";
	    System.out.println(isWebshell(s) == true);
		}

}
