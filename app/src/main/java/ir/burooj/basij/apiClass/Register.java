package ir.burooj.basij.apiClass;

public class Register {

    private int response;
    private String id;
    private String token;

    public Register(int response, String id, String token) {
        this.response = response;
        this.id = id;
        this.token = token;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }


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
}
