package phani.recast.com.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import kotlinx.android.synthetic.main.productrow.view.*
import org.greenrobot.eventbus.EventBus
import phani.recast.com.R
import phani.recast.com.modal.EventNavigatefromList
import phani.recast.com.modal.ODSP_Catelog_info

class ProductListAdapter(var elements: ArrayList<ODSP_Catelog_info>) : RecyclerView.Adapter<ProductListAdapter.ViewHolderProduct>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderProduct {
        return ViewHolderProduct(LayoutInflater.from(parent.context).inflate(R.layout.productrow, parent, false))

    }

    override fun getItemCount(): Int {
        return elements.size
    }

    override fun onBindViewHolder(holder: ViewHolderProduct, position: Int) {
        var eachrow = elements[position]
        Log.d("Productlist Adapter", "eachrow  :${Gson().toJson(eachrow)} ")
        holder.bindview(eachrow)
        holder.itemView.navigatebtn.setOnClickListener {
            EventBus.getDefault().post(EventNavigatefromList(true, "navigationlayoutpage"))
        }
    }

    class ViewHolderProduct(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bindview(ele: ODSP_Catelog_info) {
            itemView.pname.text = ele.itemname
            itemView.pprice.text = ele.itemprice.toString()
        }

    }

    /* fun filterList(filterdNames: ArrayList<ODSP_Catelog_info>) {
         elements = filterdNames
         notifyDataSetChanged()
     }*/

}