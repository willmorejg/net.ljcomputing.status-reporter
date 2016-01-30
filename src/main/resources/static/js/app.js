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
      
      angular.forEach(items, function(item){
        var itemName = item.name;
        if(itemName) {
          if(item.name.toLowerCase().indexOf(forWhat) !== -1) {
            results.push(item);
          }
        }
      })

      return results;
    }
  });

})();
