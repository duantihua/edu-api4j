[#macro i18nTitle(entity)][#if language?index_of("en")!=-1][#if entity.engTitle?if_exists?trim==""]${entity.title?if_exists}[#else]${entity.engTitle?if_exists}[/#if][#else][#if entity.title?if_exists?trim!=""]${entity.title?if_exists}[#else]${entity.engTitle?if_exists}[/#if][/#if][/#macro]
[#if result?exists&&modules?exists]
[#if (modules?size]0)>
<script  src="[@text name="menu.js.url"/]"></script>
<table id="topBar" width="${topBarWidth?default('100%')}" align="center"></table>
<script type="text/javascript">
  var bar=new ToolBar('topBar','${title?default('管理工具')}',null,true,true);
  bar.setMessage('[@getMessage/]');
  [#if style?exists&&style=='line']
  [#assign container='bar'/]
  [#else]
  [#assign container='menu'/]
  var menu = bar.addMenu("管理工具",null,'list.gif');
  [/#if]
  [#if searchBarNeed?default(false)]
    bar.addItem("搜索栏","MM_changeSearchBarStyle('searchBar');","search.gif");
  [/#if]
  [#list modules?if_exists as module]
  [#if (module.entry?index_of('.do')!=-1)]
   ${container}.addItem('[@i18nTitle module/]','G("${module.entry?js_string}")');
  [#else]
   ${container}.addItem('[@i18nTitle module/]','${module.entry?js_string}');
  [/#if]
  [/#list]
  ${(extendScript)?default('')}
  [#if backNeed?default(true)]
  bar.addBack('[@text name="action.back"/]');
  [/#if]
  function G(url){
   self.window.location=url;
  }
</script>
[/#if]
[/#if]
