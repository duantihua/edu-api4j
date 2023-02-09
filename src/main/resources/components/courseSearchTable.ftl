[#ftl/]
<table width="100%" onkeypress="dwr.util.onReturn(event, searchCourse)">
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
  <tr><td class="infoTitle">[@text name="attr.code"/]:</td><td><input type="text" name="course.code" style="width:100px;" maxlength="32"/></td></tr>
  <tr><td>[@text name="common.name"/]:</td><td><input type="text" name="course.name" style="width:100px;" maxlength="30"/></td></tr>
  <tr><td>英文名:</td><td><input type="text" name="course.enName" style="width:100px;" maxlength="30"/></td></tr>
     <tr><td>项目:</td>
       <td>
       [@htm.i18nSelect datas=projects selected="" name="course.project.id" style="width:100px;"]
        <option value="">...</option>
       [/@]
    </td>
  </tr>
     <tr><td>[@text name="level"/]:</td>
       <td>
       [@htm.i18nSelect datas=levels selected="" name="course.level.id" style="width:100px;"]
        <option value="">...</option>
       [/@]
    </td>
  </tr>
  <tr><td>建议开课院系:</td><td>
       [@htm.i18nSelect datas=departments selected="" name="course.department.id" style="width:100px;"]
        <option value="">...</option>
       [/@]
  </td></tr>
     <tr><td>[@text name="examMode"/]:</td><td>
       [@htm.i18nSelect datas=examModes selected="" name="course.examMode.id" style="width:100px;"]
        <option value="">...</option>
       [/@]
  </td></tr>
     <tr><td>[@text name="course.credits"/]:</td><td><input type="text" name="course.credits" style="width:100px;" maxlength="30"/></td></tr>
  <tr><td>[@text name="course.creditHours"/]:</td><td><input type="text" name="course.creditHours" style="width:100px;" maxlength="30"/></td></tr>
  <tr><td>[@text name="course.weekHours"/]:</td><td><input type="text" name="course.weekHours" style="width:100px;" maxlength="30"/></td></tr>
  <tr><td>周数:</td><td><input type="text" name="course.weeks" style="width:100px;" maxlength="30"/></td></tr>
  <tr><td>学时类型:</td>
    <td><select name="courseHourTypeId" >
        [#list courseHourTypes as courseHourType]
          <option value="${(courseHourType.id)!}">${(courseHourType.name)!}</option>
        [/#list]
      </select>
    </td>
  </tr>
  <tr><td></td><td><input type="text" name="courseHourType_hours" style="width:100px;" maxlength="30"/></td></tr>
  <tr><td>计学分:</td>
    <td>
    <select  name="course.required" style="width:100px;" >
      <option value="" selected>...</option>
         <option value="1" >计</option>
         <option value="0" >不计</option>
     </select>
    </td>
  </tr>
  ${extraSearchOptions?default("")}
  <tr height="50px">
    <td colspan="2" align="center">
      <button onclick="searchCourse()" accesskey="Q">[@text name="action.query"/](<U>Q</U>)</button>
    </td>
  </tr>
  </table>
