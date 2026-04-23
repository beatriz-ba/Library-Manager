package bci.core;

import java.io.*;
import java.util.*;
import bci.core.exception.UnrecognizedEntryException;

/**
 * Responsável por ler o ficheiro textual (.import)
 * e preencher a biblioteca com os dados lidos.
 */
class MyParser {

  private Library _library;

  MyParser(Library lib) {
    _library = lib;
  }

  /** Lê o ficheiro linha a linha */
  void parseFile(String filename) throws IOException, UnrecognizedEntryException {
    try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = in.readLine()) != null)
        parseLine(line);
    }
  }

  /** Decide o que fazer com base no tipo de linha */
  private void parseLine(String line) throws UnrecognizedEntryException {
    String[] components = line.split(":");

    switch (components[0].trim().toUpperCase()) {
      case "USER":
        parseUser(components, line);
        break;
      case "BOOK":
        parseBook(components, line);
        break;
      case "DVD":
        parseDvd(components, line);
        break;
      default:
        throw new UnrecognizedEntryException("Tipo inválido " + components[0] + " na linha " + line);
    }
  }

  /** USER:id:nome */
  private void parseUser(String[] components, String line) throws UnrecognizedEntryException {
    if (components.length != 3)
      throw new UnrecognizedEntryException("Número inválido de campos (3) na descrição de um utente: " + line);

    String id = components[1].trim();
    String name = components[2].trim();

    // Método da Library que irá guardar o utente
    _library.addUtente(id, name);
  }

  /** BOOK:título:autores:preço:categoria:ISBN:nExemplares */
  private void parseBook(String[] components, String line) throws UnrecognizedEntryException {
    if (components.length != 7)
      throw new UnrecognizedEntryException("Número inválido de campos (7) na descrição de um Book: " + line);

    String title = components[1].trim();
    int price = Integer.parseInt(components[3].trim());
    Categoria category = Categoria.valueOf(components[4]);
    String isbn = components[5].trim();
    int copies = Integer.parseInt(components[6].trim());

    List<Criador> authors = new ArrayList<>();
    for (String a : components[2].split(","))
      authors.add(_library.registerCreator(a.trim()));

    _library.registerBook(title, authors, price, category, isbn, copies);
  }

  /** DVD:título:realizador:preço:categoria:IGAC:nExemplares */
  private void parseDvd(String[] components, String line) throws UnrecognizedEntryException {
    if (components.length != 7)
      throw new UnrecognizedEntryException("Número inválido de campos (7) na descrição de um DVD: " + line);

    String title = components[1].trim();
    Criador director = _library.registerCreator(components[2].trim());
    int price = Integer.parseInt(components[3].trim());
    Categoria category = Categoria.valueOf(components[4]);
    String igac = components[5].trim();
    int copies = Integer.parseInt(components[6].trim());

    _library.registerDvd(title, director, price, category, igac, copies);
  }
}
