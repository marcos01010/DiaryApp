package com.example.diaryapp.data.repository

import android.util.Log
import com.example.diaryapp.model.Diary
import com.example.diaryapp.model.Mood
import com.example.diaryapp.util.Constants
import com.example.diaryapp.model.RequestState
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

object MongoDB: MongoRepository {
    private val app = App.Companion.create(Constants.APP_ID)
    private val user = app.currentUser
//    private lateinit var realm: Realm

    init {
        configureTheRealm()
    }

    override fun configureTheRealm() {
//        if (user != null) {
//            val config = SyncConfiguration.Builder(user, setOf(Diary::class))
//                .initialSubscriptions { sub ->
//                    add(
//                        query = sub.query<Diary>(query = "ownerId == $0", user.id),
//                        name = "User's Diaries"
//                    )
//                }
////                .log(LogLevel.ALL)
//                .build()
//            realm = Realm.open(config)
//        }
    }

    override fun getAllDiaries(): Flow<Diaries> {
        return if (user != null){
            try {
//                realm.query<Diary>(query = "ownerId == $0", user.id)
//                    .sort(property = "date", sortOrder = Sort.DESCENDING)
//                    .asFlow()
//                    .map { result ->
//                        RequestState.Success(
//                            data = result.list.groupBy {
//                                it.date.toInstant()
//                                    .atZone(ZoneId.systemDefault())
//                                    .toLocalDate()
//                            }
//                        )
//                    }

                val result = HashMap<LocalDate, List<Diary>>()


                result[LocalDate.parse("2023-01-05")] = listOf(
                    Diary().apply {
                        _id = "1"
                        title = "testetitle1"
                        description = "testedescription1"
                        images = realmListOf(
                            "https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on","https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on",
                            "https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on","https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on",
                            "https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on","https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on",
                            "https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on","https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on",
                            "https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on","https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on",
                            "https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on","https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on",
                            "https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on","https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on"
                        )
                    },
                    Diary().apply {
                        _id = "2"
                        title = "testetitle2"
                        description = "testedescription2"
                        images = realmListOf(
                            "https://vetores.org/wp-content/uploads/udemy.png",
                            "https://vetores.org/wp-content/uploads/udemy.png",
                            "https://vetores.org/wp-content/uploads/udemy.png",
                            "https://vetores.org/wp-content/uploads/udemy.png",
                            "https://vetores.org/wp-content/uploads/udemy.png",
                            "https://vetores.org/wp-content/uploads/udemy.png"
                        )
                        mood = Mood.Awful.name
                    }
                )

                flow { emit(RequestState.Success( data = result)) }
            }catch (e : Exception){
                flow { emit(RequestState.Error(e)) }
            }
        } else {
            flow { emit(RequestState.Error(UserNotAuthenticatedException())) }
        }
    }

    override fun getFilteredDiaries(zonedDateTime: ZonedDateTime): Flow<Diaries> {
        return getAllDiaries()
//        return if (user != null) {
//            try {
//                realm.query<Diary>(
//                    "ownerId == $0 AND date < $1 AND date > $2",
//                    user.id,
//                    RealmInstant.from(
//                        LocalDateTime.of(
//                            zonedDateTime.toLocalDate().plusDays(1),
//                            LocalTime.MIDNIGHT
//                        ).toEpochSecond(zonedDateTime.offset), 0
//                    ),
//                    RealmInstant.from(
//                        LocalDateTime.of(
//                            zonedDateTime.toLocalDate(),
//                            LocalTime.MIDNIGHT
//                        ).toEpochSecond(zonedDateTime.offset), 0
//                    ),
//                ).asFlow().map { result ->
//                    RequestState.Success(
//                        data = result.list.groupBy {
//                            it.date.toInstant()
//                                .atZone(ZoneId.systemDefault())
//                                .toLocalDate()
//                        }
//                    )
//                }
//            } catch (e: Exception) {
//                flow { emit(RequestState.Error(e)) }
//            }
//        } else {
//            flow { emit(RequestState.Error(UserNotAuthenticatedException())) }
//        }
    }

