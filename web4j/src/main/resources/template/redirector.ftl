[#ftl/][@b.head/]
<body>
<form name="redirectForm" method="post" action="${action?if_exists}" >
<table  width="100%"  >
  <tr>
    <td  class="infoTitle" style="height:22px;" >
     <img src="${b.static_url("bui","icons/16x16/actions/info.png")}" align="top"/><em>
      <em>操作提示</em>
    </td>
  </tr>
  <tr>
    <td colspan="4" style="font-size:0px" >
      <img src="${b.static_url("bui","icons/16x16/actions/keyline.png")}" height="2" width="100%" align="top"/>
    </td>
   </tr>
   <tr>
   <td id="errorTD"><font color="green">
       [@s.actionerror/]
    [#if Parameters.messages?exists]
    [#list Parameters.messages?split(",") as message]
     [#if (message?length]2)>[@text name="${message}"/][/#if]&nbsp;
    [/#list]
    [/#if]
    </font>
    </td>
   </tr>
   <tr>
     <td style="font-size:12px">该页面在半秒钟之内自动跳转，如果没有自动跳转，请点击
     <input class="buttonStyle" type="submit" value="确定"/>
     </td>
   </tr>
  </table>
  [#if method?exists]
  <input type="hidden" id="method" name="method" value="${method}"/>
  [/#if]
  [#list Parameters['params']?if_exists?split("&") as param]
    [#if param?index_of('method') = -1 && (param?length]2)>
    <input  type="hidden" name="${param[0..param?index_of('=')-1]}" value="${param[param?index_of('=')+1..param?length-1]}" />
    [/#if]
  [/#list]
  </form>

<script type="text/javascript">
   function fillSearchAction(){
    var path= document.location.pathname;
    var action="";
    if(-1!=path.lastIndexOf('?'))
    action = path.substr(path.lastIndexOf('/')+1,path.lastIndexOf('?'));
    else
    action= path.substr(path.lastIndexOf('/')+1);

    //if(document.redirectForm.action=="")
      document.redirectForm.action =action;
   }

   fillSearchAction();
   setTimeout('document.redirectForm.submit()',500);
</script>
<body>
