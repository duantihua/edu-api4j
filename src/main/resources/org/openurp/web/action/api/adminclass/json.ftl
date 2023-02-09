[#ftl]
{
squades : [[#list squades! as class]{id : '${class.id}', name : '${class.name?js_string}', code : '${class.code?js_string}'}[#if class_has_next],[/#if][/#list]]
}
