package ir.burooj.basij.apiClass;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GroupL {
    @SerializedName("id")
     private String id;
    @SerializedName("group_name")
     private String groupName;
    @SerializedName("profile_image_path")
     private String profile_image_path;
    @SerializedName("description")
     private String description;
    @SerializedName("response")
     private String response;
    @SerializedName("message")
     private String message;
    @SerializedName("members")
    private List<GroupMembers> members;


    public GroupL(String id, String groupName, String profile_image_path, String description,
                  String response, String message, List<GroupMembers> members) {
        this.id = id;
        this.groupName = groupName;
        this.profile_image_path = profile_image_path;
        this.description = description;
        this.response = response;
        this.message = message;
        this.members = members;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setProfile_image_path(String profile_image_path) {
        this.profile_image_path = profile_image_path;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getProfile_image_path() {
        return profile_image_path;
    }

    public String getDescription() {
        return description;
    }

    public List<GroupMembers> getMembers() {
        return members;
    }

    public void setMembers(List<GroupMembers> members) {
        this.members = members;
    }
}
