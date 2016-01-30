(function() {
  var myApp = angular.module('myApp', [ 
        'ngRoute'
      , 'ngAnimate'
      , 'ngSanitize'
      , 'ui.bootstrap'
      , 'ui.bootstrap.datepicker'
      , 'ui.bootstrap.tooltip'
      , 'angularUtils.directives.dirPagination'
      ]
  )
  
  myApp.config([ '$routeProvider', '$locationProvider',
      function($routeProvider, $locationProvider) {
        $routeProvider
        .when('/home', {
          templateUrl : 'js/wbs/wbsList.htm',
          controller : 'wbsController'
        })
        .when('/contact', {
          templateUrl : 'js/status-reporter/contact.htm'
        })
        .when('/about', {
          templateUrl : 'js/status-reporter/about.htm'
        })
        .otherwise({
          redirectTo : 'home'
        });
      }]);

})();
