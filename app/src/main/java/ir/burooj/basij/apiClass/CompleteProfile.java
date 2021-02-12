package ir.burooj.basij.apiClass;

public class CompleteProfile {
    private String response="";

    public CompleteProfile(String response) {
        this.response = response;

    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
