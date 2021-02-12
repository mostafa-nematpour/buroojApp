package ir.burooj.basij;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Item {

    private String postId;
    private String title;
    private String description;
    private String imageUrl;
    private String date;
    private String user;
    private String nickname;
    private String liked;
    private String likes;
    private String time;
    private String response;
    private String writerId;
    private String profile_image_path;

    public Item(String postId, String title, String description, String imageUrl, String date,
                String user, String nickname, String liked, String likes, String time,
                String response, String writerId, String profile_image_path,
                String view) {
        this.postId = postId;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.date = date;
        this.user = user;
        this.nickname = nickname;
        this.liked = liked;
        this.likes = likes;
        this.time = time;
        this.response = response;
        this.writerId = writerId;
        this.profile_image_path = profile_image_path;
        this.view = view;
    }



    public String getProfile_image_path() {
        return profile_image_path;
    }

    public void setProfile_image_path(String profile_image_path) {
        this.profile_image_path = profile_image_path;
    }

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
    }

    private String view;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }


    String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }


    @Override
    public String toString() {
        return "Item{" +
                "postId='" + postId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", date='" + date + '\'' +
                ", user='" + user + '\'' +
                ", nickname='" + nickname + '\'' +
                ", liked='" + liked + '\'' +
                ", likes='" + likes + '\'' +
                ", time='" + time + '\'' +
                ", response='" + response + '\'' +
                ", writerId='" + writerId + '\'' +
                ", view='" + view + '\'' +
                '}';
    }
}
