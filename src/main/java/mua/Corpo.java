package mua;

/** Intefaccia che gestisce il corpo di una parte di messaggio */
public interface Corpo {

  /**
   * Metodo che restituisce il corpo codificato
   *
   * @return il corpo codificato
   */
  String codifica();

  /**
   * Metodo che restituisce il corpo decodificato
   *
   * @return il corpo decodificato
   */
  String decodifica();
}
