/*
 * File: app/view/PayrollForm.js
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

Ext.define('sion.salary.payroll.view.PayrollForm', {
    extend: 'Ext.form.Panel',
    alias: 'widget.myform1',

    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.button.Button',
        'Ext.form.FieldSet',
        'Ext.toolbar.Spacer',
        'Ext.form.field.Date',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Hidden',
        'Ext.grid.Panel',
        'Ext.grid.column.Column',
        'Ext.selection.CheckboxModel'
    ],

    height: 420,
    itemId: 'PayrollForm',
    width: 671,
    bodyPadding: 10,
    title: '',

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
                            iconCls: 's_icon_page_add',
                            text: '保存薪资信息',
                            listeners: {
                                click: {
                                    fn: me.onButtonClick,
                                    scope: me
                                }
                            }
                        }
                    ]
                }
            ],
            items: [
                {
                    xtype: 'fieldset',
                    layout: 'column',
                    title: '薪资信息',
                    items: [
                        {
                            xtype: 'textfield',
                            columnWidth: 0.35,
                            fieldLabel: '薪资主题',
                            labelWidth: 80,
                            name: 'subject',
                            allowBlank: false,
                            blankText: '薪资主题不能为空',
                            validateBlank: true
                        },
                        {
                            xtype: 'tbspacer',
                            columnWidth: 0.2,
                            height: 20
                        },
                        {
                            xtype: 'datefield',
                            columnWidth: 0.35,
                            fieldLabel: '薪资月份',
                            name: 'month',
                            allowBlank: false,
                            blankText: '薪资月份不能为空',
                            validateBlank: true,
                            format: 'Y年m月',
                            submitFormat: 'Y年m月'
                        },
                        {
                            xtype: 'tbspacer',
                            columnWidth: 1,
                            height: 20
                        },
                        {
                            xtype: 'combobox',
                            columnWidth: 0.35,
                            fieldLabel: '薪资套账',
                            labelWidth: 80,
                            name: 'accountId',
                            allowBlank: false,
                            blankText: '套帐方案不能为空',
                            emptyText: '--请选择--',
                            validateBlank: true,
                            editable: false,
                            displayField: 'name',
                            store: 'AccountStore',
                            valueField: 'id',
                            listeners: {
                                select: {
                                    fn: me.onComboboxSelect,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'tbspacer',
                            columnWidth: 0.2,
                            height: 20
                        },
                        {
                            xtype: 'datefield',
                            columnWidth: 0.35,
                            fieldLabel: '社保扣费月',
                            name: 'socialCostMonth',
                            allowBlank: false,
                            blankText: '社保月份不能为空',
                            validateBlank: true,
                            format: 'Y年m月',
                            submitFormat: 'Y年m月'
                        },
                        {
                            xtype: 'tbspacer',
                            columnWidth: 1,
                            height: 20
                        },
                        {
                            xtype: 'hiddenfield',
                            fieldLabel: 'Label',
                            name: 'date'
                        },
                        {
                            xtype: 'hiddenfield',
                            fieldLabel: 'Label',
                            name: 'createPersonName'
                        },
                        {
                            xtype: 'hiddenfield',
                            fieldLabel: 'Label',
                            name: 'id'
                        },
                        {
                            xtype: 'hiddenfield',
                            fieldLabel: 'Label',
                            name: 'state'
                        },
                        {
                            xtype: 'hiddenfield',
                            fieldLabel: 'Label',
                            name: 'persons'
                        },
                        {
                            xtype: 'gridpanel',
                            columnWidth: 1,
                            height: 295,
                            store: 'PersonStore',
                            columns: [
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'name',
                                    text: '姓名',
                                    flex: 1
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'dept',
                                    text: '部门',
                                    flex: 0.6
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'position',
                                    text: '职务',
                                    flex: 1.7
                                }
                            ],
                            selModel: Ext.create('Ext.selection.CheckboxModel', {

                            })
                        }
                    ]
                }
            ],
            listeners: {
                afterrender: {
                    fn: me.onPayrollFormAfterRender,
                    scope: me
                }
            }
        });

        me.callParent(arguments);
    },

    onButtonClick: function(button, e, eOpts) {
        var me = this,
            form = me.getForm(),
            record = form.getRecord(),
            itemGrid = me.down('gridpanel'),
            itemMap = {},
            itemStore = itemGrid.getStore();


        if(!form.isValid()){
            Ext.Msg.alert("提示", "信息不完整，请继续填写！");
            return false;
        }

        form.updateRecord();
        itemStore.each(function(item){
            itemMap[item.get('id')] = item.get('name');
        });

        record.set('persons',itemMap);

        record.save({
            success: function(response, opts){
                me.close();
                Ext.Msg.alert("提示", "保存成功");
            },
            failure: function(){
                Ext.Msg.alert("提示", "保存失败");
            }
        });
    },

    onComboboxSelect: function(combo, records, eOpts) {
        var me = this,
        store = me.down('gridpanel').getStore();

        store.load({
           params:{
               accountId:records[0].get('id')
            }
        });

    },

    onPayrollFormAfterRender: function(component, eOpts) {
        var me = this,
            record = me._link.record;
        me.loadRecord(record);
    }

});