package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.*;


public class Instruments extends JButton implements ActionListener
{
    MainWindow MainWindow;
    String action;
    boolean hide = false;
    public Instruments(String action, MainWindow main) 
    {
       this.action = action;
       MainWindow = main;
       this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        switch (action)
        {
            case "Show": //Скрыть панель свойств
            {
                if (hide)
                {
                   hide = false;
                   MainWindow.jPanel3.setVisible(true);
                   MainWindow.HideShow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Hide.png"))); // NOI18N
                   break;
                }
                else
                {
                   hide = true;
                   MainWindow.jPanel3.setVisible(false);
                   MainWindow.HideShow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Show.png"))); // NOI18N
                   break;
                }     
            }
            case "Regim":
            {
               if (MainWindow.regim==0)
                {
                    MainWindow.regim = 1;
                    MainWindow.typeRegim.setText ("Конструктор"); 
                }
                else 
                {
                    MainWindow.regim = 0;
                    MainWindow.typeRegim.setText ("Графика"); 

                    if (((Canvas)MainWindow.jPanel2).ConstructShape != null)
                    {
                     //  que.set(que.size()-1, ConstructShape); //Убрал, т.к. конфликтует с синглтоном
                       ((Canvas)MainWindow.jPanel2).draw(((Canvas)MainWindow.jPanel2).que, ((Canvas)MainWindow.jPanel2).ConstructShape);
                       repaint();
                    }
                }
                break;
            }
            case "Hand":
            {
                {
                    if ((MainWindow.regim==0)||(MainWindow.regim==1))
                    {
                        MainWindow.regim = 2;
                        MainWindow.typeRegim.setText ("Лапа"); 
                    }
                    else 
                    {
                        MainWindow.regim = 0;
                        MainWindow.typeRegim.setText ("Графика"); 
                    }
                }
                 break;
            }
            case "Deformation":
            {
                if (MainWindow.regim!=4)
                {
                    MainWindow.regim = 4;
                    MainWindow.typeRegim.setText ("Точки"); 
                   ((Canvas)MainWindow.jPanel2).draw(((Canvas)MainWindow.jPanel2).que, ((Canvas)MainWindow.jPanel2).ConstructShape);
                    repaint();
                }
                else 
                {
                  MainWindow.regim = 0;
                  MainWindow.typeRegim.setText ("Графика"); 
                  ((Canvas)MainWindow.jPanel2).draw(((Canvas)MainWindow.jPanel2).que, ((Canvas)MainWindow.jPanel2).ConstructShape);
                  repaint();
                }
                break;
            }
        }
        
    }
    
}
