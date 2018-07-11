package phani.recast.com

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import co.intentservice.chatui.ChatView
import co.intentservice.chatui.models.ChatMessage
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import phani.recast.com.webservices.ApiBuilder
import phani.recast.com.webservices.Utilities.Values.token
import phani.recast.com.modal.Dialog
import phani.recast.com.modal.InputMessage
import phani.recast.com.modal.Message
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    var messageResults: ArrayList<Message>? = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chat_view.setOnSentMessageListener { chatMessage ->
            sendmessagetoServer(chatMessage)
            true
        }
        chat_view.setTypingListener(object : ChatView.TypingListener {
            override fun userStartedTyping() {
            }

            override fun userStoppedTyping() {

            }
        })

    }

    private fun sendmessagetoServer(chatMessage: ChatMessage?) {

   /*     val apiservice = ApiBuilder.create()

        val call_dialog = apiservice.getRequestData("Token $token", Date().time, InputMessage(Message("text", chatMessage!!.message)))
        call_dialog.enqueue(object : Callback<Dialog> {
            override fun onFailure(call: Call<Dialog>?, t: Throwable?) {
                Log.d("failure", Gson().toJson(t!!.message))
                chat_view.addMessage(ChatMessage("Try again", System.currentTimeMillis(), ChatMessage.Type.RECEIVED))

            }

            override fun onResponse(call: Call<Dialog>?, response: Response<Dialog>?) {

                if (response!!.isSuccessful) {
                    messageResults = response.body()?.results?.messages as ArrayList<Message>?
                    for (message: Message in messageResults!!) {
                        if (message.type == getString(R.string.type_text)) {
                            chat_view.addMessage(ChatMessage(message.content.toString(), System.currentTimeMillis(), ChatMessage.Type.RECEIVED))
                        }
                    }
                }

            }

        })*/
    }
}
