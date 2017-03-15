class App {

    constructor() {
        this.trRows = document.querySelector("#rows");
        this.pingServer();
    }

    pingServer() {
        fetch('http://localhost:8080/shopper/api/ping')
            .then(response => response.json())
            .then(json => this.createTableRows(json))
            .catch(error => console.error(error));
    }

    createTableRows(jsonObj) {
        console.log(jsonObj);
        let dbTr = document.createElement("td");
        dbTr.innerText = jsonObj.databaseConnection;
        this.trRows.appendChild(dbTr);

        let timeTr = document.createElement("td");
        timeTr.innerText = jsonObj.systemTime;
        this.trRows.appendChild(timeTr);

        let envTr = document.createElement("td");
        envTr.innerText = jsonObj.environment;
        this.trRows.appendChild(envTr);
    }

}

let app = new App();
