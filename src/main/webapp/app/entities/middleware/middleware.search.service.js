(function() {
    'use strict';

    angular
        .module('monitorApp')
        .factory('MiddlewareSearch', MiddlewareSearch);

    MiddlewareSearch.$inject = ['$resource'];

    function MiddlewareSearch($resource) {
        var resourceUrl =  'api/_search/middlewares/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
