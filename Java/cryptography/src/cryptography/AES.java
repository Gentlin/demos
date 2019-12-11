package cryptography;

import java.util.Arrays;

public class AES {
		private static short[] SBox = {
				0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76,
	        0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0,
	        0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15,
	        0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75,
	        0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84,
	        0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf,
	        0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8,
	        0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2,
	        0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73,
	        0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb,
	        0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79,
	        0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08,
	        0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a,
	        0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e,
	        0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf,
	        0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16
	        };
		
	    private static short[] invSBox = {
	        0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb,
	        0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb,
	        0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e,
	        0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25,
	        0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92,
	        0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84,
	        0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06,
	        0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b,
	        0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73,
	        0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e,
	        0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b,
	        0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4,
	        0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f,
	        0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef,
	        0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61,
	        0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d
	        };
	    private static byte[] RC = new byte[] {0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, (byte)0x80, 0x1B, 0x36};
	    
	    private static int getWordFromBytes(byte[] bytes, int offset) {
	    	int word = 0;
	    	for(int i = 0; i < 4; i++) {
	    		if(i + offset< bytes.length) {
	    			word <<= 8;
	    			word |= bytes[i + offset] & 0xFF;
	    		}
	    		else break;
	    	}
	    	return word;
	    }
	    
	    private static void getBytesFromWord(byte[] src, int offset, int word) {
	    	for(int i = 3; i > -1; i--) {
	    		if(offset + i < src.length)
	    			src[offset + i] = (byte) (word & 0xFF);
	    		word >>>= 8;
	    	}
	    }
	    
	    private static void rowShiftLeft(byte[] bytes, int begin, int end) {
	    	byte temp = bytes[begin];
	    
	    	for (int i = begin; i < end; i++) {
	    		bytes[i] = bytes[i + 1];
	    	}
	    	
	    	bytes[end] = temp; 
	    }
	    
	    private static void rowShiftRight(byte[] bytes, int begin, int end) {
	    	byte temp = bytes[end];
	    
	    	for (int i = end - 1; i >=begin; i--) {
	    		bytes[i + 1] = bytes[i];
	    	}
	    	
	    	bytes[begin] = temp; 
	    }
	    private static byte S(byte b) {
	    	byte row = (byte) ((b >>> 4)& 0x0F) ;
	    	byte col = (byte) (b & 0x0F);
	    	return (byte)SBox[row * 16 + col];
	    }
	    
	    private static byte invS(byte b) {
	    	byte row = (byte) ((b >>> 4)& 0x0F );
	    	byte col = (byte) (b & 0x0F);
	    	return (byte)invSBox[row * 16 + col];
	    }
	    
	    private static int g(int word, int round) { 
	    	byte[] b = new byte[4];
	    	getBytesFromWord(b, 0, word);
	    	
	    	rowShiftLeft(b, 0, 3);
	    	
	    	for (int i = 0; i < 4; i++) {
	    		b[i] = S(b[i]);//进行S盒替换
	    	}

	    	b[0] ^= RC[round - 1];//根据RC表进行移位
	  	
	    	return getWordFromBytes(b, 0);
	    }
	    
	    private static byte[] expandKey(byte[] key) {
	    	byte[] keys = new byte[176];  	
	    	int[] w = new int[4];
	    	
	    	for (int i = 0; i < 4; i++) {
	    		//将一列合并为1个字
	    		w[i] = getWordFromBytes(new byte[] {key[i*4], key[i*4 + 1], key[i*4 + 2], key[4*i + 3]}, 0);
	    	}
	    	
	    	for (int i = 0; i < 4; i++) {
	    		getBytesFromWord(keys, i * 4, w[i]);
	    	}
	    	transpose(keys, 0);//将密钥转为列存储
	    	for (int i = 1; i < 11; i++) {	    		
	    			w[0] =   w[0] ^ g(w[3], i);//函数g
	    			w[1] = w[0] ^ w[1];
	    			w[2] = w[1] ^ w[2];
	    			w[3] = w[2] ^ w[3];
	    			
	    			for(int j = 0; j < 4; j++) {
	    				getBytesFromWord(keys, i * 16 + j * 4, w[j]);
	    			}
	    			transpose(keys, 16 * i);
	    	}
	    	
	    	return keys;
	    }
	    
	    private static void addRoundKey(byte[] cipherText, int offset, byte[] keys, int round) {
	    	int roundOffset = round * 16;
	    	for(int i = 0; i < 16; i++) {
	    		cipherText[i + offset] ^= keys[i + roundOffset];
	    	}
	    }
	    
	    private static void subtitueBytes(byte[] cipherText, int offset) {
	    	for (int i = 0; i < 16; i++) {
	    		cipherText[i + offset] = S(cipherText[i + offset]);
	    	}
	    }
	    
