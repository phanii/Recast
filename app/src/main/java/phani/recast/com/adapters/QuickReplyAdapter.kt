package phani.recast.com.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.bot_recv_quickreply_layout.view.*
import org.greenrobot.eventbus.EventBus
import phani.recast.com.R
import phani.recast.com.modal.QuickReply
import kotlin.math.log

class QuickReplyAdapter(val quickreplylist: ArrayList<QuickReply>) : RecyclerView.Adapter<QuickReplyAdapter.QuickReplyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuickReplyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.bot_recv_quickreply_layout, parent, false)
        return QuickReplyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quickreplylist.size
    }

    override fun onBindViewHolder(holder: QuickReplyViewHolder, position: Int) {

        holder.bindView(quickreplylist[position])
        holder.itemView.quickreply_txt.setOnClickListener {
            EventBus.getDefault().post(QuickReply(quickreplylist[position].key, quickreplylist[position].value))
            //  Log.d("QuickReplyViewHolder", "click position: " + quickreplylist[position].value);

        }


    }


    class QuickReplyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bindView(quickReply: QuickReply) {
            itemView.quickreply_txt.text = quickReply.key

        }
    }

}
