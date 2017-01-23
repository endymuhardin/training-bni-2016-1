# Aplikasi AngularJS #

Cara menjalankan :

1. Install dulu aplikasi NodeJS `http-server`

		npm install http-server -g

2. Masuk ke folder yang ada `index.html`

		cd aplikasi-pelatihan-angularjs/src/main/resources/static

3. Jalankan `http-server` di port 20000

		http-server -p 20000

4. Gunakan opsi `-P` untuk membuat proxy, URL yang tidak dapat ditangani akan diforward kesana

		http-server -p 20000 -P http://localhost:8080

	Output :

		Starting up http-server, serving ./
		Available on:
		http://127.0.0.1:20000
		http://192.168.43.164:20000
		Unhandled requests will be served from: http://localhost:8080
		Hit CTRL-C to stop the server
