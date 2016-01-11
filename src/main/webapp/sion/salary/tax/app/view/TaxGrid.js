/*
 * File: app/view/TaxGrid.js
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

Ext.define('sion.salary.tax.view.TaxGrid', {
    extend: 'Ext.panel.Panel',

    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.button.Button',
        'Ext.grid.Panel',
        'Ext.grid.column.Action',
        'Ext.grid.View'
    ],

    height: '',
    layout: 'fit',
    title: '个税设置',

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
                            width: 70,
                            iconCls: 's_icon_action_add',
                            text: '<span style="font-size:14px;color:#3892D3;font-weight:bold;">新增</span>',
                            listeners: {
                                click: {
                                    fn: me.onAddTaxClick,
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
                    store: 'Tax',
                    columns: [
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'name',
                            text: '名称',
                            flex: 2
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'threshold',
                            text: '个税起征点',
                            flex: 2
                        },
                        {
                            xtype: 'actioncolumn',
                            hideable: false,
                            text: '修改',
                            tooltip: '修改',
                            flex: 0.5,
                            items: [
                                {
                                    handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                        this.up('gridpanel').up().detail(record);
                                    },
                                    iconCls: 's_icon_table_edit',
                                    tooltip: '修改'
                                }
                            ]
                        },
                        {
                            xtype: 'actioncolumn',
                            hideable: false,
                            text: '删除',
                            tooltip: '删除',
                            flex: 0.5,
                            items: [
                                {
                                    handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                        var store = Ext.getStore("Tax");

                                        Ext.Msg.confirm('提示', '确定要删除吗？', function(text){
                                            if (text == 'yes'){
                                                Ext.Ajax.request({
                                                    url :'salary/tax/remove',//请求的服务器地址
                                                    params : {
                                                        id : record.get('id')
                                                    },//发送json对象
                                                    success:function(response,action){
                                                        store.load();
                                                        //                 me.resetGridSelect(record);
                                                        Ext.Msg.alert("提示", "删除成功");
                                                    },failure: function(){
                                                        store.load();
                                                        //                 me.resetGridSelect(record);
                                                        Ext.Msg.alert("提示", "删除失败");
                                                    }
                                                });
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
                        },
                        render: {
                            fn: me.onGridpanelRender,
                            scope: me
                        }
                    }
                }
            ]
        });

        me.callParent(arguments);
    },

    onAddTaxClick: function(button, e, eOpts) {
        var me=this,
            namespace=me.getNamespace();
        Ext.create(namespace+".view.Tax").show();
    },

    onGridpanelItemDblClick: function(dataview, record, item, index, e, eOpts) {
        this.detail(record);
    },

    onGridpanelRender: function(component, eOpts) {
        var me = this,
            store = component.getStore();

        store.load();
    },

    detail: function(record) {
        var me = this,
            namespace = me.getNamespace();

        var panel =  Ext.create(namespace + '.view.Tax',{
            _tax : record
        });
        panel.show();
    }

});