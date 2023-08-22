[#ftl/]
<style type="text/css">
#apDiv1 {
  border:1px solid #e9f2f8;
  width:160px;
  height:100px;
  text-align:center;
  padding:5px;
  background-color:#FFF;
  position:absolute;
  z-index:99;
  display:none;
}
input {
  border:1px solid;
}
</style>

<tr>
  <td>
    学年学期:<input type="text" readOnly="readOnly" id="semester" name="aaaaaaa"/>
  </td>
</tr>
<div id ="apDiv1"></div>
<script type="text/javascript">
   jQuery(function(){
    jQuery('#semester').focus().click(function(){
      var input = jQuery(this);
      var offset = input.offset();
      if(jQuery("#apDiv1:has(table)").length==0){
        jQuery('#apDiv1').append("<table><tr style='height:20px'><td colSpan='3' style='border-bottom:#e9f2f8 solid 1px;'>2010-2011学年</td></tr><tr style='height:30px'><td style='border:#e9f2f8 solid 1px;'>01</td><td style='border:#e9f2f8 solid 1px;'>02</td><td style='border:#e9f2f8 solid 1px;'>暑期</td></tr><tr><td><input type='button' value='确定'/></td><td><input type='button' value='默认'/></td><td><input type='button' value='清除'/></td></tr></table>");
      }
      jQuery('#apDiv1').css('left',offset.left + 'px')
        .css('top',offset.top + input.height() + 2 + 'px')
        .slideDown('slow');
    });
    jQuery('#semester').blur(function(){
      jQuery('#apDiv1').slideUp('fast');
    });
  });

</script>
