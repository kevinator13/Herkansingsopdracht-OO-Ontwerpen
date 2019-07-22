package model;

public class VerkoopOnHold implements VerkoopState {
    @Override
    public String alert(Verkoop ctx) {
        return "Verkoop is on hold";
    }
}
