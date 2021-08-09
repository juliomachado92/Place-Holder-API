package com.jmachado.placeholdermenu.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "todos_table")
data class Todos (
    @PrimaryKey @field:SerializedName("id") val id: Int,
    @field:SerializedName("userId") val userId: Int,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("completed") val completed: Boolean)
