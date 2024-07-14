package com.emanh.mixivivu.model

import android.os.Parcel
import android.os.Parcelable

data class EvaluateModel(
    val oneStar: ArrayList<ReviewModel> = ArrayList(),
    val twoStar: ArrayList<ReviewModel> = ArrayList(),
    val threeStar: ArrayList<ReviewModel> = ArrayList(),
    val fourStar: ArrayList<ReviewModel> = ArrayList(),
    val fiveStar: ArrayList<ReviewModel> = ArrayList()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(ReviewModel.CREATOR) ?: ArrayList(),
        parcel.createTypedArrayList(ReviewModel.CREATOR) ?: ArrayList(),
        parcel.createTypedArrayList(ReviewModel.CREATOR) ?: ArrayList(),
        parcel.createTypedArrayList(ReviewModel.CREATOR) ?: ArrayList(),
        parcel.createTypedArrayList(ReviewModel.CREATOR) ?: ArrayList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(oneStar)
        parcel.writeTypedList(twoStar)
        parcel.writeTypedList(threeStar)
        parcel.writeTypedList(fourStar)
        parcel.writeTypedList(fiveStar)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EvaluateModel> {
        override fun createFromParcel(parcel: Parcel): EvaluateModel {
            return EvaluateModel(parcel)
        }

        override fun newArray(size: Int): Array<EvaluateModel?> {
            return arrayOfNulls(size)
        }
    }

    fun countReview(): Int {
        return oneStar.size + twoStar.size + threeStar.size + fourStar.size + fiveStar.size
    }

    fun calculateAverageRating(): Float {
        val totalStars = (1 * oneStar.size +
                2 * twoStar.size +
                3 * threeStar.size +
                4 * fourStar.size +
                5 * fiveStar.size).toFloat()

        val totalReviews = (oneStar.size + twoStar.size + threeStar.size + fourStar.size + fiveStar.size).toFloat()

        return if (totalReviews == 0f) 0f else totalStars / totalReviews
    }
}