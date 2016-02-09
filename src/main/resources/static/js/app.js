(function() {
  var myApp = angular.module('myApp', [
    'ui.router'
    , 'ngAnimate'
    , 'ngSanitize'
    , 'ngMessages'
    , 'ui.bootstrap'
    , 'ui.bootstrap.datepicker'
    , 'ui.bootstrap.tooltip'
    , 'angularUtils.directives.dirPagination'
    , 'ngAside'
    , 'dm.stickyNav'
    , 'ui.tree'
    , 'ui.router'
    , 'ui.bootstrap.contextMenu'
    , 'ui.grid'
    , 'ui.grid.pagination'
    , 'ui.grid.expandable'
  ]);

  myApp.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

      $urlRouterProvider.otherwise('/about');

      $stateProvider
        .state('home', {
          url: '/home',
          templateUrl: 'js/status-reporter/about.htm'
        })
        .state('dashboard', {
          url: '/dashboard',
          templateUrl: 'js/wbs/wbsList.htm',
          controller: 'wbsController'
        })
        .state('manage', {
          url: '/manage',
          templateUrl: 'js/wbs/wbsList.htm',
          controller: 'wbsController'
        })
        .state('overview', {
          url: '/overview',
          templateUrl: 'js/status-reporter/overview.htm'
        })
        .state('about', {
          url: '/about',
          templateUrl: 'js/status-reporter/about.htm'
        })
        .state('contact', {
          url: '/contact',
          templateUrl: 'js/status-reporter/contact.htm'
        });
    }
  ]);

  myApp.constant('REST_API', {
    "WBS": {
      "BASE": 'sr/wbs'
    },
    "ACTIVITY": {
      "BASE": '/activity'
    }
  });

  myApp.constant('ALERTS', {
    "UNKNOWN": {
      "MESSAGE": 'An unknown error occured. Refresh the page and try again.'
    }
  });

})();