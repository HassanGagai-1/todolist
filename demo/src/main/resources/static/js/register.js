const form = document.getElementById("registerForm");

    form.addEventListener("submit", async function(event){
    event.preventDefault(); //stops the page from refreshing

    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const userData = {
        name : name,
        email : email,
        password : password
    };

    try{
        const response  = await fetch("http://localhost:8080/api/v1/auth/register", {
          method: "POST",
          headers: {
            "Content-Type" : "application/json"
          },
          body: JSON.stringify(userData)
        });

        //Step 6: convert response to JSON
        const result = await response.json();

        //Step 7: Show message on page
        const messageEl = document.getElementById("message");
        messageEl.textContent = (result.message || "User registered successfully");
        localStorage.setItem("token", result.token);
        console.log("Token: ", result.token);
       if (response.ok){
           window.location.href = "/login.html"
       }
    }
    catch (error){
        console.log("Error in Registration: ",error);
        document.getElementById("message").textContent = " Registration Failed";
    }
});
