    function removeArrangeResult(){
        var taskId = bg.input.getCheckBoxValues("clazz.id");
        if(taskId=="" || taskId==null) {alert("请选择教学任务");return;}
        if(confirm("删除教学任务的排课结果，信息不可恢复确认删除？")){
        	bg.form.addInput(document.taskListForm,"clazzIds",taskId);
            bg.form.submit("taskListForm","manualArrange!removeArrangeResult.action");
            document.taskListForm.action="manualArrange!taskList.action";            
	    }
    }
