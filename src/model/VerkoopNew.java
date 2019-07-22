package model;

public class VerkoopNew implements VerkoopState {
    @Override
    public String alert(Verkoop ctx) {
        return "new verkoop";
    }
}
