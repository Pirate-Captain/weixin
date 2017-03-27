/**
 * Created on 2017-3-27
 */
package com.zyl.weixin.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.zyl.weixin.util.HttpRequestPostUtil;
import com.zyl.weixin.util.HttpResponseInfo;

/**
 * @author zhuyl<a href="mailto:zhuyl@chsi.com.cn">zhu Youliang</a>
 * @version $Id$
 */
public class SendWeixinKefuMessageServlet extends BasicWeixinHttpServlet {
    private static final long serialVersionUID = -1692632814950744652L;
    private String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dealRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dealRequest(req, resp);
    }
    
    private void dealRequest(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
        String token = request.getParameter("tokenId");
        String openId = request.getParameter("openId");
        String message = request.getParameter("message");
        if ( StringUtils.isBlank(token) || StringUtils.isBlank(openId) || StringUtils.isBlank(message) ) {
            response.setContentType("text/html;charset=UTF-8");
            response.getOutputStream().write("参数不能为空".getBytes("UTF-8"));
            return;
        }
        
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("touser", openId);
        paramMap.put("msgtype", "text");
        
        Map<String, String> messageMap = new HashMap<String, String>();
        messageMap.put("content", message);
        paramMap.put("text", messageMap);
        
        HttpResponseInfo responseInfo = HttpRequestPostUtil.request(url + token, null, null, JSONObject.fromObject(paramMap).toString());
        response.setContentType("text/html;charset=UTF-8");
        String result = "响应状态：" + responseInfo.getStatus() + "，message：" + responseInfo.getMessage();
        response.getOutputStream().write(result.getBytes("UTF-8"));
    }
}