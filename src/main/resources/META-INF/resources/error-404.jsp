<%@ page contentType="text/html; charset=UTF-8" language="java"
  pageEncoding="UTF-8" isErrorPage="true"%>
<% boolean unwrapped = null == request.getHeader("x-requested-with") && null == request.getParameter("x-requested-with"); %>
<% if (unwrapped){ %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr">
<head>
<title></title>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="content-style-type" content="text/css" />
<meta http-equiv="content-script-type" content="text/javascript" />
<style>
html,body {
  height: 100%;
  padding: 0;
  margin: 0;
}
</style>
</head>
<body>
  <%} %>
  <style>
.container {
  height: 150px;
  margin-top: 150px;
  overflow: hidden;
  text-align: center;
}
</style>
  <div class="container">
    <span style="font-size: 30px; font-weight: bolder">
      <img id="error_img" style="width: 48px; height: 48px;" alt="warn" /> 抱歉，您访问的页面没找到</span><br />
    <% if (unwrapped){ %>
    <input type="button" onclick="javascript:history.go(-1)" value="返回上页" />
    <input type="button" onclick="goHome()" value="返回首页" />
    <% } %>
  </div>
  <script>
    var basePath = "<%=request.getContextPath()%>";
    document.getElementById("error_img").src = basePath
        + "${base}/static/themes/default/icons/48x48/warning.png";
    var goHome = function() {
      window.location.href = basePath + "/home.action";
    }
  </script>
  <%
    if (unwrapped) {
  %>
</body>
</html>
<%
  }
%>
