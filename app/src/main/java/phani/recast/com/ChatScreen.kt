package phani.recast.com

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_chat_screen.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import phani.recast.com.adapters.ChatAdapter
import phani.recast.com.modal.*
import phani.recast.com.webservices.ApiBuilder
import phani.recast.com.webservices.Utilities
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ChatScreen : AppCompatActivity() {
    companion object {
        val TAG: String = ChatScreen::class.java.simpleName
    }

    lateinit var content: Content
    var messageResults: ArrayList<Message>? = ArrayList()
    lateinit var arrayList: ArrayList<ChatMessageModal>
    lateinit var adapter: ChatAdapter
    lateinit var typingobj: ChatMessageModal
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_screen)
        arrayList = ArrayList()
        adapter = ChatAdapter(arrayList)
        reyclerview_message_list.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        reyclerview_message_list.hasFixedSize()
        typingobj = ChatMessageModal("text", "Typing", System.currentTimeMillis(), ChatMessageModal.Type.TYPING)

        button_chatbox_send.setOnClickListener {

            loadView(edittext_chatbox.text.toString())

        }
    }


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this);
        arrayList.add(ChatMessageModal("text", "Say \'Hey\' to start", System.currentTimeMillis(), ChatMessageModal.Type.RECEIVED))
        reyclerview_message_list.adapter = adapter
    }

    private fun loadView(input: String) {
        arrayList.add(ChatMessageModal("text", input, System.currentTimeMillis(), ChatMessageModal.Type.SENT))
        adapter.notifyDataSetChanged()
        arrayList.add(typingobj)

        sendmessagetoServer(input)
        removeTyping()
        edittext_chatbox.setText("")
    }

    private fun removeTyping() {

    }

    private fun sendmessagetoServer(chatMessage: String?) {

        val apiservice = ApiBuilder.create()

        val call_dialog = apiservice.getRequestData("Token ${Utilities.token}", Date().time, InputMessage(Keyboardmessagge("text", chatMessage!!)))
        call_dialog.enqueue(object : Callback<Dialog> {
            override fun onFailure(call: Call<Dialog>?, t: Throwable?) {
                Log.d(TAG, "onFailure: ${t?.localizedMessage}")
                arrayList.add(ChatMessageModal("text", edittext_chatbox.text.toString(), System.currentTimeMillis(), ChatMessageModal.Type.RECEIVED))

            }

            override fun onResponse(call: Call<Dialog>?, response: Response<Dialog>?) {
                if (response!!.isSuccessful) {
                    messageResults = response.body()?.results?.messages as ArrayList<Message>?
                    for (message: Message in messageResults!!) {
                        arrayList.remove(typingobj)
                        Log.d(TAG, "Message Type: ${message.type}")
                        when (message.type) {
                            getString(R.string.type_text) -> {
                                Log.d(TAG, "Inside : ${message.type}");
                                arrayList.add(ChatMessageModal(getString(R.string.type_text), message.content!!, System.currentTimeMillis(), ChatMessageModal.Type.RECEIVED))
                            }
                            getString(R.string.quickReplies) -> {
                                Log.d(TAG, "Inside : ${message.type}");
                                val str = Gson().toJson(message.content)
                                content = Gson().fromJson(str, Content::class.java)
                                Log.d(TAG, "Content: " + content.title)
                                Log.d(TAG, "content info : ${Gson().toJson(content)}")
                                Log.d(TAG, "content str : $str")
                                arrayList.add(ChatMessageModal(getString(R.string.quickReplies), message.content!!, System.currentTimeMillis(), ChatMessageModal.Type.RECEIVED))
                            }
                        }
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    Log.d(TAG, "onResponse Error: ");
                }
                reyclerview_message_list.scrollToPosition(adapter.itemCount - 1)
            }

        })


    }


    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onQuickreply(quickReply: QuickReply) {
        Log.d(TAG, "Subscribe: ${quickReply.value}")
        loadView(quickReply.value)
    }
}
