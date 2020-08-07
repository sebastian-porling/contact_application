/* Global variables */
let tableContent;
let searchField;
let searchForm;
let addForm;
let nameField;
let emailField;
let numberField;
let editModal;
let editForm;
const buttons = "<button class=\"btn btn-primary btn-sm\" onclick=\"editContact(this)\">" +
                "<span class=\"oi oi-pencil\" title=\"pencil\" aria-hidden=\"true\"></span></button>\n" +
                "<button class=\"close\" onclick=\"deleteContact(this)\">x</button>";

window.addEventListener("load", () => {
    /* Initialize the global variables.  */
    tableContent = document.getElementById("tableContent");
    searchField = document.getElementById("searchField");
    searchForm = document.getElementById("searchForm");
    addForm = document.getElementById("addForm");
    editForm = document.getElementById("editForm");
    editModal = document.getElementById('editModal');

    /**
     * When the search field input is changed we call the API and reset the search field
     * and display the new contacts in the table.
     */
    searchField.addEventListener("input", (event) => {
        event.preventDefault();
        searchHelper()
    });

    /**
     * When the search form is submitted we call the API and reset the search field
     * and display the new contacts in the table.
     */
    searchForm.addEventListener("submit", (event) => {
        event.preventDefault();
        searchHelper();
        searchField.value = "";
    });

    /**
     * Finds all contacts based on query
     * if query is empty get all contacts.
     */
    function searchHelper() {
        let searchValue = searchField.value;
        if (searchValue.length) {
            search(searchValue);
        } else {
            getAll();
        }
    }

    /**
     * When the add form is submitted we call the API and reset the values and closes the modal.
     */
    addForm.addEventListener("submit", (event) => {
       event.preventDefault();
       nameField = document.getElementById("name");
       emailField = document.getElementById("email");
       numberField = document.getElementById("contactNumber");
       addContact(nameField.value, emailField.value, numberField.value);
       nameField.value = "";
       emailField.value = "";
       numberField.value = "";
       $('#addModal').modal('hide');
    });

    /**
     * When the edit form is submitted we call the API and reset the values and closes the modal.
     */
    editForm.addEventListener("submit", (event) => {
        event.preventDefault();
        const idField = document.getElementById("idField");
        const editName = document.getElementById("editName");
        const editEmail = document.getElementById("editEmail");
        const editNumber = document.getElementById("editContactNumber");
        editContact(idField.value, editName.value, editEmail.value, editNumber.value);
        idField.value = "";
        editName.value = "";
        editEmail.value = "";
        editNumber.value = "";
        $('#editModal').modal('hide');
    });
});

/**
 * Opens up the edit modal and adds the contact info to the fields.
 * @param element
 */
function showEditContact(element) {
    let row = element.parentNode.parentNode;
    let id = row.children[0].innerText;
    let name = row.children[1].innerText;
    let email = row.children[2].innerText;
    let number = row.children[3].innerText;
    $('#editModal').modal('show');
    document.getElementById("idField").value = id;
    document.getElementById("editName").value = name;
    document.getElementById("editEmail").value = email;
    document.getElementById("editContactNumber").value = number;
}

/**
 * Edits a contact on the database and replaces the one in the table if it exists
 * @param id
 * @param name
 * @param email
 * @param contactNumber
 */
function editContact(id, name, email, contactNumber) {
    if(id === "" ||name === "" || email === "" || contactNumber === "") return;
    let dataObject = { 'id': id, 'name': name, 'email': email, 'contactNumber': contactNumber };
    $.ajax({
        url: "http://localhost:8080/api/contact/update",
        type: 'PUT',
        contentType: "application/json",
        data: JSON.stringify(dataObject),
        success: function(data) {
            editRow(data);
        },
        fail: (err) => console.log("Couldn't update contact " + dataObject, err)
    });
}

