/*
 * File: app/view/LevelGrid.js
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

Ext.define('sion.salary.level.view.LevelGrid', {
    extend: 'Ext.panel.Panel',

    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.button.Button',
        'Ext.grid.Panel',
        'Ext.grid.column.Action',
        'Ext.grid.View'
    ],

    layout: 'fit',
    title: '薪资层次',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'top',
                    items: [
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
            ],
            items: [
                {
                    xtype: 'gridpanel',
                    header: false,
                    title: 'My Grid Panel',
                    store: 'LevelStore',
                    columns: [
                        {
                            xtype: 'gridcolumn',
                            width: '20%',
                            dataIndex: 'name',
                            text: '名称'
                        },
                        {
                            xtype: 'gridcolumn',
                            width: '60%',
                            dataIndex: 'levelItems',
                            text: '薪资项目'
                        },
                        {
                            xtype: 'actioncolumn',
                            width: 35,
                            items: [
                                {
                                    handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                        Ext.create("sion.salary.level.view.Level_win").show();
                                    },
                                    iconCls: 's_icon_page_edit',
                                    tooltip: '编辑'
                                }
                            ]
                        },
                        {
                            xtype: 'actioncolumn',
                            width: 35,
                            items: [
                                {
                                    iconCls: 's_icon_cross',
                                    tooltip: '删除'
                                }
                            ]
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    },

    onButtonClick: function(button, e, eOpts) {
        var me=this,
            namespace=me.getNamespace();
        Ext.create(namespace+".view.Level_win").show();
    }

});