(function() {
    'use strict';
    angular
        .module('monitorApp')
        .factory('Mmetadate', Mmetadate);

    Mmetadate.$inject = ['$resource'];

    function Mmetadate ($resource) {
        var resourceUrl =  'api/mmetadates/:id';

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
