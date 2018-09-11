package phani.recast.com

import android.app.Application
import android.content.ContextWrapper
import com.indoorway.android.common.sdk.IndoorwaySdk
import com.pixplicity.easyprefs.library.Prefs


class CustomeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        IndoorwaySdk.initContext(this)
        IndoorwaySdk.configure("b16fc663-bc0a-4403-a58d-35348ebdea6a")
        Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(packageName)
                .setUseDefaultSharedPreference(true)
                .build()
    }
}