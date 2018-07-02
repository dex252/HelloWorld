
package model;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import view.MainWindow;
import static view.MainWindow.*;

public class Ellipse extends Shape implements Serializable
{
    private static final long serialVersionUID = 1L;
    int x0 ,y0;//истинный центр
    int x0c, y0c;//дополнительный центр
    int a, b; //мировые полуоси
    int ac, bc;
    double phi2;
    
    static
    {
        FactoryShape.register(Ellipse.class);//Передача класса
    }
    
    public Ellipse ()
    {
        this.type = "Ellipse";
    }
    
    @Override
    public void LastClick (double x1, double y1)
    {
        if (view.MainWindow.regim==0)
        {
            this.x2 = (x1+x_shift)*Math.exp(scale);
            this.y2 = (y1+y_shift)*Math.exp(scale);
            xy.clear();
            coordinate ((int)this.x1, (int)this.y1, (int)this.x2, (int)this.y2);
        }
        else 
        {
            this.x2 = (x1+x_shift)*Math.exp(scale);
            this.y2 = (y1+y_shift)*Math.exp(scale);
        }
    } 
    
    private void coordinate (int x1, int y1, int x2, int y2)
    {
        if (x1<x2)
        {
          x0 = x1+(x2-x1)/2;
          a = (x2 - x1)/2;
        }
        else
        {
         if ((x1==0)&&(x2==0)) x1 = 1;
          x0 = x2+(x1-x2)/2;
          a = (x1 - x2)/2;
        }
        if (y1<y2)
        {
          y0 = y1+(y2-y1)/2;
          b = (y2 - y1)/2;
        }
        else
        {
          if ((y1==0)&&(y2==0)) y1 = 1;
          y0 = y2+(y1-y2)/2;
          b = (y1 - y2)/2;
        }
    }  
    
