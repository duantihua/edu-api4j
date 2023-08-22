[#ftl/]
[@b.head/]
[@b.toolbar title="导入数据格式有误,错误" + importResult.errs?size +"个"]
    bar.addPrint();
    bar.addBack();
[/@]
<table class="infoTable" align="center" width="100%">
   <tr><td colspan="2" align="center">导入结果</td></tr>
   <tr><td class="title">成功：</td><td>${(importer.success)!}</td></tr>
   <tr><td class="title">失败：</td><td>${(importer.fail)!}</td></tr>
</table>
[@b.grid items=importResult.errs var="message" sortable="false"]
    [@b.row]
        [@b.col title="错误序号" width="10%"]${message_index + 1}[/@]
        [@b.col title="行号" property="index" width="10%"]${(message.index + 2)!}[/@]
        [@b.col title="错误内容" width="40%"][#if message.message?starts_with("error")]${b.text(message.message)}[#else]${message.message}[/#if][/@]
        [@b.col title="错误值"][#list message.values as value]${value?default("")}[/#list][/@]
    [/@]
    <div style="text-align:center">请将所有的输入数据(包括日期,数字等)在导入文件中以文字格式存放。</div>
[/@]
<br>
[#if (importResult.msgs)?? && importResult.msgs?size > 0]
[@b.grid items=importResult.msgs var="message" sortable="false"]
    [@b.row]
        [@b.col title="序号" width="10%"]${message_index + 1}[/@]
        [@b.col title="行号" property="index" width="10%"]${(message.index + 2)!}[/@]
        [@b.col title="提示内容" width="40%"][#if message.message?starts_with("error")]${b.text(message.message)}[#else]${message.message}[/#if][/@]
        [@b.col title="提示值"][#list message.values as value]${value?default("")}[/#list][/@]
    [/@]
    <div style="text-align:center">请将所有的输入数据(包括日期,数字等)在导入文件中以文字格式存放。</div>
[/@]
[/#if]
[@b.foot/]