	    private static void invSubtitueBytes(byte[] cipherText, int offset) {
	    	for (int i = 0; i < 16; i++) {
	    		cipherText[i + offset] = invS(cipherText[i + offset]);
	    	}
	    }
	    private static byte GFMul2(byte b) {
	    	byte result = b;
	    	result <<= 1;
	    	if((b & 0x80) == 0x80) {
	    		result ^= 0b00011011;
	    	}
	    	return result;
	    }
	    private static byte GFMul3(byte b) {
	    	return (byte) (GFMul2(b) ^ b);
	    }
	    private static void mixCol(byte[] cipherText, int offset) {
	    	for (int i = 0; i < 4; i++) {
	    		//2 3 1 1
	    		byte s0 = (byte) (GFMul2(cipherText[i + offset]) ^ GFMul3(cipherText[i + 4 + offset])
	    				                 ^ cipherText[i + 8 + offset] ^ cipherText[i + 12 + offset]);
	    		//1 2 3 1
	    		byte s1 = (byte)(cipherText[i + offset]  ^ GFMul2(cipherText[i + 4 + offset])
		                 ^ GFMul3(cipherText[i + 8 + offset]) ^ cipherText[i + 12 + offset]);
	    		// 1 1 2 3
	    		byte s2 = (byte)(cipherText[i + offset]  ^ (cipherText[i + 4 + offset])
		                 ^  GFMul2(cipherText[i + 8 + offset]) ^ GFMul3(cipherText[i + 12 + offset]));
	    		//3 1 1 2
	    		byte s3 = (byte) (( GFMul3(cipherText[i + offset])) ^ (cipherText[i + 4 + offset]) 
		                 		^ cipherText[i + 8 + offset] ^ GFMul2(cipherText[i + 12 + offset]));
	    		 
	    		 cipherText[i + offset] = s0;
	    		 cipherText[i + 4 + offset] = s1;
	    		 cipherText[i + 8 + offset] = s2;
	    		 cipherText[i + 12 + offset] = s3;
	    	}
	    }
	    private static byte GFMul(byte b, int num) {
	    	byte result = 0;
	    	for (int i = 0; i <= 3; i++) {
	    		if((num & 1) == 1) {
	    			byte temp = b;
	    			for(int j = 1; j <= i; j++)
	    				temp = GFMul2(temp);
	    			result ^= temp;
	    		}
	    		num >>>= 1;
	    	}
	    	return result;
	    }
	    private static void invMixCol(byte[] cipherText, int offset) {
	    	for (int i = 0; i < 4; i++) {
	    		//0E 0B 0D 09
	    		byte s0 = (byte) (GFMul(cipherText[i + offset], 0x0E) ^ GFMul(cipherText[i + 4 + offset] , 0x0B) 
	    				                 ^ GFMul(cipherText[i + 8 + offset], 0x0D) ^ GFMul(cipherText[i + 12 + offset] , 0x09));
	    		//09 0E 0B 0D
	    		byte s1 = (byte) (GFMul(cipherText[i + offset], 0x09) ^ GFMul(cipherText[i + 4 + offset], 0x0E) 
		                 				^ GFMul(cipherText[i + 8 + offset], 0x0B) ^ GFMul(cipherText[i + 12 + offset], 0x0D));
	    		// 0D 09 OE OB
	    		byte s2 = (byte) (GFMul(cipherText[i + offset], 0x0D) ^ GFMul(cipherText[i + 4 + offset], 0x09) 
	    									^ GFMul(cipherText[i + 8 + offset], 0x0E) ^ GFMul(cipherText[i + 12 + offset], 0x0B));
	    		//0B 0D 09 0E
	    		byte s3 = (byte) (GFMul(cipherText[i + offset], 0x0B) ^ GFMul(cipherText[i + 4 + offset], 0x0D) 
											^ GFMul(cipherText[i + 8 + offset], 0x09) ^ GFMul(cipherText[i + 12 + offset], 0x0E));
	    	  
	    		 cipherText[i + offset] = s0;
	    		 cipherText[i + 4 + offset] = s1;
	    		 cipherText[i + 8 + offset] = s2;
	    		 cipherText[i + 12 + offset] = s3;
	    	}
	    }
	    private  static void swap(byte[] array, int a, int b) {
	    	array[a] = (byte) (array[a] ^ array[b]);
	    	array[b] = (byte) (array[a] ^ array[b]);
	    	array[a] = (byte) (array[a] ^ array[b]);
	    }
	    
