package phani.recast.com

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main_launching.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import phani.recast.com.modal.EventNavigatefromList
import phani.recast.com.views.NavigationHome
import phani.recast.com.views.fragments.ChatScreen
import phani.recast.com.views.fragments.ListofDeptsFragment
import phani.recast.com.views.fragments.ProductsFragment

class MainLaunchingActivity : AppCompatActivity() {
    lateinit var toolbar: ActionBar

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_products -> {
                toolbar.subtitle = "Products List"
                val productsFragment = ProductsFragment.newInstance()
                openFragment(productsFragment)
                message.setText(R.string.title_home)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_bot -> {
                toolbar.subtitle = "Chat with BoT"
                message.setText(R.string.title_dashboard)
                val chatscreenFragment = ChatScreen.newInstance()
                openFragment(chatscreenFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_departmentslist -> {
                toolbar.subtitle = "Departments List"
                val deptsFragment = ListofDeptsFragment.newInstance()
                openFragment(deptsFragment)
                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_launching)
        toolbar = supportActionBar!!
        toolbar.title = "NCR Retail BoT"
        // setSupportActionBar(toolbar_bot)
        toolbar.subtitle = "Chat with BoT"
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        // navigation.selectedItemId = R.id.navigation_bot
        navigation.menu.findItem(R.id.navigation_bot).isChecked = true
        openFragment(ChatScreen.newInstance())

    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun openNavigationStoreMap(eventNavigate: EventNavigatefromList) {
        Log.d(ChatScreen.TAG, "EventNavigate: ${eventNavigate.navigate} Store Name ${eventNavigate.navigateToShowRoomName}")
        startActivity(Intent(this@MainLaunchingActivity, NavigationHome::class.java))
    }

}
