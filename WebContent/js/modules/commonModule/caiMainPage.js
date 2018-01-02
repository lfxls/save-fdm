Ext.onReady(function() {
	var  size = $(deptSize).value; 
	if(size != 0){
		determinDivNum(); // 显示单位个数
		addHiddenElement(); // 增加对应hidden
		alertAuto(); // 解析单位,自动报警
	    window.setInterval(auto_reload, 60000);
	}
});

/*自动刷新*/
function auto_reload(){
	window.location.reload();
}
/* Click事件响应 */
function feedback(id) {
	var alertType = '0';
	var o = id;
	var str = $(id).style.backgroundColor;
	str = str.substring(4).replace(/\)/g,"").replace(/\s+/g,"");
	//alert(str);
	var arr = str.split(",");
	if(arr[0]=='204'&& arr[1]=='51'&& arr[2]=='0')		//此处根据颜色确定传递的参数中的alertType的值
	{
		alertType='1';
	}
	var num = o.charAt(11);// o:例如 depertment_1,写固定了，从0数，第11个位置
	var dwdm = $('dwdm' + num.toString()).value;
	var dwmc = $('dwmc' + num.toString()).value;
	var url = ctx + "/basic/zjgl/byqgj!mainPageRes.do?flag=11&DWDM=" +dwdm+ "&DWMC=" + encodeURIComponent(encodeURIComponent(dwmc))+ "&alertType=" +alertType+ "";
	top.openpageOnTree("Event", "10010", "Event", null, url, "yes", 1);

	/*
	 * var baseUrl = ctx +
	 * "/basic/zjgl/Qdgj!mainPageRes.do?flag=11&DWDM={0}&DWMC={1}"; var url =
	 * String.format("<a href='javascript:top.openpageOnTree(\""+"Event
	 * Title"+"\", \"10010\", \""+"Event Title"+"\", null,\"" +baseUrl +"\",
	 * \"yes\", 1);'/>"+"</a>&nbsp;&nbsp;", dwdm , dwmc); return url;
	 */
}

/*跳转到告警界面*/
function goToAlertPage() {
	top.openpageOnTree("Event", "10010", "Event", null, ctx
			+ "/basic/zjgl/Qdgj!mainPageRes.do?flag=11&DWDM=" + DWDM + "&DWMC="
			+ $En(DWMC) + "", "yes", 1);
}


/*后台获取所有单位，并根据单位代码查得该单位下所有表计的总数和报警数，之后res。msg返回信息处理*/
function alertAuto(){
	dwrAction.doAjax("mainPageAction", "getDetailMsg", {}, function(res) {
		if (res.success) { 	
			//alert(res.msg);
			var arr = new Array();
				arr = res.msg.split(";");
			for(var j=0;j<arr.length;j++){
				var innerArr = arr[j].split(",");
				var myNum=new Number(j+1);
				//alert(myNum);
				var divElem   = "department_"+myNum.toString();
				var dwdmHid   = "dwdm"+myNum.toString();
				var dwmcHid   = "dwmc"+myNum.toString();
				$(dwdmHid).value =innerArr[0];	//hidden dwdm
				$(dwmcHid).value =innerArr[1];	//hidden dwmc

					if(innerArr[3]>0){									//innerArr[2] is total meter number of this unit
						 $(divElem).style.backgroundColor='#CC3300';	//change color to red if alert number bigger than zero
						 $(divElem).style.borderColor='#CC3333';
					}	
			}		
		}
	});
}
/*给页面增加一个Input type=hidden的元素*/
function addHiddenElement(){
	var size = $('deptSize').value;
	for(var i=1;i<=size;i++)
	{
	var Attr = "dwdm"+i;
	var input = document.createElement("input");
		input.setAttribute("type", "hidden");
		input.setAttribute("id", Attr);
		input.setAttribute("name", Attr);
		//input.setAttribute("value", "11111");
		//append to form element that you want .
		document.getElementById("forHiddens").appendChild(input);
		
		var AttrDwmc = "dwmc"+i;
	var input = document.createElement("input");
		input.setAttribute("type", "hidden");
		input.setAttribute("id", AttrDwmc);
		input.setAttribute("name", AttrDwmc);
		//input.setAttribute("value", "11111");
		//append to form element that you want .
		document.getElementById("forHiddens").appendChild(input);
	}
}
/*决定隐藏模块的个数*/
function determinDivNum_hidden(){
	var count = $('deptSize').value;
	var t1 = Math.floor(count/4);
	var t2 = count%4;
	if(t2==0){
		return;
	}
	var t3 = count;
	var t4 =(t1+1)*4;
	var t0 =t4-t3;
	for(var i=1;i<=t0;i++){
		var myNum1 =new Number(t3);
		var myNum2=new Number(i);
		var t5 =myNum1+myNum2;
		var ele = "department_"+t5.toString();
		   $(ele).style.display = 'none';
	}
}
/*决定显示模块的个数*/
function determinDivNum(){
	var count = $('deptSize').value;
	for(var i=1;i<=count;i++){
		var myNum=new Number(i);
		var ele = "department_"+myNum.toString();
		   $(ele).style.display = '';
	}
}