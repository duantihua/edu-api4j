<%@ page contentType="text/html; charset=UTF-8" language="java"
  pageEncoding="UTF-8" isErrorPage="true"%>
<%@ page import="java.io.ByteArrayOutputStream"%>
<%@ page import="java.io.PrintStream"%>
<%@ page import="org.beangle.commons.lang.Strings"%>
<%
  boolean unwrapped = null == request.getHeader("x-requested-with")
          && null == request.getParameter("x-requested-with");
%>
<%
  if (unwrapped) {
%>
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
  <%
    }
  %>
  <style>
.container {
  height: 150px;
  margin-top: 150px;
  overflow: hidden;
  text-align: center;
}

.pertty-low {
  font-weight: bold;
  color: blue;
}

.pertty-height {
  font-weight: bold;
  color: red;
}

div#exceptionStack {
  text-align: left;
  display: none;
}
</style>
  <div class="container">
    <img id="error_img" style="width: 48px; height: 48px;" alt="warn" />
    <span style="font-size: 40px; font-weight: bolder"> 出错了</span> <br />
    <span style="font-size: 30px;">Error happened</span><br />
    <%
      if (Boolean.TRUE.toString().equals(
              application.getInitParameter("devMode"))) {
    %>
    <input type="button" onclick="showStack()" value="显示日志" />&nbsp;
    <%
      }
    %>
    <%
      if (unwrapped) {
    %>
    <input type="button" onclick="goHome()" value="返回首页" />
    <%
      }
    %>
  </div>
  <%
    if (Boolean.TRUE.toString().equals(
            application.getInitParameter("devMode"))) {
  %>
  <div id="exceptionStack">
    <h4>
      错误原因:
      <%=exception.getClass()%></h4>
    <pre><%=exception.getMessage()%></pre>
    <h4>
      错误路径:
      <%=request
            .getAttribute("jakarta.servlet.forward.servlet_path")%></h4>
    <p>With the following stack trace:</p>
    <%
      ByteArrayOutputStream ostr = new ByteArrayOutputStream();
            exception.printStackTrace(new PrintStream(ostr));
            String stackMsg = ostr.toString();
            stackMsg = Strings
                .replace(stackMsg, "org.openurp",
                    "<span class='pertty-height'>org.openurp</span>");
            stackMsg = Strings.replace(stackMsg, "org.beangle",
                "<span class='pertty-low'>org.beangle</span>");
            out.println(stackMsg);
    %>
  </div>
  <%
    }
  %>
  <script>
    var basePath = "<%=request.getContextPath()%>";
    document.getElementById("error_img").src = basePath
        + "${base}/static/themes/default/icons/48x48/warning.png";

    var goHome = function() {
      window.location.href = basePath + "/home.action";
    }

    var showStack = function() {
      var div = document.getElementById("exceptionStack");
      if (div) {
        if (div.style.display == "block") {
          div.style.display = "none";
        } else {
          div.style.display = "block";
        }
      }
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
