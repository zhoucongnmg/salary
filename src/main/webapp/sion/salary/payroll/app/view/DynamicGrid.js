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
        'Ext.form.field.Text',
        'Ext.form.field.Checkbox',
        'Ext.grid.Panel',
        'Ext.grid.column.Column',
        'Ext.grid.View',
        'Ext.grid.plugin.RowEditing'
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
                            itemId: 'saveBtn',
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
                            renderer: function(value, displayField) {
                                return Ext.util.Format.date(value, 'Y年m月');
                            },
                            columnWidth: 0.17,
                            padding: '0 0 0 20',
                            fieldLabel: '薪资月份',
                            labelWidth: 60,
                            name: 'month',
                            value: ''
                        },
                        {
                            xtype: 'displayfield',
                            renderer: function(value, displayField) {
                                return Ext.util.Format.date(value, 'Y年m月');
                            },
                            columnWidth: 0.17,
                            padding: '0 0 0 20',
                            fieldLabel: '社保扣费月',
                            labelWidth: 70,
                            name: 'socialCostMonth',
                            value: ''
                        },
                        {
                            xtype: 'displayfield',
                            renderer: function(value, displayField) {
                                return Ext.Object.getSize(value);
                            },
                            columnWidth: 0.16,
                            padding: '0 0 0 20',
                            fieldLabel: '人员总数',
                            labelWidth: 60,
                            name: 'persons',
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
                            boxLabel: '显示单位社保/公积金'
                        },
                        {
                            xtype: 'checkboxfield',
                            padding: '0 0 0 10',
                            name: 'showPersonalSocial',
                            boxLabel: '显示个人社保/公积金'
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
                            xtype: 'button',
                            margin: '0 0 0 20',
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
                    xtype: 'panel',
                    flex: 1,
                    itemId: 'gridPanel',
                    overflowX: 'auto',
                    layout: {
                        type: 'hbox',
                        align: 'stretch'
                    },
                    items: [
                        {
                            xtype: 'gridpanel',
                            itemId: 'simpleGrid',
                            width: 200,
                            autoScroll: true,
                            emptyText: '无工资条数据',
                            columns: [
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'string'
                                }
                            ]
                        },
                        {
                            xtype: 'gridpanel',
                            itemId: 'mainGrid',
                            width: 1050,
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
                                Ext.create('Ext.grid.plugin.RowEditing', {
                                    saveBtnText: '保存',
                                    cancelBtnText: '取消',
                                    hideMode: 'display',
                                    listeners: {
                                        edit: {
                                            fn: me.onRowEditingEdit1,
                                            scope: me
                                        },
                                        beforeedit: {
                                            fn: me.onRowEditingBeforeEdit1,
                                            scope: me
                                        }
                                    }
                                })
                            ]
                        }
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
            subGrids = me.query('grid[_subGrid=true]')||[],
            grid = me.down('#mainGrid'),
            store = grid.getStore(),
            data = [];

        Ext.Array.each(subGrids,function(subGrid,index){
            var subStore = subGrid.getStore();
            subStore.sync();
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
            searchForm = me.down('#searchForm'),
            opts = searchForm.getForm().getValues();

        Ext.Ajax.request({
            url:'salary/payroll/saveExcelTemp',
            method : 'POST',
            jsonData : {
                opts : opts
            },
            success: function(response){
                var json = Ext.JSON.decode(response.responseText);
                window.location.href = 'salary/payroll/exportItemList?id=' + id + '&optsId=' + json.message;
            },
            failure: function(){
            }
        });
    },

    onButtonClick11: function(button, e, eOpts) {
        var me = this,
            id = me._id,
            searchForm = me.down('#searchForm'),
            opts = searchForm.getForm().getValues();

        Ext.Ajax.request({
            url:'salary/payroll/saveExcelTemp',
            method : 'POST',
            jsonData : {
                opts : opts
            },
            success: function(response){
                var json = Ext.JSON.decode(response.responseText);
                window.location.href = 'salary/payroll/createPayrollExcel?id=' + id + '&optsId=' + json.message;
            },failure: function(){
            }
        });

    },

    onButtonClick111: function(button, e, eOpts) {
        var me = this;
        me.close();
    },

    onButtonClick2: function(button, e, eOpts) {
        var me = this,
            searchForm = me.down('#searchForm'),
            id = me._id,
            record = me._record,
            ns = me.getNamespace(),
            grid = me.down('#simpleGrid'),
            form = me.down('form'),
            store = grid.getStore(),
            values = searchForm.getForm().getValues();

        me.loadData(id,grid,store,values);
    },

    onRowEditingEdit1: function(editor, context, eOpts) {


        var me = this,
            record = context.record;


        me.calculate(record,function(values){
            for (var key in values) {
                record.set(key,values[key]);
            }
        });

    },

    onRowEditingBeforeEdit1: function(editor, context, eOpts) {
        var me = this;
        editor.editor.hideToolTip();
        return me._canEdit;

    },

    onWindowBeforeRender: function(component, eOpts) {
        var me = this,
            id = me._id,
            record = me._record,
            type = me._type,
            ns = me.getNamespace(),
            searchForm = me.down('#searchForm'),
            simpleGrid = me.down('#simpleGrid'),
            mainGrid = me.down('#mainGrid'),
            form = me.down('form'),
            saveBtn = me.down('#saveBtn'),
            simpleStore = Ext.create(ns+'.store.PayrollItem',{
                storeId : 'simplePayrollItem'
            }),
            opts = me._opts,
            store = Ext.create(ns+'.store.PayrollItem');

        saveBtn.setVisible(me._canEdit);
        me.setTitle(record.get('subject'));
        form.loadRecord(record);
        if (type == 'Payroll') {
            var subStore = Ext.create(ns+'.store.PayrollSubStore',{
                storeId : 'dynamicPayrollSubStore'
            });

            subStore.load({
                params : {
                    payrollId : id,
                },
                callback: function(records, operation, success) {
                    Ext.Array.each(records,function(r,index){
                        var store = Ext.create(ns+'.store.PayrollItem',{
                            storeId : 'payrollItem-' + r.get('id')
                        });
                        var subGrid = me.insertGrid(r);
                        me.loadData(id,subGrid,store,{
                            payrollSubId : r.getId(),
                            type : 'PayrollSub'
                        });
                    });

                }

            });
        }else {
            searchForm.hide();
        }

        me.loadData(id,mainGrid,store,opts);
        opts.query = 'Simple';
        me.loadData(id,simpleGrid,simpleStore,opts);

    },

    calculate: function(record, cb) {
        var me = this,
            grid = me.down('grid'),
            id = me._id,
            type = me._opts.type,
            url = type=='Payroll'?'salary/payroll/calculate':'salary/payroll/calculateSub',
            accountId = me._accountId;

        Ext.Ajax.request({
            url:url,
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
            async : false,
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
                grid.setWidth(json.data.columns.length*100);
            },failure: function(){
                Ext.Msg.alert("提示", "加载失败");
            }
        });
    },

    insertGrid: function(payrollSub) {
        var me = this,
            canEdit = me._canEdit,
            mainGrid = me.down('#mainGrid'),
            panel = me.down('#gridPanel'),
            grid = Ext.create(me.getNs()+'.view.PayrollSubGrid',{
                itemId : payrollSub.getId(),
                _mainGrid : mainGrid,
                _canEdit : canEdit,
                _dynamicGrid : me,
                _subGrid : true
            });

        panel.insert(1,grid);

        return grid;
    }

});