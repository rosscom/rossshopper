class App {

    constructor() {
        console.log("initilized...");
        this.pingServer();
    }

    pingServer() {
        fetch('http://localhost:8080/shopper/api/ping')
            .then(response => response.json())
            .then(json => console.log(json))
            .catch(error => console.error(error));
    }

}

let app = new App();
app.pingServer();
