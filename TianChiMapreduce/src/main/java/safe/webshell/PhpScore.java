package safe.webshell;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;



public class PhpScore {

    byte[] data;
    boolean black;
    boolean white;
    int count_token;
    int count_var;
    int count_svar;
    int count_cmt;
    int count_par;
    int count_bracket;
    int count_brace;

    Set<String> existed_key = new HashSet<String>();

    static Map<String, Double> keyword = new HashMap<String, Double>() {
        private static final long serialVersionUID = 6899997024892413801L;
    {

        put("PHP_SELF", 1.6);
        put("_GET", 1.2);
        put("_POST", 1.6);
        put("_COOKIE", 1.5);
        put("_FILE", 1.2);
        put("_ENV", 1.2);
        put("_SESSION", 1.5);
        put("_REQUEST", 1.5);
        put("_SERVER", 1.6);
        put("array_map", 1.4);
        put("assert", 1.3);
        put("array_slice", 1.5);
        put("base64_decode", 2.0);
        put("base_convert", 1.7);
        put("edoced_46esab", 2.0);
        put("call_user_func", 1.0);
        put("call_user_func_array", 1.0);
        put("chr", 0.4);
        put("create_function", 0.8);
        put("curl_exec", 1.4);
        put("curl_multi_exec", 1.4);
        put("dirname", 1.0);
        put("echo", 1.0);
        put("error_reporting", 1.2);
        put("eval", 1.0);
        put("exec", 1.0);
        put("exit", 0.5);
        put("file_put_contents", 1.2);
        put("gzdecode", 1.2);
        put("implode", 1.5);
        //put("include", 0.9);
        put("include_once", 1.6);
        put("ini_set", 1.1);
        put("isset", 1.1);
        put("movefile", 1.2);
        put("ob_start", 1.5);
        put("parse_str", 1.5);
        put("passthru", 1.8);
        put("pcntl_exec", 1.7);
        put("phpinfo", 1.9);
        put("prege_replace", 1.5);
        put("proc_open", 1.7);
        //put("require", 0.9);
        put("require_once", 1.6);
        put("session_start", 1.5);
        put("set_magic_quotes_runtime", 1.0);
        put("set_time_limit", 1.4);
        put("shell_exec", 1.4);
        put("str_rot13", 1.2);
        put("strrev", 1.1);
        put("system", 0.5);


        put("_memberaccess", -10.0);
        put("allowstaticmethodaccess", -10.0);
        put("alert", -1.8);
        put("document", -1.8);
        put("fromcharcode", -10.0);
        put("getdeclaredfield", -10.0);
        put("parseint", -5.0);
        put("println", -4.0);
        put("prototype", -5.0);
        put("setaccessible", -10.0);
        put("string", -1.0);
        put("tostring", -4.0);
    }};

    
// line 108 "PhpScore.java"
private static byte[] init__php_lexer_actions_0()
{
	return new byte [] {
	    0,    1,    1,    1,    2,    1,    3,    1,    6,    1,    7,    1,
	    8,    1,    9,    1,   10,    1,   11,    1,   12,    1,   13,    1,
	   14,    1,   15,    1,   16,    1,   17,    1,   18,    1,   19,    1,
	   20,    1,   21,    1,   22,    1,   24,    1,   25,    1,   26,    1,
	   27,    1,   28,    1,   29,    1,   30,    1,   31,    1,   32,    1,
	   33,    1,   34,    2,    0,   23,    2,    3,    4,    2,    3,    5
	};
}

private static final byte _php_lexer_actions[] = init__php_lexer_actions_0();


private static short[] init__php_lexer_key_offsets_0()
{
	return new short [] {
	    0,    2,    2,    4,    6,    6,   17,   18,   19,   20,   21,   24,
	   26,   27,   28,   29,   30,   31,   35,   36,   37,   38,   39,   40,
	   41,   42,   43,   44,   45,   46,   47,   48,   49,   50,   51,   52,
	   53,   54,   55,   56,   57,   58,   59,   60,   61,   62,   63,   64,
	   66,   68,   74,   75,   76,   77,  114,  116,  117,  119,  121,  123,
	  134,  137,  137,  144,  148,  150,  155,  161,  163,  164,  171,  178,
	  185
	};
}

private static final short _php_lexer_key_offsets[] = init__php_lexer_key_offsets_0();


private static char[] init__php_lexer_trans_keys_0()
{
	return new char [] {
	   34,   92,   10,   13,   39,   92,   97,   98,  100,  102,  105,  111,
	  114,  115,  117,    0,   32,  114,  114,   97,  121,   41,    0,   32,
	  105,  111,  110,   97,  114,  111,  108,   41,  101,    0,   32,   97,
	  110,  111,  117,   98,  108,  101,  108,  111,   97,  116,  110,   98,
	  106,  101,   99,  101,   97,  108,  116,  114,  105,  110,  103,  110,
	  115,  101,   42,   47,   43,   45,   48,   57,   48,   57,   65,   70,
	   97,  102,  104,  112,   96,   33,   34,   35,   36,   39,   40,   41,
	   44,   47,   48,   60,   66,   79,   91,   93,   94,   96,   98,  111,
	  123,  125,    0,   32,   37,   38,   42,   46,   49,   57,   58,   64,
	   65,   90,   95,  122,  124,  126,    0,   32,   61,   34,   92,   10,
	   13,   39,   92,   97,   98,  100,  102,  105,  111,  114,  115,  117,
	    0,   32,   42,   47,   61,   46,   69,   88,  101,  120,   48,   57,
	   69,  101,   48,   57,   48,   57,   46,   69,  101,   48,   57,   48,
	   57,   65,   70,   97,  102,   37,   63,  112,   95,   48,   57,   65,
	   90,   97,  122,   95,   48,   57,   65,   90,   97,  122,   95,   48,
	   57,   65,   90,   97,  122,   96,    0
	};
}

private static final char _php_lexer_trans_keys[] = init__php_lexer_trans_keys_0();


private static byte[] init__php_lexer_single_lengths_0()
{
	return new byte [] {
	    2,    0,    2,    2,    0,    9,    1,    1,    1,    1,    1,    2,
	    1,    1,    1,    1,    1,    2,    1,    1,    1,    1,    1,    1,
	    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
	    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,    2,
	    0,    0,    1,    1,    1,   21,    0,    1,    2,    2,    2,    9,
	    3,    0,    5,    2,    0,    3,    0,    2,    1,    1,    1,    1,
	    1
	};
}

private static final byte _php_lexer_single_lengths[] = init__php_lexer_single_lengths_0();


private static byte[] init__php_lexer_range_lengths_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,    1,    0,    0,    0,    0,    1,    0,
	    0,    0,    0,    0,    0,    1,    0,    0,    0,    0,    0,    0,
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    1,    3,    0,    0,    0,    8,    1,    0,    0,    0,    0,    1,
	    0,    0,    1,    1,    1,    1,    3,    0,    0,    3,    3,    3,
	    0
	};
}

