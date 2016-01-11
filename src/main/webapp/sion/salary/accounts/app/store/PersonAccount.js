/*
 * File: app/store/PersonAccount.js
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

Ext.define('sion.salary.accounts.store.PersonAccount', {
    extend: 'Ext.data.Store',

    requires: [
        'sion.salary.accounts.model.PersonAccount',
        'Ext.data.proxy.Ajax',
        'Ext.data.writer.Json',
        'Ext.data.reader.Json'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'sion.salary.accounts.model.PersonAccount',
            storeId: 'PersonAccount',
            proxy: {
                type: 'ajax',
                api: {
                    create: 'salary/person/saveList',
                    read: 'salary/person/getPersonByAccountId',
                    update: 'salary/person/updateList',
                    destroy: 'salary/person/removeList'
                },
                writer: {
                    type: 'json',
                    allowSingle: false
                },
                reader: {
                    type: 'json',
                    root: 'data'
                }
            }
        }, cfg)]);
    }
});