'use strict';

myapp.service('Session', function () {
    this.create = function (data) {
        this.login = data.userId;
        this.userRole = data.role;
    };
    this.invalidate = function () {
        this.login = null;
        this.userRole = null;
    };
    return this;
});


myapp.service('AuthSharedService', function ($rootScope, $http, $resource, authService, Session) {
    return {
        login: function (userName, password) {
            var config = {
                ignoreAuthModule: 'ignoreAuthModule',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            };
            $http.post('authenticate', $.param({
                username: userName,
                password: password,
            }), config)
            	.success(function (data, status, headers, config) {
            		authService.loginConfirmed(data);
            	})
            	.error(function (data, status, headers, config) {
                    $rootScope.authenticationError = true;
                    Session.invalidate();
            	});
        },
        getAccount: function () {
            $rootScope.loadingAccount = true;
            $http.get('security/account', '', {})
                .then(function (response) {
                    authService.loginConfirmed(response.data);
                    $rootScope.account = {};
                    $rootScope.account.username = response.data.userId;
                    $rootScope.account.role = response.data.role;
                });
        },
        isAuthorized: function (authorizedRoles) {
        	if (authorizedRoles == '*') {
        		return true;
        	}
            var isAuthorized = false;
            if(!!$rootScope.account){
            	for(var i = 0 ; i < authorizedRoles.length ; i++){ 
            		if(authorizedRoles[i] == $rootScope.account.role){
            			isAuthorized = true;
            			break;
            		}
            	}
            }
            return isAuthorized;
        },
        logout: function () {
            $rootScope.authenticationError = false;
            $rootScope.authenticated = false;
            $rootScope.account = null;
            $http.get('logout');
            Session.invalidate();
            authService.loginCancelled();
        }
    };
});

myapp.service('ManageService', function($rootScope, $http) {
	return {
		invite: function(invities, url) {
			$rootScope.loadingAccount = true;
			var body = {
				userIds: invities	
			};
			$http.post(url, body, {})
				.success(function (response) {
				});
		}
	}
})

myapp.service('VerifyService', function($rootScope, $http) {
	return{
	}
})




