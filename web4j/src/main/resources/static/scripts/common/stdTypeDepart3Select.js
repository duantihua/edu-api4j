    
    // 缺省值
    var defaultValues=new Object();
    // 页面上所有的三级级联选择
    var selects= new Array();
    // 当前操作影响的选择
    var mySelects=new Array();
    // 当前的三级级联选择
    var stdTypeDepart3Select=null;
    
    function addTitle(selectId){
       var options=document.getElementById(selectId).options;
       for(var i=0;i< options.length;i++){
         options[i].title=options[i].innerHTML;
       }
    }
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
	       document.getElementById(this.stdTypeSelectId).onchange=function (event){
	        notifyMajorChange(event);
	        notifyAspectChange(event);
	       } 
	    }
    }
    // 初始化部门初始化
    function initDepartSelect(departs){
        if(!document.getElementById(this.departSelectId))return;
        defaultValues[this.departSelectId]=document.getElementById(this.departSelectId).value;
        dwr.util.removeAllOptions(this.departSelectId);
	    dwr.util.addOptions(this.departSelectId,departs,'id','name');	    
        if(this.departNullable)
           dwr.util.addOptions(this.departSelectId,[{'id':'','name':'...'}],'id','name');
        setSelected(document.getElementById(this.departSelectId),defaultValues[this.departSelectId]);
        //document.getElementById(this.departSelectId).style.width="100px";
	    document.getElementById(this.departSelectId).onchange =function (event){
	        notifyMajorChange(event);
	        notifyAspectChange(event);
	       }
    }
    // 初始化专业选择框
    function initMajorSelect(){
       if(!document.getElementById(this.majorSelectId))return;
       defaultValues[this.majorSelectId]=document.getElementById(this.majorSelectId).value;
       dwr.util.removeAllOptions(this.majorSelectId);
       document.getElementById(this.majorSelectId).onchange=notifyAspectChange;
       
       var std= document.getElementById(this.stdTypeSelectId);
       var d = document.getElementById(this.departSelectId); 

       if(this.majorNullable)
           dwr.util.addOptions(this.majorSelectId,[{'id':'','name':'...'}],'id','name');
       if(std.value!=""&&d.value!=""){
            stdTypeDepart3Select=this;
       		majorDao.getMajorNames(d.value,std.value,this.majorTypeId,setMajorOptions);       
       }
       //document.getElementById(this.majorSelectId).style.width="100px";
       //setSelected(document.getElementById(this.majorSelectId),defaultValues[this.majorSelectId]);
    }
    // 初始化专业方向选择框
    function initAspectSelect(){
       if(!document.getElementById(this.directionSelectId))return;
       defaultValues[this.directionSelectId]=document.getElementById(this.directionSelectId).value;
       dwr.util.removeAllOptions(this.directionSelectId);
       var s= document.getElementById(this.majorSelectId);
        if(this.directionNullable)
           dwr.util.addOptions(this.directionSelectId,[{'id':'','name':'...'}],'id','name');
       if(s.value!=""){
           stdTypeDepart3Select=this;
           directionDao.getDirectionNames(s.value,setAspectOptions);
        }
        //document.getElementById(this.directionSelectId).style.width="100px"
       //setSelected(document.getElementById(this.directionSelectId),defaultValues[this.directionSelectId]);
    }
    // 通知专业变化,填充专业选择列表
    function notifyMajorChange(event){
       var target=  getEventTarget(event);
       //alert("event in notifyMajorChange");
       mySelects= getMySelects(target.id);
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
		       majorDao.getMajorNames(d.value,s.value,mySelects[i].majorTypeId,setMajorOptions);
	       }
	       // 过上半秒钟再去查找专业方向，以防专业查找还没有完成
	       //setTimeout(notifyAspectChange,"500");
       }
      // dwr.engine.setAsync(true);
    }
    // 通知专业方向变化，填充专业方向列表
    function notifyAspectChange(event){
       var target=  getEventTarget(event);
       //alert("event in notifyAspectChange");
       mySelects= getMySelects(target.id);
       for(var i=0;i<mySelects.length;i++){
           //alert("removeAllOptions of :"+mySelects[i].directionSelectId);
	       dwr.util.removeAllOptions(mySelects[i].directionSelectId);
	       if(mySelects[i].directionNullable)
	           dwr.util.addOptions(mySelects[i].directionSelectId,[{'id':'','name':'...'}],'id','name');
	       var s= document.getElementById(mySelects[i].majorSelectId);
	       if(s.value!=""){
	          stdTypeDepart3Select=mySelects[i];
	          //alert(stdTypeDepart3Select.directionSelectId)
	          directionDao.getDirectionNames(s.value,setAspectOptions);       
	       }
       }
    }
    function setMajorOptions(data){
       for(var i=0;i<data.length;i++){
       	 	dwr.util.addOptions(stdTypeDepart3Select.majorSelectId,[{'id':data[i][0],'name':data[i][1]}],'id','name');
       }
          
       if(defaultValues[stdTypeDepart3Select.majorSelectId]!=null){
           setSelected(document.getElementById(stdTypeDepart3Select.majorSelectId),defaultValues[stdTypeDepart3Select.majorSelectId]);
       }
       addTitle(stdTypeDepart3Select.majorSelectId);
       //alert(document.getElementById(stdTypeDepart3Select.majorSelectId).outerHTML)
    }
    function setAspectOptions(data){
       for(var i=0;i<data.length;i++)
          dwr.util.addOptions(stdTypeDepart3Select.directionSelectId,[{'id':data[i][0],'name':data[i][1]}],'id','name');       
       if(defaultValues[stdTypeDepart3Select.directionSelectId]!=null){           
           setSelected(document.getElementById(stdTypeDepart3Select.directionSelectId),defaultValues[stdTypeDepart3Select.directionSelectId]);
       }
       addTitle(stdTypeDepart3Select.directionSelectId);
    }
    /**
     *  三级级联选择的模型
     *@param stdTypeSelectId 学生类别下拉框的id
     *@param departSelectId 部门下拉框id
     *@param majorSelectId 专业下拉框id
     *@param directionSelectId 方向下拉框id
     *@param stdTypeNullable 学生类别是允许为空
     *@param departNullable 部门是否允许为空
     *@param majorNullable 专业是否允许为空
     *@param directionNullable 专业方向是否为空
     *@param majorTypeId 专业类别是否是第一专业 0表示不限制,1表示一专业,2表示二专业 默认null
     */
    function StdTypeDepart3Select(stdTypeSelectId,departSelectId,majorSelectId,directionSelectId,stdTypeNullable,departNullable,majorNullable,directionNullable,majorTypeId){
     // alert(stdTypeSelectId+":"+departSelectId+":"+majorSelectId+":"+directionSelectId);
      this.stdTypeSelectId=stdTypeSelectId;
      this.departSelectId=departSelectId;
      this.majorSelectId=majorSelectId;
      this.directionSelectId=directionSelectId;
      this.stdTypeNullable=stdTypeNullable;
      this.departNullable=departNullable;
      this.majorNullable=majorNullable;
      this.directionNullable=directionNullable;
      this.initStdTypeSelect=initStdTypeSelect;
      this.initDepartSelect=initDepartSelect;
      this.initMajorSelect=initMajorSelect;
      this.initAspectSelect=initAspectSelect;
      this.init=init;
      selects[selects.length]=this;
      this.majorTypeId=majorTypeId;
    }
    function initMajorAndAspectSelect(){
      stdTypeDepart3Select.initAspectSelect();
    }
    function init(stdTypes,departs){
       this.initStdTypeSelect(stdTypes);
       this.initDepartSelect(departs);
       this.initMajorSelect();
       stdTypeDepart3Select=this;
       // 留上100毫秒等待专业初试化完毕
       setTimeout(initMajorAndAspectSelect,"200");
    }
    function getMySelects(id){
        var mySelects = new Array();
        for(var i=0;i<selects.length;i++){
            if(selects[i].stdTypeSelectId==id||
               selects[i].departSelectId==id||
               selects[i].majorSelectId==id||
               selects[i].directionSelectId==id)
               mySelects[mySelects.length]=selects[i];
        }
        return mySelects;
    }