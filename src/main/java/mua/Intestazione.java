package mua;

/** Interfaccia che descrive i metodi necessari a rappresentare un'intestazione */
public interface Intestazione {

  /**
   * Metodo che mi restituisce il tipo dell'intestazione
   *
   * @return una stringa relativa al tipo dell'intestazione
   */
  String tipo();

  /**
   * Metodo che mi restituisce il valore di un'intestazione
   *
   * @return una stringa relativa al valore dell'intestazione
   */
  String valore();

  /** Classe statica che definisce una intestazione di un testo in HTML */
  Intestazione HTML_INTESTAZIONE =
      new Intestazione() {
        @Override
        public String tipo() {
          return "Content-Type";
        }

        @Override
        public String valore() {
          return "text/html; charset=\"utf-8\"";
        }
      };

  /** Classe statica che definisce una intestazione di un testo in caratteri ASCII */
  Intestazione ASCII_INTESTAZIONE =
      new Intestazione() {
        @Override
        public String tipo() {
          return "Content-Type";
        }

        @Override
        public String valore() {
          return "text/plain; charset=\"us-ascii\"";
        }
      };

  /** Classe statica che definisce una intestazione di un testo in caratteri non ASCII */
  Intestazione NON_ASCII_INTESTAZIONE =
      new Intestazione() {
        @Override
        public String tipo() {
          return "Content-Type";
        }

        @Override
        public String valore() {
          return "text/plain; charset=\"utf-8\"";
        }
      };

  /** Classe statica che definisce una intestazione di un testo con una versione di MIME 1.0 */
  Intestazione INTESTAZIONE_MIME =
      new Intestazione() {
        @Override
        public String tipo() {
          return "MIME-Version";
        }

        @Override
        public String valore() {
          return "1.0";
        }
      };

  /** Classe statica che definisce una intestazione di un messaggio costituito da più parti */
  Intestazione INTESTAZIONE_MULTIPARTE =
      new Intestazione() {
        @Override
        public String tipo() {
          return "Content-Type";
        }

        @Override
        public String valore() {
          return "multipart/alternative; boundary=frontier";
        }
      };

  /** Classe statica che definisce una intestazione del transfer-encoding */
  Intestazione INTESTAZIONE_TRANSFER_ENCODING =
      new Intestazione() {
        @Override
        public String tipo() {
          return "Content-Transfer-Encoding";
        }

        @Override
        public String valore() {
          return "base64";
        }
      };
}
