[#ftl]
{
teachers : [[#list teachers! as teacher]{id : '${teacher.id}', name : '${teacher.name?js_string}', code : '${teacher.staff.code?js_string}'}[#if teacher_has_next],[/#if][/#list]],
pageIndex : ${pageLimit.pageIndex},
pageSize : ${pageLimit.pageSize}
}
