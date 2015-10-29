/*
 * File: app/view/SocialItemForm.js
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

Ext.define('sion.salary.social.view.SocialItemForm', {
    extend: 'Ext.window.Window',

    requires: [
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Number',
        'Ext.form.RadioGroup',
        'Ext.form.field.Radio',
        'Ext.form.field.Display',
        'Ext.button.Button'
    ],

    height: 274,
    width: 481,
    bodyPadding: 10,

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    bodyPadding: 10,
                    items: [
                        {
                            xtype: 'textfield',
                            itemId: 'name',
                            fieldLabel: '项目名称',
                            name: 'name',
                            allowBlank: false
                        },
                        {
                            xtype: 'combobox',
                            itemId: 'itemType',
                            fieldLabel: '类型',
                            name: 'itemType',
                            allowBlank: false,
                            editable: false,
                            displayField: 'name',
                            store: 'SocialItemType',
                            valueField: 'id'
                        },
                        {
                            xtype: 'numberfield',
                            itemId: 'precision',
                            fieldLabel: '小数位数',
                            name: 'precision',
                            allowBlank: false,
                            allowDecimals: false,
                            minValue: 0
                        },
                        {
                            xtype: 'radiogroup',
                            itemId: 'carryType',
                            fieldLabel: '小数保留方式',
                            allowBlank: false,
                            items: [
                                {
                                    xtype: 'radiofield',
                                    name: 'carryType',
                                    boxLabel: '四舍五入',
                                    inputValue: 'Round'
                                },
                                {
                                    xtype: 'radiofield',
                                    name: 'carryType',
                                    boxLabel: '直接进位',
                                    inputValue: 'Isopsephy'
                                },
                                {
                                    xtype: 'radiofield',
                                    name: 'carryType',
                                    boxLabel: '直接舍去',
                                    inputValue: 'Truncation'
                                }
                            ]
                        },
                        {
                            xtype: 'displayfield',
                            fieldLabel: '温馨提示',
                            labelStyle: 'color:red;',
                            value: '小数保留方式默认为四舍五入方式；如需见分进角，则小数位数选1，保留方式选择直接进位即可。 '
                        },
                        {
                            xtype: 'button',
                            width: 70,
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
            store = Ext.StoreManager.lookup("SocialItem");

        record = form.getRecord();
        form.updateRecord(record);
        if(!me.down('#name').isValid() || !me.down('#itemType').isValid() || !me.down('#carryType').isValid()|| !me.down('#precision').isValid()){
            Ext.Msg.alert("提示", "信息不完整，请继续填写！");
            return false;
        }
        record.save({
            url: 'salary/socialitem/create',
            success: function(response, opts){
                Ext.Msg.alert("提示", "保存成功");
                //         var leaveStore = Ext.StoreManager.lookup("OverTimeApply");//更新数据从前台取
                store.load();
                me.close();
            },
            failure: function(){
                Ext.Msg.alert("提示", "保存失败");
                me.close();
            }
        });
    },

    onWindowBeforeRender: function(component, eOpts) {
        var me = this,
            namespace = me.getNamespace(),
            form = me.down("form"),
            socialItem = me._socialItem;

        if(socialItem){
            form.loadRecord(socialItem);
        }else{
            form.loadRecord(Ext.create(namespace + '.model.SocialItem', {
                name: '',
                itemType: '',
                carryType: '',
                precision: ''
            }));
        }
    }

});