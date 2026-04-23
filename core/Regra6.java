package bci.core;


public class Regra6 implements Regra {
    @Override
    public int getId() { return 6; }

    @Override
    public boolean validar(Utente u, Obra o) {
        // Se a obra custa mais de 25€
        if (o.getPreco() > 25) {
            // pergunta à estratégia se ele pode requisitar superior a 25 euros
            return u.getComportamento().verificaPrecoLimite();
        }
        return true;
    }
}
