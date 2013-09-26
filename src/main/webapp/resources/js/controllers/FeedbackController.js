'use strict';

/**
 * FeedbackController
 * @constructor
 */
var FeedbackController = function($scope, $http) {



    $scope.feedbackSubmit = function(feedback) {
        $http.post('feedback',feedback).success(function() {
            $scope.feedbacksuccess=true;
        });
        $scope.feedback = {};
    }


}
