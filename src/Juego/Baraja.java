package edd.src.Juego;

import edd.src.Estructuras.*;

public class Baraja {
  private Lista<Carta> baraja = new Lista<Carta>();
  private int cartasUsadas;

  /**
   * Constructor de baraja.
   */
  public Baraja() {
    for(int palo = 0; palo <= 3; palo++) {
      for(int valor = 1; valor <= 13; valor++) {
        baraja.add(new Carta(valor, palo));
      }
    }
    for(int valor = 1; valor <= 4; valor++) {
      baraja.add(new Carta(valor, Carta.BUFON));
      baraja.add(new Carta(valor, Carta.MAGO));
    }
    cartasUsadas = 0;
  }

  /**
   * Método que mezcla aleatoriamente las cartas de la baraja.
   */
  public void mezclar() {
    for(int i = baraja.size(); i > 0; i--) {
      int azar = (int) (Math.random() * i + 1);
      if(azar == i) {
        if(azar + 1 <= baraja.size()) azar++;
        else if(azar - 1 > 0) azar--;
      }
      Carta aux = baraja.getElemento(i);
      baraja.insert(i, baraja.getOutElemento(azar));
      baraja.insert(azar, aux);
      baraja.getOutElemento(i);
      cartasUsadas = 0;
    }
  }

  /**
   * Método que saca una carta de la baraja (lista).
   * @return carta es la última carta de la baraja.
   */
  public Carta darCarta() {
    if(baraja.isEmpty()) throw new IllegalArgumentException("No hay más cartas en la baraja");
    return baraja.pop();
  }

  /**
   * Método que regresa una carta a la baraja,
   * añadiéndola a la lista.
   * @param carta es la carta a meter.
   */
  public void meterCarta(Carta carta) {
    baraja.add(carta);
  }

  /**
   * Verifica si la baraja está vacía.
   */
  public boolean isEmpty() {
    return baraja.isEmpty();
  }

  /**
   * Método que regresa la lista de la baraja.
   * @return lista contenida en la baraja.
   */
  public Lista<Carta> getLista() {
    return baraja;
  }
}
