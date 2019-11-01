package PLA5_Actividad;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static PLA5_Actividad.Uti.*;
import java.util.function.Function;
import java.util.Scanner;

public class Main {
  /*Para el texto de entrada, también podría haber jugado con inyectar un Scanner
    o Archivo de texto como hice en la actividad 3 de SOLID con el ejemplo de PPTLS*/
  /*Usamos un típico pangrama en castellano para testar todo*/
  static String textoPrueba = "El veloz murciélago hindú comía feliz cardillo y kiwi. "
    + "La cigüeña tocaba el saxofón detrás del palenque de paja.";
 
  static Scanner scnEntrada = new Scanner(System.in);
  
  /****************************************************************************/
  public static void main(String[] args) {
    Cabecera("");
    impln("Introduzca una cadena de texto para codificar y descodificar o pulse Enter para usar la cadena:\n"
      + "\"El veloz murciélago hindú comía feliz cardillo y kiwi. La cigüeña tocaba el saxofón detrás del palenque de paja.\"\n");
    imp("Introduzca texto: ");
    /*Si hubiera más de un espacio en algún lugar, lo dejo en sólo uno.*/
    String textoIntroducido = scnEntrada.nextLine().replaceAll("[\\s]+"," ");
    if (!textoIntroducido.equals("")) {textoPrueba = textoIntroducido;}
    impln();
    
    /*En el ejercicio se piden estas dos configuraciones*/
    /*------------------------------------*/
    /* CODIFICADOR 1: PALABRAS + INVERTIR */
    /*------------------------------------*/    
    ConfiguracionProcesarCodificar(ConfigPalabrasInvertir.class, textoPrueba);
    
    /*---------------------------------*/
    /* CODIFICADOR 2: PALABRAS + CESAR */
    /*---------------------------------*/
    ConfiguracionProcesarCodificar(ConfigPalabrasCesar.class, textoPrueba);
    
    /*Además, añado las siguientes*/
    /*------------------------------------*/
    /* CODIFICADOR 3: BLOQUES + INVERTIR  */
    /*------------------------------------*/    
    ConfiguracionProcesarCodificar(ConfigBloquesInvertir.class, textoPrueba);
    
    /*---------------------------------*/
    /* CODIFICADOR 4: BLOQUES + CESAR  */
    /*---------------------------------*/
    ConfiguracionProcesarCodificar(ConfigBloquesCesar.class, textoPrueba);    
    
    /*----------------------------------------------------------*/
    /* CODIFICADOR 5: PALABRAS + CESAR -> BLOQUES + INVERTIR    */
    /* DESCODIFICAR: al revés para ver si conmutan en este caso */
    /*----------------------------------------------------------*/    
    /*A parte, he querido crear una configuración nueva: Codificación doble
      diferente y probar la Descodificación en sentido contrario...*/
    ConfiguracionDobleProcesarCodificar(textoPrueba);
  }
  /****************************************************************************/
  
  static void ConfiguracionProcesarCodificar(Class claseConfig, String textoPrueba) {
    switch (claseConfig.getName().replaceAll(".*[.](.+$)","$1")) {
      case "ConfigPalabrasInvertir":
        Cabecera("Procesado por Palabras y codificando por Inversión");
      break;
      case "ConfigPalabrasCesar":
        Cabecera("Procesado por Palabras y codificando por Cesar");
      break;
      case "ConfigBloquesInvertir":
        Cabecera("Procesado por Bloques y codificando por Inversión");
      break;
      case "ConfigBloquesCesar":
        Cabecera("Procesado por Bloques y codificando por Cesar");
      break;
    }  
    
    /*Cargamos el contexto*/
    AnnotationConfigApplicationContext contexto = 
      new AnnotationConfigApplicationContext(claseConfig);
    /*Pedimos el Bean*/
    Codificador miCodificador = contexto.getBean("codificador", Codificador.class);
    
    impln("Texto original:     \"", textoPrueba, "\"");
    /*Ejecutamos los métodos con el texto facilitado*/
    String textoPruebaCodificado = miCodificador.codificar(textoPrueba);
    impln("Texto codificado:   \"", textoPruebaCodificado, "\"");
    
    /*Y al revés recuperamos el texto original*/
    impln("Texto descodificado:\"", miCodificador.decodificar(textoPruebaCodificado), "\"\n");
    contexto.close();  
  }
  
  static void ConfiguracionDobleProcesarCodificar(String textoPrueba) {
    Cabecera("Doble codificación: PalabrasCesar + BloquesInvertir -> Descodificación: PalabrasCesar + BloquesInvertir");
    /*Crearé dos contextos, cada uno con su codificador*/
    
    /*CONTEXTO Palabras-Cesar*/
    AnnotationConfigApplicationContext contextoPC = new AnnotationConfigApplicationContext(ConfigPalabrasCesar.class);
    Codificador miCodPC = contextoPC.getBean("codificador", Codificador.class);
    /*CONTEXTO Bloques-Invertir*/
    AnnotationConfigApplicationContext contextoBI = new AnnotationConfigApplicationContext(ConfigBloquesInvertir.class);
    Codificador miCodBI = contextoBI.getBean("codificador", Codificador.class);   
    
    /*Funciones que efectúan una sola operación y que compondré*/
    Function<String,String> CodPC = (t) -> miCodPC.codificar(t);
    Function<String,String> CodBI = (t) -> miCodBI.codificar(t);
    Function<String,String> DescodPC = (t) -> miCodPC.decodificar(t);
    Function<String,String> DescodBI = (t) -> miCodBI.decodificar(t);
   
    impln("Texto original:         \"", textoPrueba, "\"");
    
    /*CODIFICACIÓN DOBLE PC -> BI*/
    /*Codificación PalabrasCesar*/
    impln("Texto codificado(PC):   \"" + CodPC.apply(textoPrueba) + "\"");
    
    /*Codificación BloquesInvertir*/
    impln("Texto codificado(PC+BI):\"", CodPC.andThen(CodBI).apply(textoPrueba), "\"");    
    
    /*DESCODIFICACIÓN INVERSA PC -> BI*/
    /*Descodificación PalabrasCesar*/
    impln("Texto codificado(BI):   \"", CodPC.andThen(CodBI).andThen(DescodPC).apply(textoPrueba), "\"");    

    /*Descodificación BloquesInvertir*/
    impln("Texto descodificado:    \"", CodPC.andThen(CodBI).andThen(DescodPC).andThen(DescodBI).apply(textoPrueba), "\""); 
  }
}