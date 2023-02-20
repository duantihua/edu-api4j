[#ftl]
[#macro newMajorSelect id projects extra...]
[#if (projects?size>1)]
[@b.select style="width:155px" label="项目" id="${id}project" name="${extra['projectId']!'project.id'}" items=projects empty="${b.text('filed.choose')}..." /]
[/#if]
[@b.select style="width:155px" label="${b.text('entity.EducationLevel')}" id="${id}level" name="${extra['levelId']!'level.id'}" items={} empty="${b.text('filed.choose')}..." /]
[#if extra['stdTypeId']??]
[@b.select style="width:155px" label="${b.text('entity.studentType')}" id="${id}stdType" name="${extra['stdTypeId']}" items={} empty="${b.text('filed.choose')}..." /]
[/#if]
[@b.select style="width:155px" label="院系" id="${id}department" name="${extra['departId']!'department.id'}" items={} empty="${b.text('filed.choose')}..." /]
[#if extra['majorId']??]
[@b.select style="width:155px" label="${b.text('entity.major')}" id="${id}major" name="${extra['majorId']}" items={} empty="${b.text('filed.choose')}..." /]
[/#if]
[#if extra['majorId']?? && extra['directionId']??]
[@b.select style="width:155px" label="${b.text('entity.direction')}" id="${id}direction" name="${extra['directionId']}" items={} empty="${b.text('filed.choose')}..." /]
[/#if]
<script language="JavaScript" type="text/JavaScript" src='${base}/dwr/interface/projectMajorDwr.js'></script>
<script language="JavaScript" type="text/JavaScript" src='${base}/dwr/engine.js'></script>
<script language="JavaScript">
  jQuery(document).ready(function () {
    jQuery("#${id}project").change(function(){
      jQuery("select").prop("disabled",true);
        jQuery("submit").prop("disabled",true);
        projectMajorDwr.levelAndDeparts(jQuery("#${id}project").val(),setProjectOption);
    });
    [#if extra['majorId']??]
      jQuery("#${id}department").change(function(){
        jQuery("select").prop("disabled",true);
        jQuery("submit").prop("disabled",true);
        projectMajorDwr.majors(jQuery("#${id}level").val(),jQuery("#${id}department").val(),setMajorOptions);
      });
      jQuery("#${id}level").change(function(){
        jQuery("select").prop("disabled",true);
        jQuery("submit").prop("disabled",true);
        projectMajorDwr.majors(jQuery("#${id}level").val(),jQuery("#${id}department").val(),setMajorOptions);
      });
      [/#if]
      [#if extra['majorId']?? && extra['directionId']??]
      jQuery("#${id}major").change(function(){
        jQuery("select").prop("disabled",true);
        jQuery("submit").prop("disabled",true);
        projectMajorDwr.directions(jQuery("#${id}major").val(),setDirectionOptions);
      });
      [/#if]
    });

  function setProjectOption(data){
    document.getElementById("${id}level").options.length=0;
    document.getElementById("${id}department").options.length=0;
    [#if extra['majorId']??]
      document.getElementById("${id}major").options.length=0;
      jQuery("#${id}major").append("<option value=''>${b.text('filed.choose')}...</option>");
      [#if extra['directionId']??]
        document.getElementById("${id}direction").options.length=0;
        jQuery("#${id}direction").append("<option value=''>${b.text('filed.choose')}...</option>");
      [/#if]
    [/#if]
    jQuery("#${id}level").append("<option value=''>${b.text('filed.choose')}...</option>");
    for(var i=0;i<data[0].length;i++){
      jQuery("#${id}level").append("<option value='"+data[0][i][0]+"'>"+data[0][i][1]+"</option>");
        }
        jQuery("#${id}department").append("<option value=''>${b.text('filed.choose')}...</option>");
    for(var i=0;i<data[1].length;i++){
      jQuery("#${id}department").append("<option value='"+data[1][i][0]+"'>"+data[1][i][1]+"</option>");
        }
        [#if extra['stdTypeId']??]
        document.getElementById("${id}stdType").options.length=0;
        jQuery("#${id}stdType").append("<option value=''>${b.text('filed.choose')}...</option>");
    for(var i=0;i<data[2].length;i++){
      jQuery("#${id}stdType").append("<option value='"+data[2][i][0]+"'>"+data[2][i][1]+"</option>");
        }
        [/#if]
        jQuery("select").prop("disabled",false);
      jQuery("submit").prop("disabled",false);
  }
  [#if extra['majorId']??]
  function setMajorOptions(data){
    document.getElementById("${id}major").options.length=0;
    [#if extra['directionId']??]
    document.getElementById("${id}direction").options.length=0;
    jQuery("#${id}direction").append("<option value=''>${b.text('filed.choose')}...</option>");
    [/#if]
    jQuery("#${id}major").append("<option value=''>${b.text('filed.choose')}...</option>");
    for(var i=0;i<data.length;i++){
      jQuery("#${id}major").append("<option value='"+data[i][0]+"'>"+data[i][1]+"</option>");
        }
        jQuery("select").prop("disabled",false);
    jQuery("submit").prop("disabled",false);
    }
    [/#if]
    [#if extra['majorId']?? && extra['directionId']??]
      function setDirectionOptions(data){
      document.getElementById("${id}direction").options.length=0;
      jQuery("#${id}direction").append("<option value=''>${b.text('filed.choose')}...</option>");
      for(var i=0;i<data.length;i++){
        jQuery("#${id}direction").append("<option value='"+data[i][0]+"'>"+data[i][1]+"</option>");
          }
          jQuery("select").prop("disabled",false);
      jQuery("submit").prop("disabled",false);
      }
  [/#if]
  [#if (projects?size==1)]
      jQuery("select").prop("disabled",true);
    jQuery("submit").prop("disabled",true);
    projectMajorDwr.levelAndDeparts("${projects[0].id}",setProjectOption);
    [/#if]
</script>
[/#macro]
