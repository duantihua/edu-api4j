[#ftl]
[#list datas as data]
  <option value="${data.id}"[#if entityId?? && data.id==entityId] selected[/#if]>${(data.name)!}[#if (data.code)??][${data.code}][/#if]</option>
[/#list]
