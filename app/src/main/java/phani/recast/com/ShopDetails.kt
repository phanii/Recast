package phani.recast.com

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_shop_details.*

class ShopDetails : AppCompatActivity() {

    private var collapsingToolbar: CollapsingToolbarLayout? = null

//    private var dessertAdapter: DessertAdapter? = null

    private var collapsedMenu: Menu? = null
    private var appBarExpanded = true
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_details)

        setSupportActionBar(anim_toolbar)
        if (supportActionBar != null)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        collapsing_toolbar.title = "Store Name"

        val bitmap = BitmapFactory.decodeResource(resources,
                R.drawable.header)

        Palette.from(bitmap).generate { palette ->
            val vibrantColor = palette.getVibrantColor(R.color.primary_500)
            collapsingToolbar?.setContentScrimColor(vibrantColor)
            collapsingToolbar?.setStatusBarScrimColor(R.color.black_trans80)
        }

        appbar.addOnOffsetChangedListener { _, verticalOffset ->
            Log.d(ShopDetails::class.java.simpleName, "onOffsetChanged: verticalOffset: $verticalOffset")

            //  Vertical offset == 0 indicates appBar is fully expanded.
            if (Math.abs(verticalOffset) > 200) {
                appBarExpanded = false
                invalidateOptionsMenu()
            } else {
                appBarExpanded = true
                invalidateOptionsMenu()
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        if (collapsedMenu != null && (!appBarExpanded || collapsedMenu!!.size() != 1)) {
            //collapsed
            collapsedMenu!!.add("Add")
                    .setIcon(R.drawable.heartoutline)
                    .setTitle("add")
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        } else {
            //expanded
        }
        return super.onPrepareOptionsMenu(collapsedMenu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        collapsedMenu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.action_settings -> return true
        }
        if (item.title === "add") {
            Toast.makeText(this, "clicked add", Toast.LENGTH_SHORT).show()

        }

        return super.onOptionsItemSelected(item)
    }
}
