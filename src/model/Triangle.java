
package model;

import java.awt.*;
import java.io.Serializable;
import static view.MainWindow.*;

public class Triangle extends Shape implements Serializable
{
    private static final long serialVersionUID = 1L;   
    static
    {
        FactoryShape.register(Triangle.class);//Передача класса
    }
    
    public Triangle ()
    {
       this.type = "Triangle";
    }

    @Override
    public void paint(Graphics g, double scale, double x_shift, double y_shift)
    {
        int x1,y1,x2,y2;
        if (!xy.isEmpty())
        {
            g.setColor(Color.blue);
            for (int i = 0; i<2;i++)
            {
                x1 = (int) (xy.get(i).x/Math.exp(scale) - x_shift);
                x2 = (int) (xy.get(i+1).x/Math.exp(scale) - x_shift);
                y1 = (int) (xy.get(i).y/Math.exp(scale) - y_shift);
                y2 = (int) (xy.get(i+1).y/Math.exp(scale) - y_shift);
                g.drawLine(x1, y1, x2, y2); 
            }
            
            x1 = (int) (xy.get(0).x/Math.exp(scale) - x_shift);
            x2 = (int) (xy.get(2).x/Math.exp(scale) - x_shift);
            y1 = (int) (xy.get(0).y/Math.exp(scale) - y_shift);
            y2 = (int) (xy.get(2).y/Math.exp(scale) - y_shift);
            g.drawLine(x1, y1, x2, y2); 
        }
       if ((view.MainWindow.regim == 4)&&(view.Canvas.DotsWeb)) paintCheck(g);
       if ((view.Canvas.Choicer)&&(view.MainWindow.regim != 4)) ChoiceWeb(g);
    }  
    
    @Override
    public void FirstClick (double x1, double y1)
    {
        if (view.MainWindow.regim==0)
        {
            this.x1 = (x1+x_shift)*Math.exp(scale);
            this.y1= (y1+y_shift)*Math.exp(scale);
            Dots dots = new Dots();

            dots.x = this.x1;
            dots.y = this.y1;
            xy.add(dots);
            xy.add(dots);
            xy.add(dots);
        }
        else 
        {
            this.x1 = (x1+x_shift)*Math.exp(scale);
            this.y1= (y1+y_shift)*Math.exp(scale);
        }
    }
    
