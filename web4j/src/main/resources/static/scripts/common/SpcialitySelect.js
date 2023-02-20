    // 缺省值
    var defaultValues=new Object();
    // 页面上所有的三级级联选择
    var selects= new Array();
    // 当前操作影响的选择
    var mySelects=new Array();
    // 当前的三级级联选择
    var stdTypeDepart3Select=null;
    // 初始化学生类别选择框
    function initStdTypeSelect(stdTypes){
        if( null==document.getElementById(this.stdTypeSelectId)) return;
        defaultValues[this.stdTypeSelectId]=document.getElementById(this.stdTypeSelectId).value;        
        dwr.util.removeAllOptions(this.stdTypeSelectId);
        dwr.util.addOptions(this.stdTypeSelectId,stdTypes,'id','name');
        if(this.stdTypeNullable){
            dwr.util.addOptions(this.stdTypeSelectId,[{'id':'','name':'...'}],'id','name');
        }
        setSelected(document.getElementById(this.stdTypeSelectId),defaultValues[this.stdTypeSelectId]);
        
        if(null != document.getElementById(this.departSelectId)&&null != document.getElementById(this.majorSelectId)){
	       document.getElementById(this.stdTypeSelectId).onchange=function (){
	        notifyMajorChange();
	       } 
	    }
    }
    // 初始化部门初始化
    function initDepartSelect(departs){
        defaultValues[this.departSelectId]=document.getElementById(this.departSelectId).value;
        dwr.util.removeAllOptions(this.departSelectId);
	    dwr.util.addOptions(this.departSelectId,departs,'id','name');	    
        if(this.departNullable)
           dwr.util.addOptions(this.departSelectId,[{'id':'','name':'...'}],'id','name');
        setSelected(document.getElementById(this.departSelectId),defaultValues[this.departSelectId]);
       
	    document.getElementById(this.departSelectId).onchange =function (){
	        notifyMajorChange();
	       }
    }
    // 初始化专业选择框
    function initMajorSelect(){
       defaultValues[this.majorSelectId]=document.getElementById(this.majorSelectId).value;
       dwr.util.removeAllOptions(this.majorSelectId);
       document.getElementById(this.majorSelectId).onchange=notifyAspectChange;
       
       var std= document.getElementById(this.stdTypeSelectId);
       var d = document.getElementById(this.departSelectId); 

       if(this.majorNullable)
           dwr.util.addOptions(this.majorSelectId,[{'id':'','name':'...'}],'id','name');
       if(std.value!=""&&d.value!=""){
            stdTypeDepart3Select=this;
       		majorDao.getMajorNames(d.value,std.value,setMajorOptions);       
       }
       //setSelected(document.getElementById(this.majorSelectId),defaultValues[this.majorSelectId]);
    }
    // 通知专业变化,填充专业选择列表
    function notifyMajorChange(){
       //alert("event in notifyMajorChange");
       mySelects= getMySelects(event.srcElement.id);
       //if(mySelects.length>1)
       //   dwr.engine.setAsync(false);
       for(var i=0;i<mySelects.length;i++){
	       var s= document.getElementById(mySelects[i].stdTypeSelectId);
	       var d = document.getElementById(mySelects[i].departSelectId);
	       if(null==s||null==d) continue;
	       dwr.util.removeAllOptions(mySelects[i].majorSelectId);
	       if(mySelects[i].majorNullable){
	           dwr.util.addOptions(mySelects[i].majorSelectId,[{'id':'','name':'...'}],'id','name');
	           setSelected(document.getElementById(mySelects[i].majorSelectId),"");
	       }    
	       if(s.value!=""&&d.value!=""){
	           stdTypeDepart3Select =mySelects[i];
		       majorDao.getMajorNames(d.value,s.value,setMajorOptions);
	       }
	       // 过上半秒钟再去查找专业方向，以防专业查找还没有完成
	       //setTimeout(notifyAspectChange,"500");
       }
      // dwr.engine.setAsync(true);
    }
    function setMajorOptions(data){
       for(var i=0;i<data.length;i++)
           dwr.util.addOptions(stdTypeDepart3Select.majorSelectId,[{'id':data[i][0],'name':data[i][1]}],'id','name');
       if(defaultValues[stdTypeDepart3Select.majorSelectId]!=null){
           setSelected(document.getElementById(stdTypeDepart3Select.majorSelectId),defaultValues[stdTypeDepart3Select.majorSelectId]);
       }
    }
    function MajorSelect(stdTypeSelectId,departSelectId,majorSelectId,stdTypeNullable,departNullable,majorNullable){
     // alert(stdTypeSelectId+":"+departSelectId+":"+majorSelectId+":"+majorSelectId);
      this.stdTypeSelectId=stdTypeSelectId;
      this.departSelectId=departSelectId;
      this.majorSelectId=majorSelectId;
      this.stdTypeNullable=stdTypeNullable;
      this.departNullable=departNullable;
      this.majorNullable=majorNullable;
      this.initStdTypeSelect=initStdTypeSelect;
      this.initDepartSelect=initDepartSelect;
      this.initMajorSelect=initMajorSelect;
      this.init=init;
      selects[selects.length]=this;
    }
    function init(stdTypes,departs){
       this.initStdTypeSelect(stdTypes);
       this.initDepartSelect(departs);
       this.initMajorSelect();
       stdTypeDepart3Select=this;
    }
    function getMySelects(id){
        var mySelects = new Array();
        for(var i=0;i<selects.length;i++){
            if(selects[i].stdTypeSelectId==id||
               selects[i].departSelectId==id||
               selects[i].majorSelectId==id)
               mySelects[mySelects.length]=selects[i];
        }
        return mySelects;
    }