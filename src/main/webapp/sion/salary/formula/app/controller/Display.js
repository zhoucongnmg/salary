/*
 * File: app/controller/Display.js
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

Ext.define('sion.salary.formula.controller.Display', {
    extend: 'Ext.app.Controller',

    refs: [
        {
            ref: 'Screen',
            selector: '#Calc_Screen'
        },
        {
            ref: 'ItemSelection',
            selector: '#Calc_ItemSelection'
        }
    ],

    addInputScreen: function(id, value) {

        var me = this,
            term = $('#'+id).terminal();
        term.insert(value);
        term.focus(true);
    },

    isItemIn: function(commandStr, pos) {
        var prefix = commandStr.substring(0,pos),
            suffix = commandStr.substring(pos,commandStr.length);

        return prefix.indexOf('[')!=-1&&suffix.indexOf(']')!=-1;
    }

});
