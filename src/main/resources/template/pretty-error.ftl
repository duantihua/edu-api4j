[#ftl]
[@b.head /]

[#assign exceptionStack]${(b.text(stack.pop().exception.i18nKey))!}[/#assign]
<div style="width:100%;text-align:center;">
  <div class="ui-widget">
    <div class="actionError">
      <div style="padding: 0.3em 0.7em;font-size:20px;" class="ui-state-error ui-corner-all;">
        <span style="float: left; margin-right: 0.3em;" class="ui-icon ui-icon-alert"></span>
        <span>${exceptionStack}</span>
      </div>
    </div>
  </div>
  [<a href="javascript:history.back();">${b.text("attr.backPage")}</a>]
</div>

[@b.foot /]
