package ir.burooj.basij.apiClass;

public class Response {
    private String response = "";
    private boolean free = true;
    private String payment_link = "";

    public Response() {
    }

    public Response(String response) {
        this.response = response;
    }

    public Response(String response, boolean free, String payment_link) {
        this.response = response;
        this.free = free;
        this.payment_link = payment_link;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public String getPayment_link() {
        return payment_link;
    }

    public void setPayment_link(String payment_link) {
        this.payment_link = payment_link;
    }

    @Override
    public String toString() {
        return "Response{" +
                "response='" + response + '\'' +
                '}';
    }
}
