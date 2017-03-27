<%@ page contentType="text/html; charset=utf-8" language="java" %>
<div>
    <form action="sendkefumessage" method="post">
        <input type="text" id="openId" name="openId"/>
        <input type="text" id="message" name="message" value="Hello Word"/>
        <input type="text" id="tokenId" name="tokenId"/>
        <input type="submit" value="发送" id="sendButton"/>
    </form>
</div>