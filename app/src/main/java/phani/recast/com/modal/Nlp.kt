package phani.recast.com.modal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Nlp @JvmOverloads constructor( var uuid: String? = null,var source: String? = null,var intents: List<Intent>? = null,var act: String? = null, var type: Any? = null,
                                     var sentiment: String? = null,var entities: Entities? = null,var language: String? = null,var processingLanguage: String? = null,
                                     var version: String? = null,var timestamp: String? = null,var status: Int? = null)