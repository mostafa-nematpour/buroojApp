package ir.burooj.basij.more;

public class LoanModel {
    private String title;
    private String text;
    private String downTitle;
    private String downColor;

    public LoanModel(String title, String text, String downTitle, String downColor) {
        this.title = title;
        this.text = text;
        this.downTitle = downTitle;
        this.downColor = downColor;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDownTitle() {
        return downTitle;
    }

    public void setDownTitle(String downTitle) {
        this.downTitle = downTitle;
    }

    public String getDownColor() {
        return downColor;
    }

    public void setDownColor(String downColor) {
        this.downColor = downColor;
    }
}
