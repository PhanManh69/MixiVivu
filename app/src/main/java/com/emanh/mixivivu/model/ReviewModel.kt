package com.emanh.mixivivu.model

import android.os.Parcel
import android.os.Parcelable

data class ReviewModel(
    val name: String,
    val content: String,
    val date: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(content)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReviewModel> {
        override fun createFromParcel(parcel: Parcel): ReviewModel {
            return ReviewModel(parcel)
        }

        override fun newArray(size: Int): Array<ReviewModel?> {
            return arrayOfNulls(size)
        }
    }
}
