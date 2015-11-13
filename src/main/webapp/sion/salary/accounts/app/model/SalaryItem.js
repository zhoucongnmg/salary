/*
 * File: app/model/SalaryItem.js
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

Ext.define('sion.salary.accounts.model.SalaryItem', {
    extend: 'Ext.data.Model',

    requires: [
        'Ext.data.Field'
    ],

    fields: [
        {
            name: 'name'
        },
        {
            name: 'field'
        },
        {
            name: 'type'
        },
        {
            name: 'note'
        },
        {
            name: 'system',
            type: 'boolean'
        },
        {
            name: 'id'
        },
        {
            name: 'taxItem',
            type: 'boolean'
        },
        {
            name: 'decimalScale',
            type: 'int'
        },
        {
            name: 'show',
            type: 'boolean'
        },
        {
            name: 'money',
            type: 'float'
        }
    ]
});