<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人工生成</title>
<style type="text/css">
#arlabel{padding-left: 12px;font-size: 12px}
.x-form-item {padding-top: 6px;}
#ext-gen27{margin-top: 3px;}
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
	var arlabel = new Ext.form.Label({
    	id:"arlabel",
    	text:"选择试题:",
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
	var makedate = new Ext.form.DateField({
		name: "tqsVo.makedate",
		fieldLabel: "制作日期",
		width: 200,
		format: "Y-m-d",
		value: new Date()
	});
	///------------------------------------题目选择
	/*未分配的条目*/
     var  proxy = new Ext.data.HttpProxy({url:"testquestions/queryPage"});
	 var questiontype = Ext.data.Record.create([ 
	    	{name : "tqid",type : "int",mapping : "tqid"},
	    	{name : "questionname",type : "string",mapping : "questionname"},
		]);
	 var reader = new Ext.data.ArrayReader({},questiontype);
	 var from = new Ext.data.Store({
		    proxy:proxy,
		    reader:reader,
		    pruneModifiedRecords : true,
	        fields: [{name:'questionname'},{name:'tqid'}],
            autoLoad : true		
	 });
	 /*已分配的条目*/
	 var to = new Ext.data.ArrayStore({
		 fields: ['questionname', 'tqid']
		    });	
	///------------------------------------题目类型选择
	var qtProxy = new Ext.data.HttpProxy({url:"questionType/queryPage"});
	var qtRecord = Ext.data.Record.create([ 
	  {name : "qtid",type : "int",mapping : "id"},
	  {name : "type",type : "string",mapping : "type"},
	  {name : "ischoose",type : "int",mapping : "ischoose"},
	  {name : "anwsercount",type : "int",mapping : "anwsercount"} 
	]);
	var qtReader = new Ext.data.JsonReader({totalProperty : "count",root : "pageData"},qtRecord);
	var store = new Ext.data.Store({
		proxy : qtProxy,
		reader : qtReader,
		autoLoad : true
	});
	store.load({params:{start:0,limit:5}});
	var questiontype = new Ext.form.ComboBox({
		store : store,
		fieldLabel : "题目类型",
		displayField : "type",
		valueField : "qtid",
		mode : "remote",
		pageSize : 5,
		triggerAction : "all",
		emptyText : "请选择题目类型",
		editable : false,
		width:120,
		listWidth: 240,
		hiddenName : "tqsVo.qtid"
	});
	questiontype.on('select',function(){
		var qtid = questiontype.getValue();
		var sid = station.getValue();
		if(qtid!=null){
			var ids = form.getForm().findField('itemselector').getValue();
			from.load({params:{qtid:qtid,sid:sid,removeid:ids}});
		}
		
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
		var sid = station.getValue();
		if(sid!=null){
			from.load({params:{sid:sid}});
			form.getForm().findField('itemselector').reset();
		}
	});
	///------------------------------------------提交数据
	var form = new Ext.form.FormPanel({
		title : "新增试卷",
		layout: "form", 
		autoHeight: true,
		width: 650, 
        labelWidth: 65, 
        labelAlign: "right",
		frame : true,
		items : [ 
		          questionname, 
		          {layout: "column",items:[
		                {columnWidth: .4, layout: "form",items: [station]},
		                {columnWidth: .4,layout: "form",items: [questiontype]}]
		          },
		          {layout: "column", items:[
		                {columnWidth: .4,layout: "form",items: [arlabel]},
		                {xtype:'itemselector',
			        	 name:'itemselector',
			        	 hideLabel:true,
			        	 imagePath: 'images/images/',
			        	 multiselects: [{　　
			        		  width: 220,
			        		  height: 260,
			        		  legend:'可选条目',
			        		  store: from,
			        		  displayField: 'questionname',
			        		  valueField: 'tqid'　
		          			},{
		          			   width: 220,
		          			   height: 260,
		          			   hideLabel:true,
		          			   legend:'已选条目',
		          			   store:to,
		          			   displayField: 'questionname',
		          			   valueField: 'tqid',
		          			   tbar:[{
		          				 text: '清除已选项目',
		          				 handler:function(b,e){
		          					form.getForm().findField('itemselector').reset();
		          				 }
		          			   }]
		          		  }]
		          	   }]
	          		},memo,makedate
		        ],
		method : "post",
		renderTo : "addpaperbyman",
		buttonAlign: "center",
		buttons : [{
			text : "提交数据",
			handler : function() {
				form.getForm().submit({
					url : "paperManage/add",
					params:{tqids:form.getForm().findField('itemselector').getValue(),sid:station.getValue()},
					success : function(f, action) {
						Ext.Msg.alert("成功", action.result.msg);
						var tab = center.getComponent("grandson3");//获取指定id的组件对象
						 var rtable = center.getComponent("grandson1");
						 if(tab) {
							 	 center.remove(rtable);
								 center.setActiveTab(tab);//检验当前Tab选项卡是否存在，如果存在只需要激活
								 
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
		} ,{
			text : "返回",
			handler : function() {
				 var tab = center.getComponent("grandson3");//获取指定id的组件对象
				 var rtable = center.getComponent("grandson1");
				 if(tab) {
					 	 center.remove(rtable);
						 center.setActiveTab(tab);//检验当前Tab选项卡是否存在，如果存在只需要激活
						 
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
		}]
	});
})
</script>
</head>
<body>
<div id="addpaperbyman"></div>
</body>
</html>