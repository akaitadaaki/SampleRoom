package jp.three_colors.sampleroom.data.entity

import androidx.room.ColumnInfo

data class ApiAddress(
    @ColumnInfo(name = "state_id") val state: String,
    @ColumnInfo(name = "state_name") val stateName: String,
    val city: String,
    val street: String
)