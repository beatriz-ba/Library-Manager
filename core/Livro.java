package bci.core;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Livro extends Obra {
    private List<Criador> _autores;
    private String _isbn;
    
    /**
     * Construtor para Livro com múltiplos autores
     * @param library Biblioteca a que pertence o livro
     * @param id Identificador único do livro
     * @param quantidadeExemplares Quantidade total de exemplares
     * @param titulo Título do livro
     * @param categoria Categoria do livro
     * @param preco Preço do livro
     * @param isbn ISBN do livro
     * @param autores Lista de autores
     */
    Livro(Library library, int id, int quantidadeExemplares, String titulo, 
                 Categoria categoria, int preco, String isbn, List<Criador> autores) {
        super(library, id, quantidadeExemplares, titulo, categoria, preco);
        _autores = new ArrayList<>(autores);
        _isbn = isbn;
    }
    
    
    @Override
    String getTipo() {
        return "Livro";
    }
    

    @Override
    List<Criador> getCriador() {
        return Collections.unmodifiableList(_autores);
    }


    String getAutoresString() {
        String frase = "";
        for (Criador criador : _autores) {
            frase += criador + "; ";
        }
        // remover o último ponto e vírgula, se existir
        if (!frase.isEmpty()) {
            frase = frase.substring(0, frase.length() - 2);
        }
        return frase;
    }

    @Override
    public String toString() {
        return super.toString() + getAutoresString() + " - " + _isbn;
    }
    
    @Override
    public boolean matches(String termo) {
        if (super.matches(termo)) return true;
        // qualquer autor (nome)
        for (Criador autor : _autores) {
            if (contemIgnorandoMaiusculas(autor.getNome(), termo)) return true;
        }
        return false;
    }

    @Override
    void inventarioAZero() {
        for (Criador c : _autores) {
            if(c.removeObra(this)){
                _library.removerCriador(c.getNome());
            }
        }
    }

}
