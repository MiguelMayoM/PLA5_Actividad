package PLA5_Actividad;

import java.util.Arrays;

/*Tiene una propiedad paso que vale 2 y realiza una codificación del tipo Cesar.
  Deberemos pasar todo a mayúsculas para simplificar y codificar sólo las letras.
  Ejemplo: codificar("ab")->"CD".
*/
public class Cesar implements ICodificar {
  /*No se piden los números, aunque no representaría ningún problema considerarlos,
    usando una tabla de conversión más larga. Tampoco se pide conservar las
    mayúsculas pero entiendo que es por facilitar el ejercicio, así que las
    mantendré. Aunque no se habla de ACENTOS ni de DIÉRESIS, también voy a
    conservarlos. La idea es convertir cada caráter acentuado o con diéresis en
    un caracter simple pero usar un carácter no imprimible colocado delante para
    que podamos leerlo y volver a reubicar el acento o diéresis al descodificar.
  */
  /*He visto que se suele codificar a partir del código ASCII asociado a cada
    caracter. Pero como allí no está la Ñ, voy a escoger yo los caracteres con los
    que quiero trabajar, un total de 27. Podría crear un String ALFABETO (o un
    Array con las letras) y buscar para cada letra de la cadena a codificar qué
    lugar ocupa en ese string o array "Alfabeto", para retornar el que ocupa
    N posiciones más, según el paso elegido para el cifrado Cesar. Lo que voy a
    hacer, no sé si internamente acaba haciendo lo mismo, pero voy a escribir la
    letra en cuestión como nombre de cada entrada de un enumerado, de forma que
    me dará la posición sin poner ningún código de búsqueda, tal que así (no es
    nada especial, se podría haber implementado con otras estructuras también,
    pero prefiero usar enum):
      Letras.valueOf(letra).getP()
    Para el proceso inverso, i.e. dada una posición "i", hallar su letra en el
    enum, usaré:
      Letras.values()[i]
  */
  enum Letras {A(0),B(1),C(2),D(3),E(4),F(5),G(6),H(7),I(8),J(9),
               K(10),L(11),M(12),N(13),Ñ(14),O(15),P(16),Q(17),R(18),
               S(19),T(20),U(21),V(22),W(23),X(24),Y(25),Z(26);
               /*Esta era otra opción para pasar de las letras acentuadas a 
                 no acentuadas, dándole la posición de estas últimas*/
               //Á(0),É(4),Í(8),Ó(15),Ú(21);
    private final int posicion;
    Letras(int intP) {posicion = intP;} 
    public int getP() {return posicion;}    
  }
  
  /*Si tengo una letra con acento, le asignaré la misma sin acento. Y al revés*/
  enum Acentos {A("Á"),E("É"),I("Í"),O("Ó"),U("Ú"),Á("A"),É("E"),Í("I"),Ó("O"),Ú("U");
    private final String letra;
    Acentos(String strL) {letra = strL;}
    public String getL() {return letra;}
  }
  enum Dieresis{U("Ü"),Ü("U");
    private final String letra;
    Dieresis(String strL) {letra = strL;}
    public String getL() {return letra;}  
  }
  
  @Override
  public String codificar(String cadena) {
    /*Ya puestos, podría pedir el paso de la codificación CESAR por consola. No 
      lo voy a hacer porque no se ha pedido y no reviste ninguna dificultad, no
      me proporcionará ningún conocimiento nuevo. Eso sí, por consola daré la
      opción de introducir texto*/
    /*En el otro tipo de codificación (Invertir) no hay problema con los acentos,
      o diéresis pero aquí no puedo conservarlos sobre una letra que no sea una
      vocal, tal que así: á -> (acento)b. Por ello, para conservarlos, usaré los
      siguientes caracteres invisibles, que ubico en las interfaces:
        U+200C (Zero-Width Non-Joiner) (&#8204;)
        U+200B (Zero-Width Space) (&#8203;)
      En la "cadena" también podré encontrarme signos de puntuación o espacios si
      hemos dividido por bloques
    */
    int paso = 2;
    
    return Arrays.asList(cadena.replaceAll("([áéíóúÁÉÍÓÚ])", INVISIBLE1 + "$1")
                               .replaceAll("([üÜ])", INVISIBLE2 + "$1")
      .split("")).stream()
      /*Primero me deshago de los acentos y diéresis. Y voy a conservar mayúsculas
        aunque no se pida, pues no se pide para facilitar el ejercicio*/
      .map( s -> s.matches("[áéíóú]") ?  Acentos.valueOf(s.toUpperCase()).getL().toLowerCase() : 
                 s.matches("[ÁÉÍÓÚ]") ? Acentos.valueOf(s).getL() : 
                 s.matches("[ü]") ? Dieresis.valueOf(s.toUpperCase()).getL().toLowerCase() : 
                 s.matches("[Ü]") ? Dieresis.valueOf(s).getL() :  s
      )
      .map( s -> s.matches("[A-ZÑLa-zñ]") ? codPaso(s, paso) : s)
      .reduce("", String::concat);
  }
  
