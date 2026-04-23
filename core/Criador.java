package bci.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;

public class Criador implements Serializable {
    private String _nome;
    private List<Obra> _obras;
    
    Criador (String nome) {
        _nome = nome;
        _obras = new ArrayList<>();
    }

    List<Obra> getObras() {
        return Collections.unmodifiableList(_obras);
    }

    void addObras(Obra obra) {
        _obras.add(obra);
    }

    String getNome () {
        return _nome;
    }

    @Override
    public String toString() {
        return _nome;
    }

    boolean removeObra(Obra o) {
        Iterator<Obra> it = _obras.iterator();
        while (it.hasNext()) {
            Obra atual = it.next();
            if (atual.getId() == o.getId()) {
                it.remove();
                break;
            }
        }
        return _obras.isEmpty();
    }
    
}
