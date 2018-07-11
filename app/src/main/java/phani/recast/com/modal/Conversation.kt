package phani.recast.com.modal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Conversation @JvmOverloads constructor(val id:String?="",val language: String?=null,val memory: Memory? = null,val skill: String? = null,val skillOccurences: Int? = null)