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
        'Ext.grid.column.Boolean',
        'Ext.grid.column.Action',
        'Ext.grid.View'
    ],

    header: false,
    title: 'My Grid Panel',
    store: 'SalaryItem',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'name',
                    text: '项目名称',
                    flex: 2
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'field',
                    text: '字段',
                    flex: 1
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'type',
                    text: '类型',
                    flex: 1
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'note',
                    text: '说明',
                    flex: 3
                },
                {
                    xtype: 'booleancolumn',
                    dataIndex: 'system',
                    text: '系统项',
                    flex: 1,
                    falseText: '否',
                    trueText: '是'
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
                                if(record.get('system')){
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

        if(record.get('system')){
            str = '.view.SalaryItemRead';
        }
        var panel =  Ext.create(namespace + str,{
            _salaryItem : record
        });
        panel.show();
        // me.resetGridSelect(record);
    }

});