<script src='${base}/dwr/interface/departmentDao.js'></script>
<script src='${base}/dwr/interface/majorDao.js'></script>
<script src='${base}/static/scripts/secondMajor2Select.js'></script>
<script type="text/javascript">
  [#if result?if_exists.secondMajorList?exists]
  [#assign secondMajorList=secondMajorList]
  [/#if]
  var secondMajorArray = new Array();
  [#list secondMajorList?if_exists as secondMajor]
  secondMajorArray[${secondMajor_index}]={'id':'${secondMajor.id?if_exists}','name':'[@i18nName secondMajor/]'};
  [/#list]
  var ss = new SecondMajor2Select("secondMajor","secondAspect",${secondMajorNullable?default('true')},${secondAspectNullable?default('true')});
  ss.init(secondMajorArray);
</script>
