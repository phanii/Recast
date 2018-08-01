package phani.recast.com

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.speachtotext.*
import java.util.*
import kotlin.collections.ArrayList

class SpeeachToText : AppCompatActivity() {
    var list: ArrayList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.speachtotext)
        list = ArrayList()
        speachbtn.setOnClickListener {

            openMic()
        }
    }

    private fun openMic() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.saysomething))
        try {
            startActivityForResult(intent, REQ_CODE)
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.speachnotsupported), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_CODE -> {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    list = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    speachtext.text = list!![0]
                }
            }
        }
    }

    companion object {
        const val REQ_CODE = 100

    }
}