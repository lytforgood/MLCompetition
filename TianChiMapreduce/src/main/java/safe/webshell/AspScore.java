package safe.webshell;

import java.util.*;



public class AspScore {

    byte[] data;
    double score;
    boolean black;
    boolean white;
    int count_token;
    int count_par;
    int count_bracket;
    int count_brace;
    
    int ps, p;

    Map<String, Integer> existed_key = new HashMap<String, Integer>();
    
    static Map<String, Pair<Double, Integer>> keyword = new HashMap<String, Pair<Double, Integer>>() {
		private static final long serialVersionUID = 8287097453483845897L;

	{
        put("eval", new Pair<Double, Integer>(1.2, 1));
        put("execute", new Pair<Double, Integer>(1.2, 1));
        put("request", new Pair<Double, Integer>(0.9, 1));
        put("response", new Pair<Double, Integer>(0.9, 1));
        put("exception", new Pair<Double, Integer>(0.8, 1));
        put("chr", new Pair<Double, Integer>(0.4, 4));
        put("write", new Pair<Double, Integer>(0.4, 1));
        put("function", new Pair<Double, Integer>(0.3, 1));
        put("server", new Pair<Double, Integer>(0.2, 1));
        put("settimeout", new Pair<Double, Integer>(0.5, 1));
        put("replace", new Pair<Double, Integer>(0.4, 1));
        put("len", new Pair<Double, Integer>(0.5, 1));
        //put("if", new Pair<Double, Integer>(0.2, 1));
        //put("else", new Pair<Double, Integer>(0.2, 1));
        put("on", new Pair<Double, Integer>(0.2, 1));
        put("error", new Pair<Double, Integer>(0.3, 1));
        put("resume", new Pair<Double, Integer>(0.6, 1));
        put("next", new Pair<Double, Integer>(0.3, 1));
        put("isnumeric", new Pair<Double, Integer>(0.8, 1));
        put("_memberaccess", new Pair<Double, Integer>(-1.0, 1));
        put("setaccessible", new Pair<Double, Integer>(-1.0, 1));
        put("getdeclaredfield", new Pair<Double, Integer>(-1.0, 1));
        put("allowstaticmethodaccess", new Pair<Double, Integer>(-1.0, 1));
    }};


    public AspScore(byte[] data) {
        this.data = data;
        ps = -1;
        p = 0;
        score = 0.0;
    }

    double keyword_score() {
        String s = new String(Arrays.copyOfRange(data, ps, p)).toLowerCase();
        if (keyword.containsKey(s)) {
        	Integer val = existed_key.get(s);
        	if (val== null) {
        		val=0;
			}
            if (val>= keyword.get(s).second) {
                return 0.0;
            }
        	existed_key.put(s, val + 1);
            return keyword.get(s).first;
        }
        return 0.0;
    }
    
    boolean alphabet(byte b) {
    	return Character.isLetter(b) || Character.isDigit(b) || b == '_';
    }
    
    public static byte[] filter_strop (byte[] input) {
    	String s = new String(input);
    	s = s.replace("\"", "");
    	s = s.replace("&", "");
    	return s.getBytes();
    }

    public double score() {
    	if (data.length > 0 && (data[0] == '[' || data[0] == '{')) {
    		score -= 3;
    	}
    	while (true) {
    		if (p >= data.length) {
    			break;
    		}
    		if (alphabet(data[p]) && ps == -1) {
    			ps = p;
    		} else if (ps != -1 && !alphabet(data[p])) {
    			score += keyword_score();
    			ps = -1;
    		}
    		if (data[p] < 0) {
    			//score -= 0.1;
    		}
    		p += 1;
    	}
    	if (ps != -1) {
    		score += keyword_score();
    	}
        return score;
    }

    public static void main(String[] args) {
        System.out.println(new AspScore("a(b(c".getBytes()).score());
        System.out.println(new AspScore("eval($_GET['a']);".getBytes()).score());
        System.out.println(new AspScore("560648;@ini_set(\"display_errors\",\"0\");@set_time_limit(0);@set_magic_quotes_runtime(0);echo(\"->|\");;echo @fwrite(fopen(base64_decode($_POST[\"z1\"]),\"w\"),base64_decode($_POST[\"z2\"]))".getBytes()).score());
                    
    }
}
