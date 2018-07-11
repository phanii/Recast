
package phani.recast.com.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Memory {

    @SerializedName("hi")
    @Expose
    private Hi hi;
    @SerializedName("hello")
    @Expose
    private Hello hello;
    @SerializedName("fruits")
    @Expose
    private Fruits fruits;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Memory() {
    }

    /**
     * 
     * @param hello
     * @param fruits
     * @param hi
     */
    public Memory(Hi hi, Hello hello, Fruits fruits) {
        super();
        this.hi = hi;
        this.hello = hello;
        this.fruits = fruits;
    }

    public Hi getHi() {
        return hi;
    }

    public void setHi(Hi hi) {
        this.hi = hi;
    }

    public Hello getHello() {
        return hello;
    }

    public void setHello(Hello hello) {
        this.hello = hello;
    }

    public Fruits getFruits() {
        return fruits;
    }

    public void setFruits(Fruits fruits) {
        this.fruits = fruits;
    }

}
