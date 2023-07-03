<script src='${base}/dwr/interface/departmentDao.js'></script>
<script src='${base}/dwr/interface/majorDao.js'></script>
<script type="text/javascript">
  var departSelectId='department';
  var majorSelectId='major';

  var majorNotNull=false;

  var defaultDepartId="";
  var defaultMajorId="";

  function initDepartSelect(){
    defaultDepartId=document.getElementById(departSelectId).value;
    dwr.util.removeAllOptions(departSelectId);
    dwr.util.addOptions(departSelectId,[[#list departmentList as depart]{'id':'${depart.id?if_exists}','name':'[@i18nName depart/]'},[/#list]{'':'please selector'}],'id','name');
    if(defaultDepartId!="")
       setSelected(document.getElementById(departSelectId),defaultDepartId);
    else{
       dwr.util.addOptions(departSelectId,[{'id':'','name':'请选择..'}],'id','name');
       setSelected(document.getElementById(departSelectId),"");
    }
    document.getElementById(departSelectId).onchange=notifyMajorChange;
  }

  function initMajorSelect(){
     defaultMajorId=document.getElementById(majorSelectId).value;
     dwr.util.removeAllOptions(majorSelectId);

     var d = document.getElementById(departSelectId);
     if(!majorNotNull)
       dwr.util.addOptions(majorSelectId,[{'id':'','name':'请选择..'}],'id','name');
     if(d.value!=""){
         majorDao.getMajorNames(d.value,null,setMajorOptions);
     }
  }

  function notifyMajorChange(){
     defaultMajorId="";
     var d = document.getElementById(departSelectId);
     dwr.util.removeAllOptions(majorSelectId);
     if(!majorNotNull)
       dwr.util.addOptions(majorSelectId,[{'id':'','name':'请选择..'}],'id','name');
     if(d.value!=""){
         majorDao.getMajorNames(d.value,null,setMajorOptions);
     }
  }
  function setMajorOptions(data){
     for(var i=0;i<data.length;i++)
       dwr.util.addOptions(majorSelectId,[{'id':data[i][0],'name':data[i][1]}],'id','name');
     if(defaultMajorId!="")
       setSelected(document.getElementById(majorSelectId),defaultMajorId);
  }

  function initDefault(){
    if(null != document.getElementById(departSelectId)&&null != document.getElementById(majorSelectId)){
      initDepartSelect();
      setTimeout(initMajorSelect,200);
    }
  }
  initDefault();
</script>
