/*
 * File: app/view/InsuredGrid.js
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

Ext.define('sion.salary.social.view.InsuredGrid', {
    extend: 'Ext.grid.Panel',

    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.toolbar.Spacer',
        'Ext.form.field.Date',
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
                    height: 137,
                    items: [
                        {
                            xtype: 'form',
                            height: 122,
                            width: 908,
                            layout: 'column',
                            bodyPadding: 10,
                            items: [
                                {
                                    xtype: 'combobox',
                                    columnWidth: 0.15,
                                    fieldLabel: '关键字',
                                    labelWidth: 60
                                },
                                {
                                    xtype: 'textfield',
                                    columnWidth: 0.3,
                                    margin: '',
                                    padding: '0 0 0 10'
                                },
                                {
                                    xtype: 'combobox',
                                    columnWidth: 0.5,
                                    padding: '0 0 0 100',
                                    fieldLabel: '社保套账',
                                    labelWidth: 60
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 1,
                                    height: 20
                                },
                                {
                                    xtype: 'datefield',
                                    columnWidth: 0.2,
                                    fieldLabel: '参保日期',
                                    labelWidth: 60
                                },
                                {
                                    xtype: 'datefield',
                                    columnWidth: 0.14,
                                    fieldLabel: ' 至 ',
                                    labelSeparator: ' ',
                                    labelWidth: 10
                                },
                                {
                                    xtype: 'combobox',
                                    columnWidth: 0.5,
                                    padding: '0 0 0 203',
                                    fieldLabel: '社保状态',
                                    labelWidth: 60
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 0.07,
                                    height: 15
                                },
                                {
                                    xtype: 'button',
                                    text: '查询'
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 1.07,
                                    height: 20
                                },
                                {
                                    xtype: 'button',
                                    text: '新建',
                                    listeners: {
                                        click: {
                                            fn: me.onButtonClick,
                                            scope: me
                                        }
                                    }
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
                    text: '参保人'
                },
                {
                    xtype: 'gridcolumn',
                    text: '身份证号'
                },
                {
                    xtype: 'gridcolumn',
                    text: '部门'
                },
                {
                    xtype: 'gridcolumn',
                    text: '职务'
                },
                {
                    xtype: 'gridcolumn',
                    text: '投保号'
                },
                {
                    xtype: 'gridcolumn',
                    text: '参保日期'
                },
                {
                    xtype: 'gridcolumn',
                    text: '户口性质'
                },
                {
                    xtype: 'gridcolumn',
                    text: '工作地'
                },
                {
                    xtype: 'gridcolumn',
                    text: '购买状态'
                },
                {
                    xtype: 'gridcolumn',
                    text: '社保套账'
                },
                {
                    xtype: 'gridcolumn',
                    text: '创建人'
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
    },

    onButtonClick: function(button, e, eOpts) {
        Ext.create('sion.salary.social.view.DocumentForm').show();
    }

});