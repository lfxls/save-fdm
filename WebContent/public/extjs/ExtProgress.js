Ext.namespace("ycl.Widgets");

Wg = ycl.Widgets;
/*
 * 初始化参数说明
 * _cfg = {
 * 		   beanId://创建任务用actionbean
 * 		   baseParam: //所用参数
 * 		   taskName: //任务名称 召测、投入、遥控、设置任务 等等自定义
 * 		   taskType: //任务类型 召测、投入、遥控、设置任务 等等自定义
 * 		   endFunction: //结束时调用方法
 * 		   noNeedExcuteStatusFn：true 表示不需要执行获取任务状态方法，false表示需要
 *        }
 */

//计时器
var se,s=0,ss=1; 
var timeoutFlag = false;

function second(){ 
	if((ss%1)==0){
		s+=1;ss=1;
		Ext.Msg.updateProgress(0,s+'s');
	} 
	ss+=1; 
} 

function startSecond(){
	clearInterval(se);
	se=setInterval("second()",1000);
} 

Wg.Progress = function(_cfg) {
	//将_cfg属性应用到本对象中
	Ext.apply(this, _cfg);
	Ext.apply(this, {taskId : ''});
};


Ext.apply(Wg.Progress.prototype, {
		//为对象添加函数
		init:function(){
			timeoutFlag = false;
			//验证传入的参数是否为空字符串''、undefind
	        var taskName = this.taskName;
	        if(!taskName) {
	        	alert(Wg_Progress_taskName_null);
	        	return;
	        }
	        if(!this.endFunction) {
	        	alert(Wg_Progress_endFunction_null);
	        	return;
	        }
	        if(!dwrAction) {
	        	alert(Wg_Progress_dwrAction_null);
	        	return;
	        }
	        
	        //定义一个boolean的变量，且赋值为false
	        this.noNeedExcuteStatusFn = this.noNeedExcuteStatusFn?true:false;//相当于var noNeedExcuteStatusFn=false;
	        
	        
			/**进入按需在线判断流程**/
	        if(!this.noNeedExcuteStatusFn){//实时召测
	        	if(onlineMode=="1"){ //按需在线
	        		if(!isInOnlineTimeRange(new Date())){ //当前时间不在在线时间段内
	        			//查询终端最后唤醒时间是否有效
	        			var wakingFlag = false;
	        			dwr.engine.setAsync(false); //同步ajax调用
	        			dwrAction.doAjax("commonAction", "getWakingFlag", this.baseParam,function(res){
	        				if (res != null) {
								if (res.success) {
									if("0"==res.dataObject) wakingFlag = true; //不需要重新唤醒
								}
	        				}
	        			});
	        			dwr.engine.setAsync(true); 
	        			if(!wakingFlag){ //需要发送唤醒短信
	        				var tempBaseParam = this.baseParam;
	        				tempBaseParam.menuId = this.menuId;
	        				tempBaseParam.czmc = this.menuId+taskName;
	        				Wg.confirm(Wg_Progress_prompt_wakesms, function() {
	        					//发送唤醒短信
	        					dwrAction.doAjax("commonAction", "wakeUpSMS", tempBaseParam,function(res){
	    	        				if (res != null) {
	    								if (res.success) { //读取唤醒状态
	    									tempBaseParam.time = res.dataObject;
	    									//查询短信发送结果
	    									var task = { 
	    										run:function(){
		    										dwrAction.doAjax("commonAction", "getWakeUpStatus", tempBaseParam,function(res){
		    											if(res!=null && res.success){
		    												Ext.TaskMgr.stop(task);
		    												Ext.Msg.hide();
		    												Wg.alert(Wg_Progress_prompt_wakesmsSuccess);
		    											}else{
		    												if(task.taskRunCount >= waitWakeUpSmsResult){ //最后一次执行
		    													Ext.TaskMgr.stop(task);
		    													Ext.Msg.hide();
		    													Wg.alert(Wg_Progress_prompt_wakesmsFailed);
		    												}
		    											}
		    										});
		    									}
	    										,scope: tempBaseParam  
	    										,interval:1000 
	    										,repeat: waitWakeUpSmsResult 
	    									};
	    									Ext.Msg.wait(Wg_Progress_prompt_wakesms_wait,Wg_Progress_msg_msg, {
	    							            interval:25,
	    							            duration: waitWakeUpSmsResult * 1000,
	    							            increment:100,
	    							            fn:function(){
	    							            }
	    						       		 });
	    									Ext.TaskMgr.start(task); 
	    								}else{
	    									Wg.alert(Wg_Progress_prompt_wakesmsFailed + " "+res.msg);
	    								}
	    	        				}
	    	        			});
	        				});
	        				return;
	        			}
	        		}
	        	}
	        }
	        /**实时召测END**/
	        
	        
	        //为对象添加时间属性
	        this.time = this.time ? this.time : 45; //不设数据则默认45秒
	        //指向当前的对象
			var obj = this;
			// 进度条对话框
			Ext.Msg.prompt(taskName + Wg_Progress_prompt_time, taskName +Wg_Progress_prompt_second,function(btn, text){
		   		 if (btn == 'ok'){
		   			 //验证超时的时间是否是数字
		   		 	 if(!obj.isNumber(text)) {
		   		 		 //提示时间要输入整数
		   		 	 	Wg.alert(Wg_Progress_prompt_alert1);
		   		 	 	return;
		   		 	 }
		   		 	 // 不能为0
		   		 	 if(text == '0') {
		   		 		 //提示设置的时间要大于0
		   		 	 	Wg.alert(Wg_Progress_prompt_alert2);
		   		 	 	return;
		   		 	 }
		   		 	 
		       		 Ext.Msg.wait(taskName+Wg_Progress_msg_title,Wg_Progress_msg_msg,{
			            interval:25,
			            shadow:false,
			            modal:false,
			            wait:false,
			            duration: text ? text * 1000 : 45 * 1000,
			            increment:100,
			            fn:function(){
		       			 	//提示召测超时
			                Wg.alert(taskName+ Wg_Progress_msg_alert,obj.endFunction);
			                timeoutFlag = true;
			            }
		       		 });
		       		 
		       		 
		       		//计时器
		       		s=0;
		       		startSecond();
		       		//创建任务
		       		obj.createTask();
			    }
			},'','',obj.time);
		},
		
		
		
		//为对象添加方法： 创建任务共用
		createTask : function(){
			var obj = this;
			//验证action对象是否为空
			if(!this.beanId) {
				Wg.alert(Wg_Progress_createTask_beanId_null);
				return;
			}
			//基本参数不能为空
			if(!this.baseParam) {
				Wg.alert(Wg_Progress_createTask_baseParam_null);
				return;
			}
			//任务类型不能为空
			if(!this.taskType) {
				Wg.alert(Wg_Progress_createTask_taskType_null);
				return;
			}
			
			//DWR回调
			dwrAction.createTask(this.beanId,this.menuId,this.baseParam,function(res){
				//后面有特殊的 taskId自己加
				if(res.success){
					obj.taskId = res.msg;
					//判断是否需要执行获取任务状态方法
					if(!obj.noNeedExcuteStatusFn){//需要
						obj.getTaskStatus(res);
					}else{//不需要
						//setTimeout("Ext.Msg.hide()",1000); 
						Ext.Msg.hide(); //先隐藏再执行结束方法
						obj.endFunction(res); //执行结束方法
					}
				}else{
					Wg.alert(res.msg);
					return false;
				}
			});
		},
		
		//为对象添加方法：获取任务状态
		getTaskStatus: function (actionRes) {
			var obj = this;
			if(this.taskId && this.taskId != '') {
				dwrAction.getTaskStatus(this.taskId,function(res){
					obj.callBack(res , actionRes);
				});
			}  
			return;
		},
		

		//为对象添加方法： 创建任务返回
		callBack : function(res , actionRes){
			var rs = res.split(',');
			var prog = rs[1];
			if(rs[0] == 'EXECUTEING') {
				this.getPro(prog);
				window.setTimeout('pro.getTaskStatus()', 990);	
				return;
		    } else if(rs[0] == 'ERROR'){
		    	Ext.Msg.hide();
		    	Wg.alert(Wg_Progress_callBack_alert);
		    	this.taskId = '';
		    	return;
		    } else {
		    	//找到进度条所在tab页，激活该tab页，然后执行Ext.Msg.hide()，否则进度条在刷新时切换到其他tab页面，
		    	//界面遮罩无法去掉
		    	//如果tab所在页面又嵌套了iframe，无法取到curTabId，所以不切换tab页
		    	if(curTabId==''){
		    		curTabId = parent.window.document.getElementById('curTabId').value;
		    		if(curTabId==''){
		    			parent.parent.window.document.getElementById('curTabId').value;
		    		}
		    	}
		    	
		    	if(curTabId!=''){
		    		var tabs = top.Ext.getCmp("defaulttab0");
		    		var ativieTabIndex = 0;
			    	var num = 0;
			    	tabs.items.each(function(){
			            if(this.id.indexOf(curTabId+'')!=-1){
			            	ativieTabIndex = num;
			            	return false;
			            }
			            num++;
			        });
			    	tabs.setActiveTab(ativieTabIndex);
		    	}
		    	Ext.Msg.hide();
		    	this.endFunction(actionRes); //执行结束方法
		    	return;
		    }
		},
		
		
		//===========================进度条======================
		getPro : function(i){
			//Ext.Msg.updateProgress(0,Wg_Progress_getPro_msg + i + '%'); 
		    //if(i == 100)
		    //Ext.Msg.hide();
		},
		
		// 验证是否数字		
		isNumber : function (oNum) {
		  if(!oNum) return false; 
		  var strP=/^\d+(\.\d+)?$/; 
		  if(!strP.test(oNum)) return false; 
		  return true; 
		},
		
		
		//获取taskId
		getTaskId : function() {
			Ext.Msg.hide();
			
			var _taskId = '';
			
			if(this.taskId)  {
				_taskId = this.taskId;
				this.taskId = '';
			}
			return _taskId;
		},
		getTimeoutFlag : function(){
			return timeoutFlag;
		}
});
		

//当鼠标放上去时，有提示信息
Ext.QuickTips.init();