package edd.src.Estructuras;

import java.util.Iterator;
import java.util.NoSuchElementException;
// iterador
//next

/*
 * Author: Eduardo Alfonso Reyes López
 */

public class Lista<T> implements Collection<T> {

    // Clase Nodo
    private class Nodo {
        public T elemento;
        public Nodo anterior;
        public Nodo siguiente;

        public Nodo(T elemento){
            this.elemento = elemento;
        }
    }

    // Iterador
    private class Iterador implements IteradorLista<T> {
        public Nodo anterior;
        public Nodo siguiente;

        public Iterador(){
            siguiente = cabeza;
        }

        @Override public boolean hasNext(){
            return siguiente != null;
        }

        @Override public T next(){
            if(!hasNext())
                throw new NoSuchElementException();
            T regresar = siguiente.elemento;

            this.anterior = this.siguiente ;
            this.siguiente=siguiente.siguiente;
            return regresar;

        }

        @Override
        public boolean hasPrevious() {
            return anterior != null;
        }

        @Override
        public T previous() {
            if (!hasPrevious())
                throw new NoSuchElementException();
            T regresar = anterior.elemento;

            this.siguiente = this.anterior;
            this.anterior = anterior.anterior;
            return regresar;

        }

        @Override
        public void start(){
            this.anterior = null;
            this.siguiente = cabeza;
        }

        @Override
        public void end() {
            this.anterior = ultimo;
            this.siguiente = null;
        }

    }

    private Nodo cabeza;
    private Nodo ultimo;
    private int longi;

    /**
     * Agrega un elemento a la lista.
     *
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    @Override
    public void add(T elemento){
        if(elemento == null){
            throw new IllegalArgumentException("El elemento es null");
        }
        agregaFinal(elemento);
    }


    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("El elemento es null");
        }
        Nodo nuevo = new Nodo(elemento);
        if (cabeza == null) {
            this.cabeza = this.ultimo = nuevo;
        } else {
            this.cabeza.anterior = nuevo;
            nuevo.siguiente = this.cabeza;
            this.cabeza = nuevo;
        }
        longi++;
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("El elemento es null");
        }
        Nodo nuevo = new Nodo(elemento);
        if(cabeza == null){
            this.cabeza = this.ultimo = nuevo;
        }
        else{
            this.ultimo.siguiente = nuevo;
            nuevo.anterior = this.ultimo;
            this.ultimo = nuevo;
        }
        longi++;
    }

    /**
     * Busca el elemento ingresado y devuelve
     * el nodo que lo contiene.
     *
     * @param elemento a buscar.
     * @return El nodo que lo contiene.
     */
    private Nodo buscaElemento(T elemento){
        Nodo n = cabeza;
        while(n !=null){
            if (elemento.equals(n.elemento)) {
                return n;
            }
            n=n.siguiente;
        }
        return null;
    }

    /**
     * Elimina un elemento de la lista.
     *
     * @param elemento el elemento a eliminar.
     */
    public boolean delete(T elemento){
        if(elemento == null)
            return false;
        Nodo n = buscaElemento(elemento);
        if(n==null){
            return false;
        }
        if(longi == 1){
            empty();
            return true;
        }
        if (n == cabeza) {
            cabeza = cabeza.siguiente;
            cabeza.anterior = null;
            longi --;
            return true;
        }
        if (n == ultimo) {
            ultimo = ultimo.anterior;
            ultimo.siguiente = null;
            longi --;
            return true;
        }
        n.siguiente.anterior = n.anterior;
        n.anterior.siguiente = n.siguiente;
        longi --;
        return true;
    }

    /**
     * Regresa un elemento de la lista. (Ultimo)
     * y lo elimina.
     *
     * @return El elemento a sacar.
     */
    public T pop(){
        if(isEmpty()) return null;
        T valor = ultimo.elemento;
        if(longi == 1) {
          cabeza = null;
          ultimo = null;
          longi--;
        }
        else {
          ultimo = ultimo.anterior;
          ultimo.siguiente = null;
          longi --;
        }
        return valor;
    }

