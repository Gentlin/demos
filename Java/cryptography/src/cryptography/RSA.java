package cryptography;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.lang.System;
public class RSA {
	private BigInteger p = new BigInteger("1874113084280153735306658132040384611093897935132757017046995662677351096773572253280762088015535511593");
	private BigInteger q = new BigInteger("601910294070546369276551615877564447613790725357839882704111695696928780728208299489784763935240286491");
	private short pLength = 1024;
	private short qLength = 1024;
	private BigInteger n = p.multiply(q);
	private BigInteger fi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
	private BigInteger e = new BigInteger("58997");
	private short eLength = 16;
	private BigInteger d = e.modInverse(fi);
	private SecureRandom rand = new SecureRandom();
	private final int BLOCK_SIZE = 16;
	public BigInteger getPrimeP() {
		return p;
	}
	public BigInteger getPrimeQ() {
		return q;
	}
	public boolean setPublicKey(BigInteger candidate) {
		if(candidate.compareTo(fi) < 0 && candidate.gcd(fi).equals(BigInteger.ONE)) {
			e = new BigInteger(candidate.toByteArray());
			return true;
		}
		return false;
	}
	public BigInteger getPublicKey() {
		return e;
	}
	public BigInteger getPrivateKey() {
		return d;
	}
	public boolean generateKey() {
		BigInteger candidateP = BigInteger.probablePrime(pLength, rand);
		BigInteger candidateQ = BigInteger.probablePrime(qLength, rand);
		
		BigInteger candidateFi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		for (BigInteger i = BigInteger.probablePrime(eLength, rand); i.compareTo(fi) < 0; i = i.nextProbablePrime()) {
			if(i.gcd(candidateFi).equals(BigInteger.ONE)) {
				e = new BigInteger(i.toByteArray());
				d = e.modInverse(fi);
				n = p.multiply(q);
				fi = candidateFi;
				p = candidateP;
				q = candidateQ;
				return true;
			}
		}
		return false;
	}
	private BigInteger fastMod(BigInteger a, BigInteger exp, BigInteger n) {
		BigInteger f = new BigInteger("1");
		for (int i = exp.bitLength() - 1 ; i > -1; i--) {
			f = f.multiply(f).mod(n);
			if(exp.testBit(i)) {
				f = f.multiply(a).mod(n);
			}
		}
		return f;
	}
	public ArrayList<BigInteger> encrypt(byte[] text) {
		ArrayList<BigInteger> cipher = new ArrayList<BigInteger>();
		//16个字节分为一块
		for (int i = 0; i < text.length; i+=this.BLOCK_SIZE) {		
			if(i + this.BLOCK_SIZE <= text.length) {
				byte[] block = new byte[this.BLOCK_SIZE + 1];
				System.arraycopy(text, i, block, 1, this.BLOCK_SIZE);
				block[0] = '1';
				cipher.add(fastMod(new BigInteger(block), e, n));
			}
			else {
				int len = text.length - i + 1;
				byte[] block = new byte[len];
				System.arraycopy(text, i, block, 1, len - 1);
				block[0] = '1';
				cipher.add(fastMod(new BigInteger(block), e, n));
			}
		}
		return cipher;
	}
	public byte[] decrypt(ArrayList<BigInteger> cipher) {
	    byte[] plainText = null;
		int last = 0;
	    Iterator<BigInteger> iter = cipher.iterator();
		while(iter.hasNext()) {
			BigInteger block = (BigInteger)iter.next();
			byte[] bytes = fastMod(block, d, n).toByteArray();
			bytes = Arrays.copyOfRange(bytes, 1, bytes.length);
			if(plainText == null) {
				plainText = new byte[bytes.length];
			}
			else {
				plainText = Arrays.copyOf(plainText, plainText.length + bytes.length);
			}
			for (int i = 0; i < bytes.length; i++) {
				plainText[last] = bytes[i];
				last++;
			}
		}		
		return plainText;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RSA rsa = new RSA();
		BigInteger num = new BigInteger("123");
		System.out.println(num.toString());
		String testStr = "你好，world123456789";
		rsa.generateKey();
		ArrayList<BigInteger> cipher = rsa.encrypt(testStr.getBytes());
		for(BigInteger i : cipher) {
			System.out.println(i);
		}
		byte[] decryptText = rsa.decrypt(cipher);
		System.out.println(new String(decryptText));
	}

}
