Ext.ux.TabCloseMenu = function() {
	var tabs, menu, ctxItem;
	this.init = function(tp) {
		tabs = tp;
		tabs.on('contextmenu', onContextMenu);
	}

	function onContextMenu(ts, item, e) {
		if (!menu) {// create context menu on first right click
			menu = new Ext.menu.Menu([ {
				id : tabs.id + '-close',
				text : '关闭标签',
				handler : function() {
					tabs.remove(ctxItem);
				}
			}, {
				id : tabs.id + '-close-others',
				text : '关闭其他标签',
				handler : function() {
					tabs.items.each(function(item) {
						if (item.closable && item != ctxItem) {
							tabs.remove(item);
						}
					});
				}
			}, {
				id : tabs.id + '-close-all',
				text : '关闭全部标签',
				handler : function() {
					tabs.items.each(function(item) {
						if (item.closable) {
							tabs.remove(item);
						}
					});
				}
			}, "-", {
				id : tabs.id + "-fresh",
				text : "刷新",
				iconCls : "x-tbar-loading",
				handler : function() {
					ctxItem.getUpdater().update(ctxItem.autoLoad.url);

				}
			}, {
				id : tabs.id + "-fresh-all",
				text : "刷新全部",
				iconCls : "x-tbar-loading",
				handler : function() {
					tabs.items.each(function(item) {
						item.getUpdater().update(item.autoLoad.url);
					});
				}
			} ]);
		}
		ctxItem = item;
		var items = menu.items;
		items.get(tabs.id + '-close').setDisabled(!item.closable);
		var disableOthers = true;
		tabs.items.each(function() {
			if (this != item && this.closable) {
				disableOthers = false;
				return false;
			}
		});
		items.get(tabs.id + '-close-others').setDisabled(disableOthers);
		var disableAll = true;
		tabs.items.each(function() {
			if (this.closable) {
				disableAll = false;
				return false;
			}
		});
		items.get(tabs.id + '-close-all').setDisabled(disableAll);
		menu.showAt(e.getPoint());
	}
};