    /**
     * Regresa el número de elementos en la lista.
     *
     * @return el número de elementos en la lista.
     */
    public int size(){
        return longi;
    }

    /**
     * Nos dice si un elemento está contenido en la lista.
     *
     * @param elemento el elemento que queremos verificar si está contenido en
     *                 la lista.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public boolean contains(T elemento){
        if(buscaElemento(elemento) == null){
            return false;
        }
        return true;
    }

    /**
     * Vacía la lista.
     *
     */
    public void empty(){
        cabeza = ultimo = null;
        longi = 0;
    }

    /**
     * Nos dice si la lista es vacía.
     *
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    public boolean isEmpty(){
        return longi == 0;
    }

    /**
     * Regresa una copia de la lista.
     *
     * @return una copia de la lista.
     */
    public Lista<T> clone() {
        Lista<T> nueva = new Lista<T>();
        Nodo nodo = cabeza;
        while (nodo != null) {
            nueva.add(nodo.elemento);
            nodo = nodo.siguiente;
        }
        return nueva;
    }

    /**
     * Nos dice si la coleccion es igual a otra coleccion recibida.
     *
     * @param coleccion la coleccion con el que hay que comparar.
     * @return <tt>true</tt> si la coleccion es igual a la coleccion recibido
     *         <tt>false</tt> en otro caso.
     */
    public boolean equals(Collection<T> coleccion){
        // lo vemos en clase
        if(coleccion instanceof Lista) {
            return true;
        }
        return false;
    }

    /**
     * Regresa un elemento de la lista. (Primero)
     * y lo elimina.
     *
     * @return El elemento a sacar.
     */
    public T popInicio(){
        if(cabeza == null) throw new IllegalArgumentException("La lista está vacía.");
        T valor = cabeza.elemento;
        cabeza = cabeza.siguiente;
        if(cabeza == null) {
          longi--;
          return valor;
        }
        else {
          cabeza.anterior = null;
          longi--;
          return valor;
        }
    }

    /**
     * Método que invierte el orden de la lista .
     *
     * Tiempo: O(n). Cuenta con un único ciclo while
     * que recorre todo la lista de n elementos.
     * Lo demás es constante.
     *
     * Espacio: O(1). Aunque se crean nodos, éstos son
     * temporales, ya que no se agregan a la lista final
     * ni se crea otra lista que ocupe espacio en memoria.
     *
     */
    public void reverse() {
        if(isEmpty()) return;
        Nodo nodo = cabeza;
        Nodo cabezaTemp = cabeza;
        while(nodo != null) {
          Nodo aux = nodo.anterior;
          nodo.anterior = nodo.siguiente;
          nodo.siguiente = aux;
          cabezaTemp = nodo;
          nodo = nodo.anterior;
        }
        ultimo = cabeza;
        cabeza = cabezaTemp;
    }

    /**
     * Regresa una representación en cadena de la coleccion.
     *
     * @return una representación en cadena de la coleccion.
     * a -> b -> c -> d
     */
    public String toString() {
        if (isEmpty()) return "";
        String lista = "";
        Nodo n = cabeza;
        while(n.siguiente != null) {
          lista += n.elemento.toString() + " -> ";
          n = n.siguiente;
        }
        lista += n.elemento.toString();
        return lista;
    }

    /**
     * Regresa la cabeza de una lista.
     *
     * @return el nodo cabeza.
     */
    private Nodo getCabeza() {
      return this.cabeza;
    }

    /**
     * Junta dos listas siempre y cuando sean del mismo tipo.
     * 
     * @param lista a anexar.
     */
    public void append(Lista<T> lista) {
      if(isEmpty()) cabeza = lista.getCabeza();
      else {
        if(lista instanceof Lista) {
            ultimo.siguiente = lista.getCabeza();
          }
      }
    }

