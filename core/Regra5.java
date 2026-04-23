package bci.core;


public class Regra5 implements Regra {
    @Override
    public int getId() { return 5; }

    @Override
    public boolean validar(Utente u, Obra o) {
        return o.getCategoria() != Categoria.REFERENCE;
    }
}