    @Override
    public void LastClick (double x1, double y1)
    {
        if (view.MainWindow.regim==0)
        {
            this.x2 = (x1+x_shift)*Math.exp(scale);
            this.y2= (y1+y_shift)*Math.exp(scale); 
            //Правая нижняя точка (2)
            Dots dots = new Dots();
            dots.x =  this.x2;
            dots.y = this.y2;  
            xy.set(2, dots);    
            //Левая нижняя точка (0)
            dots = new Dots();
            dots.x = this.x1;
            dots.y = this.y2;
            xy.set(0, dots);
            //Верхняя вершина треугольника (1)
            dots = new Dots();
            dots.x = this.x1 + (this.x2 - this.x1)/2;
            dots.y = this.y1 ;
            xy.set(1,dots);
        }
        else 
        {
            this.x2 = (x1+x_shift)*Math.exp(scale);
            this.y2= (y1+y_shift)*Math.exp(scale); 
        }
    } 

    
    @Override
    public void Cross(Graphics g) 
    { 
        double x, y; //смещение по осям       
        Dots dots = new Dots();
        
        if (xy2.size()<3) 
        {
           for (int i = 0; i<3; i++) xy2.add(dots);
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
        int x1=0,x2=0,y1=0,y2=0;
        for (int i = 0; i<2;i++)
        {
                
            x1 = (int) (xy2.get(i).x/Math.exp(scale) - x_shift);
            x2 = (int) (xy2.get(i+1).x/Math.exp(scale) - x_shift);
            y1 = (int) (xy2.get(i).y/Math.exp(scale) - y_shift);
            y2 = (int) (xy2.get(i+1).y/Math.exp(scale) - y_shift);
            g.drawLine(x1, y1, x2, y2); 
        }
        
        x1 = (int) (xy2.get(0).x/Math.exp(scale) - x_shift);
        x2 = (int) (xy2.get(2).x/Math.exp(scale) - x_shift) ;
        y1 = (int) (xy2.get(0).y/Math.exp(scale) - y_shift);
        y2 = (int) (xy2.get(2).y/Math.exp(scale) - y_shift);
        g.drawLine(x1, y1, x2, y2); 
        if ((view.Canvas.Choicer)&&(view.MainWindow.regim != 4)) ChoiceWeb(g);
    }    
    
    @Override
    public void Turned(Graphics g) 
    { 
        double alpha, beta;
        xc = (xy.get(0).x + xy.get(2).x)/2;//новая середина
        yc = (xy.get(0).y + xy.get(1).y)/2;//новая середина
        alpha = Math.atan2(x1-xc, y1-yc);
        beta = Math.atan2(x2-xc, y2-yc);
        phi = alpha-beta;
        System.out.println("Угол phi = " + Math.toDegrees(phi));
        
        Dots dots = new Dots();
        
        if (xy2.size()<3) 
        {
            for (int i = 0; i<3; i++) xy2.add(dots);
        }
        
        for (int i = 0; i< xy2.size(); i++)
        {
            dots = new Dots();
            dots.x = (xy.get(i).x - xc) * Math.cos(phi) - (xy.get(i).y - yc) * Math.sin(phi) + xc;
            dots.y = (xy.get(i).x - xc) * Math.sin(phi) + (xy.get(i).y - yc) * Math.cos(phi) + yc;
            xy2.set(i, dots);
        }
        
        int x1, y1, x2, y2;
        g.setColor(Color.pink);
        for (int i = 0; i<2;i++)
        {
            x1 = (int) (xy2.get(i).x/Math.exp(scale) - x_shift);
            x2 = (int) (xy2.get(i+1).x/Math.exp(scale) - x_shift);
            y1 = (int) (xy2.get(i).y/Math.exp(scale) - y_shift);
            y2 = (int) (xy2.get(i+1).y/Math.exp(scale) - y_shift);
            g.drawLine(x1, y1, x2, y2); 
        }
        
        x1 = (int) (xy2.get(0).x/Math.exp(scale) - x_shift);
        x2 = (int) (xy2.get(2).x/Math.exp(scale) - x_shift);
        y1 = (int) (xy2.get(0).y/Math.exp(scale) - y_shift);
        y2 = (int) (xy2.get(2).y/Math.exp(scale) - y_shift);
        g.drawLine(x1, y1, x2, y2); 
        if ((view.Canvas.Choicer)&&(view.MainWindow.regim != 4)) ChoiceWeb(g);
    }
    
    @Override
    public void Relise() 
    {    
        if (xy2.size()==3)
        {
            for (int i = 0; i<xy.size(); i++)
            {
                xy.set(i, xy2.get(i));
            }
            Check();
        }
    }
    
    @Override
    public Dots Transp(double x, double y)//тут координат фигуры (мировые)
    {
        Dots dots = new Dots();
        TranspInside(x , y);//известна точка старта, в этом методе узнаем является ли точка частью локации
        dots.x = x;
        dots.y = y;
        
        if (location == 1) 
        {   
                if ((location1==1)||(location1==2)||(location1==3))
                {
                    dots.x = x - transp_x;
                    dots.y = y - transp_y;
                }
                if ((location1==7)||(location1==4))
                {
                    dots.x = x - transp_x;
                }
            //Проверка на границы
            if ((dots.x)>=obs.get(18).x)
            {
                dots.x = obs.get(18).x-6;
            }
            if ((dots.y)>=obs.get(18).y)
            {
                dots.y = obs.get(18).y-6;
            }
        }
        
        if (location == 2) 
        {
            if ((location1==1)||(location1==2)||(location1==3))
            {
                dots.x = x;
                dots.y = y - transp_y;
            }
             //Проверка на границы
            if ((dots.y)>=obs.get(18).y)
            {
                dots.y = obs.get(18).y-6;
            }
        }
        
        if (location == 3) 
        {
            if ((location1==3)||(location1==1)||(location1==2))
            {
                dots.x = x - transp_x;
                dots.y = y - transp_y;
            }
              if ((location1==6)||(location1==9))
                {
                    dots.x = x - transp_x;
                }
            //Проверка на границы
            if ((dots.x)<=obs.get(0).x)
            {
                dots.x = obs.get(0).x+6;
            }
            if ((dots.y)>=obs.get(18).y)
            {
                dots.y = obs.get(18).y-6;
            }
        }
        
        if (location == 4) 
        {
            if ((location1==1)||(location1==4)||(location1==7))
            {
                dots.x = x - transp_x;
                dots.y = y;
            }
            //Проверка на границы
            if ((dots.x)>=obs.get(9).x)
            {
                dots.x = obs.get(9).x-6;
            }
        }
        
        if (location == 5) 
        { 
            dots.x = x - transp_x;
            dots.y = y - transp_y;
        }
        
        if (location == 6) 
        {
            if ((location1==3)||(location1==6)||(location1==9))
            {
                dots.x = x - transp_x;
                dots.y = y;
            }
            //Проверка на границы
            if ((dots.x)<=obs.get(27).x)
            {
                dots.x = obs.get(27).x+6;
            }
        }
        
        if (location == 7) 
        {
            if ((location1==7)||(location1==8)||(location1==9))
            {
                dots.x = x - transp_x;
                dots.y = y - transp_y;
            }
            if ((location1==1)||(location1==4))
            {
                dots.x = x - transp_x;
            }
            //Проверка на границы
            if ((dots.x)>=obs.get(9).x)
            {
                dots.x = obs.get(9).x-1;
            }
            if ((dots.y)<=obs.get(9).y)
            {
                dots.y = obs.get(9).y+6;
            }
        }
        
        if (location == 8) 
        {
            if ((location1==7)||(location1==8)||(location1==9))
            {
                dots.x = x;
                dots.y = y - transp_y;
            }
            //Проверка на границы
            if ((dots.y)<=obs.get(0).y)
            {
                dots.y = obs.get(0).y+6;
            }
        }
        
        if (location == 9) 
        {
            if ((location1==9)||(location1==7)||(location1==8))
            {
                dots.x = x - transp_x;
                dots.y = y - transp_y;
            }
            if ((location1==3)||(location1==6))
            {
                dots.x = x - transp_x;
            }
            //Проверка на границы
            if ((dots.x)<=obs.get(0).x)
            {
                dots.x = obs.get(0).x+6;
            }
            if ((dots.y)<=obs.get(0).y)
            {
                dots.y = obs.get(0).y+6;
            }
        }
       return dots;
    }  
}
