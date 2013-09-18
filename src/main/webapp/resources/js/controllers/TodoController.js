'use strict';

/**
 * TodoController
 * @constructor
 */
var TodoController = function($scope, $http,$filter) {
    $scope.fetchCarsList = function() {
        $http.get('todoes').success(function(todoList){
            $scope.todos = todoList;
        });
    }

    $scope.addNewTodo = function(newTodo) {
        newTodo.targetDate= $filter('date')(new Date(), 'medium');
        newTodo.isCompleted=false;
        $http.post('todoes',newTodo).success(function() {
            $scope.fetchCarsList();
        });
        $scope.todo = {};
    }

    $scope.changeStatus = function(todo) {
        $http.put('todoes/'+todo.id,todo).success(function() {
            $scope.fetchCarsList();
        });
        $scope.todo = {};
    }

    $scope.removeCar = function(car) {
        console.log('Deleting : '+car.name);

        $http.delete('todoes/'+car.id).success(function() {
            $scope.fetchCarsList();
        });
    }

    $scope.removeAllTodos = function() {
        $http.delete('todoes').success(function() {
            $scope.fetchCarsList();
        });

    };

    $scope.fetchCarsList();
}