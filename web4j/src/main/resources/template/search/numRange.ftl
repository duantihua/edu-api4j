[#ftl]
<div class="search-item">
  [#assign minField = tag.minField /]
  [#assign maxField = tag.maxField /]
  [#if minField.label??]
  <label style="font-weight:inherit" for="${minField.id}">${minField.label}:</label>
  [/#if]
  <div style="display:inline-block;width:6.25rem;text-align:left">
    <input type="text" id="${minField.id}" name="${minField.name}" value="${minField.value!}"${minField.parameterString}/>&nbsp;-&nbsp;
    <input type="text" id="${maxField.id}" [#if maxField.label??]placeholder="${maxField.label}"[/#if] name="${maxField.name}" value="${maxField.value!}"${maxField.parameterString}/>
  </div>
</div>
<script>
  jQuery(document).ready(function(){
    [@blur minField/]
    [@blur maxField/]
  });
</script>

[#macro blur numField]
  jQuery("#${numField.id}").blur(function(){

    function error($ele) {
      if($ele.css("color") != "red") {
        $ele.data("cssColor",$ele.css("color"));
      }
      if($this.css("fontWeight") != "bold") {
        $ele.data("fontWeight",$ele.css("fontWeight"));
      }
      $ele.css("fontWeight","bold");
      $ele.css("color","red");
    }

    function normal($ele) {
      $ele.css("color", $ele.data('cssColor'));
      $ele.css("fontWeight", $ele.data('fontWeight'));
    }

    function getValidRange(options) {
      var l_min_value = Number.NEGATIVE_INFINITY;
      var l_max_value = Number.POSITIVE_INFINITY;
      if(options.minValue === 0 || options.minValue) {
        l_min_value = options.minValue;
      }
      if(options.minField) {
        if(jQuery(options.minField).val() && parseFloat(jQuery(options.minField).val()) > l_min_value) {
          l_min_value = parseFloat(jQuery(options.minField).val());
        }
      }

      if(options.maxValue === 0 || options.maxValue) {
        l_max_value = options.maxValue;
      }
      if(options.maxField) {
        if(jQuery(options.maxField).val() && parseFloat(jQuery(options.maxField).val()) < l_max_value) {
          l_max_value = parseFloat(jQuery(options.maxField).val());
        }
      }
      [#if tag.debug??]
        alert("Range options: " + JSON.stringify(options));
        alert("Calculated Range: " + l_min_value + "~" + l_max_value);
      [/#if]
      return {
        inRange : function(value) {
          if(value === 0 || value) {
            var inRange = true;
            var message = "";
            if(value < l_min_value) {
              message = "数值范围不能小于:" + l_min_value;
              inRange = false;
            }
            if(value > l_max_value) {
              message = "数值范围不能大于:" + l_max_value;
              inRange = false;
            }
            if(!inRange) {
              alert(message);
            }
            return inRange;
          }
          return true;
        }
      }
    }

    var $this = jQuery(this);
    var $e;
    var numRegex = /^[+-]?(\d+(\.\d*)?|\.\d+)([Ee]\d+)?$/;
    if($this.val() == ""){
    [#if !numField.required]
      return;
    [#else]
      alert("请输入数值");
      if($this.css("color") != "red"){
        $this.data("cssColor",$this.css("color"));
      }
      if($this.css("fontWeight") != "bold"){
        $this.data("fontWeight",$this.css("fontWeight"));
      }
      $this.css("fontWeight","bold");
      $this.css("color","red");
      return;
    [/#if]
    }
    if(!numRegex.test($this.val())){
      alert("请输入数值");
      error($this);
      return;
    } else {
      normal($this);
    }

    // 验证最小值
    [#if numField.min?? || numField.max??]
      var $this = jQuery('#${numField.id}');
      if($this.val()) {
        var rangeOption = {};
        [#if numField.min?? && numField.min?index_of('#') == -1]
        rangeOption.minValue = ${numField.min};
        [/#if]
        [#if numField.min?? && minField.id != numField.id]
        rangeOption.minField = "#${minField.id}";
        [/#if]
        [#if numField.min?? && numField.min?index_of('#') == 0]
        rangeOption.minField = "${numField.min}";
        [/#if]

        [#if numField.max?? && numField.max?index_of('#') == -1]
        rangeOption.maxValue = ${numField.max};
        [/#if]
        [#if numField.max?? && maxField.id != numField.id]
        rangeOption.maxField = "#${maxField.id}";
        [/#if]
        [#if numField.max?? && numField.max?index_of('#') == 0]
        rangeOption.maxField = "${numField.max}";
        [/#if]
        var min_field_range = getValidRange(rangeOption);
        if(!min_field_range.inRange(parseFloat($this.val()))) {
          error($this);
        } else {
          normal($this);
        }
      }
    [/#if]
  });
[/#macro]
