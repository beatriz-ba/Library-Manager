
package bci.core;
import java.util.List;
import java.util.ArrayList;



public class Dvd extends Obra {
    private String _igac; 
    private Criador _realizador;
    
    Dvd(Library library, int id, int quantidadeExemplares, String titulo, Categoria categoria, int preco, String igac, Criador criador) {
        super(library, id, quantidadeExemplares, titulo, categoria, preco);
        _realizador = criador;
        _igac = igac;
    }
    
    @Override
    String getTipo() {
        return "DVD";
    }

    @Override 
    List<Criador> getCriador() {
        List<Criador> lista = new ArrayList<>();
        lista.add(_realizador);
        return lista;
    }
    

    @Override 
    public String toString() {
        return super.toString() + _realizador + " - " + _igac;
    }
    @Override
    public boolean matches(String termo) {
        return super.matches(termo) || contemIgnorandoMaiusculas(_realizador.getNome(), termo);
}

    @Override
    void inventarioAZero() {
        if (_realizador.removeObra(this)) {
            _library.removerCriador(_realizador.getNome());
        }
    }
}