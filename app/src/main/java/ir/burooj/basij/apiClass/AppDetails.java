package ir.burooj.basij.apiClass;

public class AppDetails {

    private String isNews;
    private String popupImage;
    private String popupTitle;
    private String popupDesc;
    private String minVersion;
    private String downloadLink;
    private String telegram_link;
    private String week_state;
    private String week_string;


    public AppDetails(String isNews, String popupImage, String popupTitle, String popupDesc,
                      String minVersion, String downloadLink, String telegram_link,
                      String week_state, String week_string) {
        this.isNews = isNews;
        this.popupImage = popupImage;
        this.popupTitle = popupTitle;
        this.popupDesc = popupDesc;
        this.minVersion = minVersion;
        this.downloadLink = downloadLink;
        this.telegram_link = telegram_link;
        this.week_state = week_state;
        this.week_string = week_string;
    }

    public String getTelegram_link() {
        return telegram_link;
    }

    public void setTelegram_link(String telegram_link) {
        this.telegram_link = telegram_link;
    }

    public String getIsNews() {
        return isNews;
    }

    public void setIsNews(String isNews) {
        this.isNews = isNews;
    }

    public String getPopupImage() {
        return popupImage;
    }

    public void setPopupImage(String popupImage) {
        this.popupImage = popupImage;
    }

    public String getPopupTitle() {
        return popupTitle;
    }

    public void setPopupTitle(String popupTitle) {
        this.popupTitle = popupTitle;
    }

    public String getPopupDesc() {
        return popupDesc;
    }

    public void setPopupDesc(String popupDesc) {
        this.popupDesc = popupDesc;
    }

    public String getMinVersion() {
        return minVersion;
    }

    public void setMinVersion(String minVersion) {
        this.minVersion = minVersion;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getWeek_state() {
        return week_state;
    }

    public void setWeek_state(String week_state) {
        this.week_state = week_state;
    }

    public String getWeek_string() {
        return week_string;
    }

    public void setWeek_string(String week_string) {
        this.week_string = week_string;
    }

    @Override
    public String toString() {
        return "AppDetails{" +
                "isNews='" + isNews + '\'' +
                ", popupImage='" + popupImage + '\'' +
                ", popupTitle='" + popupTitle + '\'' +
                ", popupDesc='" + popupDesc + '\'' +
                ", minVersion='" + minVersion + '\'' +
                ", downloadLink='" + downloadLink + '\'' +
                ", telegram_link='" + telegram_link + '\'' +
                ", week_state='" + week_state + '\'' +
                ", week_string='" + week_string + '\'' +
                '}';
    }
}
