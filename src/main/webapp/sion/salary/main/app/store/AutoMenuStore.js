/*
 * File: app/store/AutoMenuStore.js
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

Ext.define('sion.salary.main.store.AutoMenuStore', {
    extend: 'Ext.data.Store',

    requires: [
        'Ext.data.proxy.Ajax',
        'Ext.data.reader.Json',
        'Ext.data.Field'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'AutoMenuStore',
            data: [
                {
                    title: '薪资管理',
                    iconCls: 's_iconbig_group',
                    imgSrc: 'images/iconbig/group.png',
                    menuItems: [
                        {
                            text: '待发薪资',
                            iconCls: 's_icon_action_copy',
                            imgSrc: 'images/action_copy.png',
                            namespace: 'sion.salary.payroll',
                            viewName: 'UnpublishPayroll',
                            desc: '显示待发薪资的列表'
                        },
                        {
                            text: '已发薪资',
                            iconCls: 's_icon_action_copy',
                            imgSrc: 'images/action_copy.png',
                            namespace: 'sion.salary.payroll',
                            viewName: 'PaidPayroll',
                            desc: '显示待发薪资的列表'
                        },
                        {
                            text: '薪资档案',
                            iconCls: 's_icon_chart_pie',
                            imgSrc: 'images/chart_pie.png',
                            namespace: 'sion.salary.social',
                            viewName: 'PersonAccountGrid',
                            desc: '管理人员薪资信息，包括薪资方案社保和公积金投保、缴费比率等'
                        }
                    ]
                },
                {
                    title: '薪资体系',
                    iconCls: 's_iconbig_chart_bar',
                    imgSrc: 'images/iconbig/chart_bar.png',
                    menuItems: [
                        {
                            text: '薪资方案',
                            iconCls: 's_icon_action_copy',
                            imgSrc: 'images/action_copy.png',
                            namespace: 'sion.salary.accounts',
                            viewName: 'SalaryPlanGrid',
                            desc: '建立薪资项目，设置薪资方案，管理薪资方案成员'
                        },
                        {
                            text: '社保方案',
                            iconCls: 's_icon_chart_pie',
                            imgSrc: 'images/chart_pie.png',
                            namespace: 'sion.salary.social',
                            viewName: 'SocialPlanGrid',
                            desc: '设置社保公积金方案，多维管理人员缴保'
                        },
                        {
                            text: '薪资层次',
                            iconCls: 's_icon_computer_go',
                            imgSrc: 'images/computer_go.png',
                            namespace: 'sion.salary.level',
                            viewName: 'LevelGrid',
                            desc: '定义薪资层次，适应不同岗位'
                        }
                    ]
                },
                {
                    title: '薪资设置',
                    iconCls: 's_iconbig_group_go',
                    imgSrc: 'images/iconbig/group_go.png',
                    menuItems: [
                        {
                            text: '薪资项目',
                            iconCls: 's_icon_action_copy',
                            imgSrc: 'images/action_copy.png',
                            namespace: 'sion.salary.accounts',
                            viewName: 'SalaryItems',
                            desc: '设置薪资构成的基本项目'
                        },
                        {
                            text: '社保项目',
                            iconCls: 's_icon_chart_pie',
                            imgSrc: 'images/chart_pie.png',
                            namespace: 'sion.salary.social',
                            viewName: 'SocialItemGridPanel',
                            desc: '定义社保公积金基础项目'
                        },
                        {
                            text: '个税设置',
                            iconCls: 's_icon_action_copy',
                            imgSrc: 'images/action_copy.png',
                            namespace: 'sion.salary.tax',
                            viewName: 'TaxGrid',
                            desc: '设置个税征缴参数'
                        }
                    ]
                }
            ],
            proxy: {
                type: 'ajax',
                reader: {
                    type: 'json'
                }
            },
            fields: [
                {
                    name: 'title'
                },
                {
                    name: 'iconCls'
                },
                {
                    name: 'menuItems'
                },
                {
                    name: 'imgSrc'
                },
                {
                    defaultValue: 'tab',
                    name: 'type'
                },
                {
                    defaultValue: 'undefined',
                    name: 'callback'
                },
                {
                    name: 'namespace'
                }
            ]
        }, cfg)]);
    }
});