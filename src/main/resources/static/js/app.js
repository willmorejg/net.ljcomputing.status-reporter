(function() {
  var myApp = angular.module('myApp', [ 
        'ngRoute'
      , 'ngAnimate'
      , 'ngSanitize'
      , 'ui.bootstrap'
      , 'ui.bootstrap.datepicker'
      , 'ui.bootstrap.tooltip'
      , 'angularUtils.directives.dirPagination'
      , 'ui.tree'
      ]
  );
  
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
  
  myApp.filter('searchFilter', function(){
    return function(items, forWhat) {
      if(!forWhat) {
        return items;
      }
      
      var results = [];

      _.forEach(items, function(item) {
        var s = JSON.stringify(item);
        console.log('s : ', s);
        if(s.indexOf(forWhat) !== -1) {
          results.push(item);
        }
      });
//--
      
      
      return results;
    }
  });
  
  myApp.filter('searchFilterCount', function(){
    return function(items) {
      return items ? items.length : 0;
    }
  });

})();
