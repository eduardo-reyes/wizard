package edd.src.Juego;

import edd.src.Estructuras.*;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Arrays;
import java.io.*;

public class Wizard {

  private static final String ANSI_RESET = "\u001B[0m";
  private static final String ANSI_BLACK = "\u001B[30m";
  private static final String ANSI_RED = "\u001B[31m";
  private static final String ANSI_GREEN = "\u001B[32m";
  private static final String ANSI_YELLOW = "\u001B[33m";
  private static final String ANSI_BLUE = "\u001B[34m";
  private static final String ANSI_PURPLE = "\u001B[35m";
  private static final String ANSI_CYAN = "\u001B[36m";
  private static final String ANSI_WHITE = "\u001B[37m";
  private static Baraja baraja = new Baraja();
  private static int numJugadores;
  private static String[] jugadores;
  private static int[] puntuacionTotal;
  private static Lista<Carta> cartasJ1 = new Lista<Carta>();
  private static Lista<Carta> cartasJ2 = new Lista<Carta>();
  private static Lista<Carta> cartasJ3 = new Lista<Carta>();
  private static Lista<Carta> cartasJ4 = new Lista<Carta>();
  private static Lista<Carta> cartasJ5 = new Lista<Carta>();
  private static Lista<Carta> cartasJ6 = new Lista<Carta>();
  private static String path = "src/Juego/Historial";

    /**
     * Método que lee el archivo que se encuentra en el path.
     * @return file La cadena de texto que contiene
     * lo que tenía el archivo del path.
     */
  private static String readFile() {
  	String file = "";
  	try {
  	    BufferedReader in = new BufferedReader(new FileReader(path));
  	    String line;
  	    while((line = in.readLine()) != null) {
  		      file = file + line + "\n";
  	    }
  	    in.close();
  	} catch (FileNotFoundException e) {
  	    System.err.println("No se encontró el archivo en el path dado.");
  	} catch (IOException ioe) {
  	    System.err.println("Error al leer el contenido del path.");
  	}
    return file;
  }

    /**
     * Método que sobreescribe la cadena de texto ingresada en el archivo del path
     * @param text El texto a escribir en el archivo del path.
     */
  private static void writeFile(String text) {
  	String nombreArchivo = path;
  	try(PrintStream fout = new PrintStream(nombreArchivo)) {
  	    fout.println(text);
  	} catch(FileNotFoundException fnfe) {
  	    System.err.println("No se encontró el archivo " + nombreArchivo + " y no pudo ser creado.");
  	} catch(SecurityException se) {
  	    System.err.println("No se tiene permiso de escribir en el archivo.");
  	}
  }

    /**
     * Método que escribe la cadena de texto ingresada en el archivo del path.
     * @param text El texto a escribir en el archivo del path.
     */
  private static void appendFile(String text) {
  	String nombreArchivo = path;
    try {
      BufferedWriter out = new BufferedWriter(new FileWriter(nombreArchivo, true));
           out.write(text);
           out.newLine();
           out.close();
       }
       catch (IOException e) {
           System.out.println("Ocurrió una excpeción." + e);
       }
  }

  /**
   * Método que imprime en consola la mano de cartas del jugador.
   * @param cartas la lista con las cartas del jugador.
   */
  private static void printMano(Lista<Carta> cartas) {
    String mano = "";
    for(int i = 1; i <= cartas.size(); i++) {
      Carta carta = cartas.getElemento(i);
      int palo = carta.getPalo();
      switch (palo) {
        case 0:
          System.out.print(" [" + ANSI_BLUE + carta.getValorString() + ANSI_RESET + "] ");
          break;
        case 1:
          System.out.print(" [" + ANSI_GREEN + carta.getValorString() + ANSI_RESET + "] ");
          break;
        case 2:
          System.out.print(" [" + ANSI_YELLOW + carta.getValorString() + ANSI_RESET + "] ");
          break;
        case 3:
          System.out.print(" [" + ANSI_RED + carta.getValorString() + ANSI_RESET + "] ");
          break;
        case 4:
          System.out.print(" [Bufón " + carta.getValorString() + "] ");
          break;
        default:
          System.out.print(" [Mago " + carta.getValorString() + "] ");
        }
    }
    System.out.println("");
  }

