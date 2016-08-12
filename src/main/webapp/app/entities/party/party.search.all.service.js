(function() {
    'use strict';

    angular
        .module('monitorApp')
        .factory('PartySearchAll', PartySearchAll);

    PartySearchAll.$inject = ['$resource'];

    function PartySearchAll($resource) {
        var resourceUrl =  'api/_searchAll/parties/';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
