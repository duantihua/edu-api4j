[#ftl/]
<label for="${tag.id}" style="font-weight:inherit">${tag.label!}:</label>
<select id="${tag.id}" name="${tag.name}"${tag.parameterString}>
  ${tag.body}
  [#if tag.empty?? && tag.empty!="false"]
  <option value="">${tag.empty}</option>
  [/#if]
  [#list tag.items as item]
    <option value="${item[tag.keyName]}"[#if tag.isSelected(item)]selected="selected"[/#if]>${item[tag.valueName]!}</option>
  [/#list]
</select>
[#if tag.semesterValue??]
[@eams.semesterCalendar label="学年学期" id="${tag.id}Semester" name="${tag.semesterName}" onChange="${tag.onSemesterChange!}" empty=tag.semesterEmpty?default("true") initCallback="${(tag.initCallback)!}" value=tag.semesterValue /]
[#else]
[@eams.semesterCalendar label="学年学期" id="${tag.id}Semester" name="${tag.semesterName}" onChange="${tag.onSemesterChange!}" empty=tag.semesterEmpty?default("true") initCallback="${(tag.initCallback)!}" /]
[/#if]
<script type="text/javascript">
  [#if tag.value??]
    jQuery("#${tag.id}").val("${tag.value.id}");
  [/#if]
  jQuery("#${tag.id}").hide();
  jQuery("#${tag.id}").prev().hide();
</script>
