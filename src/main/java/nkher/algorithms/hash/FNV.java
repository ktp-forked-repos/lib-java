package nkher.algorithms.hash;

import java.util.function.Function;

/***
 * Reference : 
 * 1. https://en.wikipedia.org/wiki/Fowler%E2%80%93Noll%E2%80%93Vo_hash_function
 * 2. http://www.isthe.com/chongo/tech/comp/fnv/
 * 3. http://www.java2s.com/Code/Java/Development-Class/FNVHash.htm
 * 
 * @author nameshkher
 *
 */
public class FNV extends HashMethod implements Function<byte[], Integer>{
	
	private static final int FNVPRIME_32BIT = 0x01000193;
	private static final long FNVPRIME_64BIT = 0x100000001b3L;

	private static final int FNVOFFSET_32BIT = 0x811C9DC5;
	private static final long FNVOFFSET_64BIT = 0xcbf29ce484222325L;
	
	public FNV() {
		setHashFunctionName("fnv");
	}
	
	/***
	 * We are actually not using seed here
	 */
	@Override
	public Integer apply(byte[] data) {
		return hash_32(data);
	}
	
	public static int hash_32(byte[] bytes) {
		int hash = FNVOFFSET_32BIT;
		for (int i=0; i<bytes.length; i++) {
			hash *= FNVPRIME_32BIT;
			hash ^= bytes[i];
		}
		return hash;
	}
	
	public static long hash_64(byte[] bytes) {
		long hash = FNVOFFSET_64BIT;
		for (int i=0; i<bytes.length; i++) {
			hash *= FNVPRIME_64BIT;
			hash ^= bytes[i];
		}
		return hash;
	}
	
	public static int hash_32(String str) {
		int hash = FNVOFFSET_32BIT;
		for (int i=0; i<str.length(); i++) {
			hash *= FNVPRIME_32BIT;
			hash ^= str.charAt(i);
		}
		return hash;
	}
	
	public static long hash_64(String str) {
		long hash = FNVOFFSET_64BIT;
		for (int i=0; i<str.length(); i++) {
			hash *= FNVPRIME_64BIT;
			hash ^= str.charAt(i);
		}
		return hash;
	}
}
