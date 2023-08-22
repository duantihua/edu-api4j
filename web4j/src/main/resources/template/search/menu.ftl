[#ftl/]
<style>
  .menuPan .ui-state-focus{
    font-weight: normal;
      margin: -1px;
  }
  .menuPan .ui-menu iframe{
    position: absolute;top: 0; left: 0;
    border:0 none;
    filter: mask();
  }
  .menuPan .ui-menu span{margin-top:2px;}
  .menuPan .ui-menu a.ui-state-selected{
    font-weight: normal;
      background: url("${base}/static/images/ui-state-selected-bg.png") repeat-x scroll 50% 50% #DADADA;
    border: 1px solid #2694E8;
    color: #FDFDFD;
    margin: 0 -1px;
  }
  .menuPan .ui-combobox-text{
    cursor:pointer;
    border-right:0 none;
  }
  .menuPan{
    font-weight: normal;
  }
  .menuPan *{
    text-align:left;
    z-index:20;
  }
  .menuPan .comboboxValues{
    display:none;
  }
</style>
<tr>
  <td class="search-item">
    [#if tag.label??]<label for="${tag.id}_menu-text" class="title">[#if tag.required]<em class="required">*</em>[/#if]${tag.label}:</label>[/#if]
    <span class="menuPan">
      <input id="${tag.id}" class="comboboxValue"  name="[#if (tag.multi!)=="true"]seq_[/#if]${tag.name}"  type="hidden" title="${tag.title}" value="[#if (tag.value)??]${(tag.value.id)!}[/#if]"/>
      <input id="${tag.id}_menu-text" class="ui-combobox-text ui-widget ui-widget-content ui-corner-left" title="${tag.title!}" type="text" value="" style="text-align:center;width:80px;font-size:12px;padding-top:2px;padding-bottom:2px;vertical-align: top" readOnly="readOnly"/>
      <div class="ui-widget-content ui-corner-right" style="cursor:pointer;width:20px;margin-left:-5px;+padding:2px;display:inline-block;+display:inline;vertical-align: top">
        <span class="ui-icon ui-icon-triangle-1-s" style="margin-left:1.5px;margin-top:2px;display:inline-block"></span>
      </div>
      <ul class="${tag.id}_root ui-menu ui-widget ui-widget-content ui-corner-all" style="margin-top:-1px;width:120px;display:none;position:absolute;">
        [#if tag.items??]
          [#list tag.items as node]
            [@listChildren node/]
          [/#list]
        [/#if]
      </ul>
      [#if (tag.multi!)=="true"]
      <div class="comboboxValues"></div>
      [/#if]
    </span>
  </td>
</tr>
<script>
  jQuery(function(){
    jQuery.fn.extend({
      combobox:function(options){
        options = options || {};
        options.onChange = options.onChange || jQuery.noop;
        options.initCallback = options.initCallback || jQuery.noop;
        var $this = jQuery(this);
        $this.data("ui-combobox-options",options);

        var comboboxValue = $this;
        options.defaultVal = comboboxValue.val();
        var $parent = $this.parent();
        var text = $parent.children(".ui-combobox-text");
        var arrowBtn = text.next();
        var thisMenu = arrowBtn.next();

        $this.init = function(){
          var tagId = $this.prop("id");
          $this.parent().find("."+tagId+"_root")[0].style.left = $this.parent().find("."+tagId+"_root").prev().prev().offset().left+"px";
          $this.parent().find("."+tagId+"_root").width($this.parent().find("."+tagId+"_root").width()+25);
          $this.parent().find("."+tagId+"_root")[0].style.top = $this.parent().find("."+tagId+"_root").prev().prev().offset().top+ $this.parent().find("."+tagId+"_root").prev().prev().outerHeight()+"px";
          if(jQuery.browser.msie){
            $this.next().next().css("padding-top","0px");
          }

          text.focus(function(){
            var span = arrowBtn.find("span");
            span.removeClass("ui-icon-triangle-1-s").addClass("ui-icon-triangle-1-n");
            if(thisMenu.data("inited")){
              thisMenu.show();
            }else{
              thisMenu.data("inited",true);
              thisMenu.hide();
            }
          }).blur(function(){
            if(!thisMenu.data("mouseover") && !arrowBtn.hasClass("ui-state-hover")){
              var multiple = comboboxValue.data("ui-combobox-options").multiple;
              var doHide = true;
              var optionNodes = thisMenu.children("li");
              for(var i=0;i<optionNodes.length;i++){
                if(jQuery(optionNodes[i]).children(".ui-state-focus").length){
                  doHide = false;
                  break;
                }
              }
              if(!multiple || (doHide && !thisMenu.find("ul:visible").length)){
                thisMenu.hide();
                var span = arrowBtn.find("span");
                span.removeClass("ui-icon-triangle-1-n").addClass("ui-icon-triangle-1-s");
              }
            }
          }).click(function(){
            text.focus();
          });

          arrowBtn.click(function(){
            var span = arrowBtn.find("span");
            if(span.hasClass("ui-icon-triangle-1-n")){
              span.removeClass("ui-icon-triangle-1-n").addClass("ui-icon-triangle-1-s");
              thisMenu.hide();
            } else {
              text.focus();
            }
          }).hover(function(){
            arrowBtn.toggleClass("ui-state-hover");
          });

          comboboxValue.change(options.onChange);
          comboboxValue.bind("initCallback",options.initCallback);
          comboboxValue.bind("changeValue",function(e,unTriggerOnChange){
            var value = "";
            var titles = "";
            var multiple = comboboxValue.data("ui-combobox-options").multiple;
            comboboxValue.val("");
            if(multiple){
              thisMenu.next().empty();
            }
            var count = 0;
            thisMenu.find("a.ui-state-selected").each(function(){
              var $this = jQuery(this);
              if(!jQuery(this).next().length && jQuery(this).is("[entityId]")){
                value += $this.attr("entityId")+",";
                thisMenu.next().append("<input type='hidden' name='${tag.name}' autocomplete='off' value='"+$this.attr("entityId")+"'/>");
                titles +=$this.text()+",";
                count ++;
              }
            });
            if(count>0){
               if(comboboxValue.data("ui-combobox-options").empty && multiple){
                 var emptyEle = thisMenu.find(".ui-combobox-item-empty a");
                 if(thisMenu.find("a[entityId]").length==count){
                  emptyEle.addClass("ui-state-selected");
                }else{
                  emptyEle.removeClass("ui-state-selected");
                }
                titles = "已选中" + count + "个";
               }
              value = value.substring(0,value.length-1);
            }else if(comboboxValue.data("ui-combobox-options").empty){
              var emptyEle = thisMenu.find(".ui-combobox-item-empty a").trigger("select");
              titles = emptyEle.text();
            }
            comboboxValue.val(value);
            text.val(titles);
            var doHide = true;
            var optionNodes = thisMenu.find("li");
            for(var i=0;i<optionNodes.length;i++){
              if(!jQuery(optionNodes[i]).children(".ui-state-focus").length){
                doHide = false;
                break;
              }
            }
            if(!multiple || (doHide && !thisMenu.find("ul:visible").length)){
              //IE浏览器中display bug
              if(jQuery.browser.msie){
                setTimeout("jQuery('#"+$this.prop("id")+"').next().next().click()",10);
              }else{
                arrowBtn.find("span").removeClass("ui-icon-triangle-1-n").addClass("ui-icon-triangle-1-s");
                thisMenu.hide();
              }
            }

            if(!unTriggerOnChange){
              comboboxValue.change();
            }
          });

          thisMenu.find("li").hover(function(){
            var $this = jQuery(this);
            var a = $this.children("a");
            var childMenu = $this.children("ul");
            a.addClass("ui-state-focus");
            thisMenu.data("mouseover",true);
            childMenu.css({top:a.position().top+1.5,left:a.position().left+a.outerWidth()-1}).show().css("display","inline-block");
            thisMenu[0].style.left = thisMenu.prev().prev().offset().left+"px";
          },function(){
            jQuery(this).children("a").removeClass("ui-state-focus");
            thisMenu.removeData("mouseover");
            jQuery(this).children("ul").hide();
          });

          thisMenu.find("a").bind("show",function(){
            var $this = jQuery(this).show();
            var li = $this.parent().show();
            var ul = li.parent();
            var parentEle = li.parent().prev();
            if(parentEle.is("a")){
              parentEle.trigger("show");
            }else{
              li.show();
            }
          }).bind("select",function(){
            if(jQuery(this).next().length>0){
              jQuery(this).next().find("a").each(function(){
                jQuery(this).trigger("select");
              });
            }
            jQuery(this).addClass("ui-state-selected").parentsUntil(".comboboxPan").filter("li").each(function(){
              jQuery(this).children("a").addClass("ui-state-selected");
            });
            text.focus();
          }).bind("cancel",function(){
            if(jQuery(this).next().length>0){
              jQuery(this).next().find("a").each(function(){
                jQuery(this).trigger("cancel");
              });
            }
            jQuery(this).removeClass("ui-state-selected");
            jQuery(this).parentsUntil(".comboboxPan").filter("ul").each(function(){
              var list = jQuery(this).children("li");
              var prev = jQuery(this).prev();
              if(prev.is("a")){
                var flag = true;
                for(var i=0;i<list.length;i++){
                  if(jQuery(list[i]).children(".ui-state-selected").length>0){
                    flag = false;
                    break;
                  }
                }
                if(flag){
                  prev.removeClass("ui-state-selected");
                }
              }
            });
            text.focus();
          });

          thisMenu.find("a").click(function(){
            var $this = jQuery(this);
            if($this.parent().hasClass("ui-combobox-item-empty")){
              if(comboboxValue.data("ui-combobox-options").multiple){
                thisMenu.find("a").each(function(){
                  jQuery(this).removeClass("ui-state-selected");
                });
                jQuery(this).addClass("ui-state-selected")
                text.focus();
              }else{
                var selected = $this.hasClass("ui-state-selected")
                thisMenu.find("a").each(function(index,ele){
                  jQuery(this).removeClass("ui-state-selected");
                });
                $this.trigger("select");
                thisMenu.hide();
                var span = arrowBtn.find("span");
                span.removeClass("ui-icon-triangle-1-n").addClass("ui-icon-triangle-1-s");
              }
              comboboxValue.trigger("changeValue");
            }else if($this.next().length>0){
              if(comboboxValue.data("ui-combobox-options").multiple){
                if($this.hasClass("ui-state-selected")){
                  $this.trigger("cancel");
                }else{
                  $this.trigger("select");
                }
                comboboxValue.trigger("changeValue");
              }
            }else{
              var unTriggerOnChange = false;
              if(comboboxValue.data("ui-combobox-options").multiple){
                unTriggerOnChange = (comboboxValue.val().indexOf($this.attr("entityId"))!=-1);
                if($this.hasClass("ui-state-selected")){
                  $this.trigger("cancel");
                }else{
                  $this.parentsUntil(".comboboxPan").filter("li").each(function(){
                    jQuery(this).children("a").addClass("ui-state-selected");
                  });
                  $this.trigger("select");
                }
              }else{
                unTriggerOnChange = comboboxValue.val()==$this.attr("entityId");
                if(comboboxValue.val()!=$this.attr("entityId")){
                  var selected = $this.hasClass("ui-state-selected");
                  thisMenu.find("a").each(function(index,ele){
                    jQuery(this).removeClass("ui-state-selected");
                  });
                  if(!selected){
                    $this.trigger("select");
                  }
                  $this.parent().parent().hide();
                }
              }
              comboboxValue.trigger("changeValue",[unTriggerOnChange]);
            }
          });
        };

        var ajaxDataQuery = thisMenu.children().length==0;
        if(options.empty){
          thisMenu.prepend("<li class='ui-menu-item ui-combobox-item-empty'><a class='ui-corner-all"+(comboboxValue.val()==""?" ui-state-selected":"")+"' style='cursor:pointer' class='ui-corner-all'>"+options.empty+"</a></li>")
        }
        if(ajaxDataQuery){
          /*
          var param = {dataType:"semesterCalendar",uiType:"menu","projectId":${tag.project.id}};
          var res = jQuery.post("${base}/dataQuery.action",param,function(){
            if(res.status==200){
              if(res.responseText!=""){
                thisMenu.append(res.responseText);
                $this.init();
                if(options.defaultVal==""){
                  var firstLi = thisMenu.find("li:first");
                  if(options.empty){
                    firstLi.children("a").trigger("select");
                    text.val("全部");
                  }else{
                    firstLi.children("ul").find("li:first").children("a").trigger("select");
                    comboboxValue.trigger("changeValue",[true]);
                  }
                }else{
                  thisMenu.find("a[entityId='"+options.defaultVal+"']").trigger("select");
                  comboboxValue.trigger("changeValue",[true]);
                }
                if(jQuery.browser.msie && jQuery.browser.version == "6.0"){
                  thisMenu.append("<iframe src='javascript:\"\";' frameborder='no' border='0' style='z-index: -1;'></iframe>");
                  var w = 0;
                  thisMenu.find("ul").each(function(){
                    w = Math.max(w,jQuery(this).outerWidth());
                  });
                  thisMenu.find("iframe").width(thisMenu.outerWidth()+w).height(thisMenu.height());
                }
                comboboxValue.trigger("initCallback");
              }
            }
          });
          */
        }else{
          $this.init();
          var firstLi = thisMenu.find("li:first");
          if(options.defaultVal==""){
            if(options.empty){
              firstLi.children("a").trigger("select");
              text.val(options.empty);
            }else{
              firstLi.children("ul").find("li:first").children("a").trigger("select");
              comboboxValue.trigger("changeValue",[true]);
            }
          }else{
            thisMenu.find("a[entityId='"+options.defaultVal+"']").trigger("select");
            comboboxValue.trigger("changeValue",[true]);
          }
          if(jQuery.browser.msie && jQuery.browser.version == "6.0"){
            thisMenu.append("<iframe src='javascript:\"\";' frameborder='no' border='0' style='z-index: -1;'></iframe>");
            var w = 0;
            thisMenu.find("ul").each(function(){
              w = Math.max(w,jQuery(this).outerWidth());
            });
            thisMenu.find("iframe").width(thisMenu.outerWidth()+w).height(thisMenu.height());
          }
          comboboxValue.trigger("initCallback");
        }
        arrowBtn.find("span").removeClass("ui-icon-triangle-1-n").addClass("ui-icon-triangle-1-s");
        if(jQuery.browser.msie){
          setTimeout("jQuery('#"+$this.prop("id")+"').next().next().click()",10);
        }
      }
    });

    jQuery("#${tag.id}").combobox({
      casecade: true,
      multiple: ${tag.multi!"false"},
      [#if !tag.empty??]
      empty:"全部",
      [/#if]
      [#if tag.onChange??]
      onChange: function(){
        ${tag.onChange}
      },
      [/#if]
      [#if tag.initCallback??]
      initCallback:function(){
        ${tag.initCallback};
      },
      [/#if]
      match:function(){

      }
    });
  });
</script>
[#macro listChildren node]
<li class="ui-menu-item">
  [#if node.children?size>0]
    <a class="ui-corner-all" style="cursor:pointer"><span class="ui-menu-icon ui-icon ui-icon-carat-1-e" style="float:right"></span>${node.name}</a>
    <ul class="ui-menu ui-widget ui-widget-content ui-corner-all" style="width:120px;display: none;position:absolute">
    [#list node.children as child]
      [@listChildren child/]
    [/#list]
    </ul>
  [#else]
    <a class="ui-corner-all" entityId="${node.id}" style="cursor:pointer">${node.name}</a>
  [/#if]
</li>
[/#macro]
