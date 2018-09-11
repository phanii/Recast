package phani.recast.com.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.listlayoutrow.view.*
import org.greenrobot.eventbus.EventBus
import phani.recast.com.R
import phani.recast.com.modal.Element
import phani.recast.com.modal.EventBusMessage
import phani.recast.com.modal.EventNavigate

class ListTypeReplyAdapter(var elemlist: ArrayList<Element>) : RecyclerView.Adapter<ListTypeReplyAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.listlayoutrow, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return elemlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var element = elemlist[position]
        holder.bindview(element)
        holder.itemView.listlayout_row_viewstore.setOnClickListener {
            Log.d("ListTypeReplyAdapter", ": ${element.title}")
            EventBus.getDefault().post(EventBusMessage(holder.itemView.context.getString(R.string.gotoshopdetails)))
        }
        holder.itemView.navigate.setOnClickListener {
            EventBus.getDefault().post(EventNavigate(true, element.title!!))
        }
    }


    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bindview(element: Element) {
            itemView.listlayout_row_title.text = element.title
            itemView.listlayout_row_subtitle.text = element.subtitle
            Picasso.get()
                    .load(element.imageUrl)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(itemView.listlayout_row_image)

        }
    }
}