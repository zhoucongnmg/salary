/*
 * File: app/controller/Terminal.js
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

Ext.define('sion.salary.formula.controller.Terminal', {
    extend: 'Ext.app.Controller',

    refs: [
        {
            ref: 'itemSelection',
            selector: '#Calc_ItemSelection'
        }
    ],

    initTerm: function(config) {
        var me = this,
            itemSelection = me.getItemSelection(),
            ns = me.getNamespace(),
            store = itemSelection.getStore(),
            validator = Ext.create(ns + '.controller.Validator'),
            message,
            term;

        config.keydown = me.onKeydown;
        config._controller = me;

        var term = $('#' + config.id).terminal(function(command, term) {
            var history = term._history||[];
            if (command.trim()!='') {
                message = validator.validate(command,store.data.items);
                if (message=='') {
                    term.echo('验证成功');
                    history.push(command);
                    term._history = history;
                }else {
                    term.error(message);
                }
            }

        }, config);
        term.focus(true);
    },

    getTerm: function(id) {
        var me = this,
            term = $('#'+id).terminal();

        return term;
    },

    getCommand: function(id) {
        var me = this,
            term = me.getTerm(id);

        return term.get_command();
    },

    onKeydown: function(e, term) {
        var me = this._controller,
            commandStr = term.get_command(),
            pos = term.export_view().position;
        /**
        if (e.which == 8) {
            if (commandStr&&commandStr.length>0) {
                if (commandStr.charAt(pos-1)==']'&&(commandStr.indexOf('[')>-1&&commandStr.indexOf('[')<pos)) {
                    commandStr = commandStr.substring(0,commandStr.indexOf('['));
                    term.set_command(commandStr);
                    return false;
                }
            }
        }else if (e.which==219 || e.which==221) { //验证"["和"]"
            return false;
        }else if (e.which==13) {

            message = validator.validate(commandStr,store);
            if (message=='') {
                term.echo('验证成功');
            }else {
                term.error(message);
            }

        }
         **/
    },

    setCommand: function(id, command) {
        var me = this,
            term = me.getTerm(id);

        return term.set_command(command);
    }

});
