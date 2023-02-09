<table width="100%" onkeypress="dwr.util.onReturn(event, searchClass)">
  <tr>
    <td colspan="2" class="infoTitle" align="left" valign="bottom">
     <img src="${b.static_url("bui","icons/16x16/actions/info.png")} align="top"/>
      <em>[@text name="ui.searchForm"/]</em>
    </td>
  <tr>
    <td  colspan="2" style="font-size:0px">
      <img src="${b.static_url("bui","icons/16x16/actions/keyline.png")}" height="2" width="100%" align="top"/>
    </td>
  </tr>
  <tr>
   <td class="infoTitle" >[@text name="std.state.grade"/]:</td>
   <td><input name="adminClass.grade" type="text" value="" style="width:100px" maxlength="7"/></td>
  </tr>
  <tr>
   <td class="infoTitle">[@text name="attr.name"/]:</td>
   <td><input name="adminClass.name" type="text" value="" style="width:100px" maxlength="20"/></td>
  </tr>
  <tr>
    <td class="infoTitle">[@text name="studentType"/]:</td>
    <td align="left" id="f_direction">
    <select name="adminClass.stdType.id" id="class_stdTypeOfMajor" style="width:100px;">
       <option value="">[@text name="common.selectPlease"/]</option>
    </select>
    </td>
  </tr>
  <tr>
    <td>培养层次：</td>
       <td>
         <select id="levelOfAdminClass" name="adminClass.level.id" value="" style="width:100px">
         </select>
       </td>
    </tr>
  <tr>
      <td class="infoTitle" id="f_department">[@text name="department"/]:</td>
    <td>
    <select id="class_department" name="adminClass.department.id" style="width:100px;">
       <option value="">[@text name="common.selectPlease"/]</option>
    </select>
    </td>
   </td>
  </tr>
  <tr>
    <td class="infoTitle" >[@text name="major"/]:</td>
    <td align="left" id="f_major" >
    <select id="class_major" name="adminClass.major.id" style="width:100px;">
       <option value="">[@text name="common.selectPlease"/]</option>
    </select>
    </td>
  </tr>
  <tr>
    <td class="infoTitle">[@text name="direction"/]:</td>
    <td align="left" id="f_direction">
    <select id="class_direction" name="adminClass.direction.id" style="width:100px;">
     <option value="">[@text name="common.selectPlease"/]</option>
    </select>
    </td>
  </tr>
  <tr>
    <td>[@text name="common.status"/]:</td>
    <td><select name="adminClass.enabled" style="width:100px;">
           <option value="1" selected>[@text name="common.enabled"/]</option>
           <option value="0">[@text name="common.disabled"/]</option>
         </select>
     </td>
  </tr>
  <tr align="center" height="50px">
   <td colspan="2">
     <button onclick="searchClass()" class="buttonStyle" style="width:60px">
     [@text name="action.query"/]
     </button>
   </td>
  </tr>
  </table>
 <script type="text/javascript">
  var classSelect = new Major3Select("levelOfAdminClass","class_department","class_major","class_direction",true,true,true,true);
  classSelect.init(levelArray,departArray);
  classSelect.major=1;
  function changeClassMajorType(event){
     var select = getEventTarget(event);
     classSelect.major=select.value;
     fireChange($("class_department"));
  }
</script>
