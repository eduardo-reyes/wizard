package edd.src.Juego;

public class Carta {
  // Constantes que representan los valores de los palos de cartas.
  public final static int AZUL = 0;
  public final static int VERDE = 1;
  public final static int AMARILLO = 2;
  public final static int ROJO = 3;
  public final static int BUFON = 4;
  public final static int MAGO = 5;

  // Atributos de la clase carta.
  private final int valor;
  private final int palo;

  /**
   * Constructor de carta.
   * @param valor es el valor numérico de la carta.
   * @param palo es el valor numérico del palo.
   */
  public Carta(int valor, int palo) {
    if(palo != AZUL && palo != VERDE && palo != AMARILLO && palo != ROJO && palo != BUFON && palo != MAGO) {
      throw new IllegalArgumentException("Palo de carta no válido");
    }
    if(palo != BUFON && palo != MAGO && (valor < 1 || valor > 13)) {
      throw new IllegalArgumentException("Valor de carta no válido");
    }
    this.palo = palo;
    this.valor = valor;
  }

  /**
   * Getter para obtener el valor del palo de una carta.
   * @return palo es el valor del palo.
   */
  public int getPalo() {
    return this.palo;
  }

  /**
   * Getter para obtener el valor numérico de una carta.
   * @return valor es el valor de la carta.
   */
  public int getValor() {
    return this.valor;
  }

  /**
   * Método que regresa el valor del palo como string.
   * @return palo en string.
   */
  public String getPaloString() {
    switch (palo) {
      case AZUL:
        return "Azul";
      case VERDE:
        return "Verde";
      case AMARILLO:
        return "Amarillo";
      case ROJO:
        return "Rojo";
      case BUFON:
        return "Bufón";
      default:
        return "Mago";
    }
  }

  /**
   * Método que regresa el valor de la carta como string.
   * @return valor en string.
   */
  public String getValorString() {
    if (palo == BUFON && palo == MAGO) {
      return "" + valor;
    } else {
      return String.valueOf(valor);
    }
  }

  /**
   * Método que regresa una representación en
   * cadena de una carta.
   * @return carta como cadena.
   */
  public String toString() {
    if(palo == BUFON) {
      return "Bufón";
    } else if(palo == MAGO) {
      return "Mago";
    } else {
      return getValorString() + " " + getPaloString();
    }
  }
}
