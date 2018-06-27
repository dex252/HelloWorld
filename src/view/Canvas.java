
package view;

import controller.Shift;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import static view.MainWindow.*;

public class Canvas extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener
        
{   

    Image img; 
    Graphics buf;
    int w,h;	
    MainWindow MainWindow;
    static public model.Shape ConstructShape = null;
    public model.Shape ActionShape = null;//текущая фигура/действие
    boolean Afin = false;//Метка афинных преобразований, false - смещение, true - поворот
    static public boolean DotsWeb = false;//Отметка о необходимости рисования сетки деформации для текущей фигуры
    static public boolean Stoper;//Обработка клика мыши дл выбора фигуры в меню
    static public boolean Choicer = false;//выделение фигуры в рамку
    public boolean PickUpShape = true;//Определяем найдена ли фигиру при клике на полотне, если нет - то не рисуем!
    static public controller.Queue que = new controller.Queue();//que - очередь фигур
     
    public Canvas(MainWindow base) 
    {  
        this.MainWindow = base;
        img = new BufferedImage (1200, 600,BufferedImage.TYPE_INT_ARGB);  //	TYPE_INT_ARGB Представляет изображение с 8-битными цветовыми компонентами RGBA, упакованными в целые пиксели.
        w = getWidth(); 
        h = getHeight(); 	 
        buf = img.getGraphics();
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }
    //отрисовка в буфере
    public void draw(controller.Queue que, model.Shape ActionShape) 
    {
      buf.setColor(Color.WHITE);
      //  buf.fillRect(0,0, (int) (this.getWidth()), (int) (this.getHeight()));
      buf.fillRect(0,0, (int) (1150), (int) (600));
        que.forEach((que1) ->
        {
            que1.paint(buf, scale, x_shift, y_shift);
        }); 
  
        Choicer = true;
        if (PickUpShape==false) Choicer = false;//Если фигура не выделена на полотне, то чойсер не работает
        
        if (regim==1)
        {
            if (ConstructShape != null)
            {
                if (Afin) ConstructShape.Cross(buf);
                else ConstructShape.Turned(buf);
                ConstructShape.Check();
            }
        }
        
        if (regim==0) 
        if (ActionShape != null)
        {
            ActionShape.Check();
            ActionShape.paint(buf, scale, x_shift, y_shift);
        }
        
        if ((regim==2)&&(ConstructShape != null)) ConstructShape.paint(buf, scale, x_shift, y_shift);
        
        Choicer = false;
        
        if (ConstructShape != null)
        {
            DotsWeb = true;
            if (regim==4) ConstructShape.paint(buf, scale, x_shift, y_shift);
            if ((regim==4)&&(Deformat = true))
            {
                ConstructShape.Deformation(buf);
                Deformat = false;
            }
            DotsWeb = false;
        }
    }
         
    
    
    @Override
    public void paint(Graphics g) 
    {
        g.drawImage(img, 0, 0, this);    
    }	

