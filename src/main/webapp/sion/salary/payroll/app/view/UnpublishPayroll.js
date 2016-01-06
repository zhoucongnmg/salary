/*
 * File: app/view/UnpublishPayroll.js
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

Ext.define('sion.salary.payroll.view.UnpublishPayroll', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.unpublishpayroll',

    requires: [
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Date',
        'Ext.toolbar.Spacer',
        'Ext.button.Button',
        'Ext.grid.Panel',
        'Ext.grid.column.Date',
        'Ext.grid.column.Action',
        'Ext.selection.CheckboxModel',
        'Ext.toolbar.Paging'
    ],

    height: 487,
    itemId: 'getUnpublishPayroll',
    width: 670,
    layout: 'border',
    title: '',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            dockedItems: [
                {
                    xtype: 'form',
                    dock: 'top',
                    width: 100,
                    layout: 'column',
                    bodyPadding: 10,
                    title: '',
                    items: [
                        {
                            xtype: 'textfield',
                            columnWidth: 0.25,
                            itemId: 'subject',
                            margin: '',
                            fieldLabel: '薪资主题',
                            labelWidth: 60
                        },
                        {
                            xtype: 'combobox',
                            columnWidth: 0.25,
                            hidden: true,
                            padding: '0 0 0 25',
                            fieldLabel: '薪资套账',
                            labelWidth: 60
                        },
                        {
                            xtype: 'datefield',
                            columnWidth: 0.2,
                            itemId: 'month',
                            padding: '0 0 0 25',
                            fieldLabel: '薪资月份',
                            labelWidth: 60,
                            format: 'Y-m'
                        },
                        {
                            xtype: 'datefield',
                            columnWidth: 0.22,
                            itemId: 'socialCostMonth',
                            padding: '0 0 0 25',
                            fieldLabel: '社保扣费月',
                            labelWidth: 80,
                            format: 'Y-m'
                        },
                        {
                            xtype: 'tbspacer',
                            columnWidth: 0.03,
                            height: 15
                        },
                        {
                            xtype: 'button',
                            columnWidth: 0.05,
                            text: '查询',
                            listeners: {
                                click: {
                                    fn: me.onButtonClick1,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'tbspacer',
                            columnWidth: 0.02,
                            height: 20
                        },
                        {
                            xtype: 'button',
                            columnWidth: 0.05,
                            text: '清空',
                            listeners: {
                                click: {
                                    fn: me.onButtonClick21,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'tbspacer',
                            columnWidth: 0.02,
                            height: 20
                        },
                        {
                            xtype: 'button',
                            columnWidth: 0.05,
                            text: '发放',
                            listeners: {
                                click: {
                                    fn: me.onButtonClick,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'tbspacer',
                            columnWidth: 0.02,
                            height: 20
                        },
                        {
                            xtype: 'button',
                            columnWidth: 0.05,
                            text: '新建',
                            listeners: {
                                click: {
                                    fn: me.onButtonClick2,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'tbspacer',
                            columnWidth: 1.1,
                            height: 20
                        }
                    ]
                }
            ],
            items: [
                {
                    xtype: 'gridpanel',
                    region: 'center',
                    itemId: 'PayrollGrid',
                    title: '',
                    store: 'PayrollStore',
                    columns: [
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'subject',
                            menuDisabled: true,
                            text: '薪资主题',
                            flex: 1
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'accountName',
                            menuDisabled: true,
                            text: '套账名称',
                            flex: 1
                        },
                        {
                            xtype: 'gridcolumn',
                            renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                                return value=='Unpublish'?'待发':'已发';
                            },
                            dataIndex: 'state',
                            menuDisabled: true,
                            text: '状态',
                            flex: 1
                        },
                        {
                            xtype: 'datecolumn',
                            dataIndex: 'month',
                            menuDisabled: true,
                            text: '薪资月份',
                            flex: 1,
                            format: 'Y-m'
                        },
                        {
                            xtype: 'datecolumn',
                            dataIndex: 'socialCostMonth',
                            menuDisabled: true,
                            text: '社保扣费月',
                            flex: 1,
                            format: 'Y-m'
                        },
                        {
                            xtype: 'gridcolumn',
                            renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                                // console.log(record.get('persons').length);
                                // return record.get('persons').size();
                                return Ext.Object.getSize(record.get('persons'));
                            },
                            menuDisabled: true,
                            text: '发放人数',
                            flex: 1
                        },
                        {
                            xtype: 'gridcolumn',
                            menuDisabled: true,
                            text: '薪资总额',
                            flex: 1
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'createPersonName',
                            menuDisabled: true,
                            text: '创建人',
                            flex: 1
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'createDate',
                            menuDisabled: true,
                            text: '创建日期',
                            flex: 1
                        },
                        {
                            xtype: 'actioncolumn',
                            width: 61,
                            menuDisabled: true,
                            menuText: '',
                            text: '编辑',
                            flex: 1,
                            items: [
                                {
                                    handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                        var window = Ext.create('sion.salary.payroll.view.PayrollWindow',{
                                            title:'修改工资条',
                                            _link:{
                                                record:record
                                            }
                                        });
                                        window.show();
                                    },
                                    iconCls: 's_icon_table_edit'
                                }
                            ]
                        },
                        {
                            xtype: 'actioncolumn',
                            text: '删除',
                            flex: 1,
                            items: [
                                {
                                    handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                        Ext.Msg.confirm({
                                            title:"提示",
                                            msg:"确定删除工资条？",
                                            buttons:Ext.MessageBox.OKCANCEL,
                                            width:200,
                                            fn:function(buttonId){
                                                if(buttonId=="ok"){

                                                    //5.0以后使用erase，5.0以前为destroy
                                                    record.destroy({
                                                        success: function(){
                                                            record.commit();
                                                            Ext.Msg.alert('提示','删除成功');
                                                        },
                                                        failure: function(record){
                                                            record.reject();
                                                            Ext.Msg.alert('提示', '请检查网络连接状况');
                                                        }
                                                    });
                                                }
                                            }
                                        });

                                    },
                                    iconCls: 's_icon_table_delete'
                                }
                            ]
                        },
                        {
                            xtype: 'actioncolumn',
                            width: 62,
                            menuDisabled: true,
                            text: '发放',
                            flex: 1,
                            items: [
                                {
                                    handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                        var grid = view.up('grid'),
                                            store = grid.getStore();

                                        Ext.Msg.confirm({
                                            title:"提示",
                                            msg:"确定发放工资？",
                                            buttons:Ext.MessageBox.OKCANCEL,
                                            width:200,
                                            fn:function(buttonId){
                                                if(buttonId=="ok"){
                                                    record.set('state','Paid');
                                                    record.save({
                                                        success: function(response, opts){
                                                            store.removeAt(rowIndex);
                                                            store.commitChanges();
                                                            Ext.Msg.alert("提示", "操作成功");
                                                        },
                                                        failure: function(){
                                                            Ext.Msg.alert("提示", "操作失败");
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    },
                                    iconCls: 's_icon_action_search'
                                }
                            ]
                        },
                        {
                            xtype: 'actioncolumn',
                            width: 62,
                            menuDisabled: true,
                            text: '查看',
                            flex: 1,
                            items: [
                                {
                                    handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                        var me = this.up('unpublishpayroll'),
                                            namespace = me.getNamespace();


                                        Ext.create(namespace + '.view.DynamicGrid',{
                                            _id : record.get('id'),
                                            _accountId : record.get('accountId'),
                                            _record : record
                                        }).show();

                                    },
                                    iconCls: 's_icon_action_search'
                                }
                            ]
                        }
                    ],
                    selModel: Ext.create('Ext.selection.CheckboxModel', {

                    }),
                    dockedItems: [
                        {
                            xtype: 'pagingtoolbar',
                            dock: 'bottom',
                            width: 360,
                            afterPageText: '共{0}页',
                            beforePageText: '当前',
                            displayInfo: true,
                            displayMsg: '当前 {0} - {1} 共{2}',
                            emptyMsg: '无记录',
                            firstText: '首页',
                            lastText: '尾页',
                            nextText: '下一页',
                            prevText: '上一页',
                            refreshText: '刷新',
                            store: 'PayrollStore',
                            listeners: {
                                beforechange: {
                                    fn: me.onPagingtoolbarBeforeChange,
                                    scope: me
                                }
                            }
                        }
                    ]
                }
            ],
            listeners: {
                activate: {
                    fn: me.onGetUnpublishPayrollActivate,
                    scope: me
                }
            }
        });

        me.callParent(arguments);
    },

    onButtonClick1: function(button, e, eOpts) {
        var me = this,
            store = me.down('#PayrollGrid').getStore(),
            month = me.down('#month').getValue(),
            subject = me.down('#subject').getValue(),
            socialCostMonth = me.down('#socialCostMonth').getValue();

        store.load({
            params:{
                state:'Unpublish',
                start:'0',
                page:'1',
                subject:subject,
                month:month,
                socialCostMonth:socialCostMonth
            }
        });
    },

    onButtonClick21: function(button, e, eOpts) {
        var me = this,
            store = me.down('gridpanel').getStore();
        store.load({
            params:{
                state:'Unpublish',
                start:'0',
                page:'1'
            }
        });

        month = me.down('#month').setValue('');
        subject = me.down('#subject').setValue('');
        socialCostMonth = me.down('#socialCostMonth').setValue('');
    },

    onButtonClick: function(button, e, eOpts) {
        var me = this,
            grid = me.down('gridpanel'),
            store = grid.getStore(),
            arrId = [],
            records = grid.getSelectionModel().getSelection();

        if(records.length==0)
        {
            Ext.Msg.alert('提示', '请选择工资条！');
            return;
        }
        Ext.Msg.confirm({
            title:"提示",
            msg:"确定发放工资？",
            buttons:Ext.MessageBox.OKCANCEL,
            width:200,
            fn:function(buttonId){
                if(buttonId=="ok"){

                    if(records.length>1){
                        Ext.each(records, function (item) {
                            item.set('state','Paid');
                            arrId.push(item.get('id'));
                        });

                        Ext.Ajax.request({
                            url: 'salary/payroll/batchUpdate',
                            method: "post",
                            success: function (response, opts) {
                                store.reload();
                                Ext.Msg.alert('提示', '操作成功！');
                            },
                            failure: function () {
                                Ext.Msg.alert('提示', '操作失败，请检查网络');
                            },
                            params: {arrId:arrId}
                        });
                    }
                    else
                    {
                        records[0].set('state','Paid');
                        records[0].save({
                            success: function(response, opts){
                                store.remove(records[0]);
                                store.commitChanges();
                                Ext.Msg.alert("提示", "操作成功");
                            },
                            failure: function(){
                                Ext.Msg.alert("提示", "操作失败");
                            }
                        });
                    }
                }
            }
        });
    },

    onButtonClick2: function(button, e, eOpts) {
        var me = this,
            window,
            grid = me.down('gridpanel'),
            store = grid.getStore(),
            record = Ext.create('sion.salary.payroll.model.Payroll');

        window = Ext.create('sion.salary.payroll.view.PayrollWindow',{
            title:'新建工资条',
            _link:{
                record:record,
                payrollStore:store
            }
        });
        window.show();
    },

    onPagingtoolbarBeforeChange: function(pagingtoolbar, page, eOpts) {
        var me = this,
            grid = me.down('gridpanel');
        Ext.apply(grid.getStore().model.proxy.extraParams,{

            state:'Unpublish'

        });
    },

    onGetUnpublishPayrollActivate: function(component, eOpts) {
        var me = this,
            store = me.down('gridpanel').getStore();
        store.load({
            params:{
                state:'Unpublish',
                start:'0',
                page:'1'
            }
        });
    }

});	