private static final byte _php_lexer_range_lengths[] = init__php_lexer_range_lengths_0();


private static short[] init__php_lexer_index_offsets_0()
{
	return new short [] {
	    0,    3,    4,    7,   10,   11,   22,   24,   26,   28,   30,   33,
	   36,   38,   40,   42,   44,   46,   50,   52,   54,   56,   58,   60,
	   62,   64,   66,   68,   70,   72,   74,   76,   78,   80,   82,   84,
	   86,   88,   90,   92,   94,   96,   98,  100,  102,  104,  106,  108,
	  111,  113,  117,  119,  121,  123,  153,  155,  157,  160,  163,  166,
	  177,  181,  182,  189,  193,  195,  200,  204,  207,  209,  214,  219,
	  224
	};
}

private static final short _php_lexer_index_offsets[] = init__php_lexer_index_offsets_0();


private static byte[] init__php_lexer_indicies_0()
{
	return new byte [] {
	    2,    3,    1,    1,    6,    6,    5,    2,    8,    7,    7,   11,
	   12,   13,   14,   15,   16,   17,   18,   19,   10,    9,   20,    9,
	   21,    9,   22,    9,   23,    9,   24,   23,    9,   25,   26,    9,
	   27,    9,   28,    9,   22,    9,   29,    9,   30,    9,   24,   31,
	   23,    9,   32,    9,   23,    9,   33,    9,   34,    9,   35,    9,
	   36,    9,   23,    9,   37,    9,   38,    9,   39,    9,   23,    9,
	   39,    9,   40,    9,   41,    9,   42,    9,   39,    9,   43,    9,
	   44,    9,   23,    9,   45,    9,   46,    9,   47,    9,   48,    9,
	   23,    9,   49,    9,   50,    9,   39,    9,   53,   52,   54,   52,
	   56,   56,   55,   57,   55,   58,   58,   58,   55,   60,   59,   61,
	   59,   62,    0,   64,   65,   66,   67,   69,   70,   71,   68,   72,
	   73,   75,   77,   78,   79,   81,   68,   82,   77,   78,   83,   84,
	   63,   68,   64,   74,   68,   76,   76,   68,   80,   63,   85,   87,
	   86,    2,    3,    1,    6,    6,    5,    2,    8,    7,   11,   12,
	   13,   14,   15,   16,   17,   18,   19,   10,   89,   52,    5,   87,
	   86,   90,   92,   93,   94,   93,   94,   74,   91,   93,   93,   92,
	   91,   57,   91,   92,   93,   93,   74,   91,   58,   58,   58,   91,
	   95,   96,   86,   98,   97,   76,   76,   76,   76,   99,   76,   76,
	   76,   76,   99,   76,   76,   76,   76,   99,   88,  100,    0
	};
}

