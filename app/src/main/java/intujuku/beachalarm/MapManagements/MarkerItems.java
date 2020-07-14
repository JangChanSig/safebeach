package intujuku.beachalarm.MapManagements;

public class MarkerItems {

    private double lat;
    private double lng;
    private int index;
    private int signal;
    public MarkerItems(double lat, double lng, int signal, int index){
        this.lat = lat;
        this.lng = lng;
        this.index = index;
        this.signal = signal;
    }
    public double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public double getLng() {
        return lng;
    }
    public void setLng(double lng) {
        this.lng = lng;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public int getSignal() {
        return signal;
    }
    public void setSignal(int signal) {
        this.signal = signal;
    }


}
