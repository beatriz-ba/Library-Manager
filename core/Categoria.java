package bci.core;

public enum Categoria {
    FICTION(0), SCITECH(1), REFERENCE(3);
    
    private final int _value;
    
    private Categoria(int value) {
        _value = value;
    }
    
    int getValue() {
        return _value;
    }

    String getname () {
        if(_value == 0) {
            return "Ficção";
        }
        else if (_value == 1) {
            return "Técnica e Científica";
        }
        return "Referência";
    }
}
