class App {

    constructor() {
        document.querySelector("#loginButton").onclick = this.loginRequest;
    }

    pingServer(token) {
        fetch('http://localhost:8080/shopper/api/ping', {headers: {'Authorization': token}})
            .then(response => {
                if (response.status == 200) {
                    response.json().then(body => this.createTableRows(body));
                } else {
                    console.log("FORBIDDEN...");
                }
            })
            .catch(error => console.error(error));
    }

    createTableRows(jsonObj) {
        let trRows = document.querySelector("#rows");

        let dbTr = document.createElement("td");
        dbTr.innerText = jsonObj.databaseConnection;
        trRows.appendChild(dbTr);

        let timeTr = document.createElement("td");
        timeTr.innerText = jsonObj.systemTime;
        trRows.appendChild(timeTr);

        let envTr = document.createElement("td");
        envTr.innerText = jsonObj.environment;
        trRows.appendChild(envTr);
    }

    loginRequest() {
        let auth = new Auth();

        let username = document.querySelector("#userName").value;
        let psw = document.querySelector("#psw").value;

        let authEncodedString = 'Basic ' + auth.encode(username + ':' + psw);

        fetch('http://localhost:8080/shopper/api/auth/login', {headers: {'Authorization': authEncodedString}})
            .then(response => response.text())
            .then(res => auth.setToken(res))
            .catch(error => console.error(error));
    }
}

let app = new App();
