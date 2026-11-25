package com.example.checkinpro

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BDLocalService(context: Context?) :
    SQLiteOpenHelper(context, "CheckInProBD", null, 2) {

    private val creation = """
        CREATE TABLE users (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            fullName TEXT,
            email TEXT UNIQUE,
            password TEXT,
            role TEXT
        )
    """.trimIndent()

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(creation)

        // Compte Admin Système par défaut
        val adminValues = ContentValues().apply {
            put("fullName", "Admin Système")
            put("email", "admin@checkinpro.com")
            put("password", "123456")
            put("role", "ADMIN")
        }
        db?.insert("users", null, adminValues)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun addUser(user: User): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("fullName", user.fullName)
            put("email", user.email)
            put("password", user.password)
            put("role", user.role)
        }
        return db.insert("users", null, values)
    }

    fun getAllUsers(): MutableList<User> {
        val db = this.readableDatabase
        val usersList = mutableListOf<User>()
        val cursor: Cursor = db.rawQuery("SELECT * FROM users", null)

        if (cursor.moveToFirst()) {
            do {
                val u = User(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    fullName = cursor.getString(cursor.getColumnIndexOrThrow("fullName")),
                    email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    password = cursor.getString(cursor.getColumnIndexOrThrow("password")),
                    role = cursor.getString(cursor.getColumnIndexOrThrow("role"))
                )
                usersList.add(u)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return usersList
    }

    fun getUserById(id: Int): User? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users WHERE id = ?", arrayOf(id.toString()))
        var user: User? = null
        if (cursor.moveToFirst()) {
            user = User(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                fullName = cursor.getString(cursor.getColumnIndexOrThrow("fullName")),
                email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
                password = cursor.getString(cursor.getColumnIndexOrThrow("password")),
                role = cursor.getString(cursor.getColumnIndexOrThrow("role"))
            )
        }
        cursor.close()
        return user
    }

    fun checkLogin(email: String, password: String): User? {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM users WHERE email = ? AND password = ?",
            arrayOf(email, password)
        )
        var user: User? = null
        if (cursor.moveToFirst()) {
            user = User(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                fullName = cursor.getString(cursor.getColumnIndexOrThrow("fullName")),
                email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
                password = cursor.getString(cursor.getColumnIndexOrThrow("password")),
                role = cursor.getString(cursor.getColumnIndexOrThrow("role"))
            )
        }
        cursor.close()
        return user
    }

    fun updateUser(user: User): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("fullName", user.fullName)
            put("email", user.email)
            put("password", user.password)
            put("role", user.role)
        }
        return db.update("users", values, "id = ?", arrayOf(user.id.toString()))
    }

    fun deleteUser(id: Int): Int {
        val db = this.writableDatabase
        return db.delete("users", "id = ?", arrayOf(id.toString()))
    }
}