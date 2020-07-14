package intujuku.beachalarm.ListItem;


/**
 * Created by p on 8/15/2017.
 */



public class MyBeachListItem {

    private int index;

    private String cityName;
    private String address;
    private int beachId;
    private String beachName;
    private int beachState;
    private String cctvId;
    private String lastUpdate;
    private String tell;
    private Double lat;
    private Double lng;

    private int image;


    public void setIndex(int index) {
        this.index = index;
    }

    public void setImage(int beachImage){
        this.image = beachImage;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setBeachId(int beachId) {
        this.beachId = beachId;
    }
    public void setBeachName(String beachName) {
        this.beachName = beachName;
    }
    public void setBeachState(int beachState) {
        this.beachState = beachState;
    }
    public void setCctvId(String cctvId) {
        this.cctvId = cctvId;
    }
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    public void setLat(Double lat) {
        this.lat = lat;
    }
    public void setLng(Double lng) {
        this.lng = lng;
    }
    public void setTell(String tell) {
        this.tell = tell;
    }


    public int getIndex() {
        return index;
    }

    public int getImage() {
        return this.image;
    }
    public String getCityName() {
        return this.cityName;
    }
    public String getAddress() {
        return address;
    }
    public int getBeachId() {
        return beachId;
    }
    public String getBeachName() {
        return beachName;
    }
    public int getBeachState() {
        return beachState;
    }
    public String getCctvId() {
        return cctvId;
    }
    public String getLastUpdate() {
        return lastUpdate;
    }
    public String getTell() {
        return tell;
    }
    public Double getLat() {
        return lat;
    }
    public Double getLng() {
        return lng;
    }

    public MyBeachListItem(String cityName, String address, int beachId, String beachName,
                           int beachState, String cctvId, String lastUpdate, String tell, Double lat, Double lng) {
        this.cityName = cityName;
        this.address = address;
        this.beachId = beachId;
        this.beachName = beachName;
        this.beachState = beachState;
        this.cctvId = cctvId;
        this.lastUpdate = lastUpdate;
        this.tell = tell;
        this.lat = lat;
        this.lng = lng;
    }
}
