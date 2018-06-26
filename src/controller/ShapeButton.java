package controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import model.FactoryShape;
import view.MainWindow;


public class ShapeButton extends JButton implements ActionListener
{
    MainWindow base;
    public String type = "Shape";
    
    public ShapeButton (String type, MainWindow base)//MainWindow - наше рабочее окно
    {
        this.base = base;
        this.type = type;
        this.addActionListener(this);
        //Инициализация класса фигуры для её дальнейшего добавления в очередь
        try {
            Class classe = Class.forName("model." + type);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ShapeButton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Цвет линий и текста при нажатии на кнопку
    @Override
    protected void paintComponent(Graphics g)
    {  
       if(getModel().isArmed())
       { 
          g.setColor(Color.GREEN);
          setForeground(Color.red);
       }
       else
       {
          g.setColor(getBackground());
          setForeground(Color.black);
       }
       super.paintComponent(g);

    }
    
    //Связывание кнопки с фигурой при нажатии
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        model.Shape Shape = null;
        Shape = FactoryShape.input(this.type);
        base.ActionShape=Shape;
    }
}