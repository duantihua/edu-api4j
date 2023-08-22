 <table width="100%">
    <tr>
      <td class="infoTitle" align="left" valign="bottom">
       <img src="${b.static_url("bui","icons/16x16/actions/info.png")} align="top"/>
        <em>[@text name="baseinfo.searchStudent"/]</em>
      </td>
    </tr>
    <tr>
      <td colspan="8" style="font-size:0px">
        <img src="${b.static_url("bui","icons/16x16/actions/keyline.png")}" height="2" width="100%" align="top"/>
      </td>
     </tr>
  </table>
  <table width='100%' class="searchTable" onkeypress="dwr.util.onReturn(event, search)">
  <input type="hidden" name="pageIndex" value="1" />
    <tr>
     <td class="infoTitle" width="40%">[@text name="attr.stdNo"/]:</td>
     <td>
      <input type="text" name="std.code" value="${Parameters['std.code']?if_exists}" maxlength="32" style="width:100px"/>
     </td>
    </tr>
    <tr>
     <td class="infoTitle">[@text name="attr.personName"/]:</td>
     <td>
      <input type="text" name="std.name" value="${Parameters['std.name']?if_exists}" maxlength="20" style="width:100px"/>
     </td>
    </tr>
     <tr>
     <td class="infoTitle">年级:</td>
     <td><input type="text" name="std.state.grade" id='std.state.grade' style="width:100px;" maxlength="7"/></td>
     </tr>
<#-- FIXME 暂时disable，因为页面上并没有任何可以选择的选项
     <tr>
     <td class="infoTitle">[@text name="studentType"/]:</td>
     <td>
        <select id="std_stdTypeOfMajor" name="std.stdType.id" style="width:100px;">
        <option value="">[@text name="filed.choose"/]</option>
        </select>
     </td>
    </tr>
-->
     <tr>
     <td class="infoTitle">[@text name="level"/]:</td>
     <td>
        <select id="std_levelOfMajor" name="std.level.id" style="width:100px;">
        <option value="">[@text name="filed.choose"/]</option>
        </select>
     </td>
    </tr>
    <tr>
     <td class="infoTitle">[@text name="common.college"/]:</td>
     <td>
       <select id="std_department" name="department.id" style="width:100px;">
         <option value="">[@text name="filed.choose" /]...</option>
       </select>
     </td>
    </tr>
     <tr>
     <td class="infoTitle">[@text name="major"/]:</td>
     <td>
       <select id="std_major" name="std.state.major.id" style="width:100px;">
         <option value="">[@text name="filed.choose"/]...</option>
       </select>
     </td>
    </tr>
     <tr>
     <td class="infoTitle">[@text name="direction"/]:</td>
     <td>
       <select id="std_direction" name="std.state.direction.id" style="width:100px;">
         <option value="">[@text name="filed.choose" /]...</option>
       </select>
     </td>
    </tr>
    <tr>
     <td class="infoTitle">[@text name="common.adminClass"/]:</td>
     <td>
      <input type="text" name="adminClassName" value="" style="width:100px;" maxlength="20"/>
     </td>
    </tr>
    <tr>
     <td class="infoTitle">是否有效:</td>
     <td>
      <select name="stdActive" style="width:100px">
      <option value="">全部</option>
      <option value="1" selected>有效</option>
      <option value="0">无效</option>
      </select>
     </td>
    </tr>
    ${extraTR?if_exists}
    <tr align="center">
     <td colspan="2">
       <button style="width:60px" onclick="search(1)">[@text name="action.query"/]</button>
     </td>
    </tr>
  </table>
<script type="text/javascript">
  var sds = new Major3Select("std_levelOfMajor","std_department","std_major","std_direction",${(stdTypeNullable?string('true', 'false'))?default('false')},true,true,true);
  sds.init(levelArray,departArray);
</script>
