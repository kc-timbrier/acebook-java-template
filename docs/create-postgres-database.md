# Creating a Postgres database

Make sure Postgres is installed. You can install Postgres from homebrew using

`brew install postgresql`

Make sure the Postgres server is running. If installed with homebrew you can start the server using

`brew services start postgresql`

Note: A service started using homebrew not automatically stop, even when shutting your machine down. If you ever want to stop the server you can use

`brew services stop postgresql`

Once the Postgres server is running you can create a database using

`createdb <some-database-name>`

To delete a database you can use

`dropdb <some-database-name>`