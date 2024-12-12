package AreaAlumnos.RegistroDeNotas;

import java.util.ArrayList;

public class Arbol {

    private Nodo raiz;

    public Arbol() {

    }

    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo r) {
        this.raiz = r;
    }

    public void insertar(String dato) {
        Nodo nuevo = new Nodo(dato);
        if (this.raiz == null) {
            setRaiz(nuevo);
        } else {
            insertar(this.raiz, dato);
        }
    }

    private void insertar(Nodo padre, String dato) {
        Nodo nuev = new Nodo(dato);
        int numSpace = 0;
        String notaf = "";
        for (int i = 0; i < dato.length(); i++) {
            if (Character.isSpaceChar(dato.charAt(i))) {
                numSpace++;
            } else {
                if (numSpace == 4) {
                    notaf += dato.charAt(i);
                }
            }
        }
        double notaF = Double.parseDouble(notaf);
        if (notaF >= padre.getNotaF()) {
            if (padre.getDerecha() == null) {
                padre.setDerecha(new Nodo(dato));
            } else {
                this.insertar(padre.getDerecha(), dato);
            }
        } else {
            if (padre.getIzquierda() == null) {
                padre.setIzquierda(new Nodo(dato));
            } else {
                this.insertar(padre.getIzquierda(), dato);
            }
        }
    }

    public boolean vacio() {
        if (this.raiz == null) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList inorden() {
        list = this.inorden(this.raiz);
        return list;
    }

    ArrayList<Nodo> lista = new ArrayList<>(10);
    ArrayList<Nodo> list = new ArrayList<>(10);

    private ArrayList inorden(Nodo n) {
        if (n != null) {
            inorden(n.getIzquierda());
            lista.add(n);
            inorden(n.getDerecha());
        }
        return lista;
    }

    public void vaciar_lista() {
        lista.clear();
        list.clear();
    }

    public Nodo buscar(String cod) {
        return buscar(this.raiz, cod);
    }

    private Nodo buscar(Nodo n, String cod) {
        if (n == null) {
            return null;
        }
        String suma = "";
        for (int i = 0; i < cod.length(); i++) {
            if (i > 3) {
                suma += cod.charAt(i);
            }
        }
        int notas = Integer.parseInt(suma);

        int numcod = n.getNota1() + n.getNota2() + n.getNota3() + n.getNota4();

        if ((n.getCodigo()).equalsIgnoreCase(cod)) {
            return n;
        } else {
            if (notas < numcod) {
                return buscar(n.getIzquierda(), cod);
            } else {
                return buscar(n.getDerecha(), cod);
            }
        }
    }

    public void actualizar(String datito, String cod) {
        actualizar(this.raiz, datito, cod);
    }

    private void actualizar(Nodo n, String datito, String cod) {
        String suma = "";
        for (int i = 0; i < cod.length(); i++) {
            if (i > 3) {
                suma += cod.charAt(i);
            }
        }
        int notas = Integer.parseInt(suma);

        int numcod = n.getNota1() + n.getNota2() + n.getNota3() + n.getNota4();

        if ((n.getCodigo()).equalsIgnoreCase(cod)) {
            n.setDato(datito);
        } else {
            if (notas < numcod) {
                actualizar(n.getIzquierda(), datito, cod);
            } else {
                actualizar(n.getDerecha(), datito, cod);
            }
        }
    }
    
    // Método para eliminar un nodo de un árbol binario de búsqueda
    public void eliminar(String busqueda) {
        this.raiz = this.eliminar(this.raiz, busqueda);
    }

    // Método auxiliar recursivo que recibe el nodo raíz y el valor a eliminar
    private Nodo eliminar(Nodo nodo, String busqueda) {
        // Si el nodo es nulo, no se hace nada
        Nodo aux = new Nodo(busqueda);
        if (nodo == null) {
            return nodo;
        }
        // Si el valor a eliminar es mayor que el del nodo, se busca por la derecha
        if (aux.getNotaF() > nodo.getNotaF()) {
            nodo.setDerecha(this.eliminar(nodo.getDerecha(), busqueda));
        } // Si el valor a eliminar es menor que el del nodo, se busca por la izquierda
        else if (aux.getNotaF() < nodo.getNotaF()) {
            nodo.setIzquierda(this.eliminar(nodo.getIzquierda(), busqueda));
        } // Si el valor a eliminar es igual que el del nodo, se encontró el nodo a eliminar
        else {
            // Si el nodo no tiene hijos, se elimina
            if (nodo.getIzquierda() == null && nodo.getDerecha() == null) {
                nodo = null;
            } // Si el nodo tiene un hijo derecho, se reemplaza por él
            else if (nodo.getIzquierda() == null) {
                nodo = nodo.getDerecha();
            } // Si el nodo tiene un hijo izquierdo, se reemplaza por él
            else if (nodo.getDerecha() == null) {
                nodo = nodo.getIzquierda();
            } // Si el nodo tiene dos hijos, se busca el sucesor o el predecesor
            else {
                // Se puede usar el sucesor (el menor de los mayores)
                nodo.setDato(this.sucesor(nodo));
                // Se elimina el sucesor, que tendrá a lo sumo un hijo
                nodo.setDerecha(this.eliminar(nodo.getDerecha(), nodo.getDato()));
                // O se puede usar el predecesor (el mayor de los menores)
                // nodo.setDato(this.predecesor(nodo));
                // Se elimina el predecesor, que tendrá a lo sumo un hijo
                // nodo.setIzquierda(this.eliminar(nodo.getIzquierda(), nodo.getDato()));
            }
        }
        // Se retorna el nodo modificado
        return nodo;
    }

// Método para encontrar el sucesor de un nodo, que es el menor de los mayores
    private String sucesor(Nodo nodo) {
        // Se avanza por la derecha del nodo
        nodo = nodo.getDerecha();
        // Se busca el menor de ese subárbol, que será el más a la izquierda
        while (nodo.getIzquierda() != null) {
            nodo = nodo.getIzquierda();
        }
        // Se retorna el valor del sucesor
        return nodo.getDato();
    }

// Método para encontrar el predecesor de un nodo, que es el mayor de los menores
    private String predecesor(Nodo nodo) {
        // Se avanza por la izquierda del nodo
        nodo = nodo.getIzquierda();
        // Se busca el mayor de ese subárbol, que será el más a la derecha
        while (nodo.getDerecha() != null) {
            nodo = nodo.getDerecha();
        }
        // Se retorna el valor del predecesor
        return nodo.getDato();
    }
}
