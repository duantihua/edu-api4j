 <table onkeypress="dwr.util.onReturn(event, searchMajor)" width="100%">
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
  <tr>
    <td>[@text name="attr.code"/]:</td>
    <td><input type="text" name="major.code" style="width:100px;" maxlength="32"/></td>
  </tr>
     <tr>
       <td>[@text name="common.name"/]:</td>
       <td><input type="text" name="major.name" style="width:100px;" maxlength="25"/></td>
     </tr>
   <tr>
    [#--td][@text name="studentType"/]:</td>
    <td>[@htm.i18nSelect datas=stdTypes selected="0"  name="major.stdType.id" style="width:100px;"]
      <option value="">[@text name="common.all"/]</option>[/@]
    </td-->
  </tr>
  <tr>
    <td>[@text name="level"/]:</td>
    <td>
      [@htm.i18nSelect datas=levels selected="0"  name="levelId" style="width:100px;"]
      <option value="">[@text name="common.all"/]</option>[/@]
    </td>
  </tr>
  <tr>
    <td>[@text name="common.college"/]:</td>
    <td>[@htm.i18nSelect datas=departments selected="0" name="major.department.id" style="width:100px;"]
      <option value="">[@text name="common.all"/]</option>[/@]
    </td>
  </tr>
  ${extraSearchOptions?default("")}
  <tr height="50px"><td colspan="2" align="center"><button onclick="searchMajor();">[@text name="action.query"/]</button></td></tr>
</table>
