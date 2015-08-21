/*
 * File: app/view/SocialAccountGrid.js
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

Ext.define('sion.salary.social.view.SocialAccountGrid', {
    extend: 'Ext.grid.Panel',

    requires: [
        'Ext.grid.View',
        'Ext.grid.column.Action'
    ],

    height: 515,
    width: 1006,

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: '套账名称',
                    flex: 0.4
                },
                {
                    xtype: 'gridcolumn',
                    text: '创建人',
                    flex: 0.2
                },
                {
                    xtype: 'gridcolumn',
                    text: '创建日期',
                    flex: 0.2
                },
                {
                    xtype: 'actioncolumn',
                    flex: 0.06,
                    items: [
                        {
                            iconCls: 's_icon_table_edit'
                        }
                    ]
                },
                {
                    xtype: 'actioncolumn',
                    flex: 0.06,
                    items: [
                        {
                            iconCls: 's_icon_action_search'
                        }
                    ]
                },
                {
                    xtype: 'actioncolumn',
                    flex: 0.06,
                    items: [
                        {
                            iconCls: 's_icon_action_action_delete'
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

});