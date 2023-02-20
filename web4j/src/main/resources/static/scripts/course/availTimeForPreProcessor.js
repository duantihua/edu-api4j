     var colors=new Array() ;
     colors["1"]="#94aef3";
     colors["0"]="yellow";
     function changeAvailTime(elem,tdId,innerText,courseIdSeq){  
         var selectUnitNums = document.getElementsByName('selectUnitNum');	//一次选择的节次
         var num=4;
         for(var i=0;i<selectUnitNums.length;i++){
            if(selectUnitNums[i].checked)
            num=selectUnitNums[i].value;
         }   
         var input = document.getElementById("unit"+elem.id);
         var firstId=elem.id;
         var weekId=Math.floor(firstId/14);
         for(var i=0;i<num;i++){
             var input = document.getElementById("unit"+firstId);
             var grid = document.getElementById(firstId);
             //添加选中的课程名(对应显示)
             var span=document.getElementById("span"+firstId);
             //添加value
             var valueList=document.getElementById("unit"+firstId);
             if(courseIdSeq==""){
             	/*span.innerHTML="";
             	valueList.value="";*/
             	alert("未选中课程!");
             }else{
             	if((valueList.value).indexOf(courseIdSeq) == -1){
             		if(innerText.length>2){
             			span.innerHTML+= innerText.substring(0,2)+"..,<br/>";
             		}else{
             			span.innerHTML+= innerText+",<br/>";
             		}
             		valueList.value+=courseIdSeq+",";
             		span.title+= innerText+",";
             	}else{
				   var splitSeq="";
				   if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){
				        splitSeq="<br>";
				   }else{
				   		splitSeq="<BR>";
				   }
             		
             		var valueListSeq=valueList.value.split(",");
             		var innerSeq=span.innerHTML.split(splitSeq);
             		var titleSeq=span.title.split(",");
             		var tempValue="";
             		var tempInner="";
             		var tempTitle="";
             		for(var j=0; j<valueListSeq.length; j++){
             			if(courseIdSeq!=valueListSeq[j] && valueListSeq[j]!=""){
             				tempValue+=valueListSeq[j]+",";
             			}
             		}
             		valueList.value=tempValue;
             		var tem=0;
             		for(var j=0; j<innerSeq.length; j++){
             			if(tem==1&& innerSeq[j]!="" && innerSeq[j]!=","){
             				tempInner+=innerSeq[j].toString()+"<br/>";
             			}else if(innerSeq[j].indexOf(innerText.substring(0,2)) == -1 && innerSeq[j]!=""&& innerSeq[j]!="," ){
             				tempInner+=innerSeq[j].toString()+"<br/>";
             			}else if(innerSeq[j].indexOf(innerText.substring(0,2)) != -1){
             				tem=1;
             			}
             		}
             			span.innerHTML=tempInner;
             		for(var j=0; j<titleSeq.length;j++){
             			if(titleSeq[j] != innerText && titleSeq[j]!=""){
             				tempTitle+=titleSeq[j].toString()+",";
             			}
             		}
             		span.title=tempTitle;
             		
             		
             	}
             }
             if(span.innerHTML==""){
             	grid.style.backgroundColor=colors["1"]
             }
             if(span.innerHTML!=""){
             	grid.style.backgroundColor=colors["0"]
             }
             firstId++;
             if(Math.floor(firstId/14)!=weekId) break;
         }
      }
 
     function clearAll(){
         if(!confirm("清空课程设置,将使所有选择清空，确定设置吗？"))return;
     	 for(var i=0; i<unitsPerDay*7;i++){
		     var elem = document.getElementById("unit"+i);
		     var span=document.getElementById("span"+i);
		     span.innerHTML="";
	         elem.value="";	         
	         elem.parentNode.style.backgroundColor = colors["1"];
	         
	     }
     }
