package com.example.adminapp.FirebaseRealtimeDb.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminapp.FirebaseRealtimeDb.RealtimeModelResponse
import com.example.adminapp.FirebaseRealtimeDb.RealtimeModelResponse2
import com.example.adminapp.FirebaseRealtimeDb.repository.RealtimeRepository
import com.example.adminapp.util.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RealtimeViewModel @Inject constructor(
    private val repo: RealtimeRepository
): ViewModel(){

    private val _res: MutableState<ItemState> = mutableStateOf(ItemState())
    val res: State<ItemState> = _res

    val _msg: MutableState<ItemState2> = mutableStateOf(ItemState2())
    val msg: State<ItemState2> = _msg

    fun insert(items: RealtimeModelResponse.RealtimeItems) = repo.insert(items)

    private val _updateRes:MutableState<RealtimeModelResponse> = mutableStateOf(
        RealtimeModelResponse(
            item = RealtimeModelResponse.RealtimeItems(),
        )
    )

    val updateRes: State<RealtimeModelResponse> = _updateRes

    fun setData(data: RealtimeModelResponse){
        _updateRes.value = data
    }


    fun getAllTask(){
        viewModelScope.launch {
            repo.getItems().collect{
                when(it){
                    is ResultState.Success -> {
                        _res.value = ItemState(
                            item = it.data
                        )
                    }
                    is ResultState.Failure ->{
                        _res.value = ItemState(
                            error = it.msg.toString()
                        )
                    }
                    ResultState.Loading->{
                        _res.value = ItemState(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }
    init {
    getAllTask()
        getStudentMessage()
    }

    fun getStudentMessage(){
        viewModelScope.launch {
            repo.getStudentItems().collect{
                when(it){
                    is ResultState.Success -> {
                        _msg.value = ItemState2(
                            item = it.data
                        )
                    }
                    is ResultState.Failure ->{
                        _msg.value = ItemState2(
                            error = it.msg.toString()
                        )
                    }
                    ResultState.Loading->{
                        _msg.value = ItemState2(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }


    fun delete(key:String) = repo.delete(key)
    fun update(item:RealtimeModelResponse) = repo.update(item)
}

data class ItemState(
    val item: List<RealtimeModelResponse> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false

)
data class ItemState2(
    val item: List<RealtimeModelResponse2> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false

)