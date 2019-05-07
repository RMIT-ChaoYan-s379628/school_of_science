'use strict';

var myapp = angular
    .module('myApp', ['ngResource', 'ngRoute', 'swaggerUi', 'http-auth-interceptor', 'ngAnimate', 'angular-spinkit']);


myapp.constant('USER_ROLES', {
    all: '*',
    admin: 'ADMIN',
    user: 'ROLE_USER',
    client: 'ROLE_CLIENT',
    support: 'ROLE_SUPPORT',
    restaurant: 'ROLE_RESTAURANT'
});

myapp.config(function ($routeProvider, USER_ROLES) {
    
    $routeProvider.when("/home", {
        templateUrl: "partials/home.html",
        access: {
            loginRequired: true,
            authorizedRoles: [USER_ROLES.all]
        }
    }).when('/', {
        redirectTo: '/home',
    }).when('/login', {
        templateUrl: 'partials/login.html',
        controller: 'LoginController',
        access: {
            loginRequired: false,
            authorizedRoles: [USER_ROLES.all]
        }
    }).when('/loading', {
        templateUrl: 'partials/loading.html',
        access: {
            loginRequired: false,
            authorizedRoles: [USER_ROLES.all]
        }
    }).when("/logout", {
        template: "",
        controller: "LogoutController",
        access: {
            loginRequired: false,
            authorizedRoles: [USER_ROLES.all]
        }
    }).when("/admin/manageClients", {
        templateUrl: "partials/manage.html",
        controller: "ManageController",
        access: {
            loginRequired: true,
            authorizedRoles: [USER_ROLES.admin]
        }
    }).when("/admin/manageRestaurant", {
        templateUrl: "partials/manage.html",
        controller: "ManageController",
        access: {
            loginRequired: true,
            authorizedRoles: [USER_ROLES.admin]
        }
    }).when("/admin/manageSupport", {
        templateUrl: "partials/manage.html",
        controller: "ManageController",
        access: {
            loginRequired: true,
            authorizedRoles: [USER_ROLES.admin]
        }
    }).when("/client/manageUsers", {
        templateUrl: "partials/manage.html",
        controller: "ManageController",
        access: {
            loginRequired: true,
            authorizedRoles: [USER_ROLES.admin, USER_ROLES.client]
        }
    }).when("/verify", {
        templateUrl: "partials/verify.html",
        controller: "VerifyController",
        access: {
            loginRequired: false,
            authorizedRoles: [USER_ROLES.all]
        }
    }).when("/error/:code", {
        templateUrl: "partials/error.html",
        controller: "ErrorController",
        access: {
            loginRequired: false,
            authorizedRoles: [USER_ROLES.all]
        }
    }).otherwise({
        redirectTo: '/error/404',
        access: {
            loginRequired: false,
            authorizedRoles: [USER_ROLES.all]
        }
    });
});

myapp.run(function ($rootScope, $location, $http, AuthSharedService, Session, USER_ROLES, $q, $timeout) {
    
    delete $http.defaults.headers.common['X-Requested-With'];

    $rootScope.$on('$routeChangeStart', function (event, next) {
        if(next.originalPath === "/login" && $rootScope.authenticated) {
            event.preventDefault();
        } else if (next.access && next.access.loginRequired && !$rootScope.authenticated) {
            event.preventDefault();
            //$rootScope.$broadcast("event:auth-loginRequired", {});
        } else if (next.access && !AuthSharedService.isAuthorized(next.access.authorizedRoles)) {
            event.preventDefault();
            $rootScope.$broadcast("event:auth-forbidden", {});
        }
    });

    $rootScope.$on('$routeChangeSuccess', function (scope, next, current) {
        $rootScope.$evalAsync(function () {
            $.material.init();
        });
    });

    // Call when the the client is confirmed
    $rootScope.$on('event:auth-loginConfirmed', function (event, data) {
        console.log('login confirmed start ' + data);
        $rootScope.loadingAccount = false;
        var nextLocation = ($rootScope.requestedUrl ? $rootScope.requestedUrl : "/home");
        var delay = ($location.path() === "/loading" ? 1500 : 0);

        $timeout(function () {
            $rootScope.authenticated = true;
            $location.path(nextLocation).replace();
        }, delay);

    });

    // Call when the 401 response is returned by the server
    $rootScope.$on('event:auth-loginRequired', function (event, data) {
    	if($location.$$path.indexOf("verify") !== -1){
    		return;
    	}
        if ($rootScope.loadingAccount && data.status !== 401) {
            $rootScope.requestedUrl = $location.path()
            $location.path('/loading');
        } else {
            $rootScope.authenticated = false;
            $rootScope.loadingAccount = false;
            $location.path('/login');
        }
    });

    // Call when the 403 response is returned by the server
    $rootScope.$on('event:auth-forbidden', function (rejection) {
        $rootScope.$evalAsync(function () {
            $location.path('/error/403').replace();
        });
    });

    // Call when the user logs out
    $rootScope.$on('event:auth-loginCancelled', function () {
        $location.path('/login').replace();
    });

    // Get already authenticated user account
    AuthSharedService.getAccount();
});