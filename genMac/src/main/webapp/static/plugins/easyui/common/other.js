/**
 * EasyUI DataGrid根据字段动态合并单元格 参数 tableID 要合并table的id 参数 colList
 * 要合并的列,用逗号分隔(例如："name,department,office");
 */
function mergeCellsByField(tableID, colList) {
	var ColArray = colList.split(",");
	var tTable = $("#" + tableID);
	var TableRowCnts = tTable.datagrid("getRows").length;
	var tmpA;
	var tmpB;
	var PerTxt = "";
	var CurTxt = "";
	var alertStr = "";
	for (j = ColArray.length - 1; j >= 0; j--) {
		PerTxt = "";
		tmpA = 1;
		tmpB = 0;

		for (i = 0; i <= TableRowCnts; i++) {
			if (i == TableRowCnts) {
				CurTxt = "";
			} else {
				CurTxt = tTable.datagrid("getRows")[i][ColArray[j]];
			}
			if (PerTxt == CurTxt) {
				tmpA += 1;
			} else {
				tmpB += tmpA;

				tTable.datagrid("mergeCells", {
					index : i - tmpA,
					field : ColArray[j], // 合并字段
					rowspan : tmpA,
					colspan : null
				});
				tTable.datagrid("mergeCells", { // 根据ColArray[j]进行合并
					index : i - tmpA,
					field : "Ideparture",
					rowspan : tmpA,
					colspan : null
				});

				tmpA = 1;
			}
			PerTxt = CurTxt;
		}
	}
}

/**
 * 格式化时间
 */
function formatterDate (formatter, value) {
	var date = new Date();
	if (value != null) {
		date = new Date(value);
	}
	var year = date.getFullYear().toString();
	var month = (date.getMonth() + 1);
	var day = date.getDate().toString();
	var hour = date.getHours().toString();
	var minutes = date.getMinutes().toString();
	var seconds = date.getSeconds().toString();
	if (month < 10) {
	    month = "0" + month;
	}
	if (day < 10) {
	    day = "0" + day;
	}
	if (hour < 10) {
	    hour = "0" + hour;
	}
	if (minutes < 10) {
	    minutes = "0" + minutes;
	}
	if (seconds < 10) {
	    seconds = "0" + seconds;
	}
	return formatter == "yyyy-MM-dd" ? year + "-" + month + "-" + day : 
		year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
}

/**
 * 返回流程审批状态
 */
function getState(url, value){
	var str = [];
	if(value == "已提交"){
		$.ajax({
			url : url,
			type : 'get',
			async : false,
			dataType : 'json',
			success : function(data) {
				$.each(data, function(index, value){
					str.push(value);
				});
			}
		});
		return value + "(" + str.join(",") + ")";
	}
	return value;
}
