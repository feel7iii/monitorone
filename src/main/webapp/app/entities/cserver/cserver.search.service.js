(function() {
    'use strict';

    angular
        .module('monitorApp')
        .factory('CserverSearch', CserverSearch);

    CserverSearch.$inject = ['$resource'];

    function CserverSearch($resource) {
        var resourceUrl =  'api/_search/cservers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
