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
  
  myApp.filter('searchFilter', function(){
    return function(items, forWhat) {
      
      var results = [];
      
      angular.forEach(items, function(item){
        console.log('item : ', item);
        console.log('forWhat : ', forWhat);
        var itemName = item.name;
        if(itemName) {
          console.log('item.name.toLowerCase() : ', item.name.toLowerCase());
          console.log('item.name.toLowerCase().indexOf(forWhat) !== -1 : ', item.name.toLowerCase().indexOf(forWhat) !== -1);
          if(item.name.toLowerCase().indexOf(forWhat) !== -1) {
            results.push(item);
          }
        }
      })
      
      return results;
    }
  });

})();
