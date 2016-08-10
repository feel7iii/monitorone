(function() {
    'use strict';

    angular
        .module('monitorApp')
        .factory('AppSearch', AppSearch);

    AppSearch.$inject = ['$resource'];

    function AppSearch($resource) {
        var resourceUrl =  'api/_countSearch/applications/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
        });

    }
})();
