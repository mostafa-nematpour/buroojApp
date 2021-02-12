package ir.burooj.basij.apiClass;

import com.google.gson.annotations.SerializedName;

public class GroupMembers {
    @SerializedName("name")
    final private String name;

    public GroupMembers(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "GroupMembers{" +
                "name='" + name + '\'' +
                '}';
    }
}
