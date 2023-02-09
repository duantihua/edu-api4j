<script src='${base}/dwr/interface/semesterDao.js'></script>
<script src='${base}/static/scripts/common/NewSemesterSelect.js'></script>
<script type="text/javascript">
  var schemeArray = new Array();
  [#list semesterSchemes as scheme]
  schemeArray[${scheme_index}]={'id':'${scheme.id?if_exists}','name':'${scheme.name}'};
  [/#list]
  var dd = new NewSemesterSelect("semesterSchemeId","semesterId",${(semesterNullable?default(false))?string});
  dd.init(schemeArray);
</script>
