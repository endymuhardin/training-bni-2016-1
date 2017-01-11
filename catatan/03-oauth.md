# OAuth 2.0 #

Beberapa istilah:

* Resource Owner
* Client Application
* Authorization Server
* Resource Server

## Cara Menggunakan Auth Server ##

Beberapa flow yang tersedia:

* Authorization Code
* Implicit
* User Password
* Client Credential

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


### Implicit  ###

![Flow Implicit](img/oauth-implicit.jpg)

### User Password ###

![Flow User Password](img/oauth-user-password.jpg)

### Client Credential ###

![Flow Client Credential](img/oauth-client-cred.jpg)
