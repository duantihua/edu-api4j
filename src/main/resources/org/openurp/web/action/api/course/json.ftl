[#ftl]
{
courses : [
[#list courses! as course]
{
  id : '${course.id}',
  name : '${course.name?js_string}',
  code : '${course.code?js_string}',
  [#if course.department??]
  department : { id:'${course.department.id}', code:'${course.department.code}', name:'${course.department.name}'},
  [#else]
  department : null,
  [/#if]
}[#if course_has_next],[/#if][/#list]
],
pageIndex : ${pageLimit.pageIndex},
pageSize : ${pageLimit.pageSize}
}
