[#ftl]
[#list datas as data]
  <option value="${data.id}"[#if entityId?? && data.id==entityId] selected[/#if]>${(data.schoolYear)!}学年${(data.name)!}学期</option>
[/#list]
