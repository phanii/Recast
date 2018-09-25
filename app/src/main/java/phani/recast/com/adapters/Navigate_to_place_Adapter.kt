package phani.recast.com.adapters

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.navigate_to_particularplace_row.view.*
import phani.recast.com.R
import phani.recast.com.modal.Navigation_obj_and_obj_id

class Navigate_to_place_Adapter(val navobjlist: ArrayList<Navigation_obj_and_obj_id>) : RecyclerView.Adapter<Navigate_to_place_Adapter.NavigateViewHolder>() {


    companion object {
        val TAG: String = "Navigate_to_place_Adapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigateViewHolder {
        return NavigateViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.navigate_to_particularplace_row, parent, false))
    }

    override fun getItemCount(): Int {
        return navobjlist.size
    }

    @SuppressLint("LongLogTag")
    override fun onBindViewHolder(holder: NavigateViewHolder, position: Int) {
        val ele = navobjlist[position]
        holder.binding(ele)
        holder.itemView.setOnClickListener {
            Log.d(TAG, ":${ele.navobj_id} -----> ${ele.navobj_name} ")
        }
    }


    class NavigateViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun binding(ele: Navigation_obj_and_obj_id) {
            itemView.navigate_to_place_radio.text = ele.navobj_name
        }
    }
}
