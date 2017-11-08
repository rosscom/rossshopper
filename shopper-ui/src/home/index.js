class Index {

    constructor() {
        Index.checkLogin();

        document.querySelector("#loginButton").onclick = this.loginRequest;
    }

    loginRequest() {
        let username = document.querySelector("#userid").value;
        let psw = document.querySelector("#psw").value;

        let authEncodedString = 'Basic ' + window.btoa(username + ':' + psw);

        fetch('http://localhost:8080/shopper/api/auth/login', {headers: {'Authorization': authEncodedString}})
            .then(response => response.text())
            .then(res => Index.saveTokenAndRefresh(res))
            .catch(error => console.error(error));
    }

    static saveTokenAndRefresh(token) {
        localStorage.setItem("token", token);
        Index.checkLogin();
    }

    static checkLogin() {
        if (localStorage.getItem("token") == null){
            document.getElementById("login").style.visibility = "visible";
            document.getElementById("logout").style.visibility = "hidden";
        } else {
            document.getElementById("login").style.visibility = "hidden";
            document.getElementById("logout").style.visibility = "visible";
        }
    }
}

let index = new Index();