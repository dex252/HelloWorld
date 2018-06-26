
package model;

import java.awt.*;
import static view.MainWindow.*;

public class Line extends Shape
{  
    static
    { 
        FactoryShape.register(Line.class);
    }
    
    public Line ()
    {   
        this.type = "Line"; 
    }
    
    @Override
    public void FirstClick (double x1, double y1)
    {
        if (view.MainWindow.regim==0)
        {
            Dots dots = new Dots();
            dots.x = (x1+x_shift)*Math.exp(scale);
            dots.y = (y1+y_shift)*Math.exp(scale);
            xy.add(dots);
            xy.add(dots);
            this.x1 = (x1+x_shift)*Math.exp(scale);//ДОБАВИЛ, НЕ ХВАТАЛО???
            this.y1 = (y1+y_shift)*Math.exp(scale);
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
            Dots dots = new Dots();
            dots.x = (x1+x_shift)*Math.exp(scale);
            dots.y = (y1+y_shift)*Math.exp(scale);
            xy.set(1, dots);
            this.x2 = (x1+x_shift)*Math.exp(scale);//Добавил АНАЛОГИЧНО
            this.y2 = (y1+y_shift)*Math.exp(scale);
        }
        else 
        {
            this.x2 = (x1+x_shift)*Math.exp(scale);
            this.y2 = (y1+y_shift)*Math.exp(scale);
        }
    } 
    
    //Отрисовка линии
    @Override
    public void paint(Graphics g, double scale, double x_shift, double y_shift) 
    { 
        int x1=0,x2=0,y1=0,y2=0;
        g.setColor(Color.red);
        if (!xy.isEmpty())
        {
            x1 = (int) (xy.get(0).x/Math.exp(scale)-x_shift);
            x2 = (int) (xy.get(1).x/Math.exp(scale)-x_shift);
            y1 = (int) (xy.get(0).y/Math.exp(scale)-y_shift);
            y2 = (int) (xy.get(1).y/Math.exp(scale)-y_shift);
            g.drawLine(x1, y1, x2, y2); 
        }
        if ((view.MainWindow.regim == 4)&&(view.Canvas.DotsWeb)) paintCheck(g);
        if ((view.Canvas.Choicer)&&(view.MainWindow.regim != 4)) ChoiceWeb(g);
    }
    
