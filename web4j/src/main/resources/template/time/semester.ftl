[#ftl]
<td class="infoTitle" align="right" style="[#if (calendars?size==1)]display:none[/#if]" width="15%">[@msg.text name="semester.type"/]</td>
<td align="bottom"  style="width:100px;[#if (calendars?size==1)]display:none[/#if]">
  <input type="hidden" name="semester.id" value="${semester.id}"/>
  <select id="academicCalendar" name="semester.calendar.id" style="width:100px;">
    <option value="${semester.calendar.id}"></option>
  </select>
</td>
<td class="infoTitle" align="right" style="width:80px;">[@msg.text name="attr.year2year"/]</td>
<td style="width:100px;">
  <select id="year" name="semester.schoolYear" style="width:100px;">
    <option value="${semester.schoolYear}"></option>
  </select>
</td>
<td class="infoTitle" align="right" style="width:50px;">[@msg.text name="attr.term"/]</td>
<td style="width:60px;">
  <select id="term" name="semester.name" style="width:60px;">
    <option value="${semester.name}"></option>
  </select>
</td>
<td style="width:80px" >
  <button style="width:80px" id="semesterSwitchButton" accessKey="W" onclick="javascript:changeSemester(this.form);">
    [@msg.text name="action.changeSemester"/](<U>W</U>)
  </button>
</td>
<script type="text/javascript">
  var semesterAction=document.getElementById('semesterSwitchButton').form.action;
  function changeSemester(form){
    form['semester.id'].value="";
    var errorInfo="";
    if(form['semester.schoolYear'].value=="") errorInfo+="请选择学年度\n";
    if(form['semester.name'].value=="") errorInfo+="请选择学期";
    if(errorInfo!="") {alert(errorInfo);return;}
    form.action=semesterAction;
    form.target="";
    form.submit();
  }
</script>
[#include "/template/semesterSelect.ftl"/]
