'use strict';

/**
 * TodoController
 * @constructor
 */
var TodoController = function($scope, $http,$filter, $timeout) {

    $scope.fetchCarsList = function() {
        $http.get('todoes').success(function(todoList){
            $scope.todos = todoList;
        });
    }

    $scope.addNewTodo = function(newTodo) {
        newTodo.targetDate= $filter('date')($scope.dt, 'MM/dd/yyyy');
        newTodo.isCompleted=false;
        $http.post('todoes',newTodo).success(function() {
            $scope.fetchCarsList();
        });
        $scope.todo = {};
        $scope.dt=null;
    }

    $scope.changeStatus = function(todo) {
        $http.put('todoes/'+todo.id,todo).success(function() {
            $scope.fetchCarsList();
        });
        $scope.todo = {};
    }

    $scope.removeCar = function(todo) {
        $http.delete('todoes/'+todo.id).success(function() {
            $scope.fetchCarsList();
        });
    }

    $scope.removeAllTodos = function() {
        $http.delete('todoes').success(function() {
            $scope.fetchCarsList();
        });

    };

    $scope.fetchCarsList();

    $scope.today = function() {
        $scope.dt = new Date();
    };
    //$scope.today();

    $scope.showWeeks = false;
    $scope.toggleWeeks = function () {
        $scope.showWeeks = ! $scope.showWeeks;
    };

    $scope.clear = function () {
        $scope.dt = null;
    };

    // Disable weekend selection
    $scope.disabled = function(date, mode) {
        return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
    };

    $scope.toggleMin = function() {
        $scope.minDate = ( $scope.minDate ) ? null : new Date();
    };
    $scope.toggleMin();

    $scope.open = function() {
        $timeout(function() {
            $scope.opened = true;
        });
    };

    $scope.dateOptions = {
        'year-format': "'yy'",
        'starting-day': 1
    };

}
