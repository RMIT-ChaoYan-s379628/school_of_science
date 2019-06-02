'use strict';


myapp.controller('LoginController', function ($rootScope, $scope, AuthSharedService) {
	$scope.login = function () {
		$rootScope.authenticationError = false;
		AuthSharedService.login(
			$scope.username,
			$scope.password,
		);
		AuthSharedService.getAccount();
	}
})
.controller('LogoutController', function (AuthSharedService) {
	AuthSharedService.logout();
})
.controller('ManageController', function($rootScope, $scope, $http, $location, ManageService) {
	var inviteUrl = "";
	$scope.onLoad = function() {
		var url = $location.$$url;
		if(url.indexOf('User') !== -1){
			$scope.invitieType = "User";
			inviteUrl = "client/inviteUsers";
		} else if(url.indexOf('Client') !== -1){
			$scope.invitieType = "Client";
			inviteUrl = "admin/inviteClients";
		} else if(url.indexOf('Restaurant') !== -1){
			$scope.invitieType = "Restaurant";
			inviteUrl = "admin/inviteRestaurants";
		} else if(url.indexOf('Support') !== -1){
			$scope.invitieType = "Support";
			inviteUrl = "admin/inviteSupport";
		}
		$scope.invities = [""];
		$scope.invitiesSent = false;
	}
	$scope.addAnotherInvitie = function() {
		$scope.invities.push("");
	}
	$scope.removeInvitie = function(index){
		$scope.invities.splice(index, 1);
	}
	$scope.submitInviteRequest = function() {
		$rootScope.loadingAccount = true;
		var body = {
			userIds: $scope.invities	
		};
		$http.post(inviteUrl, body, {})
			.success(function (response) {
				$rootScope.loadingAccount = false;
				$scope.invities = [""];
				$scope.addedInvities = response.inviteStatus;
				$scope.invitiesSent = true;
			});
	}
})
.controller('VerifyController', function($rootScope, $scope, $http, $routeParams, VerifyService, AuthSharedService) {
	$scope.onLoad = function() {
		var mail = $routeParams.mail;
		var token = $routeParams.token;
		$rootScope.loadingAccount = true;
		var body = {
			mail: mail,
			token: token
		};
		$scope.passwordSetSuccess = false;
		$http.post('verify', body, {})
    		.success(function (response) {
	    		$rootScope.loadingAccount = false;
	    		$scope.invalidToken = false;
	    		$scope.verificationErrorMessage = "";
	    		$scope.passwordErrorMessage = "";
	    		if(response.code != 0){
	    			$scope.invalidToken = true;
	    			$scope.verificationErrorMessage = response.message;
	    		}
    	});
	}
	$scope.submit = function() {
		var password = $scope.password;
		var passwordConfirm = $scope.passwordConfirm;
		if(!!!password){
			$scope.passwordErrorMessage = "Password field cannot be empty";
			return;
		}
		if(!!!passwordConfirm){
			$scope.passwordErrorMessage = "Confirm Password field cannot be empty";
			return;
		}
		if(password !== passwordConfirm){
			$scope.passwordErrorMessage = "Passwords donot match";
			return;
		}
		var mail = $routeParams.mail;
		var token = $routeParams.token;
		var body = {
			mail: mail,
			token: token,
			password: password
		}
		$http.post('setPassword', body, {})
		.success(function (response) {
			console.log(response);
    		$rootScope.loadingAccount = false;
    		$scope.passwordSetSuccess = true;
	});
	}
})
.controller('ErrorController', function ($scope, $routeParams) {
	$scope.code = $routeParams.code;
	switch ($scope.code) {
		case "403" :
			$scope.message = "Oops! you have come to unauthorised page."
			break;
		case "404" :
			$scope.message = "Page not found."
			break;
		default:
			$scope.code = 500;
			$scope.message = "Oops! unexpected error"
	}
});