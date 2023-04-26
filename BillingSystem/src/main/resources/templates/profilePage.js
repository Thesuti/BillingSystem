console.log("test");


fetch("http://localhost:8080/profile/1").then(res => {
    res.text();
    console.log(res.status);
}).catch(err => err.text());