package com.example.adminapp.FirebaseRealtimeDb

data class RealtimeModelResponse(
    val item: RealtimeItems?,
    val key: String?=""
){
    data class RealtimeItems(
        val title: String? = "",
        val description: String? = "",
        val skills: String? = "",
        val whocanapply: String? = "",
        val noofopening: String? = "",
        val nameofteacher : String? = "Ayush Kushwaha"
    )
}

data class RealtimeModelResponse2(
    val item: Message?,
    val key: String?=""
){

    data class Message(
        val projectName : String? = "",
        val name : String ="",
        val batch : String = "",
        val rollno : String = "",
        val message : String = ""
    )
}

