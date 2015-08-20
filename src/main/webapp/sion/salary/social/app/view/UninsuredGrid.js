/*
 * File: app/view/UninsuredGrid.js
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

Ext.define('sion.salary.social.view.UninsuredGrid', {
    extend: 'Ext.grid.Panel',

    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
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
                            height: 42,
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
                                    columnWidth: 0.4,
                                    padding: '0 0 0 100',
                                    fieldLabel: '人员类别',
                                    labelWidth: 60
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 0.05,
                                    height: 20
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
                    text: '姓名'
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
                    text: '人员类别'
                },
                {
                    xtype: 'gridcolumn',
                    text: '岗位状态'
                },
                {
                    xtype: 'gridcolumn',
                    text: '参加工作时间'
                },
                {
                    xtype: 'gridcolumn',
                    text: '本单位工龄'
                },
                {
                    xtype: 'actioncolumn',
                    text: '投保',
                    iconCls: 's_icon_table_edit',
                    items: [
                        {

                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

});