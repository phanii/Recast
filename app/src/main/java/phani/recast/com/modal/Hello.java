
package phani.recast.com.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hello {

    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("confidence")
    @Expose
    private Double confidence;
    @SerializedName("description")
    @Expose
    private String description;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Hello() {
    }

    /**
     * 
     * @param description
     * @param slug
     * @param confidence
     */
    public Hello(String slug, Double confidence, String description) {
        super();
        this.slug = slug;
        this.confidence = confidence;
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
