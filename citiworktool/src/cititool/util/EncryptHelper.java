/**
 * <p>Title: 加密常用工具方法类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 源天软件</p>
 * @version 1.0
 */
package cititool.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 加密常用工具方法类
 */
public final class EncryptHelper {

    public static String encodeMd5(String strIn) {

        MessageDigest dig = null;
        try {
            dig = MessageDigest.getInstance("MD5");
            dig.update(strIn.getBytes());

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EncryptHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return byte2hex(dig.digest());
        }

    }

    /*
     * 解码base64解码字符串
     * @param String
     *            strIn,base64编码的字符串
     * @return	解码后的字符串
     */
    public static String decodeBase64(String strIn) throws IOException {
        BASE64Decoder dec = new BASE64Decoder();
        return new String(dec.decodeBuffer(strIn));
    }

    /*
     * 对字符串base64编码
     * @param String
     *            strIn 原字符串
     * @return	编码后的字符串
     */
    public static String encodeBase64(String strIn) throws IOException {
        BASE64Encoder enc = new BASE64Encoder();
        return (enc.encode(strIn.getBytes()));
    }
    /*
     * DES加密字符串
     * @param String
     *            encryptString,待加密的字符串
     * @param String
     *            encryptKey,要求为8位
     * @return	加密成功返回加密后的字符串，失败返回源串
     */

    public static String encryptDES(String encryptString, String encryptKey) {
        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(encryptKey.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
            return byte2hex(cipher.doFinal(encryptString.getBytes()));
        } catch (Exception e) {
            return encryptString;
        }
    }
    /*
     * DES解密字符串
     * @param String
     *            decryptString,待解密的字符串
     * @param String
     *            decryptKey,解密密钥,要求为8位,和加密密钥相同
     * @return	解密成功返回解密后的字符串，失败返源串
     */

    public static String decryptDES(String decryptString, String decryptKey) {
        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(decryptKey.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
            return new String(cipher.doFinal(hex2byte(decryptString)));
        } catch (Exception e) {
            return decryptString;
        }
    }

    private static String byte2hex(byte[] arrIn) {
        StringBuffer sb = new StringBuffer();
        String strTmp;
        for (int i = 0; i < arrIn.length; i++) {
            strTmp = (java.lang.Integer.toHexString(arrIn[i] & 0xFF));
            if (strTmp.length() == 1) {
                sb.append("0").append(strTmp);
            } else {
                sb.append(strTmp);
            }
        }
        return sb.toString().toUpperCase();
    }

    private static byte[] hex2byte(String strIn) {
        byte[] arrIn = strIn.getBytes();
        byte[] arrOut = new byte[arrIn.length / 2];
        String strTmp;
        for (int i = 0; i < arrIn.length; i += 2) {
            strTmp = new String(arrIn, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    public static void main(String args[]) {

        System.out.println(encodeMd5("1"));
    }
}

