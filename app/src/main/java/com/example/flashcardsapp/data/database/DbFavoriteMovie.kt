package com.example.flashcardsapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class DbFavoriteDeck(
    @PrimaryKey val id: Int, val name: String, val size: Int,
)