  /**
   * Busca cartas que pertenecen al palo líder o que son válidas
   * para tirar (magos y bufones).
   * @param mano es la lista donde hay que buscar.
   * @param paloLider es el número del palo líder.
   */
  private static Lista<Carta> buscaLideres(Lista<Carta> mano, int paloLider) {
    Lista<Carta> cartasLider = new Lista<Carta>();
    if(paloLider < 0 || paloLider > 3) {
      cartasLider = mano.clone();
    } else {
      for(int i = 1; i <= mano.size(); i++) {
        int palo = mano.getElemento(i).getPalo();
        if(palo == paloLider || palo == 4 || palo == 5) {
          cartasLider.add(mano.getElemento(i));
        }
      }
    }
    if(cartasLider.isEmpty()) cartasLider = mano.clone();
    return cartasLider;
  }

  /**
   * Busca el máximo en un arreglo de enteros.
   * @param a es el arreglo de enteros donde hay que buscar.
   */
  private static int maxArray(int[] a) {
    int max = a[0];
    for(int i = 1; i < a.length; i++) {
      if (a[i] > max) max = a[i];
    }
    return max;
  }

  /**
   * Método que imprime en consola el palo de triunfo de la ronda.
   * @param paloTriunfo es el valor entero del palo de triunfo.
   */
  private static void imprimeTriunfo(int paloTriunfo) {
      System.out.println("");
      System.out.println("El palo de triunfo es:" );
      switch(paloTriunfo) {
        case 0:
          System.out.print(" [" + ANSI_BLUE + "Azul" + ANSI_RESET + "] ");
          break;
        case 1:
          System.out.print(" [" + ANSI_GREEN + "Verde" + ANSI_RESET + "] ");
          break;
        case 2:
          System.out.print(" [" + ANSI_YELLOW + "Amarillo" + ANSI_RESET + "] ");
          break;
        case 3:
          System.out.print(" [" + ANSI_RED + "Rojo" + ANSI_RESET + "] ");
          break;
        default:
          System.out.print("No hay palo de triunfo.");
        }
      }

      /**
       * Método que imprime en consola el palo lider.
       * @param paloLider es el valor entero del palo líder.
       */
      private static void imprimeLider(int paloLider) {
          System.out.println("");
          System.out.println("El palo líder es:" );
          switch(paloLider) {
            case 0:
              System.out.print(" [" + ANSI_BLUE + "Azul" + ANSI_RESET + "] ");
              break;
            case 1:
              System.out.print(" [" + ANSI_GREEN + "Verde" + ANSI_RESET + "] ");
              break;
            case 2:
              System.out.print(" [" + ANSI_YELLOW + "Amarillo" + ANSI_RESET + "] ");
              break;
            case 3:
              System.out.print(" [" + ANSI_RED + "Rojo" + ANSI_RESET + "] ");
              break;
            default:
              System.out.print("No hay palo líder.");
            }
          }

