(function() {
  var myApp = angular.module('myApp', [ 
      'ui.router'
      , 'ngAnimate'
      , 'ngSanitize'
      , 'ui.bootstrap'
      , 'ui.bootstrap.datepicker'
      , 'ui.bootstrap.tooltip'
      , 'angularUtils.directives.dirPagination'
      , 'ui.tree'
      , 'ui.router'
      ]
  );
  
    myApp.config(['$stateProvider', '$urlRouterProvider',
        function($stateProvider, $urlRouterProvider) {
    
    $urlRouterProvider.otherwise('/about');
    
    $stateProvider
      .state('home', {
        url : '/home',
        templateUrl : 'js/wbs/wbsList.htm',
        controller : 'wbsController'
      })
      .state('about', {
        url : '/about',
        templateUrl : 'js/status-reporter/about.htm'
      });
  }]);
  
  myApp.filter('searchFilter', function(){
    return function(items, search) {
      if(!search) {
        return items;
      }

      var forWhat = search.toLowerCase();
      var results = [];

      if(forWhat) {
        _.forEach(items, function(item) {
          var s = JSON.stringify(item).toString().toLowerCase();
          if(s && s.indexOf(forWhat) !== -1) {
            results.push(item);
          }
        });
      }
      
      return results;
    }
  });
  
  myApp.filter('searchFilterCount', function(){
    return function(items) {
      return items ? items.length : 0;
    }
  });

})();
