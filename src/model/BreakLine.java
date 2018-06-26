
package model;

import java.awt.Color;
import java.awt.Graphics;
import static view.MainWindow.*;

public class BreakLine extends Shape
{
    Dots dots = new Dots();
    
     static
    {
        FactoryShape.register(BreakLine.class);//Передача класса
    }
    
    public BreakLine ()
    {
    this.type = "BreakLine";
    this.hard = true;
    }  
   
    @Override
    public void Click (double x1, double y1)
    {
        dots = new Dots();//создаем новый объект Dots для занесенея в список
        dots.x = (x1+x_shift)*Math.exp(scale);
        dots.y = (y1+y_shift)*Math.exp(scale);
        xy.add(dots); 
        System.out.println(xy.size());
    }
            
    @Override
    public void FirstClick (double x1, double y1)
    {
        if (view.MainWindow.regim==0)
        {
            if (xy.isEmpty())
            {
              
                dots.x = (x1+x_shift)*Math.exp(scale);
                dots.y = (y1+y_shift)*Math.exp(scale);
                xy.add(dots);
                dots = new Dots();//создаем новый объект Dots для занесенея в список
                xy.add(dots);
            } 
            else
            {
                Click(x1,y1);//здесь не меняем, т.к. меняется в Click()
            } 
        }
        else
        {
            this.x1 = (x1+x_shift)*Math.exp(scale);
            this.y1 = (y1+y_shift)*Math.exp(scale);
        }
    }
    
    @Override
    public void LastClick (double x1, double y1)
    {
        if (view.MainWindow.regim==0)
        {
            dots.x = (x1+x_shift)*Math.exp(scale);
            dots.y = (y1+y_shift)*Math.exp(scale);
        }
        else
        {
            this.x2 = (x1+x_shift)*Math.exp(scale);
            this.y2 = (y1+y_shift)*Math.exp(scale);
        }
    }  
   
    @Override
    public void paint(Graphics g, double scale, double x_shift, double y_shift) 
    {
        int x1, x2, y1, y2;
        if (!xy.isEmpty())
        {
            g.setColor(Color.MAGENTA);
            for (int i = 0; i<xy.size()-1;i++)
            {      
                x1 = (int) (xy.get(i).x/Math.exp(scale) - x_shift);
                x2 = (int) (xy.get(i+1).x/Math.exp(scale) - x_shift);
                y1 = (int) (xy.get(i).y/Math.exp(scale) - y_shift);
                y2 = (int) (xy.get(i+1).y/Math.exp(scale) - y_shift);
                g.drawLine(x1, y1, x2, y2); 
            }
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
        
        g.setColor(Color.pink);
        int x1,y1,x2,y2;
        for (int i = 0; i<xy2.size()-1;i++)
        {      
            x1 = (int) (xy2.get(i).x/Math.exp(scale) - x_shift) ;
            x2 = (int) (xy2.get(i+1).x/Math.exp(scale) - x_shift) ;
            y1 = (int) (xy2.get(i).y/Math.exp(scale) - y_shift) ;
            y2 = (int) (xy2.get(i+1).y/Math.exp(scale) - y_shift) ;
            g.drawLine(x1, y1, x2, y2); 
        }
        if ((view.Canvas.Choicer)&&(view.MainWindow.regim != 4)) ChoiceWeb(g);
    }    
    
    @Override
    public void Turned(Graphics g) 
    { 
        double x_max, x_min, y_max, y_min;
        x_max = xy.get(0).x;
        x_min = xy.get(0).x;
        y_max = xy.get(0).y;
        y_min = xy.get(0).y;
        double alpha, beta;
        //вычислить серидину по точкам из середины
        for (int i = 0; i<xy.size();i++)
        {
            x_max = Math.max(x_max, xy.get(i).x);
            x_min = Math.min(x_min, xy.get(i).x);
            y_max = Math.max(y_max, xy.get(i).y);
            y_min = Math.min(y_min, xy.get(i).y);
        }
        //новая середина
        xc = (x_max + x_min)/2;
        yc = (y_max + y_min)/2;
        //находим угол наклона
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
        
        g.setColor(Color.pink);
        int x1, x2, y1, y2;
        for (int i = 0; i<xy2.size()-1;i++)
        {      
            x1 = (int) (xy2.get(i).x/Math.exp(scale) - x_shift);
            x2 = (int) (xy2.get(i+1).x/Math.exp(scale) - x_shift);
            y1 = (int) (xy2.get(i).y/Math.exp(scale) - y_shift);
            y2 = (int) (xy2.get(i+1).y/Math.exp(scale) - y_shift);
            g.drawLine(x1, y1, x2, y2); 
        }
        if ((view.Canvas.Choicer)&&(view.MainWindow.regim != 4)) ChoiceWeb(g);
    }
    
    
    @Override
    public void Relise() 
    {    
        if (!xy2.isEmpty())
        {
           for (int i = 0; i<xy.size(); i++)
           {
               xy.set(i, xy2.get(i));
           }
           Check();
       }
    }
    
    @Override
    public void ChoiceClick(double x, double y) 
    {        
        System.out.println("Тыкнули на экран в точке x (" + x + ") y (" + y + ")" + " смотрим фигуру " + type);
        
        x = (x + x_shift) * Math.exp(scale);
        y = (y + y_shift) * Math.exp(scale);
        
        Dots Start = new Dots();
        Dots End = new Dots();
        
        Dots Click1 = new Dots();
        Dots Click2 = new Dots();
        
        //Место клика превращаем в вертикальную маленькую линию
        Click1.x = x+1;
        Click1.y = y;
        
        Click2.x = x-1;
        Click2.y = y;
        
        byte peresekNumber = 0;
        //Берем точки отрезков текущей фигуры
        for (int i = 0; i<xy.size()-1; i++)
        {
            Start.x = xy.get(i).x;
            Start.y = xy.get(i).y;
            
            End.x = xy.get(i+1).x;
            End.y = xy.get(i+1).y;
            
            //Высчитываем по функции пересекается ли отрезок с лучем.
            boolean peresek = checkIntersectionOfTwoLineSegments(Click1, Click2, Start, End) ;
            //System.out.println("Значение пересечения на шаге  " + i + " равно " + peresek);
            if (peresek)
            {
                peresekNumber++;
            }
        }
        
        Click1.x = x;
        Click1.y = y-1;
        
        Click2.x = x;
        Click2.y = y+1;
         for (int i = 0; i<xy.size()-1; i++)
        {
            Start.x = xy.get(i).x;
            Start.y = xy.get(i).y;
            
            End.x = xy.get(i+1).x;
            End.y = xy.get(i+1).y;
            
            //Высчитываем по функции пересекается ли отрезок с лучем.
            boolean peresek = checkIntersectionOfTwoLineSegments(Click1, Click2, Start, End) ;
            //System.out.println("Значение пересечения на шаге  " + i + " равно " + peresek);
            if (peresek)
            {
                peresekNumber++;
            }
        }
        
        //Если пересечения есть, то фигура входит в диапозон
        if (peresekNumber > 0)
        {
            view.Canvas.Stoper = true;
          //  System.out.println("Найдена фигура типа " + type + " номер #" + number);
            //Блок отбора по номеру number
           // view.Canvas.ConstructShape = que.get(number-1); 
           // ShowName.setVisible(true);
           //ShowName.setText(type + " " + number);
            System.out.println("Найдена фигура типа " + type + " номер #" + view.MainWindow.number);//static
            //Блок отбора по номеру number
            view.Canvas.ConstructShape = que.get(view.MainWindow.number); //static
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