  /**
   * Método que implementa toda la lógica para jugar cada ronda.
   * @param ronda es la ronda en la que va el juego.
   * @param barajador es a quien le toca ser el barajador.
   */
  private static void jugarRonda(int ronda, int barajador) {
    int jugadorActual = 0;
    int paloTriunfo = -1;
    int[] apuesta = new int[numJugadores];
    int[] trucosGanados = new int[numJugadores];
    int[] puntuacion = new int[numJugadores];
    Scanner sc = new Scanner(System.in);

    System.out.println("");
    System.out.println("########## Ronda " + String.valueOf(ronda) + " ########## \n");
    appendFile("\n");
    appendFile("########## Ronda " + String.valueOf(ronda) + " ########## \n");
    //Mezclar la baraja.
    System.out.println("El " + jugadores[barajador] + " barajeó el mazó.");
    baraja.mezclar();
    // Repartir cartas entre los jugadores.
    for(int i = 1; i <= ronda; i++) {
      cartasJ1.add(baraja.darCarta());
      cartasJ2.add(baraja.darCarta());
      cartasJ3.add(baraja.darCarta());
      if(numJugadores >= 4) cartasJ4.add(baraja.darCarta());
      if(numJugadores >= 5) cartasJ5.add(baraja.darCarta());
      if(numJugadores >= 6) cartasJ6.add(baraja.darCarta());
    }
    // Tomar una carta adicional del palo para saber cual es el palo de triunfo.
    if(!baraja.isEmpty()) {
      Carta cartaTriunfo = baraja.darCarta();
      int triunfo = cartaTriunfo.getPalo();
      System.out.println("");
      System.out.println("Carta adicional para decidir el palo de triunfo: " + cartaTriunfo.toString());
      appendFile("Carta adicional para decidir el palo de triunfo: " + cartaTriunfo.toString());
      baraja.meterCarta(cartaTriunfo);
      if(triunfo == 5) {
        System.out.println("El barajeador (" + jugadores[barajador] + ") elige el palo de triunfo.");
        boolean condicion = true;
        while (condicion) {
          try {
            System.out.println("Elige un palo del trinfo ingresando el número de las siguientes opciones:");
            System.out.println("0-Azul 1-Verde 2-Amarillo 3-Rojo");
            paloTriunfo = sc.nextInt();
            if(paloTriunfo < 0 || paloTriunfo > 3) {
              throw new IllegalArgumentException("Número de palo no válido.");
            }
            condicion = false;
          } catch (InputMismatchException ime) {
              paloTriunfo = 0;
              sc = new Scanner(System.in);
      				continue;
      		} catch (IllegalArgumentException iae) {
              paloTriunfo = 0;
              sc = new Scanner(System.in);
              continue;
          }
        }
      } else if(triunfo == 4) {
        System.out.println("Salió bufón y no hay palo de triunfo.");
        appendFile("Salió bufón y no hay palo de triunfo.");
        paloTriunfo = -1;
      } else {
        paloTriunfo = triunfo;
        System.out.print("Palo de Triunfo:");
        switch (paloTriunfo) {
          case 0:
            System.out.print(" [" + ANSI_BLUE + "Azul" + ANSI_RESET + "] ");
            appendFile("Palo de Triunfo:" + " [" + ANSI_BLUE + "Azul" + ANSI_RESET + "] ");
            break;
          case 1:
            System.out.print(" [" + ANSI_GREEN + "Verde" + ANSI_RESET + "] ");
            appendFile("Palo de Triunfo:" + " [" + ANSI_GREEN + "Verde" + ANSI_RESET + "] ");
            break;
          case 2:
            System.out.print(" [" + ANSI_YELLOW + "Amarillo" + ANSI_RESET + "] ");
            appendFile("Palo de Triunfo:" + " [" + ANSI_YELLOW + "Amarillo" + ANSI_RESET + "] ");
            break;
          default:
            System.out.print(" [" + ANSI_RED + "Rojo" + ANSI_RESET + "] ");
            appendFile("Palo de Triunfo:" + " [" + ANSI_RED + "Rojo" + ANSI_RESET + "] ");
          }
          System.out.println("\n");
      }
    } else {
      paloTriunfo = -1;
      System.out.println("No hay palo de triunfo");
      appendFile("No hay palo de triunfo");
    }
    imprimeTriunfo(paloTriunfo);
    System.out.println("");
    // En este ciclo cada jugador hace una apuesta entre 0 y #ronda.
    for(int i = 0; i < numJugadores; i++) {
      System.out.println("==============================");
      imprimeTriunfo(paloTriunfo);
      System.out.println("");
      System.out.println(jugadores[i] + " estas son tus cartas:");
      switch (i) {
        case 0:
          printMano(cartasJ1);
          break;
        case 1:
          printMano(cartasJ2);
          break;
        case 2:
          printMano(cartasJ3);
          break;
        case 3:
          printMano(cartasJ4);
          break;
        case 4:
          printMano(cartasJ5);
          break;
        default:
          printMano(cartasJ6);
      }
      boolean condicion = true;
      while (condicion) {
        try {
          System.out.println(jugadores[i] + " haz una apuesta entre 0 y " + String.valueOf(ronda));
          apuesta[i] = sc.nextInt();
          if(apuesta[i] < 0 || apuesta[i] > ronda) {
            throw new IllegalArgumentException("Apuesta no válida.");
          }
          condicion = false;
        } catch (InputMismatchException ime) {
            apuesta[i] = 0;
            sc = new Scanner(System.in);
            continue;
        } catch (IllegalArgumentException iae) {
            apuesta[i] = 0;
            sc = new Scanner(System.in);
            continue;
        }
      }
      appendFile(jugadores[i] + " apostó " + apuesta[i]);
    }
    // Ciclo que simula los n trucos de la ronda.
    int ganador = 0;
    if(barajador < numJugadores - 1) ganador = barajador + 1; // Empieza quien está a la izquierda de quien barajeó.
    else ganador = 0;
    for(int i = ronda; i >= 1; i--) {
      jugadorActual = ganador;
      int paloLider = -1;
      int longMano = i;
      int numBufones = 0;
      int primerJugador = jugadorActual;
      Lista<Carta> cartasJugadas = new Lista<Carta>();
      System.out.println("");
      System.out.println("Primer jugador: Jugador " + String.valueOf(primerJugador + 1));
      appendFile("Truco " + String.valueOf(ronda - i + 1));
      // Por cada truco debe pasar una vez cada jugador.
      for(int j = 1; j <= numJugadores; j++) {
        Lista<Carta> cartasLider = new Lista<Carta>();
        Lista<Carta> manoActual = new Lista<Carta>();
        System.out.println("");
        System.out.println("==============================");
        System.out.println("Turno del " + jugadores[jugadorActual]);
        imprimeTriunfo(paloTriunfo);
        imprimeLider(paloLider);
        System.out.println("");
        System.out.println(jugadores[jugadorActual] + " tus cartas son:");
        switch (jugadorActual) {
          case 0:
            printMano(cartasJ1);
            cartasLider = buscaLideres(cartasJ1, paloLider);
            manoActual = cartasJ1.clone();
            break;
          case 1:
            printMano(cartasJ2);
            cartasLider = buscaLideres(cartasJ2, paloLider);
            manoActual = cartasJ2.clone();
            break;
          case 2:
            printMano(cartasJ3);
            cartasLider = buscaLideres(cartasJ3, paloLider);
            manoActual = cartasJ3.clone();
            break;
          case 3:
            printMano(cartasJ4);
            cartasLider = buscaLideres(cartasJ4, paloLider);
            manoActual = cartasJ4.clone();
            break;
          case 4:
            printMano(cartasJ5);
            cartasLider = buscaLideres(cartasJ5, paloLider);
            manoActual = cartasJ5.clone();
            break;
          default:
            printMano(cartasJ6);
            cartasLider = buscaLideres(cartasJ6, paloLider);
            manoActual = cartasJ6.clone();
        }
        //System.out.println("Mano Actual: " + manoActual.toString());
        //System.out.println("Cartas válidas: " + cartasLider.toString());
        int eleccion = 0;
        boolean condicion = true;
        while (condicion) {
          try {
            System.out.println("Elije una carta por su lugar desde el índice 1 al " + String.valueOf(i));
             eleccion = sc.nextInt();
            if(eleccion < 1 || eleccion > i) {
              System.out.println("índice no válido.");
              throw new IllegalArgumentException("Índice no válido.");
            }
            if(cartasLider.contains(manoActual.getElemento(eleccion))) {
              System.out.println(jugadores[jugadorActual] + " eligió " + manoActual.getElemento(eleccion));
              if((manoActual.getElemento(eleccion).getPalo()) == 4) numBufones++;
              condicion = false;
            }
          } catch (InputMismatchException ime) {
              eleccion = 0;
              sc = new Scanner(System.in);
              continue;
          } catch (IllegalArgumentException iae) {
              eleccion = 0;
              sc = new Scanner(System.in);
              continue;
          }
        }
        // Saca la carta y la pone en el monte de cartas jugadas.
        switch (jugadorActual) {
          case 0:
            cartasJugadas.add(cartasJ1.getOutElemento(eleccion));
            break;
          case 1:
            cartasJugadas.add(cartasJ2.getOutElemento(eleccion));
            break;
          case 2:
            cartasJugadas.add(cartasJ3.getOutElemento(eleccion));
            break;
          case 3:
            cartasJugadas.add(cartasJ4.getOutElemento(eleccion));
            break;
          case 4:
            cartasJugadas.add(cartasJ5.getOutElemento(eleccion));
            break;
          default:
            cartasJugadas.add(cartasJ6.getOutElemento(eleccion));
        }
        //System.out.println("Cartas jugadas: " + cartasJugadas.toString());
        if(paloLider == -1) {
          if(cartasJugadas.getElemento(j).getPalo() < 4) paloLider = cartasJugadas.getElemento(j).getPalo();
        }
        appendFile(jugadores[jugadorActual] + " jugó " + cartasJugadas.getElemento(j).toString());
        if(jugadorActual < (numJugadores - 1)) jugadorActual++;
        else jugadorActual = 0;
      }
      // Si todos tiraron bufones, gana el primero que tiró un bufón.
      System.out.println("");
      System.out.println("==============================");
      System.out.println("Resultado del truco.");
      appendFile("Resultado del truco.");
      if(numBufones == cartasJugadas.size()) {
        ganador = primerJugador;
        trucosGanados[ganador] += 1;
        System.out.println("Ganó el jugador " + String.valueOf(ganador + 1) + "al poner el 1er bufón.");
        appendFile("Ganó el jugador " + String.valueOf(ganador + 1) + "al poner el 1er bufón.");
      } else { // De lo contrario buscamos las magos y demás opciones para ganar.
        int revision = 0;
        int triunfos = 0;
        int lideres = 0;
        boolean hayGanador = false;
        int[] cartasTriunfo = new int[numJugadores];
        int[] cartasLider = new int[numJugadores];

        while(revision < cartasJugadas.size()) {
          //System.out.println("Revisión no: " + String.valueOf(revision));
          int palo = cartasJugadas.getElemento(revision + 1).getPalo();
          //System.out.println("Palo: " + String.valueOf(palo) + " de la carta " + cartasJugadas.getElemento(revision + 1).toString());
          if(palo == 5) {
            hayGanador = true;
            if((primerJugador + revision) > (numJugadores - 1)) {
              ganador = revision - (numJugadores - primerJugador);
            } else {
              ganador = primerJugador + revision;
            }
            trucosGanados[ganador] += 1;
            System.out.println("Ganó con un mago el jugador " + String.valueOf(ganador + 1));
            appendFile("Ganó con un mago el jugador " + String.valueOf(ganador + 1));
            //System.out.println("Arreglo victorias con mago " + Arrays.toString(trucosGanados));
            break;
          } else if(palo == paloTriunfo) {
              triunfos++;
              if((primerJugador + revision) > (numJugadores - 1)) {
                int lugar = revision - numJugadores + primerJugador;
                cartasTriunfo[lugar] += cartasJugadas.getElemento(revision + 1).getValor();
              } else {
                int lugar = primerJugador + revision;
                cartasTriunfo[lugar] += cartasJugadas.getElemento(revision + 1).getValor();
              }
              //cartasTriunfo[revision] += cartasJugadas.getElemento(revision + 1).getValor();
          } else if(palo == paloLider) {
              lideres++;
              if((primerJugador + revision) > (numJugadores - 1)) {
                int lugar = revision - numJugadores + primerJugador;
                cartasLider[lugar] += cartasJugadas.getElemento(revision + 1).getValor();
              } else {
                int lugar = primerJugador + revision;
                cartasLider[lugar] += cartasJugadas.getElemento(revision + 1).getValor();
              }
              //cartasLider[revision] += cartasJugadas.getElemento(revision + 1).getValor();
          }
          revision++;
        }
        //System.out.println("Arreglo triunfo " +  Arrays.toString(cartasTriunfo));
        //System.out.println("Arreglo lider " +  Arrays.toString(cartasLider));
        if(!hayGanador) {
          if(triunfos > 0) { // Gana el que jugó la carta más alta del palo de triunfo.
            System.out.println("Ganó una carta del palo de triunfo.");
            appendFile("Ganó una carta del palo de triunfo.");
            int max = maxArray(cartasTriunfo);
            for(int k = 0; k < cartasTriunfo.length; k++) {
              if(cartasTriunfo[k] == max) {
                ganador = k;
                trucosGanados[ganador] += 1;
                break;
              }
            }
          } else if(lideres > 0) { // Gana el que jugó la carta más alta del palo líder.
            System.out.println("Ganó una carta del palo líder.");
            appendFile("Ganó una carta del palo líder.");
            int max = maxArray(cartasLider);
            for(int k = 0; k < cartasLider.length; k++) {
              if(cartasLider[k] == max) {
                ganador = k;
                trucosGanados[ganador] += 1;
                break;
              }
            }
          }
          System.out.println("Ganó el jugador " + String.valueOf(ganador + 1));
          appendFile("Ganó el jugador " + String.valueOf(ganador + 1));
        }
      } // Aquí terminamos de ver quien ganó en el else de los bufones.
      // Regresamos las cartas tomadas a la baraja.
      while(!cartasJugadas.isEmpty()) {
        baraja.meterCarta(cartasJugadas.pop());
      }
      //System.out.println("Longitud de la baraja final: " + String.valueOf(baraja.getLista().size()));
    }
    //System.out.println("Arreglo victorias " + Arrays.toString(trucosGanados));
    // Mostramos los resultados de la ronda.
    for(int i = 0; i < numJugadores; i++) {
      if(apuesta[i] == trucosGanados[i]) {
        puntuacion[i] = 20 + (10 * trucosGanados[i]);
      } else {
        puntuacion[i] = -10 * (Math.abs(apuesta[i] - trucosGanados[i]));
      }
      puntuacionTotal[i] += puntuacion[i];
      System.out.println("");
      System.out.println(jugadores[i]);
      System.out.println("Apuesta: " + String.valueOf(apuesta[i]) + ". " + "Victorias: " + trucosGanados[i]);
      System.out.println("Puntuación de la ronda: " + puntuacion[i]);
      System.out.println("Puntuación total: " + puntuacionTotal[i]);
      appendFile(jugadores[i]);
      appendFile("Apuesta: " + String.valueOf(apuesta[i]) + ". " + "Victorias: " + trucosGanados[i]);
      appendFile("Puntuación de la ronda: " + puntuacion[i]);
      appendFile("Puntuación total: " + puntuacionTotal[i]);
    }
    System.out.println("##############################");
  }

