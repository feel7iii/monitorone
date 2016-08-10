(function() {
    'use strict';
    angular
        .module('monitorApp')
        .factory('Computer', Computer);

    Computer.$inject = ['$resource'];

    function Computer ($resource) {
        var resourceUrl =  'api/computers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
