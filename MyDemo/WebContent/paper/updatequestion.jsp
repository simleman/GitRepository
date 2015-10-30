<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改试题</title>
<style type="text/css">
#plabel{padding-left: 12px;padding-top: 12px;font-size: 12px}
#arlabel{padding-left: 12px;font-size: 12px}
.x-form-item {padding-top: 13px;}
#ext-gen27{margin-top: 3px;}
</style>
<script type="text/javascript">
var j=0;
var n=0;
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	var hiddenid = new Ext.form.Hidden({name:"tqsVo.tqid"});
	var questionname = new Ext.form.TextField({
		fieldLabel : "试题名称",
		name : "tqsVo.questionname",
		width:300,
		allowBlank : false
	});
	var plabel = new Ext.form.Label({
    	id:"plabel",
    	text:"正确选项:",
    	hidden:true
    });
	var pn=new Ext.Panel({
		   autoHeight:true, 
		   columnWidth:1,
		   layout:'column'
	});  
	pn.add(plabel);
	///------------------------------------题目类型选择
	var qtProxy = new Ext.data.HttpProxy({url:"questionType/queryPage"});
	var qtRecord = Ext.data.Record.create([ 
	  {name : "qtid",type : "int",mapping : "id"},
	  {name : "type",type : "string",mapping : "type"},
	  {name : "ischoose",type : "int",mapping : "ischoose"},
	  {name : "isjudge",type : "int",mapping : "isjudge"},
	  {name : "anwsercount",type : "int",mapping : "anwsercount"} 
	]);
	var qtReader = new Ext.data.JsonReader({totalProperty : "count",root : "pageData"},qtRecord);
	var store = new Ext.data.Store({
		proxy : qtProxy,
		reader : qtReader,
		autoLoad : true,
		listeners:{
			load:function(){
				questiontype.setValue(record.get("qtid"));
			}
		}
	});
	if(record.get("qtid")>=5)
		store.load({params:{start:3,limit:5}});
	else{
		store.load({params:{start:0,limit:5}});
	}
	var questiontype = new Ext.form.ComboBox({
		store : store,
		fieldLabel : "题目类型",
		displayField : "type",
		valueField : "qtid",
		mode : "remote",
		pageSize : 5,
		triggerAction : "all",
		emptyText : "请选择题目类型",
		allowBlank : false,
		editable : false,
		width:120,
		listWidth: 240,
		name: "tqsVo.qtid",
		hiddenName : "tqsVo.qtid"
	});
	var abutton = new Ext.Button({
		icon : "images/add.png",
		cls : "x-btn-text-icon",
		id : "abutton",
		text : "增加一行",
		hidden :true,
		handler : function(){
                    j++;
                    var cbox = new Ext.form.Checkbox({name:'tqsVo.correct',id:"correct"+j,boxLabel:"选项"+String.fromCharCode((65+j)),inputValue: String.fromCharCode((65+j))});
                    var pnx=new Ext.Panel({
						   id : "pnx"+j,
						   autoHeight:true,
						   labelWidth:10,
						   layout:'form'
					});  
                    pnx.add(j,cbox);
                    pnx.doLayout();
                    pn.add(pnx);
					pn.doLayout();
					var fd = new Ext.form.TextField({name:'option',id:'option'+(j),fieldLabel:"选项"+String.fromCharCode((65+j)),width:400});
					form.insert(2+j,fd);
                    form.doLayout();
		}
       });
       var rbutton = new Ext.Button({
       	icon : "images/cross.png",
		cls : "x-btn-text-icon",
		id : "rbutton",
		text : "删除一行",
		hidden :true,
		handler : function() {
			pn.remove(Ext.getCmp("pnx"+j),true);
            form.remove(Ext.getCmp("option"+j),true);
            j--;
	    }
       });
       var arlabel = new Ext.form.Label({
       	id:"arlabel",
       	text:"编辑选项:",
       	hidden :true
       });
	questiontype.on('select',function(){
	  store.each(function(qrecord) {   
		     if(qrecord.get("qtid")==questiontype.getValue()){
		    	var i=0;
		    	if(qrecord.get("isjudge")==1){
		    		abutton.hide();
		    		rbutton.hide();
		    		arlabel.hide();
		    		plabel.hide();
		    		if(Ext.getCmp("correcttxt")!=null){
		    			form.remove(Ext.getCmp("correcttxt"),true);
		    		}
		    		if(Ext.getCmp("isjudge")!=null){
		    			form.remove(Ext.getCmp("isjudge"),true);
		    		}
		    		while(Ext.getCmp("option"+i)!=null){
		    			pn.remove(Ext.getCmp("pnx"+i),true);
		    			form.remove(Ext.getCmp("option"+i),true);
		    			i++;
		    		}
		    		var judgeyes = new Ext.form.Radio({
		    			name:"tqsVo.correct",
		    			inputValue:"1",
		    			boxLabel:"对",
		    		});
		    		var judgeno = new Ext.form.Radio({
		    			name:"tqsVo.correct",
		    			inputValue:"0",
		    			boxLabel:"错",
		    		});
		    		var isjudge = new Ext.form.RadioGroup({
		    			id:"isjudge",
		    			name:"tqsVo.correct",
		    			fieldLabel:"选择对错",
		    			items:[judgeyes,judgeno],
		    			width:80
		    		});
		    		form.add(isjudge);
		    		form.doLayout();
		    	}else if(qrecord.get("ischoose")==1){
		    		abutton.show();
		    		rbutton.show();
		    		arlabel.show();
		    		plabel.show();
		    		if(Ext.getCmp("isjudge")!=null){
		    			form.remove(Ext.getCmp("isjudge"),true);
		    		}
		    		if(Ext.getCmp("correcttxt")!=null){
		    			form.remove(Ext.getCmp("correcttxt"),true);
		    		}
		    		while(Ext.getCmp("option"+i)!=null){
		    			form.remove(Ext.getCmp("option"+i),true);
		    			pn.remove(Ext.getCmp("pnx"+i),true);
		    			i++;
		    		} 
		    		var answercount = qrecord.get("anwsercount");
		    		for(n=0 ;n<answercount+1;n++){
		    			var cbox = new Ext.form.Checkbox({name:'tqsVo.correct',id:"correct"+n,boxLabel:"选项"+String.fromCharCode((65+n)),inputValue: String.fromCharCode((65+n))});
	                    var pnx=new Ext.Panel({
							   id : "pnx"+n,
							   autoHeight:true, 
							   labelWidth:10,
							   layout:'form'
						});  
	                    pnx.add(n,cbox);
	                    pnx.doLayout();
	                    pn.add(pnx);
						pn.doLayout();
		    			var fd = new Ext.form.TextField({name:'option',id:'option'+n,fieldLabel:"选项"+String.fromCharCode((65+n)),width:400});
	                    form.insert(2+n,fd);
	                    form.doLayout();
	                    j=n;
	                }
		    	} else{
		    		abutton.hide();
		    		rbutton.hide();
		    		arlabel.hide();
		    		plabel.hide();
		    		if(Ext.getCmp("isjudge")!=null){
		    			form.remove(Ext.getCmp("isjudge"),true);
		    		}
		    		if(Ext.getCmp("correcttxt")!=null){
		    			form.remove(Ext.getCmp("correcttxt"),true);
		    		}
		    		while(Ext.getCmp("option"+i)!=null){
		    			form.remove(Ext.getCmp("option"+i),true);
		    			pn.remove(Ext.getCmp("pnx"+i),true);
		    			i++;
		    		} 
		    		var correct = new Ext.form.HtmlEditor({
		    			id:"correcttxt",
		    			fieldLabel : "正确答案",
		    			name : "tqsVo.correct",
		    			enableLists: false,
		    			enableSourceEdit: false,
		    			height: 150,
		    			width : 500,
		    			value:"如果是选择题，请直接写题号!"
		    		});
		    		form.add(correct);
		    		form.doLayout();
		    	}
		     } 
	});  
	});
	///------------------------------------岗位类型选择
	var stProxy = new Ext.data.HttpProxy({url:"station/queryPage"});
	var stRecord = Ext.data.Record.create([ 
	  {name : "stid",type : "int",mapping : "id"},
	  {name : "stname",type : "string",mapping : "name"}
	]);
	var stReader = new Ext.data.JsonReader({totalProperty : "count",root : "pageData"},stRecord);
	var store1 = new Ext.data.Store({
		proxy : stProxy,
		reader : stReader,
		autoLoad : true,
		listeners:{
			load:function(){
				station.setValue(record.get("stationid"));
			}
		}
	});
	if(record.get("stationid")>=5){
		store1.load({params:{start:3,limit:5}});
	}else{
		store1.load({params:{start:0,limit:5}});		
	}
	
	var station = new Ext.form.ComboBox({
		store : store1,
		fieldLabel : "岗位类型",
		displayField : "stname",
		valueField : "stid",
		mode : "remote",
		triggerAction : "all",
		pageSize : 5,
		emptyText : "请选择岗位类型",
		allowBlank : false,
		editable : false,
		width:120,
		listWidth: 240,
		name: "tqsVo.stationid",
		hiddenName : "tqsVo.stationid"
	});
	///------------------------------------------提交数据
	var form = new Ext.form.FormPanel({
		title : "修改试题",
		layout: "form", 
		autoHeight: true,
		width: 650, 
        labelWidth: 65, 
        labelAlign: "right",
		frame : true,
		items : [ hiddenid,
		          questionname, 
		          {layout: "column",items:[
		                {columnWidth: .4, layout: "form",items: [questiontype]},
		                {columnWidth: .4,layout: "form",items: [station]}]
		          },
		          {layout: "column", items:[
		                {columnWidth: .2,layout: "form",items: [arlabel]},
		                {columnWidth: .2,layout: "form",items: [abutton]},
		                {columnWidth: .2,layout: "form",items: [rbutton]}]
		          },pn
		        ],
		url : "testquestions/update",
		method : "post",
		renderTo : "updateqt",
		buttonAlign: "center",
		buttons : [{
			text : "提交数据",
			handler : function() {
				var json = {
					success : function(f, action) {
						Ext.Msg.alert("成功", action.result.msg);
						center.getActiveTab().load({url:'paper/testquestions.jsp',scripts:true});
					},
					failure : function() {
						Ext.Msg.alert("失败", "对不起，操作失败，请检查数据是否完整！");
					}
				};
				form.getForm().submit(json);
			}
		}, {
			text : "重置",
			handler : function() {
				var basicForm = form.getForm();
				basicForm.reset();
			}
		},{
			text : "返回",
			handler : function() {
				 center.getActiveTab().load({url:'paper/testquestions.jsp',scripts:true});
			}
		  }
		]
	});
	
	form.getForm().setValues({
		"tqsVo.tqid" : record.get("tqid"),
		"tqsVo.correct" : record.get("correct"),
		"tqsVo.questionname" : record.get("questionname"),
		"tqsVo.questionoption" : record.get("questionoption"),
		"tqsVo.stationid" : record.get("stationid"),
		"tqsVo.stationname" : record.get("stationname"),
		"tqsVo.qtid" : record.get("qtid"),
		"tqsVo.isjudge" : record.get("isjudge"),
		"tqsVo.qttype" : record.get("qttype")
	});
	    var judge = record.get("isjudge");
      	var s = record.get("questionoption");
      	var c = record.get("correct");
      	if(judge==1){
      		var judgeyes = new Ext.form.Radio({
    			name:"tqsVo.correct",
    			inputValue:"1",
    			boxLabel:"对",
    		});
    		var judgeno = new Ext.form.Radio({
    			name:"tqsVo.correct",
    			inputValue:"0",
    			boxLabel:"错",
    		});
    		var isjudge = new Ext.form.RadioGroup({
    			id:"isjudge",
    			name:"tqsVo.correct",
    			fieldLabel:"选择对错",
    			items:[judgeyes,judgeno],
    			width:80
    		});
    		if(c==1){
    			judgeyes.setValue(true);
    		}else{
    			judgeno.setValue(true);
    		}
    		form.add(isjudge);
    		form.doLayout();
      	}else if(s.trim()!=null&&s.trim()!="null"){
      		var x=0;
      		var options = s.split(".");
      		var cor=c.split(",");
      		for(n=0 ;n<options.length-1;n++){
      			var cbox = new Ext.form.Checkbox({name:'tqsVo.correct',id:"correct"+n,boxLabel:"选项"+String.fromCharCode((65+n)),inputValue: String.fromCharCode((65+n))});
               	var pnx=new Ext.Panel({
				   id : "pnx"+n,
				   autoHeight:true, 
				   labelWidth:10,
				   layout:'form'
				}); 
               	if(cor[n]!=undefined&&cor[n].trim()!=null&&cor[n].trim()!=""&&cor[n].trim()!="null"){
               		if(cor[n].trim()==cbox.inputValue)
               			cbox.setValue(true);
               	}
               	pnx.add(n,cbox);
               	pnx.doLayout();
               	pn.add(pnx);
				pn.doLayout();
                var fd = new Ext.form.TextField({name:'option',id:'option'+n,fieldLabel:"选项"+String.fromCharCode((65+n)),width:400,value : options[n].substring(2,options[n].length).trim()});
                form.insert(2+n,fd);
                form.doLayout();
                j=n;
           }
	      	abutton.show();
	   		rbutton.show();
	   		arlabel.show();
	   		plabel.show();
      	}else{
      		var correct = new Ext.form.HtmlEditor({
    			id:"correcttxt",
    			fieldLabel : "正确答案",
    			name : "tqsVo.correct",
    			enableLists: false,
    			enableSourceEdit: false,
    			height: 150,
    			width : 500,
    			value:record.get("correct")
    		});
    		form.add(correct);
    		form.doLayout();
      	}
})
</script>
</head>
<body>
<div id="updateqt"></div>
</body>
</html>