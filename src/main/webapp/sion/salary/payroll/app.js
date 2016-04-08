/*
 * File: app.js
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

/*
 * File: app.js
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

// @require @packageOverrides
Ext.Loader.setConfig({
    enabled: true
});


Ext.application({
    paths: {
        'sion.salary.payroll': 'sion/salary/payroll/app'
    },
    models: [
        'Person',
        'Payroll',
        'Account',
        'PayrollSub',
        'AccountItem'
    ],
    stores: [
        'AccountStore',
        'PayrollStore',
        'PersonStore',
        'PayrollSubStore',
        'AccountItemStore'
    ],
    views: [
        'SelectPerson_win',
        'UnpublishPayroll',
        'PaidPayroll',
        'PayrollWindow',
        'PayrollSubForm',
        'PayrollSubWin',
        'PayrollSubGrid',
        'ExportWin'
    ],
    name: 'sion.salary.payroll'
});