    @Override
    public void paint(Graphics g, double scale, double x_shift, double y_shift)
    {
    if (Visible)
        {
        Graphics2D g2 = (Graphics2D) g; 
        g2.setStroke(new BasicStroke(gageBorder));
        ac = (int) (a/Math.exp(scale));
        bc = (int) (b/Math.exp(scale));
        x0c = (int) (x0/Math.exp(scale)-x_shift);
        y0c = (int) (y0/Math.exp(scale)-y_shift);
        
     //   if (ac>1000) ac = (int) (ac * Math.exp(scale));
      //  if (bc>1000) bc = (int) (bc * Math.exp(scale));
        
      //  if  ((ac>1)||(bc>1) && ( (ac<1000) || (bc <1000) ))
      //если длина становится больше, то тогда делаем шаг больше по x и y
      //а до этого попробую вариант на проверку точек при вычислении, если они будут пободать в координаты экрана (1495,600) - тогда заносим их в массив, если нет, то нет)
      if (ac>1200 | bc >1200)
       {                                              
            if (ac>1200)
            {
               scale=1200/ac;
            }
            else
            {
              scale=1200/bc;
            }
            ac=(int) (ac*scale);
            bc=(int) (bc*scale);
        }   
        {
            Dots dots;
            int x = 0;
            int y = bc;
            int a_sqr = ac * ac; // a^2, a - длина
            int b_sqr = bc * bc; // b^2, b - высота
            int delta = 4 * b_sqr * ((x + 1) * (x + 1)) + a_sqr * ((2 * y - 1) * (2 * y - 1)) - 4 * a_sqr * b_sqr; // Функция координат точки (x+1, y-1/2)

            xy = new ArrayList();
            while (a_sqr * (2 * y - 1) > 2 * b_sqr * (x + 1)) // Первая часть дуги
            {
                dots = new Dots();
                dots.x = x0c + x;
                dots.y = y0c + y;
                xy.add(dots);

                dots = new Dots();
                dots.x = x0c + x;
                dots.y = y0c - y;
                xy.add(dots);

                dots = new Dots();
                dots.x = x0c - x;
                dots.y = y0c - y;
                xy.add(dots);

                dots = new Dots();
                dots.x = x0c - x;
                dots.y = y0c + y;
                xy.add(dots);
                // Переход по горизонтали
                if (delta < 0) 
                {
                    x++;
                    delta += 4 * b_sqr * (2 * x + 3);
                }
                // Переход по диагонали
                else 
                {
                    x++;
                    delta = delta - 8 * a_sqr * (y - 1) + 4 * b_sqr * (2 * x + 3);
                    y--;
                }
            }

            delta = b_sqr * ((2 * x + 1) * (2 * x + 1)) + 4 * a_sqr * ((y + 1) * (y + 1)) - 4 * a_sqr * b_sqr; // Функция координат точки (x+1/2, y-1)
            while (y + 1 != 0) // Вторая часть дуги, если не выполняется условие первого цикла, значит выполняется a^2(2y - 1) <= 2b^2(x + 1)
            {
                dots = new Dots();
                dots.x = x0c + x;
                dots.y = y0c + y;
                xy.add(dots);

                dots = new Dots();
                dots.x = x0c + x;
                dots.y = y0c - y;
                xy.add(dots);

                dots = new Dots();
                dots.x = x0c - x;
                dots.y = y0c - y;
                xy.add(dots);

                dots = new Dots();
                dots.x = x0c - x;
                dots.y = y0c + y;
                xy.add(dots);
                // Переход по вертикали
                if (delta < 0) 
                {
                    y--;
                    delta += 4 * a_sqr * (2 * y + 3);
                }
                // Переход по диагонали
                else 
                {
                    y--;
                    delta = delta - 8 * b_sqr * (x + 1) + 4 * a_sqr * (2 * y + 3);
                    x++;
                }
            }

            for (int i = 0; i< xy.size(); i++)
            {
                dots = new Dots();
                dots.x = (xy.get(i).x - x0c) * Math.cos(phi) + (xy.get(i).y - y0c) * Math.sin(phi) + x0c;
                dots.y = (xy.get(i).x - x0c) * Math.sin(phi) - (xy.get(i).y - y0c) * Math.cos(phi) + y0c;
                xy.set(i, dots);
            }
/*
            if (!xy.isEmpty())
            {
                g.setColor(Color.black);
                for (int i = 0; i < xy.size(); i++) 
                {
                    int x1, y1;
                    x1 = (int) xy.get(i).x ;    
                    y1 = (int) xy.get(i).y ; 
                    g.drawLine ((int) x1, (int) y1, (int) x1, (int) y1);
                }
            }

            for (int i = 0; i< xy.size(); i++)
            {
                dots = new Dots();
                dots.x = (xy.get(i).x - x0c) * Math.cos(phi) + (xy.get(i).y - y0c) * Math.sin(phi) + x0c;
                dots.y = (xy.get(i).x - x0c) * Math.sin(phi) - (xy.get(i).y - y0c) * Math.cos(phi) + y0c;
                xy.set(i, dots);
            }
*/
            if (!xy.isEmpty())
            {
                g.setColor(ColorBorder);
            if (!TypeBorder)
            {
                for (int i = 0; i < xy.size()-4; i++) 
                {
                    int x1, y1, x2, y2;
                    x1 = (int) xy.get(i).x ;    
                    y1 = (int) xy.get(i).y ; 
                    x2 = (int) xy.get(i+4).x ;    
                    y2 = (int) xy.get(i+4).y ; 
                    g.drawLine ((int) x1, (int) y1, (int) x2, (int) y2);
                    i += gageBorder*4;
                }
            }
            else 
            {
                g2.setStroke(new BasicStroke(gageBorder, BasicStroke.CAP_ROUND , BasicStroke.JOIN_BEVEL));
                for (int i = 0; i < xy.size()-4; i++) 
                {
                    int x1, y1, x2, y2;
                    x1 = (int) xy.get(i).x ;    
                    y1 = (int) xy.get(i).y ; 
                    x2 = (int) xy.get(i+4).x ;    
                    y2 = (int) xy.get(i+4).y ; 
                    g.drawLine ((int) x1, (int) y1, (int) x2, (int) y2);
                }
            } 
            }
        }
        g2.setStroke(new BasicStroke(1));
        Check();
        if ((view.MainWindow.regim == 4)&&(view.Canvas.DotsWeb)) paintCheck(g);
        if ((view.Canvas.Choicer)&&(view.MainWindow.regim != 4)) ChoiceWeb(g);
        }  
    } 
    
