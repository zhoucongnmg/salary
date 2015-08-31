/*
 * File: app/store/PersonStore.js
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

Ext.define('sion.salary.payroll.store.PersonStore', {
    extend: 'Ext.data.Store',

    requires: [
        'sion.salary.payroll.model.Person',
        'Ext.data.proxy.Ajax',
        'Ext.data.reader.Json'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'sion.salary.payroll.model.Person',
            storeId: 'PersonStore',
            data: [
                {
                    code: 'impedit',
                    name: 'quo',
                    dept: 'necessitatibus',
                    plan: 'eius',
                    level: 'eum',
                    rate: 'nihil',
                    status: true
                },
                {
                    code: 'officiis',
                    name: 'dolorem',
                    dept: 'reiciendis',
                    plan: 'officia',
                    level: 'repellendus',
                    rate: 'ut',
                    status: true
                },
                {
                    code: 'facere',
                    name: 'quia',
                    dept: 'quae',
                    plan: 'excepturi',
                    level: 'eum',
                    rate: 'vel',
                    status: true
                },
                {
                    code: 'molestiae',
                    name: 'modi',
                    dept: 'quae',
                    plan: 'itaque',
                    level: 'illum',
                    rate: 'iure',
                    status: true
                },
                {
                    code: 'assumenda',
                    name: 'non',
                    dept: 'et',
                    plan: 'sapiente',
                    level: 'nemo',
                    rate: 'qui',
                    status: false
                },
                {
                    code: 'veniam',
                    name: 'dolor',
                    dept: 'voluptas',
                    plan: 'iure',
                    level: 'est',
                    rate: 'harum',
                    status: false
                },
                {
                    code: 'delectus',
                    name: 'praesentium',
                    dept: 'ratione',
                    plan: 'voluptatem',
                    level: 'sed',
                    rate: 'atque',
                    status: false
                },
                {
                    code: 'pariatur',
                    name: 'deserunt',
                    dept: 'cum',
                    plan: 'quaerat',
                    level: 'ullam',
                    rate: 'est',
                    status: true
                },
                {
                    code: 'sint',
                    name: 'velit',
                    dept: 'non',
                    plan: 'inventore',
                    level: 'qui',
                    rate: 'tempore',
                    status: false
                },
                {
                    code: 'natus',
                    name: 'illo',
                    dept: 'autem',
                    plan: 'culpa',
                    level: 'sunt',
                    rate: 'quos',
                    status: true
                },
                {
                    code: 'reiciendis',
                    name: 'quidem',
                    dept: 'explicabo',
                    plan: 'repudiandae',
                    level: 'et',
                    rate: 'quo',
                    status: false
                },
                {
                    code: 'at',
                    name: 'at',
                    dept: 'dicta',
                    plan: 'consequatur',
                    level: 'voluptates',
                    rate: 'qui',
                    status: true
                },
                {
                    code: 'necessitatibus',
                    name: 'veniam',
                    dept: 'commodi',
                    plan: 'quaerat',
                    level: 'tempore',
                    rate: 'quasi',
                    status: true
                },
                {
                    code: 'sed',
                    name: 'sed',
                    dept: 'dolor',
                    plan: 'fuga',
                    level: 'tenetur',
                    rate: 'quia',
                    status: false
                },
                {
                    code: 'odit',
                    name: 'quidem',
                    dept: 'labore',
                    plan: 'voluptatum',
                    level: 'vel',
                    rate: 'tempore',
                    status: true
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