package com.example.adminapp.FirebaseRealtimeDb.repository

import com.example.adminapp.FirebaseRealtimeDb.RealtimeModelResponse
import com.example.adminapp.FirebaseRealtimeDb.RealtimeModelResponse2
import com.example.adminapp.util.ResultState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


class RealtimeDbRepository @Inject constructor(
    private val db: DatabaseReference
) : RealtimeRepository {
    override fun insert(item: RealtimeModelResponse.RealtimeItems): Flow<ResultState<String>> = callbackFlow{

        trySend(ResultState.Loading)

        db.child("user").push().setValue(
            item
        ).addOnCompleteListener{
            if(it.isSuccessful)
                trySend(ResultState.Success("Data Inserted Successfully..."))
        }.addOnFailureListener{
            trySend(ResultState.Failure(it))
        }

        awaitClose{
            close()
        }

    }

    override fun getItems(): Flow<ResultState<List<RealtimeModelResponse>>> = callbackFlow {
        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.child("user").children.map{
                    RealtimeModelResponse(
                        it.getValue(RealtimeModelResponse.RealtimeItems::class.java),
                        key = it.key
                    )
                }
                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Failure(error.toException()))
            }
        }

        db.addValueEventListener(valueEvent)
        awaitClose{
            db.removeEventListener(valueEvent)
            close()
        }

    }

    override fun getStudentItems(): Flow<ResultState<List<RealtimeModelResponse2>>> = callbackFlow {
        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.child("message").children.map{
                    RealtimeModelResponse2(
                        it.getValue(RealtimeModelResponse2.Message::class.java),
                        key = it.key
                    )
                }
                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Failure(error.toException()))
            }
        }

        db.addValueEventListener(valueEvent)
        awaitClose{
            db.removeEventListener(valueEvent)
            close()
        }
    }


//    override fun getStudentItems(): Flow<ResultState<List<Message>>> = callbackFlow {
//        trySend(ResultState.Loading)
//
//        val valueEvent = object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val items = snapshot.child("message").children.map{
//                    it.getValue(Message::class.java)
//                }
//                trySend(ResultState.Success<List<Message>>(Message)).isSuccess
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                trySend(ResultState.Failure(error.toException()))
//            }
//        }
//
//        db.addValueEventListener(valueEvent)
//        awaitClose{
//            db.removeEventListener(valueEvent)
//            close()
//        }
//
//    }







    override fun delete(key: String): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)
        db.child("user").child(key).removeValue()
            .addOnCompleteListener{
                trySend(ResultState.Success("item Deleted"))
            }.addOnFailureListener{
                trySend(ResultState.Failure(it))
            }
        awaitClose{
            close()
        }
    }

    override fun update(res: RealtimeModelResponse): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)
        val map = HashMap<String, Any>()
        map["title"] = res.item?.title!!
        map["description"] = res.item.description!!

        db.child("user").child(res.key!!).updateChildren(
            map
        ).addOnCompleteListener{
            trySend(ResultState.Success("Update Successfully..."))
        }.addOnFailureListener {
            trySend(ResultState.Failure(it))
        }
        awaitClose{
            close()
        }
    }
}