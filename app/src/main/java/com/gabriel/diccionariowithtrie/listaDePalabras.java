package com.gabriel.diccionariowithtrie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
IMPLEMENTACION DEL TRIE QUE HICIMOS EN JAVA, NO UTLIZADA EN EL PROGRAMA
*/

public class listaDePalabras {
    public static class Diccionario {

        static public class nodo {
            Map< Character , nodo> Child ;
            boolean esPalabra;
            String descripcion;

            public nodo( String descripcion) {
                esPalabra = false;
                Child = new HashMap<Character, nodo>();
            }
        }
        static nodo raiz = new nodo (null);

        public static void agregar( String palabra ,  String descripcion) {
            nodo nodoActual = raiz;
            int tamano = palabra.length();
            for(int i = 0; i < tamano;i++) {
                char letraActual = palabra.charAt(i);
                if(nodoActual.Child.containsKey(letraActual)) {
                    nodoActual = nodoActual.Child.get(letraActual);
                }else {
                    nodoActual.Child.put(letraActual, new nodo(null));
                    nodoActual = nodoActual.Child.get(letraActual);
                }
            }
            nodoActual.esPalabra = true;
            nodoActual.descripcion = (palabra + ", " + descripcion);
            palabras.add(palabra + ", " + descripcion);
        }

        static List<String> palabras = new ArrayList<>();
        static List<String> encontradas = new ArrayList<>();

        public static void buscar(String palabra) {
            nodo nodoActual = raiz;
            encontradas = new ArrayList<>();
            for(int i = 0; i < palabra.length(); i++) {
                if(nodoActual.Child.containsKey(palabra.charAt(i))) {
                    nodoActual = nodoActual.Child.get(palabra.charAt(i));
                }else {
                    System.out.println("No se encontro palabra :/");
                    return;

                }
            }
            dfs(nodoActual);
        }

        static void dfs(nodo nodoActual) {
            if(nodoActual.esPalabra) {
                encontradas.add(nodoActual.descripcion);
            }
            if (nodoActual.Child.isEmpty()) {
                return;
            } else {
                Object[] nodosHijos = nodoActual.Child.values().toArray();
                for (int i = 0; i < nodoActual.Child.size(); i++) {
                    dfs((nodo) nodosHijos[i]);
                }
            }
        }


        public static void main(String[] args) {

            agregar("holita" , "saludo");
            agregar("holanda" ,  "pais");
            agregar("perro" , "animal canino");
            agregar("holisa" , "animal canino");
            buscar("holi");
            for (int i = 0; i < encontradas.size(); i++) {
                System.out.println(encontradas.get(i));
            }

        }
    }
}