/**
 * Creates a new contact to the database and adds it to the table
 * @param name
 * @param email
 * @param contactNumber
 */
function addContact(name, email, contactNumber) {
    if(name === "" || email === "" || contactNumber === "") return;
    let dataObject = { 'name': name, 'email': email, 'contactNumber': contactNumber };
    $.ajax({
        url: "http://localhost:8080/api/contact/create",
        type: 'PUT',
        contentType: "application/json",
        data: JSON.stringify(dataObject),
        success: function(data) {
            addToTable(data);
        },
        fail: (err) => console.log("Couldn't add contact " + dataObject, err)
    });
}

/**
 * Deletes a contact from the database and table
 * @param element button of the row
 */
function deleteContact(element) {
    let row = element.parentNode.parentNode;
    let id = element.parentNode.parentNode.children[0].innerText;
    $.ajax({
        url: "http://localhost:8080/api/contact/delete/"+id,
        type: 'DELETE',
        success: function() {
            row.innerHTML = "";
        },
        fail: (err) => console.log("Couldn't delete " + id, err)
    });
}

/**
 * Makes a API request and updates the table based on the search query
 * @param query string
 */
function search(query) {
    $.getJSON("http://localhost:8080/api/contact/search?query="+query, (data) => {
        if (data !== undefined && data.length !== 0) {
            updateContacts(data);
        }
    }).fail((err) => console.log("Couldn't contacts ", err));
}

/**
 * Gets all active contacts
 */
function getAll() {
    $.getJSON("http://localhost:8080/api/contacts", (data) => {
        if (data !== undefined && data.length !== 0) {
            updateContacts(data);
        }
    }).fail((err) => console.log("Couldn't contacts ", err));
}

/**
 * Adds a contact to the table
 * @param contact to be added
 */
function addToTable(contact) {
    let row = generateRow(contact);
    tableContent.appendChild(row);
}

/**
 * Updates the contact table
 * @param contacts list of contacts
 */
function updateContacts(contacts) {
    tableContent.innerHTML = "";
    contacts.forEach(contact => {
        let row = generateRow(contact);
        tableContent.appendChild(row);
    });
}

/**
 * Edits the row of the contact if it exists
 * @param contact to be edited
 */
function editRow(contact) {
    if (searchRow(contact.id, 0, tableContent.children.length-1, contact)){
        console.log("found!");
    }
}

/**
 * Binary Search for finding the correct row,
 * and replaces the row if found.
 * @param x contact id
 * @param start starting point
 * @param end ending point
 * @param contact the contact
 * @returns {boolean} true if found
 */
function searchRow(x, start, end, contact) {
    if (start > end) return false;
    let mid=Math.floor((start + end)/2);
    if (tableContent.children[mid].children[0].innerText==x) {
        tableContent.children[mid].replaceWith(generateRow(contact));
        return true;
    }
    if(tableContent.children[mid].children[0].innerText > x)
        return searchRow(x, start, mid-1, contact);
    else
        return searchRow(x, mid+1, end, contact);
}

/**
 * Generates a row for the contacts table
 * @param contact
 * @returns {HTMLTableRowElement}
 */
function generateRow(contact) {
    /* Create HTML elements */
    let tr = document.createElement('tr');
    let th_id = document.createElement('th');
    th_id.setAttribute("scope", "row")
    let td_name = document.createElement('td');
    let td_email = document.createElement('td');
    let td_number = document.createElement('td');
    let td_actions = document.createElement('td');
    /* Enter values for each column */
    th_id.innerText = contact.id;
    td_name.innerText = contact.name;
    td_email.innerText = contact.email;
    td_number.innerText = contact.contactNumber;
    td_actions.innerHTML = buttons;
    /* Add columns to row */
    tr.appendChild(th_id);
    tr.appendChild(td_name);
    tr.appendChild(td_email);
    tr.appendChild(td_number);
    tr.appendChild(td_actions);
    return tr;
}