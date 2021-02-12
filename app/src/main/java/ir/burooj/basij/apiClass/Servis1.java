package ir.burooj.basij.apiClass;

public class Servis1 {
    private String imageUrl = "";
    private String lastUpdate = "";

    public Servis1(String imageUrl, String lastUpdate) {
        this.imageUrl = imageUrl;
        this.lastUpdate = lastUpdate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

}
