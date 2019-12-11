package cryptography;

import java.util.Arrays;

public class DES {

	   
	    private static final byte[] IP = {
	            58, 50, 42, 34, 26, 18, 10, 2,
	            60, 52, 44, 36, 28, 20, 12, 4,
	            62, 54, 46, 38, 30, 22, 14, 6,
	            64, 56, 48, 40, 32, 24, 16, 8,
	            57, 49, 41, 33, 25, 17, 9, 1,
	            59, 51, 43, 35, 27, 19, 11, 3,
	            61, 53, 45, 37, 29, 21, 13, 5,
	            63, 55, 47, 39, 31, 23, 15, 7
	    };

	
	    private static final byte[] FP = {
	            40, 8, 48, 16, 56, 24, 64, 32,
	            39, 7, 47, 15, 55, 23, 63, 31,
	            38, 6, 46, 14, 54, 22, 62, 30,
	            37, 5, 45, 13, 53, 21, 61, 29,
	            36, 4, 44, 12, 52, 20, 60, 28,
	            35, 3, 43, 11, 51, 19, 59, 27,
	            34, 2, 42, 10, 50, 18, 58, 26,
	            33, 1, 41, 9, 49, 17, 57, 25
	    };


	    private static final byte[] E = {
	            32, 1, 2, 3, 4, 5,
	            4, 5, 6, 7, 8, 9,
	            8, 9, 10, 11, 12, 13,
	            12, 13, 14, 15, 16, 17,
	            16, 17, 18, 19, 20, 21,
	            20, 21, 22, 23, 24, 25,
	            24, 25, 26, 27, 28, 29,
	            28, 29, 30, 31, 32, 1
	    };

	   
	    private static final byte[][] S = {{
	            14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7,
	            0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8,
	            4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0,
	            15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13
	    }, {
	            15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10,
	            3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5,
	            0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15,
	            13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9
	    }, {
	            10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8,
	            13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1,
	            13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7,
	            1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12
	    }, {
	            7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15,
	            13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9,
	            10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4,
	            3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14
	    }, {
	            2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9,
	            14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6,
	            4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14,
	            11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3
	    }, {
	            12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11,
	            10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8,
	            9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6,
	            4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13
	    }, {
	            4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1,
	            13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6,
	            1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2,
	            6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12
	    }, {
	            13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7,
	            1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2,
	            7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8,
	            2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11
	    }};

	
	    private static final byte[] P = {
	            16, 7, 20, 21,
	            29, 12, 28, 17,
	            1, 15, 23, 26,
	            5, 18, 31, 10,
	            2, 8, 24, 14,
	            32, 27, 3, 9,
	            19, 13, 30, 6,
	            22, 11, 4, 25
	    };

	   
	    private static final byte[] PC1 = {
	            57, 49, 41, 33, 25, 17, 9,
	            1, 58, 50, 42, 34, 26, 18,
	            10, 2, 59, 51, 43, 35, 27,
	            19, 11, 3, 60, 52, 44, 36,
	            63, 55, 47, 39, 31, 23, 15,
	            7, 62, 54, 46, 38, 30, 22,
	            14, 6, 61, 53, 45, 37, 29,
	            21, 13, 5, 28, 20, 12, 4
	    };

	  
	    private static final byte[] PC2 = {
	            14, 17, 11, 24, 1, 5,
	            3, 28, 15, 6, 21, 10,
	            23, 19, 12, 4, 26, 8,
	            16, 7, 27, 20, 13, 2,
	            41, 52, 31, 37, 47, 55,
	            30, 40, 51, 45, 33, 48,
	            44, 49, 39, 56, 34, 53,
	            46, 42, 50, 36, 29, 32
	    };

	   
	    private static final byte[] rotations = {
	            1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
	    };

	    private static long IP(long src) {
	        return permute(IP, src, 64);
	    } 

	    private static long FP(long src) {
	        return permute(FP, src, 64);
	    } 
	    /**
	     * 进行扩展变化
	     * @param src 32bit
	     * @return
	     */
	    private static long E(int src) {
	        return permute(E, src & 0xFFFFFFFFL, 32);
	    } 
	    /**
	     * 进行P变换
	     * @param src 32bit
	     * @return
	     */
	    private static int P(int src) {
	        return (int) permute(P, src & 0xFFFFFFFFL, 32);
	    } 