private static final byte _php_lexer_indicies[] = init__php_lexer_indicies_0();


private static byte[] init__php_lexer_trans_targs_0()
{
	return new byte [] {
	   53,    0,   53,    1,   53,    2,   53,    3,    4,   53,    5,    6,
	   11,   20,   25,   29,   30,   34,   37,   42,    7,    8,    9,   10,
	   53,   12,   15,   13,   14,   16,   17,   18,   19,   21,   22,   23,
	   24,   26,   27,   28,   31,   32,   33,   35,   36,   38,   39,   40,
	   41,   43,   44,   53,   45,   46,   61,   53,   48,   64,   66,   53,
	   51,   53,   53,   54,   55,   56,   57,   53,   53,   58,   59,   53,
	   60,   62,   65,   67,   69,   70,   71,   53,   53,   53,   72,   53,
	   53,   53,   53,   53,   53,   53,   53,   53,   63,   47,   49,   53,
	   68,   53,   50,   53,   52
	};
}

private static final byte _php_lexer_trans_targs[] = init__php_lexer_trans_targs_0();


private static byte[] init__php_lexer_trans_actions_0()
{
	return new byte [] {
	   59,    0,    7,    0,   61,    0,   11,    0,    0,   53,    0,    0,
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	   25,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0,    0,    0,   57,    0,    0,    0,   51,    0,    0,    0,   55,
	    0,   27,    9,    0,    0,    5,   69,   33,   31,    5,    5,   13,
	   66,    5,    5,    0,    0,    0,    0,   15,   35,   17,    5,   19,
	   21,   47,   45,   23,   49,   41,   63,   39,    5,    0,    0,   29,
	    5,   43,    0,   37,    0
	};
}

private static final byte _php_lexer_trans_actions[] = init__php_lexer_trans_actions_0();


private static byte[] init__php_lexer_to_state_actions_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0,    0,    0,    0,    0,    1,    0,    0,    0,    0,    0,    0,
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0
	};
}

private static final byte _php_lexer_to_state_actions[] = init__php_lexer_to_state_actions_0();


private static byte[] init__php_lexer_from_state_actions_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0,    0,    0,    0,    0,    3,    0,    0,    0,    0,    0,    0,
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0
	};
}

private static final byte _php_lexer_from_state_actions[] = init__php_lexer_from_state_actions_0();


private static short[] init__php_lexer_eof_trans_0()
{
	return new short [] {
	    1,    1,    5,    1,    1,   10,   10,   10,   10,   10,   10,   10,
	   10,   10,   10,   10,   10,   10,   10,   10,   10,   10,   10,   10,
	   10,   10,   10,   10,   10,   10,   10,   10,   10,   10,   10,   10,
	   10,   10,   10,   10,   10,   10,   10,   10,   10,   52,   52,   56,
	   56,   56,   60,   60,    1,    0,   86,   87,   89,   89,   89,   90,
	   87,   91,   92,   92,   92,   92,   92,   87,   98,  100,  100,  100,
	   89
	};
}

private static final short _php_lexer_eof_trans[] = init__php_lexer_eof_trans_0();


static final int php_lexer_start = 53;
static final int php_lexer_first_final = 53;
static final int php_lexer_error = -1;

