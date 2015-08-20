/*
 * File: app/view/Hierarchy_win.js
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

Ext.define('sion.salary.level.view.Hierarchy_win', {
    extend: 'Ext.window.Window',

    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.button.Button',
        'Ext.form.Panel',
        'Ext.form.field.TextArea'
    ],

    height: 320,
    width: 480,
    title: '薪资层次',

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
                            text: '保存'
                        }
                    ]
                }
            ],
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
                            xtype: 'textareafield',
                            anchor: '100%',
                            fieldLabel: '薪资项目'
                        },
                        {
                            xtype: 'textareafield',
                            width: 450,
                            fieldLabel: '关联职位'
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

});