  @Override
  public String decodificar(String cadena) {
    /*Aquí, lo normal habría sido obrar como en el "codificar-decodificar" de la
      clase "Invertir", habiendo implementado totalmente el cifrado CESAR en el
      método "codificar" anterior y ahora valernos de este para descodificar. En
      la clase invertir era obvio o inmediato, pero aquí existe el matiz del paso,
      cuya suma en ambos procesos ha de ser 0 o a la del número de ítems del
      conjunto de letras que se cifran (2 + 25 = 27 (ñ incluída)). Pero al ser
      "codificar" y "decodificar" métodos de una interface, hemos de respetar los
      parámetros para que sean los mismos tanto para Cesar como para Invertir,
      i.e. un simple String (También se podría haber definido un segundo parámetro
      en la interface que fuera optativo y que cada función de codificación
      estuviera avisada, usándolo o no). Siguiendo con las cosas como están, lo
      que he hecho es crear internamente en esta clase CESAR esa función que
      codifica recibiendo ese parámetro "paso" (además de la "cadena"), para que
      los métodos "codificar" y "decoficar" puedan hacer uso de él y no haya que
      repetir el mismo código de cifrado CESAR en ambos*/
    int paso = 27 - 2;
    
    /*Puedo hacer dos cosas, para recuperar los acentos y diéresis:
      + Partir el string por caracteres y en el reduce ir mirando cada carácter
        para que, cuando encuentre uno invisible, aplique acento sobre el
        siguiente. Es la que he adoptado. Es adaptable a trabajar con "chars" en
        vez de Strings
      + Hacer un split que devuelva caracteres sueltos cuando no tengan acento
        y grupos de dos (invisible + carácter) cuando haya acento diéresis.
        Comentar que para realizar el split, en este caso, debería hacer un
        "Negative Lookbehind" que parta todos los caracteres excepto cuando va
        seguido de "Invisible"
    */
      //System.out.println(Arrays.asList(cadena.split("(?<![" + INVISIBLE1 + INVISIBLE2 + "])")));    
    
    return Arrays.asList(cadena.split("")).stream()
      .map( s -> s.matches("[A-ZÑa-zñ]") ? codPaso(s, paso) : s)
      .reduce("", (s1, s2) -> /*s1 es el acumulador*/
        s1.matches(".*" + INVISIBLE1 + "$") ?
          s2.equals(s2.toLowerCase()) ? s1 + Acentos.valueOf(s2.toUpperCase()).getL().toLowerCase()
                                      : s1 + Acentos.valueOf(s2).getL()
          :
            s1.matches(".*" + INVISIBLE2 + "$") ?
              s2.equals(s2.toLowerCase()) ? s1 + Dieresis.valueOf(s2.toUpperCase()).getL().toLowerCase()
                                          : s1 + Dieresis.valueOf(s2).getL()
              :s1 + s2
      ).replace(INVISIBLE1, "").replace(INVISIBLE2, "");
  }
  
  public String codPaso(String s, int paso) {
    return s.matches(s.toUpperCase()) ? 
             Letras.values()[(Letras.valueOf(s).getP() + paso) % 27].toString()
             : s.matches(s.toLowerCase()) ? 
               Letras.values()[(Letras.valueOf(s.toUpperCase()).getP() + paso) % 27].toString().toLowerCase()
               : s;
  }
}