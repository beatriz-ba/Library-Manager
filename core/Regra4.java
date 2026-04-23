package bci.core;


public class Regra4 implements Regra {
    @Override
    public int getId() { return 4; }

    @Override
    public boolean validar(Utente u, Obra o) {
        // Pede ao comportamento o limite máximo permitido
        int limite = u.getComportamento().getLimiteRequisicoes();

        // Conta quantas requisições o utente tem ainda não devolvidas
        int abertas = 0;
        for (Requisicao r : u.getRequisicoes()) {
            if (!r.estadoDevolvido()) {
                abertas++;
            }
        }

        // A regra passa se ainda estiver abaixo do limite
        return abertas < limite;
    }
}
