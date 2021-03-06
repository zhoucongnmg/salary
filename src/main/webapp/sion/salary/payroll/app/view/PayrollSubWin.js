/*
 * File: app/view/PayrollSubWin.js
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

Ext.define('sion.salary.payroll.view.PayrollSubWin', {
    extend: 'Ext.window.Window',

    requires: [
        'Ext.grid.Panel',
        'Ext.grid.column.Date',
        'Ext.grid.View',
        'Ext.toolbar.Toolbar',
        'Ext.button.Button',
        'Ext.grid.column.Action'
    ],

    height: 630,
    width: 642,
    layout: 'fit',
    title: '薪资分次发放',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'gridpanel',
                    itemId: 'subGrid',
                    store: 'PayrollSubStore',
                    columns: [
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'content',
                            text: '内容',
                            flex: 8
                        },
                        {
                            xtype: 'datecolumn',
                            dataIndex: 'date',
                            text: '发放日期'
                        },
                        {
                            xtype: 'actioncolumn',
                            text: '详情',
                            items: [
                                {
                                    handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                        var me = view.up('window'),
                                            payroll = me._payroll;

                                        Ext.create(me.getNs() + '.view.DynamicGrid',{
                                            _id : payroll.get('id'),
                                            _type : 'PayrollSub',
                                            _accountId : payroll.get('accountId'),
                                            _record : payroll,
                                            _canEdit : false,
                                            _opts : {
                                                type : 'PayrollSub',
                                                payrollSubId : record.getId()
                                            }
                                        }).show();
                                    },
                                    iconCls: 's_icon_action_search'
                                }
                            ]
                        }
                    ],
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            ui: 'footer',
                            items: [
                                {
                                    xtype: 'button',
                                    text: '添加发放',
                                    listeners: {
                                        click: {
                                            fn: me.onButtonClick,
                                            scope: me
                                        }
                                    }
                                },
                                {
                                    xtype: 'button',
                                    text: '删除发放',
                                    listeners: {
                                        click: {
                                            fn: me.onButtonClick1,
                                            scope: me
                                        }
                                    }
                                }
                            ]
                        }
                    ]
                }
            ],
            listeners: {
                afterrender: {
                    fn: me.onWindowAfterRender,
                    scope: me
                }
            }
        });

        me.callParent(arguments);
    },

    onButtonClick: function(button, e, eOpts) {
        var me = this,
            grid = me.down('grid'),
            store = grid.getStore(),
            payroll = me._payroll;

        Ext.create(me.getNs() + '.view.PayrollSubForm',{
            _store : store,
            _payroll : payroll
        }).show();
    },

    onButtonClick1: function(button, e, eOpts) {
        var me = this,
            grid = me.down('grid'),
            store = grid.getStore(),
            payroll = me._payroll,
            records = grid.getSelectionModel().getSelection();

        if(records.length==0)
        {
            Ext.Msg.alert('提示', '请选一条分次发放！');
            return;
        }

        Ext.Msg.confirm({
            title:"提示",
            msg:"确定删除本次分次发放？",
            buttons:Ext.MessageBox.OKCANCEL,
            width:200,
            fn:function(buttonId){
                if(buttonId=="ok"){
                    records[0].destroy({
                        params : {
                            id : records[0].getId()
                        }
                    });
                    store.remove(records[0]);
                }
            }
        });
    },

    onWindowAfterRender: function(component, eOpts) {
        var me = this,
            payroll = me._payroll,
            payrollId = payroll.get('id'),
            grid = me.down('grid'),
            store = grid.getStore();

        store.load({
            params : {
                payrollId : payrollId
            }
        });
    },

    checkParent: function(node) {
        node = node.parentNode;
        if(!node) return;
        var checkP=false;
        node.cascadeBy(function (n)
                       {
                           if (n != node) {
                               if (n.get('checked') == true) {
                                   checkP = true;
                               }
                           }
                       });
        node.set('checked', checkP);
        this.checkParent(node);

    },

    removeTree: function(node) {
        if (!node) return;
        while (node.hasChildNodes()) {
            this.removeTree(node.firstChild);
            node.removeChild(node.firstChild);
        }
    },

    hasLeaf: function(rows) {
        var flag = false;
        Ext.each(rows, function (item) {
            if(item.isLeaf()){
                flag = true;
            }
        });
        return flag;
    }

});