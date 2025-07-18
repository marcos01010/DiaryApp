package com.example.diaryapp.model

import androidx.room.PrimaryKey
import com.example.diaryapp.util.toRealmInstant
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId
import java.time.Instant


class Diary: RealmObject {
//    @PrimaryKey
//    var _id: ObjectId = ObjectId.invoke()
    var _id: String = ""
    var ownerId: String = ""
    var mood: String = Mood.Neutral.name
    var title: String = ""
    var description: String = ""
    var images: RealmList<String> = realmListOf()
    var date: RealmInstant = Instant.now().toRealmInstant()

    override fun toString(): String {
        return "Diary(title='$title', _id='$_id')"
    }
}