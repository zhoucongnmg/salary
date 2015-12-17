/*
 * File: app/view/CalcButton.js
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

Ext.define('sion.salary.formula.view.CalcButton', {
    extend: 'Ext.button.Button',
    alias: 'widget.calcbutton',

    baseCls: 'formula-btn',
    margin: '5 5 5 5',
    width: 75,
    text: '1',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            listeners: {
                click: {
                    fn: me.onButtonClick,
                    scope: me
                }
            }
        });

        me.processCalcButton(me);
        me.callParent(arguments);
    },

    processCalcButton: function(config) {
        if (config._type) {
            config.baseCls = 'formula-'+ config._type +'btn';

        }

        return config;

    },

    onButtonClick: function(button, e, eOpts) {
        var me = this,
            ns = me.getNamespace(),
            ctrl = Ext.create(ns + '.controller.Display'),
            text = button.getText(),
            type = button._type;

        if (type=='Logical') {
            text = 'if (){\r\n'+
                    '}';
        }
        ctrl.addInputScreen(text);
    }

});