    //Отрисовка линии вовремя параллельного переноса
    @Override
    public void Cross(Graphics g) 
    { 
        double x, y; //смещение по осям       
        Dots dots = new Dots();
        if (xy2.size()<2) 
        {
            xy2.add(dots);
            xy2.add(dots);
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
        
        int x1=0,x2=0,y1=0,y2=0;
        x1 = (int) (xy2.get(0).x/Math.exp(scale)-x_shift);
        x2 = (int) (xy2.get(1).x/Math.exp(scale)-x_shift);
        y1 = (int) (xy2.get(0).y/Math.exp(scale)-y_shift);
        y2 = (int) (xy2.get(1).y/Math.exp(scale)-y_shift);
        
        g.setColor(Color.pink);
        g.drawLine(x1, y1, x2, y2); 
        if ((view.Canvas.Choicer)&&(view.MainWindow.regim != 4)) ChoiceWeb(g);
    }    
    
    @Override
    public void Turned(Graphics g) 
    { 
        double alpha, beta;
        xc = (xy.get(0).x + xy.get (1).x)/2;//середина линии
        yc = (xy.get(0).y + xy.get (1).y)/2;//середина линии
        alpha = Math.atan2(x1-xc, y1-yc);
        beta = Math.atan2(x2-xc, y2-yc);
        phi = alpha-beta;
        System.out.println("Угол phi = " + Math.toDegrees(phi));
        Dots dots = new Dots();
        
        if (xy2.size()<2) 
        {
            xy2.add(dots);
            xy2.add(dots);
        }
        
        for (int i = 0; i< xy2.size(); i++)
        {
            dots = new Dots();
            dots.x = (xy.get(i).x - xc) * Math.cos(phi) - (xy.get(i).y - yc) * Math.sin(phi) + xc;
            dots.y = (xy.get(i).x - xc) * Math.sin(phi) + (xy.get(i).y - yc) * Math.cos(phi) + yc;
            xy2.set(i, dots);
        }
        
        int x1=0,x2=0,y1=0,y2=0;
        x1 = (int) (xy2.get(0).x/Math.exp(scale)-x_shift);
        x2 = (int) (xy2.get(1).x/Math.exp(scale)-x_shift);
        y1 = (int) (xy2.get(0).y/Math.exp(scale)-y_shift);
        y2 = (int) (xy2.get(1).y/Math.exp(scale)-y_shift);
        g.setColor(Color.pink);
        g.drawLine(x1, y1, x2, y2); 
        if ((view.Canvas.Choicer)&&(view.MainWindow.regim != 4)) ChoiceWeb(g);
   }
    
    @Override
    public void Relise() 
    {    
        if (xy2.size()==2)
        {
           for (int i = 0; i<xy.size(); i++)
            {
               xy.set(i, xy2.get(i));
            }
            Check();
        }
    }
    
   //Метод обработки выбора фигуры по клику для прямой (в виде поиска пересечения линии с крестиком размером 2x2)
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
        Start.x = xy.get(0).x;
        Start.y = xy.get(0).y;
            
        End.x = xy.get(1).x;
        End.y = xy.get(1).y;

        System.out.println("Точки начала x " + Start.x + " y " + Start.y);
        System.out.println("Точки конца x " + End.x + " y " + End.y);
        
        //Высчитываем по функции пересекается ли отрезок с лучем.
        boolean peresek = checkIntersectionOfTwoLineSegments(Click1, Click2, Start, End) ;
        //System.out.println("Значение пересечения на шаге  " + i + " равно " + peresek);
        if (peresek)
        {
            peresekNumber++;
        }
        else
        {
        //а если не найдено пересечение, то посмотри по горизонтали (крестик)
        Click1.x = x;
        Click1.y = y-1;
        
        Click2.x = x;
        Click2.y = y+1;
        peresek = checkIntersectionOfTwoLineSegments(Click1, Click2, Start, End) ;
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
         //   view.Canvas.ConstructShape = que.get(number-1); 
          //  ShowName.setVisible(true);
          //  ShowName.setText(type + " " + number);
          
             System.out.println("Найдена фигура типа " + type + " номер #" + view.MainWindow.number);//static
            //Блок отбора по номеру number
            view.Canvas.ConstructShape = que.get(view.MainWindow.number); //static
 //           ShowName.setVisible(true);
//            ShowName.setText(view.Canvas.ConstructShape.name);
        }
        System.out.println("Число найденных пересечений " + peresekNumber);
         
        if (!view.Canvas.Stoper)
        {
 //           ShowName.setVisible(false);
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
            if ((location1==1)||(location1==2)||(location1==3)||(location1==4)||(location1==7))
            {
                
                if (location1==1)
                {
                    dots.x = x - transp_x;
                    dots.y = y - transp_y;
                }
                if ((location1==2)||(location1==3))
                {
                    dots.y = y - transp_y;
                }
                if ((location1==7)||(location1==4))
                {
                    dots.x = x - transp_x;
                }
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
            if (location1==3)
            {
                dots.x = x - transp_x;
                dots.y = y - transp_y;
            }
             if ((location1==1)||(location1==2))
                {
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
            if (location1==7)
            {
                dots.x = x - transp_x;
                dots.y = y - transp_y;
            }
            if ((location1==1)||(location1==4))
            {
                dots.x = x - transp_x;
            }
            if ((location1==8)||(location1==9))
            {
                dots.y = y - transp_y;
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
            if (location1==9)
            {
                dots.x = x - transp_x;
                dots.y = y - transp_y;
            }
            if ((location1==3)||(location1==6))
            {
                dots.x = x - transp_x;
            }
            if ((location1==7)||(location1==8))
            {
                dots.y = y - transp_y;
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
