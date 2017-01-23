var aplikasi = angular.module('aplikasi', []);

aplikasi.controller('DaftarEmailController', function DaftarEmailController($scope){
	$scope.daftarEmail = [
		"endy@muhardin.com",
		"endy.muhardin@gmail.com"
	];
});
