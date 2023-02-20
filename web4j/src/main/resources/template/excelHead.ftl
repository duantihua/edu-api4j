<html>
 <head>
  <meta http-equiv="content-type" content="text/html; charset=utf-8">
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <title>大学教学管理信息系统</title>
 </head>
 [#import "/template/table.ftl" as table]
 [#import "/template/htm.ftl" as htm]
 [#import "/template/message.ftl" as msg]
 <script  src="[@msg.text name="menu.js.url"/]"></script>

 [#global language="zh"]
 [#macro i18nName(entity)][#if locale.language?index_of("en")!=-1][#if entity.enName?if_exists?trim==""]${entity.name?if_exists}[#else]${entity.enName?if_exists}[/#if][#else][#if entity.name?if_exists?trim!=""]${entity.name?if_exists}[#else]${entity.enName?if_exists}[/#if][/#if][/#macro]
 [#macro localAttrName(entityName)][#if language?index_of("en")!=-1]#{entityName}.enName[#else]${entityName}.name[/#if][/#macro]
 [#macro yesOrNoOptions(selected)]
   <option value="0" [#if "0"==selected] selected [/#if] >[@msg.text name="common.no" /]</option>
  <option value="1" [#if "1"==selected] selected [/#if]>[@msg.text name="common.yes" /]</option>
  <option value="" [#if ""==selected] selected [/#if]>[@msg.text name="common.all" /]</option>
 [/#macro]
 [#macro eraseComma(nameSeq)][#if (nameSeq?length>2)]${nameSeq[1..nameSeq?length-2]}[#else]${nameSeq}[/#if][/#macro]
 [#macro getBeanListNames(beanList)][#list beanList as bean]${bean.name}[#if bean_has_next] [/#if][/#list][/#macro]
 [#macro getTeacherNames(teachers)][@getBeanListNames teachers/][/#macro]

 [#function sort_byI18nName entityList]
   [#return sort_byI18nNameWith(entityList,"")]
 [/#function]

 [#function sort_byI18nNameWith entityList nestedAttr]
   [#local name="name"]
   [#if nestedAttr!=""]
    [#local name=[nestedAttr,name]/]
   [/#if]
   [#return entityList?sort_by(name)]
 [/#function]
 [#macro getMessage][#assign error][@s.text name=errors/][/#assign]<div class="message fade-ffff00"  id="error">${error}</div><div class="message fade-ffff00"  id="message">[#if Parameters.messages?exists][#list Parameters.messages?split(",") as message][#if (message?length)>2]>[@msg.text name="${message}"/][/#if][/#list][/#if]</div>[/#macro]
 [#macro searchParams]<input name="params" type="hidden" value="${Parameters['params']?default('')}"/>[/#macro]
