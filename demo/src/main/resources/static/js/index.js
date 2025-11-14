const total = document.getElementById("total");
const query = document.getElementById("q");
const rows = document.getElementById("rows");
const empty = document.getElementById("empty");

isToken();
function isToken(){
    if (localStorage.getItem("token") == null)
        location.href = "/login.html";
}

async function fetchTasks() {
    try{
        const token = localStorage.getItem("token");
        const res = await fetch(`/api/v1/tasks/fetchAll`, {
            method: "GET",
            headers: {
                "authorization": "Bearer " + token,
            }
        })
        return await res.json();
    }
    catch(err){
        throw new Error(`Failed to fetch tasks -- error: ${err}`)
    }
}

query.addEventListener("input", async function(e){
        console.log(e.target.value);

        const input = e.target.value;

        const tasks = await fetchTasks();

        const filtering = t => t.task.toLowerCase().includes(input.toLowerCase()) ||
            t.description.toLowerCase().includes(input.toLowerCase());
        const filteredData = tasks.filter(filtering)
        console.log(filteredData);
        renderTasks(filteredData);


})

function renderTasks(tasks){
    rows.innerHTML = '';
    if(!tasks || tasks.length === 0){
        empty.display.style = "block";
        total.textContent = 0;
        return
    }
    empty.style.display = "none";
    total.textContent = tasks.length;
    for(const t of tasks){
        const tr = document.createElement("tr");
        tr.innerHTML =
            `
            <td><button>Update</button></td>
            <td><strong>${escapeHtml(t.task)}</strong></td>
            <td>${escapeHtml(t.description)}</td>
            <td>${t.created_at}</td>
            <td>${t.dueDate}</td>
            <td>${t.priority}</td>
            <td>${t.status}</td>
            <td><button class =" delete-btn " data-id="${t.id}">>Delete</button></td>
        `;
        rows.appendChild(tr);

    }
}
document.addEventListener("click", async function(e){
        const btn = e.target.closest(".delete-btn")
        if(!btn) return;
        const token = localStorage.getItem("token");
        const id = e.target.dataset.id;
        console.log("ID: ", id, "Event: ", e);
        await fetch(`/api/v1/tasks/delete/${id}`, {
            method: "DELETE",
            headers: {
                "authorization": "Bearer " + token,
            }
        })
        location.reload();

    })

document.querySelector(".logout").addEventListener('click', async function(){
    localStorage.removeItem("token");
    location.reload();
})
document.getElementById("new").addEventListener("click", async function(){
    location.href = "/create-task.html"
})
document.getElementById("refresh").addEventListener("click", load)

