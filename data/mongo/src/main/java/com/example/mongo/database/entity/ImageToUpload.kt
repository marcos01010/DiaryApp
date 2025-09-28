package com.example.mongo.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.util.Constants

@Entity(tableName = Constants.IMAGE_TO_UPLOAD_TABLE)
data class ImageToUpload(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val remoteImagePath: String,
    val imageUri: String,
    val sessionUri: String
)
