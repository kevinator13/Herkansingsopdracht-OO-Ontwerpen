package model;

public class RekeningInstelling {
    private String instelling;
    private String boodschap;

    public RekeningInstelling(String instelling) {
        this.instelling = instelling;
        this.boodschap = null;
    }

    public RekeningInstelling(String instelling, String boodschap) {
        this.instelling = instelling;
        this.boodschap = boodschap;
    }

    public String getInstelling() {
        return instelling;
    }

    public String getBoodschap() {
        return boodschap;
    }
}
