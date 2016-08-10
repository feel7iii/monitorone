(function() {
    'use strict';

    angular
        .module('monitorApp')
        .factory('ComputerCount', ComputerCount);

    ComputerCount.$inject = ['$resource'];

    function ComputerCount($resource) {
        var resourceUrl =  'api/_countSearch/computers';

        return $resource(resourceUrl, {}, {
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    return data;
                }
            }
        });
    }
})();
