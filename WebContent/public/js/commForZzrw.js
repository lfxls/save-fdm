/**
 * 主站任务功能页面使用的公共JS方法
 * 包括采集任务管理、数据召测、参数管理等页面
 * @author cw_hgb
 */
// 获取表格中某一列的所有值，并组成字符串(以","隔开)
function getGridColumn(record, column) {
	var result = "";
	if(record) {
		for(var i = 0; i < record.length; i ++) {
			result += record[i].get(column) + ",";
		}
		result = result.substring(0, result.length - 1);
	}
	return result;
}

// 获取表格中key列为value值的某一列所有值，并组成字符串(以","隔开)
function getGridColumnByKey(record, column, key, value) {
	var result = "";
	if(record) {
		for(var i = 0; i < record.length; i ++) {
			if(record[i].get(key) == value) {
				result += record[i].get(column) + ",";
			}
		}
		if(result != "") {
			result = result.substring(0, result.length - 1);
		}
	}
	return result;
}

// 获取List中所有行的值，并组成字符串(以","隔开)
function getListRow(record) {
	var result = "";
	if(record) {
		for(var i = 0; i < record.length; i ++) {
			result += record.options[i].value + ",";
		}
		result = result.substring(0, result.length - 1);
	}
	return result;
}

//取得各参数值，并组成字符串(以","隔开)
function getParamByName(name) {
	var result = "";
	var obj = document.getElementsByName(name);
	for(var i = 0; i < obj.length; i++) {
		result += obj[i].value + ",";
	}
	if(result != "") {
		result = result.substring(0, result.length - 1);
	}
	return result;
}
