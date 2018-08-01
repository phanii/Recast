package phani.recast.com.adapters

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.botresp_message.view.*
import kotlinx.android.synthetic.main.item_message_received.view.*
import kotlinx.android.synthetic.main.item_message_sent.view.*
import kotlinx.android.synthetic.main.typingindicator.view.*
import org.greenrobot.eventbus.EventBus
import phani.recast.com.R
import phani.recast.com.adapters.ChatAdapter.Companion.TAG
import phani.recast.com.adapters.ChatAdapter.Companion.typinganim
import phani.recast.com.modal.*


class ChatAdapter(val chatmessages: ArrayList<ChatMessageModal>) : Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val MESSAGESENTVALUE = 1
        const val MESSAGERECEIVEVALUE = 2
        const val TYPINGVALUE = 3
        const val TAG = "ChatAdapter"
        var typinganim: ObjectAnimator? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        var viewHolder: RecyclerView.ViewHolder? = null
        when (viewType) {
            MESSAGERECEIVEVALUE -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_received, parent, false)
                viewHolder = ReceivedMessageHolder(view)
            }
            MESSAGESENTVALUE -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_sent, parent, false)
                viewHolder = SentMessageHolder(view)
            }
            TYPINGVALUE -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.typingindicator, parent, false)
                viewHolder = TypingIndicatorHolder(view)
            }
        }
        return viewHolder!!
    }


    override fun getItemViewType(position: Int): Int {

        val chatMessage = chatmessages[position]


        return when (chatMessage.direction) {
            ChatMessageModal.Type.SENT -> MESSAGESENTVALUE
            ChatMessageModal.Type.RECEIVED -> MESSAGERECEIVEVALUE
            ChatMessageModal.Type.TYPING -> TYPINGVALUE


            null -> TODO()
        }


    }

    override fun getItemCount(): Int {
        return chatmessages.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var cmm: ChatMessageModal = chatmessages[position]
        when (holder.itemViewType) {
            MESSAGESENTVALUE -> (holder as SentMessageHolder).binditem(cmm)
            MESSAGERECEIVEVALUE -> (holder as ReceivedMessageHolder).binditem(cmm)
            TYPINGVALUE -> (holder as TypingIndicatorHolder).bindTypingIndicator()
        }
    }


}


class ReceivedMessageHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    fun binditem(chatMessageModal: ChatMessageModal) {
        typinganim?.cancel()
        Log.d(TAG, "Message Type : ${chatMessageModal.type}")
        when (chatMessageModal.type) {
            itemView.context.getString(R.string.type_text) -> {
                itemView.layout_generalresponse.visibility = View.VISIBLE
                itemView.quickreply_layout.visibility = View.GONE
                itemView.layout_picture.visibility = View.GONE
                itemView.list_layout.visibility = View.GONE

                itemView.receive_body.text = chatMessageModal.message.toString()
                itemView.receive_time.text = chatMessageModal.getFormattedTime()
                Log.d("ReceivedMessageHolder", "text :${chatMessageModal.type} ")

            }
            itemView.context.getString(R.string.picture) -> {
                itemView.layout_generalresponse.visibility = View.GONE
                itemView.quickreply_layout.visibility = View.GONE
                itemView.layout_picture.visibility = View.VISIBLE
                itemView.list_layout.visibility = View.GONE

                itemView.receivemainlayout.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
                Picasso.get()
                        .load(chatMessageModal.message.toString())
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(itemView.receive_thumbnail)
                itemView.receive_thumbnail.setOnClickListener {
                    EventBus.getDefault().post(EventBusMessage(itemView.context.getString(R.string.gotoshopdetails)))

                }

            }
            itemView.context.getString(R.string.quickReplies) -> {
                itemView.layout_generalresponse.visibility = View.GONE
                itemView.quickreply_layout.visibility = View.VISIBLE
                itemView.layout_picture.visibility = View.GONE
                itemView.list_layout.visibility = View.GONE

                var quickreplyList: ArrayList<QuickReply> = arrayListOf()
                val str = Gson().toJson(chatMessageModal.message)
                val content: Content = Gson().fromJson(str, Content::class.java)
                for (button: Button in content.buttons!!) {
                    quickreplyList.add(QuickReply(button.title!!, button.value!!))
                }
                itemView.quickreply_title.text = content.title

                val q = QuickReplyAdapter(quickreplyList)
                itemView.child_bot_recv.hasFixedSize()
                itemView.child_bot_recv.layoutManager = LinearLayoutManager(itemView.context, LinearLayout.HORIZONTAL, false)
                itemView.child_bot_recv.adapter = q
            }
            itemView.context.getString(R.string.list) -> {
                itemView.layout_generalresponse.visibility = View.GONE
                itemView.quickreply_layout.visibility = View.GONE
                itemView.layout_picture.visibility = View.GONE
                itemView.list_layout.visibility = View.VISIBLE


                var listreplyList: ArrayList<Element> = arrayListOf()
                val str = Gson().toJson(chatMessageModal.message)
                val content: Content? = Gson().fromJson(str, Content::class.java)
                Log.d(TAG, "Gson listReply Value: ${Gson().toJson(chatMessageModal.message)}")
                listreplyList = content?.elements!!
                if (listreplyList.size > 0) {
                    val q = ListTypeReplyAdapter(listreplyList)
                    itemView.child_bot_recv_listlayout.hasFixedSize()
                    itemView.child_bot_recv_listlayout.layoutManager = LinearLayoutManager(itemView.context, LinearLayout.VERTICAL, false)
                    itemView.child_bot_recv_listlayout.adapter = q

                }

            }
            else -> {


            }


        }


    }

}

class SentMessageHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    fun binditem(chatMessageModal: ChatMessageModal) {
        itemView.sent_body.text = chatMessageModal.message.toString()
        itemView.sent_time.text = chatMessageModal.getFormattedTime()
    }

}

class TypingIndicatorHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    fun bindTypingIndicator() {
        itemView.typingindicator.visibility = View.VISIBLE

        typinganim = ObjectAnimator.ofInt(itemView.typingindicator, "textColor", Color.BLACK, Color.TRANSPARENT)
        typinganim?.duration = 1000
        typinganim?.setEvaluator(ArgbEvaluator())
        typinganim?.repeatCount = ValueAnimator.INFINITE
        typinganim?.repeatMode = ValueAnimator.REVERSE
        typinganim?.start()
    }

}