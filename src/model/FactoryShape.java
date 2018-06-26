
package model;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FactoryShape
{
    static Shape shape;//супер-класс
    String type = "Shape"; 
    static ArrayList<Class> allShapes = new ArrayList<Class>();
    
    public static void register(Class classe)
    {
        allShapes.add(classe);
    }
    
    //Объявление фигуры
    public static Shape input (String type)
    {
        Class classe;    
        Shape Action = null;

        for (int i = 0; i < allShapes.size(); i++)
        {     
            if (allShapes.get(i).getName().equals("model." + type))
            {
                System.out.println(allShapes.get(i).getName());
                try 
                {
                    Action = (Shape) allShapes.get(i).newInstance();
                } 
                catch (InstantiationException | IllegalAccessException ex)
                {
                    Logger.getLogger(FactoryShape.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return Action;   
    }
               
    //Повторное присвоение фигуры после отрисовки
    public Shape repeat (Shape ActionShape)
    {
        Class classe;    
        Shape Action = null;//СЮДА ЗАНОСИМ ПРИСВОЕНИЕ CLICK_MODE - сложная или простая фигура
        type = ActionShape.type;

        for (int i = 0; i < allShapes.size(); i++)
        {
            if (allShapes.get(i).getName().equals("model." + type))
            {
                if (allShapes.get(i).getName().equals("model." + type))
                try {
                    Action = (Shape) allShapes.get(i).newInstance();
                } catch (InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(FactoryShape.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return Action;   
    }
}  