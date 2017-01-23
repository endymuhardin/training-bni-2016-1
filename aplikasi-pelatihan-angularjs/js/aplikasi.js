var aplikasi = angular.module('aplikasi', []);

aplikasi.controller('DaftarEmailController', function DaftarEmailController($scope){
	$scope.daftarEmail = [
		"endy@muhardin.com",
		"endy.muhardin@gmail.com"
	];
});

aplikasi.controller('DaftarPesertaController', function DaftarPesertaController($http){
	var $ctrl = this;

	$ctrl.editPeserta = function(p){
		console.log("Edit peserta ");
		console.log(p);
	};

	// get data dari server
	$http.get('http://localhost:8080/api/peserta/').then(function(response) {
        $ctrl.daftarPeserta = response.data.content;
    });
});
