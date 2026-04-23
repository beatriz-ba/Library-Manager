package bci.core;

public class Regra2 implements Regra {
    @Override public int getId() { return 2; }

    @Override
    public boolean validar(Utente u, Obra o) {
        if (u.getEstado().equals("ACTIVO")) {
            return true;
        }
        return false;
    }
}
