package phani.recast.com.modal

import android.text.format.DateFormat
import java.io.Serializable
import java.util.concurrent.TimeUnit

class ChatMessageModal(var type: String?, var message: Any?, var time: Long?, var direction: Type?) : Serializable
{
    fun getFormattedTime(): String {

        val oneDayInMillis = TimeUnit.DAYS.toMillis(1) // 24 * 60 * 60 * 1000;

        val timeDifference = System.currentTimeMillis() - this.time!!

        return if (timeDifference < oneDayInMillis)
            DateFormat.format("hh:mm a", time!!).toString()
        else
            DateFormat.format("dd MMM - hh:mm a", time!!).toString()
    }

    enum class Type {
        SENT, RECEIVED,TYPING
    }
}