    @Override
    public void Cross(Graphics g) 
    { 
        double x, y;      
        Dots dots;
        //смещение по осям  
        x = x2 - x1;
        y = y2 - y1;
        //переодим смещение центра в мировые координаты
        x0c =  (int) ((int) x0 + x);
        y0c = (int) ((int) y0 + y);
        ac = (int) (a/Math.exp(scale));
        bc = (int) (b/Math.exp(scale));
        int x0 = (int) (x0c/Math.exp(scale)-x_shift);
        int y0 = (int) (y0c/Math.exp(scale)-y_shift);
        x0c = x0;
        y0c = y0;
        
    /*       if (ac>1000 | bc >1000)
      {                                              
          if (ac>1000){
              scale=1000/ac;
          }else{
             scale=1000/bc;
          }
          ac=(int) (ac*scale);
          bc=(int) (bc*scale);
      } 
       */
        
        dots = new Dots();
        x = 0;
        y = bc;
        int a_sqr = ac * ac; // a^2, a - длина
        int b_sqr = bc * bc; // b^2, b - высота
        int delta = (int) (4 * b_sqr * ((x + 1) * (x + 1)) + a_sqr * ((2 * y - 1) * (2 * y - 1)) - 4 * a_sqr * b_sqr); // Функция координат точки (x+1, y-1/2)
      
        xy2 = new ArrayList();
      
         
        while (a_sqr * (2 * y - 1) > 2 * b_sqr * (x + 1)) // Первая часть дуги
        {
            dots = new Dots();
            dots.x = x0 + x;
            dots.y = y0 + y;
            xy2.add(dots);

            dots = new Dots();
            dots.x = x0 + x;
            dots.y = y0 - y;
            xy2.add(dots);

            dots = new Dots();
            dots.x = x0 - x;
            dots.y = y0 - y;
            xy2.add(dots);

            dots = new Dots();
            dots.x = x0 - x;
            dots.y = y0 + y;
            xy2.add(dots);
            // Переход по горизонтали
            if (delta < 0) 
            {
                x++;
                delta += 4 * b_sqr * (2 * x + 3);
            }
            // Переход по диагонали
            else 
            {
                x++;
                delta = (int) (delta - 8 * a_sqr * (y - 1) + 4 * b_sqr * (2 * x + 3));
                y--;
            }
        }
        delta = (int) (b_sqr * ((2 * x + 1) * (2 * x + 1)) + 4 * a_sqr * ((y + 1) * (y + 1)) - 4 * a_sqr * b_sqr); // Функция координат точки (x+1/2, y-1)
        while (y + 1 != 0) // Вторая часть дуги, если не выполняется условие первого цикла, значит выполняется a^2(2y - 1) <= 2b^2(x + 1)
        {
            dots = new Dots();
            dots.x = x0 + x;
            dots.y = y0 + y;
            xy2.add(dots);

            dots = new Dots();
            dots.x = x0 + x;
            dots.y = y0 - y;
            xy2.add(dots);

            dots = new Dots();
            dots.x = x0 - x;
            dots.y = y0 - y;
            xy2.add(dots);

            dots = new Dots();
            dots.x = x0 - x;
            dots.y = y0 + y;
            xy2.add(dots);
            // Переход по вертикали
            if (delta < 0) 
            {
                y--;
                delta += 4 * a_sqr * (2 * y + 3);
            }
            // Переход по диагонали
            else 
            {
                y--;
                delta = (int) (delta - 8 * b_sqr * (x + 1) + 4 * a_sqr * (2 * y + 3));
                x++;
            }
        }
        for (int i = 0; i< xy2.size(); i++)
        {
            dots = new Dots();
            dots.x = (xy2.get(i).x - x0) * Math.cos(phi) + (xy2.get(i).y - y0) * Math.sin(phi) + x0;
            dots.y = (xy2.get(i).x - x0) * Math.sin(phi) - (xy2.get(i).y - y0) * Math.cos(phi) + y0;
            xy2.set(i, dots);
        }
        
        g.setColor(Color.pink);
        for (int i = 0; i < xy2.size()-4; i++) 
        {
            g.drawLine((int)(xy2.get(i).x), (int)(xy2.get(i).y), (int)(xy2.get(i+4).x), (int)(xy2.get(i+4).y));
        }
        if ((view.Canvas.Choicer)&&(view.MainWindow.regim != 4)) ChoiceWeb(g);
    }   
    
