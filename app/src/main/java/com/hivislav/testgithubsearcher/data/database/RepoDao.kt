package com.hivislav.testgithubsearcher.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RepoDao {

    @Query("SELECT * FROM repositories")
    suspend fun getReposList(): List<RepoDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRepo(repoDbModel: RepoDbModel)
}