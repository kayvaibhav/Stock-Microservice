document.addEventListener("DOMContentLoaded", function () {
    const stockForm = document.getElementById("stripe-login");
    const stockReduceForm = document.getElementById("stripe-login2");
    const stockShowForm = document.getElementById("stripe-login3");
    const tableBody = document.getElementById("tableBody");

    if (stockForm) {
    stockForm.addEventListener("submit", function (event) {
        event.preventDefault();

        const stockName = document.getElementById("stockName").value;
        const stockPrice = document.getElementById("stockPrice").value;
        const stockQuantity = document.getElementById("stockQuantity").value;

        const stockData = {
            stockName: stockName,
            stockPrice: parseFloat(stockPrice),
            stockQuantity: parseInt(stockQuantity)
        };

        // Assuming you have an API endpoint to handle form submissions
        fetch("http://localhost:8080/stocks/add", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(stockData)
        })
            .then(response => response.json())
            .then(data => {
                showNotification("Your Stock Was Added Successfully !!"); // Display the notification
                stockForm.reset();   // Clear the form
            })
            .catch(error => {
                console.error("Error:", error);
                alert("An error occurred while adding the stock.");
            });
    });
    }
    if (stockReduceForm) {
    stockReduceForm.addEventListener("submit", function (event) {
        event.preventDefault();

        const stockId = document.getElementById("stockId").value;
        const reduceAmount = parseInt(document.getElementById("stockQuantity").value);

        fetch(`http://localhost:8080/stocks/reduce?id=${stockId}&quantity=${reduceAmount}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            }
        })
            .then(response => response.json())
            .then(data => {
                showNotification("Your Stock Was Reduced Successfully !!");
                tableBody.innerHTML = "";

                // Iterate through the JSON object and populate the table
                for (const key in data) {
                    if (data.hasOwnProperty(key)) {
                        const row = document.createElement("tr");
                        const keyCell = document.createElement("td");
                        const valueCell = document.createElement("td");

                        keyCell.textContent = key;
                        valueCell.textContent = data[key];

                        row.appendChild(keyCell);
                        row.appendChild(valueCell);

                        tableBody.appendChild(row);
                    }
                }
            })
    });
    }

    if (stockShowForm) {
        console.log("Inside SHOWForm if condition");
        stockShowForm.addEventListener("submit", function (event) {
            event.preventDefault();

            const stockId = document.getElementById("stockId").value;
            console.log("stockID: ",stockId);

            fetch(`http://localhost:8080/stocks/show/${stockId}`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json"
                }
            })
                .then(response => response.json())
                .then(data => {
                    showNotification("Your Stock Was Found Successfully !!");
                    tableBody.innerHTML = "";

                    // Iterate through the JSON object and populate the table
                    for (const key in data) {
                        if (data.hasOwnProperty(key)) {
                            const row = document.createElement("tr");
                            const keyCell = document.createElement("td");
                            const valueCell = document.createElement("td");

                            keyCell.textContent = key;
                            valueCell.textContent = data[key];

                            row.appendChild(keyCell);
                            row.appendChild(valueCell);

                            tableBody.appendChild(row);
                        }
                    }
                })
        });
        }

    function showNotification(message) {
        console.log("notif");
        const notification = document.getElementById('notification');
        notification.textContent = message;
        notification.style.display = 'block';
        setTimeout(() => {
            notification.style.display = 'none';
        }, 4000); // Display for 3 seconds
    }

});