    @Override
    public void Turned(Graphics g) 
    { 
        double alpha, beta;
        Dots dots = new Dots();

        x0c = x0;
        y0c = y0;
        ac = (int) (a/Math.exp(scale));
        bc = (int) (b/Math.exp(scale));
        int x0 = (int) (x0c/Math.exp(scale)-x_shift);
        int y0 = (int) (y0c/Math.exp(scale)-y_shift);
        x0c = x0;
        y0c = y0;
        //новая середина
        xc = x0;
        yc = y0;
        alpha = Math.atan2(x1/Math.exp(scale)-x_shift-xc, y1/Math.exp(scale)-y_shift-yc);
        beta = Math.atan2(x2/Math.exp(scale)-x_shift-xc, y2/Math.exp(scale)-y_shift-yc);
        phi2 = alpha-beta;
        System.out.println("Угол phi = " + Math.toDegrees(phi2));
        
   /*     if (ac>1000 | bc >1000)
      {                                              
          if (ac>1000){
              scale=1000/ac;
          }else{
             scale=1000/bc;
          }
          ac=(int) (ac*scale);
          bc=(int) (bc*scale);
      }  */
        
        dots = new Dots();
        int x = 0;
        int y = bc;
        int a_sqr = ac * ac; // a^2, a - длина
        int b_sqr = bc * bc; // b^2, b - высота
        int delta = (int) (4 * b_sqr * ((x + 1) * (x + 1)) + a_sqr * ((2 * y - 1) * (2 * y - 1)) - 4 * a_sqr * b_sqr); // Функция координат точки (x+1, y-1/2)
        xy2 = new ArrayList();
        // Первая часть дуги
        while (a_sqr * (2 * y - 1) > 2 * b_sqr * (x + 1)) 
        {
            dots = new Dots();
            dots.x = x0 + x;
            dots.y = y0 + y;
            xy2.add(dots);
                  
            dots = new Dots();
            dots.x = x0 + x;
            dots.y = y0 - y;
            xy2.add(dots);

            dots = new Dots();
            dots.x = x0 - x;
            dots.y = y0 - y;
            xy2.add(dots);

            dots = new Dots();
            dots.x = x0 - x;
            dots.y = y0 + y;
            xy2.add(dots);
            // Переход по горизонтали
            if (delta < 0) 
            {
                x++;
                delta += 4 * b_sqr * (2 * x + 3);
            }
            // Переход по диагонали
            else 
            {
                x++;
                delta = (int) (delta - 8 * a_sqr * (y - 1) + 4 * b_sqr * (2 * x + 3));
                y--;
            }
        }
        // Функция координат точки (x+1/2, y-1)
        delta = (int) (b_sqr * ((2 * x + 1) * (2 * x + 1)) + 4 * a_sqr * ((y + 1) * (y + 1)) - 4 * a_sqr * b_sqr); 
        // Вторая часть дуги, если не выполняется условие первого цикла, значит выполняется a^2(2y - 1) <= 2b^2(x + 1)
        while (y + 1 != 0) 
        {
            dots = new Dots();
            dots.x = x0 + x;
            dots.y = y0 + y;
            xy2.add(dots);

            dots = new Dots();
            dots.x = x0 + x;
            dots.y = y0 - y;
            xy2.add(dots);

            dots = new Dots();
            dots.x = x0 - x;
            dots.y = y0 - y;
            xy2.add(dots);

            dots = new Dots();
            dots.x = x0 - x;
            dots.y = y0 + y;
            xy2.add(dots);
            // Переход по вертикали
            if (delta < 0) 
            {
                y--;
                delta += 4 * a_sqr * (2 * y + 3);
            }
            else // Переход по диагонали
            {
                y--;
                delta = (int) (delta - 8 * b_sqr * (x + 1) + 4 * a_sqr * (2 * y + 3));
                x++;
            }
        }
        
        
        for (int i = 0; i< xy2.size(); i++)
        {
            dots = new Dots();
            dots.x = (xy2.get(i).x - xc) * Math.cos(phi2) + (xy2.get(i).y - yc) * Math.sin(phi2) + xc;
            dots.y = (xy2.get(i).x - xc) * Math.sin(phi2) - (xy2.get(i).y - yc) * Math.cos(phi2) + yc;
            xy2.set(i, dots);
        }
        
        g.setColor(Color.pink);
        for (int i = 0; i < xy2.size(); i++) 
        {
            g.drawLine((int)xy2.get(i).x, (int)xy2.get(i).y, (int)xy2.get(i).x, (int)xy2.get(i).y);
        }
        if ((view.Canvas.Choicer)&&(view.MainWindow.regim != 4)) ChoiceWeb(g);
    }
    
