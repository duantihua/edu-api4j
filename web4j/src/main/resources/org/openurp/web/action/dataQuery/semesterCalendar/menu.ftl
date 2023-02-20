[#ftl]
[#list datas.keySet() as year]
<li class="ui-menu-item">
    <a class="ui-corner-all" style="cursor:pointer"><span class="ui-menu-icon ui-icon ui-icon-carat-1-e" style="float:right"></span>${year}</a>
    <ul class="ui-menu ui-widget ui-widget-content ui-corner-all" style="width:120px;display: none;position:absolute">
    [#list datas[year] as semester]
      <li class="ui-menu-item"><a class="ui-corner-all" entityId="${semester.id}" style="cursor:pointer">${semester.name}学期</a></li>
    [/#list]
    </ul>
</li>
[/#list]
