/*
 * File: app/view/SocialAccountItemEdit.js
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

Ext.define('sion.salary.social.view.SocialAccountItemEdit', {
    extend: 'Ext.window.Window',

    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.button.Button',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Number'
    ],

    layout: 'fit',
    title: '项目',

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
                            style: 'background:#3ca9fc;',
                            width: 70,
                            text: '<font color=\'#fff\'>保存</font>',
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
                    style: 'margin-bottom:20px;',
                    bodyPadding: 10,
                    layout: {
                        type: 'vbox',
                        align: 'stretch'
                    },
                    items: [
                        {
                            xtype: 'panel',
                            layout: {
                                type: 'hbox',
                                align: 'stretch'
                            },
                            items: [
                                {
                                    xtype: 'combobox',
                                    flex: 1,
                                    itemId: 'socialItemName',
                                    fieldLabel: '名称',
                                    labelWidth: 60,
                                    name: 'socialItemName',
                                    allowBlank: false,
                                    editable: false,
                                    displayField: 'name',
                                    store: 'SocialItem',
                                    valueField: 'id'
                                },
                                {
                                    xtype: 'numberfield',
                                    flex: 1,
                                    itemId: 'cardinality',
                                    style: 'margin-left:10px;',
                                    fieldLabel: '基数',
                                    labelWidth: 60,
                                    name: 'cardinality',
                                    allowBlank: false,
                                    minValue: 0
                                }
                            ]
                        },
                        {
                            xtype: 'panel',
                            style: 'margin-top:20px;',
                            layout: {
                                type: 'hbox',
                                align: 'stretch'
                            },
                            items: [
                                {
                                    xtype: 'numberfield',
                                    flex: 1,
                                    itemId: 'companyPaymentValue',
                                    fieldLabel: '单位缴费',
                                    labelWidth: 60,
                                    name: 'companyPaymentValue',
                                    allowBlank: false,
                                    minValue: 0
                                },
                                {
                                    xtype: 'combobox',
                                    flex: 0.5,
                                    itemId: 'companyPaymentType',
                                    name: 'companyPaymentType',
                                    allowBlank: false,
                                    editable: false,
                                    displayField: 'name',
                                    store: 'PaymentType',
                                    valueField: 'id'
                                },
                                {
                                    xtype: 'numberfield',
                                    flex: 1,
                                    itemId: 'personalPaymentValue',
                                    style: 'margin-left:10px;',
                                    fieldLabel: '个人缴费',
                                    labelWidth: 60,
                                    name: 'personalPaymentValue',
                                    allowBlank: false,
                                    minValue: 0
                                },
                                {
                                    xtype: 'combobox',
                                    flex: 0.5,
                                    itemId: 'personalPaymentType',
                                    name: 'personalPaymentType',
                                    allowBlank: false,
                                    editable: false,
                                    displayField: 'name',
                                    store: 'PaymentType',
                                    valueField: 'id'
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
            uuid = Ext.create('Ext.data.UuidGenerator'),
            id = uuid.generate(),
            form = me.down("form"),
            store = me._itemStore;

        record = form.getRecord();
        form.updateRecord(record);
        if(!me.down('#socialItemName').isValid() || !me.down('#cardinality').isValid() ||
           !me.down('#companyPaymentType').isValid() || !me.down('#personalPaymentType').isValid() ||
           !me.down('#companyPaymentValue').isValid()|| !me.down('#personalPaymentValue').isValid()){
            Ext.Msg.alert("提示", "信息不完整，请继续填写！");
            return false;
        }
        record.set('socialItemName', me.down('#socialItemName').getRawValue());
        if(record.get('id') === ''){
            record.set('id', id);
            store.add(record);
        }
        me.close();
        // record.save({
        //     url: 'salary/socialitem/create',
        //     success: function(response, opts){
        //         Ext.Msg.alert("提示", "保存成功");
        //         //         var leaveStore = Ext.StoreManager.lookup("OverTimeApply");//更新数据从前台取
        //         store.load();
        //         me.close();
        //     },
        //     failure: kfunction(){
        //         Ext.Msg.alert("提示", "保存失败");
        //         me.close();
        //     }
        // });
    },

    onWindowBeforeRender: function(component, eOpts) {
        var me = this,
            namespace = me.getNamespace(),
            form = me.down("form"),
            item = me._socialAccountItem;

        if(item){
            form.loadRecord(item);
        }else{
            form.loadRecord(Ext.create(namespace + '.model.SocialAccountItem', {
                id: '',
                socialItemId: '',
                socialItemName: '',
                companyPaymentValue: 0,
                personalPaymentValue: 0,
                cardinality: 0,
                companyPaymentType: '',
                personalPaymentType: ''
            }));
        }
    }

});