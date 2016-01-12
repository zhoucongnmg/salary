/*
 * File: app/view/PayrollWindow.js
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

Ext.define('sion.salary.payroll.view.PayrollWindow', {
    extend: 'Ext.window.Window',

    requires: [
        'Ext.form.Panel',
        'Ext.toolbar.Toolbar',
        'Ext.button.Button',
        'Ext.form.FieldSet',
        'Ext.toolbar.Spacer',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Hidden',
        'Ext.tree.Panel',
        'Ext.tree.Column'
    ],

    height: 430,
    width: 642,
    title: 'My Window',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    itemId: 'PayrollForm',
                    layout: 'fit',
                    bodyPadding: 10,
                    title: '',
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'button',
                                    iconCls: 's_icon_page_add',
                                    text: '保存薪资信息',
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
                            height: 100,
                            width: 605,
                            layout: 'column',
                            title: '薪资信息',
                            items: [
                                {
                                    xtype: 'textfield',
                                    columnWidth: 0.35,
                                    fieldLabel: '薪资主题',
                                    labelWidth: 80,
                                    name: 'subject',
                                    allowBlank: false,
                                    blankText: '薪资主题不能为空',
                                    validateBlank: true
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 0.2,
                                    height: 20
                                },
                                me.processMonth({
                                    xtype: 'triggerfield',
                                    columnWidth: 0.35,
                                    fieldLabel: '薪资月份',
                                    name: 'month',
                                    allowBlank: false,
                                    blankText: '薪资月份不能为空',
                                    validateBlank: true
                                }),
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 1,
                                    height: 20
                                },
                                {
                                    xtype: 'combobox',
                                    columnWidth: 0.35,
                                    fieldLabel: '薪资方案',
                                    labelWidth: 80,
                                    name: 'accountId',
                                    allowBlank: false,
                                    blankText: '薪资方案不能为空',
                                    emptyText: '--请选择--',
                                    validateBlank: true,
                                    editable: false,
                                    displayField: 'name',
                                    store: 'AccountStore',
                                    valueField: 'id',
                                    listeners: {
                                        select: {
                                            fn: me.onComboboxSelect,
                                            scope: me
                                        }
                                    }
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 0.2,
                                    height: 20
                                },
                                me.processSocialCostMonth({
                                    xtype: 'triggerfield',
                                    columnWidth: 0.35,
                                    fieldLabel: '社保扣费月',
                                    name: 'socialCostMonth',
                                    allowBlank: false,
                                    blankText: '社保月份不能为空',
                                    validateBlank: true
                                }),
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 1,
                                    height: 20
                                },
                                {
                                    xtype: 'hiddenfield',
                                    fieldLabel: 'Label',
                                    name: 'date'
                                },
                                {
                                    xtype: 'hiddenfield',
                                    fieldLabel: 'Label',
                                    name: 'createPersonName'
                                },
                                {
                                    xtype: 'hiddenfield',
                                    fieldLabel: 'Label',
                                    name: 'id'
                                },
                                {
                                    xtype: 'hiddenfield',
                                    fieldLabel: 'Label',
                                    name: 'state'
                                },
                                {
                                    xtype: 'hiddenfield',
                                    fieldLabel: 'Label',
                                    name: 'persons'
                                }
                            ]
                        },
                        {
                            xtype: 'treepanel',
                            height: 300,
                            store: 'PersonStore',
                            rootVisible: false,
                            useArrows: true,
                            columns: [
                                {
                                    xtype: 'treecolumn',
                                    dataIndex: 'name',
                                    text: '人员信息',
                                    flex: 1
                                }
                            ],
                            listeners: {
                                checkchange: {
                                    fn: me.onTreepanelCheckChange,
                                    scope: me
                                }
                            }
                        }
                    ],
                    listeners: {
                        afterrender: {
                            fn: me.onPayrollFormAfterRender,
                            scope: me
                        }
                    }
                }
            ],
            listeners: {
                beforeclose: {
                    fn: me.onWindowBeforeClose,
                    scope: me
                }
            }
        });

        me.callParent(arguments);
    },

    processMonth: function(config) {
        config.xtype=　'monthfield';
        config.hiddenName = 'date';
        config.format=　"Y-m";
        return config;

    },

    processSocialCostMonth: function(config) {
        config.xtype=　'monthfield';
        config.hiddenName = 'date';
        config.format=　"Y-m";
        return config;

    },

    onButtonClick: function(button, e, eOpts) {
        var me = this,
            state = me._link.state,
            form = me.down('form').getForm(),
            record = form.getRecord(),
            comboBox = me.down('combobox'),
            itemTree = me.down('treepanel'),
            selectedRows = itemTree.getChecked(),
            itemMap = {},
            itemStore = itemTree.getStore();


        if(!form.isValid()){
            Ext.Msg.alert("提示", "信息不完整，请继续填写！");
            return false;
        }

        if(!selectedRows[0]){
            Ext.Msg.alert("提示", "请选择人员！");
            return false;
        }

        form.updateRecord();

        Ext.each(selectedRows, function (item) {
            if(item.isLeaf()){
                itemMap[item.get('id')] = item.get('name');
            }
        });

        record.set('persons',itemMap);
        record.set('accountName',comboBox.getRawValue());

        record.save({
            success: function(response, opts){
                record.commit();
                if(state=='add'){
                    payrollStore = me._link.payrollStore;
                    payrollStore.reload();
                }
                me.close();
                Ext.Msg.alert("提示", "保存成功");
            },
            failure: function(){
                Ext.Msg.alert("提示", "保存失败");
            }
        });
    },

    onComboboxSelect: function(combo, records, eOpts) {
        var me = this,
            record = {},
        store = me.down('treepanel').getStore();

        store.load({
           params:{
               accountId:records[0].get('id'),
               persons:Ext.encode(record)
            }
        });

    },

    onPayrollFormAfterRender: function(component, eOpts) {
        var me = this,
            tree = me.down('treepanel'),
            accountStore = me.down('combobox').getStore(),
            store = tree.getStore(),
            state = me._link.state,
            record = me._link.record;

        me.down('form').loadRecord(record);

        if(state=='update'){
            store.load({
                params:{
                    accountId:record.get('accountId'),
                    persons:Ext.encode(record.get('persons'))
                }
            });
            accountStore.load();
        }

    },

    onTreepanelCheckChange: function(node, checked, eOpts) {
        node.cascadeBy(function (n) { n.set('checked', checked); });

        this.checkParent(node);
    },

    onWindowBeforeClose: function(panel, eOpts) {
        var me = this,
            tree = me.down('treepanel'),
            root = tree.getRootNode();
        this.removeTree(root);
    },

    checkParent: function(node) {
        node = node.parentNode;
        if(!node) return;
        var checkP=false;
        node.cascadeBy(function (n)
                       {
                           if (n != node) {
                               if (n.get('checked') == true) {
                                   checkP = true;
                               }
                           }
                       });
        node.set('checked', checkP);
        this.checkParent(node);

    },

    removeTree: function(node) {
        if (!node) return;
        while (node.hasChildNodes()) {
            this.removeTree(node.firstChild);
            node.removeChild(node.firstChild);
        }
    }

});