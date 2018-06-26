
package model;

import java.awt.Color;
import java.awt.Graphics;
import static view.MainWindow.*;


public class Polygon extends BreakLine
{
   static
    {
        FactoryShape.register(Polygon.class);//Передача класса
        System.out.println(FactoryShape.allShapes);
    }
    
    public Polygon ()
    {
        this.type = "Polygon";
        this.hard = true;
    }   
    
    @Override
    public void paint(Graphics g, double scale, double x_shift, double y_shift) 
    {
        int x1, x2, y1, y2;
        g.setColor(Color.orange);
        for (int i = 0; i<xy.size()-1;i++)
        {      
            x1 = (int) (xy.get(i).x/Math.exp(scale) - x_shift) ;
            x2 = (int) (xy.get(i+1).x/Math.exp(scale) - x_shift) ;
            y1 = (int) (xy.get(i).y/Math.exp(scale) - y_shift) ;
            y2 = (int) (xy.get(i+1).y/Math.exp(scale) - y_shift) ;
            g.drawLine(x1, y1, x2, y2); 
        }
        
        if (!xy.isEmpty())
        {
            x1 = (int) (xy.get(0).x/Math.exp(scale) - x_shift) ;
            x2 = (int) (xy.get(xy.size()-1).x/Math.exp(scale) - x_shift) ;
            y1 = (int) (xy.get(0).y/Math.exp(scale) - y_shift);
            y2 = (int) (xy.get(xy.size()-1).y/Math.exp(scale) - y_shift) ;
            g.drawLine(x1, y1, x2, y2); 
        }  
        if ((view.MainWindow.regim == 4)&&(view.Canvas.DotsWeb)) paintCheck(g);
        if ((view.Canvas.Choicer)&&(view.MainWindow.regim != 4)) ChoiceWeb(g);
    }  
    
    @Override
    public void Cross(Graphics g) 
    { 
        double x, y; //смещение по осям       
        dots = new Dots();
        if (xy2.isEmpty()) 
        {
           for (int i = 0; i<xy.size(); i++) xy2.add(dots);
        }
        
        x = x2 - x1;
        y = y2 - y1;
        
       for (int i = 0; i< xy2.size(); i++)
       {
           dots = new Dots();
           dots.x = xy.get(i).x + x;
           dots.y = xy.get(i).y + y;
           xy2.set(i, dots);
       }
       
       int x1,y1,x2,y2;
       g.setColor(Color.pink);
       for (int i = 0; i<xy.size()-1;i++)
       {      
            x1 = (int) (xy2.get(i).x/Math.exp(scale) - x_shift);
            x2 = (int) (xy2.get(i+1).x/Math.exp(scale) - x_shift) ;
            y1 = (int) (xy2.get(i).y/Math.exp(scale) - y_shift) ;
            y2 = (int) (xy2.get(i+1).y/Math.exp(scale) - y_shift) ;
            g.drawLine((int)x1, (int)y1, (int)x2, (int)y2); 
        }
       
        if (!xy.isEmpty())
        {
            x1 = (int) (xy2.get(0).x/Math.exp(scale) - x_shift) ;
            x2 = (int) (xy2.get(xy2.size()-1).x/Math.exp(scale) - x_shift) ;
            y1 = (int) (xy2.get(0).y/Math.exp(scale) - y_shift) ;
            y2 = (int) (xy2.get(xy2.size()-1).y/Math.exp(scale) - y_shift);
            g.drawLine((int)x1, (int)y1, (int)x2, (int)y2); 
       }  
        if ((view.Canvas.Choicer)&&(view.MainWindow.regim != 4)) ChoiceWeb(g);
    }   
    
