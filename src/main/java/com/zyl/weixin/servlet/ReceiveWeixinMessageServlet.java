/**
 * Created on 2017-3-24
 */
package com.zyl.weixin.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * @author zhuyl<a href="mailto:zhuyl@chsi.com.cn">zhu Youliang</a>
 * @version $Id$
 */
public class ReceiveWeixinMessageServlet extends BasicWeixinHttpServlet {
    private static final long serialVersionUID = 4404420099539090810L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dealRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dealRequest(req, resp);
    }
    
    private void dealRequest(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        
    System.out.println(signature + "\t" + timestamp + "\t" + nonce + "\t" + echostr);
        
        if ( !verifyWeixin(request) ) {
            response.setContentType("text/html;charset=UTF-8");
            response.getOutputStream().write("拒绝访问".getBytes("UTF-8"));
            return;
        }
        
        String message = readRequestBody(request);
        if ( StringUtils.isBlank(message) ) {
System.out.println("没有请求信息");
        }
        
        response.getOutputStream().write("".getBytes());
    }
    
    /**
     * 读取消息信息
     * @param request
     * @return
     */
    private String readRequestBody(HttpServletRequest request) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            String line = "";
            
            StringBuffer buffer = new StringBuffer();
            while ( (line = br.readLine()) != null ) {
                buffer.append(line);
            }
            
System.out.println("消息信息：" + buffer.toString() );
            return buffer.toString();
        } catch ( UnsupportedEncodingException e ) {
            e.printStackTrace();
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( null != br ) {
                try {
                    br.close();
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        }
        
        return null;
    }
}