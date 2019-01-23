package com.aduran.photos.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.os.Parcel
import android.os.Parcelable
import com.aduran.photos.util.FlickrPhotoTypeConverters

@Entity
data class Album(
    @PrimaryKey(autoGenerate = true) val id: Int, var title: String?, @TypeConverters(
        FlickrPhotoTypeConverters::class
    ) var photos: MutableList<FlickrPhoto>) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        arrayListOf<FlickrPhoto>().apply {
            parcel.readList(this, FlickrPhoto::class.java.classLoader)
        }
    )

    @Ignore
    constructor(
        title: String = "",
        photos: MutableList<FlickrPhoto> = mutableListOf()
    ) : this(0, title, photos)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeList(photos)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Album> {
        override fun createFromParcel(parcel: Parcel): Album {
            return Album(parcel)
        }

        override fun newArray(size: Int): Array<Album?> {
            return arrayOfNulls(size)
        }
    }
}