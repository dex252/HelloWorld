
package controller;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Serializator 
{
    public void serialization(Queue que, String name)
    {
        File file = new File ("D:/Projects/HelloWorld/saves/" + name + ".qqw");
        ObjectOutputStream oos = null;
        try 
        {
            FileOutputStream fos = new FileOutputStream(file);
            if (fos!=null)
            {
            oos = new ObjectOutputStream(fos);
            oos.writeObject(que);
            System.out.println("Серилизация прошла успешно");
            }
        } 
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(Serializator.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Косяк во время серилизации");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Путь к файлу закосячен");
        }
        finally 
        {
            if (oos!=null) try
            {
                oos.close();
            }
            catch (IOException ex) 
            {
                Logger.getLogger(Serializator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
    
    public Queue deserialization (String name) throws InvalidObjectException
    {
        File file = new File ("D:/Projects/HelloWorld/saves/" + name + ".qqw");
        ObjectInputStream ois = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            if (fis!=null)
            {
            ois = new ObjectInputStream(fis);
            Queue que = (Queue)ois.readObject();
            System.out.println("Объекты успешно загружены");
            return que;
            }
            } 
        catch (FileNotFoundException ex) 
        {
            //Logger.getLogger(Serializator.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Путь к файлу не найден");
        }
        catch (ClassNotFoundException ex) 
        {
         //   Logger.getLogger(Serializator.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Класс не найден");
        }
        finally 
        {      
            if (ois!=null) 
            try
            {
            ois.close();
            }
            catch (IOException ex)
            {
                // Logger.getLogger(Serializator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        throw new   InvalidObjectException("Объект поврежден");
     }                
}