	    private static long PC1(long src) {
	        return permute(PC1, src, 64);
	    } 

	    private static long PC2(long src) {
	        return permute(PC2, src, 56);
	    } 
	    /**
	     * 循环右移
	     * @param table
	     * @param src
	     * @param srcWidth
	     * @return
	     */
	    private static long permute(byte[] table, long src, int srcWidth) {
	        long result = 0;
	        for (int i = 0; i < table.length; i++) {
	            int pos = srcWidth - table[i];
	            result = (result << 1) | (src >> pos & 1);
	        }
	        return result;
	    }
	
	    private static byte S(int boxNumber, byte src) {
	        src = (byte) (src & 0x20 | ((src & 1) << 4) | ((src & 0x1E) >> 1));
	        return S[boxNumber - 1][src];
	    }


	    public static long getLongFromBytes(byte[] bytes, int offset) {
	        long l = 0;
	        for (int i = 0; i < 8; i++) {
	            byte value;
	            if ((offset + i) < bytes.length) {
	                value = bytes[offset + i];
	            }
	            else {
	                value = 0;
	            }
	            l = l << 8 | (value & 0xFFL);
	        }
	        return l;
	    }
	
	    private static void getBytesFromLong(byte[] bytes, int offset, long l) {
	        for (int i = 7; i > -1; i--) {
	            if ((offset + i) < bytes.length) {
	                bytes[offset + i] = (byte) (l & 0xFF);
	                
	            } 
	            l = l >> 8;
	        }
	    }
	    /**
	     * 
	     * @param r 密文的右半部分
	     * @param key DES密钥
	     * @return feistel计算的返回结果
	     */
	    private static int f(int r, long key) {
	        long e = E(r); //E变换
	        long x = e ^ key; //与该轮的密钥进行异或
	        int dst = 0;//存储变换的结果
	        for (int i = 0; i < 8; i++) {
	            dst >>>= 4;
	            int s = S(8 - i, (byte) (x & 0x3F));//对异或的结果x的每6位进行一次S盒变换
	            dst |= s << 28;//存储变换的结果
	            x >>= 6;//对下6位进行变换
	        }
	        return P(dst);//最终结果进行一次P变换
	    }

	
	    private static long[] createSubkeys(long key) {
	        long subkeys[] = new long[16];//扩展为16轮，每轮48位的密钥

	        key = PC1(key);//丢弃密钥的低8位
        
	        int high = (int) (key >> 28);//
	        int low = (int) (key & 0x0FFFFFFF);

	        for (int i = 0; i < 16; i++) {
	            if (rotations[i] == 1) {//按照表进行不同的移位操作，此处为循环左移1位
	                high = ((high << 1) & 0x0FFFFFFF) | (high >> 27);
	                low = ((low << 1) & 0x0FFFFFFF) | (low >> 27);
	            } 
	            else if (rotations[i] == 2) {//按照表进行不同的移位操作，此处为循环左移2位
	                high = ((high << 2) & 0x0FFFFFFF) | (high >> 26);
	                low = ((low << 2) & 0x0FFFFFFF) | (low >> 26);
	            }
	            
	            long result = (high & 0xFFFFFFFFL) << 28 | (low & 0xFFFFFFFFL);//拼接左右两部分
	            subkeys[i] = PC2(result);//再进行一次压缩变换
	        }

	        return subkeys;
	    }
               
	    /**
	     * 对一个64位的块进行加密， 密钥64位
	     * @param m   需要加密的明文
	     * @param key 密文
	     * @return
	     */
	    private static long encryptBlock(long m, long key) {
	        long subkeys[] = createSubkeys(key);//密钥扩展 
	        //初始置换
	        long ip = IP(m); // 对明文进行P置换
	        int l = (int) (ip >> 32); //L
	        int r = (int) (ip & 0xFFFFFFFFL); //H
	        //进行16轮变换
	        for (int i = 0; i < 16; i++) {
	            int previous_l = l;//前一轮的左半部分
	            l = r;
	            r = previous_l ^ f(r, subkeys[i]);//右半部分进行F轮函数变换后的结果与前一轮的结果异或
	        }
	        
	        long rl = (r & 0xFFFFFFFFL) << 32 | (l & 0xFFFFFFFFL);//将左右两部分拼接

	        long fp = FP(rl);//逆P置换

	        return fp;
	    }

