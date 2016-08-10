(function() {
    'use strict';

    angular
        .module('monitorApp')
        .factory('MidSearch', MidSearch);

    MidSearch.$inject = ['$resource'];

    function MidSearch($resource) {
        var resourceUrl =  'api/_countSearch/middlewares/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
