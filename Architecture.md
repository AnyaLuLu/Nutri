# Architecture

Some notes about the architecture. Since we are using Room, we will be using a MVVM architecture for our application. All data-layer components are under the `com.example.nutri.database` package.
- `com.example.nutri.database.NutriDatabase` file - This is our database 
- `com.example.nutri.database.dao` package - This contains our data access objects
- `com.example.nutri.database.repositories` package  - This is akin to a controller in the MVC architecture. It is called by the ViewModel to get data from the database. It gets data from the database via the DAO.  
- `com.example.nutri.database.entities` package - This contains the schema for all of the objects we store in the database. It defines the SQL schemas used by Room

View Models can be found in our `com.example.nutri.ui` package. 