	    private static long decryptBlock(long c, long key) {
	        long[] subkeys = createSubkeys(key);

	        long ip = IP(c);

	        int l = (int) (ip >> 32);
	        int r = (int) (ip & 0xFFFFFFFFL);

	        for (int i = 15; i > -1; i--) {
	            int previous_l = l;
	            l = r;
	            r = previous_l ^ f(r, subkeys[i]);
	        }

	        long rl = (r & 0xFFFFFFFFL) << 32 | (l & 0xFFFFFFFFL);
	        long fp = FP(rl);

	        return fp;
	    }

	   private static void encryptBlock(byte[] message, int messageOffset, byte[] ciphertext, int ciphertextOffset, byte[] key) {
	        long m = getLongFromBytes(message, messageOffset);
	        long k = getLongFromBytes(key, 0);
	        long c = encryptBlock(m, k);
	        getBytesFromLong(ciphertext, ciphertextOffset, c);
	    }

	    private static void decryptBlock(byte[] ciphertext, int ciphertextOffset, byte[] message, int messageOffset, byte[] key) {
	        long c = getLongFromBytes(ciphertext, ciphertextOffset);
	        long k = getLongFromBytes(key, 0);
	        long m = decryptBlock(c, k);
	        getBytesFromLong(message, messageOffset, m);
	    }
	    
	
	    public static byte[] encryptECB(byte[] message, byte[] key) {
	    	byte[] cipherText;
	    	if (message.length % 8 != 0)cipherText = new byte[message.length/8 * 8 + 8];
	    	else cipherText = new byte[message.length];

	        for (int i = 0; i < message.length; i += 8) {
	            encryptBlock(message, i, cipherText, i, key);
	        }

	        return cipherText;
	    }
	    
	    public static byte[] decryptECB(byte[] ciphertext, byte[] key) {
	        byte[] message = new byte[ciphertext.length];

	        for (int i = 0; i < ciphertext.length; i += 8) {
	            decryptBlock(ciphertext, i, message, i, key);
	        }

	        return trim(message);
	    }


	    private static long IV;

	    public static long getIv() {
	        return IV;
	    }

	    public static void setIv(long iv) {
	        IV = iv;
	    }

	    public static byte[] encryptCBC(byte[] message, byte[] key) {
	    	byte[] cipherText;
	    	if (message.length % 8 != 0)cipherText = new byte[message.length/8 * 8 + 8];
	    	else cipherText = new byte[message.length];
	        long k = getLongFromBytes(key, 0);
	        long previousCipherBlock = IV;

	        for (int i = 0; i < message.length; i += 8) {
	            long messageBlock = getLongFromBytes(message, i);
	            //前一个分组的密文与当前分组进行异或
	            long cipherBlock = encryptBlock(messageBlock ^ previousCipherBlock, k);

	            getBytesFromLong(cipherText, i, cipherBlock);

	            previousCipherBlock = cipherBlock;
	        }
	        
	        return cipherText;
	    }

	    public static byte[] decryptCBC(byte[] ciphertext, byte[] key) {
	        byte[] message = new byte[ciphertext.length];
	        long k = getLongFromBytes(key, 0);
	        long previousCipherBlock = IV;

	        for (int i = 0; i < ciphertext.length; i += 8) {
	            long cipherBlock = getLongFromBytes(ciphertext, i);

	            long messageBlock = decryptBlock(cipherBlock, k);
	            messageBlock = messageBlock ^ previousCipherBlock;

	            getBytesFromLong(message, i, messageBlock);

	            previousCipherBlock = cipherBlock;
	        }
	        return trim(message);
	    }
	    
	    public static byte[] encryptCFB(byte[] message, byte[] key) {
	    	byte[] cipherText = new byte[message.length];
	    	long I = IV;
	    	long k = getLongFromBytes(key, 0);
	    	for (int i = 0; i < message.length; i++) {
	    		//前一轮的明文与替换当前的IV向量的第8位，加密结果再与明文分组异或
	    		cipherText[i] = (byte) ((encryptBlock(I, k) >>> 56) ^ message[i]);
	    		I = cipherText[i] ^ (I << 8);
	    	}
	    	return cipherText;
	    }
	    
