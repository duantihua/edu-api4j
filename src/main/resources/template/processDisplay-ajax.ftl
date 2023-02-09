[#ftl]
[@b.head/]

[#if !title??]
[#assign title='处理进度'/]
[/#if]

[@b.toolbar title=title]
  bar.addBack();
[/@]

<div style="width:100%;align:center;">
  [@sj.progressbar id="_progressbar_${barId}" value="0" cssStyle="height:20px;width:100%;display:block;"/]
  <div id="_content_${barId}" style="width:100%;height:100%;overflow:auto;padding:10px;">
  </div>
</div>

<script type="text/javascript">
  var jq_progressbar = jQuery('#_progressbar_${barId}');
  var _total = 1;

  function query() {
    var finished = false;
    jQuery.ajax({
      type: "post",
      url : "${base}/${resource}!retrieveProgress.action?barId=${barId}",
      async : false,
      success : function(data, textStatus, jqXHR) {
        var msg = eval("(" + data + ")");
        if(msg.message == '!!!GAME_OVER!!!') {
          finished = true;
          return;
        }
        if(msg.total) {
          _total = new Number(msg.total);
        }
        var jq_span = jQuery('<span/>').css('display', 'block');
        if(msg.status == 'GOOD') {
          // do nothing
        } else if (msg.status == 'WARNING') {
          jq_span.css('color', 'blue');
        } else if (msg.status == 'ERROR') {
          jq_span.css('color', 'red');
        }
        jq_span.html(msg.message).appendTo(jQuery('#_content_${barId}'));
        if(msg.increase && msg.increase == 'true') {
          var value = jq_progressbar.progressbar('option', 'value');
          jq_progressbar.progressbar('option', 'value', new Number(value) + (1 / _total) * 100);
        }

      }
    });

    if(!finished) {
      query();
    } else {
      return;
    }
  }
  jQuery(function() {
    query();
  });
</script>
