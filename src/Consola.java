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
public class Consola {
    
    
    private final File carpetaRaiz;
    private File carpetaActual;

    public Consola(File carpetaRaiz) throws IOException {
        if (carpetaRaiz == null) {
            
            throw new IllegalArgumentException("La carpeta raíz no puede trner un valor nulo.");
        }

        this.carpetaRaiz = carpetaRaiz.getCanonicalFile();

        if (!this.carpetaRaiz.exists() && !this.carpetaRaiz.mkdirs()) {
           
            throw new IOException("No se pudo crear la carpeta raíz.");
        }

        if (!this.carpetaRaiz.isDirectory()) {
            throw new IOException("La ruta raíz no es una carpeta.");
        }

        this.carpetaActual = this.carpetaRaiz;
    }

    public File getCarpetaRaiz() {
        return carpetaRaiz;
    }

    public File getCarpetaActual() {
        return carpetaActual;
    }

    public String getPrompt() {
        return carpetaActual.getPath() + ">";
    }

    public File resolverRuta(String ruta) throws IOException {
        
        File archivo = new File(ruta);

        if (!archivo.isAbsolute()) {
            
            archivo = new File(carpetaActual, ruta);
        }

        archivo = archivo.getCanonicalFile();

        if (!DentroDeLaRaiz(archivo)) {
            throw new IOException("No se permite trabajar fuera de la carpeta raíz.");
        }

        return archivo;
    }

    public boolean DentroDeLaRaiz(File archivo) throws IOException {
        File actual = archivo.getCanonicalFile();

        while (actual != null) {
            if (actual.equals(carpetaRaiz)) {
               
                return true;
            }

            actual = actual.getParentFile();
        }

        return false;
    }

    public boolean esHijoDirectoDeLaCarpetaActual(File archivo)
            throws IOException {

        File archivoCanonico = archivo.getCanonicalFile();
        
        File padre = archivoCanonico.getParentFile();

        return padre != null && padre.equals(carpetaActual);
    }

    public void cambiarCarpeta(File nuevaCarpeta) throws IOException {
        nuevaCarpeta = nuevaCarpeta.getCanonicalFile();

        if (!DentroDeLaRaiz(nuevaCarpeta)) {
            throw new IOException( "No se permite salir de la carpeta raíz." );
        }

        if (!nuevaCarpeta.exists()) {
            throw new IOException("La carpeta indicada no existe.");
        }

        if (!nuevaCarpeta.isDirectory()) {
            throw new IOException("La ruta indicada no es una carpeta.");
        }

        carpetaActual = nuevaCarpeta;
    }

    public boolean regresar() throws IOException {
       
        if (carpetaActual.equals(carpetaRaiz)) {
           
            return false;
        }

        File carpetaAnterior = carpetaActual.getParentFile();

        if (carpetaAnterior == null || !DentroDeLaRaiz(carpetaAnterior)) {
            return false;
        }

        carpetaActual = carpetaAnterior.getCanonicalFile();
        return true;
    }
}

