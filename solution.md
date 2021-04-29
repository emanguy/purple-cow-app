## Purple Cow App

This is a simple spring boot server serving the required API. Included endpoints are:

* GET /item - retrieves the list of all items
* POST /item - creates a new item, request body should be of the format `{"name": "itemName"}`
* GET /item/{id} - retrieves an item by its ID
* PATCH /item/{id} - allows you to update an item's name, body should be in the format `{"name": "newName"}`
* DELETE /item/{id} - deletes an item by its ID

You can run the app by executing `./runPurpleCowApp.sh` which will spin up the database via docker compose and run the 
server. When the server boots it will run the liquibase migrations from the resources folder on the database and start 
runnning the server. The shell script requires you to have Docker and the Java 8 JDK installed on your system. The gradlew
script executed by `runPurpleCowApp.sh` will take care of downloading the appropriate version of gradle and building/running 
the software.

A dockerfile is provided for compiling the project as a docker image. It runs a multi-stage build so Java isn't even required,
you'd just need docker. The software assumes you are running MySQL 8.

By default the app serves on port 3000, but this can be changed by either editing the [application.properties file](src/main/resources/application.properties)
or by providing an environment variable: `SERVER_PORT`. Other configuration options are listed in a following section.

## Persistence Strategies

Purple cow app can either use in-memory data storage or a MySQL database to persist items. You can change the persistence implementation
by doing the following:

1. In [ItemService](src/main/java/me/erittenhouse/purplecowapp/service/ItemService.java), change the `@Qualifier` annotation to "db"
   for database persistence, or "mem" for the in-memory persister.
2. In [application.properties](src/main/resources/application.properties), comment out the mentioned lines so the configuration is correct
   for the persister you've chosen.
   
Note that the database is configured to soft-delete items so they can be recovered in the event of accidental deletion.
   
## Configuration

The following configuration options are available either in the environment or [application.properties](src/main/resources/application.properties), 
depending on where you'd like to configure:

| Environment Variable | application.properties Value | Description |
| --- | --- | --- |
| SERVER_PORT | server.port | The port to serve on |
| DB_USER | db.user | If using MySQL persistence, this is the username of the MySQL user to authenticate with |
| DB_PASSWORD | db.password | If using MySQL persistence, this is the password to authenticate against MySQL with |
| DB_URL | db.url | If using MySQL for persistence, this is the URL for the database. For example, `jdbc:mysql://localhost:3306/purplecowapp` |

## Future Work

Beyond this implementation, here are some things we could add to the app in the future:

* Unit tests. We should create automated tests and run them in CI with something like CircleCI to verify the API continues to work
* Validation, perhaps if we want to verify that someone isn't submitting two items with the same name
* Nullability annotations on both entities and DTOs so it's easy to catch when we should be performing null-checks
* Configuration for the server hostname and perhaps HTTPS credentials, because traffic should be sent securely
* Integrate `runPurpleCowApp.sh` into the gradle file so we can just run a single task and spin up the server and database with one tool
* Add a database procedure triggered daily to clean up the soft-deleted items to manage database storage