    @Override
    public void Relise() 
    {    
        if (!xy2.isEmpty())
        {
           System.out.println("Запоминалка залетела");
        /*   for (int i = 0; i<xy.size(); i++)
            {
            xy.set(i, xy2.get(i));
            }*/
           
            System.out.println(xy);
            System.out.println("-------------");
            System.out.println(xy2);

           x0 = (int) ((x0c + x_shift)*Math.exp(scale));
           y0 = (int) ((y0c + y_shift)*Math.exp(scale));
           a = (int) (ac*Math.exp(scale));
           b = (int) (bc*Math.exp(scale));
           phi = phi2;
           Check();
        }
    }
    
    @Override
     public void paintCheck(Graphics g)
    {
        int x1,x2,y1,y2;
        g.setColor(Color.blue);
        if (!obs.isEmpty()) 
        {
            for (int i = 0; i< obs.size()-1; i+=2)
            {
                x1 = (int) obs.get(i).x ;
                y1 = (int) obs.get(i).y;
                x2 = (int) obs.get(i+1).x;
                y2 = (int) obs.get(i+1).y ;
                g.drawLine(x1, y1, x2, y2);
            }

            //Очень важный блок, показывает истинные мировые координаты фигуры
            /*
            g.setColor(Color.green);
            if (!obs.isEmpty()) 
            {
                for (int i = 0; i< obs.size()-1; i+=2)
                {
                    x1 = (int) obs.get(i).x;
                    y1 = (int) obs.get(i).y;
                    x2 = (int) obs.get(i+1).x;
                    y2 = (int) obs.get(i+1).y;
                    g.drawLine(x1, y1, x2, y2);
                }
            }
        */

            g.setColor(Color.red);
            for (int i = 0; i< obs.size()-1; i+=3)
            {
                x1 = (int) obs.get(i).x;
                y1 = (int) obs.get(i).y;
                g.drawRect(x1, y1,1,1);
            }
        }
    }
   
    @Override
     public void StartLock(double x, double y) 
    {
        loc_x = x;
        loc_y = y;
        //определяем зону первого клика
        //если точка не входит в диапозон, то локация остается равной нулю
       
        if ((x<=obs.get(0).x)||(x>=obs.get(18).x)||(y<=obs.get(0).y)||(y>=obs.get(18).y))
        {
            location = 0;
        }
        else
        {
             //прогоняем по массиву мировых координат
            for (byte i = 0; i < 6; i++)
            {
                //просматриваем верхний диапозон координат
                if (i<3)
                {
                    //проверяем первые три локации от 0 до 9
                    if ((x>obs.get(i*3).x)&&(x<obs.get(i*3+3).x))location = (byte)( i+1);
                }
                //просматриваемый правый диапозон координат, проверяем локации от 9 до 18
                else 
                {
                    if ((y>obs.get(i*3).y)&&(y<obs.get(i*3+3).y))location = (byte)( location+3*(i-3));
                }
            }
        }
        System.out.println("Nlocation = " + location);
    }

    //вычисляем длину сжатия-расширения фигуры от точки клика до конца клика
    @Override
    public void EndLock(double x, double y) 
    {
        //определяем где же движется курсор
        transp_x = loc_x - x;
        transp_y = loc_y - y;
    }   
     
