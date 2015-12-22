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
            listeners: {
                afterrender: {
                    fn: me.onWindowAfterRender,
                    scope: me
                }
            },
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'top',
                    items: [
                        {
                            xtype: 'button',
                            width: 70,
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
                                    displayField: 'name',
                                    store: 'AccountItemType',
                                    valueField: 'id',
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
                                    ]
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
                            layout: 'fit',
                            title: '公式'
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    },

    onWindowAfterRender: function(component, eOpts) {
        var me = this,
            namespace = me.getNamespace(),
            form = me.down('form'),
            record = me._accountItem,
            store = Ext.getStore('AccountItem'),
            panel = me.down('#formulaPanel');

        // if(record !== null && record.get('type') == 'Calculate' && record.get('formulaId') !== ''){
        //     Ext.Ajax.request({
        //         url: 'salary/formula/read',
        //         method: 'get',
        //         async: false,    //不使用异步
        //         params: {
        //             id: record.get('formulaId')
        //         },
        //         success: function(response, opts){
        //             var data = Ext.JSON.decode(response.responseText);
        //             record.set('formula', data);
        //         },
        //         failure: function(response, opts) {
        //             Ext.Msg.alert('提示信息','数据请求错误，请稍候重新尝试获取数据……');
        //         }
        //     });
        // }
        var app = Ext.ClassManager.get('sion.salary.formula' + ".$application").create();
        var Api = app.getController('Api');
        Api.initFormula({
            _formulaId : 'AddSalaryItem',//窗口的ItemId
            _container :  panel,//需要将公式编辑器面板显示到哪一个Container中
            _data : store.data.items,//计算项store(Model必须包含id,text等field)
            _command : record !== null && record.get('formula') !== null ? record.get('formula').formula : ''
        });
        me._formulaApi = Api;
        if(record === null){
            record = Ext.create(namespace + '.model.AccountItem', {
                id : Ext.data.IdGenerator.get('uuid').generate()
            });
        }else{
            me.getSalaryItem(record.get('type'));
        }
        form.loadRecord(record);
    },

    onButtonClick: function(button, e, eOpts) {
        var me = this,
            accountItem = me._accountItem,
            grid = me.down('gridpanel'),
            account = me._account,
            form = me.down('form'),
            record = form.getRecord(),
            store = Ext.getStore('AccountItem'),
            formulaItemStore = Ext.getStore('FormulaItem'),
            select = grid.getSelectionModel().getSelection(),
            formulaApi = me._formulaApi,
            formulaItems = new Array();

        if(select.length < 1){
            Ext.Msg.alert('提示', '请选择结果项');
            return false;
        }
        var recordIndex = store.find('salaryItemId', select[0].data.id);
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
        form.updateRecord(record);
        if(record.get('type') == 'System'){
            record.set('value', '');
        }
        if(record.get('type') == 'Calculate'){
            var formulaDatas = formulaApi.getFormula();
            if(!formulaDatas){
                Ext.Msg.alert('', '公式未完成，不能保存');
                return false;
            }
            var formulaData = formulaDatas[formulaDatas.length - 1];
            var va = formulaApi.validateFormula(formulaData);
            var formulaFields = formulaApi.getFields(formulaData);
            Ext.Array.each(formulaFields, function(formulaField){
                var item = store.findRecord('id', formulaField.fieldId);
                var formulaItem = {
                    value : '',
                    fieldId : item.data.salaryItemId,
                    text : formulaField.text,
                    type : 'Calculate'
                };
                formulaItems.push(formulaItem);
            });
        //     var formulaResult = {
        //         value : '',
        //         fieldId : select[0].data.id,
        //         text : select[0].data.name,
        //         type : 'Result'
        //     };
        //     formulaItems.push(formulaResult);
            var formula = {
                items : formulaItems,
                resultFieldId : select[0].data.id,
                formula : formulaData
            };
            record.set('formula', formula);
            record.set('value', formulaData);
        }else{
            record.set('formula', null);
            record.set('formulaId', '');
        }
        record.set('salaryItemId', select[0].data.id);
        record.set('name', select[0].data.name);
        record.set('fieldName', select[0].data.field);
        if(accountItem === null){
            store.add(record);
        }
        me.close();
    },

    onTypeChange: function(field, newValue, oldValue, eOpts) {
        var me = this,
            store = Ext.getStore('SalaryItem'),
            window = field.up("window");

        if('Calculate' == newValue){
            me.down('#formulaPanel').show();
            me.down('#inputPanel').hide();
            me.getSalaryItem(newValue);
        }else if('Input' == newValue){
            me.down('#formulaPanel').hide();
            me.down('#inputPanel').show();
            me.getSalaryItem(newValue);
        }else if('System' == newValue){
            me.down('#formulaPanel').hide();
            me.down('#inputPanel').hide();
            me.getSalaryItem(newValue);
        }else{
            store.removeAll();
        }
    },

    getSalaryItem: function(type) {
        var me = this,
            grid = me.down('gridpanel'),
            store = grid.getStore(),
            accountItem = me._accountItem,
            system = false;

        if(type === 'System'){
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
                if(accountItem !== null){
                    var record = store.find('id', accountItem.get('salaryItemId'));
                    grid.getSelectionModel().select(record);
                }
            }
        });
    }

});