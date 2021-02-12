package ir.burooj.basij.apiClass;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Event {
    @SerializedName("can_buy")
    private String can_buy;
    @SerializedName("description")
    private String description;
    @SerializedName("end_date")
    private String end_date;
    @SerializedName("end_time")
    private String end_time;
    @SerializedName("groupName")
    private String groupName;
    @SerializedName("id")
    private String id;
    @SerializedName("imageUrl")
    private String imageUrl;
    @SerializedName("messages")
    private List<MessageModel> messages;
    @SerializedName("price")
    private String price;
    @SerializedName("registered")
    private String registered;
    @SerializedName("remainingTickets")
    private String remainingTickets;
    @SerializedName("response")
    String response;
    @SerializedName("start_date")
    private String start_date;
    @SerializedName("start_time")
    private String start_time;
    @SerializedName("ticketToken")
    private String ticketToken;
    @SerializedName("ticket_end_date")
    private String ticket_end_date;
    @SerializedName("title")
    private String title;
    @SerializedName("type")
    private String type;

    public Event() {
    }

    public Event(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, List<MessageModel> list, String str17) {
        this.id = str;
        this.title = str2;
        this.description = str3;
        this.remainingTickets = str4;
        this.can_buy = str5;
        this.start_date = str6;
        this.end_date = str7;
        this.imageUrl = str8;
        this.groupName = str9;
        this.start_time = str10;
        this.end_time = str11;
        this.response = str12;
        this.ticket_end_date = str13;
        this.registered = str14;
        this.ticketToken = str15;
        this.price = str16;
        this.messages = list;
        this.type = str17;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String str) {
        this.price = str;
    }

    public List<MessageModel> getMessages() {
        return this.messages;
    }

    public void setMessages(List<MessageModel> list) {
        this.messages = list;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String str) {
        this.response = str;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public String getRemainingTickets() {
        return this.remainingTickets;
    }

    public void setRemainingTickets(String str) {
        this.remainingTickets = str;
    }

    public String getCan_buy() {
        return this.can_buy;
    }

    public void setCan_buy(String str) {
        this.can_buy = str;
    }

    public String getStart_date() {
        return this.start_date;
    }

    public void setStart_date(String str) {
        this.start_date = str;
    }

    public String getEnd_date() {
        return this.end_date;
    }

    public void setEnd_date(String str) {
        this.end_date = str;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String str) {
        this.imageUrl = str;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String str) {
        this.groupName = str;
    }

    public String getStart_time() {
        return this.start_time;
    }

    public void setStart_time(String str) {
        this.start_time = str;
    }

    public String getEnd_time() {
        return this.end_time;
    }

    public void setEnd_time(String str) {
        this.end_time = str;
    }

    public String getTicket_end_date() {
        return this.ticket_end_date;
    }

    public void setTicket_end_date(String str) {
        this.ticket_end_date = str;
    }

    public String getRegistered() {
        return this.registered;
    }

    public void setRegistered(String str) {
        this.registered = str;
    }

    public String getTicketToken() {
        return this.ticketToken;
    }

    public void setTicketToken(String str) {
        this.ticketToken = str;
    }

    private void x() {
        new ArrayList();
        Event event = (Event) new Gson().fromJson("jsonString", Event.class);
    }

    public String toString() {
        return "Events{id='" + this.id + '\'' + ", title='" + this.title + '\'' + ", description='" + this.description + '\'' + ", remainingTickets='" + this.remainingTickets + '\'' + ", can_buy='" + this.can_buy + '\'' + ", start_date='" + this.start_date + '\'' + ", end_date='" + this.end_date + '\'' + ", imageUrl='" + this.imageUrl + '\'' + ", groupName='" + this.groupName + '\'' + ", start_time='" + this.start_time + '\'' + ", end_time='" + this.end_time + '\'' + ", response='" + this.response + '\'' + ", ticket_end_date='" + this.ticket_end_date + '\'' + ", registered='" + this.registered + '\'' + ", ticketToken='" + this.ticketToken + '\'' + '}';
    }
}