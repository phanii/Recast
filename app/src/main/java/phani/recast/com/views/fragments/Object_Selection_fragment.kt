package phani.recast.com.views.fragments

import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.indoorway.android.common.sdk.IndoorwaySdk
import com.indoorway.android.common.sdk.listeners.generic.Action1
import com.indoorway.android.common.sdk.model.IndoorwayObjectParameters
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.mapobject_dialog.*
import phani.recast.com.R
import phani.recast.com.adapters.Navigate_to_place_Adapter
import phani.recast.com.modal.Navigation_obj_and_obj_id
import phani.recast.com.views.NavigationHome.Companion.TAG
import phani.recast.com.webservices.Utilities.Values.buildingUUID
import phani.recast.com.webservices.Utilities.Values.mapUUID

class Object_Selection_fragment() : DialogFragment() {

    lateinit var objAdapter: Navigate_to_place_Adapter
    lateinit var objlist: ArrayList<Navigation_obj_and_obj_id>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.mapobject_dialog, container, false)
        dialog.setTitle("Simple Dialog")
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.dialog_theme_full)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_cancle.setOnClickListener {
            dismiss()
        }
        objlist = arrayListOf()
        objlist.clear()
        last_location.text = Prefs.getString("recentLocation", "Not Available")
        objAdapter = Navigate_to_place_Adapter(objlist)
        obj_recyclerView.hasFixedSize()
        obj_recyclerView.layoutManager = LinearLayoutManager(activity.applicationContext, LinearLayoutManager.VERTICAL, false)
        obj_recyclerView.adapter = objAdapter
        loadobjects()
    }

    private fun loadobjects() {
        IndoorwaySdk.instance()
                .map()
                .details(buildingUUID, mapUUID)
                .setOnCompletedListener(Action1 {
                    var mapObjs: List<IndoorwayObjectParameters> = it.objects
                    for (itb in mapObjs) {
                        Log.d(TAG, "${itb.id} and ${itb.name}: ")
                        objlist.add(Navigation_obj_and_obj_id(itb.name, itb.id))
                    }
                    System.out.println("Buildling maps objects : ${Gson().toJson(it)}")
                    objAdapter.notifyDataSetChanged()
                }

                )


                .setOnFailedListener(Action1 {
                    Log.d(TAG, "Building map objects Exception: ${it.message}")
                })
                .execute()
    }

    override fun onStart() {
        super.onStart()
        val d = dialog
        if (d != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            d.window.setLayout(width, height)
        }
    }

}
