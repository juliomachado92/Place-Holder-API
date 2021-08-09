package com.jmachado.placeholdermenu.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "posts_table")
data class Post (
    @PrimaryKey  @field:SerializedName("id") val id: Int,
    @field:SerializedName("userId") val userId: Int,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("body") val body: String)