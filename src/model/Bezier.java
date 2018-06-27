/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import static view.MainWindow.*;

public class Bezier extends BreakLine implements Serializable
{
    private static final long serialVersionUID = 1L;
    Dots dots2 = new Dots();
    double step = 0.005;  //шаг
    int n = (int) Math.round(1/step);//шаг
    double [] x = new double [n];
    double [] y = new double [n];
    ArrayList <Dots> finalXY = new ArrayList();
    ArrayList <Dots> finalXY2 = new ArrayList();
    
    static
    {
        FactoryShape.register(Bezier.class);//Передача класса
    }
    
    public Bezier ()
    {
    this.type = "Bezier";
    this.hard = true;
    }
    
    
    @Override
    public void paint(Graphics g, double scale, double x_shift, double y_shift) 
    {
        double x1, x2, y1, y2;
        if (xy.size()>2) 
        {
            finalXY = new ArrayList();
            for (double t = 0; t<1; t+=step)
            {
                //Заполняем дополнительный массив исходным набором точек
                for (int k = 0; k<xy.size(); k++)
                {
                    x[k] = xy.get(k).x;
                    y[k] = xy.get(k).y; 
                }
                //Сокращаем каждую итерацию исходный набор точек до одной
                for (int k = xy.size()-1; k>0; k--)
                {            
                    for (int i = 0; i<k; i++)
                    {
                        x[i] = x[i]*(1-t) + x[i+1]*t;
                        y[i] = y[i]*(1-t) + y[i+1]*t;
                    }
                }
                dots2 = new Dots(); 
                dots2.x = x[0];
                dots2.y = y[0];
                finalXY.add(dots2);
            }  
            //Отрисовка линий из опорных точек - закомментировано (не убирать)
         /* for (int t = 0; t<xy.size()-1; t++)
            {
                x1 = (xy.get(t).x/Math.exp(scale) - x_shift);
                y1 = (xy.get(t).y/Math.exp(scale) - y_shift);
                x2 = (xy.get(t+1).x/Math.exp(scale) - x_shift);
                y2 = (xy.get(t+1).y/Math.exp(scale) - y_shift);
                g.setColor(new Color(255, 250, 200));
                g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
           }*/
            //Отрисовка Безье
            for (int t = 0; t<finalXY.size()-1; t++)
            {
                x1 = (int) (finalXY.get(t).x/Math.exp(scale) - x_shift);
                x2 = (int) (finalXY.get(t+1).x/Math.exp(scale) - x_shift);
                y1 = (int) (finalXY.get(t).y/Math.exp(scale) - y_shift);
                y2 = (int) (finalXY.get(t+1).y/Math.exp(scale) - y_shift);
                g.setColor(Color.black);
                g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);    
            }
        }
        else
        {
            if (xy.size()==2)//рисуем если заданы две точки, возможно их и нет, если объект только-только инициализирован
            {
                g.setColor(Color.yellow);
                x1 = (int) (xy.get(0).x/Math.exp(scale) - x_shift);
                x2 = (int) (xy.get(1).x/Math.exp(scale) - x_shift);
                y1 = (int) (xy.get(0).y/Math.exp(scale) - y_shift);
                y2 = (int) (xy.get(1).y/Math.exp(scale) - y_shift);
                g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);    
           }
        }  
        if ((view.MainWindow.regim == 4)&&(view.Canvas.DotsWeb)) paintCheck(g);
        if ((view.Canvas.Choicer)&&(view.MainWindow.regim != 4)) ChoiceWeb(g);
    }
    
    //Отрисовка линии вовремя параллельного переноса
    @Override
    public void Cross(Graphics g) 
    { 
        double x, y; 
        dots = new Dots();
        
        if (xy2.isEmpty()) 
        {
           for (int i = 0; i<xy.size(); i++) xy2.add(dots);
        }
        
        if (finalXY2.isEmpty()) 
        {
           finalXY2 = new ArrayList();
           for (int i = 0; i<finalXY.size(); i++) finalXY2.add(dots);
        }
        
        x = x2 - x1;
        y = y2 - y1;
        
        for (int i = 0; i< xy2.size(); i++)
        {
           dots = new Dots();
           dots.x = xy.get(i).x+ x;
           dots.y = xy.get(i).y+ y;
           xy2.set(i, dots);
        }
        
        for (int t = 0; t<finalXY2.size()-1; t++)
        {
           dots = new Dots();
           dots.x = finalXY.get(t).x + x;
           dots.y = finalXY.get(t).y+ y;
           finalXY2.set(t, dots);    
        }

        int x1,y1,x2,y2;
        for (int t = 0; t<finalXY2.size()-2; t++)
        {
            x1 = (int) (finalXY2.get(t).x/Math.exp(scale) - x_shift);
            x2 = (int) (finalXY2.get(t+1).x/Math.exp(scale) - x_shift);
            y1 = (int) (finalXY2.get(t).y/Math.exp(scale) - y_shift);
            y2 = (int) (finalXY2.get(t+1).y/Math.exp(scale) - y_shift);
            g.setColor(Color.black);
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
        //новая середина
        xc = (x_max + x_min)/2;
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
          
        if (finalXY2.isEmpty()) 
        {
           for (int i = 0; i<finalXY.size(); i++) finalXY2.add(dots);
        }
          
        for (int i = 0; i< xy2.size(); i++)
        {
            dots = new Dots();
            dots.x = (xy.get(i).x - xc) * Math.cos(phi) - (xy.get(i).y - yc) * Math.sin(phi) + xc;
            dots.y = (xy.get(i).x - xc) * Math.sin(phi) + (xy.get(i).y - yc) * Math.cos(phi) + yc;
            xy2.set(i, dots);
        }
        
        for (int i = 0; i< finalXY2.size(); i++)
        {
            dots = new Dots();
            dots.x = (finalXY.get(i).x - xc) * Math.cos(phi) - (finalXY.get(i).y - yc) * Math.sin(phi) + xc;
            dots.y = (finalXY.get(i).x - xc) * Math.sin(phi) + (finalXY.get(i).y - yc) * Math.cos(phi) + yc;
            finalXY2.set(i, dots);
        }
        
        g.setColor(Color.pink);          
        for (int t = 0; t<finalXY2.size()-1; t++)
        {
            int x1, x2, y1, y2;
            x1 = (int) (finalXY2.get(t).x/Math.exp(scale) - x_shift);
            x2 = (int) (finalXY2.get(t+1).x/Math.exp(scale) - x_shift) ;
            y1 = (int) (finalXY2.get(t).y/Math.exp(scale) - y_shift) ;
            y2 = (int) (finalXY2.get(t+1).y/Math.exp(scale) - y_shift);
            g.drawLine((int)x1, (int)y1, (int)x2, (int)y2); 
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
        }
        
        if (!finalXY2.isEmpty())
        {
           for (int i = 0; i<finalXY.size(); i++)
           {
               finalXY.set(i, finalXY2.get(i));
           }
           Check();
        } 
    }
     
     //Необходимо изменить
    @Override
      public void Deformation(Graphics g)//деформация фигуры
      {
        Dots dots;
        if (xy2.isEmpty()) 
        {
            for (int i = 0; i<xy.size(); i++)
            {
                dots = new Dots();
                dots.x = xy.get(i).x;
                dots.y = xy.get(i).y;
                xy2.add(dots);
            }
        }
       else
        {
            for (int i = 0; i<xy.size(); i++)
            {
                dots = new Dots();
                dots.x = xy.get(i).x;
                dots.y = xy.get(i).y;
                xy2.set(i, dots);
            }
        }
        
        if (!xy2.isEmpty()) 
        {
            for (int i = 0; i<xy2.size(); i++)
            {
                 dots = new Dots();
                 dots.x = xy2.get(i).x;
                 dots.y = xy2.get(i).y;
                 dots = Transp(dots.x, dots.y);
                 xy2.set(i, dots);
            }
        }
        
        g.setColor(new Color(255, 250, 200));
        int x1, x2, y1, y2;
        for (int i = 0; i<xy2.size()-1; i++)
        {
           x1 = (int) (xy2.get(i).x/Math.exp(scale)-x_shift);
           x2 = (int) (xy2.get(i+1).x/Math.exp(scale)-x_shift);
           y1 = (int) (xy2.get(i).y/Math.exp(scale)-y_shift);
           y2 = (int) (xy2.get(i+1).y/Math.exp(scale)-y_shift); 
           g.drawLine(x1, y1, x2, y2);
        }      
        
        dots = new Dots();
        finalXY2 = new ArrayList();
         //кривая по xy2
        for (double t = 0; t<1; t+=step)
        {
        //Заполняем дополнительный массив исходным набором точек
            for (int k = 0; k<xy2.size(); k++)
            {
                x[k] = xy2.get(k).x;
                y[k] = xy2.get(k).y; 
            }
            //Сокращаем каждую итерацию исходный набор точек до одной
            for (int k = xy2.size()-1; k>0; k--)
            {            
                for (int i = 0; i<k; i++)
                {
                    x[i] = x[i]*(1-t) + x[i+1]*t;
                    y[i] = y[i]*(1-t) + y[i+1]*t;
                }
            }
            dots2 = new Dots(); 
            dots2.x = x[0];
            dots2.y = y[0];
            finalXY2.add(dots2);
        } 
        
        for (int t = 0; t<finalXY2.size()-2; t++)
        {
            x1 = (int)  (finalXY2.get(t).x/Math.exp(scale) - x_shift);
            x2 = (int) (finalXY2.get(t+1).x/Math.exp(scale) - x_shift);
            y1 = (int) (finalXY2.get(t).y/Math.exp(scale) - y_shift);
            y2 = (int) (finalXY2.get(t+1).y/Math.exp(scale) - y_shift);
            g.setColor(Color.pink);
            g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);  
        }
      }
      
     //Для Безье отдельно, т.к. рассматриваем не опорные точки, а саму линию в finalXY листе
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
        for (int i = 0; i<finalXY.size()-1; i++)
        {
            Start.x = finalXY.get(i).x;
            Start.y = finalXY.get(i).y;
            
            End.x = finalXY.get(i+1).x;
            End.y = finalXY.get(i+1).y;
            
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
            Start.x = finalXY.get(i).x;
            Start.y = finalXY.get(i).y;
            
            End.x = finalXY.get(i+1).x;
            End.y = finalXY.get(i+1).y;
            
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
     //       System.out.println("Найдена фигура типа " + type + " номер #" + number);
            //Блок отбора по номеру number
        //    view.Canvas.ConstructShape = que.get(number-1); 
         //   ShowName.setVisible(true);
       //     ShowName.setText(type + " " + number);
             System.out.println("Найдена фигура типа " + type + " номер #" + view.MainWindow.number);//static
            //Блок отбора по номеру number
             view.Canvas.ConstructShape = view.Canvas.que.get(view.MainWindow.number); //static
 //           ShowName.setVisible(true);
 //           ShowName.setText(view.Canvas.ConstructShape.name);
        }
        System.out.println("Число найденных пересечений " + peresekNumber);
         
        if (!view.Canvas.Stoper)
        {
//            ShowName.setVisible(false);
        }
    }
}
