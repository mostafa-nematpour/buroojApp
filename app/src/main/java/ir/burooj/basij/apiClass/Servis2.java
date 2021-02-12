package ir.burooj.basij.apiClass;

public class Servis2 {
    private String first="";
    private String second="";

    public Servis2(String first, String second) {
        this.first = first;
        this.second = second;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return
                "first='" + first + '\'' +
                ", second='" + second + '\'' +"\n";
    }
}
