/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import view.MainWindow;

/**
 *
 * @author dex25
 */
public class SmileShape extends JButton implements ActionListener
{
    MainWindow MainWindow;
    String action;
    boolean hide = false;
    public SmileShape(String action, MainWindow main) 
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
            case "ColorBorder":
            {
                if (hide) 
                {
                    MainWindow.Colors.setVisible(false);
                    hide = false;
                }
                else 
                {
                    MainWindow.Colors.setVisible(true);
                    hide = true;
                }
                
                break;
            }
        }
    }
}

