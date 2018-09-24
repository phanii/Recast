package phani.recast.com.modal

import com.google.gson.annotations.SerializedName


data class MapObjectsBean(
        @SerializedName("buildingName") val buildingName: String, // Building Number 9
        @SerializedName("buildingUuid") val buildingUuid: String, // FqfSwbjQ3Lk
        @SerializedName("mapName") val mapName: String, // samplemap
        @SerializedName("mapUuid") val mapUuid: String, // C4etnWJBuEg
        @SerializedName("objects") val objects: List<Object>
) {

    data class Object(
            @SerializedName("bounds") val bounds: Bounds,
            @SerializedName("coordinates") val coordinates: List<Coordinate>,
            @SerializedName("drawPosition") val drawPosition: DrawPosition,
            @SerializedName("properties") val properties: Properties
    ) {

        data class DrawPosition(
                @SerializedName("latitude") val latitude: Double, // 17.4399472
                @SerializedName("longitude") val longitude: Double // 78.3812028
        )


        data class Properties(
                @SerializedName("indoorObject") val indoorObject: String, // yes
                @SerializedName("indoorObjectId") val indoorObjectId: String, // C4etnWJBuEg_31bcb
                @SerializedName("indoorObjectLabelPosition") val indoorObjectLabelPosition: IndoorObjectLabelPosition,
                @SerializedName("indoorObjectName") val indoorObjectName: String, // S2
                @SerializedName("indoorObjectTags") val indoorObjectTags: List<String>,
                @SerializedName("indoorObjectType") val indoorObjectType: String // room
        ) {

            data class IndoorObjectLabelPosition(
                    @SerializedName("latitude") val latitude: Double, // 17.44020195
                    @SerializedName("longitude") val longitude: Double // 78.38121269999999
            )
        }


        data class Coordinate(
                @SerializedName("latitude") val latitude: Double, // 17.4399472
                @SerializedName("longitude") val longitude: Double // 78.3812028
        )


        data class Bounds(
                @SerializedName("bottomRight") val bottomRight: BottomRight,
                @SerializedName("center") val center: Center,
                @SerializedName("heightMeters") val heightMeters: Double, // 2.1027999753274904
                @SerializedName("metersToLatRatio") val metersToLatRatio: Double, // 111319.49440665054
                @SerializedName("metersToLngRatio") val metersToLngRatio: Double, // 106202.01893441961
                @SerializedName("topLeft") val topLeft: TopLeft,
                @SerializedName("widthMeters") val widthMeters: Double // 56.71728240015712
        ) {

            data class Center(
                    @SerializedName("latitude") val latitude: Double, // 17.44020195
                    @SerializedName("longitude") val longitude: Double // 78.38121269999999
            )


            data class TopLeft(
                    @SerializedName("latitude") val latitude: Double, // 17.4404567
                    @SerializedName("longitude") val longitude: Double // 78.3812028
            )


            data class BottomRight(
                    @SerializedName("latitude") val latitude: Double, // 17.4399472
                    @SerializedName("longitude") val longitude: Double // 78.3812226
            )
        }
    }
}