async function load() {
    const tasks =await fetchTasks();
    renderTasks(tasks)
}
// small utility to  preventXSS
function escapeHtml(s){
    return String(s).replace(/[&<>"']/g, c =>
        (
            {
                '&':'&amp;',
                '<':'&lt;',
                '>':'&gt;',
                '"':'&quot;',
                "'":'&#39;'
            }
                [c]
        )
    );
}
load();
























//
//     // Minimal client-side rendering. Replace `fetchTasks()` URL with your API.
//     const rowsEl = document.getElementById('rows');
//     const totalEl = document.getElementById('total');
//     const emptyEl = document.getElementById('empty');
//     const qEl = document.getElementById('q');
//
//     async function fetchTasks() {
//     try {
//         const token = localStorage.getItem('token');
//         const res = await fetch('/api/v1/tasks/fetchAll', {
//             method: "GET",
//             headers:
//                 {
//                     'Authorization': token ?
//                         ('Bearer ' + token) : ''
//                 }
//     });
//         if (!res.ok)
//             throw new Error('Failed to fetch');
//         return await res.json();
//     } catch(e) {
//         console.error(e);
//         return [];
//         }
//     }
//
//     function formatDate(s) {
//     if(!s) return '-';
//     const d = new Date(s);
//     return d.toLocaleString([], {
//         year:'numeric',
//         month:'short',
//         day:'numeric',
//         hour:'numeric',
//         minute:'numeric'
//         });
//     }
//
//     function priorityLabel(p){
//         return p.toUpperCase();
//     }
//
//     function statusPill(status){
//     const s =   (status).toUpperCase();
//     const cls = s === 'COMPLETED' ? 'pill completed' : 'pill pending';
//     return `<span class="${cls}">${s || 'PENDING'}</span>`;
//     }
//
//     async function deleteTaskById(id){
//         const token = localStorage.getItem('token');
//         console.log("Tasks ID: ",id)
//         const res = await fetch(`/api/v1/tasks/delete/${id}`, {
//             method: "DELETE",
//             headers:
//                 {
//                     'Authorization': token ?
//                         (
//                             'Bearer ' + token
//                         ) : ''
//                 }
//         });
//         if(res.ok){
//             location.reload()
//         } else {
//             console.log("Deletion failed")
//         }
//     }
//
//     function sortByPriority(priority)
//     {
//         const priorityOrder = {
//             "HIGH": 1,
//             "MEDIUM": 2,
//             "LOW": 3
//             }
//     }
//
//     const tableHead = document.querySelector('.table-wrap');
//     tableHead.addEventListener('click', e => {
//         const th = e.target.closest('th');
//         console.log(th)
//         if (!th) return;
//         const key = th.dataset.key;
//         if(!key) return;
//         console.log("key",key,"tablehead: ",th);
//         onHeaderClick(key,th)
//     })
//     let currentsort = {
//             key : null,
//             asc : true,
//         }
//     function onHeaderClick(key,th)
//     {
//         if( currentsort.key === key)
//         {
//             currentsort.asc = !currentsort.asc;
//         }
//         else {
//             currentsort.key = key;
//             currentsort.asc = true;
//         }
//     }
//
//
//     function render(tasks){
//         console.log("Tasks", tasks)
//         const priorityOrder = {
//             "HIGH": 1,
//             "MEDIUM":2,
//             "LOW":3
//         }
//         tasks = tasks.sort((a, b) => {
//             return priorityOrder[a.priority] - priorityOrder[b.priority];
//         })
//         rowsEl.innerHTML = '';
//         if(!tasks || tasks.length === 0){
//             emptyEl.style.display = 'block';
//             totalEl.textContent = 0;
//         return;
//         }
//     emptyEl.style.display = 'none';
//     totalEl.textContent = tasks.length;
//     for(const t of tasks){
//     const tr = document.createElement('tr');
//     tr.innerHTML =
//         `
//         <td>
//             <button class="edit-btn" data-id="${t.id}">️
//                 edit
//             </button>
//         </td>
//         <td><strong>${escapeHtml(t.task || '')}</strong></td>
//         <td>${escapeHtml(t.description || '')}</td>
//         <td>${formatDate(t.created_at)}</td>
//         <td>${formatDate(t.dueDate)}</td>
//         <td>${escapeHtml(priorityLabel(t.priority))}</td>
//         <td>${statusPill(t.status)}</td>
//         <td><button class="btn primary" onclick="deleteTaskById(${t.id})">Delete</td>
// `;
//
//     rowsEl.appendChild(tr);
//     }
// }
//
//     document.addEventListener('click', async function(e)  {
//         const btn = e.target.closest('.logout');
//         if (!btn) return;
//         console.log(btn);
//         localStorage.removeItem('token');
//         window.location.href = "/login.html"
//     });
//
//     document.addEventListener("click", async function (e){
//         const btn = e.target.closest(".edit-btn");
//         console.log("btn value",btn)
//         console.log("Key: ", btn.dataset)
//         if (!btn) return;
//         const id = btn.dataset.id;
//         const token = localStorage.getItem("token");
//         const res = await fetch(`/api/v1/tasks/${id}`, {
//             headers: {
//                 'Authorization': `Bearer ${token}`
//             },
//         });
//         const task = await res.json();
//         document.getElementById("editTask").value = task.task;
//         document.getElementById("editDescription").value = task.description;
//         document.getElementById("editDueDate").value = task.dueDate;
//         document.getElementById("editPriority").value = task.priority;
//         document.getElementById("editStatus").value = task.status;
//         document.getElementById("saveEdit").dataset.id = id;
//         document.getElementById("editModal").style.display = 'flex';
//
//         if (!res.ok)
//             throw new Error('Failed to fetch');
//     });
//
//     document.getElementById("saveEdit").addEventListener("click",
//         async function (){
//         const id = this.dataset.id;
//         const token = localStorage.getItem('token');
//         const body = {
//             task: document.getElementById("editTask").value,
//             description: document.getElementById("editDescription").value,
//             priority: document.getElementById("editPriority").value,
//             status: document.getElementById("editStatus").value,
//             dueDate: document.getElementById("editDueDate").value
//         };
//
//         const res = await fetch(`/api/v1/tasks/update/${id}`, {
//             method: "PUT",
//             headers: {
//                 'Authorization': `Bearer ${token}`,
//                 "Content-Type": "application/json",
//             },
//             body: JSON.stringify(body)
//
//         });
//         console.log("response: ",res.status);
//             if (!res.ok) {
//                 alert("Failed to update task");
//                 return;
//             }
//
//         document.getElementById("editModal").style.display = 'none';
//
//         load();
//         });
//     document.getElementById("closeModal").addEventListener("click",
//         async function (e){
//         document.getElementById("editModal").style.display = 'none';
//     });

//
//     async function load(){
//     const all = await fetchTasks();
//     render(all);
// }
//     document.getElementById('refresh').addEventListener('click', load);
//     qEl.addEventListener('input', async () => {
//     const q = qEl.value.trim().toLowerCase();
//     const all = await fetchTasks();
//     const filtered = all.filter(t =>
//     (t.task||'').toLowerCase().includes(q) ||
//     (t.description||'').toLowerCase().includes(q)
//     );
//     render(filtered);
// });
//
//     document.getElementById('new').addEventListener('click', () => {
//     location.href = '/create-task.html';
// });
//
// load();
