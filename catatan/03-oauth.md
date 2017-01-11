# OAuth 2.0 #

Beberapa istilah:

* Resource Owner
* Client Application
* Authorization Server
* Resource Server

## Cara Menggunakan Auth Server ##

Beberapa flow yang tersedia:

* Authorization Code : digunakan bila client app adalah aplikasi server side (Java, PHP, Ruby, Python, NodeJS, what have you) dan bisa menyimpan `client_secret` atau lazim juga disebut dengan istilah `API Key`. Bila aplikasi client menggunakan flow ini, kita bisa mengidentifikasi aplikasi mana saja yang mengakses aplikasi kita.
* Implicit : digunakan bila aplikasi client tidak bisa menyimpan `client_secret`. Biasanya karena aplikasinya berjalan di sisi user seperti misalnya aplikasi client-side JavaScript([Angular](https://angular.io), [Vue](https://vuejs.org/), [EmberJS](http://emberjs.com/), dsb).
* User Password : digunakan bila kita mengijinkan user untuk menginputkan username dan passwordnya di aplikasi client. Ini biasanya dilakukan bila aplikasi client kita juga yang membuat, seperti misalnya aplikasi Twitter, Facebook, dsb yang dibuat sendiri oleh Twitter, Facebook, dsb.
* Client Credential : digunakan untuk akses host to host antar aplikasi. Flow ini biasanya digunakan untuk fitur yang tidak spesifik ke user tertentu, misalnya trending topic di social media. Datanya global dan tidak terkait ke user tertentu.

Beberapa cara verifikasi token dari resource server ke authorization server:

* Lewat HTTP request.

### Authorization Code ###

![Flow Authorization Code](img/oauth-authcode.jpg)

* Mendapatkan auth code

        http://localhost:10000/auth/oauth/authorize?client_id=clientauthcode&response_type=code&redirect_uri=http://example.com

* Menukar auth code dengan access token. Misalnya auth code `wfLNoc`

        curl -X POST -vu clientauthcode:123456 http://localhost:10000/auth/oauth/token -H "Accept: application/json" -d "grant_type=authorization_code&code= wfLNoc&redirect_uri=http://example.com"

* Hasilnya seperti ini

   ```json
   {
        "access_token": "adfba26b-a38a-44fe-ab64-7039931de4a9",
        "token_type": "bearer",
        "refresh_token": "3a86bd39-fcf3-4191-af7c-aee8216b4def",
        "expires_in": 43199,
        "scope": "read write"
    }
   ```
* Token yang didapatkan bisa dicek detailnya ke auth server dengan perintah berikut

        curl -X POST -vu clientauthcode:123456 http://localhost:10000/auth/oauth/check_token?token=adfba26b-a38a-44fe-ab64-7039931de4a9

* Hasilnya sebagai berikut

    ```json
    {
        "aud": ["aplikasitraining"],
        "exp": 1484184845,
        "user_name": "endy",
        "authorities": ["EDIT_KELAS", "EDIT_PESERTA", "VIEW_PESERTA", "VIEW_KELAS"],
        "client_id": "clientauthcode",
        "scope": ["read", "write"]
    }
    ```

### Implicit  ###

![Flow Implicit](img/oauth-implicit.jpg)

* Mendapatkan `access_token`

        curl http://localhost:10000/auth/oauth/authorize?client_id=jsclient&response_type=token&scope=write

* Kita akan diredirect ke `http://example.com/#access_token=70322d66-6c76-4fe0-9786-622a9a314261&token_type=bearer&expires_in=86399`, sesuai dengan konfigurasi redirect_uri di konfigurasi client.

* `access_token` bisa diparse langsung dari alamat URL tersebut.

### User Password ###

![Flow User Password](img/oauth-user-password.jpg)

* Mendapatkan `access_token`

        curl -X POST -vu clientapp:123456 http://localhost:10000/auth/oauth/token -H "Accept: application/json" -d "client_id=clientapp&grant_type=password&username=endy&password=1234"

* Perhatikan bahwa pada flow ini, kita langsung mengirim username dan password ke auth server. Artinya, user _menitipkan_ username/passwordnya kepada kita. Berbeda dengan flow sebelumnya yang mengharuskan user memasukkan sendiri username/passwordnya ke auth server.

### Client Credential ###

![Flow Client Credential](img/oauth-client-cred.jpg)

* Mendapatkan `access_token`

        curl -X POST -vu clientcred:123456 http://localhost:10000/auth/oauth/token -H "Accept: application/json" -d "client_id=clientcred&grant_type=client_credentials"

* Hasilnya seperti ini

    ```json
    {
      "access_token" : "75c5d29b-0b1d-4bc6-a999-896331d625b0",
      "token_type" : "bearer",
      "expires_in" : 43199,
      "scope" : "trust"
    }
    ```

* Perhatikan bahwa di flow ini, `client_id` dan `client_secret` langsung ditukarkan dengan `access_token`. Tidak ada keterlibatan user pada flow ini.

* Bila kita cek tokennya

        curl -X POST -vu clientcred:123456 http://localhost:10000/auth/oauth/check_token?token=75c5d29b-0b1d-4bc6-a999-896331d625b0

* Hasilnya seperti ini

    ```json
    {
      "aud" : [ "aplikasitraining" ],
      "scope" : [ "trust" ],
      "exp" : 1484186350,
      "authorities" : [ "CLIENT" ],
      "client_id" : "clientcred"
    }
    ```

## JSON Web Token ##

Tujuannya adalah supaya auth server tidak terus-menerus ditanyai untuk keperluan :

* verifikasi token
* meminta informasi tentang user

JWT adalah token yang berisi informasi tentang user dan memiliki digital signature sehingga bisa diverifikasi bahwa isinya tidak diubah ditengah jalan.

Konfigurasinya di Spring Security dilakukan dengan mengubah baris berikut


```java
@Override
public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints
    .tokenStore(new InMemoryTokenStore())
    .authenticationManager(authenticationManager);
}
```

menjadi seperti ini

```java
@Override
public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints
    .accessTokenConverter(jwtAccessTokenConverter())
    .authenticationManager(authenticationManager);
}
```

Tentunya kita harus sediakan `jwtAccessTokenConverter` sebagai berikut

```java
@Bean
public JwtAccessTokenConverter jwtAccessTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setSigningKey("123"); // gunakan symmetric key
    return converter;
}
```

Konfigurasi di atas dilakukan dengan `symmetric key`. Jadi di aplikasi resource server juga harus memiliki `key` yang sama (yaitu `123`) untuk bisa memverifikasi signature dari token JWT tersebut.

Setelah dilakukan perubahan, token yang kita dapatkan menjadi seperti ini

```json
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXBsaWthc2l0cmFpbmluZyJdLCJ1c2VyX25hbWUiOiJlbmR5Iiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTQ4NDE4NzYzNiwiYXV0aG9yaXRpZXMiOlsiRURJVF9LRUxBUyIsIkVESVRfUEVTRVJUQSIsIlZJRVdfUEVTRVJUQSIsIlZJRVdfS0VMQVMiXSwianRpIjoiNDRjZDhhYTctZDNiYy00N2RmLTg5MGItMWQ3MTcyZWE4YjJiIiwiY2xpZW50X2lkIjoiY2xpZW50YXBwIn0.afIrpeD_IB-olXcv416MNMFyT-JMFCz8w639_r-V8bg",
    "token_type": "bearer",
    "expires_in": 43199,
    "scope": "read write",
    "jti": "44cd8aa7-d3bc-47df-890b-1d7172ea8b2b"
}
```

JWT ini bisa diverifikasi di website [jwt.io](https://jwt.io/) seperti ini

![Verifikasi JWT](img/screenshot-jwtio.png)
