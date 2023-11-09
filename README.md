# Vipps Summer Internship - Technical case - Android

This option for the assignment will be delivered as an Android app.

To save time on setup we have provided you with an empty app project where we have included some dependencies we think you might need. 
You do not have to use our setup though, and you are free to use whatever APIs and libraries you see fit.

## The task  
In this task you will be retrieving information from the [Rest countries API](https://restcountries.com/) and showing it in the app.

### Part 1:  
Using an HTTP GET method, query [https://restcountries.com/v2/name/{name}](https://restcountries.com/v2/name/%7Bname%7D), where you replace _{name}_ with the name of a country of your choice.

Parse the response you get and use it to show the following in the app:
* The country name  
* The country capital  
* The number of alternative spellings of the country name (`altSpellings`)

### Part 2:  
Add a search field and a button to the app so that the user can search for any country they want, and show the data from part 1.

The UI does not have to be advanced, a simple input field with a button and some text fields is enough.
