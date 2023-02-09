  <table width="100%" onkeypress="dwr.util.onReturn(event, searchTeacher)">
   <tr>
    <td colspan="2" class="infoTitle" align="left" valign="bottom">
     <img src="${b.static_url("bui","icons/16x16/actions/info.png")} align="top"/>
      <em>[@text name="ui.searchForm"/]</em>
    </td>
   <tr>
    <td colspan="2" style="font-size:0px">
      <img src="${b.static_url("bui","icons/16x16/actions/keyline.png")}" height="2" width="100%" align="top"/>
    </td>
   </tr>
   <tr><td>[@text name="teacher.staff.code"/]：</td><td><input type="text" name="teacher.staff.code" style="width:100px;" value="${Parameters['teacher.staff.code']?if_exists}" maxlength="32"/></td></tr>
   <tr><td>[@text name="attr.personName"/]:</td><td><input type="text" name="teacher.name" value ="${Parameters['teacher.name']?if_exists}"style="width:100px;" maxlength="20"/></td></tr>
   <tr><td>移动电话:</td><td><input type="text" name="teacher.contactInfo.mobilePhone" value ="${Parameters['teacher.contactInfo.mobilePhone']?if_exists}"style="width:100px;" maxlength="20"/></td></tr>
   <tr><td>办公电话:</td><td><input type="text" name="teacher.contactInfo.phoneOfOffice" value ="${Parameters['teacher.contactInfo.phoneOfOffice']?if_exists}"style="width:100px;" maxlength="20"/></td></tr>
   <tr><td>[@text name="common.college"/]:</td>
     <td>[@htm.i18nSelect datas=departments selected=Parameters['teacher.department.id']?default("") name="teacher.department.id" style="width:100px;"><option value="">[@text name="common.all"/]</option][/@]</td>
   </tr>
   <tr>
     <td>教师类别:</td>
    <td>[@htm.i18nSelect datas=teacherTypes selected=Parameters['teacher.teacherType.id']?default("") name="teacher.teacherType.id" style="width:100px;"><option value="">[@text name="common.all"/]</option][/@]</td>
   </tr>
   <tr>
     <td>职称：</td>
     <td>[@htm.i18nSelect datas=teacherTitles selected=Parameters['teacher.title.id']?default("") name="teacher.title.id" style="width:100px;"><option value="">[@text name="common.all"/]</option][/@]</td>
   </tr>
   <tr>
     <td>在职状态:</td>
    <td>[@htm.i18nSelect datas=teacherWorkStateList selected=Parameters['teacher.state.id']?default("") name="teacher.state.id" style="width:100px;"><option value="">[@text name="common.all"/]</option][/@]</td>
   </tr>
  <tr>
    <td>是否虚拟:</td>
    <td>
      <select name="teacher.virtual" style="width:100px;">
        <option value="1" [#if Parameters['teacher.virtual']?if_exists=="1"]selected[/#if]>[@text name="common.yes" /]</option>
        <option value="0" [#if Parameters['teacher.virtual']?if_exists=="0"]selected[/#if]>[@text name="common.no" /]</option>
        <option value="" [#if Parameters['teacher.virtual']?if_exists==""]selected[/#if]>[@text name="common.all" /]</option>
      </select>
    </td>
  </tr>
   <tr height="50px"><td align="center" colspan="2"><button onclick="searchTeacher();">[@text name="action.query"/]</button></td></tr>
 </table>
