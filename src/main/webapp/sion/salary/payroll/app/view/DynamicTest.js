/*
 * File: app/view/DynamicTest.js
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

Ext.define('sion.salary.payroll.view.DynamicTest', {
    extend: 'Ext.window.Window',

    requires: [
        'Ext.button.Button',
        'Ext.form.field.Text'
    ],

    height: 250,
    width: 400,

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'button',
                    text: '新增',
                    listeners: {
                        click: {
                            fn: me.onButtonClick,
                            scope: me
                        }
                    }
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Label'
                }
            ]
        });

        me.callParent(arguments);
    },

    onButtonClick: function(button, e, eOpts) {
        var me = this,
            namespace = me.getNamespace();

        Ext.create(namespace+".view.DynamicGrid", {
            _accountId : '566a461a9929c0da95998785'
        }).show();
    }

});