	    private static void transpose(byte[] block, int offset) {
	           for (int i = 0; i < 4; i++) {
	        	   for (int j = i + 1; j < 4; j++) {
	        		   swap(block, 4 * i + j + offset, 4 * j + i + offset);
	        	   }
	           }
	    }
	    
	    private static void encryptBlock(byte[] cipherText, int offset, byte[] keys) {
	    	transpose(cipherText, offset);//为了与课本相同，将行存储转化为列存储
	    	
 	    	addRoundKey(cipherText, offset, keys, 0);//轮密钥加
	    	
	    	for (int i = 1; i < 11; i++) {
	    		subtitueBytes(cipherText, offset);//S盒代替
	    		
	    		for (int j = 0; j < 4; j++) {
	    			for (int k = 1; k <= j; k++) {//行移位
	    				rowShiftLeft(cipherText, offset + 4 * j, offset + 4 * j + 3);
	    			}
	    		}
	    			    		//列混淆
	    		mixCol(cipherText, offset);
	    		//轮密钥加
	    		addRoundKey(cipherText, offset, keys, i);
	    	}
	   
	    }
	    	   
	    private static void decryptBlock(byte[] cipherText, int offset, byte[] keys) {	
	    
	    	
	    	for (int i = 10; i >= 1; i--) {    		
	    		addRoundKey(cipherText, offset, keys, i);//轮密钥加
	    		
	    		invMixCol(cipherText, offset);
	    			    		
	    		for (int j = 0; j < 4; j++) {
	    			for (int k = 1; k <= j; k++) {//逆向列混淆
	   				 rowShiftRight(cipherText, offset + 4 * j, offset + 4 * j + 3);
	    			}
	    		}
	    		//逆向字节代替
	    		invSubtitueBytes(cipherText, offset);	    		
	    	}
	    	//轮密钥加
	    	addRoundKey(cipherText, offset, keys, 0);
	    	transpose(cipherText, offset);//转化为行存储
	    }
	   
	     public static byte[] encrypt(byte[] plainText, byte[] key) {
	    	 byte[] cipherText ;
	    	 //不足16个字节进行补0
	    	 if(plainText.length % 16 != 0)cipherText = new byte[plainText.length / 16 * 16 + 16];
	    	 else cipherText = new byte[plainText.length];
	    	 
	    	 //密钥扩展
	    	 byte[] keys = expandKey(key);
	    	 
	    	 for(int i = 0; i < plainText.length; i++)
	    		 cipherText[i] = plainText[i];
	    	 
	    	 for(int i = plainText.length; i < cipherText.length; i++)
	    		 cipherText[i] = 0;//不足的部分补0
	    	 
	    	 for (int i = 0; i < plainText.length; i += 16) {	    	
	    		 encryptBlock(cipherText, i, keys);
	    	 }
	    	 return cipherText;
	     }
	     	    
	     public static byte[] decrypt(byte[] cipherText, byte[] key) {
	    	 byte[] plainText;
	    	 if(cipherText.length % 16 != 0)plainText = new byte[cipherText.length / 16 * 16 + 16];
	    	 else plainText = new byte[cipherText.length];
	    	 byte[] keys = expandKey(key);
	    	  
	    	 for (int i = 0; i < cipherText.length; i++)
	    		 plainText[i] = cipherText[i];
	    	 
	    	 for (int i = cipherText.length; i < plainText.length; i++)
	    		 plainText[i] = 0;
	    	 
	    	 for (int i = 0; i < cipherText.length; i += 16) {
	    		 decryptBlock(plainText, i, keys);
	    	 }
	    	 
	    	 return trim(plainText);
	     }
	     
	     private static byte[] trim(byte[] text) {
	    	 int last = text.length - 1;
	    	 
	    	 while(text[last] == 0) {
	    		 last--;
	    	 }
	    	 
	    	 return Arrays.copyOfRange(text, 0, last + 1);
	     }
	
	/////////////////////function for Test/////////////////////
	private static String hex(byte[] bytes) {
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
		String plainStr =  "0123456789abcdeffedcba9876543210";
		String cipherStr = "ff0b844a0853bf7c6934ab4364148fb9";
		String keyStr =    "0f1571c947d9e8590cb7add6af7f6798";
		byte[] plainText = convertStringToBytes(plainStr);
        byte[] cipherText = convertStringToBytes(cipherStr);
        byte[] key = convertStringToBytes(keyStr);     
        
		byte[] cipher = encrypt(plainText, key);
        byte[] plain = decrypt(cipher, key);
        
        System.out.println("plainText: " + hex(plainText));
        System.out.println("cipherText: " + hex(cipherText));
        System.out.println("key : " + hex(key));
        System.out.println("AES-128 encrypt: " + hex(cipher));
        System.out.println("AES-128 decrypt: " + hex(plain));
	}

}
