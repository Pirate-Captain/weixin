/**
 * Created on 2017-3-27
 */
package com.zyl.weixin.servlet;

import java.util.Arrays;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.zyl.weixin.util.DigestUtil;

/**
 * @author zhuyl<a href="mailto:zhuyl@chsi.com.cn">zhu Youliang</a>
 * @version $Id$
 */
public abstract class BasicWeixinHttpServlet extends HttpServlet {
    private static final long serialVersionUID = -6475314379985872608L;
    private String token = "weixin1234567890";
    
    /**
     * 验证是否来自微信
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    protected boolean verifyWeixin(HttpServletRequest request) {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        
        if ( StringUtils.isBlank(signature) || StringUtils.isBlank(timestamp) || StringUtils.isBlank(nonce) ) {
            return false;
        }
        String[] verifyArr = new String[]{token, timestamp, nonce};
        Arrays.sort(verifyArr);
        
        String verifyStr = StringUtils.join(verifyArr, "");
        try {
            String sha1VerifyStr = DigestUtil.encryptSHA1(verifyStr);
            return StringUtils.equals(sha1VerifyStr, signature);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return false;
    }
}