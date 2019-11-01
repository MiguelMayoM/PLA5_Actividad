package PLA5_Actividad;

import java.util.ArrayList;
import static java.util.stream.Collectors.toCollection;

public class Codificador {
  /*Variables a inyectar en el constructor que serán proporcionadas por Spring
    en los archivos de configuración correspondientes*/
  IProcesar _procesar;
  ICodificar _codificar;
  
  public Codificador(IProcesar procesar, ICodificar codificar) {
    _procesar = procesar;
    _codificar = codificar;
  }
  
  public String codificar(String cadena) {
    /*Esta es la primera clase que he implementado. He usado un bucle pero después
      para el resto de la actividad me he propuesto usar programación funcional,
      pues ya aquí el propio IDE me lo sugería, y así se evitará crear recursos
      intermedios, al menos en el código escrito, quedando todo más limpio*/
    /*1.- Dividir cadena en trozos con el método "dividir" de IProcesar*/
    ArrayList<String> aLstTrozos = _procesar.dividir(cadena);
    
    /*2.- Codificar cada uno de los elementos con el método "codificar" de ICodificar
      Podría leer el objeto de la posición, eliminarlo del ArrayList, modificarlo
      y reintroducirlo en la posición que ocupaba. O crear un nuevo ArrayList*/
    ArrayList <String> aLstTrozosCodificados = new ArrayList<>(0);
    for(String trozo : aLstTrozos) {
      aLstTrozosCodificados.add(_codificar.codificar(trozo));
    }
    /*Aquí ya el IDE me sugería hacer:*/
    //aLstTrozos.forEach((trozo) -> {
    //  aLstTrozosCodificados.add(_codificar.codificar(trozo));
    //});
    
    /*3.- Unir los trozos con el método "unir" de IProcesar y devolverlos*/    
    return _procesar.unir(aLstTrozosCodificados);
  }
  
  public String decodificar(String cadena) {
    /*Abordamos ahora la tarea con programación funcional (hasta el resto de la
      actividad), creando, al menos explícitamente/visualmente, los mínimos
      recursos intermedios. Lo hemos separado en líneas, pero ocupa bastante
      menos y con menos "verbosity". El procedimiento es análogo al anterior,
      pero con "Decodificar" en el punto 2*/
    return _procesar.unir(
      _procesar.dividir(cadena)
      .stream()
      .map(_codificar::decodificar) /*que equivale a:*/
      //.stream().map((trozo) -> {
      //  return _codificar.decodificar(trozo);
      //})
      .collect(toCollection(ArrayList::new)));
      /*El operador .collect crea una List, especificando después que queremos un
        ArrayList*/  
  } 
}