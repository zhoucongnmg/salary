/*
 * File: app/store/LevelStore.js
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

Ext.define('sion.salary.level.store.LevelStore', {
    extend: 'Ext.data.Store',

    requires: [
        'sion.salary.level.model.Level',
        'Ext.data.proxy.Ajax',
        'Ext.data.reader.Json'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'sion.salary.level.model.Level',
            storeId: 'LevelStore',
            data: [
                {
                    name: 'sit',
                    levelItems: 'optio'
                },
                {
                    name: 'totam',
                    levelItems: 'sed'
                },
                {
                    name: 'numquam',
                    levelItems: 'aut'
                },
                {
                    name: 'illo',
                    levelItems: 'non'
                },
                {
                    name: 'est',
                    levelItems: 'occaecati'
                },
                {
                    name: 'placeat',
                    levelItems: 'magni'
                },
                {
                    name: 'maiores',
                    levelItems: 'voluptatum'
                },
                {
                    name: 'saepe',
                    levelItems: 'saepe'
                },
                {
                    name: 'dolorum',
                    levelItems: 'et'
                },
                {
                    name: 'eaque',
                    levelItems: 'commodi'
                },
                {
                    name: 'sunt',
                    levelItems: 'eius'
                },
                {
                    name: 'distinctio',
                    levelItems: 'aut'
                },
                {
                    name: 'assumenda',
                    levelItems: 'quia'
                },
                {
                    name: 'natus',
                    levelItems: 'nam'
                },
                {
                    name: 'amet',
                    levelItems: 'aspernatur'
                }
            ],
            proxy: {
                type: 'ajax',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});