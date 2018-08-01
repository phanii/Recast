package phani.recast.com.modal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Element {

    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("imageUrl")
    @Expose
    var imageUrl: String? = null
    @SerializedName("subtitle")
    @Expose
    var subtitle: String? = null
    @SerializedName("buttons")
    @Expose
    var buttons: List<Button>? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor()

    /**
     *
     * @param title
     * @param imageUrl
     * @param subtitle
     * @param buttons
     */
    constructor(title: String, imageUrl: String, subtitle: String, buttons: List<Button>) : super() {
        this.title = title
        this.imageUrl = imageUrl
        this.subtitle = subtitle
        this.buttons = buttons
    }

}
