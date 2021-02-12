package ir.burooj.basij.apiClass;

public class verifyPhone {

    private Integer response;
    private String haveAccount;
    private String id;
    private String token;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public verifyPhone(Integer response, String haveAccount) {
        this.response = response;
        this.haveAccount = haveAccount;
    }

    public String getHaveAccount() {
        return haveAccount;
    }

    public void setHaveAccount(String haveAccount) {
        this.haveAccount = haveAccount;
    }

    public verifyPhone(Integer response) {
        this.response = response;
    }

    public Integer getResponse() {
        return response;
    }

    public void setResponse(Integer response) {
        this.response = response;
    }
}
