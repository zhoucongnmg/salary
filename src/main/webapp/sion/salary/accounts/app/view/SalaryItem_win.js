/*
 * File: app/view/SalaryItem_win.js
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

Ext.define('sion.salary.accounts.view.SalaryItem_win', {
    extend: 'Ext.window.Window',

    requires: [
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Checkbox',
        'Ext.form.field.Number',
        'Ext.form.field.TextArea',
        'Ext.toolbar.Toolbar',
        'Ext.button.Button'
    ],

    height: 300,
    width: 480,
    title: '薪资项目',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    bodyPadding: 10,
                    header: false,
                    title: 'My Form',
                    items: [
                        {
                            xtype: 'textfield',
                            anchor: '100%',
                            fieldLabel: '名称'
                        },
                        {
                            xtype: 'combobox',
                            anchor: '100%',
                            fieldLabel: '类型',
                            store: [
                                '输入项',
                                '计算项',
                                '系统提取项'
                            ]
                        },
                        {
                            xtype: 'checkboxfield',
                            anchor: '100%',
                            hideEmptyLabel: false,
                            boxLabel: '个人所得税项目'
                        },
                        {
                            xtype: 'numberfield',
                            anchor: '100%',
                            fieldLabel: '小数位数'
                        },
                        {
                            xtype: 'textareafield',
                            anchor: '100%',
                            fieldLabel: '备注'
                        }
                    ]
                }
            ],
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'top',
                    items: [
                        {
                            xtype: 'button',
                            text: '保存'
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

});