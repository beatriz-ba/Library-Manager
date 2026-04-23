package bci.core;

public enum TipoNotificacao {
    DISPONIBILIDADE(0),
    REQUISICAO(1);
    
    private final int _value ;
    
    private TipoNotificacao(int value) {
        _value = value;
    }
    
    int getValue() {
        return _value;
    }
    
}
