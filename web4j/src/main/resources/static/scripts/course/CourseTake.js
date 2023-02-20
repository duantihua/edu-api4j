   var form =document.actionForm;
   function sendMessage(){
     var courseTakerIds = getSelectIds("courseTakerId");
     if(""==courseTakerIds){alert("请选择上课学生");return;}
     window.open("courseTakerForTask.action?method=sendMessage&courseTakerIds="+courseTakerIds);
   }
    function withdraw(){
      addParamsInput(form,getInputParams(parent.document.courseTakerForm));
      submitId(form,'courseTakerId',true,'courseTaker.action?method=withdraw&log=1',"确认退课操作吗?");
    }
    function editCourseTakeType(typeId){
      addParamsInput(form,getInputParams(parent.document.courseTakerForm));
      submitId(form,'courseTakerId',true,'courseTaker.action?method=batchSetTakeType&courseTakeTypeId='+typeId,"确认设置操作吗?");
    }
    function exportData(action,exportExplicit){
       var takerIds = getSelectIds("courseTakerId");
       form.action=action+".action?method=export";
       if(null==exportExplicit){
          exportExplicit=true;
       }
       if(exportExplicit){
	       addInput(form,"keys","task.seqNo,task.course.code,task.course.name,task.course.credits,task.courseType.name,std.code,std.name,std.majorClass.name,std.state.department.name,std.state.department.code,courseTakeType.name,task.courseSchedule.teacherNames,task.ScheduleSuggest.teacherDepartNames","hidden");
	       addInput(form,"titles","课程序号,课程代码,课程名称,学分,课程类别,学号,姓名,班级,院系,院系代码,修读类别,授课教师,授课教师院系","hidden");
	       if(""==takerIds){
	          if(!confirm("未选中学生,系统将导出查询条件内的所有学生的上课记录?")) return;
	          else{
	             transferParams(parent.document.courseTakerForm,form,"courseTaker",false);
	             form.submit();
	          }
	       }else{
	          if(!confirm("系统将导出选中学生的上课记录?")) return;
	          else{
	             addInput(form,"courseTakerIds",takerIds,"hidden");
	             form.submit();
	          }
	       }
       }else{
	       addInput(form,"keys","task.seqNo,task.course.code,task.course.name,task.course.credits,task.courseType.name,std.code,std.name,std.majorClass.name,courseTakeType.name,task.arrangeInfo.teacherNames,task.arrangeInfo.teacherDepartNames,arrangeInfo","hidden");
	       addInput(form,"titles","课程序号,课程代码,课程名称,学分,课程类别,学号,姓名,班级,修读类别,授课教师,授课教师院系,课程安排","hidden");
           if(""==takerIds){alert("请选择上课名单，导出明细.");return;}
           addInput(form,"courseTakerIds",takerIds,"hidden");
           form.submit();
       }
    }
