/*
 * File: app/view/LevelGrid.js
 *
 * This file was generated by Sencha Architect version 3.2.0.
 * http://www.sencha.com/products/architect/
 *
 * This file requires use of the Ext JS 4.2.x library, under independent license.
 * License of Sencha Architect does not include license for Ext JS 4.2.x. For more
 * details see http://www.sencha.com/license or contact license@sencha.com.
 *
 * This file will be auto-generated each and everytime you save your project.
 *
 * Do NOT hand edit this file.
 */

Ext.define('sion.salary.level.view.LevelGrid', {
    extend: 'Ext.panel.Panel',

    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.button.Button',
        'Ext.grid.Panel',
        'Ext.grid.column.Action',
        'Ext.grid.View'
    ],

    layout: 'fit',
    title: '薪资层次',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'top',
                    items: [
                        {
                            xtype: 'button',
                            iconCls: 's_icon_action_add',
                            text: '<span style="font-size:14px;color:#3892D3;font-weight:bold;">新建</span>',
                            listeners: {
                                click: {
                                    fn: me.onButtonClick,
                                    scope: me
                                }
                            }
                        }
                    ]
                }
            ],
            items: [
                {
                    xtype: 'gridpanel',
                    header: false,
                    title: 'My Grid Panel',
                    store: 'LevelStore',
                    columns: [
                        {
                            xtype: 'gridcolumn',
                            width: '20%',
                            dataIndex: 'name',
                            text: '层次名称',
                            flex: 1
                        },
                        {
                            xtype: 'gridcolumn',
                            renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                                var rank=[];
                                Ext.Array.each(value,function(v){rank.push(v.rank);});
                                return rank;
                            },
                            width: '60%',
                            dataIndex: 'levelItems',
                            text: '级别',
                            flex: 1
                        },
                        {
                            xtype: 'actioncolumn',
                            width: 35,
                            text: '修改',
                            flex: 0.3,
                            items: [
                                {
                                    handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                        this.up('gridpanel').up().detail(record);
                                    },
                                    iconCls: 's_icon_page_edit',
                                    tooltip: '修改'
                                }
                            ]
                        },
                        {
                            xtype: 'actioncolumn',
                            width: 35,
                            text: '删除',
                            flex: 0.3,
                            items: [
                                {
                                    handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                        var me=this,
                                            grid=view;
                                        Ext.Msg.confirm({
                                            title:"提示",
                                            msg:'确认删除吗？',
                                            buttons:Ext.MessageBox.OKCANCEL,
                                            width:200,
                                            fn:function(buttonId){
                                                if(buttonId=="ok"){
                                                    Ext.Ajax.request({
                                                        url:'salary/level/remove?id='+record.data.id,
                                                        method:'GET',
                                                        success:function(res){
                                                            var responseData=Ext.JSON.decode(res.responseText);
                                                            if(responseData.success===true){
                                                                grid.getStore().reload();
                                                            }
                                                            Ext.Msg.alert("提示",responseData.message);
                                                        },
                                                        failure:function(form,action){
                                                            me.getStore().reload();
                                                            Ext.Msg.alert('删除失败，请检查网络连接状况');
                                                        }
                                                    });

                                                }
                                            }
                                        });
                                    },
                                    iconCls: 's_icon_cross',
                                    tooltip: '删除'
                                }
                            ]
                        }
                    ],
                    listeners: {
                        itemdblclick: {
                            fn: me.onGridpanelItemDblClick,
                            scope: me
                        }
                    }
                }
            ],
            listeners: {
                afterrender: {
                    fn: me.onPanelAfterRender,
                    scope: me
                }
            }
        });

        me.callParent(arguments);
    },

    onButtonClick: function(button, e, eOpts) {
        var me=this,
            namespace=me.getNamespace(),
            grid=me.down('gridpanel');
        Ext.create(namespace+".view.Level_win",{_levelGrid:grid}).show();
        // test Ext.create(namespace+".view.MyWindow").show();
    },

    onGridpanelItemDblClick: function(dataview, record, item, index, e, eOpts) {
        this.detail(record);
    },

    onPanelAfterRender: function(component, eOpts) {
        var me =this,
            grid=me.down('gridpanel');
        grid.getStore().load();
    },

    detail: function(record) {
        var me = this,
            grid = me.down('gridpanel');

        Ext.create("sion.salary.level.view.Level_win",{
            _levelGrid:grid,
            _record:record
        }).show();
    }

});