    /**
     * Regresa un entero con la posicion del elemento.
     * Sólo nos importara la primera aparición del elemento
     * Empieza a contar desde 0.
     *
     * @param elemento elemento del cual queremos conocer la posición.
     * @return entero con la posicion del elemento.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public int indexOf(T elemento) {
        if(!contains(elemento)) throw new NoSuchElementException("La lista no contiene al elemento.");
        int count = 0;
        Nodo n = cabeza;
        while(n !=null){
            if (elemento.equals(n.elemento)) {
              return count;
            }
            count++;
            n = n.siguiente;
        }
        return 0;
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor que cero, el elemento se agrega al inicio de la
     * lista. Si el índice es mayor o igual que el número de elementos en la
     * lista, el elemento se agrega al fina de la misma. En otro caso, después
     * de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     *
     * @param i        el índice donde insertar el elemento. Si es menor que 0 el
     *                 elemento se agrega al inicio, y si es mayor o igual que el
     *                 número
     *                 de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    public void insert(int i, T elemento) {
        if(i < 0 || isEmpty()) {
          agregaInicio(elemento);
        } else if(i >= longi) {
          agregaFinal(elemento);
        } else {
          int count = 1;
          Nodo n = cabeza;
          while(count < i) {
            count++;
            n = n.siguiente;
          }
          Nodo nuevo = new Nodo(elemento);
          n.siguiente.anterior = nuevo;
          nuevo.siguiente = n.siguiente;
          nuevo.anterior = n;
          n.siguiente = nuevo;
          longi++;
        }
        return ;
    }

    /**
    * Regresa el elemento en el índice ingresado.
    *
    * @param index el índice del nodo de la lista con el elemento de interés.
    * @return elemento del índice indicado.
    * @throws IllegalArgumentException si index <= 0 v index > longitud de la lista.
    */
    public T getElemento(int index) {
        if (index <= 0 || index > longi) throw new IllegalArgumentException("Índice no válido.");
        Nodo n = cabeza;
        int i = 1;
        while(n != null){
          if(i == index) {
            return n.elemento;
          }
          i++;
          n = n.siguiente;
        }
        return null;
    }

    /**
    * Regresa y saca el elemento en el índice ingresado.
    *
    * @param index el índice del nodo de la lista con el elemento de interés.
    * @return elemento del índice indicado.
    * @throws IllegalArgumentException si index <= 0 v index > longitud de la lista.
    */
    public T getOutElemento(int index) {
        if (index <= 0 || index > longi) throw new IllegalArgumentException("Índice no válido.");
        if (index == 1) return popInicio();
        if (index == longi) return pop();
        Nodo n = cabeza;
        int i = 1;
        while(n != null){
          if(i == index) {
            T aux = n.elemento;
            n.siguiente.anterior = n.anterior;
            n.anterior.siguiente = n.siguiente;
            longi--;
            return aux;
          }
          i++;
          n = n.siguiente;
        }
        return null;
    }


    /**
     * Dados 2 ejemplares de nuestra clase Lista A y B, la función los une de manera
     * alternada, es decir, a un elemento de A le seguirá un elemento de la lista B y viceversa.
     * Este método cambia la lista original.
     *
     * Tiempo: O(n+m). Con n el tamaño de la lista que llama al método.
     * Es de ese orden porque cuenta con un ciclo while que itera
     * hasta llegar a la longitud de la lista, por lo que su
     * complejidad es en realidad O(n).
     *
     * Espacio O(1). Es constante porque no se crean nodos
     * ni listas nuevas que se quedan en memoria. Sólo hay nodos
     * auxiliares para iterar.
     *
     * @param lista es la lista con la cual se va a intercalar la lista objetivo.
     */
    public void mezclaAlternada(Lista<T> lista) {
      Nodo nodo = cabeza;
      Nodo nuevo;
      int count = 1;
      while(count < longi) {
        if(!lista.isEmpty()){
          nuevo = lista.getCabeza();
          lista.popInicio();
          nuevo.anterior = nodo;
          nuevo.siguiente = nodo.siguiente;
          nodo.siguiente.anterior = nuevo;
          nodo.siguiente = nuevo;
          nodo = nuevo.siguiente;
          count++;
        }
        else break;
      }

      //Otro método de mezclaAlternada que ocupa espacio O(n+m).
      /* int count = 1;
     int index = 1;
     while(count <= lista.size()) {
       T elem = lista.getElemento(count);
       insert(index, elem);
       count++;
       index += 2;
     } */

    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }
}
