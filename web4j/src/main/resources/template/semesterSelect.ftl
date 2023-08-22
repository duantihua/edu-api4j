[#ftl]
<script src='${base}/dwr/interface/semesterDao.js'></script>
<script src='${base}/static/scripts/common/SemesterSelect.js'></script>
<script type="text/javascript">
  var calendarArray = new Array();
  [#list calendars?if_exists as sc]
  calendarArray[${sc_index}]={'id':'${sc.id?if_exists}','name':'${sc.name}'};
  [/#list]
  [#if !(yearNullable?exists)]
    [#assign yearNullable=false]
  [/#if]
  [#if !(termNullable?exists)]
    [#assign termNullable=false]
  [/#if]
  [#if !(stdTypeDefaultFirst)?exists]
    [#assign stdTypeDefaultFirst=true]
  [/#if]
  var dd = new SemesterSelect("academicCalendar","year","term",${yearNullable?string},${termNullable?string},${stdTypeDefaultFirst?string});
  dd.init(calendarArray);
</script>
