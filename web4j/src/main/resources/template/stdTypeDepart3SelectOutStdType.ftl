<script src='${base}/dwr/interface/departmentDao.js'></script>
<script src='${base}/dwr/interface/majorDao.js'></script>
<script src='scripts/stdTypeDepart3Select.js'></script>
<script type="text/javascript">
  [#if result?if_exists.stdTypeList?exists]
  [#assign stdTypeList=stdTypeList]
  [/#if]
  [#if result?if_exists.departmentList?exists]
  [#assign departmentList=departmentList]
  [/#if]
  var stdTypeArray = new Array();
  [#list stdTypeList as stdType]
  stdTypeArray[${stdType_index}]={'id':'${stdType.id?if_exists}','name':'[@i18nName stdType/]'};
  [/#list]
  var departArray = new Array();
  [#list departmentList as depart]
  departArray[${depart_index}]={'id':'${depart.id?if_exists}','name':'[@i18nName depart/]'};
  [/#list]
  var dd = new StdTypeDepart3Select("stdType","department","major","direction",true,true,true,true);
  dd.init(stdTypeArray,departArray);
</script>
