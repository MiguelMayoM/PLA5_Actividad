package PLA5_Actividad;

import java.util.ArrayList;
import java.util.Arrays;

/*Divide la cadena en 'palabras' (el espacio, no tenemos en cuenta otros
  caracteres) al dividir y las junta con un espacio al unir.
  Ejemplo: "hola, que tal"->["hola,","que","tal"]
*/ 
public class Palabras implements IProcesar {
  @Override
  public ArrayList<String> dividir(String cadena) {
    /*Uso "Diamond Operator para no repetir especificaciones de parámetros obvios"*/
    return new ArrayList<>(
      Arrays.asList(cadena.split(" "))
    );
    /*Si no se pidiera un ArrayList como salida, podría haber usado programación
      funcional y retornar un stream:
      Stream<String> palabras = Pattern.compile(" ").splitAsStream(cadena);
      Trataría este stream con la codificación elegida y después en unir.
    */
  }
    
  @Override
  public String unir(ArrayList<String> cadenas) {
    //String textoUnido = "";
    //for(String strPalabra : cadenas) {
    //  textoUnido += strPalabra + " ";
    //}
    /*Y le quitamos el espacio final. No sé si sería mejor hacer un bucle con
      índices y controlar de no añadir un espacio cuando sea el último índice...*/
    //return textoUnido.substring(0, textoUnido.length()-1);
    
    /*En vez de hacerlo así, que ya funciona, voy a usar PROGRAMACIÓN FUNCIONAL: 
      + Los elementos del ArrayList se convierten en un "stream" o flujo de
        elementos.
      + Puedo tomarlos uno a uno ("mapearlos") y modificarlos añadiéndoles un " "
      + Finalmente, "reducir" el stream, convirtiendo el flujo de elementos a uno
        solo. Uso "" para no tener que usar "java.util.Optional;" en la parte del
        acumulador; e indico qué operación realizar con cada elemento del stream,
        en este caso usar el método concat de String. Elimino el espacio final.
    */
    return cadenas.stream()
                  .map((palabra) -> palabra + " ")
                  .reduce("", String::concat).replaceAll("(.+)\\s{1}$","$1");
                  /*También podía no usar map si defino la unión en el reduce así:*/
                  //.reduce("", (palabra1, palabra2) ->
                  //  palabra1 + " " + palabra2
                  //)
                  //.replaceAll("^\\s{1}(.+)","$1"); /*Elimino el espacio inicial*/
  }  
}