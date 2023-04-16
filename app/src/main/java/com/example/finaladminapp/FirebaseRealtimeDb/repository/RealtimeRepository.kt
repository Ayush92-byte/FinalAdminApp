package com.example.adminapp.FirebaseRealtimeDb.repository

import com.example.adminapp.FirebaseRealtimeDb.RealtimeModelResponse
import com.example.adminapp.FirebaseRealtimeDb.RealtimeModelResponse2
import com.example.adminapp.util.ResultState
import kotlinx.coroutines.flow.Flow


interface RealtimeRepository {

    fun insert(
        item:RealtimeModelResponse.RealtimeItems
    ): kotlinx.coroutines.flow.Flow<ResultState<String>>

    fun getItems() : Flow<ResultState<List<RealtimeModelResponse>>>

    fun getStudentItems() : Flow<ResultState<List<RealtimeModelResponse2>>>

    fun delete(
        key: String
    ): Flow<ResultState<String>>

    fun update(
        res: RealtimeModelResponse
    ): Flow<ResultState<String>>
}