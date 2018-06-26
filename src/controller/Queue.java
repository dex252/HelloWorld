
package controller;

import java.io.Serializable;
import java.util.ArrayList;
import model.Shape;

public class Queue extends ArrayList<Shape> implements Serializable
{
    ArrayList<Shape> QS = new ArrayList(); //Queue Shape
    //Добавление типа фигуры из кнопки в очередь типов
    public void get (Shape sh)
    {
        QS.add(sh);
    }
}
