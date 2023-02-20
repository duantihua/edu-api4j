[#ftl]
[#include "/template/macros.ftl"/]
<script  type="text/javascript">
  //下拉列表框初始化
  var districtList= new Array();//校区下拉框数组
  var buildingList= new Array();//教学楼下拉框数组
  var distictSize=0;//校区下拉框长度
  var buildingSize=0;//教学楼下拉框长度

  //校区下拉框数组初始化
  districtList[distictSize]=new Array(1);
  districtList[distictSize][0]=null;
  districtList[distictSize][1]="";
  districtList[distictSize++][2]="${b.text('common.all')}";
  [#list campusList as campus]
    districtList[distictSize]=new Array(1);
    districtList[distictSize][0]=null;
    districtList[distictSize][1]="${campus.id}";
    districtList[distictSize++][2]="[@i18nName campus/]";

    //教学楼框数组初始化
     [#--
     [#list district.buildings as building]
      buildingList[buildingSize]=new Array(1);
      buildingList[buildingSize][0]="${district.id}";
      buildingList[buildingSize][1]="${building.id}";
      buildingList[buildingSize++][2]="[@i18nName building/]";
       [/#list]
       --]
  [/#list]
  initSelect(districtList,"district",null,"building");
  initSelect(buildingList,"building","district",null);
  tip="";
</script>
