var aplikasi = angular.module('aplikasi', []);

aplikasi.controller('DaftarEmailController', function DaftarEmailController($scope){
	$scope.daftarEmail = [
		"endy@muhardin.com",
		"endy.muhardin@gmail.com"
	];
});

aplikasi.controller('DaftarPesertaController', function DaftarPesertaController($scope){
	$scope.daftarPeserta = [
		{nama: "Peserta 001", email: "p001@gmail.com", nomorHandphone: "0876543211", tanggalLahir: new Date()},
		{nama: "Peserta 002", email: "p002@gmail.com", nomorHandphone: "0876543212", tanggalLahir: new Date()},
		{nama: "Peserta 003", email: "p003@gmail.com", nomorHandphone: "0876543213", tanggalLahir: new Date()},
		{nama: "Peserta 004", email: "p004@gmail.com", nomorHandphone: "0876543214", tanggalLahir: new Date()},
		{nama: "Peserta 005", email: "p005@gmail.com", nomorHandphone: "0876543215", tanggalLahir: new Date()}
	];

	$scope.editPeserta = function(p){
		console.log("Edit peserta ");
		console.log(p);
		$scope.showModal = true;
	};
});
