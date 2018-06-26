/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import javax.swing.JButton;
import view.Canvas;
import static view.Canvas.ConstructShape;
import static view.Canvas.que;
import view.MainWindow;
import view.Singleton;

/**
 *
 * @author dex25
 */
public class Settings extends JButton implements ActionListener
{
    MainWindow MainWindow;
    String operation;
    
    public Settings (String operation, MainWindow base)//MainWindow - наше рабочее окно
    {
        this.MainWindow = base;
        this.addActionListener(this);
        this.operation = operation;
    }

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        switch (operation)
        {
            case "Max":
            {
                {
                if (((Canvas)MainWindow.jPanel2).que.size() > 1)
                   {
                       for (int i = MainWindow.number ; i < ((Canvas)MainWindow.jPanel2).que.size()-1; i++)
                       {
                           Collections.swap (((Canvas)MainWindow.jPanel2).que, i, i+1);
                       }
                       ((Singleton)MainWindow.jPanel3).save_number = ((Canvas)MainWindow.jPanel2).que.size()-1;
                       ((Singleton)MainWindow.jPanel3).NewChoice();
                   }
                else
                   {
                       System.out.println("В очереди один или меньше элементов");
                   }
                }
                break;
            }
            case "Min":
            {
                {
                   if (((Canvas)MainWindow.jPanel2).que.size() > 1)
                   {
                        for (int i = MainWindow.number ; i > 0; i--)
                        {
                           Collections.swap (((Canvas)MainWindow.jPanel2).que, i, i-1);
                        }
                       ((Singleton)MainWindow.jPanel3).save_number = 0;
                       ((Singleton)MainWindow.jPanel3).NewChoice();
                   }
                   else
                   {
                        System.out.println("В очереди один или меньше элементов");
                   }
    }
                break;
            }
            case "Up":
            {
                {
                  ((Singleton)MainWindow.jPanel3).save_number = MainWindow.number;
                if ((MainWindow.number+1)<((Canvas)MainWindow.jPanel2).que.size())
                {
                    Collections.swap(((Canvas)MainWindow.jPanel2).que, MainWindow.number, MainWindow.number+1);
                    //Поменяли выделенный элемент с нижним местами, теперь нужно присвоить конструкту новые значения и переименовать все фигуры в choiceMenu
                    ((Singleton)MainWindow.jPanel3).save_number++;
                    ((Singleton)MainWindow.jPanel3).NewChoice();
                    //присваиваем обновленное значение конструктору
                  //  Canvas.ConstructShape = base.que.get(save_number);
                }
                else 
                {
                   System.out.println("Выше уже некуда :C");
                }
    }
                break;
            }
            case "Down":
            {
                {
                   //Мне извесен MainWindow.number - позиция текущего элемента
                   ((Singleton)MainWindow.jPanel3).save_number = MainWindow.number;
                   if ((MainWindow.number-1)>-1)
                   {
                        Collections.swap(((Canvas)MainWindow.jPanel2).que, MainWindow.number, MainWindow.number-1);
                        //Поменяли выделенный элемент с нижним местами, теперь нужно присвоить конструкту новые значения и переименовать все фигуры в choiceMenu
                        ((Singleton)MainWindow.jPanel3).save_number--;
                        ((Singleton)MainWindow.jPanel3).NewChoice();
                        //присваиваем обновленное значение конструктору

                       // Canvas.ConstructShape = base.que.get(save_number);
                   }
                   else 
                   {
                       System.out.println("Достигнуто самое дно");
                   }
                }
                break;
            }
            case "Delete":
            {
                {                                      
                if (!que.isEmpty())
                {
                    //Удаление выбранного в Choice объекта
                 //   System.out.println("Объект #" + (ConstructShape.number - 1) + "удален");
                    System.out.println("Объект #" + (MainWindow.number+1) + "удален"); //number - реальная позиция фигуры в очереди
                    //  que.remove(ConstructShape.number - 1);
                    que.remove(MainWindow.number);//разобраться с удалением первой фигуры до изменений в очереди
                    //Смещение номеров в очереди фигур после удаления элемента
                  /*  for (int i = 0; i<que.size();i++)
                    {
                        ConstructShape = que.get(i);
                       // ConstructShape.number = i+1;
                       ((MainWindow)base).number = i;
                        que.set(i, ConstructShape);
                    }*/
                    //Поторное заполнение ChoiceMenu
                    MainWindow.ChoiceMenu.removeAll();
                    for (int i = 0; i<que.size();i++)
                    {
                        MainWindow.number = i;
                        ((Canvas)MainWindow.jPanel2).ActionShape = que.get(i);
                        ((Canvas)MainWindow.jPanel2).ActionShape.name = ((Canvas)MainWindow.jPanel2).ActionShape.type + " " + (MainWindow.number+1);//имя на 1 больше истинного значения
                       // MainWindow.ChoiceMenu.add(ActionShape.type + " " + ActionShape.number);
                       //MainWindow.ChoiceMenu.add(ActionShape.type + " " + ((MainWindow)base).number);
                       MainWindow.ChoiceMenu.add(((Canvas)MainWindow.jPanel2).ActionShape.name);
                    }
                    ConstructShape = null;
                    ((Canvas)MainWindow.jPanel2).ActionShape = null;
                    ((Canvas)MainWindow.jPanel2).draw(que, ((Canvas)MainWindow.jPanel2).ActionShape);
                    ((Canvas)MainWindow.jPanel2).repaint();     
                    //Повтор блока в ChoiceMenu 
                    ((Singleton)MainWindow.jPanel3).ChoiceMethod();
                }
                else 
                {
                    System.out.println("Нужно бодьше фигур");
                }
            }    
                if (que.isEmpty())
                {
                    ((Canvas)MainWindow.jPanel2).PickUpShape = false;
               MainWindow.LevelUp.setEnabled(false);
                MainWindow.LevelDown.setEnabled(false);
                 MainWindow.LevelMin.setEnabled(false);
                  MainWindow.LevelMax.setEnabled(false);
                    MainWindow.Delete.setEnabled(false);
                     MainWindow.Enter.setEnabled(false);
                }
                break;
            }
            case "Enter":
            {
                break;
            }
        }
    }
}