	    public static byte[] decryptCFB(byte[] message, byte[] key) {
	    	byte[] cipherText = new byte[message.length];
	    	long I = IV;
	    	long k = getLongFromBytes(key, 0);
	    	for (int i = 0; i < message.length; i++) {
	    		//前一轮的明文与替换当前的IV向量的第8位，加密结果再与明文分组异或
	    		cipherText[i] = (byte) ((encryptBlock(I, k) >>> 56) ^ message[i]);
	    		I = message[i] ^ (I << 8);
	    	}
	    	return cipherText;
	    }
	    
	 
	    
	    public static byte[] encryptOFB(byte[] message, byte[] key, long IV) {
	    	byte[] cipherText;
	    	if (message.length % 8 != 0)cipherText = new byte[message.length/8 * 8 + 8];
	    	else cipherText = new byte[message.length];
	    	long O = IV;
	    	long k = getLongFromBytes(key, 0);
	    	for (int i = 0; i < message.length; i += 8) {
	    		O = encryptBlock(O, k);
	    		getBytesFromLong(cipherText, i, O ^ getLongFromBytes(message, i));
	    	}
	    	return trim(cipherText);
	    }
	    
	    public static byte[] decryptOFB(byte[] message, byte[] key, long IV) {
	    	return encryptOFB(message, key, IV);
	    }
	    
	 
	    public static byte[] encryptCTR(byte[] message, byte[] key, long intialCounter) {
	    	long cnt = intialCounter;
	    	byte[] cipherText = new byte[message.length];
	    	long k = getLongFromBytes(key, 0);
	    	
	    	for (int i = 0; i < cipherText.length; i += 8) {
	    		getBytesFromLong(cipherText, i, encryptBlock(cnt, k) ^ getLongFromBytes(message, i));
	    		cnt++;
	    	}
	    	
	    	return cipherText;
	    }
	    
	    public static byte[] decryptCTR(byte[] message, byte[] key, long intialCounter) {
	    	return encryptCTR(message, key, intialCounter);
	    }
	    
	    private static byte[] trim(byte[] message) {
	    	int last = message.length - 1;
	        while (message[last] == 0) {
	        	last--;
	        }
	        return Arrays.copyOfRange(message, 0, last + 1);
	    }
	    //////////////////////////////////////////////////
		public static String hex(byte[] bytes) {
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < bytes.length; i++) {
	            sb.append(String.format("%02X ", bytes[i]));
	        }
	        return sb.toString();
	    }
		private static byte charToByte(char c) {
			if(Character.isDigit(c))return (byte)(c - '0');
			else return (byte) (c - 'a' + 10) ;
		}
		public static byte[] convertStringToBytes(String str){
			 byte[] bytes = new byte[str.length() / 2];
			 
			 for (int i = 0; i < bytes.length; i++) {
				 bytes[i] = 0;
				 bytes[i] ^= charToByte(str.charAt(2*i)) << 4 ;
				 bytes[i] ^=  charToByte(str.charAt(2*i+1));
			 }
			 
			 return bytes;
		}
	    public static void main(String[] args) {
	    	String oriText = "你好World, 123你好，world123你好，world123你好，world123你好，world123";
	    	
	    	byte[] key = "password      ".getBytes();
	    	byte[] test = oriText.getBytes();

	    	
	    	byte[] result = encryptECB(test, key);
	    	byte[] decResult = decryptECB(result, key);
	    	System.out.println("EncryptionECB result:  " + new String(result));
	    	
	    	System.out.println("DecryptionECB result:  " + new String(decResult));
	    	/*
	    	byte[] result = encryptCFB(test, key);
	    	byte[] decResult = decryptCFB(result, key);
	    	System.out.println("EncryptionCFB result:  " + new String(result));
	    	
	    	System.out.println("DecryptionCFB result:  " + new String(decResult));
	    	
	    	//byte[] result = encryptOFB(test, key, 0);
	    //	byte[] decResult = decryptOFB(result, key, 0);
	    	System.out.println("EncryptionOFB result:  " + test.length);
	    	
	    	System.out.println("DecryptionOFB result:  " + new String(decResult)); 
	    	
	    	result = encryptCTR(test, key, 0);
	    	decResult = decryptCTR(result, key, 0);
	    	System.out.println("EncryptionOFB result:  " + test.length);
	    	
	    	System.out.println("DecryptionOFB result:  " + new String(decResult)); 	*/
	    }

}
