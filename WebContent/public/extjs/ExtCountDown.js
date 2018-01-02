Ext.namespace("ycl.Widgets");

Wg = ycl.Widgets;

/*初始化参数说明
 * 	_cfg = {
 *		 beanId://创建任务用actionBean
 * 		 baseParam: //所用参数
 * 		 taskName: //任务名称 召测等等自定义
 * 		 taskType: //任务类型 召测等等自定义
 * 		 endFunction: //结束时调用方法
 *	}
*/

Wg.CountDown = function(_cfg) {
	Ext.apply(this, _cfg);
	Ext.apply(this, {
		taskId : '',
		countDownBar : '',
		taskMgr : ''
	});
};
Ext.apply(Wg.CountDown.prototype, {
	// 初始化
	init : function() {
		var taskName = this.taskName;
	    if(!taskName) {
	    	Wg.alert(Wg_CountDown_taskName_null);
	    	return;
	    }
	    if(!this.endFunction) {
	    	Wg.alert(Wg_CountDown_endFunction_null);
	    	return;
	    }
	    if(!dwrAction) {
	    	Wg.alert(Wg_CountDown_dwrAction_null);
	    	return;
	    }
		var obj = this;
		Ext.Msg.prompt(taskName + Wg_CountDown_prompt_time, Wg_CountDown_prompt_set + taskName + Wg_CountDown_prompt_second, function(btn, text){
			if (btn == "ok") {
				if(!obj.isNumber(text)) {
	   		 		Wg.alert(Wg_CountDown_prompt_alert1);
	   		 	 	return;
	   		 	}
	   		 	// 不能为0
	   		 	if(text == "0") {
	   		 		Wg.alert(Wg_CountDown_prompt_alert2);
	   		 	 	return;
	   		 	}
	   		 	var duration = text ? text : 45;
	   		 	obj.countDownBar = Ext.Msg.show({
					title : taskName + Wg_CountDown_prompt_msg_title,
					msg : duration + Wg_CountDown_prompt_msg_msg,
					width : 250,
					buttons : Ext.Msg.CANCEL,
					fn : function() {
						obj.countDownBar.hide();
						Ext.TaskMgr.stop(obj.taskMgr);
					}
				});
				obj.taskMgr = Ext.TaskMgr.start({
					run : function() {
						if(duration == 0) {
							obj.endFunction();
			                return;
						}
						obj.countDownBar.updateText(duration + Wg_CountDown_prompt_msg_msg);	
						duration --;
					},
					interval : 1000
				});
				obj.createTask();
	   		}
		}, "", "", "45");
    },
	
	// 创建任务
	createTask : function() {
		var obj = this;
		if(!this.beanId) {
			Wg.alert(Wg_CountDown_createTask_beanId_null);
			return;
		}
		if(!this.baseParam) {
			Wg.alert(Wg_CountDown_createTask_baseParam_null);
			return;
		}
		if(!this.taskType) {
			Wg.alert(Wg_CountDown_createTask_taskType_null);
			return;
		}
		Ext.apply(this.baseParam,{taskType:this.taskType});
		dwrAction.createTask(this.beanId,this.menuId,this.baseParam,function(res){
			if(res.success){
				obj.taskId = res.msg;
			}else{
				Wg.alert(res.msg);
				return false;
			}
		});
	},
    
    // 验证是否数字
	isNumber : function (oNum) {
		if(!oNum) return false;
	  	var strP = /^\d+(\.\d+)?$/;
	  	if(!strP.test(oNum)) return false;
	  	return true;
	},
	
	//获取taskId
	getTaskId : function() {
		this.countDownBar.hide();
		Ext.TaskMgr.stop(this.taskMgr);
		var _taskId = '';
		if(this.taskId)  {
			_taskId = this.taskId;
			this.taskId = '';
		}
		return _taskId;
	}
});	
Ext.QuickTips.init();