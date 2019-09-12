package com.restapi.application.database

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.*
import java.util.*

class DatabaseHandler {
    private val logger: Logger = LoggerFactory.getLogger(DatabaseHandler::class.java)
    private val databasePresets = DatabaseHandler::class.java.classLoader.getResourceAsStream("application.properties")
    private lateinit var conn: Connection
    private lateinit var statement: Statement
    private lateinit var userTable: String


    fun connect() {
        val properties = Properties()
        properties.load(databasePresets)

        val url = properties.getProperty("spring.datasource.url").toString()
        val username = properties.getProperty("spring.datasource.username").toString()
        val password = properties.getProperty("spring.datasource.password").toString()
        userTable = properties.getProperty("spring.datasource.userTable")

        try {
            logger.info("Connecting to database......")
            conn = DriverManager.getConnection(url, username, password)
            logger.info("Connected to database")
            statement = conn.createStatement()
            userTable = properties.getProperty("spring.datasource.userTable")
            checkTableExistence(tableName = userTable)
        } catch (ex: Exception) {
            logger.error("${ex.message}")
        }
    }

    private fun checkTableExistence(tableName: String) {
        try {
            val metaData = conn.metaData
            val resultSet = metaData.getTables(null, null, tableName, arrayOf("TABLE"))

            if (!resultSet.next()) {
                logger.info("Table $tableName not created yet")
                createUserTable()
                logger.info("Adding the default licence")

            } else {
                logger.info("Table $tableName exists")
            }
        }catch(ex: Exception){
            logger.error(ex.message)
        }
    }

    //User Functions
    private fun createUserTable() {
        try {
            val query = "CREATE TABLE $userTable" +
                    "(id INTEGER not NULL, " +
                    " name VARCHAR(255), " +
                    " surname VARCHAR(255), " +
                    " pin INTEGER not NULL, " +
                    " PRIMARY KEY ( id ))"

            statement.executeUpdate(query)
            logger.info("Table $userTable created")
        }catch(ex: Exception){
            logger.error(ex.message)
        }
    }
/*
    fun addUser(user: User) {
        try {
            val query = "INSERT INTO $userTable VALUES ('${user.getName()}', " +
                    "'${user.getSurname()}', '${user.getPin()}')"
        }catch(ex: Exception){
            logger.error(ex.message)
        }
    }
*/
    private fun getUserById(id: String): ResultSet {
            val query = "SELECT * FROM $userTable WHERE id = $id"
            return statement.executeQuery(query)
    }

    private fun getUserByName(name: String): ResultSet {
            val query = "SELECT * FROM $userTable WHERE name = $name"
            return statement.executeQuery(query)
    }

    private fun getUserBySurname(surname: String): ResultSet? {
            val query = "SELECT * FROM $userTable WHERE surname = $surname"
            return statement.executeQuery(query)
    }

}