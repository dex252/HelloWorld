/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Shape;
import static view.MainWindow.*;

/**
 *
 * @author dex25
 */
public class Shift {
    static double x, y;
    static double x_memory;
    static double y_memory;
    //Смещение фигур при перетаскивании полотна
    public static void FirstClick (int x1, int y1)
    {
        x_memory = x_shift;
        y_memory = y_shift;
        x = x1;
        y = y1;
    }
    
    public static void MovedClick (int x1, int y1)
    {
        double x_move, y_move;
        x_move = x1 - x;
        y_move = y1 - y;
        //ActionShape.Check();
        x_shift = x_memory - x_move;
        y_shift = y_memory - y_move;
        if (x_shift > 0) x_shift = 0;
        if (y_shift > 0) y_shift = 0;

    }
}
