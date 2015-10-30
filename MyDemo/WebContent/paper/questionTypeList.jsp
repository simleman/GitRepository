<%@ page language="java" contentType="text/html;charset=UTF-8"
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
.x-form-check-wrap {
	padding-top: 3px;
}
</style>
<script type="text/javascript">
Ext.onReady(function(){
		Ext.QuickTips.init();
		Ext.form.Field.prototype.msgTarget = 'side';
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),
			sm,
			{header : "编号",width : 40,dataIndex : "id",align : "center"},
			{id : "type",header : "题目类型名",dataIndex : "type",align : "center"},
			{header : "是否是选择题",width : 150,dataIndex : "ischoose",align : "center",
				renderer : function(v) {
					if (v == 1) {
						return "是";
					} else {
						return "否";
					}
				}
			},
			{header : "是否是判断题",width : 150,dataIndex : "isjudge",align : "center",
				renderer : function(v) {
					if (v == 1) {
						return "是";
					} else {
						return "否";
					}
				}
			},
			{header : "操作",width : 150,dataIndex : "",menuDisabled : true,align : "center",
				renderer : function(v) {
					return "<span style='margin-right:10px'><a href='#'>修改 </a></span><span><a href='#'>删除</a></span>";
				}
			} 
		]);
	var proxy = new Ext.data.HttpProxy({url:"questionType/queryPage"});
	var questiontype = Ext.data.Record.create([ 
	  {name : "id",type : "int",mapping : "id"},
	  {name : "type",type : "string",mapping : "type"},
	  {name : "ischoose",type : "int",mapping : "ischoose"},
	  {name : "isjudge",type : "int",mapping : "isjudge"},
	  {name : "anwsercount",type : "int",mapping : "anwsercount"} 
	  ]);

	var reader = new Ext.data.JsonReader({totalProperty : "count",root : "pageData"}, questiontype);
	var store = new Ext.data.Store({proxy : proxy,reader : reader});
	store.load({params:{start:0,limit:5}});

	var pageToolbar = new Ext.PagingToolbar({
		store:store,
		pageSize:5,
		beforePageText:"第",
		afterPageText:"页 共{0}页",
		displayInfo:true,
		displayMsg:"当前显示从{0}条到{1}条,共{2}条",
		emptyMsg:"<span style='color:red;font-style:italic;'>对不起,没有找到数据</span>"
	});

	var grid = new Ext.grid.GridPanel({
				title:"题目类型列表",
				autoHeight:true,
				width:1000,
				cm :cm,
				sm:sm,
				store:store,
				bbar:pageToolbar,
				renderTo:"child1",
				autoExpandColumn:"type",
				tbar:[
					  {
						text : "新建试题类型",
						icon : "images/add.png",
						cls : "x-btn-text-icon",
						handler:function(){
							var type = new Ext.form.TextField({
								fieldLabel:"类型名称",
								name:"qtype.type",
								allowBlank : false
							});
							var yes = new Ext.form.Radio({
								name:"qtype.ischoose",
								inputValue:"1",
								boxLabel:"是",
							});
							var no = new Ext.form.Radio({
								name:"qtype.ischoose",
								inputValue:"0",
								boxLabel:"否",
							});
							var ischoose = new Ext.form.RadioGroup({
								name:"qtype.ischoose",
								fieldLabel:"是不是选择题",
								items:[yes,no],
								width:80
							});
							var judgeyes = new Ext.form.Radio({
								name:"qtype.isjudge",
								inputValue:"1",
								boxLabel:"是",
							});
							var judgeno = new Ext.form.Radio({
								name:"qtype.isjudge",
								inputValue:"0",
								boxLabel:"否",
							});
							var isjudge = new Ext.form.RadioGroup({
								name:"qtype.isjudge",
								fieldLabel:"是不是判断题",
								items:[judgeyes,judgeno],
								width:80
							});
							var anwsercount = new Ext.form.NumberField({
								name : "qtype.anwsercount",
								fieldLabel : "答案的个数"
							});
							ischoose.on('change',function(rdgroup,checked){
								if (checked.getRawValue() != 1) {
									isjudge.getEl().up('.x-form-item').show();
									anwsercount.getEl().up('.x-form-item').hide();
								} else {
									isjudge.getEl().up('.x-form-item').hide();
									anwsercount.getEl().up('.x-form-item').show();
								}
							});
							isjudge.on('change',function(rdgroup,checked){
								if (checked.getRawValue() != 1) {
									ischoose.getEl().up('.x-form-item').show();
									anwsercount.getEl().up('.x-form-item').show();
								} else {
									ischoose.getEl().up('.x-form-item').hide();
									anwsercount.getEl().up('.x-form-item').hide();
								}
							});
								var form = new Ext.form.FormPanel({
								frame : true,
								items : [ type,ischoose,isjudge,anwsercount ],
								url : "questionType/addType",
								method : "post",
								buttons : [
									{
										text : "增加",
										handler : function() {
											var json = {
												success : function(f,action) {
													Ext.Msg.alert("成功",action.result.msg); 
													store.load({params:{start:0,limit:5}});
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
									}]
							});
								var win = new Ext.Window({
									title : "增加题目类型",
									id : "edit",
									width : 500,
									modal : true,
									autoHeight : true,
									items : [ form ]
								});
								win.show();
							}
						},
						{
							text : "修改试题类型",
							icon : "images/edit.png",
							cls : "x-btn-text-icon",
							handler : function() {
								var selModel = grid.getSelectionModel();
								var record;
								if (!selModel.hasSelection()) {
									Ext.Msg.alert("错误","请选择要修改的行!");
								} else if (selModel.getSelections().length > 1) {
									Ext.Msg.alert("错误","一次只能修改一行,不行同时选择多行!");
								} else {
									record = selModel.getSelected();
									var hiddenid = new Ext.form.Hidden({name:"qtype.id"});
									var type = new Ext.form.TextField({
										fieldLabel:"类型名称",
										name:"qtype.type",
										allowBlank:false
										});
									var yes = new Ext.form.Radio({
										name : "qtype.ischoose",
										inputValue : "1",
										boxLabel : "是",

									});
									var no = new Ext.form.Radio({
										name : "qtype.ischoose",
										inputValue : "0",
										boxLabel : "否",

									});
									var ischoose = new Ext.form.RadioGroup({
										name : "qtype.ischoose",
										fieldLabel : "是不是选择题",
										items : [ yes, no ],
										width : 80
									});
									var judgeyes = new Ext.form.Radio({
										name:"qtype.isjudge",
										inputValue:"1",
										boxLabel:"是",
									});
									var judgeno = new Ext.form.Radio({
										name:"qtype.isjudge",
										inputValue:"0",
										boxLabel:"否",
									});
									var isjudge = new Ext.form.RadioGroup({
										name:"qtype.isjudge",
										fieldLabel:"是不是判断题",
										items:[judgeyes,judgeno],
										width:80
									});
									var anwsercount = new Ext.form.NumberField({
										name : "qtype.anwsercount",
										fieldLabel : "答案的个数"
									});
									ischoose.on('change',function(rdgroup,checked){
										if (checked.getRawValue() != 1) {
											isjudge.getEl().up('.x-form-item').show();
											anwsercount.getEl().up('.x-form-item').hide();
										} else {
											isjudge.getEl().up('.x-form-item').hide();
											anwsercount.getEl().up('.x-form-item').show();
										}
									});
									isjudge.on('change',function(rdgroup,checked){
										if (checked.getRawValue() != 1) {
											ischoose.getEl().up('.x-form-item').show();
											anwsercount.getEl().up('.x-form-item').show();
										} else {
											ischoose.getEl().up('.x-form-item').hide();
											anwsercount.getEl().up('.x-form-item').hide();
										}
									});
									var form = new Ext.form.FormPanel({
										frame : true,
										items : [ hiddenid,type,ischoose,isjudge,anwsercount ],
										url : "questionType/updateType",
										method : "post",
										buttons : [
											{
												text : "修改",
												handler : function() {
													var json = {
														success : function(f,action) {
															Ext.Msg.alert("成功",action.result.msg);
															store.reload();
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
											}]
									});
									var win = new Ext.Window({
										title : "修改题目类型",
										id:"edit",
										width:500,
										modal:true,
										autoHeight:true,
										items:[form]
									});
									win.show();
									form.getForm().setValues({
										"qtype.id" : record.get("id"),
										"qtype.type" : record.get("type"),
										"qtype.ischoose" : record.get("ischoose"),
										"qtype.isjudge" : record.get("isjudge"),
										"qtype.anwsercount" : record.get("anwsercount")
									});
								}
							}
						},
						{
							icon : "images/cross.png",
							cls : "x-btn-text-icon",
							text : "批量删除",
							handler : function() {
								var selModel = grid.getSelectionModel();
								if (selModel.hasSelection()) {
									Ext.Msg.confirm(" 确 认 "," 您 确 定 要 删 除 选 择 的 记 录 吗 ?",
										function(btn) {
											if (btn == "yes") {
												var records = selModel.getSelections();
												var ids = [];
												for (var i = 0; i < records.length; i++) {
													ids.push(records[i]
																	.get("id"));
												}
												Ext.Ajax.request({
													url : "questionType/deleteByIds",
													params : {ids : ids},
													method : "post",
													success : function(response){
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
								store.load({params:{start:0,limit:5},add:false});
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
								var searchProxy = new Ext.data.HttpProxy({
									url : "questionType/queryPageByName"
								});
								store.proxy = searchProxy;
								store.load({params : {start : 0,limit : 5,type : v}
								});
							}
						})
					]
			});
})
</script>
</head>
<body>
	<div id="child1"></div>
</body>
</html>