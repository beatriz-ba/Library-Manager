package bci.core;

public enum Classificacao {
    FALTOSO(0),
    NORMAL(1),
    CUMPRIDOR(2);
    
    private final int _value;
    
    private Classificacao(int value) {
        _value = value;
    }
    
    int getValue() {
        return _value;
    }
    
    
    Classificacao fromValue(int value) {
        for (Classificacao c : Classificacao.values()) {
            if (c.getValue() == value) {
                return c;
            }
        }
        return NORMAL;
    
    }
}
