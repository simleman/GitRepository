<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>岗位类型管理</title>
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
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	var sim = new Ext.grid.CheckboxSelectionModel();
	var cim = new Ext.grid.ColumnModel([
	     new Ext.grid.RowNumberer(),
	     sim, 
	     {header: "编号", width: 150, dataIndex: "id", align: "center"}, 
	     {header: "岗位类型名称", dataIndex: "name", align: "center",width: 300}, 
	     {id:"opration",header:"操作", width:150, dataIndex:"", menuDisabled: true, align: "center",
		     renderer: function(v){
		     		 return "<span style='margin-right:10px'><a href='#'>修改 </a></span><span><a href='#'>删除</a></span>";
		      }
	      }
	]);
	var stationproxy = new Ext.data.HttpProxy({url:"station/queryPage"});
	var stationtype = Ext.data.Record.create([
       {name : "id",type : "int",mapping : "id"}, 
       {name : "name",type : "string",mapping : "name"}
	 ]);

	 var stationreader = new Ext.data.JsonReader({totalProperty : "count",root : "pageData"}, stationtype);
	 var stationstore = new Ext.data.Store({proxy : stationproxy,reader : stationreader});
	 stationstore.load({params : {start : 0,limit : 5}});

	 var stationpageToolbar = new Ext.PagingToolbar({
		store : stationstore,
		pageSize : 5,
		beforePageText : "第",
		afterPageText : "页 共{0}页",
		displayInfo : true,
		displayMsg : "当前显示从{0}条到{1}条,共{2}条",
		emptyMsg : "<span style='color:red;font-style:italic;'>对不起,没有找到数据</span>"
	 });

	var stationgrid = new Ext.grid.GridPanel({
		title : "岗位类型列表",
		autoHeight : true,
		width : 1000,
		cm : cim,
		sm : sim,
		store : stationstore,
		bbar : stationpageToolbar,
		renderTo : "child2",
		autoExpandColumn : "opration",
		tbar : [{
				  text : "新建岗位类型",
				  icon : "images/add.png",
				  cls : "x-btn-text-icon",
				  handler : function() {
						var name = new Ext.form.TextField({
							  fieldLabel : "类型名称",
							  name : "stype.name",
							  allowBlank : false
						});
						var stationform = new Ext.form.FormPanel({
							  frame : true,
							  items : [name],
							  url : "station/addType",
							  method : "post",
							  buttons : [{
								  text : "增加",
								  handler : function() {
										var json = {
											success : function(f,action) {
												Ext.Msg.alert("成功",action.result.msg);
												stationstore.reload();
												stationwin.close();
											},
											failure : function() {
												Ext.Msg.alert("失败","对不起， 操作失败 请检查数据是否完整！");
											}
										};
										stationform.getForm().submit(json);
								  }
							  },{
								  text : "关闭",
								  handler : function() {
									  stationwin.close();
								  }
							 }]
						  });
						  var stationwin = new Ext.Window({
							  title : "增加岗位类型",
							  id : "edit",
							  width : 500,
							  modal : true,
							  autoHeight : true,
							  items : [ stationform ]
						  });
						  stationwin.show();	
				  }
			   },{
				  text : "修改岗位类型",
				  icon : "images/edit.png",
				  cls : "x-btn-text-icon",
				  handler : function() {
						var stationselModel = stationgrid.getSelectionModel();
						var stationrecord;
						if (!stationselModel.hasSelection()) {
							Ext.Msg.alert("错误","请选择要修改的行!");
						} else if (stationselModel.getSelections().length > 1) {
							Ext.Msg.alert("错误","一次只能修改一行,不行同时选择多行!");
						} else {
							stationrecord = stationselModel.getSelected();
							var stationhiddenid = new Ext.form.Hidden({name:"stype.id"});
							var name = new Ext.form.TextField({
							  fieldLabel : "类型名称",
							  name : "stype.name",
							  allowBlank : false
							});
							var form = new Ext.form.FormPanel({
							  frame : true,
							  items : [stationhiddenid,name],
							  url : "station/updateType",
							  method : "post",
							  buttons : [{
								text : "修改",
								handler : function() {
									var json = {
										success : function(f,action) {
											Ext.Msg.alert("成功",action.result.msg);
											stationstore.reload();
											win.close();
										},
										failure : function() {
											Ext.Msg.alert("失败","对不起， 操作失败 请检查数据是否完整！");
										}
									};
									form.getForm().submit(json);
								}
							  },{
								  text : "关闭",
								  handler : function() {
									win.close();
								  }
								 }
							  ]
							});
							var win = new Ext.Window({
								title : "修改岗位类型",
								id : "edit",
								width : 500,
								modal : true,
								autoHeight : true,
								items : [ form ]
							});
							win.show();
							form.getForm().setValues({
							  "stype.id" : stationrecord.get("id"),
							  "stype.name" : stationrecord.get("name"),
							});
						}
					 }
				},{
					icon : "images/cross.png",
					cls : "x-btn-text-icon",
					text : "批量删除",
					handler : function() {
						var selModel = stationgrid.getSelectionModel();
						if (selModel.hasSelection()) {
							Ext.Msg.confirm(" 确 认 "," 您 确 定 要 删 除 选 择 的 记 录 吗 ?",
								function(btn) {
									if (btn == "yes") {
										var records = selModel.getSelections();
										var ids = [];
										for (var i = 0; i < records.length; i++) {
											ids.push(records[i].get("id"));
										}
										Ext.Ajax.request({
											url : "station/deleteByIds",
											params : {ids : ids},
											method : "post",
											success : function(response) {
												var json = Ext.util.JSON.decode(response.responseText);
												Ext.MessageBox.alert("结果",json.msg);
												stationstore.proxy = stationproxy;
												stationstore.reload();
											},
											failure : function() {
												Ext.Msg.alert("结果","对不起,删除失败!!!!!");
											}
										});
										stationstore.reload();
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
							stationstore.proxy = stationproxy;
							stationstore.reload();
						}
					},
						new Ext.Toolbar.Fill(),
						new Ext.Toolbar.TextItem("搜索:"),
						new Ext.form.TriggerField({
							  id : "keyword",
							  triggerClass : "x-form-search-trigger", 
							  emptyText : "请输入类型名称",
							  onTriggerClick : function() {
								  var v = Ext.get("keyword").getValue();
								  var searchProxy = new Ext.data.HttpProxy({url : "station/queryPageByName"});
								  stationstore.proxy = searchProxy;
								  store.load({params : {start : 0,limit : 5,type : v}});
						      }
						})
		]
	});
})
</script>
</head>
<body>
<div id="child2"></div>
</body>
</html>