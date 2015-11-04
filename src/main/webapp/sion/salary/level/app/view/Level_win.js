/*
 * File: app/view/Level_win.js
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

Ext.define('sion.salary.level.view.Level_win', {
    extend: 'Ext.window.Window',

    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.button.Button',
        'Ext.form.Panel',
        'Ext.form.field.Text',
        'Ext.form.FieldSet',
        'Ext.grid.Panel',
        'Ext.grid.column.Column',
        'Ext.grid.View',
        'Ext.grid.plugin.RowEditing'
    ],

    height: 640,
    width: 780,
    layout: 'fit',
    title: '薪资层次',

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
                            text: '保存',
                            listeners: {
                                click: {
                                    fn: me.onSaveClick,
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
                    title: 'My Form',
                    items: [
                        {
                            xtype: 'textfield',
                            anchor: '100%',
                            height: 35,
                            itemId: 'name',
                            fieldLabel: '名称',
                            name: 'name'
                        },
                        {
                            xtype: 'button',
                            text: '添加等级',
                            listeners: {
                                click: {
                                    fn: me.onAddRowClick,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'button',
                            margin: '0 0 0 10',
                            text: '删除等级',
                            listeners: {
                                click: {
                                    fn: me.onDeleteClick,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'fieldset',
                            layout: 'fit',
                            items: [
                                {
                                    xtype: 'gridpanel',
                                    height: 483,
                                    header: false,
                                    title: 'My Grid Panel',
                                    store: 'LevelItemStore',
                                    columns: [
                                        {
                                            xtype: 'gridcolumn',
                                            dataIndex: 'rank',
                                            text: '等级',
                                            editor: {
                                                xtype: 'textfield'
                                            }
                                        }
                                    ],
                                    plugins: [
                                        Ext.create('Ext.grid.plugin.RowEditing', {
                                            listeners: {
                                                edit: {
                                                    fn: me.onRowEditingEdit,
                                                    scope: me
                                                }
                                            }
                                        })
                                    ]
                                }
                            ]
                        }
                    ]
                }
            ],
            listeners: {
                afterrender: {
                    fn: me.onWindowAfterRender,
                    scope: me
                },
                beforeclose: {
                    fn: me.onWindowBeforeClose,
                    scope: me
                }
            }
        });

        me.processLevel_win(me);
        me.callParent(arguments);
    },

    processLevel_win: function(config) {

        var fields = [];


        fields.push({name:"id"});
        fields.push({name:"rank"});

        Ext.Ajax.request({
            url:'salary/level/getSalaryItem',
            method:'get',
            timeout:4000,
            async:false,    //不使用异步
            success:function(response, opts){

                var items=Ext.JSON.decode(response.responseText);
                config._salaryItems=items;
                Ext.Array.each(items,function(f){
                    fields.push({"name":f.id,"displayname":f.name});
                });
            },
            failure: function(response, opts) {
                Ext.Msg.alert('提示信息','数据请求错误，请稍候重新尝试获取数据……');
            }
        });


        config.itemModel=Ext.define("sion.salary.level.model.LevelItem", {
            extend:'Ext.data.Model',
            fields:fields
        });
        return config;
    },

    onSaveClick: function(button, e, eOpts) {
        var me=this,
            namespace=me.getNamespace(),
            form=me.down('form'),
            grid=me.down('gridpanel'),
            store=grid.getStore(),
            levelItems=[],
            model =me._record?me._record: Ext.create(namespace+'.model.Level');

        model.set('name',form.down('#name').getValue());

        //set items
        store.each(function(record){
            var item={id:record.data.id===null?"":record.data.id,rank:record.data.rank};
            var salaryItemNames=[];
            var salaryItemValues=[];

            var keys=record.fields.keys;
            Ext.Array.each(keys,function(key){
                if(key!=="id" && key !=="rank"){
                    value={};
                    value[key]=record.get(key);
                    salaryItemValues.push(value);

                    var name={};
                    Ext.Array.each(me._salaryItems,function(salary){
                        if(key===salary.id){
                            name[key]=salary.name;
                        }
                    });

                    salaryItemNames.push(name);
                }
            });
            item.salaryItemValues=salaryItemValues;
            item.salaryItemNames=salaryItemNames;

            levelItems.push(item);
        });


        model.set("levelItems",levelItems);



        Ext.Ajax.request({
            url:'salary/level/create',
            method:'POST',
            jsonData:model.data,
            success:function(res){
                var responseData=Ext.JSON.decode(res.responseText);
                if(responseData.success===true){
                    if(me._levelGrid){
                        me._levelGrid.getStore().reload();
                    }
                }
                Ext.Msg.alert("提示",responseData.message);
                button.disabled=false;
                me.close();

            },
            failure:function(form,action){
                Ext.Msg.alert("提示","网络通信异常，请联系管理员");
                button.disabled=false;
            }
        });
    },

    onAddRowClick: function(button, e, eOpts) {
        var me=this,
            grid=me.down('gridpanel'),
            store=grid.getStore(),
            form=me.down('form');

        var model=Ext.create(me.itemModel);

        store.add(model);
    },

    onDeleteClick: function(button, e, eOpts) {

    },

    onRowEditingEdit: function(editor, context, eOpts) {

    },

    onWindowAfterRender: function(component, eOpts) {
        var me=this,
            grid=me.down('gridpanel'),
            store=grid.getStore(),columns=new Array(),
            form=me.down('form');




        // model=Ext.create(me.itemModel);
        // model.set("rank","level2");
        // model.set("task1","12");
        // model.set('task2',"123");
        // store.add(model);

        //Danymic columns
        Ext.suspendLayouts();

        var cols=grid.columns;
        Ext.Array.each(cols,function(c){
            columns.push({text:c.text,dataIndex:c.dataIndex,editor:c.editor});
        });

        Ext.Array.each(me._salaryItems,function(salary){
            columns.push({text:salary.name,dataIndex:salary.id,editor: {
                xtype: 'numberfield'
            }});
        });

        grid.reconfigure(store, columns);
        Ext.resumeLayouts(true);

        //set value
        if(me._record){
            form.getForm().setValues(me._record.data);

            var levelItems=me._record.data.levelItems;
            Ext.Array.each(levelItems,function(item){
                var model=Ext.create(me.itemModel);
                    model.set("rank",item.rank);

                var salaryItemValues=item.salaryItemValues,
                    salaryItemNames=item.salaryItemNames;

                Ext.Array.each(salaryItemValues,function(value){
                    var key=Object.keys(value)[0];
                    model.set(key,value[key]);
                });
                 store.add(model);
            });



        }

        //set grid value

    },

    onWindowBeforeClose: function(panel, eOpts) {
        var me=this,
                    grid=me.down('gridpanel'),
                    store=grid.getStore();
        store.removeAll();
    }

});