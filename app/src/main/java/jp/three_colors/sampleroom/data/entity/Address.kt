package jp.three_colors.sampleroom.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address_table")
data class Address(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "zip1") val zip1: String,
    @ColumnInfo(name = "zip2") val zip2: String,
    @ColumnInfo(name = "stateId") val stateId: Int,
    @ColumnInfo(name = "stateName") val stateName: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "street") val street: String
)