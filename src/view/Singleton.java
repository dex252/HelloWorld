
package view;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import model.Shape;
import static view.Canvas.ConstructShape;
import static view.MainWindow.*;

public class Singleton extends JPanel implements Propertis, ActionListener //Это single-тон
{
    int save_number;
        //public static final Singleton INSTANCE = new Singleton();
    MainWindow MainWindow = null;
    public Singleton (MainWindow base)
    {
        this.MainWindow = base;
        addActionListener(this);
    }
    //Смещение фигуры на 1 уроень вниз
    void LevelDownActionPerformed(ActionEvent evt) {
       //Мне извесен MainWindow.number - позиция текущего элемента
       save_number = MainWindow.number;
       if ((MainWindow.number-1)>-1)
       {
            Collections.swap(MainWindow.que, MainWindow.number, MainWindow.number-1);
            //Поменяли выделенный элемент с нижним местами, теперь нужно присвоить конструкту новые значения и переименовать все фигуры в choiceMenu
            save_number--;
            NewChoice();
            //присваиваем обновленное значение конструктору

           // Canvas.ConstructShape = base.que.get(save_number);
       }
       else 
       {
           System.out.println("Достигнуто самое дно");
       }
    }
   //Смещение фигуры на самый верхний уровень в очереди
    void LevelMaxActionPerformed(ActionEvent evt) 
    {
       if (que.size() > 1)
       {
           for (int i = MainWindow.number ; i < que.size()-1; i++)
           {
               Collections.swap (MainWindow.que, i, i+1);
           }
           save_number = que.size()-1;
           NewChoice();
       }
       else
       {
            System.out.println("В очереди один или меньше элементов");
       }
    }
    //Смещение фигуры на 1 уровень вверх
    void LevelUpActionPerformed(ActionEvent evt) 
    {
       save_number = MainWindow.number;
       if ((MainWindow.number+1)<MainWindow.que.size())
       {
            Collections.swap(MainWindow.que, MainWindow.number, MainWindow.number+1);
            //Поменяли выделенный элемент с нижним местами, теперь нужно присвоить конструкту новые значения и переименовать все фигуры в choiceMenu
            save_number++;
            NewChoice();
            //присваиваем обновленное значение конструктору
          //  Canvas.ConstructShape = base.que.get(save_number);
       }
       else 
       {
           System.out.println("Выше уже некуда :C");
       }
    }
    //Смещение фигуры на самый нижний уровень в очереди
    void LevelMinActionPerformed(ActionEvent evt)
    {
       if (que.size() > 1)
       {
            for (int i = MainWindow.number ; i > 0; i--)
           {
               Collections.swap (MainWindow.que, i, i-1);
           }
           save_number = 0;
           NewChoice();
       }
       else
       {
            System.out.println("В очереди один или меньше элементов");
       }
    }
    //новая очередь, удаляем старую, заливаем новую и рисуем её тут же
    void NewChoice()
    { 
        //Поторное заполнение ChoiceMenu
        MainWindow.ChoiceMenu.removeAll();
        for (int i = 0; i<que.size();i++)
        {
            ActionShape = que.get(i);
            ActionShape.name = ActionShape.type + " " + (i+1);//имя на 1 больше истинного значения
            MainWindow.ChoiceMenu.add(ActionShape.name);
            ActionShape = null;
           }
      //   Canvas.ConstructShape = base.que.get(0);
     //    ((MainWindow)base).number = 0;
          Canvas.ConstructShape = MainWindow.que.get(save_number);
         ((MainWindow)MainWindow).number = save_number;
         //ВОТ СЮДА ЗАПИХАТЬ В ИМЯ ПЕРЕМЕННОЙ ЧОЙСА ПРЯМО ТАКИ ОЧЕНЬ НАДО, ИНАЧЕ КИРДЫК
         MainWindow.ChoiceMenu.select(save_number);
         ChoiceMethod();
        ((Canvas)(MainWindow.jPanel1)).DrawOutside();
    }
    

   //выбор активной фигуры
    public void jButton14ActionPerformed(java.awt.event.ActionEvent evt)
    {      
        ChoiceMethod();
        ((Canvas)MainWindow.jPanel1).PickUpShape = true;
               MainWindow.LevelUp.setEnabled(true);
                MainWindow.LevelDown.setEnabled(true);
                 MainWindow.LevelMin.setEnabled(true);
                  MainWindow.LevelMax.setEnabled(true);
        ((Canvas)(MainWindow.jPanel1)).DrawOutside();
    } 

    @Override
    public void loadPropertis(Shape figure) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteShape(Shape figure) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //Метод выборки фигур по названию в окне
    public void ChoiceMethod()
    {
        String textMenu = MainWindow.ChoiceMenu.getSelectedItem();
        if (textMenu != null)
        {
            String[] subStr;
            subStr = textMenu.split(" ");
            System.out.println(subStr[1]);
            Canvas.ConstructShape = que.get(Integer.parseInt(subStr[1])-1); 
           //Мне нужно поместить текущую фигуру в текстовое окошко
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
            
            subStr = null;
        }
//        if (que.isEmpty()) ShowName.setVisible(false);
    }   
    //отрисовка
    void ChoiceMenuItemStateChanged(ItemEvent evt) 
    {
        ChoiceMethod();
        ((Canvas)MainWindow.jPanel1).PickUpShape = true;
               MainWindow.LevelUp.setEnabled(true);
                MainWindow.LevelDown.setEnabled(true);
                 MainWindow.LevelMin.setEnabled(true);
                  MainWindow.LevelMax.setEnabled(true);
        ((Canvas)(MainWindow.jPanel1)).DrawOutside();
        //((Singleton)base.jPanel2).ChoiceMenuItemStateChanged(evt);//лол
    }

    //Вызов палитры цветов
    void ColorButton(ActionEvent evt) 
    {
        JColorChooser.showDialog(this, TOOL_TIP_TEXT_KEY, Color.yellow);
    }

}
