<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>题目类型管理</title>
<style type="text/css">
.x-tree-node {
	font-size: 14px;
}

.x-panel-header {
	font-size: 15px;
}

.x-tab-strip span.x-tab-strip-text {
	font-size: 12px;
}
</style>
<script type="text/javascript" >
var record;
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	var expander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template("<p style='padding:5px;border:1px #DFE8F6solid;margin:2px;'><span style='color:#15428B; font-weight:bold;'>正确答案：</span>{correct}</p>")
		});
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([
	     expander,
	     sm,
	     {header: "编号", width: 40, dataIndex: "tqid", align: "center"}, 
	     {id:"questionname", header: "题目", dataIndex: "questionname", align: "center"}, 
	     {header:"题目类型名称", width:150, dataIndex:"qttype", align: "center"},
	     {header:"岗位类型名称", width:150, dataIndex:"stationname", align: "center"},
	     {header:"操作", width:150, dataIndex:"", menuDisabled: true, align: "center",
		     renderer: function(v){
		     		 return "<span style='margin-right:10px'><a href='#'>修改 </a></span><span><a href='#'>删除</a></span>";
		     }
	     }
	]);
	
	 var proxy = new Ext.data.HttpProxy({url:"testquestions/queryPage"});
	 var questiontype = Ext.data.Record.create([ 
	    	{name : "tqid",type : "int",mapping : "tqid"},
	    	{name : "correct",type : "string",mapping : "correct"},
	    	{name : "questionname",type : "string",mapping : "questionname"},
	    	{name : "questionoption",type : "string",mapping : "questionoption"},
	    	{name : "stationid",type : "int",mapping : "stationid"},
	    	{name : "stationname",type : "string",mapping : "stationname"},
	    	{name : "qtid",type : "int",mapping : "qtid"},
	    	{name : "qttype",type : "string",mapping : "qttype"},
	    	{name : "isjudge",type : "int",mapping : "isjudge"}
		
    	]);
		var reader = new Ext.data.JsonReader({totalProperty : "count",root : "pageData"}, questiontype);
		var store = new Ext.data.Store({
			proxy : proxy,
			reader : reader
		});
		store.load({params : {start : 0,limit : 5}});

		var pageToolbar = new Ext.PagingToolbar({
			store : store,
			pageSize : 5,
			beforePageText : "第",
			afterPageText : "页 共{0}页",
			displayInfo : true,
			displayMsg : "当前显示从{0}条到{1}条,共{2}条",
			emptyMsg : "<span style='color:red;font-style:italic;'>对不起,没有找到数据</span>"
		});

	    tqgrid = new Ext.grid.GridPanel({
			title : "试题列表",
			autoHeight : true,
			width : 1000,
			cm : cm,
			sm : sm,
			plugins : expander,
			store : store,
			bbar : pageToolbar,
			renderTo : "child3",
			autoExpandColumn : "questionname",
			tbar : [{
					  text : "新建试题",
					  icon : "images/add.png",
					  cls : "x-btn-text-icon",
					  handler : function() {
						 center.getActiveTab().load({url:'paper/addquestion.jsp',scripts:true});
					  }
				  },{
					  text : "修改试题",
					  icon : "images/edit.png",
					  cls : "x-btn-text-icon",
					  handler : function() {
						  var selModel = tqgrid.getSelectionModel();
						  record = selModel.getSelected();;
						  if (!selModel.hasSelection()) {
							  Ext.Msg.alert("错误","请选择要修改的行!");
						  } else if (selModel.getSelections().length > 1) {
							  Ext.Msg.alert("错误","一次只能修改一行,不行同时选择多行!");
						  } else {
					  		  center.getActiveTab().load({url:'paper/updatequestion.jsp',scripts:true});
						  }
					  }
				  },{
					  icon : "images/cross.png",
					  cls : "x-btn-text-icon",
					  text : "批量删除",
					  handler : function() {
						var selModel = tqgrid.getSelectionModel();
						if (selModel.hasSelection()) {
							Ext.Msg.confirm(" 确 认 "," 您 确 定 要 删 除 选 择 的 记 录 吗 ?",
								function(btn) {
									if (btn == "yes") {
										var records = selModel.getSelections();
										var ids = [];
										for (var i = 0; i < records.length; i++) {
											ids.push(records[i].get("tqid"));
										}
										Ext.Ajax.request({
											url : "testquestions/deleteByIds",
											params : {ids : ids},
											method : "post",
											success : function(response) {
												var json = Ext.util.JSON.decode(response.responseText);
												Ext.MessageBox.alert("结果",json.msg);
												store.proxy = proxy;
												store.reload();
											},
											failure : function() {
												Ext.Msg.alert("结果","对不起,删除失败!!!!!");
											}
										});
										store.reload();
									}
								});
							} else {
								Ext.Msg.alert("错误","请选择要删除的行!");
							}
						}
					 },{
							icon : "images/refresh.png",
							cls : "x-btn-text-icon",
							text : "查看全部",
							handler : function() {
								store.proxy = proxy;
								store.reload();
							}
						},
							new Ext.Toolbar.Fill(),
							new Ext.Toolbar.TextItem("搜索:"),
							new Ext.form.TriggerField(
							{
							  id : "keyword",
							  triggerClass : "x-form-search-trigger", 
							  emptyText : "请输入类型名称",
							  onTriggerClick : function() {
								  var v = Ext.get("keyword").getValue();
								  var searchProxy = new Ext.data.HttpProxy({url : "testquestions/queryPageByName"});
								  store.proxy = searchProxy;
								  store.load({params : {start : 0,limit : 5,type : v}});
							  }
							})
				]
		});
})
</script>
</head>
<body>
<div id="child3"></div>
</body>
</html>
