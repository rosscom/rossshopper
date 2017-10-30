class Auth {

    setToken(token) {
        if(token.startsWith('Auth-shopper')) {
          this.token = token;

          new App().pingServer(token);
        } else {
           console.log("FORBIDDEN...");                                     
        }
    }

    encode(strVal) {
        return window.btoa(strVal);
    }

}