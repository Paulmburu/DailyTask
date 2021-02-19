package com.kotdroidsicario.dailytask.data

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kotdroidsicario.dailytask.util.validateTime
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey()
    @ColumnInfo(name = "entryid") var id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "hours") val hours: Int,
    @ColumnInfo(name = "minutes") val minutes:Int,
    @ColumnInfo(name = "completed") val isComplete: Boolean = false
) : Parcelable {
    val time: String
        get() = "${hours.validateTime()}:${minutes.validateTime()}"
}