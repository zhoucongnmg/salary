/*
 * File: app/view/ExportWin.js
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

Ext.define('sion.salary.payroll.view.ExportWin', {
    extend: 'Ext.window.Window',

    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.toolbar.Fill',
        'Ext.button.Button',
        'Ext.form.field.TextArea'
    ],

    height: 350,
    width: 500,
    layout: 'fit',
    title: '请填写导出备注',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'top',
                    ui: 'footer',
                    items: [
                        {
                            xtype: 'tbfill'
                        },
                        {
                            xtype: 'button',
                            scale: 'medium',
                            text: '导出',
                            listeners: {
                                click: {
                                    fn: me.onButtonClick,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'button',
                            scale: 'medium',
                            text: '取消',
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
                    xtype: 'textareafield',
                    itemId: 'note',
                    margin: '5 5 5 5',
                    style: 'margin-right:10px;\nmargin-bottom:10px;\nmargin-left:10px;',
                    labelWidth: 30,
                    emptyText: '请输入备注信息',
                    maxLength: 100
                }
            ]
        });

        me.callParent(arguments);
    },

    onButtonClick: function(button, e, eOpts) {
        var me = this,
            note = me.down('#note').getValue(),
            id = me._id,
            opts = me._opts;

        if(me.down('#note').validate()){
            note = encodeURI(encodeURI(note));
            Ext.Ajax.request({
                url:'salary/payroll/saveExcelTemp',
                method : 'POST',
                jsonData : {
                    opts : opts
                },
                success: function(response){
                    var json = Ext.JSON.decode(response.responseText);
                    window.location.href = 'salary/payroll/exportItemList?id=' + id + '&optsId=' +
                        json.message + '&note=' + note;
                },
                failure: function(){
                }
            });
        }else{
            Ext.Msg.alert('提示', '您输入的备注信息过长，请重新输入！');
        }
    },

    onButtonClick1: function(button, e, eOpts) {
        this.close();
    }

});