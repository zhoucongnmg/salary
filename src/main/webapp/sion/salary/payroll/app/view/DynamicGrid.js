/*
 * File: app/view/DynamicGrid.js
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

Ext.define('sion.salary.payroll.view.DynamicGrid', {
    extend: 'Ext.window.Window',

    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.button.Button',
        'Ext.form.Panel',
        'Ext.form.field.Display',
        'Ext.form.field.Checkbox',
        'Ext.form.field.ComboBox',
        'Ext.grid.Panel',
        'Ext.grid.column.Column',
        'Ext.grid.View',
        'Ext.grid.plugin.CellEditing'
    ],

    height: 700,
    width: 1050,

    layout: {
        type: 'vbox',
        align: 'stretch'
    },

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            dockedItems: [
                {
                    xtype: 'toolbar',
                    flex: 1,
                    dock: 'top',
                    layout: {
                        type: 'hbox',
                        align: 'middle'
                    },
                    items: [
                        {
                            xtype: 'button',
                            text: '保存',
                            listeners: {
                                click: {
                                    fn: me.onButtonClick,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'button',
                            text: '导出',
                            listeners: {
                                click: {
                                    fn: me.onButtonClick1,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'button',
                            text: '生成工资条',
                            listeners: {
                                click: {
                                    fn: me.onButtonClick11,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'button',
                            text: '关闭',
                            listeners: {
                                click: {
                                    fn: me.onButtonClick111,
                                    scope: me
                                }
                            }
                        }
                    ]
                }
            ],
            items: [
                {
                    xtype: 'form',
                    itemId: 'payrollForm',
                    layout: 'column',
                    bodyPadding: 10,
                    items: [
                        {
                            xtype: 'displayfield',
                            columnWidth: 0.25,
                            fieldLabel: '薪资主题',
                            labelWidth: 60,
                            name: 'subject',
                            value: ''
                        },
                        {
                            xtype: 'displayfield',
                            columnWidth: 0.25,
                            padding: '0 0 0 20',
                            fieldLabel: '薪资方案',
                            labelWidth: 60,
                            name: 'accountName',
                            value: ''
                        },
                        {
                            xtype: 'displayfield',
                            columnWidth: 0.17,
                            padding: '0 0 0 20',
                            fieldLabel: '薪资月份',
                            labelWidth: 60,
                            name: 'monthFmt',
                            value: ''
                        },
                        {
                            xtype: 'displayfield',
                            columnWidth: 0.17,
                            padding: '0 0 0 20',
                            fieldLabel: '社保扣费月',
                            labelWidth: 70,
                            name: 'socialCostMonthFmt',
                            value: ''
                        },
                        {
                            xtype: 'displayfield',
                            columnWidth: 0.16,
                            padding: '0 0 0 20',
                            fieldLabel: '人员总数',
                            labelWidth: 60,
                            name: 'personSize',
                            value: ''
                        }
                    ]
                },
                {
                    xtype: 'form',
                    itemId: 'searchForm',
                    layout: 'column',
                    bodyPadding: 10,
                    items: [
                        {
                            xtype: 'textfield',
                            fieldLabel: '姓名',
                            labelWidth: 50,
                            name: 'name'
                        },
                        {
                            xtype: 'checkboxfield',
                            padding: '0 0 0 20',
                            name: 'showCompanySocial',
                            boxLabel: '显示单位社保'
                        },
                        {
                            xtype: 'checkboxfield',
                            padding: '0 0 0 10',
                            name: 'showPersonalSocial',
                            boxLabel: '显示个人社保'
                        },
                        {
                            xtype: 'checkboxfield',
                            padding: '0 0 0 10',
                            name: 'showDept',
                            boxLabel: '显示部门/职位'
                        },
                        {
                            xtype: 'checkboxfield',
                            padding: '0 0 0 10',
                            name: 'showIdCard',
                            boxLabel: '显示身份证号'
                        },
                        {
                            xtype: 'checkboxfield',
                            padding: '0 0 0 10',
                            name: 'showBank',
                            boxLabel: '显示开户行/工资卡号'
                        },
                        {
                            xtype: 'combobox',
                            padding: '0 0 0 10',
                            width: 150,
                            fieldLabel: '排序方式',
                            labelWidth: 60,
                            name: 'sortBy',
                            store: [
                                [
                                    'bydept',
                                    '按组织机构'
                                ],
                                [
                                    'byusercode',
                                    '按员工编号'
                                ],
                                [
                                    'byusername',
                                    '按员工姓名'
                                ]
                            ]
                        },
                        {
                            xtype: 'button',
                            text: '查询',
                            listeners: {
                                click: {
                                    fn: me.onButtonClick2,
                                    scope: me
                                }
                            }
                        }
                    ]
                },
                {
                    xtype: 'gridpanel',
                    autoScroll: true,
                    emptyText: '无工资条数据',
                    columns: [
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'string',
                            columns: [
                                {
                                    xtype: 'gridcolumn',
                                    text: 'MyColumn3'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    text: 'MyColumn16'
                                }
                            ]
                        }
                    ],
                    plugins: [
                        Ext.create('Ext.grid.plugin.CellEditing', {
                            listeners: {
                                edit: {
                                    fn: me.onCellEditingEdit,
                                    scope: me
                                }
                            }
                        })
                    ]
                }
            ],
            listeners: {
                beforerender: {
                    fn: me.onWindowBeforeRender,
                    scope: me
                }
            }
        });

        me.callParent(arguments);
    },

    onButtonClick: function(button, e, eOpts) {
        var me = this,
            id = me._id,
            accountId = me._accountId,
            namespace = me.getNamespace(),
            mainModel = Ext.create(namespace + '.model.Main'),
            grid = me.down('grid'),
            store = grid.getStore(),
            data = [];

        store.each(function(record,index){
           record.set('payrollId',id);
        });

        store.sync({
            success: function(response, options){
                Ext.Msg.alert("提示", "保存工资条成功");

            },failure: function(response, options){
                Ext.Msg.alert("提示", "操作失败");
            }
        });

    },

    onButtonClick1: function(button, e, eOpts) {
        var me = this,
            id = me._id,
            accountId = me._accountId,
            namespace = me.getNamespace(),
            mainModel = Ext.create(namespace + '.model.Main'),
            grid = me.down('grid'),
            store = grid.getStore(),
            data = [];

        store.each(function(record,index){
           record.set('payrollId',id);
        });

        store.sync({
            success: function(response, options){
                Ext.Msg.alert("提示", "保存工资条成功");

            },failure: function(response, options){
                Ext.Msg.alert("提示", "操作失败");
            }
        });

    },

    onButtonClick11: function(button, e, eOpts) {
        var me = this,
            id = me._id,
            accountId = me._accountId,
            namespace = me.getNamespace(),
            mainModel = Ext.create(namespace + '.model.Main'),
            grid = me.down('grid'),
            store = grid.getStore(),
            data = [];

        store.each(function(record,index){
           record.set('payrollId',id);
        });

        store.sync({
            success: function(response, options){
                Ext.Msg.alert("提示", "保存工资条成功");

            },failure: function(response, options){
                Ext.Msg.alert("提示", "操作失败");
            }
        });

    },

    onButtonClick111: function(button, e, eOpts) {
        var me = this,
            id = me._id,
            accountId = me._accountId,
            namespace = me.getNamespace(),
            mainModel = Ext.create(namespace + '.model.Main'),
            grid = me.down('grid'),
            store = grid.getStore(),
            data = [];

        store.each(function(record,index){
           record.set('payrollId',id);
        });

        store.sync({
            success: function(response, options){
                Ext.Msg.alert("提示", "保存工资条成功");

            },failure: function(response, options){
                Ext.Msg.alert("提示", "操作失败");
            }
        });

    },

    onButtonClick2: function(button, e, eOpts) {
        var me = this,
            searchForm = me.down('#searchForm'),
            id = me._id,
            record = me._record,
            ns = me.getNamespace(),
            grid = me.down('grid'),
            form = me.down('form'),
            store = grid.getStore(),
            opts = searchForm.getForm().getValues();

        console.log(opts);
        me.loadData(id,grid,store,opts);
    },

    onCellEditingEdit: function(editor, e, eOpts) {
        var me = this,
            field = e.field,
            record = e.record;
        me.calculate(field,record,function(values){
            for (var key in values) {
                record.set(key,values[key]);
            }
        });
    },

    onWindowBeforeRender: function(component, eOpts) {
        var me = this,
            id = me._id,
            record = me._record,
            ns = me.getNamespace(),
            grid = me.down('grid'),
            form = me.down('form'),
            store = Ext.create(ns+'.store.PayrollItem');

        form.loadRecord(record);
        me.loadData(id,grid,store);
    },

    calculate: function(fieldId, record, cb) {
        var me = this,
            grid = me.down('grid'),
            id = me._id,
            accountId = me._accountId;
        /*
        Ext.Array.each(grid.columnManager.columns,function(column,index) {
            if (column.dataType == 'accountItem') {
                json[column.dataIndex] = record.get(column.dataIndex);
            }
        });
        */

        Ext.Ajax.request({
            url:'salary/payroll/calculate',
            async : false,
            jsonData : {
                accountId : accountId,
                record : record.data
            },
            success: function(response){
                var json = Ext.JSON.decode(response.responseText);
                Ext.callback(cb,me,[json.data]);
            },failure: function(){
                Ext.Msg.alert("提示", "加载失败");
            }
        });
    },

    loadData: function(id, grid, store, opts) {
        var me = this;
        Ext.Ajax.request({
            url:'salary/payroll/findItemList',
            jsonData : {
                id : id,
                opts : opts
            },
            success: function(response){
                store.removeAll();
                var json = Ext.JSON.decode(response.responseText);
                store.model.setFields(json.data.fields);
                store.add(json.data.data);
                grid.reconfigure(store, json.data.columns);
                me._columns = json.data.columns;
            },failure: function(){
                Ext.Msg.alert("提示", "加载失败");
            }
        });
    }

});