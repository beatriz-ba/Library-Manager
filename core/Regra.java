package bci.core;

import java.io.Serializable;

public interface Regra extends Serializable {
    int getId();
    boolean validar(Utente utente, Obra obra);
}


