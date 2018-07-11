package phani.recast.com.modal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Intent @JvmOverloads constructor(var slug: String? = null,var confidence: Double? = null,var description: String? = null)