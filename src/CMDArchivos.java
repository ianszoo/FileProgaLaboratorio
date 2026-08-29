
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
    private CMDDirectorio dir;

    public CMDArchivos(CMDDirectorio dir) {
        this.dir = dir;
    }
    
    public String rd(String nombre){
        File f=dir.resolverRuta(nombre);
        
        if(!dir.dentroDeRaiz(f)){
            return "Acesso denegado";
        }
        
        if(!f.exists()){
            return "Hay un error, este archivo no existe";
        }
        
        if(f.isDirectory()){
            return "Hay un error, esta es una carpeta y no un archivo";
        }
        
        StringBuilder s=new StringBuilder();
        try{
            FileReader fr=new FileReader(f);
            BufferedReader b=new BufferedReader(fr);
            
            String txt;
            while((txt=b.readLine())!= null){
                s.append(txt).append("/n");
            }
            fr.close();
            b.close();
        }
        catch(Exception e){
            return "Error al leer el archivo"+e.getMessage();
        }
        return s.toString();
    }
    
    public String wr(String nombre, List<String> txt){
        File f=dir.resolverRuta(nombre);
        
        if(!dir.dentroDeRaiz(f)){
            return "Acceso ha sido denegado";
        }
        if(f.isDirectory()){
            return "Hay un error, no se puede escribir sobre una carpeta";
        }
        
        try{
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
        File f=dir.ResolverRuta(nombre);
        
        if(!dir.dentroDeRaiz(f)){
            return "Acceso ha sido denegado";
        }
        if(f.isDirectory()){
            return "Hay un error, no se puede escribir sobre una carpeta";
        }
        
        try{
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
        File orig=dir.resolverRuta(inicio);
        File end=dir.resolverRuta(fin);
        
        if(!dir.dentroDeRaiz(orig)|| !dir.dentroDeRaiz(end)){
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
        
        try{
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
}
