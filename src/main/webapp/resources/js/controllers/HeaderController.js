'use strict';

/**
 * UserController
 * @constructor
 */
var HeaderController = function($scope, $location) {


        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };


}
