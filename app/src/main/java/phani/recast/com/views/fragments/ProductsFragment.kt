package phani.recast.com.views.fragments


import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.fragment_products.*
import phani.recast.com.R
import phani.recast.com.adapters.ProductListAdapter
import phani.recast.com.modal.ODSP_Catelog_info
import phani.recast.com.modal.OdspItemList
import phani.recast.com.webservices.ApiBuilder_ODSP
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 *
 */
class ProductsFragment : DialogFragment() {

    companion object {
        fun newInstance(): ProductsFragment = ProductsFragment()
        val TAG: String = "product Fragment"
    }

    var dialog: AlertDialog? = null
    lateinit var odspList: ArrayList<ODSP_Catelog_info>
    lateinit var odsp_productslist_adapter: ProductListAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    /*
        override fun onAttach(context: Context?) {
            super.onAttach(context)
            EventBus.getDefault().register(activity)
        }

        override fun onDetach() {
            EventBus.getDefault().unregister(activity)
            super.onDetach()
        }*/
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        odspList = arrayListOf()
        odspList.clear()
        dialog = SpotsDialog.Builder().setContext(activity).build()

        odsp_productslist_adapter = ProductListAdapter(odspList)
        odsp_productlist.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        odsp_productlist.hasFixedSize()
        odsp_productlist.adapter = odsp_productslist_adapter
        loadfromOdsp()

    }

    fun loadfromOdsp() {
        dialog?.show()

        var apiservice = ApiBuilder_ODSP.create()
        val call_productlist = apiservice.getRequestedCatelogData()

        call_productlist.enqueue(object : Callback<OdspItemList> {


            override fun onFailure(call: Call<OdspItemList>, t: Throwable) {
                Toast.makeText(activity, "${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                Log.d(TAG, " onFailure Error : ${Gson().toJson(t.stackTrace)} \n Error name is ${t.localizedMessage}")
                dialog?.dismiss()
            }

            override fun onResponse(call: Call<OdspItemList>, response: Response<OdspItemList>) {
                if (response.isSuccessful) {
                    if (response.code() == 200) {
                        var list = response.body()

                        Log.d(TAG, "ODSP list: ${Gson().toJson(list)} and pagecontent ${list?.pageContent}")
                        //odspList = list?.pageContent as ArrayList<OdspItemList.PageContent>
                        for (ele in list?.pageContent!!) {
                            odspList.add(ODSP_Catelog_info(ele.itemId.itemCode, ele.shortDescription.value))
                            Log.d(TAG, "item Details : ${ele.itemId.itemCode} --- ${ele.shortDescription.value} --- ")
                        }
                        Log.d(TAG, "ODSP LIST: ${Gson().toJson(odspList)}")
                        odsp_productslist_adapter.notifyDataSetChanged()

                    } else {
                        Toast.makeText(activity, getString(R.string.unabletofetch), Toast.LENGTH_SHORT).show()
                    }
                }
                dialog?.dismiss()
            }

        })
    }


    /*  @Subscribe(threadMode = ThreadMode.MAIN)
      fun openNavigationStoreMap(eventNavigate: EventNavigatefromList) {
          Log.d(ChatScreen.TAG, "EventNavigate: ${eventNavigate.navigate} Store Name ${eventNavigate.navigateToShowRoomName}")
          startActivity(Intent(activity, NavigationHome::class.java))
      }*/
}
