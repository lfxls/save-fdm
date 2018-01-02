Ext.namespace("ycl.Widgets");
Wg = ycl.Widgets;
//计时器
var timer;
//滚动条显示秒数
var sec=0;

//秒速更新显示
function secDisplay(){
	sec += 1;
	Ext.Msg.updateProgress(0, sec + 's');
}

//开始计时
function startTimer(){
	clearInterval(timer);
	timer = setInterval("secDisplay()", 1000);
} 

Wg.SmsProgress = function(_cfg) {
	Ext.apply(this, _cfg);
	Ext.apply(this, {retMsgId : ''});
	Ext.apply(this, {outMsgId : ''});
	Ext.apply(this, {timeoutFlag : false});
};

Ext.apply(Wg.SmsProgress.prototype, {
		//初始化方法
		init:function(){
	        //超时时间，默认60s
	        this.time = this.time ? this.time : 60;
	        var obj = this;
	        //是否需要设置超时时间
			if(this.setTimeout) {
				// 进度条对话框
				Ext.Msg.prompt(Wg_Progress_prompt_time, Wg_Progress_prompt_second, function(btn, text){
					if (btn == 'ok'){
						//验证超时的时间是否是数字
			   		 	if(!this.isNumber(text)) {
			   		 		//提示时间要输入整数
			   		 	 	parent.Wg.alert(Wg_Progress_prompt_alert1);
			   		 	 	return;
			   		 	}
			   		 	// 不能为0
			   		 	if(text == '0') {
			   		 		//提示设置的时间要大于0
			   		 		parent.Wg.alert(Wg_Progress_prompt_alert2);
			   		 		return;
			   		 	}
			   		 	Ext.Msg.wait(Wg_Progress_msg_title, Wg_Progress_msg_msg,{
			   		 		interval: 25,
				            duration: text ? text * 1000 : 70 * 1000,
				            increment: 100,
				            fn:function(){
				            	Ext.Msg.hide();
					        	obj.timeoutFlag = true;
				                parent.Wg.alert(Wg_Progress_msg_alert);
				            }
			   		 	});
			   		 	
			       		//计时器
			       		sec = 0;
			       		startTimer();
			       		//发送短信
			       		obj.sendMessage();
				    }
				},'','',this.time);
			} else {
				Ext.Msg.wait(Wg_Progress_msg_title, Wg_Progress_msg_msg,{
			        interval: 25,
			        duration: 70 * 1000,
			        increment: 100,
			        fn:function(){
			        	Ext.Msg.hide();
			        	obj.timeoutFlag = true;
			        	parent.Wg.alert(Wg_Progress_msg_alert);
			        }
				});
				obj.sendMessage();
			}
		},

		//发送短信
		sendMessage : function(){
			var obj = this;
			dwrAction.doAjax(this.beanId, this.actionName, this.baseParam, function(res){
				if(res.success) {
					//获取短信ID
					obj.outMsgId = res.dataObject;
					//判断是否需要循环获取短信发送状态
					if(obj.cycMsgStatus){
						obj.getOutMsgStatus();
					}else{
						//隐藏滚动条
						Ext.Msg.hide();
						//执行结束方法
						obj.endFunction();
					}
				}else{
					//隐藏滚动条
					Ext.Msg.hide();
					parent.Wg.alert(res.msg);
					return;
				}
			});
		},

		//获取短信发送状态
		getOutMsgStatus: function() {
			var obj = this;
			var param = {
				msgId : this.outMsgId
			};
			//根据发送短信ID循环获取短信状态
			if(this.outMsgId && this.outMsgId != '') {
				dwrAction.doAjax('smsServerAction', 'getOutMsg', param, function(res){
					obj.outMsgCallBack(res);
				});
			}
			return;
		},
		
		//获取返回短信
		getRetMsg: function() {
			var obj = this;
			var param = {
				simNo : this.simNo,
				sendDate : this.sendDate
			};
			//根据SIM卡卡号获取返回短信
			dwrAction.doAjax('smsServerAction', 'getRetMsg', param, function(res){
				obj.retMsgCallBack(res);
			});
			return;
		},

		//短信回调方法
		outMsgCallBack : function(res){
			var obj = this;
			if(res.success){
				var outMsg = res.dataObject;
				//短信处于未发送或者队列发送状态
				if(outMsg.status == 'U' || outMsg.status == 'Q') {
					if(!this.timeoutFlag)
						window.setTimeout('smsPro.getOutMsgStatus()', 990);	
			    } else if(outMsg.status == 'S'){
			    	//短信发送成功
			    	//需要等待返回短信
			    	if(obj.waitRetMsg){
			    		this.simNo = outMsg.recipient;
			    		this.sendDate = outMsg.sentDate;
			    		obj.getRetMsg();
			    	}else{
			    		//隐藏滚动条
				    	Ext.Msg.hide();
				    	//调用结束方法
				    	obj.endFunction();
			    	}
			    } else {
			    	//短信发送失败
			    	//隐藏滚动条
			    	Ext.Msg.hide();
			    	//调用结束方法
			    	obj.endFunction();
			    }
			}else{
				//隐藏滚动条
		    	Ext.Msg.hide();
		    	//调用结束方法
		    	obj.endFunction();
			}
			return;
		},
		
		//短信回调方法
		retMsgCallBack : function(res){
			var obj = this;
			if(res.success) {
				this.retMsgId = res.dataObject.msgId;
				//隐藏滚动条
		    	Ext.Msg.hide();
		    	//调用结束方法
		    	obj.endFunction();
		    } else {
		    	//短信发送失败
		    	if(!this.timeoutFlag)
		    		window.setTimeout('smsPro.getRetMsg()', 990);	
		    }
			return;
		},
		
		//获取返回短信ID
		getRetMsgId : function(){
			Ext.Msg.hide();
			var _retMsgId = '';
			if(this.retMsgId)  {
				_retMsgId = this.retMsgId;
				this.retMsgId = '';
			}
			return _retMsgId;
		},
		
		//获取发送短信ID
		getOutMsgId : function(){
			Ext.Msg.hide();
			var _outMsgId = '';
			if(this.outMsgId)  {
				_outMsgId = this.outMsgId;
				this.outMsgId = '';
			}
			return _outMsgId;
		},
		
		//验证是否数字		
		isNumber : function (oNum) {
		  if(!oNum) return false; 
		  var strP=/^\d+(\.\d+)?$/; 
		  if(!strP.test(oNum)) return false; 
		  return true; 
		}
});

//当鼠标放上去时，有提示信息
Ext.QuickTips.init();