package com.internshala.activitylifecycle.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurants")
data class RestaurantEntity(
@PrimaryKey val id : String,
@ColumnInfo(name = "name") val resName: String,
@ColumnInfo(name = "rating") val resRating: String,
@ColumnInfo(name = "cost_for_one") val resCostForOne: String,
@ColumnInfo(name = "image_url") val resImageUrl: String,
)
