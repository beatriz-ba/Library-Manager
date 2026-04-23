package bci.core;


public class Regra1 implements Regra {
    @Override public int getId() { return 1; }

    @Override
    public boolean validar(Utente u, Obra o) {
        // verifica se já existe uma requisição aberta desta obra
        for (Requisicao r : u.getRequisicoes()) {
            if (!r.estadoDevolvido() && r.getObra().getId() == o.getId()) return false;
        }
        return true;
    }
}
