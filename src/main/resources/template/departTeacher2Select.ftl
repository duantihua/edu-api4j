<script src='${base}/dwr/interface/teacherDao.js'></script>
<script src='${base}/static/scripts/common/departTeacher2Select.js'></script>
  <script type="text/javascript">
  [#if result?if_exists.departmentList?exists]
  [#assign departmentList=departmentList]
  [/#if]
  var departArray = new Array();
  [#list departmentList?sort_by("name")  as depart]
  departArray[departArray.length]={'id':'${depart.id?if_exists}','name':'[@i18nName depart/]'};
  [/#list]
  var t1=new DepartTeacher2Select('teachDepartment','teacher',false,true);
  t1.initTeachDepartSelect(departArray);
  t1.initTeacherSelect();
  </script>
