/*
 * File: app/view/AddSalaryItem.js
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

Ext.define('sion.salary.accounts.view.AddSalaryItem', {
    extend: 'Ext.window.Window',

    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.button.Button',
        'Ext.form.Panel',
        'Ext.form.FieldSet',
        'Ext.form.field.Checkbox',
        'Ext.form.field.ComboBox',
        'Ext.grid.Panel',
        'Ext.grid.column.Column',
        'Ext.grid.View',
        'Ext.form.field.TextArea'
    ],

    height: 480,
    width: 860,
    title: '新增方案项目',

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
                            text: '确定',
                            listeners: {
                                click: {
                                    fn: me.onButtonClick,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'button',
                            text: '动态列',
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
            items: [
                {
                    xtype: 'form',
                    width: '100%',
                    bodyPadding: 10,
                    header: false,
                    layout: {
                        type: 'hbox',
                        align: 'stretch'
                    },
                    items: [
                        {
                            xtype: 'fieldset',
                            height: 381,
                            width: 300,
                            title: '项目',
                            items: [
                                {
                                    xtype: 'checkboxfield',
                                    anchor: '100%',
                                    hideEmptyLabel: false,
                                    name: 'show',
                                    boxLabel: '在薪资报表中显示'
                                },
                                {
                                    xtype: 'combobox',
                                    anchor: '100%',
                                    itemId: 'type',
                                    fieldLabel: '类型',
                                    name: 'type',
                                    store: [
                                        '输入项',
                                        '计算项',
                                        '系统提取项'
                                    ],
                                    listeners: {
                                        change: {
                                            fn: me.onTypeChange,
                                            scope: me
                                        }
                                    }
                                },
                                {
                                    xtype: 'gridpanel',
                                    header: false,
                                    store: 'SalaryItem',
                                    columns: [
                                        {
                                            xtype: 'gridcolumn',
                                            width: '100%',
                                            dataIndex: 'name',
                                            text: '请选择结果项'
                                        }
                                    ],
                                    listeners: {
                                        beforerender: {
                                            fn: me.onGridpanelBeforeRender,
                                            scope: me
                                        }
                                    }
                                }
                            ]
                        },
                        {
                            xtype: 'fieldset',
                            flex: 1,
                            margins: '0 10 0 20',
                            hidden: true,
                            itemId: 'inputPanel',
                            style: 'padding-top:10px;',
                            width: 100,
                            items: [
                                {
                                    xtype: 'textareafield',
                                    anchor: '100%',
                                    fieldLabel: '值',
                                    labelWidth: 20,
                                    name: 'value',
                                    rows: 20
                                }
                            ]
                        },
                        {
                            xtype: 'fieldset',
                            flex: 1,
                            margins: '0 10 0 20',
                            hidden: true,
                            itemId: 'formulaPanel',
                            width: 100,
                            title: '公式'
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
        var me = this,
            accountItem = me._accountItem,
            grid = me.down('gridpanel'),
            account = me._account,
            form = me.down('form'),
            record = form.getRecord(),
            store = Ext.getStore('AccountItem'),
            select = grid.getSelectionModel().getSelection();

        if(select.length < 1){
            Ext.Msg.alert('提示', '请选择待选项目');
            return false;
        }

        if(accountItem === null){
            if(recordIndex > -1){
                Ext.Msg.alert('提示', '该项目已经存在，不能重复录入');
                return false;
            }
        }else{
            if(recordIndex > -1 && recordIndex !== store.indexOf(record)){
                Ext.Msg.alert('提示', '该项目已经存在，不能重复录入');
                return false;
            }
        }

        var recordIndex = store.find('salaryItemId', select[0].get('id'));
        form.updateRecord(record);
        if(record.get('type') == '系统提取项'){
            record.set('value', '');
        }
        if(record.get('type') == '计算项'){
            record.set('formula', null);
            record.set('value', '');
        }else{
            record.set('formula', null);
        }
        record.set('salaryItemId', select[0].get('id'));
        record.set('name', select[0].get('name'));
        record.set('fieldName', select[0].get('field'));
        if(accountItem === null){
            store.add(record);
        }
        me.close();
    },

    onButtonClick1: function(button, e, eOpts) {
        var me = this,
            namespace = me.getNamespace();

        Ext.create(namespace+".view.testDynamicGrid", {

        }).show();
    },

    onTypeChange: function(field, newValue, oldValue, eOpts) {
        var me = this,
            store = Ext.getStore('SalaryItem'),
        //     newValue = field.getValue(),
            window = field.up("window");

        if('计算项' == newValue){
            me.down('#formulaPanel').show();
            me.down('#inputPanel').hide();
            me.getSalaryItem(newValue);
        }else if('输入项' == newValue){
            me.down('#formulaPanel').hide();
            me.down('#inputPanel').show();
            me.getSalaryItem(newValue);
        }else if('系统提取项' == newValue){
            me.down('#formulaPanel').hide();
            me.down('#inputPanel').hide();
            me.getSalaryItem(newValue);
        }else{
            store.removeAll();
        }
        if(newValue === "计算项"){
        //     window.width=860;
        }
        else{
        //     window.width=320;
        }
    },

    onGridpanelBeforeRender: function(component, eOpts) {
        // var store = component.getStore();

        // if(store.getCount() === 0){
        //     store.load();
        // }
    },

    onWindowAfterRender: function(component, eOpts) {
        var me = this,
            namespace = me.getNamespace(),
            form = me.down('form'),
            record = me._accountItem,
            store = Ext.getStore('AccountItem'),
            win = Ext.create("sion.salary.formula.view.Main");

        // me.down('#formulaPanel').add(win);
        var app = Ext.ClassManager.get('sion.salary.formula' + ".$application").create();
        var Api = app.getController('Api');
        var panel = Api.initFormula({
            _formulaId : 'AddSalaryItem',//窗口的ItemId
            _container :  me.down('#formulaPanel'),//需要将公式编辑器面板显示到哪一个Container中
            _store : store//计算项store(Model必须包含id,text等field)

        });

        if(record === null){
            record = Ext.create(namespace + '.model.AccountItem', {
                id : Ext.data.IdGenerator.get('uuid').generate()
            });
        }else{
        //     me.down('#type').setValue(record.get('type'));
            me.getSalaryItem(record.get('type'));
        }
        form.loadRecord(record);
    },

    getSalaryItem: function(type) {
        var me = this,
            grid = me.down('gridpanel'),
            store = grid.getStore(),
            accountItem = me._accountItem,
            system = false;

        if(type === '系统提取项'){
            system = true;
        }
        store.clearFilter(true);
        Ext.apply(store.proxy.extraParams, {
            system : system,
            type : type
        });
        store.load({
            scope: this,
            callback: function(records, operation, success) {
                var record = store.find('id', accountItem.get('salaryItemId'));
                grid.getSelectionModel().select(record);
            }
        });
    }

});