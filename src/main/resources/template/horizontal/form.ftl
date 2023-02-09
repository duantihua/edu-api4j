[#ftl]
<form id="${tag.id}" name="${tag.name}" action="${tag.action}" method="post"[#if tag.target??] target="${tag.target}"[/#if]>
<table class="search-widget" align="left">${tag.body}[#if !tag.body?contains('submit')]<td align="center"><input type="submit" value="查询" onclick="bg.form.submit('${tag.id}');return false;"/></td></tr>[/#if]
</table>
</form>
