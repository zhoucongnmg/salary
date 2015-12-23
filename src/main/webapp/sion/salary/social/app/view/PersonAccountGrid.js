/*
 * File: app/view/PersonAccountGrid.js
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

Ext.define('sion.salary.social.view.PersonAccountGrid', {
    extend: 'Ext.grid.Panel',

    requires: [
        'Ext.button.Button',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Date',
        'Ext.grid.column.Column',
        'Ext.grid.View',
        'Ext.toolbar.Paging'
    ],

    width: 1006,
    store: 'PersonAccountStore',

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
                        },
                        {
                            xtype: 'button',
                            margin: '0 0 0 10',
                            iconCls: 's_icon_action_search',
                            text: '查询',
                            listeners: {
                                click: {
                                    fn: me.onSearchClick1,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'button',
                            margin: '0 0 0 10',
                            iconCls: 's_icon_action_clockwise',
                            text: '重置',
                            listeners: {
                                click: {
                                    fn: me.onResetClick1,
                                    scope: me
                                }
                            }
                        }
                    ]
                },
                {
                    xtype: 'toolbar',
                    dock: 'top',
                    items: [
                        {
                            xtype: 'combobox',
                            itemId: 'salaryAccount',
                            fieldLabel: '薪资方案',
                            labelWidth: 60,
                            displayField: 'name',
                            store: 'SalaryAccount',
                            valueField: 'id'
                        },
                        {
                            xtype: 'combobox',
                            itemId: 'socialAccount',
                            fieldLabel: '社保方案',
                            labelWidth: 60,
                            displayField: 'name',
                            store: 'SocialAccount',
                            valueField: 'id'
                        },
                        {
                            xtype: 'combobox',
                            itemId: 'status',
                            margin: '0 0 0 10',
                            width: 160,
                            fieldLabel: '社保状态',
                            labelWidth: 60,
                            store: [
                                [
                                    'In',
                                    '在保'
                                ],
                                [
                                    'Out',
                                    '退保'
                                ]
                            ]
                        },
                        {
                            xtype: 'datefield',
                            itemId: 'from',
                            margin: '0 0 0 10',
                            width: 170,
                            fieldLabel: '参保日期',
                            labelWidth: 60,
                            format: 'Y-m-d'
                        },
                        {
                            xtype: 'datefield',
                            itemId: 'to',
                            margin: '0 0 0 10',
                            width: 120,
                            fieldLabel: ' 至 ',
                            labelSeparator: ' ',
                            labelWidth: 10,
                            format: 'Y-m-d'
                        }
                    ]
                },
                {
                    xtype: 'pagingtoolbar',
                    dock: 'bottom',
                    width: 360,
                    displayInfo: true,
                    store: 'PersonAccountStore'
                }
            ],
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
                    dataIndex: 'dept',
                    text: '部门',
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
                    dataIndex: 'idCard',
                    text: '身份证号',
                    flex: 1
                },
                {
                    xtype: 'gridcolumn',
                    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                        var store = this._salaryAccountStore;
                        if(store.getCount()===0){
                            store.load();
                        }
                        var index=store.find("id",value);
                        if(index>-1){
                            var model=store.getAt(index);
                            return model.data.name;
                        }
                        return value;
                    },
                    dataIndex: 'accountId',
                    text: '薪资方案',
                    flex: 1
                },
                {
                    xtype: 'gridcolumn',
                    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                        var store = this._levelStore;
                        if(store.getCount()===0){
                            store.load();
                        }
                        var index=store.find("id",value);
                        if(index>-1){
                            var model=store.getAt(index);
                            return model.data.name;
                        }
                        return value;
                    },
                    dataIndex: 'level',
                    text: '薪资层次',
                    flex: 1
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'rank',
                    text: '薪资级别',
                    flex: 1
                },
                {
                    xtype: 'gridcolumn',
                    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {

                        var store = this._socialAccountStore;
                        if(store.getCount()===0){
                            store.load();
                        }
                        var index=store.find("id",value.accountId);
                        if(index>-1){
                            var model=store.getAt(index);
                            return model.data.name;
                        }
                        return value.accountId;

                    },
                    dataIndex: 'insuredPerson',
                    text: '社保方案',
                    flex: 1
                },
                {
                    xtype: 'gridcolumn',
                    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                        if(value.status==="In")
                        return '在保';
                        else if(value.status==="Out")
                        return '退保';
                    },
                    dataIndex: 'insuredPerson',
                    text: '社保状态',
                    flex: 1
                },
                {
                    xtype: 'gridcolumn',
                    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                        return value.socialWorkplace;
                    },
                    dataIndex: 'insuredPerson',
                    text: '社保代付地',
                    flex: 1
                },
                {
                    xtype: 'gridcolumn',
                    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                        return value.accumulationFundsWorkplace;
                    },
                    dataIndex: 'insuredPerson',
                    text: '公积金代付地',
                    flex: 1
                }
            ],
            listeners: {
                afterrender: {
                    fn: me.onGridpanelAfterRender,
                    scope: me
                },
                itemdblclick: {
                    fn: me.onGridpanelItemDblClick,
                    scope: me
                },
                beforerender: {
                    fn: me.onGridpanelBeforeRender,
                    scope: me
                }
            }
        });

        me.processPersonAccountGrid(me);
        me.callParent(arguments);
    },

    processPersonAccountGrid: function(config) {
        var me=this,
            ns=me.getNamespace();
        config._salaryAccountStore=Ext.create(ns+'.store.SalaryAccount');
        config._socialAccountStore=Ext.create(ns+'.store.SocialAccount');
        config._levelStore=Ext.create(ns+'.store.LevelStore');
        return config;
    },

    onButtonClick: function(button, e, eOpts) {
        var me=this,
            namespace=me.getNamespace();
        Ext.create(namespace+'.view.PersonAccountForm',{_grid:me}).show();

    },

    onSearchClick1: function(button, e, eOpts) {
        var me=this,
            store=me.getStore(),
            dateUtil = Ext.create(me.getNamespace() + '.controller.DateUtil'),
            startDate=me.down('#from').getValue(),
            endDate=me.down('#to').getValue();
        store.getProxy().setExtraParam("from",startDate===null?"":dateUtil.format(new Date(startDate),'yyyy-MM-dd'));
        store.getProxy().setExtraParam("to",endDate===null?"":dateUtil.format(new Date(endDate),'yyyy-MM-dd'));
        store.getProxy().setExtraParam("status",me.down('#status').getValue());
        store.getProxy().setExtraParam("salaryAccount",me.down('#salaryAccount').getValue());
        store.getProxy().setExtraParam("socialAccount",me.down('#socialAccount').getValue());

        store.reload();
    },

    onResetClick1: function(button, e, eOpts) {
        var me=this,
            store=me.getStore();

        store.getProxy().setExtraParam("from","");
        store.getProxy().setExtraParam("to","");
        store.getProxy().setExtraParam("status","");
        store.getProxy().setExtraParam("salaryAccount","");
        store.getProxy().setExtraParam("socialAccount","");

        me.down('#from').setValue("");
        me.down('#to').setValue("");
        me.down('#status').setValue("");
        me.down('#salaryAccount').setValue("");
        me.down('#socialAccount').setValue("");
    },

    onGridpanelAfterRender: function(component, eOpts) {


        component.getStore().load();
    },

    onGridpanelItemDblClick: function(dataview, record, item, index, e, eOpts) {
        var me=this,
                    namespace=me.getNamespace();
                Ext.create(namespace+'.view.PersonAccountForm',{_grid:me,_record:record}).show();
    },

    onGridpanelBeforeRender: function(component, eOpts) {
        this._salaryAccountStore.load();
        this._socialAccountStore.load();
        this._levelStore.load();
    }

});