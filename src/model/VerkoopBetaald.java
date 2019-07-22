package model;

public class VerkoopBetaald implements VerkoopState {
    @Override
    public String alert(Verkoop ctx) {
        return "Betaald";
    }
}