    //override fun getSelectedDiary(diaryId: ObjectId): RequestState<Diary> {
    override fun getSelectedDiary(diaryId: String): Flow<RequestState<Diary>> {
        return if (user != null) {
//            try {
//                realm.query<Diary>(query = "_id == $0", diaryId).asFlow().map {
//                    RequestState.Success(data = it.list.first())
//                }
//            } catch (e: Exception) {
//                flow { emit(RequestState.Error(e)) }
//            }

            Log.d("selectedDiary", diaryId.toString())

            if(diaryId == "1"){
                flow { emit( RequestState.Success(data = Diary().apply {
                    _id = "1"
                    title = "testetitle1"
                    description = "testedescription1"
                    images = realmListOf(
                        "https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on","https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on",
                        "https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on","https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on",
                        "https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on","https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on",
                        "https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on","https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on",
                        "https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on","https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on",
                        "https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on","https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on",
                        "https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on","https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/90069/97748/Rick-and-Morty-Rick-Morty-Smith-Lifesize-Cardboard-Cutout-buy-now-at-starstills__63410.1678789939.jpg?c=2&imbypass=on"
                    )
                }))}
            }else{
                flow { emit( RequestState.Success(data = Diary().apply {
                    _id = "2"
                    title = "testetitle2"
                    description = "testedescription2"
                    images = realmListOf(
                        "https://vetores.org/wp-content/uploads/udemy.png",
                        "https://vetores.org/wp-content/uploads/udemy.png",
                        "https://vetores.org/wp-content/uploads/udemy.png",
                        "https://vetores.org/wp-content/uploads/udemy.png",
                        "https://vetores.org/wp-content/uploads/udemy.png",
                        "https://vetores.org/wp-content/uploads/udemy.png"
                    )
                    mood = Mood.Awful.name
                }))}
            }
        } else {
            flow { emit(RequestState.Error(UserNotAuthenticatedException()))}
        }
    }

    override fun addNewDiary(diary: Diary): RequestState<Diary> {
//        return if (user != null) {
//            realm.write {
//                try {
//                    val addedDiary = copyToRealm(diary.apply { ownerId = user.id })
//                    RequestState.Success(data = addedDiary)
//                } catch (e: Exception) {
//                    RequestState.Error(e)
//                }
//            }
//        } else {
//            RequestState.Error(UserNotAuthenticatedException())
//        }


        return RequestState.Success(data = Diary().apply {
            _id = "2"
            title = "testetitle2"
            description = "testedescription2"
            images = realmListOf(
                "https://vetores.org/wp-content/uploads/udemy.png",
                "https://vetores.org/wp-content/uploads/udemy.png",
                "https://vetores.org/wp-content/uploads/udemy.png",
                "https://vetores.org/wp-content/uploads/udemy.png",
                "https://vetores.org/wp-content/uploads/udemy.png",
                "https://vetores.org/wp-content/uploads/udemy.png"
            )
            mood = Mood.Awful.name
        })
    }

    override suspend fun updateDiary(diary: Diary): RequestState<Diary> {
//        return if (user != null) {
//            realm.write {
//                val queriedDiary = query<Diary>(query = "_id == $0", diary._id).first().find()
//                if (queriedDiary != null) {
//                    queriedDiary.title = diary.title
//                    queriedDiary.description = diary.description
//                    queriedDiary.mood = diary.mood
//                    queriedDiary.images = diary.images
//                    queriedDiary.date = diary.date
//                    RequestState.Success(data = queriedDiary)
//                } else {
//                    RequestState.Error(error = Exception("Queried Diary does not exist."))
//                }
//            }
//        } else {
//            RequestState.Error(UserNotAuthenticatedException())
//        }

        return RequestState.Success(data = Diary().apply {
            _id = "2"
            title = "testetitle2"
            description = "testedescription2"
            images = realmListOf(
                "https://vetores.org/wp-content/uploads/udemy.png",
                "https://vetores.org/wp-content/uploads/udemy.png",
                "https://vetores.org/wp-content/uploads/udemy.png",
                "https://vetores.org/wp-content/uploads/udemy.png",
                "https://vetores.org/wp-content/uploads/udemy.png",
                "https://vetores.org/wp-content/uploads/udemy.png"
            )
            mood = Mood.Awful.name
        })
    }

    override suspend fun deleteDiary(id: String): RequestState<Boolean> {
//        return if (user != null) {
//            realm.write {
//                val diary =
//                    query<Diary>(query = "_id == $0 AND ownerId == $1", id, user.id)
//                        .first().find()
//                if (diary != null) {
//                    try {
//                        delete(diary)
//                        RequestState.Success(data = true)
//                    } catch (e: Exception) {
//                        RequestState.Error(e)
//                    }
//                } else {
//                    RequestState.Error(Exception("Diary does not exist."))
//                }
//            }
//        } else {
//            RequestState.Error(UserNotAuthenticatedException())
//        }
        return RequestState.Success(data = true)
    }

    override suspend fun deleteAllDiaries(): RequestState<Boolean> {
        return RequestState.Success(data = true)
//        return if (user != null) {
//            realm.write {
//                val diaries = this.query<Diary>("ownerId == $0", user.id).find()
//                try {
//                    delete(diaries)
//                    RequestState.Success(data = true)
//                } catch (e: Exception) {
//                    RequestState.Error(e)
//                }
//            }
//        } else {
//            RequestState.Error(UserNotAuthenticatedException())
//        }
        // - teste
    }
}

private class UserNotAuthenticatedException: Exception("User is not Logged in.")