
package view;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.InputMismatchException;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import model.Shape;
import static view.MainWindow.*;

public class Singleton extends JPanel implements Propertis //Это single-тон
{
    public int save_number;
        //public static final Singleton INSTANCE = new Singleton();
    MainWindow MainWindow = null;
    public Singleton (MainWindow base)
    {
        this.MainWindow = base;    
    }
    //новая очередь, удаляем старую, заливаем новую и рисуем её тут же
    public void NewChoice()
    { 
        //Поторное заполнение ChoiceMenu
        String namer = null;
        MainWindow.ChoiceMenu.removeAll();
        for (int i = 0; i<Canvas.que.size();i++)
        {
           // ((Canvas)MainWindow.jPanel2).ActionShape = Canvas.que.get(i);
             namer = Canvas.que.get(i).name;
           // ((Canvas)MainWindow.jPanel2).ActionShape.name = ((Canvas)MainWindow.jPanel2).ActionShape.name + " " + (i+1);//имя на 1 больше истинного значения
            MainWindow.ChoiceMenu.add(namer);
           }
         Canvas.ConstructShape = Canvas.que.get(save_number);
         view.MainWindow.number = save_number;
         //ВОТ СЮДА ЗАПИХАТЬ В ИМЯ ПЕРЕМЕННОЙ ЧОЙСА ПРЯМО ТАКИ ОЧЕНЬ НАДО, ИНАЧЕ КИРДЫК
         MainWindow.ChoiceMenu.select(save_number);
         ChoiceMethod();
        ((Canvas)(MainWindow.jPanel2)).DrawOutside();
    }
    

   //выбор активной фигуры - мусор от кнопки ОК старого проекта, можно удалить
    public void jButton14ActionPerformed(java.awt.event.ActionEvent evt)
    {      
        ChoiceMethod();
        ((Canvas)MainWindow.jPanel2).PickUpShape = true;
               Open();//Разрешить кнопки
        ((Canvas)(MainWindow.jPanel2)).DrawOutside();
    } 

    //Метод выборки фигур по названию в окне
    public void ChoiceMethod()
    {
        double w,h;
        String textMenu = MainWindow.ChoiceMenu.getSelectedItem();
        if (textMenu != null)
        {
         //  String[] subStr;
         //  subStr = textMenu.split(" ");
        //   System.out.println(subStr[1]);
            for (int i = 0; i<Canvas.que.size();i++)
            {
                if (Canvas.que.get(i).name.equals(textMenu))
                { 
                    Canvas.ConstructShape = Canvas.que.get(i);
                    number = i;
                }
            }
       
        //   ((Canvas)MainWindow.jPanel2).ConstructShape = ((Canvas)MainWindow.jPanel2).que.get(Integer.parseInt(subStr[1])-1); 
           //Мне нужно поместить текущую фигуру в текстовое окошко
           MainWindow.type.setText(((Canvas)MainWindow.jPanel2).ConstructShape.type);
           MainWindow.name.setText(((Canvas)MainWindow.jPanel2).ConstructShape.name);
           MainWindow.GageBorder.setText(""+((Canvas)MainWindow.jPanel2).ConstructShape.gageBorder);
           MainWindow.ColorBorder.setBackground(((Canvas)MainWindow.jPanel2).ConstructShape.ColorBorder);
           MainWindow.ColorFill.setBackground(((Canvas)MainWindow.jPanel2).ConstructShape.ColorFill);
           MainWindow.ColorFill.setBackground(((Canvas)MainWindow.jPanel2).ConstructShape.ColorFill);
           if (!((Canvas)MainWindow.jPanel2).ConstructShape.TypeBorder) MainWindow.TypeBorder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/punctir.png")));
           else MainWindow.TypeBorder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/splohnaya.png")));
           if (((Canvas)MainWindow.jPanel2).ConstructShape.Visible) MainWindow.visible.setSelected(true); else MainWindow.visible.setSelected(false);
           switch (((Canvas)MainWindow.jPanel2).ConstructShape.TypeFill)
           {
               case 1:
               {
                   MainWindow.TypeFill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/TypeF1.png")));
                   break;
               }
               case 2:
               {
                   MainWindow.TypeFill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/TypeF2.png")));
                   break;
               }
               case 3:
               {
                   MainWindow.TypeFill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/TypeF3.png")));
                   break;
               }
               case 4:
               {
                   MainWindow.TypeFill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/TypeF4.png")));
                   break;
               }
               case 5:
               {
                   MainWindow.TypeFill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/TypeF5.png")));
                   break;
               }
           }
           
           if (!((Canvas)MainWindow.jPanel2).ConstructShape.hard)
            {
                //Зададим метод для Shape для ширины, высоты и т.д.
             /*  w = ((Canvas)MainWindow.jPanel2).ConstructShape.x2 - ((Canvas)MainWindow.jPanel2).ConstructShape.x1;
               MainWindow.Width.setText(""+w);
               h = ((Canvas)MainWindow.jPanel2).ConstructShape.y2 - ((Canvas)MainWindow.jPanel2).ConstructShape.y1;
               MainWindow.Height.setText(""+h);
                MainWindow.Xmin.setText(""+((Canvas)MainWindow.jPanel2).ConstructShape.x1);
                 MainWindow.Xmax.setText(""+((Canvas)MainWindow.jPanel2).ConstructShape.x2);
                  MainWindow.Ymin.setText(""+((Canvas)MainWindow.jPanel2).ConstructShape.y1);
                   MainWindow.Ymax.setText(""+((Canvas)MainWindow.jPanel2).ConstructShape.y2); */
                ((Canvas)MainWindow.jPanel2).ConstructShape.Setting(MainWindow);
            }
           else 
           {
                MainWindow.Width.setText("");
                 MainWindow.Height.setText("");
                  MainWindow.Xmin.setText("");
                   MainWindow.Xmax.setText("");
                    MainWindow.Ymin.setText("");
                     MainWindow.Ymax.setText("");
           }
           
           // loadPropertis(Canvas.ConstructShape);
           //Ниже передаем все параметры фигуры
 //          jLabel2.setText(Canvas.ConstructShape.x1 + " : " + Canvas.ConstructShape.y1);
//           jLabel4.setText(Canvas.ConstructShape.x2 + " : " + Canvas.ConstructShape.y2);
//            System.out.println("Тип фигуры = " + Canvas.ConstructShape.type);
          //  System.out.println("Номер фигуры = " + Canvas.ConstructShape.number);
//            System.out.println("Coordinate1 = " + (Canvas.ConstructShape.x1 + " : " + Canvas.ConstructShape.y1));//static
//            System.out.println("Coordinate2 = " + (Canvas.ConstructShape.x2 + " : " + Canvas.ConstructShape.y2));//static
//            MainWindow.ShowName.setVisible(true);
//            MainWindow.ShowName.setText(textMenu);
            
           // subStr = null;
      //  }
//        if (que.isEmpty()) ShowName.setVisible(false);
        }
    }   
    //отрисовка
    void ChoiceMenuItemStateChanged(ItemEvent evt) 
    {
        ChoiceMethod();
        ((Canvas)MainWindow.jPanel2).PickUpShape = true;
            Open();//разрешить кнопки
        ((Canvas)(MainWindow.jPanel2)).DrawOutside();
        //((Singleton)base.jPanel2).ChoiceMenuItemStateChanged(evt);//лол
    }

