package phani.recast.com.modal

import com.google.gson.annotations.SerializedName


data class OdspItemList(
        @SerializedName("lastPage") val lastPage: Boolean, // true
        @SerializedName("pageNumber") val pageNumber: Int, // 0
        @SerializedName("totalPages") val totalPages: Int, // 1
        @SerializedName("totalResults") val totalResults: Int, // 2
        @SerializedName("pageContent") val pageContent: List<PageContent>
) {

    data class PageContent(
            @SerializedName("version") val version: Int, // 0
            @SerializedName("itemId") val itemId: ItemId,
            @SerializedName("packageIdentifiers") val packageIdentifiers: List<Any>,
            @SerializedName("shortDescription") val shortDescription: ShortDescription,
            @SerializedName("longDescription") val longDescription: LongDescription,
            @SerializedName("status") val status: String, // ACTIVE
            @SerializedName("merchandiseCategory") val merchandiseCategory: MerchandiseCategory,
            @SerializedName("departmentId") val departmentId: String, // string
            @SerializedName("familyCode") val familyCode: String, // string
            @SerializedName("manufacturerCode") val manufacturerCode: String, // string
            @SerializedName("nonMerchandise") val nonMerchandise: Boolean // true
    ) {

        data class ShortDescription(
                @SerializedName("locale") val locale: String, // en-us
                @SerializedName("value") val value: String // cool drink
        )


        data class LongDescription(
                @SerializedName("locale") val locale: String, // en-us
                @SerializedName("value") val value: String // cool drink
        )


        data class MerchandiseCategory(
                @SerializedName("nodeId") val nodeId: String // 2
        )


        data class ItemId(
                @SerializedName("itemCode") val itemCode: String // 12345688
        )
    }
}