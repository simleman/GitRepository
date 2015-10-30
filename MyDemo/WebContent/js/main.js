Ext.BLANK_IMAGE_URL = "extjs/resources/images/default/s.gif";
Ext.QuickTips.init();
var left;
var center;
var grandson2;
var grandson1;
var grandson3;
Ext.onReady(function() {
		var top = new Ext.Panel({
			region : 'north',
			title : "试卷管理系统",
			height : 80,
			border : true,
			html : "欢迎使用超级试卷管理系统",
			margins : '0 0 5 0'
		});
	   left = new Ext.tree.TreePanel({
			region : 'west',
			collapsible : true,
			title : '操作菜单',
			width : 200,
			autoScroll : true,
			containerScroll: true,
			split : true,
			listeners : {
				click : function(n) {
					var url = n.attributes.url;
					var id = n.attributes.id;
					var p = center.getItem(id);
					if (url) {
						if (p) {
							center.setActiveTab(p);
						} else {
							p = new Ext.Panel({
								title : n.attributes.text,
								autoLoad : {
									url : url,
									scripts : true
								},
								closable : true,
								id : id
							});
							center.add(p);
							center.setActiveTab(p);
						}
					}
				}
			}
		});
		var root = new Ext.tree.TreeNode({
			id : "root",
			text : "试卷管理系统",
			leaf: "false"
		});
		var child1 = new Ext.tree.TreeNode({
			text : "题目类型管理",
			url : "paper/questionTypeList.jsp"
		});
		var child2 = new Ext.tree.TreeNode({
			text : "岗位类型管理",
			url : "paper/station.jsp"
		});
		var child3 = new Ext.tree.TreeNode({
			text : "试题管理",
			url : "paper/testquestions.jsp"
		});
		var child4 = new Ext.tree.TreeNode({
			text : "试卷生成系统",
		});
		grandson1 = new Ext.tree.TreeNode({
			id : "grandson1",
			text : "人工生成",
			url : "paper/addpaperbyman.jsp"
		});
		grandson2 = new Ext.tree.TreeNode({
			id : "grandson2",
			text : "系统生成",
			url : "paper/addpaperbysystem.jsp"
		});
	    grandson3 = new Ext.tree.TreeNode({
			id : "grandson3",
			text : "试卷查询",
			url : "paper/paper.jsp"
		});
		root.appendChild([ child1, child2, child3, child4]);
		child4.appendChild([ grandson1, grandson2, grandson3]);
		left.setRootNode(root);
		center = new Ext.TabPanel({
			region : 'center',
			defaults: {autoScroll:true},
			items : [{
				id : "opt1",
				title : '试卷管理系统首页',
				html : '欢迎使用超级试卷管理系统'
			}],
			enableTabScroll : true,
			plugins: [new Ext.ux.TabCloseMenu()]
		});
		center.setActiveTab("opt1");
		var bottom = new Ext.Panel({
			region : 'south',
			title : 'Information',
			collapsible : true,
			html : '版权所有，翻版必究!',
			height : 100,
			split : true,
			minHeight : 100,
			bodyStyle : "padding: 10px; font-size: 12px; text-align:center;"
		});
		var vp = new Ext.Viewport({
			layout : 'border',
			items : [ top, left, center, bottom ]
		});
		left.expandAll();
	})