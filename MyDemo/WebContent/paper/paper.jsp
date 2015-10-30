<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>试卷管理</title>
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
.x-grid3-row td, .x-grid3-summary-row td{  
            line-height:18px;
        }
</style>
<script type="text/javascript" >
var paperrecords;
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	var expander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template("<p style='padding:5px;border:1px #DFE8F6solid;margin:2px;'><span style='color:#15428B; font-weight:bold;'>备注：</span>{memo}</p>")
		});
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([
	     expander,
	     sm,
	     {id: "paperid",header: "编号", width: 40, dataIndex: "paperid", align: "center"}, 
	     {id:"papername", header: "试卷名称", dataIndex: "papername", align: "center"}, 
	     {header:"岗位类型名称", width:200, dataIndex:"stationname", align: "center",
	    	renderer:function(v){
	    		if(v==""||v==null){
	    			return "多岗位试卷";
	    		}else{
	    			return v;
	    		}
	    	}	 
	     },
	     {header:"日期", width:150,dataIndex:"makedate", align: "center"},
	     {header:"操作", width:150, dataIndex:"", menuDisabled: true, align: "center",
		     renderer: function(v,record,store){
				 var url ="<%=request.getContextPath()%>/paperView/preview?paperid=";
				 url = url+store.data['paperid'];
		     	 return "<span style='margin-right:10px'><a href='"+ url+"'  target='_Blank'>预览 </a>";
		     }
	     }
	]);
	 var proxy = new Ext.data.HttpProxy({url:"paperManage/queryPage"});
	 var paperRecord = Ext.data.Record.create([ 
	    	{name : "paperid",type : "int",mapping : "paperid"},
	    	{name : "papername",type : "string",mapping : "papername"},
	    	{name : "makedate",type : "string",mapping : "makedate"},
	    	{name : "memo",type : "string",mapping : "memo"},
	    	{name : "stationid",type : "int",mapping : "stationid"},
	    	{name : "stationname",type : "string",mapping : "stationname"}
		]);
		var reader = new Ext.data.JsonReader({totalProperty : "count",root : "pageData"}, paperRecord);
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

	    var papergrid = new Ext.grid.GridPanel({
			title : "试卷列表",
			autoHeight : true,
			width : 1000,
			cm : cm,
			sm : sm,
			plugins : expander,
			store : store,
			bbar : pageToolbar,
			renderTo : "child4",
			autoExpandColumn : "papername",
			tbar : [{
					  text : "人工生成",
					  icon : "images/add.png",
					  cls : "x-btn-text-icon",
					  handler : function() {
							 var table1 = center.getComponent("grandson1");
							 if(table1) {
									 center.setActiveTab(table1);//检验当前Tab选项卡是否存在，如果存在只需要激活
									 
								 }else{
									 p = new Ext.Panel({
											title : grandson1.attributes.text,
											autoLoad : {
												url : grandson1.attributes.url,
												scripts : true
											},
											closable : true,
											id : grandson1.attributes.id
									 });
									 center.add(p);
									 center.setActiveTab(p);
							  }
					  }
				  },{
					  text : "系统生成",
					  icon : "images/add.png",
					  cls : "x-btn-text-icon",
					  handler : function() {
							 var table2 = center.getComponent("grandson2");
							 if(table2) {
									 center.setActiveTab(table2);//检验当前Tab选项卡是否存在，如果存在只需要激活
								 }else{
									 p = new Ext.Panel({
											title : grandson2.attributes.text,
											autoLoad : {
												url : grandson2.attributes.url,
												scripts : true
											},
											closable : true,
											id : grandson2.attributes.id
									 });
									 center.add(p);
									 center.setActiveTab(p);
								 }
					  }
				  }, /* {
					  text : "重新生成",
					  icon : "images/edit.png",
					  cls : "x-btn-text-icon",
					  handler : function() {
						  var selModel = papergrid.getSelectionModel();
						  paperrecords = selModel.getSelected();
						  if (!selModel.hasSelection()) {
							  Ext.Msg.alert("错误","请选择要修改的行!");
						  } else if (selModel.getSelections().length > 1) {
							  Ext.Msg.alert("错误","一次只能修改一行,不行同时选择多行!");
						  } else {
							  center.getActiveTab().load({url:'paper/updatepaperbyman.jsp',scripts:true}); 
						  }
					  }
				  }, */{
					  text : "导出doc",
					  icon : "images/edit.png",
					  cls : "x-btn-text-icon",
					  handler : function() {
						  var selModel = papergrid.getSelectionModel();
						  paperrecords = selModel.getSelected();
						  if (!selModel.hasSelection()) {
							  Ext.Msg.alert("错误","请选择要导出的行!");
						  } else if (selModel.getSelections().length > 1) {
							  Ext.Msg.alert("错误","一次只能导出一行,不行同时选择多行!");
						  } else {
							  paperrecords = selModel.getSelected();
/* 							  var hiddenid = new Ext.form.Hidden({
								  name:"tqsVo.paperid",
								  value : paperrecords.get("paperid")
							  });
 */							  if (selModel.hasSelection()) {
									Ext.Msg.confirm(" 确 认 "," 您 确 定 要 导出 选 择 的 记 录 吗 ?",
										function(btn) {
											if (btn == "yes") {
												Ext.Ajax.request({
													url : "paperView/printpreview",
													params : {paperid : paperrecords.get("paperid")},
													method : "post",
												    success:function(res){
											          var obj =Ext.decode(res.responseText);
											          window.open(obj.path) ;
												    }
												});
											}
									});
							  }
						  }
					  }
				  },{
					  icon : "images/cross.png",
					  cls : "x-btn-text-icon",
					  text : "批量删除",
					  handler : function() {
						var selModel = papergrid.getSelectionModel();
						if (selModel.hasSelection()) {
							Ext.Msg.confirm(" 确 认 "," 您 确 定 要 删 除 选 择 的 记 录 吗 ?",
								function(btn) {
									if (btn == "yes") {
										var records = selModel.getSelections();
										var ids = [];
										for (var i = 0; i < records.length; i++) {
											ids.push(records[i].get("paperid"));
										}
										Ext.Ajax.request({
											url : "paperManage/deleteByIds",
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
							new Ext.form.TriggerField({
							  id : "keyword",
							  triggerClass : "x-form-search-trigger", 
							  emptyText : "请输入类型名称",
							  onTriggerClick : function() {
								  var v = Ext.get("keyword").getValue();
								  var searchProxy = new Ext.data.HttpProxy({url : "paperManage/queryPageByName"});
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
<div id="child4"></div>
  <br />
</body>
</html>
