package phani.recast.com.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.indoorway.android.common.sdk.listeners.generic.Action0
import com.indoorway.android.common.sdk.listeners.generic.Action1
import com.indoorway.android.common.sdk.model.*
import com.indoorway.android.fragments.sdk.map.MapFragment
import com.indoorway.android.location.sdk.IndoorwayLocationSdk
import com.indoorway.android.location.sdk.model.IndoorwayLocationSdkError
import com.indoorway.android.location.sdk.model.IndoorwayLocationSdkState
import com.indoorway.android.map.sdk.listeners.OnRoomSelectedListener
import com.indoorway.android.map.sdk.view.camera.ScaleFactorType
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fabmenu.*
import kotlinx.android.synthetic.main.navigation_home.*
import phani.recast.com.R
import phani.recast.com.views.fragments.Object_Selection_fragment
import phani.recast.com.webservices.Utilities.Values.buildingUUID
import phani.recast.com.webservices.Utilities.Values.mapUUID

class NavigationHome : AppCompatActivity(), MapFragment.OnMapFragmentReadyListener {
    override fun onMapFragmentReady(fragment: MapFragment) {
        ref_fragment = fragment

        loadMap(fragment)

        /* indoorwayPositionlistener = Action1 {
             // store last position as a field
             Log.d(TAG, "indoorwayPositionlistener: ${Gson().toJson(it)}");

             // react for position changes...

             // If you are using map view, you can pass position.
             // Second argument indicates if you want to auto reload map on position change
             // for eg. after going to different building level.
             ref_fragment?.mapView?.position?.setPosition(it, true)
         }*/

    }

    companion object {
        val TAG: String = javaClass.simpleName
    }

    private var fabExpanded = false
    /*var buildingUUID: String = "FqfSwbjQ3Lk"
    var mapUUID: String = "C4etnWJBuEg"*/
    var paths: List<IndoorwayNode>? = null
    var ref_fragment: MapFragment? = null

    var objectIdList: ArrayList<String>? = ArrayList()
    lateinit var indoorLocationlistener: Action1<IndoorwayLocationSdkState>
    lateinit var error_listener: Action1<IndoorwayLocationSdkError>
    lateinit var indoorwayPositionlistener: Action1<IndoorwayPosition>

    var mapObjects: List<IndoorwayObjectParameters>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_home)
