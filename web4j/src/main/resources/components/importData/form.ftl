[#ftl/]
[@b.head/]
[@b.toolbar title=Parameters["display"]!("导入")]
    bar.addItem("模板下载","downloadTemplate()","action-download");
[/@]
[@b.form action="!importData" theme="list" enctype="multipart/form-data"]
    [@b.messages/]
    <label for="importFile" class="label">文件目录:</label><input type="file" name="importFile" value="" id="importFile"/>
    <br>
    <div style="padding-left: 110px;">
    [@b.submit value="system.button.submit" onsubmit="validateExtendName"/]
    <input type="reset" value="${b.text("system.button.reset")}" class="buttonStyle"/>

    </div>
    <div style="color:red;font-size:2">上传文件中的所有信息均要采用文本格式。对于日期和数字等信息也是一样。</div>
    [#list Parameters?keys as key]
          [#if key!='method']<input type="hidden" name="${key}" value="${Parameters[key]}" />[/#if]
     [/#list]
[/@]
 [@b.form name="downloadForm" action="!downloadTemplate"]
     [#list Parameters?keys as key]
          [#if key!='method']<input type="hidden" name="${key}" value="${Parameters[key]}" />[/#if]
     [/#list]
 [/@]
<script type="text/javascript">
    function downloadTemplate(){
      document.downloadForm.submit();
    }
    function validateExtendName(form){
        var value = form.importFile.value;
        if(value == ""){
          alert("请选择文件");
          return false;
        }
        var index = value.indexOf(".xls");
        if((index < 1) || (index < (value.length - 4))){
            alert("${b.text("filed.file")}'.xls'");
            return false;
        }
        return true;
    }
</script>
[@b.foot/]
