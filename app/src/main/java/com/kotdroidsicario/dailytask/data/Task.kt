package com.kotdroidsicario.dailytask.data

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kotdroidsicario.dailytask.util.validateTime
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Long = 0L,
    @NonNull
    val title: String,
    @NonNull
    val description: String,
    @NonNull
    val hours: Int,
    @NonNull
    val minutes:Int,
    @NonNull
    val isComplete: Boolean = false
) : Parcelable {
    val time: String
        get() = "${hours.validateTime()}:${minutes.validateTime()}"
}