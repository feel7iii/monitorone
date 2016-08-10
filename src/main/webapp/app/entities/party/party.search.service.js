(function() {
    'use strict';

    angular
        .module('monitorApp')
        .factory('PartySearch', PartySearch);

    PartySearch.$inject = ['$resource'];

    function PartySearch($resource) {
        var resourceUrl =  'api/_search/parties/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
