# Contact Application
This solution is made by Sebastian Porling and Josefin Salomaa.

## Compile

This project uses maven, so for compiling it is probably easier to import this project into an IDE.
## Execute

Either run this through an IDE, or use the jar file:

```bash
java -jar contact_application-1.jar
```
## Explanation and motivation

This project utilizes **H2 SQL** in memory database, 
we use JPA to create the database and tables and use
the **data.sql** file in resources to generate the **Contacts** table.
Spring Boot finds the **data.sql** file at the start of execution and executes it.

The 'backend' have three controllers, one repository and one data object/entity.
It is the entity that decides how the table **Contacts** will look like.
It has the following properties:

|name|type|property|
|----|----|----|
|id|int (INTEGER)|primary auto|
|name|String (VARCHAR)|non null|
|email|String (VARCHAR)|non null|
|contactNumber|String (VARCHAR)|non null|
|active|boolean|non null|

The **WebController** handles the request for the index page.
It loads active users from the **ContactRepository** and adds them as an attribute.

The **ApiController** is a RestController that has the following methods:

|name|method|return|comment|
|----|----|----|----|
|/contacts|GET|List<Contact>|Gets all active users|
|/contact/delete/{id}|DELETE|void|Sets the active to false on contact by id of it exists|
|/contact/create|PUT|Contact|Saves a new contact to the database and returns the contact object|
|/contact/update|PUT|Contact|Saves the modified contact if it exists to the database|
|/contact/search?query=|GET|List<Contact>|Gets all active contacts where the name, email and/or contact number contans the query string|

Then we have the **BasicErrorController** that redirects the user
to an appropriate error page like 404 or 500.

The class **ContactRepository** has all the methods to query the database.
The method worth mentioning is the **search(String query)** method.
It has a custom query that has been applied with the **@Query** annotation.

For the 'frontend' we generate the index page with thymeleaf the first time it is loaded.
All the actions happens through javascript after that.
The file **fetch_api.js** does most of the work.
It generates a new table when a search happens, modifies the row locally and on the databse.
Adds a new row to the table and the database. Most through jQuery Ajax requests.
So everything happens asynchronously which gives a more smooth experience.
We have event listeners for the search field, search button, and forms.
The forms are in the **add modal** and **edit modal**. A modal is a fancy popup through bootstrap.

To edit the row in the table we use a binary search to find the correct row. 
When searching we just generate a new table and when adding we add a new row to the end of the table.

The delete button removes the HTML object from the parent object and sends the Ajax Request.
