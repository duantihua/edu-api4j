    // 缺省值
    var defaultSemesterValues=new Object();
    // 页面上所有的三级级联选择
    var calendarSelects= new Array();
    // 当前的三级级联选择
    var yearSelectQueue=new Array();
    var termSelectQueue=new Array();
    var originalOnChanges=new Array();
    var selectInited=new Object();
    // 初始化校历选择框
    function intCalendarSelect(calendars){
        if(null==selectInited[this.calendarSelectId]){
           selectInited[this.calendarSelectId]=true;
        }else{
           return;
        }
        if( null==document.getElementById(this.calendarSelectId)) return;
        defaultSemesterValues[this.calendarSelectId]=document.getElementById(this.calendarSelectId).value;
        dwr.util.removeAllOptions(this.calendarSelectId);
        if(this.calendarDefaultFirst){
          	dwr.util.addOptions(this.calendarSelectId,calendars,'id','name');
          	if(this.calendarNullable){
               dwr.util.addOptions(this.calendarSelectId,[{'id':'','name':'...'}],'id','name');
            }
        }else{
          	if(this.calendarNullable){
               dwr.util.addOptions(this.calendarSelectId,[{'id':'','name':'...'}],'id','name');
            }
            dwr.util.addOptions(this.calendarSelectId,calendars,'id','name');
        }
        
        // 让非空的学生类别作为默认
        if(""!=defaultSemesterValues[this.calendarSelectId])        
	        setSelected(document.getElementById(this.calendarSelectId),defaultSemesterValues[this.calendarSelectId]);
       
        var selfOnchange =document.getElementById(this.calendarSelectId).onchange;
        document.getElementById(this.calendarSelectId).onchange=function (event){
            if(event==null)
               event=getEvent();
	        notifyYearChange(event);
	        if(selfOnchange!=null)
    	        selfOnchange();
	    }
    }
    // 初始化学年度选择框
    function initYearSelect(){
       dwr.util.removeAllOptions(this.yearId);       
       var std= document.getElementById(this.calendarSelectId);
       if(std.value!=""){
            yearSelectQueue.push(this);
       		semesterDao.getYearsOrderByDistance(std.value,setYearOptions);       
       }
       var originalOnChange=document.getElementById(this.yearId).onchange;
       if(null==originalOnChange){
          document.getElementById(this.yearId).onchange=function(event){notifyTermChange(event,null);}
       }else{
           originalOnChanges[originalOnChanges.length]=originalOnChange;
           document.getElementById(this.yearId).onchange=
           //function(event){alert(1);notifyTermChange(event,null);originalOnChanges[(originalOnChanges.length-1)]();}
           new Function("event","notifyTermChange(event,null);originalOnChanges["+(originalOnChanges.length-1)+"]();");
       }
    }
    // 通知学年度变化,填充学年度选择列表
    function notifyYearChange(event){
       if(event==null)return;
       //alert("event in notifyYearChange"+event);
       yearCalendarSelects = getMyCalendarSelects(getEventTarget(event).id);
       //alert(yearCalendarSelects.length);
       for(var i=0;i<yearCalendarSelects.length;i++){
	       var s= document.getElementById(yearCalendarSelects[i].calendarSelectId);
	       if(null==s) continue;
	       dwr.util.removeAllOptions(yearCalendarSelects[i].yearId);
	       if(s.value!=""){
	           yearSelectQueue.push(yearCalendarSelects[i]);
		       semesterDao.getYearsOrderByDistance(s.value,setYearOptions);
	       }else{
	          dwr.util.removeAllOptions(yearCalendarSelects[i].termId);
	       }
       }
    }
    function setYearOptions(data){
       var curCalendarSelect=yearSelectQueue.shift();
       if(null!=curCalendarSelect){
	       if(curCalendarSelect.yearNullable){
	           dwr.util.addOptions(curCalendarSelect.yearId,[{'id':'','name':'...'}],'id','name');	           
	       }
	       dwr.util.addOptions(curCalendarSelect.yearId,data);
	       if(defaultSemesterValues[curCalendarSelect.yearId]!=""){
	           setSelected(document.getElementById(curCalendarSelect.yearId),defaultSemesterValues[curCalendarSelect.yearId]);
	       }
	       notifyTermChange(null,curCalendarSelect.yearId);
       }
    }
    // 通知学期变化，填充学期列表
    function notifyTermChange(event,selectId){
       //alert("event in notifyTermChange");
       if(null==selectId) {
         selectId=getEventTarget(event).id;
       }
       myCalendarSelects= getMyCalendarSelects(selectId);
       for(var i=0;i<myCalendarSelects.length;i++){
           //alert("removeAllOptions of :"+myCalendarSelects[i].termId);
	       dwr.util.removeAllOptions(myCalendarSelects[i].termId);	       
	       var s= document.getElementById(myCalendarSelects[i].calendarSelectId);
	       var y= document.getElementById(myCalendarSelects[i].yearId);
	       
	       if(s.value!=""&&y.value!=null){
  	          termSelectQueue.push(myCalendarSelects[i]);
	          //alert(curCalendarSelect.termId)
	          semesterDao.getTermsOrderByDistance(s.value,y.value,setTermOptions);       
	       }else{
	          dwr.util.removeAllOptions(myCalendarSelects[i].termId);
	       }
       }
    }
    function setTermOptions(data){
       //alert(data)
       var curCalendarSelect=termSelectQueue.shift();
       if(null!=curCalendarSelect){
           dwr.util.removeAllOptions(curCalendarSelect.termId);
	       dwr.util.addOptions(curCalendarSelect.termId,data); 
	            
           if(curCalendarSelect.termNullable){
              //alert("add null term");
              dwr.util.addOptions(curCalendarSelect.termId,[{'id':'','name':'...'}],'id','name');
           }
	       if(defaultSemesterValues[curCalendarSelect.termId]!=""){    
	           setSelected(document.getElementById(curCalendarSelect.termId),defaultSemesterValues[curCalendarSelect.termId]);
	       }
       }
    }
    function SemesterSelect(calendarSelectId,yearId,termId,yearNullable,termNullable,calendarDefaultFirst){
    //  alert(calendarSelectId+":"+termId+":"+yearId+":"+yearNullable+":"+termNullable+":"+calendarDefaultFirst);
      this.calendarSelectId=calendarSelectId;
      this.yearId=yearId;
      this.termId=termId;
      this.yearNullable=yearNullable;
      this.termNullable=termNullable;
      this.calendarDefaultFirst=calendarDefaultFirst;
      if(calendarDefaultFirst==null)calendarDefaultFirst=true;
      this.initCalendarSelect=intCalendarSelect;
      
      this.initYearSelect=initYearSelect;
      this.init=initCalendar;
      this.getdefaultSemesterValues=getdefaultSemesterValues;
      calendarSelects[calendarSelects.length]=this;      
      this.getdefaultSemesterValues();
    }
    
    function initCalendar(calendars){  
       this.initCalendarSelect(calendars);       
       this.initYearSelect();         
    }
    function getMyCalendarSelects(id){
        var myCalendarSelects = new Array();
        for(var i=0;i<calendarSelects.length;i++){
            if(calendarSelects[i].calendarSelectId==id||
               calendarSelects[i].yearId==id||
               calendarSelects[i].termId==id)
               myCalendarSelects[myCalendarSelects.length]=calendarSelects[i];
        }
        return myCalendarSelects;
    }
    /**
     * 获得缺剩值
     */
    function getdefaultSemesterValues(){
       defaultSemesterValues[this.calendarSelectId]=document.getElementById(this.calendarSelectId).value;
       defaultSemesterValues[this.yearId]=document.getElementById(this.yearId).value;
       defaultSemesterValues[this.termId]=document.getElementById(this.termId).value;
       //alert(defaultSemesterValues[this.termId]);
    }