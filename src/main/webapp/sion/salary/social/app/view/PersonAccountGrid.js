/*
 * File: app/view/PersonAccountGrid.js
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

Ext.define('sion.salary.social.view.PersonAccountGrid', {
    extend: 'Ext.grid.Panel',

    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Date',
        'Ext.button.Button',
        'Ext.toolbar.Spacer',
        'Ext.grid.column.Column',
        'Ext.grid.View'
    ],

    height: 515,
    width: 1006,
    store: 'PersonAccountStore',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'top',
                    height: 90,
                    items: [
                        {
                            xtype: 'form',
                            height: 122,
                            width: 908,
                            layout: 'column',
                            bodyPadding: 10,
                            items: [
                                {
                                    xtype: 'combobox',
                                    columnWidth: 0.25,
                                    fieldLabel: '社保套账',
                                    labelWidth: 60
                                },
                                {
                                    xtype: 'combobox',
                                    columnWidth: 0.2,
                                    margin: '0 0 0 10',
                                    fieldLabel: '社保状态',
                                    labelWidth: 60,
                                    store: [
                                        [
                                            'In',
                                            '在保'
                                        ],
                                        [
                                            'Out',
                                            '退保'
                                        ]
                                    ]
                                },
                                {
                                    xtype: 'datefield',
                                    columnWidth: 0.248,
                                    margin: '0 0 0 10',
                                    fieldLabel: '参保日期',
                                    labelWidth: 60,
                                    format: 'Y-m-d'
                                },
                                {
                                    xtype: 'datefield',
                                    columnWidth: 0.18,
                                    margin: '0 0 0 10',
                                    fieldLabel: ' 至 ',
                                    labelSeparator: ' ',
                                    labelWidth: 10,
                                    format: 'Y-m-d'
                                },
                                {
                                    xtype: 'button',
                                    margin: '0 0 0 10',
                                    text: '查询'
                                },
                                {
                                    xtype: 'button',
                                    margin: '0 0 0 10',
                                    text: '重置'
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 1.2,
                                    height: 20
                                },
                                {
                                    xtype: 'button',
                                    text: '新建',
                                    listeners: {
                                        click: {
                                            fn: me.onButtonClick,
                                            scope: me
                                        }
                                    }
                                }
                            ]
                        }
                    ]
                }
            ],
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'personCode',
                    text: '员工编号'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'name',
                    text: '员工姓名'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'dept',
                    text: '部门'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'duty',
                    text: '职务'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'idCard',
                    text: '身份证号'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'accountId',
                    text: '薪资方案'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'level',
                    text: '薪资层次'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'rank',
                    text: '薪资级别'
                },
                {
                    xtype: 'gridcolumn',
                    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                        return value.accountId;
                    },
                    dataIndex: 'insuredPerson',
                    text: '社保方案'
                },
                {
                    xtype: 'gridcolumn',
                    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                        if(value.status==="In")
                        return '在保';
                        else if(value.status==="Out")
                        return '退保';
                    },
                    dataIndex: 'insuredPerson',
                    text: '社保状态'
                },
                {
                    xtype: 'gridcolumn',
                    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                        return value.socialWorkplace;
                    },
                    dataIndex: 'insuredPerson',
                    text: '社保代付地'
                },
                {
                    xtype: 'gridcolumn',
                    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                        return value.accumulationFundsWorkplace;
                    },
                    dataIndex: 'insuredPerson',
                    text: '公积金代付地'
                }
            ],
            listeners: {
                afterrender: {
                    fn: me.onGridpanelAfterRender,
                    scope: me
                },
                itemdblclick: {
                    fn: me.onGridpanelItemDblClick,
                    scope: me
                }
            }
        });

        me.callParent(arguments);
    },

    onButtonClick: function(button, e, eOpts) {
        var me=this,
            namespace=me.getNamespace();
        Ext.create(namespace+'.view.PersonAccountForm',{_grid:me}).show();
    },

    onGridpanelAfterRender: function(component, eOpts) {
        component.getStore().load();
    },

    onGridpanelItemDblClick: function(dataview, record, item, index, e, eOpts) {
        var me=this,
                    namespace=me.getNamespace();
                Ext.create(namespace+'.view.PersonAccountForm',{_grid:me,_record:record}).show();
    }

});