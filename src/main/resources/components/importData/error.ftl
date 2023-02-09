[#ftl/]
[@b.head/]
[@b.toolbar title="导入数据格式有误,错误" + (importResult.errs?size)!0 + "个"]
    bar.addPrint();
    bar.addBack();
[/@]
[#if importResultState! !="importFileError"]
  <table class="infoTable" align="center" width="100%">
     <tr><td colspan="2" align="center">导入结果</td></tr>
     <tr><td class="title">成功：</td><td>${(importer.success)!}</td></tr>
     <tr><td class="title">失败：</td><td>${(importer.fail)!}</td></tr>
  </table>
[/#if]
[#if (importResult.errs)??]
  [@b.grid items=(importResult.errs)! var="message" sortable="false"]
      [@b.row]
        [#if importResultState!!="importFileError"]
            [@b.col title="错误序号" width="10%"]${message_index + 1}[/@]
            [@b.col title="行号" property="index" width="10%"]${(message.index + 2)!}[/@]
            [@b.col title="错误内容" width="40%"][#if message.message?starts_with("error")]${b.text(message.message)}[#else]${message.message}[/#if][/@]
            [@b.col title="错误值"][#list message.values as value]${value?default("")}[/#list][/@]
        [#else]
          [@b.col title="错误信息" width="40%"][#if message.message?starts_with("error")]${b.text(message.message)}[#else]${message.message}[/#if][/@]
        [/#if]
      [/@]
      <div style="text-align:center">
        [#if importResultState!!="importFileError"]请将所有的输入数据(包括日期,数字等)在导入文件中以文字格式存放。
        [#else]请使用正确的导入模版，您可以<a href="${downUrl!}"><b>[点击下载]</b></a>模版
        [/#if]
      </div>
  [/@]
[/#if]
<br>
[#if (importResult.msgs)?? && importResult.msgs?size > 0]
  [@b.grid items=(importResult.msgs)! var="message" sortable="false"]
      [@b.row]
        [#if importResultState!!="importFileError"]
            [@b.col title="序号" width="10%"]${message_index + 1}[/@]
            [@b.col title="行号" property="index" width="10%"]${(message.index + 2)!}[/@]
            [@b.col title="提示内容" width="40%"][#if message.message?starts_with("error")]${b.text(message.message)}[#else]${message.message}[/#if][/@]
            [@b.col title="提示值"][#list message.values as value]${value?default("")}[/#list][/@]
        [#else]

        [/#if]
      [/@]

  [/@]
[/#if]
[@b.foot/]
