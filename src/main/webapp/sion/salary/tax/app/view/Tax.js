/*
 * File: app/view/Tax.js
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

Ext.define('sion.salary.tax.view.Tax', {
    extend: 'Ext.window.Window',

    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.button.Button',
        'Ext.form.Panel',
        'Ext.form.field.Number',
        'Ext.grid.Panel',
        'Ext.grid.column.Number',
        'Ext.grid.column.Action',
        'Ext.grid.View'
    ],

    height: 480,
    width: 600,
    title: '税率设置',

    layout: {
        type: 'vbox',
        align: 'stretch'
    },

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
                            width: 70,
                            iconCls: 's_icon_table_save',
                            text: '保存',
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
                    xtype: 'form',
                    bodyPadding: 10,
                    header: false,
                    items: [
                        {
                            xtype: 'textfield',
                            anchor: '100%',
                            itemId: 'name',
                            fieldLabel: '名称',
                            name: 'name',
                            allowBlank: false
                        },
                        {
                            xtype: 'numberfield',
                            anchor: '100%',
                            itemId: 'threshold',
                            fieldLabel: '起征点',
                            name: 'threshold',
                            allowBlank: false,
                            minValue: 0
                        }
                    ],
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'bottom',
                            items: [
                                {
                                    xtype: 'button',
                                    width: 70,
                                    iconCls: 's_icon_action_add',
                                    text: '增加',
                                    listeners: {
                                        click: {
                                            fn: me.onButtonClick1,
                                            scope: me
                                        }
                                    }
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'gridpanel',
                    flex: 1,
                    itemId: 'itemGrid',
                    header: false,
                    store: 'TaxItem',
                    columns: [
                        {
                            xtype: 'numbercolumn',
                            dataIndex: 'start',
                            text: '区段开始值',
                            flex: 2
                        },
                        {
                            xtype: 'numbercolumn',
                            dataIndex: 'end',
                            text: '区段结束值',
                            flex: 2
                        },
                        {
                            xtype: 'gridcolumn',
                            renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                                var str = value + '';
                                if(str.split(".").length > 1){
                                    return (Number(value)  * 100).toFixed(str.split(".")[1].length - 2 > 0 ? str.split(".")[1].length - 2 : 0);
                                }
                                return value;
                            },
                            dataIndex: 'rate',
                            text: '税率(%)',
                            flex: 2
                        },
                        {
                            xtype: 'numbercolumn',
                            dataIndex: 'fastNumber',
                            text: '速算扣除数',
                            flex: 2
                        },
                        {
                            xtype: 'actioncolumn',
                            hideable: false,
                            text: '修改',
                            tooltip: '修改',
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
                            tooltip: '删除',
                            flex: 0.5,
                            items: [
                                {
                                    handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                        var store = Ext.getStore('TaxItem');

                                        Ext.Msg.confirm('提示', '确定要删除吗？', function(text){
                                            if (text == 'yes'){
                                                store.remove(record);
                                                if(store.getCount() > 0){
                                                    store.getAt(0).set('fastNumber', 0);
                                                    for(var i = 1; i < store.getCount(); i++){
                                                        var prev = store.getAt(i - 1);
                                                        var current = store.getAt(i);
                                                        var fastNumber = me.countFastNumber(prev.get('end'), prev.get('rate'), prev.get('fastNumber'),parseFloat(current.get('rate'))  * 100);
                                                        current.set('fastNumber', fastNumber);
                                                    }
                                                }
                                            }
                                        });
                                    },
                                    iconCls: 's_icon_cross',
                                    tooltip: '删除'
                                }
                            ]
                        }
                    ],
                    listeners: {
                        itemdblclick: {
                            fn: me.onItemGridItemDblClick,
                            scope: me
                        }
                    }
                }
            ],
            listeners: {
                beforerender: {
                    fn: me.onWindowBeforeRender,
                    scope: me
                }
            }
        });

        me.callParent(arguments);
    },

    onButtonClick: function(button, e, eOpts) {
        var me = this,
            namespace = me.getNamespace(),
            form = me.down("form"),
            store = Ext.getStore("Tax"),
            itemGrid = me.down('#itemGrid'),
            itemList = [],
            itemStore = itemGrid.getStore();

        record = form.getRecord();
        form.updateRecord(record);
        if(!me.down('#name').isValid() || !me.down('#threshold').isValid()){
            Ext.Msg.alert("提示", "信息不完整，请继续填写！");
            return false;
        }
        itemStore.each(function(item){
            itemList.push(item.data);
        });
        record.set('taxItems', itemList);
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
        var me = this,
            namespace = me.getNamespace(),
            threshold = me.down('#threshold').getValue(),
            itemGrid = me.down('#itemGrid'),
            store = itemGrid.getStore();

        // if(threshold === 0){
        //     Ext.Msg.alert('', '请输入起征点');
        //     return false;
        // }
        panel =  Ext.create(namespace + '.view.TaxItem',{
            _threshold : 0,
            _itemStore : store
        });
        panel.show();
    },

    onItemGridItemDblClick: function(dataview, record, item, index, e, eOpts) {
        this.detailItem(record);
    },

    onWindowBeforeRender: function(component, eOpts) {
        var me = this,
            namespace = me.getNamespace(),
            itemStore = Ext.getStore('TaxItem'),
            form = me.down("form"),
            tax = me._tax;

        itemStore.removeAll();
        if(tax){
            form.loadRecord(tax);
            itemStore.add(tax.get('taxItems'));
        }else{
            form.loadRecord(Ext.create(namespace + '.model.Tax', {
                id: '',
                name: '',
                threshold: 0
            }));
        }
    },

    detailItem: function(record) {
        var me = this,
            namespace = me.getNamespace(),
            itemGrid = me.down('#itemGrid'),
            store = itemGrid.getStore();

        var panel =  Ext.create(namespace + '.view.TaxItem',{
            _taxItem : record,
            _itemStore : store
        });
        panel.show();
    },

    countFastNumber: function(prevEnd, prevRate, prevFastNumber, rate) {
        // var value = 0;

        // value = prevEnd * (rate - prevRate) ;
        // value = (value * 0.01).toFixed(2);
        // value = parseFloat(value) + parseFloat(prevFastNumber);
        // return value;

        var value = 0;

        value = prevEnd * ((rate * 0.01).toFixed(2) - prevRate);
        value = parseFloat(value) + parseFloat(prevFastNumber);
        return value;
    }

});