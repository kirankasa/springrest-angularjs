'use strict';

/**
 * UserController
 * @constructor
 */
var UserController = function($scope, $http,$filter) {



    $scope.signUpUser = function(user) {
        $http.post('userinfoes',user).success(function() {
            $scope.signupsuccess=true;
        });
        $scope.user = {};
    }


}
