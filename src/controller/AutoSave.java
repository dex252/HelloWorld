
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import view.*;


public class AutoSave extends Thread
{
    Timer timer = new Timer (15000, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae) 
            {
                base.serialisator.serialization (((Canvas)base.jPanel2).que, "/autosave/autosave");
                System.out.println("Автосохранение....");
            }
        });
    MainWindow base;
    public AutoSave (MainWindow main)
    {
       base = main;
    }
    
    @Override
    public void run()
    {     
        timer.start();
    }
    
}
