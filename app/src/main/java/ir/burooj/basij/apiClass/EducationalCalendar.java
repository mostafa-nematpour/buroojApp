package ir.burooj.basij.apiClass;

import androidx.annotation.NonNull;

public class EducationalCalendar {
    private String response;
    private String link;
    private String pdfLink;
    private boolean flag;


    public EducationalCalendar(String response, String link, String pdfLink, boolean flag) {
        this.response = response;
        this.link = link;
        this.pdfLink = pdfLink;
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPdfLink() {
        return pdfLink;
    }

    public void setPdfLink(String pdfLink) {
        this.pdfLink = pdfLink;
    }

    @Override
    public String toString() {
        return "EducationalCalendar{" +
                "response='" + response + '\'' +
                ", link='" + link + '\'' +
                ", pdfLink='" + pdfLink + '\'' +
                ", flag=" + flag +
                '}';
    }
}
