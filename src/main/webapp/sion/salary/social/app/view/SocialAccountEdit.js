/*
 * File: app/view/SocialAccountEdit.js
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

Ext.define('sion.salary.social.view.SocialAccountEdit', {
    extend: 'Ext.window.Window',

    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.button.Button',
        'Ext.form.FieldSet',
        'Ext.form.Panel',
        'Ext.form.field.Text',
        'Ext.toolbar.Spacer',
        'Ext.grid.Panel',
        'Ext.form.Label',
        'Ext.toolbar.Fill',
        'Ext.grid.column.Action',
        'Ext.grid.View'
    ],

    style: 'padding-bottom:40px;',
    width: 861,
    layout: 'column',
    bodyPadding: 10,

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
                            iconCls: 's_icon_table_save',
                            text: '保存信息',
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
                    xtype: 'fieldset',
                    columnWidth: 1,
                    title: '基本信息',
                    layout: {
                        type: 'vbox',
                        align: 'stretch'
                    },
                    items: [
                        {
                            xtype: 'form',
                            bodyPadding: 10,
                            items: [
                                {
                                    xtype: 'textfield',
                                    itemId: 'name',
                                    fieldLabel: '名称',
                                    labelWidth: 80,
                                    name: 'name',
                                    allowBlank: false
                                }
                            ]
                        },
                        {
                            xtype: 'tbspacer',
                            height: 20
                        }
                    ]
                },
                {
                    xtype: 'gridpanel',
                    columnWidth: 1,
                    itemId: 'itemGrid',
                    store: 'SocialAccountItem',
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'label',
                                    html: '<span style=\'font-weight:bold\'>社保项目</span>'
                                },
                                {
                                    xtype: 'tbfill'
                                },
                                {
                                    xtype: 'button',
                                    width: 70,
                                    iconCls: 's_icon_action_add',
                                    text: '新增',
                                    listeners: {
                                        click: {
                                            fn: me.onButtonClick1,
                                            scope: me
                                        }
                                    }
                                }
                            ]
                        }
                    ],
                    columns: [
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'name',
                            text: '项目名称',
                            flex: 4
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'companyCardinality',
                            text: '单位缴费基数',
                            flex: 2
                        },
                        {
                            xtype: 'gridcolumn',
                            renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                                if(record.get('companyPaymentType') == 'Percent'){
                                    var str = value + '';
                                    if(str.split(".").length > 1){
                                        return (Number(value)  * 100).toFixed(str.split(".")[1].length - 2 > 0 ? str.split(".")[1].length - 2 : 0) + '%';
                                    }
                                }
                                return value;
                            },
                            dataIndex: 'companyPaymentValue',
                            text: '单位缴费',
                            flex: 2
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'personalCardinality',
                            text: '个人缴费基数',
                            flex: 2
                        },
                        {
                            xtype: 'gridcolumn',
                            renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                                if(record.get('personalPaymentType') == 'Percent'){
                                    var str = value + '';
                                    if(str.split(".").length > 1){
                                        return (Number(value)  * 100).toFixed(str.split(".")[1].length - 2 > 0 ? str.split(".")[1].length - 2 : 0) + '%';
                                    }
                                }
                                return value;
                            },
                            dataIndex: 'personalPaymentValue',
                            text: '个人缴费',
                            flex: 2
                        },
                        {
                            xtype: 'actioncolumn',
                            hideable: false,
                            text: '修改',
                            flex: 0.5,
                            items: [
                                {
                                    handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                        this.up('window').detailItem(record);
                                    },
                                    iconCls: 's_icon_table_edit',
                                    tooltip: '修改'
                                }
                            ]
                        },
                        {
                            xtype: 'actioncolumn',
                            hideable: false,
                            text: '删除',
                            flex: 0.5,
                            items: [
                                {
                                    handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                        var me = this.up('window'),
                                            store = Ext.getStore('SocialAccountItem');

                                        Ext.Msg.confirm('提示', '确定要删除吗？', function(text){
                                            if (text == 'yes'){
                                                store.remove(record);
                                                me.sum();
                                            }
                                        });
                                    },
                                    iconCls: 's_icon_action_action_delete',
                                    tooltip: '删除'
                                }
                            ]
                        }
                    ],
                    listeners: {
                        render: {
                            fn: me.onGridpanelRender,
                            scope: me
                        },
                        itemdblclick: {
                            fn: me.onItemGridItemDblClick,
                            scope: me
                        }
                    }
                }
            ]
        });

        me.callParent(arguments);
    },

    onButtonClick: function(button, e, eOpts) {
        var me = this,
            namespace = me.getNamespace(),
            form = me.down("form"),
            store = Ext.getStore("SocialAccount"),
            itemGrid = me.down('#itemGrid'),
            itemList = [],
            itemStore = itemGrid.getStore();

        record = form.getRecord();
        form.updateRecord(record);
        if(!me.down('#name').isValid()){
            Ext.Msg.alert("提示", "信息不完整，请继续填写！");
            return false;
        }
        itemStore.each(function(item){
            itemList.push(item.data);
        });
        record.set('socialAccountItems', itemList);
        if(record.get('id') === ''){
            store.add(record);
        }
        store.sync({
            success: function(response, opts){
                Ext.Msg.alert("提示", "保存成功");
                store.load();
                me.close();
            },
            failure: function(){
                Ext.Msg.alert("提示", "保存失败");
                me.close();
            }
        });
        me.close();
    },

    onButtonClick1: function(button, e, eOpts) {
        this.detailItem(null);
    },

    onGridpanelRender: function(component, eOpts) {
        var me = this,
            namespace = me.getNamespace(),
            itemStore = Ext.getStore('SocialAccountItem'),
            form = me.down("form"),
            socialAccount = me._socialAccount;

        itemStore.removeAll();
        if(socialAccount){
            itemStore.add(socialAccount.get('socialAccountItems'));
            form.loadRecord(socialAccount);
        }else{
            form.loadRecord(Ext.create(namespace + '.model.SocialAccount', {
                id: '',
                name: ''
            }));
        }
    },

    onItemGridItemDblClick: function(dataview, record, item, index, e, eOpts) {
        this.detailItem(record);
    },

    detailItem: function(record) {
        var me = this,
            namespace = me.getNamespace(),
            itemGrid = me.down('#itemGrid'),
            store = itemGrid.getStore();

        var panel =  Ext.create(namespace + '.view.SocialAccountItemEdit',{
            //     _opener : me,
            _socialAccountItem : record,
            _itemStore : store
        });
        panel.show();
        // me.resetGridSelect(record);
    },

    sum: function() {
        // var me = this,
        //     socialItemStore = Ext.getStore('SocialItem'),
        //     namespace = me.getNamespace(),
        //     form = me.down("form"),
        //     record = form.getRecord(),
        //     itemGrid = me.down('#itemGrid'),
        //     accumulationSum = 0,
        //     socialSum = 0,
        //     store = itemGrid.getStore();
        // alert('sum');


        // store.each(function(item){
        //     console.log(item);
        //     alert(item.data.socialItemId);
        //     var socialItem = socialItemStore.findRecord('id', item.data.socialItemId);
        //     alert();
        //     if(socialItem.get('itemType') == 'SocialSecurity'){
        //         socialSum = 0;
        //     }else{
        //         accumulationSum = 0;
        //     }
        // //     itemList.push(item.data);
        // });
    }

});