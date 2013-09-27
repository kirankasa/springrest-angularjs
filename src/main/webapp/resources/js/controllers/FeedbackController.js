'use strict';

/**
 * FeedbackController
 * @constructor
 */
var FeedbackController = function($scope, $http, $location,AuthenticationService) {



    $scope.feedbackSubmit = function(feedback) {
        $http.post('feedback',feedback).success(function() {
            $scope.feedbacksuccess=true;
        });
        $scope.feedback = {};
    }



    $scope.logout = function() {
        AuthenticationService.logout().success(function() {
            $location.path('/login');
        });
    };

    if(AuthenticationService.isLoggedIn()){
        $scope.showLogout=true;
    }else{
        $scope.showLogout=false;
    }



}
