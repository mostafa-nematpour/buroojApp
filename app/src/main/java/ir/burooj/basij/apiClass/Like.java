package ir.burooj.basij.apiClass;

public class Like {

    private String response = "";
    private String action = "";

    public Like(String response, String action) {
        this.response = response;
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

}
