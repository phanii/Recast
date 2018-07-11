package phani.recast.com.webservices

import phani.recast.com.modal.Dialog
import phani.recast.com.modal.InputMessage
import retrofit2.Call
import retrofit2.http.*

interface APIinterface {
     @POST("/build/v1/dialog")
    fun getRequestData(@Header("Authorization") auth: String, @Query("conversation_id") input: Long, @Body body: InputMessage): Call<Dialog>


}