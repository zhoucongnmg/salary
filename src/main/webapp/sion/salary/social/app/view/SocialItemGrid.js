/*
 * File: app/view/SocialItemGrid.js
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

Ext.define('sion.salary.social.view.SocialItemGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.socialitemgrid',

    requires: [
        'Ext.grid.RowNumberer',
        'Ext.grid.View',
        'Ext.grid.column.Action'
    ],

    store: 'SocialItem',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            columns: [
                {
                    xtype: 'rownumberer',
                    text: '序号',
                    flex: 0.06
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'name',
                    text: '项目名称',
                    flex: 0.42
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'itemTypeName',
                    text: '项目类型',
                    flex: 0.2
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'precision',
                    text: '小数位数',
                    flex: 0.2
                },
                {
                    xtype: 'actioncolumn',
                    hideable: false,
                    text: '修改',
                    flex: 0.06,
                    items: [
                        {
                            handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                this.up('gridpanel').detail(record);
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
                    flex: 0.06,
                    items: [
                        {
                            handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                var store = Ext.StoreManager.lookup("SocialItem");

                                Ext.Msg.confirm('提示', '确定要删除吗？', function(text){
                                    if (text == 'yes'){
                                        Ext.Ajax.request({
                                            url :'salary/socialitem/remove',//请求的服务器地址
                                            params : {
                                                id : record.get('id')
                                            },//发送json对象
                                            success:function(response,action){
                                                var result = Ext.JSON.decode(response.responseText);
                                                if (result.success) {
                                                    store.load();
                                                    //                 me.resetGridSelect(record);
                                                    Ext.Msg.alert("提示", "删除成功");
                                                }else{
                                                    Ext.Msg.alert("提示", "社保套帐中使用了该项目，不能删除！");
                                                }
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
        component.getStore().load();
    },

    onGridpanelItemDblClick: function(dataview, record, item, index, e, eOpts) {
        this.detail(record);
    },

    detail: function(record) {
        var me = this,
            namespace = me.getNamespace();

        var socialItem =  Ext.create(namespace + '.view.SocialItemForm',{
            _socialItem : record
        });
        socialItem.show();
        // me.resetGridSelect(record);
    }

});