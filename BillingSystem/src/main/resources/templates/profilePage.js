const fetchPromise = fetch("http://localhost:8080/profile/Chris12345");
fetchPromise.then(response => {
    return response.json();
}).then(people => {
    const username = document.getElementById("username");
    const balance = document.getElementById("balance");
    username.textContent += people["username"];
    balance.textContent += people["balance"];
    console.log(people);
});