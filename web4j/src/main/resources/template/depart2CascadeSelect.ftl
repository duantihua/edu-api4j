<script  type="text/javascript">
 //下拉列表框初始化
 var departmentList= new Array();//院系下拉框数组
 var majorList= new Array();//专业下拉框数组
 var directionList = new Array();
 var depSize=0;//院系下拉框长度
 var speSize=0;//专业下拉框长度
 [#if departmentList?exists]
 [#else]
 [#assign departmentList = departmentList /]
 [/#if]
 //院系下拉框数组初始化
 [#list departmentList as department]
   departmentList[depSize]=new Array(1);
   departmentList[depSize][0]=null;
   departmentList[depSize][1]="${department.id}";
   departmentList[depSize][2]="[@i18nName department/]";

   //专业框数组初始化
   [#list department.specialitis as major]
    majorList[speSize]=new Array(2);
    majorList[speSize][0]="${department.id}";
    majorList[speSize][1]="${major.id}";
    majorList[speSize][2]="[@i18nName major/]";
    speSize=speSize+1;
   [/#list]
   depSize=depSize+1;
 [/#list]
 initSelect(departmentList,"department",null,"major");
 initSelect(majorList,"major","department",null);
</script>
