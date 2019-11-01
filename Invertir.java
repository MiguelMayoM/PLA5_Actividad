package PLA5_Actividad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*Invertir: Codificar invierte la cadena y decodificar hace lo mismo.
  Ejemplo:  codificar("hola")->"aloh"
*/
public class Invertir implements ICodificar {
  
  @Override
  public String codificar(String cadena) {
    /*Con bucles sería, por ejemplo:
      + Leer el String carácter a carácter con CharAt(i) comenzando por la última
        posición, dada por cadena.length(); y guardando los caracteres en un nuevo
        String.
      + O hacer un split y leer un Array intercambiando los índices simétricamente:
        primero con el último, etc
      Esto ya lo hemos hecho otras veces, aunque creo que es un pelín más rápido
      que no usar programación funcional. Pero a veces tiene más importancia la
      legibilidad del código, así que a ello vamos.
      Una solución particular, en una sola línea de código, sería convertir el
      String a StringBuilder y usar su método .reverse
    */
    //return new StringBuilder(cadena).reverse().toString();
    /*De forma más genérica (para cualquier tipo de objetos de una colección, y
      no sólo caracteres dentro de un String), también Collections tiene un método
      "reverse"; así, voy a construir un Array y a revertirlo sin usar índices.
      Voy a tener en cuenta que antes haya podido codificar con CESAR y el hecho
      de que conservo acentos y diéresis con sendos caracteres especiales no
      imprimibles. Para los experimentos que quiero hacer, como codificar
      PalabrasCesar -> BloquesInvertir y luego descodificar en  sentido contrario,
      i.e. PalabrasCesar -> BloquesInvertir, tengo que tener en cuenta esto para
      mantener siempre la misma posición de los caracteres invisibles, cosa que
      haré no separándolos de su carácter, con un negative lookbehind*/
    ArrayList<String> aLstCaracteres = new ArrayList<String>(
      Arrays.asList(cadena.split("(?<![" + INVISIBLE1 + INVISIBLE2 + "])"))
    );
    Collections.reverse(aLstCaracteres);
      
    return String.join("", aLstCaracteres);
  }
  
  @Override
  public String decodificar (String cadena) {
    return codificar(cadena);
  } 
}