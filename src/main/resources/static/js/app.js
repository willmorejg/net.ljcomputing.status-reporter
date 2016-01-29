(function() {
  var myApp = angular.module('myApp', [ 'ngRoute', 'ngAnimate', 'ngSanitize', 'ui.bootstrap', 'ui.bootstrap.datepicker', 'ui.bootstrap.tooltip' ])
  
  myApp.config([ '$routeProvider', '$locationProvider',
      function($routeProvider, $locationProvider) {
        $routeProvider.when('/home', {
          templateUrl : 'js/wbs/wbsList.htm',
          controller : 'wbsController'
        }).otherwise({
          redirectTo : 'home'
        });
      } ])
    .controller('dateController', [ '$scope', '$sce', function ($scope, $sce) {
        $scope.pickerTooltip = $sce.trustAsHtml('Click to change date');
        
        $scope.today = function() {
          $scope.dt = new Date();
        };
        $scope.today();
  
        $scope.clear = function() {
          $scope.dt = null;
        };
        
        $scope.picker = {
          opened: false
        };
  
        $scope.openPicker = function() {
          $scope.picker.opened = true;
        };

        $scope.setDate = function(year, month, day) {
          $scope.dt = new Date(year, month, day);
        };

        $scope.dateOptions = {
          formatYear: 'yy',
          startingDay: 1
        };

        $scope.formats = ['MM/dd/yyyy', 'MM-dd-yyyy', 'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
        $scope.format = $scope.formats[0];
        $scope.altInputFormats = ['M!/d!/yyyy'];
  }]);
})();
