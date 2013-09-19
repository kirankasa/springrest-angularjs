'use strict';

/**
 * UserController
 * @constructor
 */
var UserController = function($scope, $http,$filter) {



    $scope.signUpUser = function(user) {
        newTodo.targetDate= $filter('date')($scope.dt, 'MM/dd/yyyy');
        newTodo.isCompleted=false;
        $http.post('userinfoes',user).success(function() {
            $scope.signupsuccess=true;
        });
        $scope.user = {};
    }


}
