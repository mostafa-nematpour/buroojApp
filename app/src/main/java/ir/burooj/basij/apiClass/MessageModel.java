package ir.burooj.basij.apiClass;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class MessageModel {
    @SerializedName("id")
    private String id;
    @SerializedName("text")
    private String text;

    public MessageModel(String str, String str2) {
        this.id = str;
        this.text = str2;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String str) {
        this.text = str;
    }

    @NotNull
    public String toString() {
        return this.id + "\n" + this.text;
    }
}
