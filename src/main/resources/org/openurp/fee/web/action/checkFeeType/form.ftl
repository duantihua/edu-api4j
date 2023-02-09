[#ftl]
[@b.head/]
[@b.toolbar title="编辑验证费用类型配置"]
  bar.addBack();
[/@]
[@b.form name="checkFeeTypeEditForm" title="" action="!save" theme="list"]
  [@b.select name="checkFeeType.feeType.id" label="费用类型" required="true" items=feeTypes value=Parameters['checkFeeType.feeType.id'] empty="..." style="width:20%"/]
  [@b.formfoot]
    [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]

    <input type="hidden" name="checkFeeType.id" value="${(checkFeeType.id)!}" />
  [/@]
[/@]
[@b.foot/]
