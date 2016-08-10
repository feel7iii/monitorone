(function() {
    'use strict';
    angular
        .module('monitorApp')
        .factory('Middleware', Middleware);

    Middleware.$inject = ['$resource'];

    function Middleware ($resource) {
        var resourceUrl =  'api/middlewares/:id';

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
