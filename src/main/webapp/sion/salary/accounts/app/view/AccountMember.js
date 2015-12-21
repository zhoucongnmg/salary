/*
 * File: app/view/AccountMember.js
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

Ext.define('sion.salary.accounts.view.AccountMember', {
    extend: 'Ext.window.Window',

    requires: [
        'Ext.grid.Panel',
        'Ext.grid.column.Action',
        'Ext.grid.View',
        'Ext.button.Button',
        'Ext.toolbar.Paging'
    ],

    height: 540,
    width: 760,
    layout: 'fit',
    title: '方案成员',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'gridpanel',
                    header: false,
                    store: 'PersonAccount',
                    columns: [
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'personCode',
                            text: '员工编号',
                            flex: 1
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'name',
                            text: '员工姓名',
                            flex: 1
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'duty',
                            text: '职务',
                            flex: 1
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'dept',
                            text: '部门',
                            flex: 1
                        },
                        {
                            xtype: 'actioncolumn',
                            hideable: false,
                            text: '薪资设定',
                            flex: 0.5,
                            items: [
                                {
                                    handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                        var me = this.up('gridpanel').up(),
                                            account = me._account,
                                            //     store = Ext.getStore('PersonAccountItem');
                                            namespace = me.getNamespace();

                                        var config =  Ext.create(namespace + '.view.AccountMemberConfig',{
                                            _account : account,
                                            _member : record
                                        });
                                        var store = config.down('gridpanel').getStore();
                                        store.removeAll();
                                        store.add(record.get('accountItems'));
                                        config.show();
                                    },
                                    iconCls: 's_icon_coins',
                                    tooltip: '薪资设定'
                                }
                            ]
                        },
                        {
                            xtype: 'actioncolumn',
                            text: '删除',
                            flex: 0.5,
                            items: [
                                {
                                    handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                        var store = Ext.getStore('PersonAccount');

                                        record.set('accountId', '');
                                        if(record.get('insuredPerson') === ''){
                                            record.set('insuredPerson', null);
                                        }
                                        store.remove(record);
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
                        }
                    },
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'button',
                                    width: 70,
                                    iconCls: 's_icon_action_add',
                                    text: '新增',
                                    listeners: {
                                        click: {
                                            fn: me.onButtonClick,
                                            scope: me
                                        }
                                    }
                                },
                                {
                                    xtype: 'button',
                                    width: 70,
                                    iconCls: 's_icon_table_save',
                                    text: '保存',
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
            dockedItems: [
                {
                    xtype: 'pagingtoolbar',
                    dock: 'bottom',
                    width: 360,
                    displayInfo: true,
                    store: 'AccountMember'
                }
            ]
        });

        me.callParent(arguments);
    },

    onGridpanelRender: function(component, eOpts) {
        var me = this,
            account = me._account,
            store = component.getStore();

        store.clearFilter(true);
        Ext.apply(store.proxy.extraParams, {
            id : account.get('id')
        });
        store.load();
    },

    onButtonClick: function(button, e, eOpts) {
        var personSelection = Ext.create("sion.salary.social.view.SearchPerson",
                                         {_scope : this, _callback : this.selectedCallback});

        personSelection.show();
    },

    onButtonClick1: function(button, e, eOpts) {
        var me = this,
            memberStore = Ext.getStore('PersonAccount');
        //     store = Ext.getStore('Account'),
        //     account = me._account;
        console.log(memberStore);
        memberStore.sync({
            success: function(response, opts){
                Ext.Msg.alert("提示", "保存成功");
        //         store.load();
                me.close();
            },
            failure: function(){
                Ext.Msg.alert("提示", "保存失败");
                me.close();
            }
        });
    },

    selectedCallback: function(person, scope) {
        var me = scope,
            namespace = me.getNamespace(),
            account = me._account,
            store = Ext.getStore('PersonAccount');

        for(var i = 0; i < person.length; i++){
            if(store.find('id', person[i].data.id) === -1){
                var model = Ext.create(namespace + '.model.PersonAccount');
                model.data = person[i].data;
                model.set('accountId', account.get('id'));
                if(model.get('insuredPerson') === ''){
                    model.set('insuredPerson', null);
                }
                store.add(model);
            }
        }
    }

});