/*
 * File: app/view/SalaryItemGrid.js
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

Ext.define('sion.salary.accounts.view.SalaryItemGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.salaryitemgrid',

    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.button.Button',
        'Ext.grid.RowNumberer',
        'Ext.grid.column.Action',
        'Ext.grid.View'
    ],

    autoScroll: true,
    header: false,
    title: 'My Grid Panel',
    store: 'SalaryItem',

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
                                    fn: me.onNewSalaryItemClick,
                                    scope: me
                                }
                            }
                        }
                    ]
                }
            ],
            columns: [
                {
                    xtype: 'rownumberer',
                    text: '序号',
                    flex: 0.5
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'name',
                    text: '项目名称',
                    flex: 2
                },
                {
                    xtype: 'gridcolumn',
                    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                        var store = Ext.getStore("AccountItemType"),
                            rec = store.findRecord('id', value);

                        return rec.get("name");
                    },
                    dataIndex: 'type',
                    text: '类型',
                    flex: 1
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'precision',
                    text: '小数位数',
                    flex: 1
                },
                {
                    xtype: 'gridcolumn',
                    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                        if('Round' == value){
                            return '四舍五入';
                        }else if('Isopsephy' == value){
                            return '直接进位';
                        }else if('Truncation' == value){
                            return '直接舍去';
                        }
                    },
                    dataIndex: 'carryType',
                    text: '小数保留方式',
                    flex: 1
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'note',
                    text: '备注',
                    flex: 3
                },
                {
                    xtype: 'actioncolumn',
                    dataIndex: 'id',
                    hideable: false,
                    text: '修改',
                    tooltip: '修改',
                    flex: 0.5,
                    items: [
                        {
                            handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                this.up('gridpanel').detail(record);
                            },
                            iconCls: 's_icon_page_edit',
                            tooltip: '修改'
                        }
                    ]
                },
                {
                    xtype: 'actioncolumn',
                    dataIndex: 'id',
                    hideable: false,
                    text: '删除',
                    flex: 0.5,
                    items: [
                        {
                            handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                var store = Ext.getStore("SalaryItem");
                                if(record.get('type') == 'System'){
                                    Ext.Msg.alert('提示','系统提取项不可删除！');
                                    return false;
                                }

                                Ext.Msg.confirm('提示', '确定要删除吗？', function(text){
                                    if (text == 'yes'){
                                        Ext.Ajax.request({
                                            url :'salary/salaryitem/remove',//请求的服务器地址
                                            params : {
                                                id : record.get('id')
                                            },//发送json对象
                                            success:function(response,action){
                                                var text = JSON.parse(response.responseText);
                                                if(!text.success){
                                                    Ext.Msg.alert("提示", text.message);
                                                }else{
                                                    Ext.Msg.alert("提示", "删除成功");
                                                    store.load();
                                                }
                                                //                 me.resetGridSelect(record);
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
                render: {
                    fn: me.onGridpanelRender,
                    scope: me
                },
                itemdblclick: {
                    fn: me.onGridpanelItemDblClick,
                    scope: me
                }
            }
        });

        me.callParent(arguments);
    },

    onNewSalaryItemClick: function(button, e, eOpts) {
        var me =this,
            namespace=me.getNamespace();

        Ext.create(namespace+".view.SalaryItemEdit").show();
    },

    onGridpanelRender: function(component, eOpts) {
        var me = this,
            store = component.getStore();

        store.clearFilter(true);
        Ext.apply(store.proxy.extraParams, {
            system : '',
            type : ''
        });
        store.load();
    },

    onGridpanelItemDblClick: function(dataview, record, item, index, e, eOpts) {
        this.detail(record);
    },

    detail: function(record) {
        var me = this,
            namespace = me.getNamespace(),
            str = '.view.SalaryItemEdit';

        if(record.get('type') == 'System'){
            str = '.view.SalaryItemRead';
        }
        var panel =  Ext.create(namespace + str,{
            _salaryItem : record
        });
        panel.show();
    }

});