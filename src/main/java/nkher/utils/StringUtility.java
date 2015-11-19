package nkher.utils;

/***
 * A String API that provides useful functions for String compression.
 * 
 * @author nameshkher
 *
 */
public class StringUtility {
	
	/***
	 * An efficient method for compressing a String using Run Length Encoding.
	 * It optimizes for single char sequences by only appending the char and not the count (1). 
	 * Internally uses a StringBuilder for forming the compression String.
	 * 
	 * @param str
	 * @return
	 */
	public static String runLengthEncoding(String str) {
		
		if (null == str) return null;
		int n = str.length();
		if (n == 0) return str;
		
		int sizeAfterCompression = compressionSize(str);
		
		/** Return the String if its compression length is greater than its original length */
		if (sizeAfterCompression > n) return str;
		
		int count = 1;
		char prev = str.charAt(0), ch;
		StringBuilder sb = new StringBuilder();
		
		for (int i=1; i<n; i++) {
			ch = str.charAt(i);
			if (ch == prev) {
				count++;
			}
			else {
				sb.append(prev);
				if (count > 1) sb.append(count);
				prev = ch;
				count = 1;
			}
		}
		
		/** Appending the last character */
		sb.append(prev);
		if (count > 1) sb.append(count);
		
		return sb.toString();
	}
	
	
	/***
	 * Calculates and returns the size of the compressed String without actually performing the compression.
	 * This helps in determining whether or not compression should be performed. The Algorithm is to count the 
	 * number of character sequences and every character sequence has another number after it which gives its count.
	 * The total length is the compression size. For example if str = 'aabeedbbbesssff' then the size after compression
	 * returned by the following function would be 13. This method optimizes for character sequences occurring only once
	 * in a consecutive sequence by not appending its occurrence. 
	 * 
	 * @param str
	 * @return
	 */
	private static int compressionSize(String str) {
		
		char prev = str.charAt(0);
		int compSize = 0;
		int count = 1;
		
		char ch;
		for (int i=1; i<str.length(); i++) {
			ch = str.charAt(i);
			if (ch == prev) {
				count++;
			}
			else {
				compSize += (count == 1)  ? 1 : (1 + String.valueOf(count).length());
				prev = ch;
				count = 1;
			}
		}
		
		compSize += (count == 1)  ? 1 : (1 + String.valueOf(count).length());
		return compSize;
	}
	
	/***
	 * Returns the de-compressed String for the passed String which has been previously compressed 
	 * using the runLengthEncoding method of this class. This decompress function is guaranteed to 
	 * work appropriately if the String is compressed by the above compressing function.
	 * 
	 * @param str
	 * @return
	 */
	public static String decompress(String str) {
		
		if (null == str) return null;
		int n = str.length();
		if (n == 0) return str;
		
		StringBuilder sb = new StringBuilder();
		
		for (int i=n-1; i>=0 ; i--) {
			System.out.println(str.charAt(i));
		}
		
		return null;
	}
	
	public static void main(String args[]) {
		
		String str = "aabeedbbbesssff";
		System.out.println(runLengthEncoding(str));
		
		System.out.println(decompress("a2be2db3es3f2"));
	}
}