    @Override
    public void Deformation(Graphics g)//деформация фигуры
    {
        /*
        loc_x, loc_y - точка начала клика в мировых координатах
        transp_x, transp_y - изменение расстояния в мир.координатах
        Transp - метод изменения координат фигуры
        */
    
        x0c = (int) (x0/Math.exp(scale)-x_shift);
        y0c = (int) (y0/Math.exp(scale)-y_shift);

        TranspEll();
        Dots dots;
        dots = new Dots();
        int x = 0;
        int y = bc;
        int a_sqr = ac * ac; // a^2, a - длина
        int b_sqr = bc * bc; // b^2, b - высота
        int delta = (int) (4 * b_sqr * ((x + 1) * (x + 1)) + a_sqr * ((2 * y - 1) * (2 * y - 1)) - 4 * a_sqr * b_sqr); // Функция координат точки (x+1, y-1/2)
        xy2 = new ArrayList();
        // Первая часть дуги
        while (a_sqr * (2 * y - 1) > 2 * b_sqr * (x + 1)) 
        {
            dots = new Dots();
            dots.x = x0c + x;
            dots.y = y0c + y;
            xy2.add(dots);
                  
            dots = new Dots();
            dots.x = x0c + x;
            dots.y = y0c - y;
            xy2.add(dots);

            dots = new Dots();
            dots.x = x0c - x;
            dots.y = y0c - y;
            xy2.add(dots);

            dots = new Dots();
            dots.x = x0c - x;
            dots.y = y0c + y;
            xy2.add(dots);
            // Переход по горизонтали
            if (delta < 0) 
            {
                x++;
                delta += 4 * b_sqr * (2 * x + 3);
            }
            // Переход по диагонали
            else 
            {
                x++;
                delta = (int) (delta - 8 * a_sqr * (y - 1) + 4 * b_sqr * (2 * x + 3));
                y--;
            }
        }
        // Функция координат точки (x+1/2, y-1)
        delta = (int) (b_sqr * ((2 * x + 1) * (2 * x + 1)) + 4 * a_sqr * ((y + 1) * (y + 1)) - 4 * a_sqr * b_sqr); 
        // Вторая часть дуги, если не выполняется условие первого цикла, значит выполняется a^2(2y - 1) <= 2b^2(x + 1)
        while (y + 1 != 0) 
        {
            dots = new Dots();
            dots.x = x0c + x;
            dots.y = y0c + y;
            xy2.add(dots);

            dots = new Dots();
            dots.x = x0c + x;
            dots.y = y0c - y;
            xy2.add(dots);

            dots = new Dots();
            dots.x = x0c - x;
            dots.y = y0c - y;
            xy2.add(dots);

            dots = new Dots();
            dots.x = x0c - x;
            dots.y = y0c + y;
            xy2.add(dots);
            // Переход по вертикали
            if (delta < 0) 
            {
                y--;
                delta += 4 * a_sqr * (2 * y + 3);
            }
            else // Переход по диагонали
            {
                y--;
                delta = (int) (delta - 8 * b_sqr * (x + 1) + 4 * a_sqr * (2 * y + 3));
                x++;
            }
        }
        
        
        for (int i = 0; i< xy2.size(); i++)
        {
            dots = new Dots();
            dots.x = (xy2.get(i).x - x0c) * Math.cos(phi2) + (xy2.get(i).y - y0c) * Math.sin(phi2) + x0c;
            dots.y = (xy2.get(i).x - x0c) * Math.sin(phi2) - (xy2.get(i).y - y0c) * Math.cos(phi2) + y0c;
            xy2.set(i, dots);
        }
        
        g.setColor(Color.pink);
        for (int i = 0; i < xy2.size(); i++) 
        {
            g.drawLine((int)xy2.get(i).x, (int)xy2.get(i).y, (int)xy2.get(i).x, (int)xy2.get(i).y);
        }
    }

