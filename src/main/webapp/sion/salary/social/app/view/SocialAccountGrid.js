/*
 * File: app/view/SocialAccountGrid.js
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

Ext.define('sion.salary.social.view.SocialAccountGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.socialaccountgrid',

    requires: [
        'Ext.button.Button',
        'Ext.form.field.Date',
        'Ext.grid.View',
        'Ext.grid.column.Action',
        'Ext.toolbar.Paging'
    ],

    width: 1006,
    store: 'SocialAccount',

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
                            iconCls: 's_icon_action_add',
                            text: '<span style="font-size:14px;color:#3892D3;font-weight:bold;">新增</span>',
                            listeners: {
                                click: {
                                    fn: me.onButtonClick,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'button',
                            width: 70,
                            iconCls: 's_icon_action_search',
                            text: '查询',
                            listeners: {
                                click: {
                                    fn: me.onButtonClick2,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'button',
                            width: 70,
                            iconCls: 's_icon_action_clockwise',
                            text: '重置',
                            listeners: {
                                click: {
                                    fn: me.onButtonClick3,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'textfield',
                            itemId: 'name',
                            fieldLabel: '名称',
                            labelWidth: 30
                        },
                        {
                            xtype: 'textfield',
                            itemId: 'creater',
                            fieldLabel: '创建人',
                            labelWidth: 50
                        },
                        {
                            xtype: 'datefield',
                            itemId: 'startDate',
                            width: 170,
                            fieldLabel: '创建日期',
                            labelWidth: 60,
                            format: 'Y-m-d',
                            submitFormat: 'Y-m-d',
                            listeners: {
                                change: {
                                    fn: me.onStartDateChange,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'datefield',
                            itemId: 'endDate',
                            width: 120,
                            fieldLabel: '-',
                            labelSeparator: ' ',
                            labelWidth: 10,
                            format: 'Y-m-d',
                            submitFormat: 'Y-m-d',
                            listeners: {
                                change: {
                                    fn: me.onEndDateChange,
                                    scope: me
                                }
                            }
                        }
                    ]
                },
                {
                    xtype: 'pagingtoolbar',
                    dock: 'bottom',
                    width: 360,
                    displayInfo: true,
                    store: 'SocialAccount'
                }
            ],
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'name',
                    text: '方案名称',
                    flex: 6
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'createUserName',
                    text: '创建人',
                    flex: 3
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'date',
                    text: '创建日期',
                    flex: 3
                },
                {
                    xtype: 'actioncolumn',
                    hideable: false,
                    text: '方案成员',
                    tooltip: '修改',
                    flex: 2,
                    items: [
                        {
                            handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                // var me = this.up('gridpanel'),
                                //     namespace = me.getNamespace();

                                // var accountMember =  Ext.create(namespace + '.view.AccountMember',{
                                //     _account : record
                                // });
                                // accountMember.show();

                                var me = this.up('gridpanel').up(),
                                    store = Ext.getStore('InsuredPersonAccount'),
                                    selectRecords = [],
                                    personSelection = Ext.create("sion.salary.social.view.SearchPerson",
                                    {_scope : me, _callback : this.up('gridpanel').selectedCallback}),
                                    personGrid = personSelection.down('gridpanel'),
                                    personStore = personGrid.getStore();

                                me._account = record;
                                store.clearFilter(true);
                                Ext.apply(store.proxy.extraParams, {
                                    id : record.get('id')
                                });
                                store.load({
                                    callback: function(records, operation, success) {
                                        personStore.getProxy().extraParams = {insuredPersonExists : 'true',
                                        loadAll : 'true'};
                                        personStore.load({
                                            callback: function(records, operation, success) {
                                                personSelection.show();
                                                Ext.Array.each(store.data.items, function(item){
                                                    selectRecords.push(personStore.findRecord('id', item.data.id));
                                                });
                                                personSelection.down('gridpanel').getSelectionModel().select(selectRecords);
                                            }
                                        });
                                    }
                                });
                            },
                            iconCls: 's_icon_org_gear',
                            tooltip: '方案成员'
                        }
                    ]
                },
                {
                    xtype: 'actioncolumn',
                    hideable: false,
                    text: '修改',
                    tooltip: '修改',
                    flex: 1,
                    items: [
                        {
                            handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                this.up('gridpanel').detail(record);
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
                    flex: 1,
                    items: [
                        {
                            handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                var store = Ext.getStore("SocialAccount");

                                Ext.Msg.confirm('提示', '确定要删除吗？', function(text){
                                    if (text == 'yes'){
                                        Ext.Ajax.request({
                                            url :'salary/socialaccount/remove',//请求的服务器地址
                                            params : {
                                                id : record.get('id')
                                            },//发送json对象
                                            success:function(response,action){
                                                store.load();
                                                //                 me.resetGridSelect(record);
                                                Ext.Msg.alert("提示", "删除成功");
                                            },failure: function(){
                                                store.load();
                                                //                 me.resetGridSelect(record);
                                                Ext.Msg.alert("提示", "删除失败");
                                            }
                                        });
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
                itemdblclick: {
                    fn: me.onGridpanelItemDblClick,
                    scope: me
                },
                render: {
                    fn: me.onGridpanelRender,
                    scope: me
                }
            }
        });

        me.callParent(arguments);
    },

    onButtonClick: function(button, e, eOpts) {
        var  namespace = this.getNamespace(),
            panel =  Ext.create(namespace + '.view.SocialAccountEdit');

        panel.show();
    },

    onButtonClick2: function(button, e, eOpts) {
        var me = this,
            grid = button.up('grid'),
            store = grid.getStore(),
            name = grid.down('#name').getValue(),
            creater = grid.down('#creater').getValue(),
            startDate = grid.down('#startDate').getValue(),
            endDate = grid.down('#endDate').getValue(),
            startStr = '',
            endStr = '',
            filters = new Array();

        if(name && name !== ''){
            filters.push({
                property: "name",
                value: name
            });
        }
        if(creater && creater !== ''){
            filters.push({
                property: "creater",
                value: creater
            });
        }
        if(startDate && startDate !== ''){
            startStr =
                startDate.getFullYear()+"-"+
                (me.getFullDate(startDate.getMonth()+1))+"-"+
                me.getFullDate(startDate.getDate());
            filters.push({
                filterFn: function(item) {
                    return item.get("date") >= startStr;
                }
            });
        }
        if(endDate && endDate !== ''){
            startStr =
                endDate.getFullYear()+"-"+
                (me.getFullDate(endDate.getMonth()+1))+"-"+
                me.getFullDate(endDate.getDate());
            filters.push({
                filterFn: function(item) {
                    return item.get("date") >= startStr;
                }
            });
        }
        name = encodeURI(encodeURI(name));
        creater = encodeURI(encodeURI(creater));
        Ext.apply(store.proxy.extraParams, {
            name : name,
            creater : creater,
            startDate : startDate,
            endDate : endDate
        });
        if(filters.length ===0 ){
            store.load();
        }else{
            store.loadPage(1);
        }
    },

    onButtonClick3: function(button, e, eOpts) {
        var me = this,
            grid = button.up('grid'),
            name = grid.down('#name'),
            creater = grid.down('#creater'),
            startDate = grid.down('#startDate'),
            endDate = grid.down('#endDate');

        name.setValue('');
        creater.setValue('');
        startDate.setValue('');
        endDate.setValue('');
    },

    onStartDateChange: function(field, newValue, oldValue, eOpts) {
        this.down('#endDate').setMinValue(newValue);
    },

    onEndDateChange: function(field, newValue, oldValue, eOpts) {
        this.down('#startDate').setMaxValue(newValue);
    },

    onGridpanelItemDblClick: function(dataview, record, item, index, e, eOpts) {
        this.detail(record);
    },

    onGridpanelRender: function(component, eOpts) {
        var me = this,
            store = component.getStore();

        store.clearFilter(true);
        Ext.apply(store.proxy.extraParams, {
            name : '',
            creater : '',
            startDate : '',
            endDate : ''
        });
        store.load();
    },

    detail: function(record) {
        var me = this,
            namespace = me.getNamespace();

        var panel =  Ext.create(namespace + '.view.SocialAccountEdit',{
            _socialAccount : record
        });
        panel.show();
        // me.resetGridSelect(record);
    },

    getFullDate: function(day) {
        day = day > 9 ? day : '0' + day;
        return day;
    },

    selectedCallback: function(person, scope) {
        // var me = scope,
        //     namespace = me.getNamespace(),
        //     account = me._account,
        //     store = Ext.getStore('InsuredPersonAccount');

        // for(var i = 0; i < person.length; i++){
        //     if(store.find('id', person[i].data.id) === -1){
        //         var model = Ext.create(namespace + '.model.InsuredPersonAccount');
        //         model.data = person[i].data;
        //         if(model.get('insuredPerson') === ''){
        //             model.set('insuredPerson', null);
        //         }else{
        //             model.get('insuredPerson').accountId = account.get('id');
        //         }
        //         store.add(model);
        //     }
        // }
        var me = scope,
            namespace = me.getNamespace(),
            account = me._account,
            store = Ext.getStore('InsuredPersonAccount');

        store.each(function(record){
            record.phantom = true;
            if(record.get('insuredPerson') === ''){
                record.set('insuredPerson', null);
            }else{
                record.get('insuredPerson').accountId = '';
            }
        });
        for(var i = 0; i < person.length; i++){
            var record = store.findRecord('id', person[i].data.id);
        //     if(store.find('id', person[i].data.id) === -1){
            if(!record){
                var model = Ext.create(namespace + '.model.InsuredPersonAccount');
                model.data = person[i].data;
                if(model.get('insuredPerson') === ''){
                    model.set('insuredPerson', null);
                }else{
                    model.get('insuredPerson').accountId = account.get('id');
                }
                store.add(model);
            }else{
                if(record.get('insuredPerson') === ''){
                    record.set('insuredPerson', null);
                }else{
                    record.get('insuredPerson').accountId = account.get('id');
                }
            }
        }
        store.sync({
            success: function(response, opts){
                Ext.Msg.alert("提示", "保存成功");
            },
            failure: function(){
                Ext.Msg.alert("提示", "保存失败");
            }
        });
    }

});