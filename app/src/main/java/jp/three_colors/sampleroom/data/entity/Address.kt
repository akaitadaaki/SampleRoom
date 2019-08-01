package jp.three_colors.sampleroom.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "address_table",
    indices = arrayOf(Index(value = ["zip1", "zip2"]))
)
data class Address(

    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val zip1: String,
    val zip2: String,

    @Embedded
    val apiAddress: ApiAddress
)