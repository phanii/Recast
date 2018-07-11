package phani.recast.com

import android.app.Application
import com.android.volley.RequestQueue
import android.text.TextUtils
import com.android.volley.Request
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley


class AppController : Application() {

    override fun onCreate() {
        super.onCreate()
        VolleyService.initialize(this)
    }
}