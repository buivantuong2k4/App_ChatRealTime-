package com.example.appchat.data

import android.os.Parcel
import android.os.Parcelable

data class Messages(
    val sender : String? = "",
    val receiver: String? = "",
    val message: String? = "",
    val time: String? = "",

    ) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Messages> {
        override fun createFromParcel(parcel: Parcel): Messages {
            return Messages(parcel)
        }

        override fun newArray(size: Int): Array<Messages?> {
            return arrayOfNulls(size)
        }
    }
}