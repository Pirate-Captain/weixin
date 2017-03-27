/**
 * Created on 2017-3-27
 */
package com.zyl.weixin.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;

/**
 * @author zhuyl<a href="mailto:zhuyl@chsi.com.cn">zhu Youliang</a>
 * @version $Id$
 */
public class HttpRequestPostUtil {
    public static HttpResponseInfo request(String url,Map<String, String> headerMap, Map<String, String> paramMap,String requestBody) {
        HttpResponseInfo httpResponseInfo = new HttpResponseInfo();
        
        PostMethod postMethod = new PostMethod(url);
        //设置请求的编码格式为 utf-8
        postMethod.getParams().setContentCharset("UTF-8");
        //设置请求的头信息
        if(headerMap != null && !headerMap.isEmpty()) {
            Iterator<Entry<String,String>> ite = headerMap.entrySet().iterator();
            while(ite.hasNext()) {
                Entry<String,String> entry = ite.next();
                postMethod.addRequestHeader(entry.getKey(), entry.getValue());
            }
        }
        //设置请求的参数
        if(null != paramMap && !paramMap.isEmpty()) {
            NameValuePair[] pairs = null;
            pairs = new NameValuePair[paramMap.size()];
            Iterator<Entry<String,String>> ite = paramMap.entrySet().iterator();
            int i = 0;
            while(ite.hasNext()) {
                Entry<String,String> entry = ite.next();
                pairs[i++] = new NameValuePair(entry.getKey(),entry.getValue());
            }
            if(null != pairs) {
                postMethod.setRequestBody(pairs);
            }
        }
        //请求体设置，非键值对的形式
        if(StringUtils.isNotBlank(requestBody)) {
            try {
                postMethod.setRequestEntity(new StringRequestEntity(requestBody, "application/json", "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        
        HttpClient httpClient = new HttpClient();
        //设置超时时间为30s
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
        try {
            httpClient.executeMethod(postMethod);
            httpResponseInfo.setStatus(postMethod.getStatusCode());
            System.out.println("请求url：" + url + "，响应状态：" + httpResponseInfo.getStatus());
            try {
                httpResponseInfo.setMessage(postMethod.getResponseBodyAsString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (HttpException e) {
            e.printStackTrace();
            httpResponseInfo.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpResponseInfo.setMessage(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            httpResponseInfo.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpResponseInfo.setMessage(e.getMessage());
        } finally {
            if(null != postMethod) {
                postMethod.releaseConnection();
            }
        }
        
        return httpResponseInfo;
    }
}