(function() {
    'use strict';

    var jhiItemCount = {
        template: '',
        bindings: {
            page: '<',
            queryCount: '<total'
        }
    };

    angular
        .module('monitorApp')
        .component('jhiItemCount', jhiItemCount);
})();
