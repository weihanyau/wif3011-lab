package examples;

/**
 *
 * @author Chiew Thiam Kian
 */
public class VolatileTest2 {

    private volatile int vol = 0;

    public VolatileTest2(int vol) {
        this.vol = vol;
    }

    public void setVol(int vol) {
        this.vol = vol;
    }

    public int getVol() {
        return vol;
    }

}
