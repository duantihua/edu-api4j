<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=utf-8">
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <script type="text/javascript" src="${base}/static/js/base/jquery.ui.core.min.js"></script>
  <script type="text/javascript" src="${base}/static/js/base/jquery.ui.position.min.js"></script>
  <script type="text/javascript" src="${base}/static/js/base/jquery.ui.widget.min.js"></script>
  <script type="text/javascript" src="${base}/static/js/base/jquery.ui.progressbar.min.js"></script>
  <script type="text/javascript" src="${base}/static/scripts/common/Common.js"></script>

</head>
<body leftmargin="0" topmargin="0" ondblclick="changeFlag();" >
<script>
  var complete = 0;
  var count = 1;
  var colors = ['black','blue','red'];
  var flag = true;
</script>
<table id="processBarTable"  cellspadding="0"  style="width:100%;font-size:12px;" border="0" >
  <tr>
    <td colspan="2" height="20px">
      <table>
        <tr>
          <td><img src="${b.static_url("bui","icons/16x16/actions/info.png")}  align="top" /></td>
          <td id="summary" style="font-size:12px"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <div id="_progressbar"></div>
  </tr>
  <tr>
    <td style="width:100%;height:600px;border-style:hidden;" colspan="2" >
    <select id="processContent" name="" multiple style="width:100%;height:100%;"></select>
    </td>
  </tr>
</table>
<script type="text/javascript">
  jQuery("#_progressbar" ).progressbar({value: 0});

  var contentSelect =  document.getElementById("processContent");

  function setSummary(msg){
    document.getElementById("summary").innerHTML=msg;
  }

  function changeFlag(){
    flag = !flag;
  }

  function addProcessMsg(level,msg,increaceStep){
    var option = new Option(msg,"");
    option.style.color = colors[level - 1];
    if(contentSelect.options.length>0)
      contentSelect.options[contentSelect.options.length-1].selected=false;
    contentSelect.options[contentSelect.options.length]=option;
    if(flag){
      contentSelect.options[contentSelect.options.length-1].selected=true;
    }
    increaceProcess(increaceStep);
  }
  function increaceProcess(increaceStep){
    if(increaceStep==null)
      increaceStep=1;
    else if(increaceStep<=0) return;
    complete+=increaceStep;
    var percent =Math.floor((complete/count)*100);
    if(percent>100) percent=100;

    jQuery("#_progressbar" ).progressbar("value", percent);
  }
</script>
