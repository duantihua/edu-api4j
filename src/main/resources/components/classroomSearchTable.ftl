 <table width="100%" onkeypress="dwr.util.onReturn(event, searchClassroom)">
  <tr>
    <td colspan="2" class="infoTitle" align="left" valign="bottom" >
     <img src="${b.static_url("bui","icons/16x16/actions/info.png")} align="top"/>
      <em>[@text name="ui.searchForm"/]</em>
    </td>
  <tr>
    <td colspan="2" style="font-size:0px">
      <img src="${b.static_url("bui","icons/16x16/actions/keyline.png")}" height="2" width="100%" align="top"/>
    </td>
   </tr>
  <tr>
   <td class="infoTitle">[@text name="attr.name"/]:</td>
   <td><input name="classroom.name" type="text" value="" style="width:100px" maxlength="20"/></td>
  </tr>
  <tr>
   <td class="infoTitle">[@text name="attr.code"/]:</td>
   <td><input name="classroom.code" type="text" value="" style="width:100px" maxlength="32"/></td>
  </tr>
  <tr>
    <td class="infoTitle">[@text name="classroomType"/]:</td>
    <td id="configType">[@htm.i18nSelect datas=classroomConfigTypeList selected="" name="classroom.roomType.id" style="width:100px;"><option value="">[@text name="common.all"/]</option][/@]</td>
  </tr>
  <tr>
     <td class="infoTitle">[@text name="campus"/]:</td>
     <td>
         <select id="district" name="classroom.campus.id" style="width:100px;">
         <option value="">[@text name="common.all"/]</option>
       </select>
    </td>
  </tr>
  <tr>
    <td class="infoTitle">[@text name="building"/]:</td>
    <td>
     <select id="building" name="classroom.room.building.id" style="width:100px;">
       <option value="">[@text name="common.all"/]</option>
     </select>
     </td>
  </tr>
  <tr>
     <td class="infoTitle">[@text name="department"/]:</td>
     <td>
        [@htm.i18nSelect datas=roomDepartList name="roomDepartId" selected="${Parameters['roomDepartId']?default(0)}"style="width:100px;"><option value="">[@text name="common.all"/]</option][/@]
     </td>
  </tr>
  ${extraSearchOptions?default("")}
  <#--
  <tr>
     <td class="infoTitle">冲突检测:</td>
     <td>
        <select name="classroom.detectCollision">
          <option value="">全部</option>
          <option value="1">是</option>
          <option value="0">否</option>
        </select>
     </td>
  </tr>
  -->
  <tr align="center" heigth="50px">
   <td colspan="2"><button  onclick="searchClassroom()">[@text name="action.query"/]</button></td>
  </tr>
  </table>
