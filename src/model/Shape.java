
package model;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import static view.MainWindow.*;


abstract public class Shape implements Serializable
{
    //public byte choice;
    private static final long serialVersionUID = 1L;
    public double x1 = 0, x2 = 0;
    public double y1 = 0, y2 = 0;
    public ArrayList <Dots> xy = new ArrayList();
    ArrayList <Dots> xy2 = new ArrayList();
    ArrayList <Dots> obs = new ArrayList();
    public String type = null;
    public boolean hard = false;//сложная фигура или простая, true-сложная, false-простая
    double xc, yc; //centre
    double phi=0;
    byte location1; //локация для внутренних точек
    public String name = null;
    public boolean Visible = true;
    
    public Shape ()
    {
        this.type = "Shape";
        this.hard = false;
        this.name = type;
    }
    
    public void Click (double x1, double y1)
    {
        
    }
    
    public void FirstClick (double x1, double y1)
    {
        this.x1 = (x1+x_shift)*Math.exp(scale);
        this.y1 = (y1+y_shift)*Math.exp(scale);
    }
    
    public void LastClick (double x2, double y2)
    {   
        this.x2 = (x1+x_shift)*Math.exp(scale);
        this.y2 = (y1+y_shift)*Math.exp(scale);
    }
    
    abstract public void paint (Graphics g, double scale, double x_shift, double y_shift);

    public void Cross(Graphics buf) 
    {
        
    }

    public void Relise()
    {
        
    }

    public void Turned(Graphics buf) 
    {
        
    }
    
    public void Check()
    {
        if (!xy.isEmpty()) 
        {
            obs = new ArrayList<Dots>();
            double max_x=xy.get(0).x, max_y = xy.get(0).y, min_x = xy.get(0).x,min_y = xy.get(0).y;
            for (int i = 0; i<xy.size(); i++)
            {
                if (xy.get(i).x < min_x) min_x = xy.get(i).x;
                if (xy.get(i).x > max_x) max_x = xy.get(i).x;
                if (xy.get(i).y < min_y) min_y = xy.get(i).y;
                if (xy.get(i).y > max_y) max_y = xy.get(i).y;
            }
            
            double dop_x, dop_y;
            //левая верхняя (0)
            Dots dots = new Dots();
            dots.x = min_x - 5;
            dots.y = min_y - 5;
           
             //точки сверху - добавляем ровно 9 точек [0] -левая крайняя верхняя
            dop_x = ((max_x+5) - (min_x-5))/9;
            for (int i = 0; i<9; i++)
            {
                dots = new Dots();
                dots.x = min_x - 5 + i*dop_x;
                dots.y = min_y - 5;
                obs.add(dots);
            }
             
            //правая верхняя (1)
            dots = new Dots();
            dots.x = max_x + 5;
            dots.y = min_y - 5;  
             
             //точки справа - добавляем 9 точек с учетом конечной [9] - крайняя правая верхняя
            dop_y = ((max_y+5) - (min_y-5))/9;
            for (int i = 0; i<9; i++)
            {
                dots = new Dots();
                dots.x = max_x + 5;
                dots.y = min_y - 5 + i*dop_y;
                obs.add(dots);
            }     
                     
             //правая нижняя (2)
            dots = new Dots();
            dots.x = max_x + 5;
            dots.y = max_y + 5;
             
             //точки снизу [18] - крайняя
            for (int i = 0; i<9; i++)
            {
                dots = new Dots();
                dots.x = max_x + 5 - i*dop_x;
                dots.y = max_y + 5;
                obs.add(dots);
            }
    
             //левая нижняя (3)
            dots = new Dots();
            dots.x = min_x - 5;
            dots.y = max_y + 5;
             
            //точки слева [27] - крайняя
            for (int i = 0; i<9; i++)
            {
                dots = new Dots();
                dots.x = min_x - 5;
                dots.y = max_y + 5 - i*dop_y;
                obs.add(dots);
            }  
        }
    } 
    
    public void paintCheck(Graphics g)
    {
        int x1,x2,y1,y2;
        g.setColor(Color.blue);
        if (!obs.isEmpty()) 
        {
            for (int i = 0; i< obs.size()-1; i+=2)
            {
                x1 = (int) (obs.get(i).x/Math.exp(scale)-x_shift) ;
                y1 = (int) (obs.get(i).y/Math.exp(scale)-y_shift);
                x2 = (int) (obs.get(i+1).x/Math.exp(scale)-x_shift);
                y2 = (int) (obs.get(i+1).y/Math.exp(scale)-y_shift) ;
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
                x1 = (int) (obs.get(i).x/Math.exp(scale)-x_shift);
                y1 = (int) (obs.get(i).y/Math.exp(scale)-y_shift);
                g.drawRect(x1, y1,1,1);
            }
        }
    }

