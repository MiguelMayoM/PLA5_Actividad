package PLA5_Actividad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toCollection;
import java.util.stream.IntStream;

/*Bloques: Tiene una longitud predeterminada de 4 y divide la cadena en bloques
  de esa longitud y para unirlas simplemente las vuelve a poner en fila.
  Ejemplo: "hola, que tal"->["hola",", qu","e ta","l"]
*/
public class Bloques implements IProcesar {
 
  @Override
  public ArrayList<String> dividir(String cadena) {
    /*Voy a usar también programación funcional en vez de imperativa(bucle "for").
    /*Con una regex, también se puede dividir una cadena en grupos de 4, aunque
      luego me faltaría considerar que no cuente los caracteres invisibles:
      cadena.split("(?<=\\G.{4})")
    */
    /*Para no considerar los carcteres invisibles que mantiene acentos y diéresis,
      voy a hacer primero un split que mantenga estos caracteres unidos a las
      letras que acompañan*/
    ArrayList<String> caracteres = new ArrayList<>(
      Arrays.asList(cadena.split("(?<![" + INVISIBLE1 + INVISIBLE2 + "])"))
    );
    
    /*Uso programación funcional y Collectors.groupingBy para agrupar en trozos
      de igual medida*/
    int medidaBloque = 4;
    return IntStream
      .range(0, caracteres.size())  /*Rango de números desde 0 a tamaño - 1*/
      .boxed()
      .collect(Collectors.groupingBy(ind -> ind / medidaBloque))
      .values().stream()            /*En este punto tengo: [0,1,2,3], ...*/
      .map(elem -> elem             /*Para cada elems del stream [n1,n2,n3,n4]*/
        .stream()                   /*Cada elemento por separado, e.g. n3*/
        .map(caracteres::get)       /*Obtengo el valor(letra) que le toca */
        .reduce("", String::concat) /*Y uno los 4 valores en un String*/
        
      )/*Y lo recolecto en un nuevo ArrayList, pues partía de un IntStream*/
      .collect(toCollection(ArrayList::new));                                 
  }
    
  @Override
  public String unir(ArrayList<String> cadenas) {
    return  cadenas.stream()
                   .reduce("", String::concat);
  }
}