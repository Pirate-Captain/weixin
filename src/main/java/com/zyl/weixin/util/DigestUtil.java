/**
 * Created on 2017-3-24
 */
package com.zyl.weixin.util;

import java.security.MessageDigest;

import org.apache.commons.lang.StringUtils;

/**
 * @author zhuyl<a href="mailto:zhuyl@chsi.com.cn">zhu Youliang</a>
 * @version $Id$
 */
public class DigestUtil {
    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    /**
     * SHA 加密
     * @param data
     *            需要加密的字符串
     * @return 加密之后的字符串
     * @throws Exception
     */
    public static String encryptSHA1(String data) throws Exception {
        // 验证传入的字符串
        if ( StringUtils.isBlank(data) ) {
            return "";
        }
        // 创建具有指定算法名称的信息摘要
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        // 使用指定的字节数组对摘要进行最后更新
        sha.update(data.getBytes());
        // 完成摘要计算
        byte[] bytes = sha.digest();
        // 将得到的字节数组变成字符串返回
        return byteArrayToHexString(bytes);
    }

    /**
     * 将一个字节转化成十六进制形式的字符串
     * 
     * @param b
     *            字节数组
     * @return 字符串
     */
    private static String byteToHexString(byte b) {
        int ret = b;
        if ( ret < 0 ) {
            ret += 256;
        }
        int m = ret / 16;
        int n = ret % 16;
        return hexDigits[m] + hexDigits[n];
    }

    /**
     * 转换字节数组为十六进制字符串
     * 
     * @param bytes
     *            字节数组
     * @return 十六进制字符串
     */
    private static String byteArrayToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for ( int i = 0; i < bytes.length; i++ ) {
            sb.append(byteToHexString(bytes[i]));
        }
        return sb.toString();
    }
}