//        loadMap()
        addressAndLocation.setSingleLine()
        addressAndLocation.isSelected = true
        addressAndLocation.text = Prefs.getString("mapname", "") + " : " + Prefs.getString("street", "")

        ref_fragment?.mapView?.selection?.onRoomSelectedListener = object : OnRoomSelectedListener {
            override fun canRoomBeSelected(room: IndoorwayObjectParameters): Boolean {
                Log.d(TAG, "canRoomBeSelected details:${Gson().toJson(room)} ")
                return "inaccessible" != room.type
            }

            override fun onRoomSelected(room: IndoorwayObjectParameters) {

                // called on room selection, check parameters for details
                if (fabExpanded) {
                    closeSubMenusFab()
                }


                Log.d(TAG, "room id: ${room.id}")//C4etnWJBuEg_6bb4a   C4etnWJBuEg_b9ab8
                Log.d(TAG, "onRoomSelected details:${Gson().toJson(room)} ")

                Log.d(TAG, "obj list : ${objectIdList?.size} ")
                try {
                    if (objectIdList?.size!! == 2) {
                        Log.d(TAG, "objectidlist size is more than 2:${objectIdList!!.size} ")
                        objectIdList?.clear()
                        ref_fragment?.mapView?.navigation?.stop()
                        objectIdList?.add(room.id)
                    } else {
                        if (objectIdList!!.size < 2) {
                            objectIdList?.add(room.id)
                            when {
                                objectIdList?.size == 2 -> {
                                    ref_fragment?.mapView?.navigation?.start(objectIdList!![0], objectIdList!![1])
                                    Prefs.putString("recentLocation", room.name)
                                }
                                objectIdList!!.size > 2 -> {
                                    Log.d(TAG, "else condition: ${objectIdList!!.size}")
                                    objectIdList?.clear()
                                    ref_fragment?.mapView?.navigation?.stop()
                                }
                                else -> {
                                    //Toast.makeText(this@NavigationHome, "Uable to find a path!!", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@NavigationHome, "Uable to find a path!!", Toast.LENGTH_SHORT).show()
                }


                val myLayer = ref_fragment?.mapView?.marker?.addLayer(7f)
                /*   myLayer!!.add(
                           DrawableText(
                                   room.id,
                                   Coordinates(room.centerPoint.latitude, room.centerPoint.longitude),
                                   room.name!!,
                                   6f    // eg. 2f
                           )
                   )*/

            }

            override fun onSelectionCleared() {
                // called when no room is selected
                Log.d(TAG, ": noroom selected")
                Toast.makeText(this@NavigationHome, "No Path was found from this place!!", Toast.LENGTH_SHORT).show()
            }
        }

        button_start.setOnClickListener { startnavigation(ref_fragment) }
        button_stop.setOnClickListener {


            /* *//*     // it ->
                Log.d(TAG, "Stop clicked: before");
                indoorwayPositionlistener = Action1 {
                    // store last position as a field
                    Log.d(TAG, "Stop clicked : after");
                    Log.d(TAG, "indoorwayPositionlistener: ${Gson().toJson(it)}");

                    // react for position changes...

                    // If you are using map view, you can pass position.
                    // Second argument indicates if you want to auto reload map on position change
                    // for eg. after going to different building level.
                    ref_fragment?.mapView?.position?.setPosition(it, true)
                }*/

            ref_fragment?.mapView?.navigation?.stop()

/*
            val visitor = Visitor(
                    groupUuid = "NCR Retail", // user group
                    name = "Phani Kumar", // user name
                    age = 30, // user age
                    sex = Sex.MALE,// user gender
                    devicePlatform = DevicePlatform.ANDROID,
                    shareLocation = true
            )

            IndoorwaySdk.instance().visitor().setup(visitor)*/


        }
        indoorwayPositionlistener = Action1 {
            // store last position as a field
            Log.d(TAG, "Stop clicked : after");
            Log.d(TAG, "indoorwayPositionlistener: ${Gson().toJson(it)}");

            // react for position changes...

            // If you are using map view, you can pass position.
            // Second argument indicates if you want to auto reload map on position change
            // for eg. after going to different building level.
            ref_fragment?.mapView?.position?.setPosition(it, true)
        }

        indoorLocationlistener = Action1 {
            // handle state changes
            when (it) {
                IndoorwayLocationSdkState.STARTING -> {
                    Log.d("StateChange", "STARTING: location is starting ${Gson().toJson(it)}")

                }
                IndoorwayLocationSdkState.DETERMINING_LOCATION -> Log.d("StateChange", "DETERMINING_LOCATION: ")
                IndoorwayLocationSdkState.ERROR -> Log.d("StateChange", "ERROR: ${Gson().toJson(it)}")
                IndoorwayLocationSdkState.LOCATING_BACKGROUND -> Log.d("StateChange", "LOCATING_BACKGROUND: ")
                IndoorwayLocationSdkState.LOCATING_FOREGROUND -> Log.d("StateChange", "LOCATING_FOREGROUND: ")
                IndoorwayLocationSdkState.STOPPED -> Log.d("StateChange", "STOPPED: ")
            }
        }


        error_listener = Action1 {
            when (it) {
                IndoorwayLocationSdkError.BleNotSupported -> {
                    Log.d(TAG, "BleNotSupported : Low Energy is not supported, positioning service will be stopped, it can't work")
                    toast("BleNotSupported : Low Energy is not supported, positioning service will be stopped, it can't work")
                }
                is IndoorwayLocationSdkError.MissingPermission -> {
                    Log.d(TAG, "MissingPermission: Some permissions are missing, ask for it.permission")
                    toast("MissingPermission: Some permissions are missing, ask for it.permission")
                }
                IndoorwayLocationSdkError.BluetoothDisabled -> {
                    Log.d(TAG, "BluetoothDisabled:  Bluetooth is disabled, user have to turn it on ")
                    toast("BluetoothDisabled:  Bluetooth is disabled, user have to turn it on ")
                }
                IndoorwayLocationSdkError.LocationDisabled -> {
                    Log.d(TAG, "LocationDisabled: Location is disabled, user have to turn it on ")
                    toast("LocationDisabled: Location is disabled, user have to turn it on ")
                }
                IndoorwayLocationSdkError.UnableToFetchData -> {
                    Log.d(TAG, "UnableToFetchData: Network-related error, service will be restarted on network connection established ")
                    toast("UnableToFetchData: Network-related error, service will be restarted on network connection established ")
                }
                IndoorwayLocationSdkError.NoRadioMaps -> {
                    Log.d(TAG, "NoRadioMaps: Measurements have to be taken in order to use location ")
                    toast("NoRadioMaps: Measurements have to be taken in order to use location ")
                }
            }
        }

        closeSubMenusFab()
        fabSetting.setOnClickListener {

            if (fabExpanded) {
                closeSubMenusFab()

            } else {
                openSubMenusFab()
            }
        }
        f1Layout.setOnClickListener { openF1() }
        f2Layout.setOnClickListener { openF2() }
        f3Layout.setOnClickListener { openF3() }
    }

    private fun openF3() {

        Toast.makeText(this, "You clicked F3", Toast.LENGTH_SHORT).show()
        closeSubMenusFab()
    }

    private fun openF2() {

        Toast.makeText(this, "You clicked F2", Toast.LENGTH_SHORT).show()
        closeSubMenusFab()
    }

    private fun openF1() {
        Toast.makeText(this, "You clicked F1", Toast.LENGTH_SHORT).show()
        closeSubMenusFab()
    }

    private fun startnavigation(ref_fragment: MapFragment?) {
        val fm = fragmentManager
        val dialogFragment = Object_Selection_fragment()
        dialogFragment.show(fm, "Sample Fragment")
        /*   IndoorwaySdk.instance()
                   .map()
                   .details(buildingUUID, mapUUID)
                   .setOnCompletedListener(Action1 {
                       var mapObjs: List<IndoorwayObjectParameters> = it.objects
                       for (itb in mapObjs) {
                           Log.d(TAG, "${itb.id} and ${itb.name}: ")
                       }
                       System.out.println("Buildling maps objects : ${Gson().toJson(it)}")
                   }

                   )


                   .setOnFailedListener(Action1 {
                       Log.d(TAG, "Building map objects Exception: ${it.message}")
                   })
                   .execute()*/
        /*       IndoorwaySdk.instance()
                       .building()
                       .listMaps(buildingUUID)
                       .setOnCompletedListener(Action1 {
                           Log.d(TAG, "Buildling maps list : ${Gson().toJson(it)}")
                       })
                       .setOnFailedListener(Action1 {
                           Log.d(TAG, "Building maplist Exception: ${it.message}")
                       })
                       .execute()*/

        /*     ref_fragment?.mapView?.navigation?.start("C4etnWJBuEg_6bb4a", "C4etnWJBuEg_b9ab8")
             Log.d(TAG, "navigation Steps: ${ref_fragment?.mapView?.navigation?.getSteps()}")
     */
        // ref_fragment?.mapView?.navigation?.start("C4etnWJBuEg_ecbd8", "C4etnWJBuEg_928a9")


    }

    override fun onStart() {
        super.onStart()

        IndoorwayLocationSdk.instance()
                .position()
                .onChange()
                .register(indoorwayPositionlistener)
    }

    override fun onResume() {
        super.onResume()
        IndoorwayLocationSdk.instance()
                .state()
                .onChange()
                .register(indoorLocationlistener)
        IndoorwayLocationSdk.instance()
                .state()
                .onError()
                .register(error_listener)

    }

    override fun onDestroy() {
        IndoorwayLocationSdk.instance()
                .state()
                .onChange()
                .unregister(indoorLocationlistener)
        IndoorwayLocationSdk.instance()
                .state()
                .onError()
                .register(error_listener)
        super.onDestroy()
    }

    private fun loadMap(fragment: MapFragment) {
        fragment.mapView
                // optional: assign callback for map loaded event
                .onMapLoadCompletedListener = Action1<IndoorwayMap> { indoorwayMap ->
            // called on every new map load success

            // access to paths graph
            paths = indoorwayMap.paths

            // access to map objects
            mapObjects = indoorwayMap.objects
            /*  val myLayer = ref_fragment?.mapView?.marker?.addLayer(7f)
              myLayer!!.add(
                      DrawableText(
                              room.id,
                              Coordinates(room.centerPoint.latitude, room.centerPoint.longitude),
                              room.name!!,
                              6f    // eg. 2f
                      )
              )*/

            /*    for (objname in mapObjects!!) {
                    val myLayer = ref_fragment?.mapView?.marker?.addLayer(7f)
                    myLayer!!.add(
                            DrawableText(
                                    objname.id,
                                    Coordinates(objname.centerPoint.latitude, objname.centerPoint.longitude),
                                    objname.name!!,
    
                                    3f    // eg. 2f
                            )
                    )
    
                }*/
            Log.d(TAG, "Paths: ${Gson().toJson(paths)} and Mapobjects ${Gson().toJson(mapObjects)}")
        }
        fragment.mapView.onMapLoadFailedListener = Action0 {
            Log.d(TAG, ": Map failed to load")
        }

        fragment.mapView.camera.setPosition(Coordinates(17.4379351, 78.38466970000002))
        // perform map loading using building UUID and map UUID

        fragment.mapView.load(buildingUUID, mapUUID)
        fragment.mapView.camera.setScale(10f, ScaleFactorType.PERCENTAGE)
    }

    fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    //closes FAB submenus
    private fun closeSubMenusFab() {
        f1Layout.visibility = View.INVISIBLE
        f2Layout.visibility = View.INVISIBLE
        f3Layout.visibility = View.INVISIBLE
        fabSetting.setImageResource(R.drawable.baseline_layers_white_24dp)
        fabExpanded = false

    }

    //Opens FAB submenus
    private fun openSubMenusFab() {

        f1Layout.visibility = View.VISIBLE
        f2Layout.visibility = View.VISIBLE
        f3Layout.visibility = View.VISIBLE
        //Change settings icon to 'X' icon
        fabSetting.setImageResource(R.drawable.baseline_close_white_24dp)
        fabExpanded = true

    }

}
