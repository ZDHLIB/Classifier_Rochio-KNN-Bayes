package FileOperate;

public class Utf8ToUnicode {

	 /** 
	    * ��utf-8����ת��Ϊunicode���� 
	    * @param aByte byte[] -ԭ�ַ�����utf-8�����ֽ����� 
	    * return sByte byte[] -ת�����unicode���뷽ʽ���ַ���
	    */ 
	    public static String changeUtf8ToUnicode(byte[] aByte) { 
	       int sLength = aByte.length; //ԭ�ֽ����鳤�� 
	       //�洢ת��Ϊunicode������StringBuffer�ַ��� 
	       StringBuffer sUnicodeStringBuffer = new StringBuffer(); 
	       char sChar; //������ʱ���ÿ����utf-8�н���������unicode���� 
	       //���²������ж��ֽ��Ƿ���"1110 xxxx 10xxxxxx 10xxxxxx"����ʽ���� 
	       for (int i = 0; i < sLength; i++) { //ѭ��ÿһ���ֽ� 
	           if (i + 2 < sLength) { 
	              /** 
	               * aByte[i] & 0xF0 == 0xE0       ---> �жϵ�ǰ�ֽ��Ƿ��ԡ�1110������ʽ��ʼ�� 
	               * aByte[i + 1] & 0xC0 == 0x80   ---> �ж���һ���ֽ��Ƿ��ԡ�10������ʽ��ʼ�� 
	               * aByte[i + 2] & 0xC0 == 0x80   ---> �ж�����һ���ֽ��Ƿ��ԡ�10������ʽ��ʼ�� 
	               * �������������㣬���ʾ�˶��ֽڽ�����utf-8���룬�򽫶�����н����������ת * ��Ϊunicode���룩 
	               */ 
	              if ((aByte[i] & 0xF0) == 0xE0 && (aByte[i + 1] & 0xC0) == 0x80 && 
	                  (aByte[i + 2] & 0xC0) == 0x80) { 
	                  /** 
	                   * ����ǰ�ֽ� 1110 xxxx ת��Ϊ xxxx 000000 000000 ����ʽ�����岽��Ϊ�� 
	                   * 1110 xxxx << 12 = xxxx 000000 000000 
	                   * 1110 0100 << 12 = 0100 000000 000000 
	                   */ 
	                  sChar = (char) (aByte[i] << 12); 
	                  /** 
	                   * �� ǰ�����ֽ� ת��Ϊ xxxx xxxxxx 000000 ����ʽ�����岽��Ϊ�� 
	                   * 10 xxxxxx & 0x003F = 0000 000000 xxxxxx 
	                   * 10 111000 & 0x003F = 0000 000000 111000 
	                   * 
	                   * 0000 000000 xxxxxx << 6 = 0000 xxxxxx 000000 
	                  * 0000 000000 111000 << 6 = 0000 111000 000000 
	                   * 
	                   * xxxx 000000 000000 | 0000 xxxxxx 000000 = xxxx xxxxxx 000000 
	                   * 0100 000000 000000 | 0000 111000 000000 = 0100 111000 000000 
	                   */ 
	                  sChar = (char) ((((aByte[i + 1] & 0x003F) << 6) | sChar)); 
	                  /** 
	                   * ���������ֽ�ת��Ϊ xxxx xxxxxx xxxxxx ����ʽ�����岽��Ϊ�� 
	                   * 10 xxxxxx & 0x003F = 0000 0000 00 xxxxxx 
	                   * 10 101101 & 0x003F = 0000 0000 00 101101 
	                   * 
	                   * xxxx xxxxxx 000000 | 0000 000000 xxxxxx = xxxx xxxxxx xxxxxx 
	                   * 0100 111000 000000 | 0000 000000 101101 = 0100 111000 101101 
	                   */ 
	                  sChar = (char) ((aByte[i + 2] & 0x003F) | sChar); 
	                   i = i + 2; 
	                   sUnicodeStringBuffer.append(sChar); 
	               } else { 
	                   sUnicodeStringBuffer.append((char) aByte[i]); 
	               } 
	           } 
	         } 
	       return sUnicodeStringBuffer.toString(); 
	    } 
}

