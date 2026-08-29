import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ian Suazo Palao
 */
public class CMDArchivos {
    private Consola dir;

    public CMDArchivos(Consola dir) {
        this.dir = dir;
    }
    
    public String rd(String nombre){
        try{
            File f=dir.resolverRuta(nombre);
            
            if(!dir.DentroDeLaRaiz(f)){
                return "Acesso denegado";
            }
            
            if(!f.exists()){
                return "Hay un error, este archivo no existe";
            }
            
            if(f.isDirectory()){
                return "Hay un error, esta es una carpeta y no un archivo";
            }
            
            StringBuilder s=new StringBuilder();
            FileReader fr=new FileReader(f);
            BufferedReader b=new BufferedReader(fr);
            
            String txt;
            while((txt=b.readLine())!= null){
                s.append(txt).append("\n");
            }
            fr.close();
            b.close();
            return s.toString();
        }
        catch(Exception e){
            return "Error al leer el archivo"+e.getMessage();
        }
    }
    
    public String wr(String nombre, List<String> txt){
        try{
            File f=dir.resolverRuta(nombre);
            
            if(!dir.DentroDeLaRaiz(f)){
                return "Acceso ha sido denegado";
            }
            if(f.isDirectory()){
                return "Hay un error, no se puede escribir sobre una carpeta";
            }
            
            FileWriter file_w = new FileWriter(f, false);
            for (String l : txt){
                file_w.write(l+"\n");
            }
            file_w.close();
            return "Archivo escrito exitosamente.";
        }
        catch(Exception e){
            return "Ha habido un error al escribir en el archivo: "+e.getMessage();
        }
    }
    
    public String ap(String nombre, List<String> txt){
        try{
            File f=dir.resolverRuta(nombre);
            
            if(!dir.DentroDeLaRaiz(f)){
                return "Acceso ha sido denegado";
            }
            if(f.isDirectory()){
                return "Hay un error, no se puede escribir sobre una carpeta";
            }
            
            FileWriter file_w = new FileWriter(f, true);
            for (String l : txt){
                file_w.write(l+"\n");
            }
            file_w.close();
            return "Contenido agregado exitosamente.";
        } 
        catch (Exception e) {
            return "Ha habido un error al escribir en el archivo: "+e.getMessage();
        }
    }
    
    public String copy(String inicio, String fin){
        try{
            File orig=dir.resolverRuta(inicio);
            File end=dir.resolverRuta(fin);
            
            if(!dir.DentroDeLaRaiz(orig)|| !dir.DentroDeLaRaiz(end)){
                return "Acesso denegado";
            }
            
            if (!orig.exists()){
                return "Hay un error, el archivo de origen no existe";
            }
            
            if (orig.isDirectory()){
                return "Hay un error, solo se puede copiar archivos indivuales y no una carpeta.";
            }
            
            if (end.isDirectory()){
                end=new File(end,orig.getName());
            }
            
            FileReader file_r = new FileReader(orig);
            FileWriter file_w = new FileWriter(end);
            int a;
            while ((a=file_r.read()) !=-1){
                file_w.write(a);
            }
            file_r.close();
            file_w.close();
            return "1 archivo copiado.";
        } 
        catch (Exception e){
            return "Ha habido un error al copiar: "+e.getMessage();
        }  
    }
    public String info(String nombre) {
        try{
            File file = dir.resolverRuta(nombre);

            if (!dir.DentroDeLaRaiz(file)){
                return "Acceso denegado por estar afuera de la raiz";
            }

            if (file.exists()) {
                String salida="\nSI EXISTE\n\n";
                salida+="Nombre: "+file.getName()+"\n";
                salida+="Path: "+file.getPath()+"\n";
                salida+="Absoluta: "+file.getAbsolutePath()+"\n";
                File padre=file.getAbsoluteFile().getParentFile();
                salida+="Padre: "+(padre!=null ? padre.getName() : "")+"\n";
                salida+="Bytes: "+file.length()+"\n";

                if (file.isFile()){
                    salida+="Es un archivo\n";
                } 
                else if (file.isDirectory()){
                    salida+="Es un folder\n";
                }

                salida += "Ultima modificacion: "+new Date(file.lastModified());
                return salida;
            } 
            else{
                return "Aun no existe";
            }
        }
        catch(Exception e){
            return "Error al obtener informacion: "+e.getMessage();
        }
    }
    
    public String find(String txt){
        StringBuilder sb=new StringBuilder();
        int found=buscar(dir.getCarpetaActual(),txt.toLowerCase(),sb);
        
        if (found==0){
            return "No se encontraron coincidencias para "+txt+".";
        }
        
        sb.append("\nTotal encontrados: ").append(found);
        return sb.toString();
    }
    
    private int buscar(File carpeta, String texto, StringBuilder sb) {
        int indx=0;
        File[] hijos=carpeta.listFiles();
        if (hijos!=null){
            for (File f : hijos){
                if (f.getName().toLowerCase().contains(texto)){
                    sb.append(f.isDirectory() ? "(DIR)  ":"(FILE) ").append(f.getAbsolutePath()).append("\n");
                    indx++;
                }
                if (f.isDirectory()){
                    indx+=buscar(f,texto,sb);
                }
            }
        }
        return indx;
    }
    
    public String tree(){
        String arbol=dir.getCarpetaActual().getName()+"\n";
        arbol+=TreeBuilder(dir.getCarpetaActual(),"   ");
        return arbol;
    }

    private String TreeBuilder(File carpeta, String spc){
        String res="";
        File[] hijos=carpeta.listFiles();

        if (hijos != null){
            for (int i = 0; i < hijos.length; i++){
                File f=hijos[i];
                res+=spc+"|-- "+f.getName()+"\n";
                
                if (f.isDirectory()){
                    res+=TreeBuilder(f,spc+"|   ");
                }
            }
        }
        return res;
    }
    
    public String help(){
        return "Comandos disponibles:\n"
             +"  Mkdir <nombre>          - Crea una nueva carpeta.\n"
             +"  Mfile <nombre.ext>      - Crea un nuevo archivo.\n"
             +"  Rm <nombre>             - Elimina un archivo o carpeta.\n"
             +"  Cd <nombre carpeta>     - Cambia a la carpeta indicada.\n"
             +"  ..                      - Regresa a la carpeta anterior.\n"
             +"  Dir                     - Lista el contenido de la carpeta actual.\n"
             +"  Date                    - Muestra la fecha del sistema.\n"
             +"  Time                    - Muestra la hora del sistema.\n"
             +"  Wr <archivo.ext>        - Escribe texto en un archivo (termina con EXIT).\n"
             +"  Rd <archivo.ext>        - Muestra el contenido del archivo.\n"
             +"  Ap <archivo.ext>        - Agrega texto al final del archivo (termina con EXIT).\n"
             +"  Ren <actual> <nuevo>    - Renombra un archivo o carpeta.\n"
             +"  Copy <origen> <destino> - Copia un archivo a otra ruta o nombre.\n"
             +"  Find <nombre>           - Busca archivos o carpetas por nombre.\n"
             +"  Info <nombre>           - Muestra información del archivo/carpeta.\n"
             +"  Tree                    - Muestra la estructura de carpetas en árbol.\n"
             +"  Cls                     - Limpia la pantalla.\n"
             +"  Help                    - Muestra esta lista de comandos.\n"
             +"  Exit                    - Cierra la consola.";
    }
}