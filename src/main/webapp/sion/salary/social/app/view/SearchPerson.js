/*
 * File: app/view/SearchPerson.js
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

Ext.define('sion.salary.social.view.SearchPerson', {
    extend: 'Ext.window.Window',

    requires: [
        'Ext.grid.Panel',
        'Ext.grid.column.Column',
        'Ext.grid.View',
        'Ext.selection.CheckboxModel',
        'Ext.toolbar.Toolbar',
        'Ext.button.Button'
    ],

    height: 640,
    maxHeight: 640,
    minHeight: 640,
    width: 720,
    layout: 'fit',
    title: '人员薪资档案',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'gridpanel',
                    header: false,
                    title: 'My Grid Panel',
                    store: 'PersonAccountStore',
                    columns: [
                        {
                            xtype: 'gridcolumn',
                            width: '18%',
                            dataIndex: 'personCode',
                            text: '员工编号',
                            flex: 1
                        },
                        {
                            xtype: 'gridcolumn',
                            width: '18%',
                            dataIndex: 'name',
                            text: '员工姓名',
                            flex: 1
                        },
                        {
                            xtype: 'gridcolumn',
                            width: '20%',
                            dataIndex: 'dept',
                            text: '部门',
                            flex: 1
                        },
                        {
                            xtype: 'gridcolumn',
                            width: '18%',
                            dataIndex: 'duty',
                            text: '职务',
                            flex: 1
                        },
                        {
                            xtype: 'gridcolumn',
                            width: '20%',
                            dataIndex: 'idCard',
                            text: '身份证号',
                            flex: 2
                        }
                    ],
                    selModel: Ext.create('Ext.selection.CheckboxModel', {

                    }),
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'button',
                                    iconCls: 's_icon_table_save',
                                    text: '确定',
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
            listeners: {
                afterrender: {
                    fn: me.onWindowAfterRender,
                    scope: me
                }
            }
        });

        me.callParent(arguments);
    },

    onButtonClick: function(button, e, eOpts) {
        var me=this,
            grid=me.down('gridpanel');
        persons = grid.getSelectionModel().getSelection();

        // if(persons === null||persons.length===0){
        //     Ext.Msg.alert("提示","请至少选中表格中的一条记录!");
        //     return;
        // }
        if(this._callback){
            this._callback(persons,this._scope);
            me.close();
        }
    },

    onWindowAfterRender: function(component, eOpts) {
        var me=this,
            grid=me.down('gridpanel');
        grid.getStore().load();
    }

});