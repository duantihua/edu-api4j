[#ftl/][@b.head/]
<body>
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
    [@getMessage /]
    [@s.actionerror/]
    [#if Parameters.messages?exists]
    [#list Parameters.messages?split(",") as message]
     [#if (message?length]2)>[@text name="${message}"/][/#if]&nbsp;
    [/#list]
    [/#if]
    </font>
    </td>
   </tr>
  </table>
<body>
<script type="text/javascript">
  if(!self.name){
    window.resizeTo(300,200);
    window.moveTo(300,200);
    setTimeout("window.close()",2000);
  }
</script>
[#include "/template/foot.ftl"/]
