/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author diego
 */
public class ComandosDiego {
  public String ejecutar(String comando, String argumentos,Consola consola) {

        switch (comando) {
            case "Mkdir":
                return mkdir(argumentos, consola);

            case "Mfile":
                return mfile(argumentos, consola);

            case "Rm":
                return rm(argumentos, consola);

            case "Cd":
                return cd(argumentos, consola);

            case "..":
                return anterior(argumentos, consola);

            case "Date":
                return date(argumentos);

            case "Time":
                return time(argumentos);

            case "Ren":
                return ren(argumentos, consola);
                
            case "Dir":
                return dir(argumentos, consola);

            default:
                return null;
        }
    }

    private String mkdir(String argumentos, Consola consola) {

        String nombre = argumentos == null ? "" : argumentos.trim();

        if (nombre.isEmpty()) {
            return "Uso: Mkdir  <nombre>";
        }

        try {
            File carpeta = consola.resolverRuta(nombre);

            if (!consola.esHijoDirectoDeLaCarpetaActual(carpeta)) {
                return "Mkdir solamente puede crear una crpeta " + " dentro de la carpeta actual.";
            }

            if (carpeta.exists()) {
                return "ya existe un archivo con ese nombre";
            }

            if (!carpeta.mkdir()) {
                return "no se pudo generar la carpeta";
            }

            return "";
        } 
        
        catch (IOException ex) {
            
            return "Error en Mkdir: " + ex.getMessage();
        }
        
    }


    private String mfile(String argumentos, Consola consola) {
               String nombre = argumentos == null ? "" : argumentos.trim();

        if (nombre.isEmpty()) {
            return "Uso: Mfile <nombre.ext>";
        }

        try {
            
            File archivo = consola.resolverRuta(nombre);

            if (!consola.esHijoDirectoDeLaCarpetaActual(archivo)) {
                
                return "Mfile solo puede crear un archivo " + "dentro de la carpeta actual.";
            }

            if (archivo.exists()) {
                return "Ya existe un archivo o carpeta con ese nombre.";
            }

            if (!archivo.createNewFile()) {
                return "No se pudo crear el archivo.";
            }

            return "";
            
        } 
        catch (IOException ex) {
            
            return "Error en Mfile " + ex.getMessage();
        }
    }

    private String rm(String argumentos, Consola consola) {
        
         String nombre = argumentos == null? "" : argumentos.trim();

        if (nombre.isEmpty()) {
            return "se uso: Rm <nombre>";
        }

        try {
            File objetivo = consola.resolverRuta(nombre);

            if (!consola.esHijoDirectoDeLaCarpetaActual(objetivo)) {
                return "rm solamente puede eliminar un elemento " + "de la carpeta actual.";
            }

            if (!objetivo.exists()) {
                return "El archivo o carpeta no existen";
            }

            if (!eliminarRec(objetivo, consola)) {
                return "No se pudo eliminar completamente: " + objetivo.getName();
            }

            return "";
        } 
        catch (IOException ex) {
            return "Error en Rm: " + ex.getMessage();
        }
    }

    private boolean eliminarRec(
            File archivo,Consola contexto) throws IOException {

        if (!contexto.DentroDeLaRaiz(archivo)) {
            
            return false;
        }

        if (archivo.isDirectory()) {
            File[] contenido = archivo.listFiles();

            if (contenido == null) {
                
                return false;
            }

            for (File elemento : contenido) {
                if (!eliminarRec(elemento, contexto)) {
                   
                    return false;
                
            }
            }
        }
    return archivo.delete();
    }
        

    private String cd(String argumentos, Consola consola) {
      
        String nombre = argumentos == null? "": argumentos.trim();

        if (nombre.isEmpty()) {
            return "seUso: cd <nombre carpeta>";
        }

        try {
            File carpeta = consola.resolverRuta(nombre);

            if (!carpeta.exists()) {
                return "El sistema no puede encontrar la ruta especificada.";
            }

            if (!carpeta.isDirectory()) {
                return "La ruta indicada no es una carpeta.";
            }

            consola.cambiarCarpeta(carpeta);
            return "";
        } catch (IOException ex) {
           
            return "Error en cd: " + ex.getMessage();
        }
    }

        
    

    private String anterior(String argumentos, Consola consola) {
       
          if (argumentos != null && !argumentos.trim().isEmpty()) {
            return "Uso: ..";
        }

        try {
            if (!consola.regresar()) {
                return "No se permite salir de la carpeta rai";
            }

            return "";
           
            
        }
        catch (IOException ex) {
            
            return "Error al regresar: " + ex.getMessage();
        }
    }
        


    private String date(String argumentos) {
      
        if (argumentos != null && !argumentos.trim().isEmpty()) {
            
            return "se uso: Date";
        }

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        return formato.format(new java.util.Date());
   
    }

    private String time(String argumentos) {
      
          if (argumentos != null && !argumentos.trim().isEmpty()) { 
              
              return "se uso: Time";
        }

        SimpleDateFormat formato = new SimpleDateFormat("HH:MM:SS");

        return formato.format(new java.util.Date());
       
    }

    private String ren(String argumentos, Consola consola) {
       
        String texto = argumentos == null ? "" : argumentos.trim();

        if (texto.isEmpty()) {
            return "SE Uso: Ren <actual> <nuevo>";
        }

        String[] partes = texto.split("\\s+");

        if (partes.length != 2) {
            return "se uso Ren <actual> <nuevo>";
        }

        String nombreActual = partes[0];
        String nombreNuevo = partes[1];

        if (nombreNuevo.equals(".") || nombreNuevo.equals("..")) {
            
            return "el renombramiento no es válido.";
        }

        try {
            File origen = consola.resolverRuta(nombreActual);

            if (!origen.exists()) {
                return "el archivo o carpeta indicado no existen en el directorio";
            }

            if (consola.esHijoDirectoDeLaCarpetaActual(origen)) {
                return "Ren solamente puede renombrar elementos " + "de la carpeta actual.";
            }

            File destino = consola.resolverRuta(nombreNuevo);

            if (!consola.esHijoDirectoDeLaCarpetaActual(destino)) {
                return "El nuevo nombre no puede contener una ruta.";
            }

            if (destino.exists()) {
                return "Ya existe un archivo o carpeta " + "con esenombre.";
            }

            if (!origen.renameTo(destino)) {
                return "No se pudieron renombrar el archivo o carpeta";
            }

            return "";
            
        } catch (IOException ex) {
            return "Error en Ren: " + ex.getMessage();
        }
        
    }
    
    private String dir(String argumentos, Consola consola) {

    if (argumentos != null && !argumentos.trim().isEmpty()) {
        return "se uso: Dir";
    }

    File carpetaActual = consola.getCarpetaActual();
    
    File[] elementos = carpetaActual.listFiles();

    if (elementos == null) {
      
        return "No se pudo obtener contenido de la carpeta actual";
    }

    StringBuilder salida = new StringBuilder();

    salida.append("Directorio de ")
            
            .append(carpetaActual.getPath())
           
            .append("\n\n");

    if (elementos.length == 0) {
        salida.append("esta carpeta esta vacia");
       
        return salida.toString();
    }

    for (File elemento : elementos) {
        if (elemento.isDirectory()) {
            salida.append("<DIR>     ");
        } 
        
        else {
            salida.append("          ");
        }

        salida.append(elemento.getName()).append("\n");
    }

    return salida.toString();
}
}

