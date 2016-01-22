/*
 * File: app/store/Account.js
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

Ext.define('sion.salary.accounts.store.Account', {
    extend: 'Ext.data.Store',

    requires: [
        'sion.salary.accounts.model.Account',
        'Ext.data.proxy.Ajax',
        'Ext.data.reader.Json'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: false,
            model: 'sion.salary.accounts.model.Account',
            remoteFilter: true,
            remoteSort: true,
            storeId: 'Account',
            pageSize: 50,
            proxy: {
                type: 'ajax',
                api: {
                    create: 'salary/account/create',
                    read: 'salary/account/load',
                    update: 'salary/account/create',
                    destroy: 'salary/account/remove'
                },
                reader: {
                    type: 'json',
                    root: 'data'
                }
            }
        }, cfg)]);
    }
});