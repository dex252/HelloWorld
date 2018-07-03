/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author dex25
 */
public class FillShape 
{
    int type;
    Color color;
    ArrayList <Dots> dots;
    String name;
    public FillShape(int TypeFill, Color ColorFill, ArrayList <Dots> dots, String name)
    {
        this.type = TypeFill;//тип заливки
        this.color = ColorFill;//цвет заливки 
        this.dots = dots;
        this.name = name;
    }
    
    void FillShape (Graphics g)//dots - набр точек в экранных координатах
    {
       int up, dn, left, right;//верхняя и нижняя границы фигуры + левая и правая границы для отрисовки
       up = (int) dots.get(0).y;
       dn = (int) dots.get(0).y;
       if (type != 0)//заливка выполняется только в том случае, если тип заливки не установлен на 0, 0 - прозрачный
       {
           //вычисляем нижнюю и верхнюю границы фигуры, рассматривать будем сверху-вниз по оси ОY
           for (int i = 0; i< dots.size(); i++)
           {
               if (up > dots.get(i).y) up = (int) dots.get(i).y;
               if (dn < dots.get(i).y) dn = (int) dots.get(i).y;
           }
           
           left = 0;
           right = 1200;
           //if (up < 0) up = 0;
          // if (dn > 600) dn = 600;
           
           Dots Start = new Dots();
           Dots End = new Dots();
           Dots fStart = new Dots();
           Dots fEnd = new Dots();
           Dots Point = new Dots();
           ArrayList <Dots> zalivka = new ArrayList();
          
           Start.x = left;
           End.x = right;
           System.out.println("left= "+left+ "  right = "+right);
           System.out.println("up= "+up+ "  dn = "+dn);
           if (type==1)//сплошная
           {
               for (int i = up; i < dn; i++)
               {
                   Start.y = i;
                   End.y = i;
                   for (int j = 0; j < dots.size()-1; j++)
                   {
                       fStart.x = dots.get(j).x;
                       fStart.y = dots.get(j).y;
                       fEnd.x = dots.get(j+1).x;
                       fEnd.y = dots.get(j+1).y;
                       Point = Presek(Start, End, fStart, fEnd);
                       if (Point != null) zalivka.add(Point); 
                   }
                   if (zalivka.size() == 2)
                   {
                       g.setColor(color);
                       g.drawLine((int) zalivka.get(0).x, (int) zalivka.get(0).y,(int)  zalivka.get(1).x, (int) zalivka.get(1).y);
                   }
                   if (zalivka.size()>2)
                   {
                       g.setColor(color);
                       for (int j = 0; j < zalivka.size()/2; j++)
                       {
                            g.drawLine((int) zalivka.get(i*2).x, (int) zalivka.get(i*2).y,(int)  zalivka.get(i*2+1).x, (int) zalivka.get(i*2+1).y);
                       }
                       if ((name=="Triangle")||(name=="Rectangle"))
                       {
                           g.drawLine((int) zalivka.get(0).x, (int) zalivka.get(0).y,(int)  zalivka.get(zalivka.size()).x, (int) zalivka.get(zalivka.size()).y);
                       }
                   }
                   zalivka.clear();
               }
           }
       }
    }
   
     //Если точно известно, что точки пересечения есть!
    Dots Presek(Dots a1, Dots a2, Dots a3, Dots a4)  //
    {  
        if (a4.x < a3.x) 
        {

            Dots tmp = a3;
            a3 = a4;
            a4 = tmp;
        }
        Dots Point = new Dots();

            double v1, v2, v3, v4; 
            double xv12, xv13, xv14, xv31, xv32, xv34, yv12, yv13, yv14, yv31, yv32, yv34;
            //нахождение координат векторов
            xv12 = a2.x - a1.x;		xv13 = a3.x - a1.x;		xv14 = a4.x - a1.x;	
            yv12 = a2.y - a1.y;		yv13 = a3.y - a1.y;		yv14 = a4.y - a1.y;	

            xv31 = a1.x - a3.x;		xv32 = a2.x - a3.x;		xv34 = a4.x - a3.x; 
            yv31 = a1.y - a3.y;		yv32 = a2.y - a3.y;		yv34 = a4.y - a3.y;   

            // векторные произведения

            v1 = xv34 * yv31 - yv34 * xv31; 
            v2 = xv34 * yv32 - yv34 * xv32; 
            v3 = xv12 * yv13 - yv12 * xv13; 
            v4 = xv12 * yv14 - yv12 * xv14; 

            if((v1 * v2) < 0 && (v3 * v4) < 0)
            {
                double A1, B1, C1, A2, B2, C2;
                A1 = a2.y - a1.y;									
                A2 = a4.y - a3.y;
                B1 = a1.x - a2.x;									
                B2 = a3.x - a4.x;
                C1 = (a1.x * (a1.y - a2.y) + a1.y * (a2.x - a1.x)) * (-1);
                C2 = (a3.x * (a3.y - a4.y) + a3.y * (a4.x - a3.x)) * (-1);
                double D = (double) ((A1 * B2) - (B1 * A2)); 
                double Dx = (double) ((C1 * B2) - (B1 * C2)); 
                double Dy = (double) ((A1 * C2) - (C1 * A2));

                if(D != 0)
                {
                Point.x = (int) (Dx / D); 
                Point.y = (int) (Dy / D); 
                }
                else 
                {
                    Point = null;
                }
            }
            else Point = null;
        return Point;
    }
}
