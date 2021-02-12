package ir.burooj.basij.apiClass;

public class generatePhoneToken {

    private Integer response;
    private String token;

    public generatePhoneToken(Integer response, String token) {
        this.response = response;
        this.token = token;
    }
    public Integer getResponse() {
        return response;
    }

    public void setResponse(Integer response) {
        this.response = response;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
