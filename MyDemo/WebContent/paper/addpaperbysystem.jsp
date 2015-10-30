<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统生成</title>
<style type="text/css">
#plabel{padding-left: 12px;font-size: 12px}
.x-form-item {margin-bottom: 10px;margin-top: 6px;}
.x-panel-mc {font-size: 12px}
.x-column{margin-top: 2px;}
#ext-gen27{margin-top: 3px;}
.x-column-inner{margin-top: 1px; }
#pn{margin-left: 70px;}
</style>
<script type="text/javascript">
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	var questionname = new Ext.form.TextField({
		fieldLabel : "试卷名称",
		name : "tqsVo.papername",
		width:300,
		allowBlank : false
	});
	var memo = new Ext.form.HtmlEditor({
		fieldLabel : "试卷备注",
		name : "tqsVo.memo",
		enableLists: false,
		enableSourceEdit: false,
		height: 150,
		width : 500,
		value:"关于试卷的相关情况"
	}); 
	var plabel = new Ext.form.Label({
    	id:"plabel",
    	text:"选择试题:",
    	hidden : true
    });
	var pn=new Ext.Panel({
		   id:"pn",
		   autoHeight:true, 
		   columnWidth:.8,
		   layout:'column'
	});  
	///------------------------------------岗位类型选择
	var stProxy = new Ext.data.HttpProxy({url:"station/queryPage"});
	var stRecord = Ext.data.Record.create([ 
	  {name : "stid",type : "int",mapping : "id"},
	  {name : "stname",type : "string",mapping : "name"}
	]);
	var stReader = new Ext.data.JsonReader({},stRecord);
	var store1 = new Ext.data.Store({
		proxy : stProxy,
		reader : stReader,
		autoLoad : true
	});
	store1.load();
	var station = new Ext.form.MultiSelect({
		fieldLabel:"岗位类型",  
        width: 120,  
        editable: false,  
        hiddenName:'tqsVo.stationid',  
        store: store1,  
        emptyText: '请选择岗位类型',  
        blankText: '请选择',   
        mode:'remote',  
        displayField: "stname",  
        valueField: "stid",  
        triggerAction: 'all',  
        selectOnFocus: true,  
        listWidth:240  
	});
	
	station.on('select',function(){
		var j = 0;
		while(Ext.getCmp("pnx"+j)!=null){
			pn.remove(Ext.getCmp("pnx"+j),true);
			j++;
		} 
		var stationid = station.getValue();
		var tqsProxy = new Ext.data.HttpProxy({url:"testquestions/count"});
		var tqsRecord = Ext.data.Record.create([ 
		  {name : "qtid",type : "int",mapping : "qtid"},                                     
		  {name : "num",type : "int",mapping : "num"},
		  {name : "type", type : "string",mapping:"type"}
		]);
		var tqsReader = new Ext.data.ArrayReader({},tqsRecord);
		var tqsstore = new Ext.data.Store({
			proxy : tqsProxy,
			reader : tqsReader,
			listeners:{
				load:function(){
					plabel.show();
					for (var i = 0; i < tqsstore.getCount(); i++) {
						var record = tqsstore.getAt(i);
						var qtid = record.get("qtid");
						var num = record.get("num");
						var type = record.get("type");
						var pnx=new Ext.Panel({
							   id : "pnx"+i,
							   autoHeight:true, 
							   columnWidth:.3,
							   layout:'form'
						});  
						var hiddenid = new Ext.form.Hidden({
							name:"qtid",
							value:qtid
						});
						var fcount = new Ext.form.NumberField({
							id:"count"+i,
							name : "count",
							fieldLabel : type+"("+num+")",
							value:0,
							maxValue: num, 
				            minValue: 0,
							width:40
						});
						pnx.add(hiddenid);
						pnx.add(fcount);
						pnx.doLayout();
						pn.add(pnx);
						pn.doLayout();
					}
				}
			}
		});
		tqsstore.load({params:{stationid:stationid}});
		
	});
	
	var form = new Ext.form.FormPanel({
		title : "新增试卷",
		layout: "form", 
		autoHeight: true,
		width: 650, 
        labelWidth: 65, 
        labelAlign: "right",
		frame : true,
		items : [questionname, station,plabel,pn, memo],
		method : "post",
		renderTo : "addpaperbysystem",
		buttonAlign: "center",
		buttons : [{
			text : "提交数据",
			handler : function() {
				form.getForm().submit({
					url : "paperManage/addbysystem",
					params:{stid:station.getValue()},
					success : function(f, action) {
						Ext.Msg.alert("成功", action.result.msg);
						var tab = center.getComponent("grandson3");
						 var rtable = center.getComponent("grandson2");
						 if(tab) {
								 center.setActiveTab(tab);
								 center.remove(rtable);
							 }else{
								 center.remove(rtable);
								 p = new Ext.Panel({
										title : grandson3.attributes.text,
										autoLoad : {
											url : grandson3.attributes.url,
											scripts : true
										},
										closable : true,
										id : grandson3.attributes.id
								 });
								 center.add(p);
								 center.setActiveTab(p);
							 }
					},
					failure : function() {
						Ext.Msg.alert("失败", "对不起，操作失败，请检查数据是否完整！");
					}
				});
			}
		},{
			text : "重置",
			handler : function() {
				var basicForm = form.getForm();
				basicForm.reset();
			}
		},{
			text : "返回",
			handler : function() {
				 var tab = center.getComponent("grandson3");
				 var rtable = center.getComponent("grandson2");
				 if(tab) {
						 center.setActiveTab(tab);
						 center.remove(rtable);
					 }else{
						 center.remove(rtable);
						 p = new Ext.Panel({
								title : grandson3.attributes.text,
								autoLoad : {
									url : grandson3.attributes.url,
									scripts : true
								},
								closable : true,
								id : grandson3.attributes.id
						 });
						 center.add(p);
						 center.setActiveTab(p);
					 }
			}
		} ]
	});
	
})
</script>
</head>
<body>
<div id="addpaperbysystem"></div>
</body>
</html>