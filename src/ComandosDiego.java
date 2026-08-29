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
        
        return "";
    }

    private String cd(String argumentos, Consola consola) {
      
        return "";
    }

    private String anterior(String argumentos, Consola consola) {
       
        return "";
    }

    private String date(String argumentos) {
      
        return "";
    }

    private String time(String argumentos) {
      
        return "";
    }

    private String ren(String argumentos, Consola consola) {
       
        return "";
    }
}