    @Override
    public void Turned(Graphics g) 
    { 
        double alpha, beta;
        double x_max, x_min, y_max, y_min;
        x_max = xy.get(0).x;
        x_min = xy.get(0).x;
        y_max = xy.get(0).y;
        y_min = xy.get(0).y;
        //вычислить серидину по точкам из середины
        for (int i = 0; i<xy.size();i++)
        {
            x_max = Math.max(x_max, xy.get(i).x);
            x_min = Math.min(x_min, xy.get(i).x);
            y_max = Math.max(y_max, xy.get(i).y);
            y_min = Math.min(y_min, xy.get(i).y);
        }
        
        xc = (x_max + x_min)/2;//новая середина
        yc = (y_max + y_min)/2;
        alpha = Math.atan2(x1-xc, y1-yc);
        beta = Math.atan2(x2-xc, y2-yc);
        phi = alpha-beta;
        System.out.println("Угол phi = " + Math.toDegrees(phi));
        Dots dots = new Dots();
        
        if (xy2.isEmpty())
        {
           for (int i = 0; i<xy.size(); i++) xy2.add(dots);
        }
        
        for (int i = 0; i< xy2.size(); i++)
        {
            dots = new Dots();
            dots.x = (xy.get(i).x - xc) * Math.cos(phi) - (xy.get(i).y - yc) * Math.sin(phi) + xc;
            dots.y = (xy.get(i).x - xc) * Math.sin(phi) + (xy.get(i).y - yc) * Math.cos(phi) + yc;
            xy2.set(i, dots);
        }
        
        int x1, x2, y1, y2;
        g.setColor(Color.pink);
        for (int i = 0; i<xy.size()-1;i++)
        {      
            x1 = (int) (xy2.get(i).x/Math.exp(scale) - x_shift);
            x2 = (int) (xy2.get(i+1).x/Math.exp(scale) - x_shift) ;
            y1 = (int) (xy2.get(i).y/Math.exp(scale) - y_shift) ;
            y2 = (int) (xy2.get(i+1).y/Math.exp(scale) - y_shift) ;
            g.drawLine((int)x1, (int)y1, (int)x2, (int)y2); 
        }
        if (!xy.isEmpty())
        {
            x1 = (int) (xy2.get(0).x/Math.exp(scale) - x_shift) ;
            x2 = (int) (xy2.get(xy2.size()-1).x/Math.exp(scale) - x_shift) ;
            y1 = (int) (xy2.get(0).y/Math.exp(scale) - y_shift) ;
            y2 = (int) (xy2.get(xy2.size()-1).y/Math.exp(scale) - y_shift) ;
            g.drawLine((int)x1, (int)y1, (int)x2, (int)y2); 
        }  
        if ((view.Canvas.Choicer)&&(view.MainWindow.regim != 4)) ChoiceWeb(g);
    }
    
    //Повторяет Shape, т.к. я дурак и не знаю, как повторить метод после переприсвоения его в BreakLine
    @Override
    public void ChoiceClick(double x, double y) 
    {        
        System.out.println("Тыкнули на экран в точке x (" + x + ") y (" + y + ")" + " смотрим фигуру " + type);
        
        x = (x + x_shift) * Math.exp(scale);
        y = (y + y_shift) * Math.exp(scale);
        
        Dots Start = new Dots();
        Dots End = new Dots();
        Dots Click = new Dots();
        Dots Null = new Dots();
        
        //Место клика
        Click.x = x;
        Click.y = y;
        
        //точка в сторону бесконечности влево
        Null.x = -1000;
        Null.y = y;
        
        byte peresekNumber = 0;
        //Берем точки отрезков текущей фигуры
        for (int i = 0; i<xy.size()-1; i++)
        {
            Start.x = xy.get(i).x;
            Start.y = xy.get(i).y;
            
            End.x = xy.get(i+1).x;
            End.y = xy.get(i+1).y;
            
            //Высчитываем по функции пересекается ли отрезок с лучем.
            boolean peresek = checkIntersectionOfTwoLineSegments(Null, Click, Start, End) ;
            //System.out.println("Значение пересечения на шаге  " + i + " равно " + peresek);
            if (peresek)
            {
                peresekNumber++;
            }
        }
       
        //Для последней линии - соединителе (для замкнутых фигур)
        Start.x = xy.get(0).x;
        Start.y = xy.get(0).y;
            
        End.x = xy.get(xy.size()-1).x;
        End.y = xy.get(xy.size()-1).y;
        boolean peresek = checkIntersectionOfTwoLineSegments(Null, Click, Start, End) ;
        if (peresek)
           {
               peresekNumber++;
           }
        //Конец
        
        //Если количество пересечений не четно, то фигура входит в диапозон
        if (peresekNumber%2==1)
        {
            view.Canvas.Stoper = true;
           // System.out.println("Найдена фигура типа " + type + " номер #" + number);
           System.out.println("Найдена фигура типа " + type + " номер #" + view.MainWindow.number);//static
            //Блок отбора по номеру number
             view.Canvas.ConstructShape = view.Canvas.que.get(view.MainWindow.number); //static
 //           ShowName.setVisible(true);
 //           ShowName.setText(view.Canvas.ConstructShape.name);
        }
        System.out.println("Число найденных пересечений " + peresekNumber);
         
        if (!view.Canvas.Stoper)
        {
 //           ShowName.setVisible(false);
        }
    }
}
