
const form1 = document.getElementById("loginForm");
form1.addEventListener("submit", async function(event){
    event.preventDefault();
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    console.log("email: ", email);
    console.log("password: ",password);

    const userData = {
        email : email,
        password : password
    };
    try {
        const response = await fetch("http://localhost:8080/api/v1/auth/authenticate",{
            method: "POST",
            headers: {
            "Content-Type" : "application/json"
            },
            body: JSON.stringify(userData)
        });
        const result = await response.json();
        localStorage.setItem("token", result.token);
        localStorage.setItem("userId", result.userId);
        console.log("Token: ", result.token)
        console.log("Result: ", result);
        const message = document.getElementById("message");
        message.innerText = result.message || "User LogedIn Successfully";
       if(response.ok){
               window.location.href = "/index.html"
           }
    }
    catch(error)
    {
            console.log("Error in Login",error);
            document.getElementById("message").innerText = "login failed";
    }
});