    private void TranspEll() 
    {
        /*
        loc_x, loc_y - точка начала клика в мировых координатах
        transp_x, transp_y - изменение расстояния в мир.координатах
        Transp - метод изменения координат фигуры
        */
        
        if (location == 1) 
        {
            ac = (int) (a + transp_y/2*Math.exp(scale));
            bc = (int) (b + transp_x/2*Math.exp(scale));
        }

        if (location == 3) 
        {
            ac = (int) (a + transp_y/2*Math.exp(scale));
            bc = (int) (b - transp_x/2*Math.exp(scale));
        }


        if (location == 7) 
        {
            ac = (int) (a - transp_y/2*Math.exp(scale));
            bc = (int) (b + transp_x/2*Math.exp(scale));
        }

        if (location == 9)
        {     
            ac = (int) (a - transp_y/2*Math.exp(scale));
            bc = (int) (b - transp_x/2*Math.exp(scale));
        
        }
        if ((location == 2) ||(location == 8))
        {
            bc = (int) (b + transp_y/2*Math.exp(scale));
        }
        
        if ((location == 4) ||(location == 6))
        {
            ac = (int) (a - transp_x/2*Math.exp(scale));
        }
        if (location == 5) 
        { 
            x0c = (int) (x0c - transp_x);
            y0c = (int) (y0c - transp_y);
            System.out.println(x0c + " " + y0c + ";");
        }
        if (bc <= 0) bc = 1;
        if (ac <= 0) ac = 1;
    }
  
    @Override
    public void ChoiceClick(double x, double y) 
    {        
        System.out.println("Тыкнули на экран в точке x (" + x + ") y (" + y + ")" + " смотрим фигуру " + type);
        
       // x = (x + x_shift) * Math.exp(scale);
       // y = (y + y_shift) * Math.exp(scale);
        
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
         //   System.out.println("Найдена фигура типа " + type + " номер #" + number);
            //Блок отбора по номеру number
          //  view.Canvas.ConstructShape = que.get(number-1); 
          //  ShowName.setVisible(true);
          //  ShowName.setText(type + " " + number);
          
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
    
    @Override
    public void ChoiceWeb(Graphics g)
    {
        //Рисуем на основе сетки obs
        g.setColor(Color.blue);
        int x1, y1, x2, y2;
        //Рисуем линии от каждого угла вправо-вниз
        for (int i = 0; i< obs.size()-1; i+=9)
            {
                x1 = (int) (obs.get(i).x) ;
                y1 = (int) (obs.get(i).y);
                x2 = (int) (obs.get(i+1).x);
                y2 = (int) (obs.get(i+1).y) ;
                g.drawLine(x1, y1, x2, y2);
            }
        //Рисуем линии от каждого угла влево-вверх
        for (int i = 8; i< obs.size()-1; i+=9)
            {
                x1 = (int) (obs.get(i).x) ;
                y1 = (int) (obs.get(i).y);
                x2 = (int) (obs.get(i+1).x);
                y2 = (int) (obs.get(i+1).y) ;
                g.drawLine(x1, y1, x2, y2);
            }
           //Рисуем последний штрих в левом верхнем углу
                x1 = (int) (obs.get(0).x) ;
                y1 = (int) (obs.get(0).y);
                x2 = (int) (obs.get(35).x);
                y2 = (int) (obs.get(35).y) ;
                g.drawLine(x1, y1, x2, y2);
    }
    
    @Override
    public void Setting(MainWindow base)
    {
        double xmax, xmin, ymax, ymin, w, h;
        xmax = xy.get(0).x;
        xmin = xy.get(0).x;
        ymax = xy.get(0).y;
        ymin = xy.get(0).y;
        for (int i = 0; i < xy.size(); i++)
        {
            if (xmax < xy.get(i).x) xmax = xy.get(i).x;
            if (xmin > xy.get(i).x) xmin = xy.get(i).x;
            if (ymax < xy.get(i).y) ymax = xy.get(i).y;
            if (ymin > xy.get(i).y) ymin = xy.get(i).y;
        }
        w = a*2;
        h = b*2;
        base.Xmin.setText (""+ xmin);
        base.Xmax.setText (""+ xmax);
        base.Ymin.setText (""+ ymin);
        base.Ymax.setText (""+ ymax);
        base.Width.setText (""+ w);
        base.Height.setText (""+ h);
    }
}
