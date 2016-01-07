/*
 * File: app/view/SalaryPlan.js
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

Ext.define('sion.salary.accounts.view.SalaryPlan', {
    extend: 'Ext.window.Window',

    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.button.Button',
        'Ext.form.Panel',
        'Ext.form.field.Checkbox',
        'Ext.form.field.TextArea',
        'Ext.grid.Panel',
        'Ext.grid.RowNumberer',
        'Ext.grid.column.Boolean',
        'Ext.grid.column.Action',
        'Ext.grid.View',
        'Ext.form.Label',
        'Ext.toolbar.Fill'
    ],

    height: 600,
    width: 800,
    title: '方案信息',

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
                            fieldLabel: '方案名称',
                            name: 'name',
                            allowBlank: false
                        },
                        {
                            xtype: 'checkboxfield',
                            anchor: '100%',
                            fieldLabel: '&nbsp;',
                            labelSeparator: ' ',
                            name: 'enableLevel',
                            boxLabel: '启用薪资体系'
                        },
                        {
                            xtype: 'textareafield',
                            anchor: '100%',
                            fieldLabel: '备注',
                            name: 'remark'
                        }
                    ]
                },
                {
                    xtype: 'gridpanel',
                    flex: 1,
                    height: 450,
                    itemId: 'itemGrid',
                    header: false,
                    title: '方案项目 ',
                    store: 'AccountItem',
                    columns: [
                        {
                            xtype: 'rownumberer',
                            text: '序号',
                            flex: 1
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'name',
                            text: '项目名称',
                            flex: 3
                        },
                        {
                            xtype: 'gridcolumn',
                            renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                                var store = Ext.getStore("AccountItemType"),
                                    rec = store.findRecord('id', value);

                                return rec.get("name");
                            },
                            dataIndex: 'type',
                            text: '项目类型',
                            flex: 2
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'value',
                            text: '值或计算公式',
                            flex: 6
                        },
                        {
                            xtype: 'booleancolumn',
                            dataIndex: 'show',
                            text: '是否显示',
                            flex: 2,
                            falseText: '否',
                            trueText: '是'
                        },
                        {
                            xtype: 'actioncolumn',
                            dataIndex: 'date',
                            hideable: false,
                            text: '操作',
                            flex: 2,
                            items: [
                                {
                                    handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                        var panel = this.up('panel').up('panel');

                                        panel.detail(record);
                                    },
                                    iconCls: 's_icon_page_edit',
                                    tooltip: '修改'
                                },
                                {
                                    handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                        // var store = Ext.getStore('AccountItem');
                                        var store = view.getStore();

                                        Ext.Msg.confirm('提示', '确定要删除吗？', function(text){
                                            if (text == 'yes'){
                                                store.remove(record);
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
                    },
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'label',
                                    html: '<span style=\'font-weight:bold\'>薪资项目</span>'
                                },
                                {
                                    xtype: 'tbfill'
                                },
                                {
                                    xtype: 'button',
                                    iconCls: 's_icon_action_add',
                                    text: '新增',
                                    listeners: {
                                        click: {
                                            fn: me.onAddSalaryItemClick,
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
            store = Ext.getStore("Account"),
            itemGrid = me.down('#itemGrid'),
            itemList = [],
            account = me._account;
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
        record.set('accountItems', itemList);
        record.set('persons', null);
        // if(record.get('id') === ''){
        //     store.add(record);
        // }
        if(account){
            Ext.Msg.confirm('提示', '是否更新工资条？', function(text){
                if(text == 'yes'){
                    record.set('updatePayroll', true);
                }else{
                    record.set('updatePayroll', false);
                }
                me.save(store);
            });
        }else{
            store.add(record);
            me.save(store);
        }

    },

    onItemGridItemDblClick: function(dataview, record, item, index, e, eOpts) {
        this.detail(record);
    },

    onAddSalaryItemClick: function(button, e, eOpts) {
        this.detail(null);
    },

    onWindowBeforeRender: function(component, eOpts) {
        var me = this,
            store = Ext.getStore('SalaryItem');

        store.clearFilter(true);
        Ext.apply(store.proxy.extraParams, {
            type : ''
        });
        // if(store.getCount() === 0){
            store.load({
                callback: function(records, operation, success) {
                    me.loadData(store);
                }
            });
        // }else{
        //     me.loadData(store);
        // }
    },

    detail: function(record) {
        var me = this,
            grid = me.down('grid'),
            namespace = me.getNamespace();
        //     accountItem = Ext.create(namespace + '.model.AccountItem');

        Ext.create(namespace+".view.AddSalaryItem", {
            //     _account : me._account,
            _accountItem : record,
            _store : grid.getStore()
        }).show();
    },

    getFormula: function(id) {
        // if(record !== null && record.get('type') == 'Calculate' && record.get('formulaId') !== ''){
            Ext.Ajax.request({
                url: 'salary/formula/read',
                method: 'get',
                async: false,    //不使用异步
                params: {
                    id: id
                },
                success: function(response, opts){
                    var data = Ext.JSON.decode(response.responseText);
                    alert(data);
                    console.log(data);
                    return data;
        //             record.set('formula', data);
                },
                failure: function(response, opts) {
                    Ext.Msg.alert('提示信息','数据请求错误，请稍候重新尝试获取数据……');
                }
            });
        // }
    },

    save: function(store) {
        var me = this;

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
    },

    loadData: function(store) {
        var me = this,
            namespace = me.getNamespace(),
            grid = me.down('grid'),
            itemStore = grid.getStore(),
            form = me.down("form"),
            account = me._account;

        itemStore.removeAll();
        if(account){
            Ext.Array.each(account.get('accountItems'), function(item){
                if(item !== null && item.type == 'Calculate' && item.formulaId !== ''){
                    Ext.Ajax.request({
                        url: 'salary/formula/read',
                        method: 'get',
                        async: false,    //不使用异步
                        params: {
                            id: item.formulaId
                        },
                        success: function(response, opts){
                            if(response.responseText !== ''){
                                var data = Ext.JSON.decode(response.responseText);
                                item.formula = data;
                            }
                        },
                        failure: function(response, opts) {
                            Ext.Msg.alert('提示信息','数据请求错误，请稍候重新尝试获取数据……');
                        }
                    });
                }else{
                    item.formula = null;
                }
                var salaryItem = store.findRecord('id', item.salaryItemId);
                item.name = salaryItem.get('name');
                itemStore.add(item);
            });
            form.loadRecord(account);
        }else{
            form.loadRecord(Ext.create(namespace + '.model.Account', {
                id: '',
                name: ''
            }));
        }
    }

});