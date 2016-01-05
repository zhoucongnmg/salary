/*
 * File: app/view/SalaryItems.js
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

Ext.define('sion.salary.accounts.view.SalaryItems', {
    extend: 'Ext.panel.Panel',

    requires: [
        'sion.salary.accounts.view.SalaryItemGrid',
        'Ext.tab.Panel',
        'Ext.tab.Tab',
        'Ext.grid.Panel',
        'Ext.toolbar.Toolbar'
    ],

    height: 480,
    width: 640,
    title: '薪资项目管理',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'tabpanel',
                    hidden: true,
                    items: [
                        {
                            xtype: 'panel',
                            hidden: true,
                            layout: 'fit',
                            title: '系统提取项',
                            items: [
                                {
                                    xtype: 'salaryitemgrid'
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    itemId: 'mypanel',
                    layout: 'fit',
                    items: [
                        {
                            xtype: 'salaryitemgrid'
                        }
                    ],
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'button',
                                    iconCls: 's_icon_action_add',
                                    text: '<span style="font-size:14px;color:#3892D3;font-weight:bold;">新建</span>',
                                    listeners: {
                                        click: {
                                            fn: me.onNewSalaryItemClick,
                                            scope: me
                                        }
                                    }
                                }
                            ]
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    },

    onNewSalaryItemClick: function(button, e, eOpts) {
        var me =this,
            namespace=me.getNamespace();

        Ext.create(namespace+".view.SalaryItemEdit").show();
    }

});