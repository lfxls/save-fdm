var oPic = '';
var oList = '';

var oPrev = '';
var oNext = '';

var oPicLi = '';
var oListLi = '';
var len1 = '';
var len2 = '';

var oPicUl = '';
var oListUl = '';
var w1 = '';
var w2 = '';

var index = 0;

var num = 0;
var num2 = 0;

var picCount = 0;
Ext.onReady(function() {
	var pics = $F('pics');
	var spics = $F('spics');
	var picBox = '';
	if(!$E(pics)) {
		picBox = '<ul class="cf">';
		var picsArray = pics.split(',');
		picCount = picsArray.length;
		for(var i = 0; i < picsArray.length; i++) {
			var picsPath = picsArray[i];
			picBox = picBox + '<li style="text-align:center">'+
			'<img src="'+ctx+picsPath+'" />' +
		    '</li>';
		}
		picBox = picBox +'</ul>';
	}	
	
	var listBox = '';
	if(!$E(spics)) {
		listBox = '<ul class="cf">';
		var spicsArray = spics.split(',');
		for(var i = 0; i < spicsArray.length; i++) {
			var spicsPath = spicsArray[i];
			if(i == 0) {
				listBox = listBox + '<li class="on"><i class="arr2"></i>'+
				'<img src="'+ctx+spicsPath+'"/></li>';
			} else {
				listBox = listBox + '<li><i class="arr2"></i>'+
				'<img src="'+ctx+spicsPath+'"/></li>';
			}
		}
		listBox = listBox + '</ul>';
	}
	
	if($E(pics) && $E(spics)) {
		$("mod18").style.display='none';
		$("mod19").style.display='none';
		$("noData").innerHTML=main.insMgt.fb.text;
	} else {
		$("noData").style.display='none';
		$("picBox").innerHTML=picBox;
		$('listBox').innerHTML=listBox;
		init(picCount);
	}
	
});

function init(count) {
	oPic = $("picBox");
	oList = G("listBox");

	oPrev = G("prev");
	oNext = G("next");

	oPicLi = oPic.getElementsByTagName("li");
	oListLi = oList.getElementsByTagName("li");
	len1 = oPicLi.length;
	len2 = oListLi.length;
	
	oPicUl = oPic.getElementsByTagName("ul")[0];
	oListUl = oList.getElementsByTagName("ul")[0];
	w1 = oPicLi[0].offsetWidth;
	w2 = oListLi[0].offsetWidth;

	oPicUl.style.width = w1 * len1 + "px";
	oListUl.style.width = w2 * len2 + "px";
	
	index = 0;

	num = 5;
	num2 = Math.ceil(num / 2);
	
	for (var i = 0; i < len2; i++) {
		oListLi[i].index = i;
		oListLi[i].onclick = function(){
			index = this.index;
			Change();
		}
	}
	
	oNext.onclick = function(){
		index ++;
		index = index == len2 ? 0 : index;
		Change();
	}

	oPrev.onclick = function(){
		index --;
		index = index == -1 ? len2 -1 : index;
		Change();
	}
}

function G(s){
	return document.getElementById(s);
}

function getStyle(obj, attr){
	if(obj.currentStyle){
		return obj.currentStyle[attr];
	}else{
		return getComputedStyle(obj, false)[attr];
	}
}

function Animate(obj, json){
	if(obj.timer){
		clearInterval(obj.timer);
	}
	obj.timer = setInterval(function(){
		for(var attr in json){
			var iCur = parseInt(getStyle(obj, attr));
			iCur = iCur ? iCur : 0;
			var iSpeed = (json[attr] - iCur) / 2;
			iSpeed = iSpeed > 0 ? Math.ceil(iSpeed) : Math.floor(iSpeed);
			obj.style[attr] = iCur + iSpeed + 'px';
			if(iCur == json[attr]){
				clearInterval(obj.timer);
			}
		}
	}, 30);
}

function Change(){

	Animate(oPicUl, {left: - index * w1});
	
	if(index < num2){
		Animate(oListUl, {left: 0});
	}else if(index + num2 <= len2){
		Animate(oListUl, {left: - (index - num2 + 1) * w2});
	}else{
		Animate(oListUl, {left: - (len2 - num) * w2});
	}

	for (var i = 0; i < len2; i++) {
		oListLi[i].className = "";
		if(i == index){
			oListLi[i].className = "on";
		}
	}
}