const form = document.getElementById("taskCreation");
isToken();
function isToken(){
    if (localStorage.getItem("token") == null)
        location.href = "/login.html";
}
form.addEventListener("submit", async function(event){
    event.preventDefault();
    const task = document.getElementById("task").value;
    const description = document.getElementById("description").value;
    const dueDate = document.getElementById("dueDate").value;
    console.log("Duedate", dueDate);
    const priority = document.getElementById("priority").value;
    const status = document.getElementById("status").value;
    const userId = localStorage.getItem("userId")
    const data = {
        task : task,
        description : description,
        dueDate : dueDate,
        priority : priority,
        status : status,
        user : {id : userId }
    };
    const token = localStorage.getItem("token");
    console.log("Token: ", token)
    try{
        const response = await fetch("http://localhost:8080/api/v1/tasks/create", {
            method: "POST",
                      headers: {
                        "Content-Type" : "application/json",
                        'Authorization': `Bearer ${token}`
                      },
                      body: JSON.stringify(data)
        });

        const result = await response.json();

        console.log("result: ",result);
        console.log("response: ", response);

        message = document.getElementById("message");
        message.textContent = result.message || "Task Created Successfully";
        location.href = "/index.html";


    }catch(error){
        document.getElementById("message").textContent = "Task Creation failed"
    }


})