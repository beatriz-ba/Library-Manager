package bci.core;

public class Regra3 implements Regra {
    @Override
    public int getId() { return 3; }

    @Override
    public boolean validar(Utente u, Obra o) {
        if (o.isDisponivel()) {
            return true;
        }
		return false;
    }
}
