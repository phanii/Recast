package phani.recast.com

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import com.google.gson.Gson
import com.indoorway.android.common.sdk.IndoorwaySdk
import com.indoorway.android.common.sdk.listeners.generic.Action1
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_chat_screen.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import phani.recast.com.adapters.ChatAdapter
import phani.recast.com.modal.*
import phani.recast.com.views.NavigationHome
import phani.recast.com.webservices.ApiBuilder
import phani.recast.com.webservices.Utilities
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ChatScreen : AppCompatActivity() {
    var list: ArrayList<String>? = null
    private val PACKAGE_NAME = "com.whatsapp"

    companion object {
        val TAG: String = ChatScreen::class.java.simpleName
        const val REQ_CODE = 100
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
        list = ArrayList()
        adapter = ChatAdapter(arrayList)
        reyclerview_message_list.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        reyclerview_message_list.hasFixedSize()
        typingobj = ChatMessageModal("text", "Typing", System.currentTimeMillis(), ChatMessageModal.Type.TYPING)
        // arrayList.add(ChatMessageModal("text", "Say \' to start", System.currentTimeMillis(), ChatMessageModal.Type.RECEIVED))
        reyclerview_message_list.adapter = adapter
        switchchange.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> {

                    openMic()
                }
                false -> {
                    button_chatbox_send.setOnClickListener {

                        loadView(edittext_chatbox.text.toString())
                    }
                }
            }
        }
        button_chatbox_send.setOnClickListener {

            if (edittext_chatbox.text.toString().contains("/WhatsApp", ignoreCase = true)) {

                if (packageInstalledOrNot(PACKAGE_NAME)) {
                    //opens whats app dialog if it is installed on device
                    shareMsgOnWhatsApp(edittext_chatbox.text.toString())
                } else {
                    Toast.makeText(this, "WhatsApp is not installed on your device", Toast.LENGTH_SHORT).show()
                }
            } else {

                loadView(edittext_chatbox.text.toString())
            }
        }
        IndoorwaySdk.instance()
                .buildings()
                .setOnCompletedListener(Action1 {
                    Log.d(TAG, "Building List: ${Gson().toJson(it)}")
                    Prefs.putString("street", it[0].street)
                    Prefs.putString("mapname", it[0].maps[0].name)
                    Prefs.putString("buildingUUID", it[0].uuid)
                    Prefs.putString("mapUUID", it[0].maps[0].uuid)
                    // handle buildings list
                })
                .setOnFailedListener(Action1 {
                    // handle error, original exception is given on e.getCause()
                })
                .execute()

    }


    override fun onStart() {
        super.onStart()

        EventBus.getDefault().register(this)

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
                                Log.d(TAG, "Inside : ${message.type}")
                                arrayList.add(ChatMessageModal(getString(R.string.type_text), message.content!!, System.currentTimeMillis(), ChatMessageModal.Type.RECEIVED))
                            }
                            getString(R.string.quickReplies) -> {
                                Log.d(TAG, "Inside : ${message.type}")
                                val str = Gson().toJson(message.content)
                                content = Gson().fromJson(str, Content::class.java)
                                Log.d(TAG, "Content: " + content.title)
                                Log.d(TAG, "content info : ${Gson().toJson(content)}")
                                Log.d(TAG, "content str : $str")
                                arrayList.add(ChatMessageModal(getString(R.string.quickReplies), message.content!!, System.currentTimeMillis(), ChatMessageModal.Type.RECEIVED))
                            }
                            getString(R.string.picture) -> {
                                arrayList.add(ChatMessageModal(getString(R.string.picture), message.content!!, System.currentTimeMillis(), ChatMessageModal.Type.RECEIVED))
                            }
                            getString(R.string.list) -> {
                                arrayList.add(ChatMessageModal(getString(R.string.list), message.content!!, System.currentTimeMillis(), ChatMessageModal.Type.RECEIVED))

                            }
                        }
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    Log.d(TAG, "onResponse Error: ")
                }
                // reyclerview_message_list.scrollToPosition(adapter.itemCount - 1)
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun openShopDetails(eventBusMessage: EventBusMessage) {
        startActivity(Intent(this, ShopDetails::class.java))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun openNavigationStoreMap(eventNavigate: EventNavigate) {
        Log.d(TAG, "EventNavigate: ${eventNavigate.navigate} Store Name ${eventNavigate.navigateToShowRoomName}")
        startActivity(Intent(this, NavigationHome::class.java))
    }

    private fun openMic() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.saysomething))
        try {
            startActivityForResult(intent, REQ_CODE)
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.speachnotsupported), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "Request code: $requestCode and $resultCode")
        if (resultCode != 0) {
            when (requestCode) {
                REQ_CODE -> {
                    if (resultCode == Activity.RESULT_OK && null != data) {
                        list = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                        try {
                            //  arrayList.add(ChatMessageModal("text", list!![0], System.currentTimeMillis(), ChatMessageModal.Type.SENT))
                            adapter.notifyDataSetChanged()
                            //  speachtext.text = list!![0]
                            loadView(list!![0])
                        } catch (e: Exception) {
                            Log.d(TAG, "Speachtext Error :${list!![0]} ")
                        }
                        switchchange.isChecked = false
                    }
                }
            }
        } else {
            switchchange.isChecked = false
        }
    }


    //method checks for passed package install or not
    private fun packageInstalledOrNot(packageName: String): Boolean {
        val packageManager = this.packageManager
        val isInstalled: Boolean
        isInstalled = try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }

        return isInstalled
    }

    //open whatsApp share dialog screen
    private fun shareMsgOnWhatsApp(msg: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.setPackage("com.whatsapp")
        intent.putExtra(Intent.EXTRA_TEXT, msg)
        startActivity(Intent.createChooser(intent, ""))
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable("chathistory", arrayList)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        arrayList = savedInstanceState?.getSerializable("chathistory") as ArrayList<ChatMessageModal>
    }

}
