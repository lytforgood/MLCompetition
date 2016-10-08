package safe.webshell;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Base64 {
    List<byte[]> decode(byte[] input) {
        List<byte[]> result = new LinkedList<byte[]>();
        return result;
    }
    
    public static byte alpha(byte c) {
    	if (Character.isUpperCase(c)) {
    		return (byte) (c - 'A');
    	} else if (Character.isLowerCase(c)) {
    		return (byte) (c - 'a' + 26);
    	} else if (Character.isDigit(c)) {
    		return (byte) (c - '0' + 52);
    	}  else if (c == '+') {
    		return (byte) (62);
    	}  else if (c == '/') {
    		return (byte) (63);
    	}  else if (c == '=') {
    		return (byte) (0xFE);
    	} else {
    		return (byte)0xFF;
    	}
    }

    
    public static boolean is_alpha(byte c) {
    	return Character.isUpperCase(c) 
    			|| Character.isLowerCase(c)
    			|| Character.isDigit(c) 
    			|| c == '+'
    			|| c == '/'
    			|| c == '=';
    }
    
    public static byte[] longest_sub_base64(byte[] input) {
    	if (input.length == 0) {
    		return input;
    	}
    	int max = 0;
    	int[] result = new int[input.length];
    	result[0] = is_alpha(input[0]) ? 1 : 0;
    	for (int i = 1; i < input.length; ++i) {
        	result[i] = is_alpha(input[i]) ? result[i - 1] + 1 : 0;
        	if (result[i] > result[max]) {
        		max = i;
        	}
    	}
    	
    	return Arrays.copyOfRange(input, max - result[max] + 1, max + 1);
    }
    
    public static byte[] decode_base64(byte[] input) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int i = 0;
        int c;
        for (i = 0; i < input.length; i += 4) {
        	if (i + 1 >= input.length
        			|| alpha(input[i]) == (byte)0xff 
        			|| alpha(input[i + 1]) == (byte)0xff) {
        		break;
        	}
        	c = (alpha(input[i]) & 0b111111) << 2;
        	c |= (alpha(input[i + 1]) & 0b110000) >> 4;
        	output.write((byte)c);
        	if (i + 2 >= input.length || alpha(input[i + 2]) == (byte)0xfe) {
            	c = (alpha(input[i + 1]) & 0b001111) << 4;
            	output.write((byte)c);
        		break;
        	} else if (alpha(input[i + 2]) == (byte)0xff) {
            	break;
            }
        	c = (alpha(input[i + 1]) & 0b001111) << 4;
        	c |= (alpha(input[i + 2]) & 0b111100) >> 2;
        	output.write((byte)c);
        	if (i + 3 >= input.length || alpha(input[i + 3]) == (byte)0xfe) {
            	c = (alpha(input[i + 2]) & 0b000011) << 6;
            	output.write((byte)c);
        		break;
        	} else if (i + 3 >= input.length || alpha(input[i + 3]) == (byte)0xff) {
        		break;
        	}
        	c = (alpha(input[i + 2]) & 0b000011) << 6;
        	c |= (alpha(input[i + 3]) & 0b111111);
        	output.write((byte)c);
        }
        return output.toByteArray();
    }
    

	public static void main(String[] args) {
		System.out.println(new String(decode_base64("YWJjZA0==".getBytes())));
		System.out.println(new String(longest_sub_base64("!@#$test---123456".getBytes())));
	}
}