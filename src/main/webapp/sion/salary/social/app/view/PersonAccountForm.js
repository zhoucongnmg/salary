/*
 * File: app/view/PersonAccountForm.js
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

Ext.define('sion.salary.social.view.PersonAccountForm', {
    extend: 'Ext.window.Window',

    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.tab.Panel',
        'Ext.form.Panel',
        'Ext.tab.Tab',
        'Ext.toolbar.Spacer',
        'Ext.form.field.ComboBox',
        'Ext.grid.Panel',
        'Ext.grid.column.Column',
        'Ext.form.field.Number',
        'Ext.grid.View',
        'Ext.grid.plugin.RowEditing',
        'Ext.form.field.Hidden',
        'Ext.form.field.Date'
    ],

    height: 766,
    width: 861,
    layout: 'column',
    bodyPadding: 10,
    title: '员工薪资档案',

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
                            iconCls: 's_icon_page_add',
                            text: '保存信息',
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
                    xtype: 'tabpanel',
                    columnWidth: 1,
                    height: 672,
                    activeTab: 0,
                    items: [
                        {
                            xtype: 'form',
                            height: 543,
                            itemId: 'salaryForm',
                            layout: 'column',
                            bodyPadding: 10,
                            title: '基本信息',
                            items: [
                                {
                                    xtype: 'textfield',
                                    columnWidth: 0.45,
                                    itemId: 'personCode',
                                    fieldLabel: '员工编号',
                                    labelWidth: 80,
                                    name: 'personCode'
                                },
                                {
                                    xtype: 'button',
                                    itemId: 'search',
                                    text: '搜',
                                    listeners: {
                                        click: {
                                            fn: me.onSearchClick,
                                            scope: me
                                        }
                                    }
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 0.06,
                                    height: 20
                                },
                                {
                                    xtype: 'textfield',
                                    columnWidth: 0.45,
                                    itemId: 'name',
                                    fieldLabel: '姓名',
                                    labelWidth: 80,
                                    name: 'name',
                                    invalidText: '必须填写人员姓名',
                                    allowBlank: false,
                                    allowOnlyWhitespace: false
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 1.1,
                                    height: 20
                                },
                                {
                                    xtype: 'textfield',
                                    columnWidth: 0.45,
                                    itemId: 'dept',
                                    fieldLabel: '部门',
                                    labelWidth: 80,
                                    name: 'dept'
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 0.1,
                                    height: 20
                                },
                                {
                                    xtype: 'textfield',
                                    columnWidth: 0.45,
                                    itemId: 'duty',
                                    fieldLabel: '职务',
                                    labelWidth: 80,
                                    name: 'duty'
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 1.1,
                                    height: 20
                                },
                                {
                                    xtype: 'textfield',
                                    columnWidth: 0.45,
                                    itemId: 'idCard',
                                    fieldLabel: '身份证号',
                                    labelWidth: 80,
                                    name: 'idCard'
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 1.1,
                                    height: 20
                                },
                                {
                                    xtype: 'textfield',
                                    columnWidth: 0.45,
                                    fieldLabel: '工资卡号',
                                    labelWidth: 80,
                                    name: 'bankAccount'
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 0.1,
                                    height: 20
                                },
                                {
                                    xtype: 'textfield',
                                    columnWidth: 0.45,
                                    fieldLabel: '银行',
                                    labelWidth: 80,
                                    name: 'bank'
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 1.1,
                                    height: 20
                                },
                                {
                                    xtype: 'textfield',
                                    columnWidth: 0.45,
                                    fieldLabel: '开户网点',
                                    labelWidth: 80,
                                    name: 'bankOfDeposit'
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 0.1,
                                    height: 20
                                },
                                {
                                    xtype: 'combobox',
                                    columnWidth: 0.45,
                                    itemId: 'accountId',
                                    fieldLabel: '薪资方案',
                                    labelWidth: 80,
                                    name: 'accountId',
                                    editable: false,
                                    displayField: 'name',
                                    store: 'SalaryAccount',
                                    valueField: 'id'
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 1.1,
                                    height: 20
                                },
                                {
                                    xtype: 'combobox',
                                    columnWidth: 0.45,
                                    itemId: 'level',
                                    fieldLabel: '薪资层次',
                                    labelWidth: 80,
                                    name: 'level',
                                    displayField: 'name',
                                    store: 'LevelStore',
                                    valueField: 'id',
                                    listeners: {
                                        change: {
                                            fn: me.onLevelChange,
                                            scope: me
                                        }
                                    }
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 0.1,
                                    height: 20
                                },
                                {
                                    xtype: 'combobox',
                                    columnWidth: 0.45,
                                    itemId: 'rank',
                                    fieldLabel: '薪资级别',
                                    labelWidth: 80,
                                    name: 'rank',
                                    displayField: 'rank',
                                    queryMode: 'local',
                                    store: 'RankStore',
                                    valueField: 'rank',
                                    listeners: {
                                        change: {
                                            fn: me.onRankChange,
                                            scope: me
                                        }
                                    }
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 1,
                                    height: 20
                                },
                                {
                                    xtype: 'textfield',
                                    columnWidth: 1,
                                    margin: '10 0 0 0',
                                    fieldLabel: '备注',
                                    labelWidth: 80,
                                    name: 'note'
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 1,
                                    height: 20,
                                    width: 100
                                },
                                {
                                    xtype: 'gridpanel',
                                    columnWidth: 1,
                                    height: 286,
                                    itemId: 'SalaryItemGrid',
                                    header: false,
                                    title: 'My Grid Panel',
                                    store: 'PersonSalaryItem',
                                    columns: [
                                        {
                                            xtype: 'gridcolumn',
                                            width: '30%',
                                            dataIndex: 'name',
                                            text: '工资项目'
                                        },
                                        {
                                            xtype: 'gridcolumn',
                                            width: '15%',
                                            dataIndex: 'value',
                                            text: '方案值'
                                        },
                                        {
                                            xtype: 'gridcolumn',
                                            width: '15%',
                                            dataIndex: 'rankValue',
                                            text: '层级值'
                                        },
                                        {
                                            xtype: 'gridcolumn',
                                            width: '15%',
                                            dataIndex: 'personValue',
                                            text: '个人值',
                                            editor: {
                                                xtype: 'numberfield'
                                            }
                                        },
                                        {
                                            xtype: 'gridcolumn',
                                            width: '20%',
                                            dataIndex: 'choose',
                                            text: '取值设置',
                                            editor: {
                                                xtype: 'combobox',
                                                displayField: 'name',
                                                store: 'ChooseStore',
                                                valueField: 'value'
                                            }
                                        }
                                    ],
                                    plugins: [
                                        Ext.create('Ext.grid.plugin.RowEditing', {
                                            cancelBtnText: '取消',
                                            saveBtnText: '保存'
                                        })
                                    ]
                                },
                                {
                                    xtype: 'hiddenfield',
                                    itemId: 'personId',
                                    fieldLabel: 'Label',
                                    name: 'personId'
                                }
                            ]
                        },
                        {
                            xtype: 'form',
                            itemId: 'socialForm',
                            layout: 'column',
                            bodyPadding: 10,
                            title: '社保信息',
                            items: [
                                {
                                    xtype: 'textfield',
                                    columnWidth: 0.45,
                                    fieldLabel: '社保号',
                                    labelWidth: 80,
                                    name: 'insuredNo'
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 0.1,
                                    height: 20
                                },
                                {
                                    xtype: 'datefield',
                                    columnWidth: 0.45,
                                    fieldLabel: '参保日期',
                                    labelWidth: 80,
                                    name: 'insuredDate',
                                    editable: false,
                                    format: 'Y-m-d'
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 1,
                                    height: 20
                                },
                                {
                                    xtype: 'textfield',
                                    columnWidth: 0.45,
                                    fieldLabel: '工作地',
                                    labelWidth: 80,
                                    name: 'workplace'
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 0.1,
                                    height: 20
                                },
                                {
                                    xtype: 'combobox',
                                    columnWidth: 0.45,
                                    fieldLabel: '参保状态',
                                    labelWidth: 80,
                                    name: 'status',
                                    allowBlank: false,
                                    allowOnlyWhitespace: false,
                                    editable: false,
                                    store: [
                                        [
                                            'In',
                                            '在保'
                                        ],
                                        [
                                            'Out',
                                            '退保'
                                        ]
                                    ]
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 1,
                                    height: 20
                                },
                                {
                                    xtype: 'datefield',
                                    columnWidth: 0.45,
                                    fieldLabel: '退保日期',
                                    labelWidth: 80,
                                    name: 'outDate',
                                    editable: false,
                                    format: 'Y-m-d'
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 0.1,
                                    height: 20
                                },
                                {
                                    xtype: 'combobox',
                                    columnWidth: 0.45,
                                    itemId: 'socialAccount',
                                    fieldLabel: '社保方案',
                                    labelWidth: 80,
                                    name: 'accountId',
                                    editable: false,
                                    displayField: 'name',
                                    store: 'SocialAccountAll',
                                    valueField: 'id'
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 1,
                                    height: 20
                                },
                                {
                                    xtype: 'textfield',
                                    columnWidth: 0.45,
                                    fieldLabel: '社保代付地',
                                    hideEmptyLabel: false,
                                    labelWidth: 80,
                                    name: 'socialWorkplace'
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 0.1,
                                    height: 20
                                },
                                {
                                    xtype: 'textfield',
                                    columnWidth: 0.45,
                                    fieldLabel: '公积金代付地',
                                    hideEmptyLabel: false,
                                    labelWidth: 80,
                                    name: 'accumulationFundsWorkplace'
                                },
                                {
                                    xtype: 'tbspacer',
                                    columnWidth: 1,
                                    height: 20
                                },
                                {
                                    xtype: 'gridpanel',
                                    columnWidth: 1,
                                    height: 403,
                                    itemId: 'socialGrid',
                                    header: false,
                                    title: 'My Tab',
                                    store: 'PersonSocialItem',
                                    columns: [
                                        {
                                            xtype: 'gridcolumn',
                                            dataIndex: 'socialItemName',
                                            text: '项目名称',
                                            flex: 1
                                        },
                                        {
                                            xtype: 'gridcolumn',
                                            dataIndex: 'cardinality',
                                            text: '基数',
                                            flex: 0.6,
                                            editor: {
                                                xtype: 'numberfield'
                                            }
                                        },
                                        {
                                            xtype: 'gridcolumn',
                                            dataIndex: 'companyPaymentValue',
                                            text: '单位缴费',
                                            flex: 1.25,
                                            editor: {
                                                xtype: 'numberfield'
                                            }
                                        },
                                        {
                                            xtype: 'gridcolumn',
                                            renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                                                if(value==="Percent"){
                                                    return "百分比";
                                                }
                                                else{
                                                    return "定额";
                                                }
                                            },
                                            dataIndex: 'companyPaymentType',
                                            text: '单位缴费类型',
                                            editor: {
                                                xtype: 'combobox',
                                                store: [
                                                    [
                                                        'Percent',
                                                        '百分比'
                                                    ],
                                                    [
                                                        'Quota',
                                                        '定额'
                                                    ]
                                                ]
                                            }
                                        },
                                        {
                                            xtype: 'gridcolumn',
                                            dataIndex: 'personalPaymentValue',
                                            text: '个人缴费',
                                            flex: 1.25,
                                            editor: {
                                                xtype: 'numberfield'
                                            }
                                        },
                                        {
                                            xtype: 'gridcolumn',
                                            renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                                                if(value==="Percent"){
                                                    return "百分比";
                                                }
                                                else{
                                                    return "定额";
                                                }
                                            },
                                            dataIndex: 'personalPaymentType',
                                            text: '个人缴费类型',
                                            editor: {
                                                xtype: 'combobox',
                                                store: [
                                                    [
                                                        'Percent',
                                                        '百分比'
                                                    ],
                                                    [
                                                        'Quota',
                                                        '定额'
                                                    ]
                                                ]
                                            }
                                        }
                                    ],
                                    plugins: [
                                        Ext.create('Ext.grid.plugin.RowEditing', {
                                            saveBtnText: '保存',
                                            cancelBtnText: '取消'
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

        me.callParent(arguments);
    },

    onSaveClick: function(button, e, eOpts) {
        var me =this,
            namespace=me.getNamespace(),
            salaryForm=me.down('#salaryForm'),
            socialForm=me.down('#socialForm'),
            salaryItemGrid=me.down('#SalaryItemGrid'),
            salaryItemStore=salaryItemGrid.getStore(),
            insuredGrid=me.down('#socialGrid'),
            insuredItemStore=insuredGrid.getStore(),
            accountItems=[],
            insuredItems=[],
            insuredPerson,
            model;

        if(!salaryForm.isValid()){
            Ext.Msg.alert("提示","请填写必填项目");
            return;
        }

        if(me._record){
            model=Ext.create(namespace+".model.PersonAccount",salaryForm.getValues());
            model.set('id',me._record.data.id);
        }
        else{
            model=Ext.create(namespace+".model.PersonAccount",salaryForm.getValues());
        }

        //薪资项目个人值设置、取值设置
        var accountItemsSetting={};
        salaryItemStore.each(function(item){
            accountItems.push({accountItemId:item.data.salaryItemId,accountItemName:item.data.name,value:item.data.personValue});
            accountItemsSetting[item.data.salaryItemId]=item.data.choose;
        });
        //保险项目设置
        insuredItemStore.each(function(item){
            insuredItems.push(item.data);
        });


        model.set("accountItems",accountItems);
        model.set('accountItemsSetting',accountItemsSetting);
        model.set('insuredItems',insuredItems);
        insuredPerson=Ext.create(namespace+".model.InsuredPerson",socialForm.getValues());
        if(!insuredPerson.data.status||insuredPerson.data.status.length===0){
            insuredPerson.data.status="Out";
        }
        model.set('insuredPerson',insuredPerson.data);

        button.disabled=true;
        Ext.Ajax.request({
            url: 'salary/person/create',
            method:'POST',
            jsonData: model.data,
            success:function(response){
                var res=Ext.JSON.decode(response.responseText);
                Ext.Msg.alert("提示",res.message);
                if(res.success==false)
                {
                    return;
                }
                if(me._grid){
                    me._grid.getStore().reload();
                }
                button.disabled=false;
                me.close();
            },
            failure:function(form,action){
                Ext.Msg.alert("提示","保存信息失败!");
                button.disabled=false;
            }
        });
    },

    onSearchClick: function(button, e, eOpts) {
        var personSelection =Ext.create("Sion.Hr.Base.view.PersonSelection",
                                        {_selectionMode:"SINGLE",_scope:this,_callback:this.selectedCallback});
        personSelection.show();
    },

    onLevelChange: function(field, newValue, oldValue, eOpts) {
        var me=this,
            store=field.getStore(),
            rank=me.down('#rank'),
            namespace=me.getNamespace(),
            rankstore=rank.getStore();
        rankstore.removeAll();
        store.each(function(level){
            if(level.data.id===newValue){
                var levelItems=level.data.levelItems;
                Ext.Array.each(levelItems,function(item){
                    rankstore.add(Ext.create(namespace+".model.Rank",item));
                });
            }
        });

    },

    onRankChange: function(field, newValue, oldValue, eOpts) {
        var me=this,
            levelStore=me.down('#level').getStore(),
            levelValue=me.down('#level').getValue(),
            salaryItemStore=me.down('#SalaryItemGrid').getStore();
        levelStore.each(function(level){
            if(level.data.id===levelValue){
                var levelItems=level.data.levelItems;
                Ext.Array.each(levelItems,function(item){
                    if(item.rank===newValue){

                        salaryItemStore.each(function(salaryItem){
                            salaryItem.set('rankValue',item.salaryItemValues[salaryItem.data.salaryItemId]);
                            salaryItem.set('choose','Level');
                        });

                    }
                });
            }
        });

    },

    onWindowAfterRender: function(component, eOpts) {
        var me=this,
            namespace=me.getNamespace(),
            salaryForm=me.down('#salaryForm'),
            socialForm=me.down('#socialForm'),
            salaryItemGrid=me.down('#SalaryItemGrid'),
            salaryItemStore=salaryItemGrid.getStore(),
            insuredGrid=me.down('#socialGrid'),
            insuredItemStore=insuredGrid.getStore();

        //load combobox
        var level=me.down('#level'),
            accountId=me.down('#accountId'),
            socialAccount=me.down('#socialAccount');

        level.getStore().load();
        accountId.getStore().load();
        socialAccount.getStore().load();

        if(me._record){
            salaryForm.getForm().setValues(me._record.data);
            socialForm.getForm().setValues(me._record.data.insuredPerson);
            salaryItemStore.removeAll();
            //回显
            Ext.Array.each(me._record.data.accountItems,function(item){
                salaryItemStore.add(Ext.create(namespace+".model.PersonSalaryItem",
                                               {salaryItemId:item.accountItemId,
                                                name:item.accountItemName,
                                                personValue:item.value,
                                                choose:me._record.data.accountItemsSetting[item.accountItemId]}));
            });
            insuredItemStore.removeAll();
            Ext.Array.each(me._record.data.insuredItems,function(item){
                insuredItemStore.add(Ext.create(namespace+".model.PersonSocialItem",item));
            });

            salaryForm.down('#personCode').setReadOnly(true);
            salaryForm.down('#name').setReadOnly(true);
            salaryForm.down('#search').disabled=true;

            var echotask = new Ext.util.DelayedTask(function(){

                accountId.getStore().each(function(account){
                    if(account.data.id===me._record.data.accountId){
                        var items=account.data.accountItems;
                        me.fillSalaryItemValue(salaryItemStore,items);
                    }
                });

                level.getStore().each(function(level){

                    if(level.data.id===me._record.data.level){
                        var levelItems=level.data.levelItems;
                        Ext.Array.each(levelItems,function(item){
                            if(item.rank===me._record.data.rank){

                                me.fillRankValue(salaryItemStore,item.salaryItemValues);

                            }
                        });
                    }
                });

            });

            echotask.delay(500);
        }

        var task = new Ext.util.DelayedTask(function(){
            accountId.on("change", me.salaryAccountChange, me);
            socialAccount.on("change", me.socialAccountChange, me);
        });

        task.delay(1000);
    },

    onWindowBeforeClose: function(panel, eOpts) {
        var me=this,
            socialItemGrid=me.down('#socialGrid'),
            salaryItemGrid=me.down('#SalaryItemGrid');

        socialItemGrid.getStore().removeAll();
        salaryItemGrid.getStore().removeAll();
    },

    selectedCallback: function(persons, scope) {
        var me=scope,
            salaryForm=me.down('#salaryForm'),
            socialForm=me.down('#socialForm'),
            person=persons[0];

        Ext.Ajax.request({
            url:'salary/person/checkExistByPersonCode?personCode='+person.data.serialNum,
            method:'GET',
            success:function(res){
                var responseData=Ext.JSON.decode(res.responseText);
                if(responseData===true){
                    Ext.Msg.alert("提示","此员工的薪资档案已经存在，不能重复建档");
                    return;
                }

                salaryForm.down('#personCode').setValue(person.data.serialNum);
                salaryForm.down('#name').setValue(person.data.name);
                salaryForm.down('#dept').setValue(person.data.deptName);
                salaryForm.down('#duty').setValue(person.data.position);
                salaryForm.down('#idCard').setValue(person.data.idCard);
                salaryForm.down('#personId').setValue(person.data.id);


            },
            failure:function(form,action){
                Ext.Msg.alert("提示","网络通信异常，请联系管理员");
                button.disabled=false;
            }
        });


    },

    socialAccountChange: function(field, newValue, oldValue, eOpts) {
        var me=this,
            namespace=me.getNamespace(),
            store=field.getStore(),
            socialGrid=me.down('#socialGrid'),
            gridStore=socialGrid.getStore();
        gridStore.removeAll();
        store.each(function(account){
            if(account.data.id===newValue){
                var items=account.data.socialAccountItems;
                Ext.Array.each(items,function(item){
                    gridStore.add(Ext.create(namespace+".model.PersonSocialItem",item));
                });
            }
        });

    },

    salaryAccountChange: function(field, newValue, oldValue, eOpts) {
        var me=this,
            namespace=me.getNamespace(),
            store=field.getStore(),
            SalaryItemGrid=me.down('#SalaryItemGrid'),
            gridStore=SalaryItemGrid.getStore();

        gridStore.removeAll();
        store.each(function(account){
            if(account.data.id===newValue){
                var items=account.data.accountItems;
                Ext.Array.each(items,function(item){
                    if(item.type==='Input'){
                        var salaryItem=Ext.create(namespace+".model.PersonSalaryItem",item);
                        salaryItem.set('choose','Solution');
                        gridStore.add(salaryItem);
                    }
                });
            }
        });
    },

    fillSalaryItemValue: function(gridStore, items) {
        gridStore.each(function(salaryItem){
            Ext.Array.each(items,function(item){
                if(salaryItem.data.salaryItemId===item.salaryItemId){
                    salaryItem.set('value',item.value);
                }
            });


        });
    },

    fillRankValue: function(gridStore, items) {
        gridStore.each(function(salaryItem){
            Ext.Array.each(items,function(item){
                salaryItem.set('rankValue',item[salaryItem.data.salaryItemId]);
            });


        });
    }

});