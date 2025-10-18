package com.example.tp_kotlin_grupo_v.domain

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tp_kotlin_grupo_v.domain.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertUsers(userEntities: List<User>)

    @Query("SELECT * FROM user_entity")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM user_entity WHERE id = :id")
    suspend fun getUserById(id: Int): User?

    @Query("select * from user_entity where username= :username")
    suspend fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM user_entity WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM user_entity")
    suspend fun deleteAll()
}