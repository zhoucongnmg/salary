/*
 * File: app/view/PayrollGrid.js
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

Ext.define('sion.salary.payroll.view.PayrollGrid', {
    extend: 'Ext.grid.Panel',

    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Date',
        'Ext.toolbar.Spacer',
        'Ext.button.Button',
        'Ext.grid.View',
        'Ext.grid.column.Action'
    ],

    height: 515,
    width: 1006,

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'top',
                    items: [
                        {
                            xtype: 'form',
                            width: 908,
                            layout: 'column',
                            bodyPadding: 10,
                            items: [
                                {
                                    xtype: 'textfield',
                                    columnWidth: 0.25,
                                    margin: '',
                                    fieldLabel: '薪资主题',
                                    labelWidth: 60
                                },
                                {
                                    xtype: 'combobox',
                                    columnWidth: 0.25,
                                    padding: '0 0 0 25',
                                    fieldLabel: '薪资套账',
                                    labelWidth: 60
                                },
                                {
                                    xtype: 'datefield',
                                    columnWidth: 0.2,
                                    padding: '0 0 0 25',
                                    fieldLabel: '薪资月份',
                                    labelWidth: 60
                                },
                                {
                                    xtype: 'datefield',
                                    columnWidth: 0.22,
                                    padding: '0 0 0 25',
                                    fieldLabel: '社保扣费月',
                                    labelWidth: 80
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 0.07,
                                    height: 15
                                },
                                {
                                    xtype: 'button',
                                    text: '查询'
                                }
                            ]
                        }
                    ]
                }
            ],
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: '薪资主题'
                },
                {
                    xtype: 'gridcolumn',
                    text: '套账名称'
                },
                {
                    xtype: 'gridcolumn',
                    text: '状态'
                },
                {
                    xtype: 'gridcolumn',
                    text: '薪资月份'
                },
                {
                    xtype: 'gridcolumn',
                    text: '社保扣费月'
                },
                {
                    xtype: 'gridcolumn',
                    text: '发放人数'
                },
                {
                    xtype: 'gridcolumn',
                    text: '薪资总额'
                },
                {
                    xtype: 'gridcolumn',
                    text: '创建日期'
                },
                {
                    xtype: 'actioncolumn',
                    items: [
                        {
                            iconCls: 's_icon_table_edit'
                        }
                    ]
                },
                {
                    xtype: 'actioncolumn',
                    items: [
                        {
                            iconCls: 's_icon_action_search'
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

});