show('dashboard');


// myVar = setInterval(display, 1000)


//switching pages
function show(param_div_id) {
    if (param_div_id === 'dashboard') {
        document.getElementById('dashboard').style.display = 'inline';
        document.getElementById('update').style.display = 'none';
    }
    if (param_div_id === 'update') {
        document.getElementById('update').style.display = 'inline';
        document.getElementById('dashboard').style.display = 'none';
    }


}


//update page
let tbodyEl = document.querySelector('#add_input');
let tableEl = document.querySelector('#admin_table');

function assign_name(tbody, table_name) {
    tbodyEl = document.querySelector(tbody);
    tableEl = document.querySelector(table_name);
    tableEl.addEventListener("click", onDeleteRow);
}

function onAddWebsite() {
    let count = tableEl.rows.length;
    let row = tbodyEl.insertRow();
    let names = "name" + count
    row.insertCell(0).innerHTML = count.toString()
    row.insertCell(1).innerHTML = '<input type="text" name={{names}} style="background: #ffffff" required>'
    row.insertCell(2).innerHTML = '<input type="text" name="city" style="background: #ffffff" required>'
    row.insertCell(3).innerHTML = '<input type="text" name="Latitude" style="background: #ffffff" required>'
    row.insertCell(4).innerHTML = '<input type="text" name="Longitude" style="background: #ffffff" required>'
    row.insertCell(5).innerHTML = '<input type="email" name="Address" style="background: #ffffff" required>'
    row.insertCell(6).innerHTML = '<button type="button"  class="deleteBtn">Delete</button>'
}

function onDeleteRow(e) {
    console.log(e)
    if (!e.target.classList.contains("deleteBtn")) {
        return;
    }
    const btn = e.target;
    btn.closest("tr").remove();
}


//display data
fetch('/display_data')
    .then(function (response) {
        return response.json();
    }).then(function (data) {
    for (let cnt = 0; cnt < data['data'].length; cnt++)
        current_display(data['data'].at(cnt));
});

function current_display(data) {
    const row = document.querySelector('#show_details').insertRow();
    row.insertCell(0).innerHTML = data.ID
    row.insertCell(1).innerHTML = data.Name
    row.insertCell(2).innerHTML = data.City
    row.insertCell(3).innerHTML = data.Latitude
    row.insertCell(4).innerHTML = data.Longitude
    row.insertCell(5).innerHTML = data.Address
}

