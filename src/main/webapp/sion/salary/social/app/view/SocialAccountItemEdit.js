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
    title: '社保项目',

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
                                    itemId: 'socialItemId',
                                    fieldLabel: '名称',
                                    labelWidth: 60,
                                    name: 'socialItemId',
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
                                    value: 0.00,
                                    allowBlank: false
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
                                    value: 0.00,
                                    allowBlank: false
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
                                    value: 0.00,
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
            store = me._itemStore,
            itemStore = me.down('#socialItemId').getStore();

        record = form.getRecord();
        form.updateRecord(record);
        if(!me.down('#socialItemId').isValid() || !me.down('#cardinality').isValid() ||
           !me.down('#companyPaymentType').isValid() || !me.down('#personalPaymentType').isValid() ||
           !me.down('#companyPaymentValue').isValid()|| !me.down('#personalPaymentValue').isValid()){
            Ext.Msg.alert("提示", "信息不完整，请继续填写！");
            return false;
        }
        var item = itemStore.findRecord('id', me.down('#socialItemId').getValue());
        // record.set('name', me.down('#socialItemId').getRawValue());
        // record.set('socialItemId', me.down('#socialItemId').getValue());
        record.set('name', item.get('name'));
        record.set('socialItemId', item.get('id'));
        record.set('itemType', item.get('itemType'));
        record.set('carryType', item.get('carryType'));
        record.set('precision', item.get('precision'));
        record.set('item', item.get('item'));

        if(record.get('companyPaymentType') == 'Percent'){
             record.set('companyPaymentValue',Number(Number(record.get('companyPaymentValue')) * 0.01).toFixed(4));
        //     record.set('companyPaymentValue',parseFloat(record.get('companyPaymentValue')) * 0.01);
        }
        if(record.get('personalPaymentType') == 'Percent'){
             record.set('personalPaymentValue',Number(Number(record.get('personalPaymentValue')) * 0.01).toFixed(4));
        //     record.set('personalPaymentValue',parseFloat(record.get('personalPaymentValue')) * 0.01);
        }

        if(record.get('id') === ''){
            record.set('id', id);
            store.add(record);
        }
        me.close();
    },

    onWindowBeforeRender: function(component, eOpts) {
        var me = this,
            namespace = me.getNamespace(),
            form = me.down("form"),
            item = me._socialAccountItem;


        Ext.override(Ext.form.NumberField, {
            setValue : function(v){
                v = typeof v == 'number' ? v : parseFloat(String(v).replace(this.decimalSeparator, "."));
                v = isNaN(v) ? '' : v.toFixed(this.decimalPrecision).replace(".", this.decimalSeparator);
                var f_x = Math.round(v*100)/100;
                var s_x = f_x.toString();
                var pos_decimal = s_x.indexOf('.');
                if (pos_decimal < 0)
                {
                    pos_decimal = s_x.length;
                    s_x += '.';
                }
                while (s_x.length <= pos_decimal + 2)
                {
                    s_x += '0';
                }


                return Ext.form.NumberField.superclass.setValue.call(this, s_x);
            }
        });

        if(item){
            form.loadRecord(item);
            if(item.get('companyPaymentType') == 'Percent'){
                me.down('#companyPaymentValue').setValue(parseFloat(item.get('companyPaymentValue'))  * 100 + '%');
            }
            if(item.get('personalPaymentType') == 'Percent'){
                me.down('#personalPaymentValue').setValue(parseFloat(item.get('personalPaymentValue'))  * 100 + '%');
            }
        }else{
            form.loadRecord(Ext.create(namespace + '.model.SocialAccountItem', {
                item: 'SocialItem',
                id: '',
                socialItemId: '',
                name: '',
                companyPaymentValue: 0.00,
                personalPaymentValue: 0.00,
                cardinality: 0.00,
                companyPaymentType: '',
                personalPaymentType: ''
            }));


            Ext.Array.each(me.down('numberfield'),function(component,index){
                component.setValue('0.00');
            });
        }
    }

});