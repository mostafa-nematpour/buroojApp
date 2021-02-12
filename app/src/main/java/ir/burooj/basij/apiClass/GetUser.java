package ir.burooj.basij.apiClass;

import com.google.gson.annotations.SerializedName;

public class GetUser {
    @SerializedName("phone_number")
    private String phone_number = "";
    @SerializedName("name")
    private String name = "";
    @SerializedName("lastname")
    private String lastname = "";
    @SerializedName("register_date")
    private String register_date = "";
    @SerializedName("reshte_edu")
    private String reshte_edu = "";
    @SerializedName("enter_year")
    private String enter_year = "";
    @SerializedName("shomare_daneshjouyi")
    private String shomare_daneshjouyi = "";
    @SerializedName("client_ver")
    private String client_ver = "";
    @SerializedName("ban")
    private String ban = "";
    @SerializedName("verified")
    private String verified = "";
    @SerializedName("response")
    private int response = 0;
    @SerializedName("complete")
    private boolean complete;
    @SerializedName("invitation_code")
    private String invitationCode="";
    @SerializedName("total_invitations")
    private String totalInvitation="0";


    public GetUser(int response) {
        this.response = response;
    }

    public GetUser(String phone_number, String name, String lastname, String register_date,
                   String reshte_edu, String enter_year, String shomare_daneshjouyi,
                   String client_ver, String ban, String verified, int response, boolean complete,
                   String invitationCode, String totalInvitation) {
        this.phone_number = phone_number;
        this.name = name;
        this.lastname = lastname;
        this.register_date = register_date;
        this.reshte_edu = reshte_edu;
        this.enter_year = enter_year;
        this.shomare_daneshjouyi = shomare_daneshjouyi;
        this.client_ver = client_ver;
        this.ban = ban;
        this.verified = verified;
        this.response = response;
        this.complete = complete;
        this.invitationCode = invitationCode;
        this.totalInvitation = totalInvitation;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getTotalInvitation() {
        return totalInvitation;
    }

    public void setTotalInvitation(String totalInvitation) {
        this.totalInvitation = totalInvitation;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }

    public String getReshte_edu() {
        return reshte_edu;
    }

    public void setReshte_edu(String reshte_edu) {
        this.reshte_edu = reshte_edu;
    }

    public String getEnter_year() {
        return enter_year;
    }

    public void setEnter_year(String enter_year) {
        this.enter_year = enter_year;
    }

    public String getShomare_daneshjouyi() {
        return shomare_daneshjouyi;
    }

    public void setShomare_daneshjouyi(String shomare_daneshjouyi) {
        this.shomare_daneshjouyi = shomare_daneshjouyi;
    }

    public String getClient_ver() {
        return client_ver;
    }

    public void setClient_ver(String client_ver) {
        this.client_ver = client_ver;
    }

    public String getBan() {
        return ban;
    }

    public void setBan(String ban) {
        this.ban = ban;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }


    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @Override
    public String toString() {
        return "GetUser{" +
                "phone_number='" + phone_number + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", register_date='" + register_date + '\'' +
                ", reshte_edu='" + reshte_edu + '\'' +
                ", enter_year='" + enter_year + '\'' +
                ", shomare_daneshjouyi='" + shomare_daneshjouyi + '\'' +
                ", client_ver='" + client_ver + '\'' +
                ", ban='" + ban + '\'' +
                ", verified='" + verified + '\'' +
                ", response=" + response +
                ", complete=" + complete +
                '}';
    }
}
