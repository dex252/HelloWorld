
package model;
import static view.MainWindow.*;

import java.awt.*;
import java.io.Serializable;

public class Rectangle extends Shape implements Serializable
{   
    private static final long serialVersionUID = 1L;
    static
    {
        FactoryShape.register(Rectangle.class);//Передача класса
    }
    
    public Rectangle ()
    {
        this.type = "Rectangle";
    }

    @Override
    public void FirstClick (double x1, double y1)
    {
        if (view.MainWindow.regim==0)
        {
            this.x1 = (x1+x_shift)*Math.exp(scale);
            this.y1 = (y1+y_shift)*Math.exp(scale);
            
            Dots dots = new Dots();
            dots.x = this.x1;
            dots.y = this.y1;

            xy.add(dots);
            xy.add(dots);
            xy.add(dots);
            xy.add(dots);
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
            this.x2 = (x1+x_shift)*Math.exp(scale);
            this.y2 = (y1+y_shift)*Math.exp(scale);
            //Левая верхняя точка (1) уже задана из FirstClick
            //Правая нижняя точка (3)
            Dots dots = new Dots();
            dots.x =  this.x2;
            dots.y = this.y2;
            xy.set(3, dots);    
            //Левая нижняя точка (0)
            dots = new Dots();
            dots.x = xy.get(1).x;
            dots.y = this.y2;
            xy.set(0, dots);
            //Верхняя правая точка (2)
            dots = new Dots();
            dots.x = this.x2;
            dots.y = xy.get(1).y;
            xy.set(2,dots);
        }
        else 
        {
            this.x2 = (x1+x_shift)*Math.exp(scale);
            this.y2 = (y1+y_shift)*Math.exp(scale);
        }
    } 
    
    @Override
    public void Cross(Graphics g) 
    { 
        double x, y;       
        Dots dots = new Dots();
        if (xy2.size()<4) 
        {
           for (int i = 0; i<4; i++) xy2.add(dots);
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
        int x1, x2 ,y1, y2;
        for (int i = 0; i<3;i++)
        {
            x1 = (int) (xy2.get(i).x/Math.exp(scale) - x_shift);
            x2 = (int) (xy2.get(i+1).x/Math.exp(scale) - x_shift) ;
            y1 = (int) (xy2.get(i).y/Math.exp(scale) - y_shift) ;
            y2 = (int) (xy2.get(i+1).y/Math.exp(scale) - y_shift);
            g.drawLine(x1, y1, x2, y2); 
       }
            x1 = (int) (xy2.get(0).x/Math.exp(scale) - x_shift) ;
            x2 = (int) (xy2.get(3).x/Math.exp(scale) - x_shift);
            y1 = (int) (xy2.get(0).y/Math.exp(scale) - y_shift) ;
            y2 = (int) (xy2.get(3).y/Math.exp(scale) - y_shift) ;
            g.drawLine(x1, y1, x2, y2);    
            if ((view.Canvas.Choicer)&&(view.MainWindow.regim != 4)) ChoiceWeb(g);
    }    
    
    @Override
    public void Turned(Graphics g) 
    { 
        double alpha, beta;
        xc = (xy.get(1).x + xy.get(3).x)/2;//новая середина
        yc = (xy.get(1).y + xy.get(3).y)/2;//новая середина
        alpha = Math.atan2(x1-380, y1-210);
        beta = Math.atan2(x2-380, y2-210);
        phi = alpha-beta;
        System.out.println("Угол phi = " + Math.toDegrees(phi));
        
        Dots dots = new Dots();
        
        if (xy2.size()<4) 
        {
            for (int i = 0; i<4; i++) xy2.add(dots);
        }
        
        for (int i = 0; i< xy2.size(); i++)
        {
            dots = new Dots();
            dots.x = (xy.get(i).x - xc) * Math.cos(phi) - (xy.get(i).y - yc) * Math.sin(phi) + xc;
            dots.y = (xy.get(i).x - xc) * Math.sin(phi) + (xy.get(i).y - yc) * Math.cos(phi) + yc;
            xy2.set(i, dots);
        }
        
        g.setColor(Color.pink);
        int x1, x2 ,y1, y2;
        for (int i = 0; i<3;i++)
        {
            x1 = (int) (xy2.get(i).x/Math.exp(scale) - x_shift) ;
            x2 = (int) (xy2.get(i+1).x/Math.exp(scale) - x_shift) ;
            y1 = (int) (xy2.get(i).y/Math.exp(scale) - y_shift) ;
            y2 = (int) (xy2.get(i+1).y/Math.exp(scale) - y_shift) ;
            g.drawLine(x1, y1, x2, y2); 
        }
        
        x1 = (int) (xy2.get(0).x/Math.exp(scale) - x_shift) ;
        x2 = (int) (xy2.get(3).x/Math.exp(scale) - x_shift) ;
        y1 = (int) (xy2.get(0).y/Math.exp(scale) - y_shift) ;
        y2 = (int) (xy2.get(3).y/Math.exp(scale) - y_shift) ;
        g.drawLine(x1, y1, x2, y2);    
        if ((view.Canvas.Choicer)&&(view.MainWindow.regim != 4)) ChoiceWeb(g);
    }
    
    @Override
    public void Relise() 
    {    
        if (xy2.size()==4)
        {
           for (int i = 0; i<xy.size(); i++)
           {
               xy.set(i, xy2.get(i));
           }
           Check();
        }
    }
     
    @Override
    public void paint(Graphics g, double scale, double x_shift, double y_shift)
    {   
        if (!xy.isEmpty())
        {
            g.setColor(Color.green);
            int x1,y1,x2,y2;
            for (int i = 0; i<3;i++)
            {
                x1 = (int) (xy.get(i).x/Math.exp(scale) - x_shift) ;
                x2 = (int) (xy.get(i+1).x/Math.exp(scale) - x_shift) ;
                y1 = (int) (xy.get(i).y/Math.exp(scale) - y_shift) ;
                y2 = (int) (xy.get(i+1).y/Math.exp(scale) - y_shift) ;
                g.drawLine(x1, y1, x2, y2); 
            }
  
            x1 = (int) (xy.get(0).x/Math.exp(scale) - x_shift) ;
            x2 = (int) (xy.get(3).x/Math.exp(scale) - x_shift) ;
            y1 = (int) (xy.get(0).y/Math.exp(scale) - y_shift) ;
            y2 = (int) (xy.get(3).y/Math.exp(scale) - y_shift) ;
            g.drawLine(x1, y1, x2, y2);  
        }
        if ((view.MainWindow.regim == 4)&&(view.Canvas.DotsWeb)) paintCheck(g);
        if ((view.Canvas.Choicer)&&(view.MainWindow.regim != 4)) ChoiceWeb(g);
    }    
}
