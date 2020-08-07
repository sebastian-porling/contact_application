window.addEventListener("load", () => {
    const searchField = document.getElementById("searchField");
    const tableContent = document.getElementById("tableContent");

    searchField.addEventListener("input", (event) => {
        event.preventDefault();
        let searchValue = searchField.value;
        if (searchValue.length) {
            let contacts = search(searchValue);
            if (contacts !== undefined && contacts.length !== 0) {
                updateContacts(contacts);
            }
        }
    });

    function editContact() {

    }

    function addContact() {

    }

    function deleteContact() {

    }

    function search(query) {
        $.getJSON("http://localhost:8080/api/contact/search?query="+query, (data) => {
            console.log(data);
            return data;
        }).fail((err) => console.log("Couldn't contacts ", err));
    }

    function updateContacts(contacts) {
        console.log(contacts);
    }
});