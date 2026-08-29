/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.File;
import java.io.IOException;

/**
 *
 * @author diego
 */
public class ComandoMkdir implements Comando {

    @Override
    public String ejecutar(String argumentos,Consola contexto) {

        String nombre = argumentos == null ? "" : argumentos.trim();

        if (nombre.isEmpty()) {
            return "Uso: Mkdir  <nombre>";
        }

        try {
            File carpeta = contexto.resolverRuta(nombre);

            if (!contexto.esHijoDirectoDeLaCarpetaActual(carpeta)) {
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
}