static final int php_lexer_en_main = 53;



    int p, pe, eof;
    int te, ts, cs, act;


    public PhpScore(byte[] data) {
        this.data = data;

        black = false;
        white = false;
        count_cmt = 0;
        count_var = 0;
        count_svar = 0;
        count_par = 0;
        count_token = 0;
        count_bracket = 0;
        count_brace = 0;

    }

    double string_score() {
        byte[] sb = Arrays.copyOfRange(data, ts + 1, te - 1);
        String s = new String(sb);
        //System.out.println(s);
        if (keyword.containsKey(s) && !existed_key.contains(s)) {
            existed_key.add(s);
            return keyword.get(s);
        }
        if (s.length() >= 10) {
            byte[] bsb = Base64.decode_base64(Base64.longest_sub_base64(sb));
            double s1 = new PhpScore(bsb).score();
            double s2 = new PhpScore(sb).score();
            s1 = s1 > s2 ? s1 : s2;
            s1 = s1 > 0 ? s1 : 0;
            return s1;
        }
        return 0.0;
    }

    double keyword_score() {
        byte[] sb = Arrays.copyOfRange(data, ts, te);
        String s = new String(sb);
        //System.out.println(s);
        if (keyword.containsKey(s) && !existed_key.contains(s)) {
            existed_key.add(s);
            return keyword.get(s);
        }
        byte[] bsb = Base64.decode_base64(Base64.longest_sub_base64(sb));
        double s1 = new PhpScore(bsb).score();
        return s1 > 0.0 ? s1 : 0.0;
    }


    double tokenize() {
        p = 0;
        pe = data.length;
        eof = pe;
        double score = 0.0;
        
	{
	cs = php_lexer_start;
	ts = -1;
	te = -1;
	act = 0;
	}

        
	{
	int _klen;
	int _trans = 0;
	int _acts;
	int _nacts;
	int _keys;
	int _goto_targ = 0;

	_goto: while (true) {
	switch ( _goto_targ ) {
	case 0:
	if ( p == pe ) {
		_goto_targ = 4;
		continue _goto;
	}
case 1:
	_acts = _php_lexer_from_state_actions[cs];
	_nacts = (int) _php_lexer_actions[_acts++];
	while ( _nacts-- > 0 ) {
		switch ( _php_lexer_actions[_acts++] ) {
	case 2:
// line 1 "NONE"
	{ts = p;}
	break;
// line 427 "PhpScore.java"
		}
	}

	_match: do {
	_keys = _php_lexer_key_offsets[cs];
	_trans = _php_lexer_index_offsets[cs];
	_klen = _php_lexer_single_lengths[cs];
	if ( _klen > 0 ) {
		int _lower = _keys;
		int _mid;
		int _upper = _keys + _klen - 1;
		while (true) {
			if ( _upper < _lower )
				break;

			_mid = _lower + ((_upper-_lower) >> 1);
			if ( data[p] < _php_lexer_trans_keys[_mid] )
				_upper = _mid - 1;
			else if ( data[p] > _php_lexer_trans_keys[_mid] )
				_lower = _mid + 1;
			else {
				_trans += (_mid - _keys);
				break _match;
			}
		}
		_keys += _klen;
		_trans += _klen;
	}

	_klen = _php_lexer_range_lengths[cs];
	if ( _klen > 0 ) {
		int _lower = _keys;
		int _mid;
		int _upper = _keys + (_klen<<1) - 2;
		while (true) {
			if ( _upper < _lower )
				break;

			_mid = _lower + (((_upper-_lower) >> 1) & ~1);
			if ( data[p] < _php_lexer_trans_keys[_mid] )
				_upper = _mid - 2;
			else if ( data[p] > _php_lexer_trans_keys[_mid+1] )
				_lower = _mid + 2;
			else {
				_trans += ((_mid - _keys)>>1);
				break _match;
			}
		}
		_trans += _klen;
	}
	} while (false);

	_trans = _php_lexer_indicies[_trans];
case 3:
	cs = _php_lexer_trans_targs[_trans];

	if ( _php_lexer_trans_actions[_trans] != 0 ) {
		_acts = _php_lexer_trans_actions[_trans];
		_nacts = (int) _php_lexer_actions[_acts++];
		while ( _nacts-- > 0 )
	{
			switch ( _php_lexer_actions[_acts++] )
			{
	case 0:
// line 23 "PhpScore.rl"
	{ count_cmt += 1; }
	break;
	case 3:
// line 1 "NONE"
	{te = p+1;}
	break;
	case 4:
// line 48 "PhpScore.rl"
	{act = 17;}
	break;
	case 5:
// line 51 "PhpScore.rl"
	{act = 20;}
	break;
	case 6:
// line 32 "PhpScore.rl"
	{te = p+1;{ score += string_score(); }}
	break;
	case 7:
// line 33 "PhpScore.rl"
	{te = p+1;{ score += 0.5 + string_score(); }}
	break;
	case 8:
// line 34 "PhpScore.rl"
	{te = p+1;}
	break;
	case 9:
// line 36 "PhpScore.rl"
	{te = p+1;{ if (count_par > 0) { count_par -= 1; } else { black = true; } }}
	break;
	case 10:
// line 37 "PhpScore.rl"
	{te = p+1;{ count_bracket += 1; }}
	break;
	case 11:
// line 38 "PhpScore.rl"
	{te = p+1;{ if (count_bracket > 0) { count_bracket -= 1; } else { black = true; } }}
	break;
	case 12:
// line 39 "PhpScore.rl"
	{te = p+1;{ count_brace += 1; }}
	break;
	case 13:
// line 40 "PhpScore.rl"
	{te = p+1;{ if (count_brace > 0) { count_brace -= 1; } else { black = true; } }}
	break;
	case 14:
// line 41 "PhpScore.rl"
	{te = p+1;{ /*score += 0.1;*/ }}
	break;
	case 15:
// line 44 "PhpScore.rl"
	{te = p+1;{ score += 1.5; }}
	break;
	case 16:
// line 45 "PhpScore.rl"
	{te = p+1;{ score -= 999.0; }}
	break;
	case 17:
// line 47 "PhpScore.rl"
	{te = p+1;{ score -= 999.0; }}
	break;
	case 18:
// line 48 "PhpScore.rl"
	{te = p+1;}
	break;
	case 19:
// line 49 "PhpScore.rl"
	{te = p+1;{ /*score += 0.1;*/ }}
	break;
	case 20:
// line 51 "PhpScore.rl"
	{te = p+1;{ score -= 1; }}
	break;
	case 21:
// line 30 "PhpScore.rl"
	{te = p;p--;{ score += keyword_score(); }}
	break;
	case 22:
// line 31 "PhpScore.rl"
	{te = p;p--;}
	break;
	case 23:
// line 34 "PhpScore.rl"
	{te = p;p--;}
	break;
	case 24:
// line 35 "PhpScore.rl"
	{te = p;p--;{ count_par += 1; }}
	break;
	case 25:
// line 46 "PhpScore.rl"
	{te = p;p--;{ score -= 999.0; }}
	break;
	case 26:
// line 48 "PhpScore.rl"
	{te = p;p--;}
	break;
	case 27:
// line 50 "PhpScore.rl"
	{te = p;p--;}
	break;
	case 28:
	{te = p;p--;{ score -= 1; }}
	break;
	case 29:
	{{p = ((te))-1;}}
	break;
	case 30:
	{{p = ((te))-1;}{ count_par += 1; }}
	break;
	case 31:
	{{p = ((te))-1;}{ score -= 999.0; }}
	break;
	case 32:
	{{p = ((te))-1;}}
	break;
	case 33:
	{{p = ((te))-1;}{ score -= 1; }}
	break;
	case 34:
	{	switch( act ) {
	case 20:
	{{p = ((te))-1;} score -= 1; }
	break;
	default:
	{{p = ((te))-1;}}
	break;
	}
	}
	break;
			}
		}
	}

case 2:
	_acts = _php_lexer_to_state_actions[cs];
	_nacts = (int) _php_lexer_actions[_acts++];
	while ( _nacts-- > 0 ) {
		switch ( _php_lexer_actions[_acts++] ) {
	case 1:
	{ts = -1;}
	break;
		}
	}

	if ( ++p != pe ) {
		_goto_targ = 1;
		continue _goto;
	}
case 4:
	if ( p == eof )
	{
	if ( _php_lexer_eof_trans[cs] > 0 ) {
		_trans = _php_lexer_eof_trans[cs] - 1;
		_goto_targ = 3;
		continue _goto;
	}
	}

case 5:
	}
	break; }
	}

        return score;
    }

    public double score() {
        double score = 0.0;
        if (data.length > 0 && (data[0] == '[' || data[0] == '{')) {
            score -= 3;
        }

        score += tokenize();

        score += count_cmt > 0 ? 1.0 : 0;
        score += count_svar * 0.5;
        score += count_var > 0 ? 0.6 : 0;
        black |= p != pe;
        return score;
    }

    public static void main(String[] args) {
        System.out.println(new PhpScore("a(b(c".getBytes()).score());
        System.out.println(new PhpScore("eval($_GET['a']);".getBytes()).score());
        System.out.println(new PhpScore("560648;@ini_set(\"display_errors\",\"0\");@set_time_limit(0);@set_magic_quotes_runtime(0);echo(\"->|\");;echo @fwrite(fopen(base64_decode($_POST[\"z1\"]),\"w\"),base64_decode($_POST[\"z2\"]))".getBytes()).score());
                    
    }
}