    //Вызов палитры цветов
    void ColorButton(ActionEvent evt) 
    {
        JColorChooser.showDialog(this, TOOL_TIP_TEXT_KEY, Color.yellow);
    }

    public void Closed() 
    {
               MainWindow.LevelUp.setEnabled(false);
                MainWindow.LevelDown.setEnabled(false);
                 MainWindow.LevelMin.setEnabled(false);
                  MainWindow.LevelMax.setEnabled(false);
                    MainWindow.Delete.setEnabled(false);
                     MainWindow.Enter.setEnabled(false);
                      MainWindow.name.setText(" ");
                       MainWindow.name.setEnabled (false);
                        MainWindow.type.setText(" ");
                         MainWindow.visible.setEnabled (false);
                          MainWindow.visible.setEnabled(false);
                           MainWindow.Width.setText("");
                            MainWindow.Height.setText("");
                             MainWindow.Xmin.setText("");
                              MainWindow.Xmax.setText("");
                               MainWindow.Ymin.setText("");
                                MainWindow.Ymax.setText("");
                                 MainWindow.Xmin.setEnabled(false);
                                  MainWindow.Xmax.setEnabled(false);
                                   MainWindow.Ymin.setEnabled(false);
                                    MainWindow.Ymax.setEnabled(false);
                                     MainWindow.ColorBorder.setEnabled(false);
                                       MainWindow.GageBorder.setEnabled(false);
                                        MainWindow.TypeBorder.setEnabled(false);
                                         MainWindow.TypeFill.setEnabled(false);
                                          MainWindow.ColorFill.setEnabled(false);
    }

    public void Open() 
    {
        MainWindow.LevelUp.setEnabled(true);
                MainWindow.LevelDown.setEnabled(true);
                 MainWindow.LevelMin.setEnabled(true);
                  MainWindow.LevelMax.setEnabled(true);
                    MainWindow.Delete.setEnabled(true);
                     MainWindow.Enter.setEnabled(true);
                       MainWindow.visible.setEnabled (true);
                        MainWindow.name.setEnabled (true);
                         MainWindow.visible.setEnabled(true);
                           MainWindow.Xmin.setEnabled(true);
                             MainWindow.Xmax.setEnabled(true);
                               MainWindow.Ymin.setEnabled(true);
                                 MainWindow.Ymax.setEnabled(true);
                                  MainWindow.ColorBorder.setEnabled(true);
                                   MainWindow.GageBorder.setEnabled(true);
                                    MainWindow.TypeBorder.setEnabled(true);
                                     MainWindow.TypeFill.setEnabled(true);
                                          MainWindow.ColorFill.setEnabled(true);
    }

    void CheckBox() 
    {
       if (MainWindow.visible.isSelected())
       {
           ((Canvas)MainWindow.jPanel2).ConstructShape.Visible = true;
       }
       else
           ((Canvas)MainWindow.jPanel2).ConstructShape.Visible = false;
    }

    void SetName() 
    {
        if (((Canvas)MainWindow.jPanel2).ConstructShape != null)
        {
            ((Canvas)MainWindow.jPanel2).ConstructShape.name = MainWindow.name.getText();
            NewChoice();
        }
    }

    void SetBorderSize() 
    {
        if (((Canvas)MainWindow.jPanel2).ConstructShape != null)
        {
            float i = 1;
            try 
            {
               try 
               {
                   i =  Float.parseFloat(MainWindow.GageBorder.getText());
               }
               catch (ClassCastException e)
               {
                   i = 1;
               }
               if (i<1) 
               {
                   i = 1;
                   MainWindow.GageBorder.setText("1.0");
               }
               if (i>100)
               {
                   i = 100;
                   MainWindow.GageBorder.setText("100.0");
               }
            }
            catch (InputMismatchException e)
            {
               i =  ((Canvas)MainWindow.jPanel2).ConstructShape.gageBorder;
            }
            ((Canvas)MainWindow.jPanel2).ConstructShape.gageBorder = i;
            ((Canvas)MainWindow.jPanel2).DrawOutside();
        }
    }
}