    @Override
    public void mousePressed(java.awt.event.MouseEvent evt)
    {        
        if (regim==0)
        {            
            //Если Action не является сложной фигурой, то делаем первый клик
            if ((ActionShape != null)&&(!ActionShape.hard))
            {
                ActionShape.FirstClick(evt.getX(),evt.getY()); 
            }
            
            //Если Action является сложной фигурой и нажата правая кнопка мыши, то записываем сложную фигуру (при условии, что она не является точкой)
            if ((ActionShape != null)&&(ActionShape.hard)&&(evt.getButton() == java.awt.event.MouseEvent.BUTTON3)&&(ActionShape.xy.size()>1))
            {
                que.add(ActionShape);//сюда я воткнул запись сложной фигуры
                MainWindow.number = que.size();
                ActionShape.name = ActionShape.type + " " + MainWindow.number;
                ConstructShape = que.get(que.size()-1);
               // ConstructShape.name = ConstructShape.type + ((MainWindow)base).number;
               // ConstructShape.number = que.size();
             //   ((MainWindow)base).number = que.size();
                System.out.println("Создан объект #" + que.size() + " Тип объекта: "+ ActionShape.type); 
                System.out.println("Конструкту присвоено  " + ConstructShape.name); 
                ActionShape = FS.repeat((model.Shape)ActionShape);//теперь можно обновить объект   
                //Аналогичная запись в строке 199
                MainWindow.number = que.size();
               // ActionShape.number = que.size();
                ActionShape.name = ActionShape.type + " " + MainWindow.number;
                MainWindow.number = que.size()-1;//для возможности удаления фигуры с первого клика  
                MainWindow.ChoiceMenu.add(ActionShape.name);
                //Нужно как то выбирать последний добавленный элемент и показывать его в текстбоксе
                ConstructShape = que.get(que.size()-1);//Пересоздаем фигурку после синглтона
                
                //ЭТО НОВОЕ
                PickUpShape = true;
               MainWindow.LevelUp.setEnabled(true);
                MainWindow.LevelDown.setEnabled(true);
                 MainWindow.LevelMin.setEnabled(true);
                  MainWindow.LevelMax.setEnabled(true);
                    MainWindow.Delete.setEnabled(true);
                     MainWindow.Enter.setEnabled(true);
                    //ЕСЛИ РИСУЕМ ФИГУРУ _ ТО АВТОМАТОМ ВЫДЕЛЯЕМ ИМЕННО ЕЕ
            }
            
            //Если Action является сложной фигурой и нажата НЕ правая кнопка мыши, то создаем часть сложной фигуры
            if ((ActionShape != null)&&(ActionShape.hard)&&(evt.getButton() != java.awt.event.MouseEvent.BUTTON3))
            {      
                ActionShape.FirstClick(evt.getX(),evt.getY());        
            }
        }
        
        if (regim==1)
        {
            //первый клик - начало смещения - левая км
            if ((ConstructShape != null)&&(evt.getButton() == java.awt.event.MouseEvent.BUTTON2))
            {
                Afin = false;
                System.out.println("Начало смещения " + Afin); 
                ConstructShape.FirstClick(evt.getX(),evt.getY()); 
            }
            
            //левый клик - начало поворота - средняя км
            if ((ConstructShape != null)&&(evt.getButton() == java.awt.event.MouseEvent.BUTTON1))
            {
                Afin = true;
                System.out.println("Начало поворота " + Afin); 
                ConstructShape.FirstClick(evt.getX(),evt.getY()); 
            }
            //Запоминаем фигуру
            if ((ConstructShape != null)&&(evt.getButton() == java.awt.event.MouseEvent.BUTTON3))
            { 
                if (ConstructShape != null)
                {
                    System.out.println("Нажата запоминалка");
                    ConstructShape.Relise(); 
                    System.out.println("Запоминалка завершнена");
                  //  que.set(que.size()-1, ConstructShape); - убираем  присвоение последней игуре, ибо конфликтует с меню
                    draw(que, ConstructShape);
                    repaint();
                }
            }
        }
        
        if (regim==2)
        {
            ActionShape = null;
            Shift.FirstClick(evt.getX(),evt.getY());
        }
        
        if ((regim ==4)&&(ConstructShape != null))
        {
            //лкм - высчитываем локацию старта
            if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1)
            {
                ConstructShape.StartLock(evt.getX(),evt.getY()); 
            }
            //пкм - запоминалка
            if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3)
            { 
                if (ConstructShape != null)
                {
                    System.out.println("Нажата запоминалка");
                    ConstructShape.Relise(); 
                    System.out.println("Запоминалка завершнена");
                   // que.set(que.size()-1, ConstructShape); 
                    draw(que, ConstructShape);
                    repaint();
                }
            }
        }
    } 
    
    @Override
    public void mouseReleased(java.awt.event.MouseEvent evt)
    {                                      
        if (regim==0)
        {     
                //Если Action не является сложной фигурой
                if ((ActionShape != null)&&(!ActionShape.hard))
                {            
                    //сюда я воткнул запись фигуры                    
                    que.add(ActionShape);
                   MainWindow.number = que.size();
                    ActionShape.name = ActionShape.type + " " + MainWindow.number;
                    ConstructShape = que.get(que.size()-1);
                    //ConstructShape.number = que.size();
                   // ((MainWindow)base).number = que.size();
                    System.out.println("Создан объект #" + que.size() + " Тип объекта: "+ ActionShape.type); 
                    System.out.println("Конструкту присвоено  " + ConstructShape.name);    
                    //создаем новый объект для рисования 
                    ActionShape = FS.repeat((model.Shape)ActionShape);
                    //Добавляем фигуру в список под номером последнего добавленного элемента в очереди, это норма
                    //Реализовать при удалении не только само удаление, но и изменение всех имен на 1 меньше.
                  //  ActionShape.number = que.size();
                    MainWindow.number = que.size();
                    ActionShape.name = ActionShape.type + " " + MainWindow.number;
                    MainWindow.number = que.size()-1;//чтобы удалить фигуру с первого клика
                   // MainWindow.ChoiceMenu.add(ActionShape.type + " " + ActionShape.number);
                    MainWindow.ChoiceMenu.add(ActionShape.name);
                    ((Singleton)MainWindow.jPanel3).ChoiceMethod();
                    ConstructShape = que.get(que.size()-1);//Пересоздаем фигурку после синглтона
                    
                     //ЭТО НОВОЕ
                PickUpShape = true;
               ((Singleton)MainWindow.jPanel3).Open();//показываем кнопки
                    //ЕСЛИ РИСУЕМ ФИГУРУ _ ТО АВТОМАТОМ ВЫДЕЛЯЕМ ИМЕННО ЕЕ
                }
        }
    }                               
    
    @Override
    public void mouseMoved(MouseEvent me)
    {
        
   }

    @Override
    public void mouseDragged(java.awt.event.MouseEvent evt) 
    {
        if (regim==0)     
        {
            if (ActionShape != null)
            {
                ActionShape.LastClick(evt.getX(),evt.getY()); 
                if (evt.getX()>0)
                {
                    draw(que, ActionShape);
                    ActionShape.Check();
                    repaint();
                }
                else
                {
                    ActionShape.LastClick(0,evt.getY());
                    ActionShape.Check();
                    draw(que, ActionShape);
                    repaint();        
                }    
            }
        }
        
        if (regim==1) 
        {
           if ((ConstructShape != null)&&(evt.getButton() != java.awt.event.MouseEvent.BUTTON3))
            {
            ConstructShape.LastClick(evt.getX(),evt.getY()); 
            draw(que, ActionShape);
            repaint();        
            }
        }
        
        if (regim==2)
        {
            Shift.MovedClick(evt.getX(),evt.getY());
            draw(que, ActionShape);
            repaint();
        }
        
        if (regim==4)
        {
            if (ConstructShape!=null)
            {
                ConstructShape.EndLock(evt.getX(),evt.getY());//Точка, определяющее расстояние изменения
                Deformat = true;
                draw(que, ConstructShape);
                repaint();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent me)
    {
        //В режиме "Лапа" пытаемся выделить объект на холсте
        if (regim == 2)
        {
            Stoper = false; //остановиться когда будет найдено пересечение клика
            PickUpShape = true;
            for (int i = que.size()-1; i > -1; i--)
            {
                if (!Stoper)
                {
                    MainWindow.number = i;//Записываем номер рассматриваемой фигуры (это номер в очереди)
                    if (ConstructShape.Visible) que.get(i).ChoiceClick(me.getX(),me.getY());//если фигура видимая - смотрим, если нет, то не смотрим
                    System.out.println("## "+i + " название" + ConstructShape.name);
                    MainWindow.ChoiceMenu.select(number);
                    ((Singleton)MainWindow.jPanel3).ChoiceMethod();
                    
                }
            }
            if (Stoper==false) 
            {
                PickUpShape = false;
                ((Singleton)MainWindow.jPanel3).Closed();//прячем кнопки
            }
            else {
                PickUpShape = true;
                 ((Singleton)MainWindow.jPanel3).Open();//показываем кнопки
            }
            draw(que, ConstructShape);
            repaint();
        }
       
    }

    @Override
    public void mouseEntered(MouseEvent me) 
    {
        
    }

    @Override
    public void mouseExited(MouseEvent me) 
    {
     
    }  
    
    @Override
    public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) 
    {                                   
        int notches = evt.getWheelRotation();
        if (evt.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL)
        {
            if (notches >= 0)
            {
                System.out.println(scale);
                if (scale < 10)
                scale+=0.1;
                draw(que, ConstructShape);
                repaint();
            }
            else 
            {
                System.out.println(scale);
                scale-=0.1;
                draw(que, ConstructShape);
                repaint();
            }
        }
    }      
    //Отрисовка фигур из любой части программы
    public void DrawOutside()
    {
          draw(que, ConstructShape);
          repaint();
    }
}