    public void Deformation(Graphics g)//деформация фигуры
    {
        /*
        loc_x, loc_y - точка начала клика в мировых координатах
        transp_x, transp_y - изменение расстояния в мир.координатах
        Transp - метод изменения координат фигуры
        */
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
        
       g.setColor(Color.pink);
       int x1, x2, y1, y2;
       
       for (int i = 0; i<xy2.size()-1; i++)
        {
            x1 = (int) (xy2.get(i).x/Math.exp(scale)-x_shift);
            x2 = (int) (xy2.get(i+1).x/Math.exp(scale)-x_shift);
            y1 = (int) (xy2.get(i).y/Math.exp(scale)-y_shift);
            y2 = (int) (xy2.get(i+1).y/Math.exp(scale)-y_shift); 
            g.drawLine(x1, y1, x2, y2);
        }    
       
        if (type!="Line") 
        {
           x1 = (int) (xy2.get(0).x/Math.exp(scale)-x_shift);
           x2 = (int) (xy2.get(xy2.size()-1).x/Math.exp(scale)-x_shift);
           y1 = (int) (xy2.get(0).y/Math.exp(scale)-y_shift);
           y2 = (int) (xy2.get(xy2.size()-1).y/Math.exp(scale)-y_shift); 
           g.drawLine(x1, y1, x2, y2);
        }
    }
    

