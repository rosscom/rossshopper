class Account {

    constructor() {
        document.getElementById("accountTable").style.visibility = localStorage.getItem("token") == null ? "hidden": "visible";
        document.querySelector("#createUser").onclick = this.sendUserCreationRequest;

        this.fetchAccounts();
    }

    fetchAccounts() {
        let token = localStorage.getItem("token");

        fetch('http://localhost:8080/shopper/api/account', {headers: {'Authorization': token}})
            .then(response => {
                if (response.status == 200) {
                    response.json().then(body => this.createTableRows(body));
                } else {
                    console.log("FORBIDDEN...");
                }
            })
            .catch(error => console.error(error));
    }

    sendUserCreationRequest() {
        let username = document.querySelector("#userId").value;
        let psw = document.querySelector("#psw").value;
        let repeatPsw = document.querySelector("#r-psw").value;
        let home = document.querySelector("#home").value;
        let mail = document.querySelector("#mail").value;
        let data = {"userId": username, "password": psw, "choosedHome": home, "mail": mail};

        fetch('http://localhost:8080/shopper/api/account', {
            method: "POST",
            headers: {'Authorization': localStorage.getItem("token"), 'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        })
            .then(response => {
                if (response.status == 201) {
                    new Account().fetchAccounts();
                    

                } else {
                    console.log("FORBIDDEN... ", response);
                }
            })
            .catch(error => console.error(error));
    }

    createTableRows(jsonList) {
        let tBody = document.querySelector("#accounts");

        jsonList.forEach(function (json) {
            let tr = document.createElement("tr");

            let userTd = document.createElement("td");
            userTd.innerText = json.userId;
            tr.appendChild(userTd);

            let mailTd = document.createElement("td");
            mailTd.innerText = json.mail;
            tr.appendChild(mailTd);

            let homeTd = document.createElement("td");
            homeTd.innerText = json.choosedHome;
            tr.appendChild(homeTd);

            tBody.appendChild(tr);
        });
    }
}

let account = new Account();