  public static void main(String[] args) {
    int rondas = 0;
    int ronda = 1;
    int barajador = 0;
    boolean seguirJugando = true;
    Scanner sc = new Scanner(System.in);

    System.out.println("====== W I Z A R D ======");

    writeFile(""); // Borra el historial anterior si es que existe.

    // El usuario ingresa el número de jugadores de la partida.
    while(seguirJugando) {
      try {
        System.out.println("Ingrese el número de jugadores: (3 a 6 jugadores)");
        numJugadores = sc.nextInt();
        if(numJugadores >= 3 && numJugadores <= 6) {
          switch(numJugadores) {
            case 3:
              rondas = 20;
              jugadores = new String[]{"jugador1", "jugador2", "jugador3"};
              break;
            case 4:
              rondas = 15;
              jugadores = new String[]{"jugador1", "jugador2", "jugador3", "jugador4"};
              break;
            case 5:
              rondas = 12;
              jugadores = new String[]{"jugador1", "jugador2", "jugador3", "jugador4", "jugador5"};
              break;
            default:
              rondas = 10;
              jugadores = new String[]{"jugador1", "jugador2", "jugador3", "jugador4", "jugador5", "jugador6"};
          }
          seguirJugando = false;
        } else {
          System.out.println("Sólo se aceptan números entre el 3 y el 6.");
          continue;
        }
      } catch (InputMismatchException ime) {
          numJugadores = 0;
          sc = new Scanner(System.in);
  				continue;
  		}
    }

    puntuacionTotal = new int[numJugadores];

    seguirJugando = true;

    // El menú del juego.
    do {
      jugarRonda(ronda, barajador);
      if(barajador < numJugadores - 1) barajador++;
      else barajador = 0;
      ronda++;
      char opcionH;
      try {
        System.out.println("");
        System.out.println("¿Desea ver el historial? (y/n)");
        opcionH = sc.next().charAt(0);
      } catch (InputMismatchException ime) {
        System.out.println("Para elegir la opción escriba sólo una opción válida.");
        opcionH = 'n';
        sc = new Scanner(System.in);
        continue;
      }
      if(opcionH == 'y') System.out.println(readFile());
      int opcion;
      try {
        System.out.println("");
        System.out.println("¿Desea seguir jugando? (y/n)");
        opcion = sc.next().charAt(0);
      } catch (InputMismatchException ime) {
        System.out.println("Para elegir la opción escriba sólo una opción válida.");
        opcion = 'y';
        sc = new Scanner(System.in);
        continue;
      }
      if(opcion == 'n') seguirJugando = false;
    } while(seguirJugando && ronda <= rondas);

    System.out.println("");
    System.out.println("########## FIN DEL JUEGO ##########");
    int maxFinal = maxArray(puntuacionTotal);
    for(int i = 0; i < numJugadores; i++) {
      if(puntuacionTotal[i] == maxFinal) {
        System.out.println("El ganador es: " + jugadores[i]);
        appendFile("\n");
        appendFile("El ganador es: " + jugadores[i]);
      }
    }
  }
}
