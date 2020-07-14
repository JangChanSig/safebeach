package intujuku.beachalarm.ListItem;

/**
 * Created by p on 8/15/2017.
 */

public class myD2ListItem {
    private int image;
    private String storeName;
    private String storeAddress;
    private String contents;

    private int storeId;
    private String storePhone;
    private String instarUrl;
    private String storeType;
    private Double lat;
    private Double lng;

    public int getImage() {
        return this.image;
    }
    public String getStoreName() {
        return this.storeName;
    }
    public String getStoreAddress()
    {
        return this.storeAddress;
    }
    public String getContents()
    {
        return this.contents;
    }
    public int getStoreId() {
        return storeId;
    }
    public String getStorePhone() {
        return storePhone;
    }
    public String getStoreType() {
        return storeType;
    }
    public String getInstarUrl() {
        return instarUrl;
    }
    public Double getLng() {
        return lng;
    }
    public Double getLat() {
        return lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
    public void setLat(Double lat) {
        this.lat = lat;
    }
    public void setContents(String contents) {
        this.contents = contents;
    }
    public void setImage(int image) {
        this.image = image;
    }
    public void setInstarUrl(String instarUrl) {
        this.instarUrl = instarUrl;
    }
    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }
    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }
    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public myD2ListItem(int storeId, String storeName, String storePhone, String instarUrl, String storeType, String storeAddress, String contents, Double lat, Double lng){
        this.image = image;
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.contents = contents;
        this.storePhone = storePhone;
        this.instarUrl = instarUrl;
        this.storeType = storeType;
        this.lat = lat;
        this.lng = lng;
    }

}