    //Определяем в какой локации находится точка фигуры
    public void TranspInside(double x, double y) 
    {
        //если точка не входит в диапозон, то локация остается равной нулю
        if ((x<=obs.get(0).x)||(x>=obs.get(18).x)||(y<=obs.get(0).y)||(y>=obs.get(18).y))
        {
            location1 = 0;
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
                    if ((x>obs.get(i*3).x)&&(x<obs.get(i*3+3).x))location1 = (byte)( i+1);
                }
                //просматриваемый правый диапозон координат, проверяем локации от 9 до 18
                else 
                {
                    if ((y>obs.get(i*3).y)&&(y<obs.get(i*3+3).y))location1 = (byte)( location1+3*(i-3));
                }
            }
        }
    }

    //За основу транспонирования точек взят прямоугольник
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
            
    //определяем в какой локации находится точка клика
    public void StartLock(double x, double y) 
    {
        loc_x = (x + x_shift) * Math.exp(scale);
        loc_y = (y + y_shift) * Math.exp(scale);
        //определяем зону первого клика
        x =  (x + x_shift) * Math.exp(scale);
        y =  (y + y_shift) * Math.exp(scale);
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
    public void EndLock(double x, double y) 
    {
        //определяем где же движется курсор
        transp_x = loc_x - (x+x_shift)*Math.exp(scale);
        transp_y = loc_y - (y+y_shift)*Math.exp(scale);
    }   

    //Метод обработки выбора фигуры по клику
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
           System.out.println("Найдена фигура типа " + type + " номер #" + view.MainWindow.number);//static - также реальная позиция в очереди
            //Блок отбора по номеру number
           // view.Canvas.ConstructShape = que.get(number-1); 
           view.Canvas.ConstructShape = view.Canvas.que.get(view.MainWindow.number); //static - реальная позиция фигуры в очереди
 //           ShowName.setVisible(true);
          //  ShowName.setText(type + " " + number);
//            ShowName.setText(name);
        }
        System.out.println("Число найденных пересечений " + peresekNumber);
         
        if (!view.Canvas.Stoper)
        {
//            ShowName.setVisible(false);
        }
    }
    
    //метод, проверяющий пересекаются ли 2 отрезка [p1, p2] и [p3, p4]
    public boolean checkIntersectionOfTwoLineSegments(Dots p1, Dots p2, Dots p3, Dots p4)  //
    {
        //сначала расставим точки по порядку, т.е. чтобы было p1.x <= p2.x
        if (p2.x < p1.x) 
        {

            Dots tmp = p1;
            p1 = p2;
            p2 = tmp;
        }
        //и p3.x <= p4.x
        if (p4.x < p3.x) 
        {

            Dots tmp = p3;
            p3 = p4;
            p4 = tmp;
        }

        //проверим существование потенциального интервала для точки пересечения отрезков
        if (p2.x < p3.x) 
        {
            return false; //ибо у отрезков нету взаимной абсциссы
        }

        //если оба отрезка вертикальные
        if((p1.x - p2.x == 0) && (p3.x - p4.x == 0))
        {

            //если они лежат на одном X
            if(p1.x == p3.x) 
            {

                //проверим пересекаются ли они, т.е. есть ли у них общий Y
                //для этого возьмём отрицание от случая, когда они НЕ пересекаются
                if (!((Math.max(p1.y, p2.y) < Math.min(p3.y, p4.y)) ||
                        (Math.min(p1.y, p2.y) > Math.max(p3.y, p4.y)))) 
                {

                    return true;
                }
            }

            return false;
        }

        //найдём коэффициенты уравнений, содержащих отрезки
        //f1(x) = A1*x + b1 = y
        //f2(x) = A2*x + b2 = y

        //если первый отрезок вертикальный
        if (p1.x - p2.x == 0) 
        {

            //найдём Xa, Ya - точки пересечения двух прямых
            double Xa = p1.x;
            double A2 = (p3.y - p4.y) / (p3.x - p4.x);
            double b2 = p3.y - A2 * p3.x;
            double Ya = A2 * Xa + b2;

            if (p3.x <= Xa && p4.x >= Xa && Math.min(p1.y, p2.y) <= Ya &&
                    Math.max(p1.y, p2.y) >= Ya)
            {

                return true;
            }

            return false;
        }

    //если второй отрезок вертикальный
        if (p3.x - p4.x == 0) 
        {

            //найдём Xa, Ya - точки пересечения двух прямых
            double Xa = p3.x;
            double A1 = (p1.y - p2.y) / (p1.x - p2.x);
            double b1 = p1.y - A1 * p1.x;
            double Ya = A1 * Xa + b1;

            if (p1.x <= Xa && p2.x >= Xa && Math.min(p3.y, p4.y) <= Ya &&
                    Math.max(p3.y, p4.y) >= Ya) 
            {

                return true;
            }

            return false;
        }

        //оба отрезка невертикальные
        double A1 = (p1.y - p2.y) / (p1.x - p2.x);
        double A2 = (p3.y - p4.y) / (p3.x - p4.x);
        double b1 = p1.y - A1 * p1.x;
        double b2 = p3.y - A2 * p3.x;

        if (A1 == A2) 
        {
            return false; //отрезки параллельны
        }

        //Xa - абсцисса точки пересечения двух прямых
        double Xa = (b2 - b1) / (A1 - A2);

        if ((Xa < Math.max(p1.x, p3.x)) || (Xa > Math.min( p2.x, p4.x)))
        {
            return false; //точка Xa находится вне пересечения проекций отрезков на ось X 
        }
        else 
        {
            return true;
        }
    }
    
    //Выделяем фигуры при её выборе на палитре
    public void ChoiceWeb(Graphics g)
    {
        if (!obs.isEmpty())
        {
            //Рисуем на основе сетки obs
            g.setColor(Color.blue);
            int x1, y1, x2, y2;
            //Рисуем линии от каждого угла вправо-вниз
            for (int i = 0; i< obs.size()-1; i+=9)
                {
                    x1 = (int) (obs.get(i).x/Math.exp(scale)-x_shift) ;
                    y1 = (int) (obs.get(i).y/Math.exp(scale)-y_shift);
                    x2 = (int) (obs.get(i+1).x/Math.exp(scale)-x_shift);
                    y2 = (int) (obs.get(i+1).y/Math.exp(scale)-y_shift) ;
                    g.drawLine(x1, y1, x2, y2);
                }
            //Рисуем линии от каждого угла влево-вверх
            for (int i = 8; i< obs.size()-1; i+=9)
                {
                    x1 = (int) (obs.get(i).x/Math.exp(scale)-x_shift) ;
                    y1 = (int) (obs.get(i).y/Math.exp(scale)-y_shift);
                    x2 = (int) (obs.get(i+1).x/Math.exp(scale)-x_shift);
                    y2 = (int) (obs.get(i+1).y/Math.exp(scale)-y_shift) ;
                    g.drawLine(x1, y1, x2, y2);
                }
           //Рисуем последний штрих в левом верхнем углу
            x1 = (int) (obs.get(0).x/Math.exp(scale)-x_shift) ;
            y1 = (int) (obs.get(0).y/Math.exp(scale)-y_shift);
            x2 = (int) (obs.get(35).x/Math.exp(scale)-x_shift);
            y2 = (int) (obs.get(35).y/Math.exp(scale)-y_shift) ;
            g.drawLine(x1, y1, x2, y2);
        }
    }
}