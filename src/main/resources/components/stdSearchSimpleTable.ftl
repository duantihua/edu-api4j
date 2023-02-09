 <table  width="100%">
    <tr>
      <td  class="infoTitle" align="left" valign="bottom">
       <img src="${b.static_url("bui","icons/16x16/actions/info.png")} align="top"/>
        <em>[@text name="baseinfo.searchStudent"/]</em>
      </td>
    </tr>
    <tr>
      <td  colspan="8" style="font-size:0px">
        <img src="${b.static_url("bui","icons/16x16/actions/keyline.png")}" height="2" width="100%" align="top"/>
      </td>
     </tr>
  </table>
  <table   width='100%'  class="searchTable"  onkeypress="dwr.util.onReturn(event, search)">
  <input type="hidden" name="pageIndex" value="1"/>
  <input type="hidden" name="std_state" value="1"/>
    <tr>
     <td  class="infoTitle" width="35%">[@text name="attr.stdNo"/]:</td>
     <td>
      <input type="text" name="std.code" size="10" value="${Parameters['std.code']?if_exists}" style="width:100px;" maxlength="32"/>
     </td>
    </tr>
    <tr>
     <td   class="infoTitle">[@text name="attr.personName"/]:</td>
     <td>
      <input type="text" name="std.name" size="10" value="${Parameters['std.name']?if_exists}" style="width:100px;" maxlength="20"/>
     </td>
    </tr>
     <tr>
     <td class="infoTitle">年级:</td>
     <td><input type="text" name="std.state.grade" id='std.state.grade' style="width:100px;" maxlength="7"/></td>
     </tr>
     <tr>
     <td class="infoTitle">[@text name="studentType"/]:</td>
     <td>
        <select id="std_stdTypeOfMajor" name="std.stdType.id" style="width:100px;">
        <option value="">[@text name="filed.choose"/]</option>
        </select>
     </td>
    </tr>
    <tr>
     <td class="infoTitle">[@text name="common.college"/]:</td>
     <td>
       <select id="std_department" name="std.state.department.id"  style="width:100px;">
         <option value="">[@text name="filed.choose"/]...</option>
       </select>
     </td>
    </tr>
     <tr>
     <td class="infoTitle">[@text name="major"/]:</td>
     <td>
       <select id="std_major" name="std.state.major.id"  style="width:100px;">
         <option value="">[@text name="filed.choose"/]...</option>
       </select>
     </td>
    </tr>
     <tr>
     <td class="infoTitle">[@text name="direction"/]:</td>
     <td>
       <select id="std_direction" name="std.state.direction.id"  style="width:100px;">
         <option value="">[@text name="filed.choose"/]...</option>
       </select>
     </td>
    </tr>
    ${extraElement?if_exists}
    <tr>
    <tr align="center">
     <td colspan="2">
       <button style="width:60px" class="buttonStyle" onclick="search(1)">[@text name="action.query"/]</button>
     </td>
    </tr>
  </table>
<script type="text/javascript">
  var sds = new StdTypeDepart3Select("std_stdTypeOfMajor","std_department","std_major","std_direction",true,true,true,true);
  sds.init(stdTypeArray,departArray);
  sds.major=1;
  function changeMajorType(event){
     var select = getEventTarget(event);
     sds.major=select.value;
     fireChange($("std_department"));
  }
</script>
