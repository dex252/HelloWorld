
package view;

import controller.Instruments;
import controller.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InvalidObjectException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javax.swing.ImageIcon;
import javax.swing.event.ChangeEvent;

public class MainWindow extends javax.swing.JFrame {
    
    public Serializator serialisator = new Serializator();
    AutoSave autosave = new AutoSave (this);
    ImageIcon icon = new ImageIcon("src/resources/images.PNG");//Иконка кошака
    static public model.FactoryShape FS = new model.FactoryShape();//объект фабрики создания фигур
    static public double scale = 0, x_shift = 0, y_shift = 0;//scale - коэфф увеличения/сужения, x-,y-_shift - смещения экрана по х и у
    Graphics g = null;
    static public byte regim = 0;
    public static byte location = 0;//локация точки
    public static double transp_x, transp_y;//направление изменения точек внутри фигуры
    public static double loc_x, loc_y;//первый клик по локации (стоит запомнить)
    static public boolean Deformat = false;
    public static int number;//Номер активной фигуры
    boolean show = true; //видимость синглтона 
    public boolean typeColor = true; //метка о том, какая палитра будет вызвана (заливка границы или заливка фигуры)
    
    public MainWindow() 
    {
        initComponents();
        jPanel2.setBackground(Color.WHITE);
        jPanel2.setDoubleBuffered(true);//двойной буфер    
        ((Canvas)jPanel2).draw(((Canvas)jPanel2).que, ((Canvas)jPanel2).ActionShape);
        autosave.start();
        System.out.println("Поток ушёл на пробежку");
    }
    
    @SuppressWarnings("unchecked")
    
    private javax.swing.JButton Bezier;
    public javax.swing.JButton ColorBorder;
    public javax.swing.JButton ColorFill;
    public java.awt.Label ColorShape;
    private javax.swing.JButton Deformation;
    private javax.swing.JButton Ellipse;
    public javax.swing.JTextField GageBorder;
    private javax.swing.JButton Hand;
    public java.awt.Label Height;
    public javax.swing.JButton HideShow;
    private java.awt.Label Label7;
    private javax.swing.JButton Line;
    private javax.swing.JButton Polygon;
    private javax.swing.JButton Polyline;
    private javax.swing.JButton Rectangle;
    private javax.swing.JButton Regim;
    private javax.swing.JButton Triangle;
    public javax.swing.JButton TypeBorder;
    public javax.swing.JButton TypeFill;
    public java.awt.Label Width;
    public javax.swing.JTextField Xmax;
    public javax.swing.JTextField Xmin;
    public javax.swing.JTextField Ymax;
    public javax.swing.JTextField Ymin;
    public java.awt.Choice ChoiceMenu;
    public javax.swing.JButton Enter;
    public javax.swing.JButton Delete;
    public javax.swing.JButton LevelMax;
    public javax.swing.JButton LevelMin;
    public javax.swing.JButton LevelUp;
    public javax.swing.JButton LevelDown;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem NewFile;
    private javax.swing.JMenuItem Save;
    private javax.swing.JMenuItem SaveAs;
    private javax.swing.JMenuItem Load;
    private javax.swing.JMenuItem AutoLoad;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private java.awt.Label label1;
    private java.awt.Label label10;
    private java.awt.Label label11;
    private java.awt.Label label12;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private java.awt.Label label5;
    private java.awt.Label label6;
    private java.awt.Label label7;
    private java.awt.Label label8;
    private java.awt.Label label9;
    public javax.swing.JTextField name;
    public java.awt.Label type;
    public javax.swing.JLabel typeRegim;
    public javax.swing.JCheckBox visible;
    static public java.awt.Label shiftx;
    static public java.awt.Label shifty;
    public javax.swing.JColorChooser Colors;
    public javax.swing.JPanel TypeLine;
    public javax.swing.JPanel TypeSpace;
    public javax.swing.JButton Type1;
    public javax.swing.JButton Type2;
    public javax.swing.JButton TypeF1;
    public javax.swing.JButton TypeF2;
    public javax.swing.JButton TypeF3;
    public javax.swing.JButton TypeF4;
    public javax.swing.JButton TypeF5;
    
    private void initComponents() {

    jPanel1 = new javax.swing.JPanel();//Панель инструментов
    Line = new ShapeButton("Line", this);
    Triangle = new ShapeButton("Triangle", this);
    Ellipse = new ShapeButton("Ellipse", this);
    Rectangle = new ShapeButton("Rectangle", this);
    Polygon = new ShapeButton("Polygon", this);
    Bezier = new ShapeButton("Bezier", this);
    Polyline = new ShapeButton("BreakLine", this);
    Regim = new Instruments("Regim", this);
    Hand = new Instruments("Hand", this);
    Deformation = new Instruments("Deformation", this);
    typeRegim = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    jSeparator4 = new javax.swing.JSeparator();
    jPanel2 = new Canvas (this);
    jPanel3 = new Singleton(this);//Панель свойств
    ChoiceMenu = new java.awt.Choice();
    label1 = new java.awt.Label();
    label2 = new java.awt.Label();
    type = new java.awt.Label();
    label4 = new java.awt.Label();
    visible = new javax.swing.JCheckBox();
    name = new javax.swing.JTextField();
    label3 = new java.awt.Label();
    label5 = new java.awt.Label();
    label6 = new java.awt.Label();
    Height = new java.awt.Label();
    Xmin = new javax.swing.JTextField(3);
    Ymin = new javax.swing.JTextField(3);
    Label7 = new java.awt.Label();
    Width = new java.awt.Label();
    Xmax = new javax.swing.JTextField(3);
    label10 = new java.awt.Label();
    label11 = new java.awt.Label();
    Ymax = new javax.swing.JTextField(3);
    jSeparator1 = new javax.swing.JSeparator();
    label7 = new java.awt.Label();
    label9 = new java.awt.Label();
    jSeparator2 = new javax.swing.JSeparator();
    TypeBorder = new SmileShape("TypeBorder", this);
    ColorBorder = new SmileShape("ColorBorder", this);
    GageBorder = new javax.swing.JTextField();
    label12 = new java.awt.Label();
    TypeFill = new SmileShape("TypeFill", this);
    ColorShape = new java.awt.Label();
    ColorFill = new SmileShape("ColorFill", this);
    jSeparator3 = new javax.swing.JSeparator();
    Enter = new Settings("Enter", this);
    LevelMax = new Settings("Max", this);
    LevelMin = new Settings("Min", this);
    LevelUp = new Settings("Up", this);
    LevelDown = new Settings("Down", this);
    Delete = new Settings("Delete", this);
    label8 = new java.awt.Label();
    HideShow = new Instruments("Show", this);
    jMenuBar1 = new javax.swing.JMenuBar();
    fileMenu = new javax.swing.JMenu();
    NewFile = new javax.swing.JMenuItem("Создать");
    Save= new javax.swing.JMenuItem("Сохранить");
    SaveAs= new javax.swing.JMenuItem("Сохранить как");
    Load= new javax.swing.JMenuItem("Открыть");
    AutoLoad= new javax.swing.JMenuItem("Загрузить autosave");
    shiftx = new java.awt.Label(); 
    shifty = new java.awt.Label();
    TypeLine = new javax.swing.JPanel();
    TypeSpace = new javax.swing.JPanel();
    Colors = new javax.swing.JColorChooser();
    Type1 = new javax.swing.JButton();
    Type2 = new javax.swing.JButton();
    TypeF1 = new javax.swing.JButton();
    TypeF2 = new javax.swing.JButton();
    TypeF3 = new javax.swing.JButton();
    TypeF4 = new javax.swing.JButton();
    TypeF5 = new javax.swing.JButton();
    
    TypeSpace.setSize(60, 160);
    TypeSpace.setLocation(87,360);
    TypeSpace.setBackground(Color.gray);
    TypeSpace.setVisible(false);
    jPanel3.add(TypeSpace);
   
    TypeF1.setSize(36,40);
    TypeF1.setLocation(0,0);
    TypeF1.setIcon (new javax.swing.ImageIcon(getClass().getResource("/resources/TypeF1.png")));
    TypeSpace.add(TypeF1);
    TypeF1.addActionListener(new ActionListener (){
        public void actionPerformed(ActionEvent e)
            {
              TypeSpace.setVisible(false);
              ((SmileShape)TypeFill).hide = false;
              if ( ((Canvas)jPanel2).ConstructShape != null)
                {
                    ((Canvas)jPanel2).ConstructShape.TypeFill = 1;
                     TypeFill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/TypeF1.png")));
                }
              ((Canvas)jPanel2).DrawOutside();
            }
        });
    TypeF2.setSize(36,40);
    TypeF2.setLocation(0,0);
    TypeF2.setIcon (new javax.swing.ImageIcon(getClass().getResource("/resources/TypeF2.png")));
    TypeSpace.add(TypeF2);
    TypeF2.addActionListener(new ActionListener (){
        public void actionPerformed(ActionEvent e)
            {
              TypeSpace.setVisible(false);
              ((SmileShape)TypeFill).hide = false;
              if ( ((Canvas)jPanel2).ConstructShape != null)
                {
                    ((Canvas)jPanel2).ConstructShape.TypeFill = 2;
                     TypeFill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/TypeF2.png")));
                }
              ((Canvas)jPanel2).DrawOutside();
            }
        });
    TypeF3.setSize(36,40);
    TypeF3.setLocation(0,0);
    TypeF3.setIcon (new javax.swing.ImageIcon(getClass().getResource("/resources/TypeF3.png")));
    TypeSpace.add(TypeF3);
    TypeF3.addActionListener(new ActionListener (){
        public void actionPerformed(ActionEvent e)
            {
              TypeSpace.setVisible(false);
              ((SmileShape)TypeFill).hide = false;
              if ( ((Canvas)jPanel2).ConstructShape != null)
                {
                    ((Canvas)jPanel2).ConstructShape.TypeFill = 3;
                     TypeFill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/TypeF3.png")));
                }
              ((Canvas)jPanel2).DrawOutside();
            }
        });
    TypeF4.setSize(36,40);
    TypeF4.setLocation(0,0);
    TypeF4.setIcon (new javax.swing.ImageIcon(getClass().getResource("/resources/TypeF4.png")));
    TypeSpace.add(TypeF4);
    TypeF4.addActionListener(new ActionListener (){
        public void actionPerformed(ActionEvent e)
            {
              TypeSpace.setVisible(false);
              ((SmileShape)TypeFill).hide = false;
              if ( ((Canvas)jPanel2).ConstructShape != null)
                {
                    ((Canvas)jPanel2).ConstructShape.TypeFill = 4;
                     TypeFill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/TypeF4.png")));
                }
              ((Canvas)jPanel2).DrawOutside();
            }
        });
    TypeF5.setSize(36,40);
    TypeF5.setLocation(0,0);
    TypeF5.setIcon (new javax.swing.ImageIcon(getClass().getResource("/resources/TypeF5.png")));
    TypeSpace.add(TypeF5);
    TypeF5.addActionListener(new ActionListener (){
        public void actionPerformed(ActionEvent e)
            {
              TypeSpace.setVisible(false);
              ((SmileShape)TypeFill).hide = false;
              if ( ((Canvas)jPanel2).ConstructShape != null)
                {
                    ((Canvas)jPanel2).ConstructShape.TypeFill = 5;
                     TypeFill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/TypeF5.png")));
                }
              ((Canvas)jPanel2).DrawOutside();
            }
        });
    
    
    TypeLine.setSize(85, 70);
    TypeLine.setLocation(115,220);
    TypeLine.setBackground(Color.gray);
    TypeLine.setVisible(false);
    jPanel3.add(TypeLine);
    
    Type1.setSize(36,40);
    Type1.setLocation(0,0);
    Type1.setText("Сплошная");
    TypeLine.add(Type1);
    Type1.addActionListener(new ActionListener (){
        public void actionPerformed(ActionEvent e)
            {
              TypeLine.setVisible(false);
              ((SmileShape)TypeBorder).hide = false;
              if ( ((Canvas)jPanel2).ConstructShape != null)
                {
                    ((Canvas)jPanel2).ConstructShape.TypeBorder = true;
                     TypeBorder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/splohnaya.png")));
                }
              ((Canvas)jPanel2).DrawOutside();
            }
        });
    
    Type2.setSize(36,40);
    Type2.setLocation(0,40);
    Type2.setText("Пунктир");
    TypeLine.add(Type2);
    Type2.addActionListener(new ActionListener (){
        public void actionPerformed(ActionEvent e)
            {
               TypeLine.setVisible(false);
               ((SmileShape)TypeBorder).hide = false;
               if ( ((Canvas)jPanel2).ConstructShape != null)
                {
                    ((Canvas)jPanel2).ConstructShape.TypeBorder = false;
                     TypeBorder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/punctir.png")));
                }
               ((Canvas)jPanel2).DrawOutside();
            }
        });
        
      this.add(Colors);
      Colors.setLocation(521, 340);
      Colors.setSize(420, 240);
      Colors.setVisible(false);
      Colors.setPreviewPanel(new javax.swing.JPanel());
         
      Colors.getSelectionModel().addChangeListener(new javax.swing.event.ChangeListener() 
        {
            @Override
            public void stateChanged(ChangeEvent ce)
            {
                if (((Canvas)jPanel2).ConstructShape != null)
                {
                    if (typeColor)
                    {
                    ((Canvas)jPanel2).ConstructShape.ColorBorder = Colors.getColor();
                    ColorBorder.setBackground(Colors.getColor());
                    }
                    else
                    {
                    ((Canvas)jPanel2).ConstructShape.ColorFill = Colors.getColor();
                    ColorFill.setBackground(Colors.getColor());
                    }
                  
                    Colors.setVisible(false);
                    ((SmileShape)ColorBorder).hide = false;
                    ((SmileShape)ColorFill).hide = false;
                    ((Canvas)jPanel2).DrawOutside();
                }  
            }
        }
        );
      
        Xmax.setEditable(false);
           Xmin.setEditable(false);
              Ymax.setEditable(false);
                 Ymin.setEditable(false);
         
        shiftx.setSize(34,20);
        shiftx.setBackground(Color.white);
        shiftx.setLocation(195,370);
        shiftx.setText(""+x_shift);
        jPanel1.add(shiftx);
         
        shifty.setSize(34,20);
        shifty.setBackground(Color.white);
        shifty.setLocation(195,392);
        shifty.setText(""+y_shift);
        jPanel1.add(shifty);
         
        fileMenu.add(NewFile);
        fileMenu.add(Load);
        fileMenu.addSeparator();
        fileMenu.add(Save);
        fileMenu.add(SaveAs);
        fileMenu.addSeparator();
        fileMenu.add(AutoLoad);
        
        visible.addItemListener(new ItemListener() 
        {
            public void itemStateChanged(ItemEvent e) 
            {
                ((Singleton)jPanel3).CheckBox();
                ((Canvas)(jPanel2)).DrawOutside();
            }
        }
        );
         
      name.addKeyListener(new java.awt.event.KeyAdapter()
      {
          public void keyPressed(KeyEvent e)
          {
              int key = e.getKeyCode();
               if (key == KeyEvent.VK_ENTER)
               { 
                   ((Singleton)jPanel3).SetName();
               }
          }
      });
      
       GageBorder.addKeyListener(new java.awt.event.KeyAdapter()
      {
          public void keyPressed(KeyEvent e)
          {
              int key = e.getKeyCode();
               if (key == KeyEvent.VK_ENTER)
               { 
                   ((Singleton)jPanel3).SetBorderSize();
               }
          }
      });
        NewFile.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent e)
                {
                 System.out.println("Новый файл");
            }
        });
        
        Load.addActionListener(new ActionListener ()
        {
            public void actionPerformed(ActionEvent e)
                {
                    try 
                    {
                        System.out.println("Открыть");
                        ((Canvas)jPanel2).que = serialisator.deserialization ("Save");
                        ChoiceMenu.removeAll();
                        ((Canvas)jPanel2).que.forEach((que1) ->
                         {
                          ChoiceMenu.add(que1.name);
                          ((Canvas)jPanel2).ConstructShape = que1;
                         }); 
                        ((Canvas)jPanel2).DrawOutside();
                    } 
                    catch (InvalidObjectException ex) 
                    {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
               }
        });
        
        Save.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent e)
                {
                 System.out.println("Сохранить");
                 serialisator.serialization (((Canvas)jPanel2).que, "Save");
            }
        });
        
        SaveAs.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent e)
                {
                 System.out.println("Сохранить как");
            }
        });
        
        AutoLoad.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        System.out.println("Автозагрузка");
                        //serialisator.serialization (((Canvas)jPanel2).que, "/autosave/autosave");
                        ((Canvas)jPanel2).que = serialisator.deserialization ("/autosave/autosave");
                        ChoiceMenu.removeAll();
                        ((Canvas)jPanel2).que.forEach((que1) ->
                        {
                        ChoiceMenu.add(que1.name);
                        ((Canvas)jPanel2).ConstructShape = que1;
                        }); 
                        ((Canvas)jPanel2).DrawOutside();
                        } 
                    catch (InvalidObjectException ex) 
                    {
                       // Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("Autosave не найден");
                    }
            }
        });
        
        this.setTitle("Которедактор - Безымянный");
   
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(icon.getImage());
        setLocation(new java.awt.Point(180, 30));
        setMaximumSize(new java.awt.Dimension(1200, 625));
        setMinimumSize(new java.awt.Dimension(1200, 625));
        setPreferredSize(new java.awt.Dimension(1200, 625));
        setResizable(false);

        Line.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Line.png"))); // NOI18N

        Triangle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Triangle.png"))); // NOI18N

        Ellipse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Ellipse.png"))); // NOI18N

        Rectangle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Rectangle.png"))); // NOI18N

        Polygon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Polygon.png"))); // NOI18N

        Bezier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Bezier.png"))); // NOI18N

        Polyline.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Polyline.png"))); // NOI18N

        Regim.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        Regim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Regim.png"))); // NOI18N
        Regim.setText("Construct");

        Hand.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        Hand.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Hand.png"))); // NOI18N
        Hand.setText("Hand");

        Deformation.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        Deformation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/deformation.png"))); // NOI18N
        Deformation.setText("Deformation");

        typeRegim.setBackground(new java.awt.Color(255, 255, 255));
        typeRegim.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        typeRegim.setText("Графика");

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel3.setText("Режим:");

        LevelUp.setEnabled(false);
        LevelDown.setEnabled(false);
        LevelMin.setEnabled(false);
        LevelMax.setEnabled(false);
        Delete.setEnabled(false);
        Enter.setEnabled(false);
        visible.setEnabled(false);
        ColorBorder.setEnabled(false);
        TypeBorder.setEnabled(false);
        GageBorder.setEnabled(false);
       
        
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Line, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Triangle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(Rectangle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Ellipse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(Polyline, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(Bezier, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Polygon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(Deformation, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(Hand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Regim, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(jSeparator4)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(typeRegim, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(Line, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Triangle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Rectangle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Ellipse, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Polyline, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Bezier, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Polygon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(typeRegim, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Deformation, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Hand, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Regim, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        label1.setText("Имя фигуры:");

        label2.setText("Тип фигуры:");


        label4.setText("Видимость:");

        label3.setText("Высота:");

        label5.setText("Xmin:");

        label6.setText("Ymin:");

        Height.setName("Height"); // NOI18N
        Height.setText("0000");

        Xmin.setText("0000");

        Ymin.setText("0000");

        Label7.setText("Ширина:");

        Width.setName("Hight"); // NOI18N
        Width.setText("0000");

        Xmax.setText("0000");

        label10.setText("Xmax:");

        label11.setText("Ymax:");

        Ymax.setText("0000");

        label7.setText("Тип границы:");

        label9.setText("Цвет границы:");

        //TypeBorder.setText(" ");
         TypeBorder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/splohnaya.png")));

        ColorBorder.setText(" ");

        label12.setText("Тип заливки:");
        
        GageBorder.setSize(80, 20);

        TypeFill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/TypeF1.png")));
        TypeFill.setEnabled(false);

        ColorShape.setText("Цвет заливки:");

        ColorFill.setText(" ");
        ColorFill.setEnabled(false);

        Enter.setText("Применить");

        LevelMax.setText("Level MAX");

        LevelMin.setText("Level MIN");

        LevelUp.setText("Level UP");

        LevelDown.setText("Level DOWN");

        Delete.setText("Удалить");

        label8.setText("Толщина границы:");

        HideShow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Hide.png"))); // NOI18N
        HideShow.setToolTipText("");

        
       
        
        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ChoiceMenu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jSeparator2)
            .addComponent(jSeparator3)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(visible)
                            .addComponent(type, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(83, 83, 83))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(name)
                        .addContainerGap())))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Height, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Xmin))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Ymin, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(label10, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Xmax))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(label11, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Ymax, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(Label7, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Width, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(label12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TypeFill, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(ColorShape, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(ColorFill, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(LevelMax, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                                .addComponent(LevelUp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(Enter))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(LevelDown, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(LevelMin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(TypeBorder, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(11, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GageBorder, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(ColorBorder, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               /* .addComponent(Hide, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)*/)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(ChoiceMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(type, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(visible))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Height, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Xmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Ymin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Width, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Xmax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Ymax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(20, 20, 20)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3))
                    .addComponent(TypeBorder, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ColorBorder)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                      //  .addGap(2, 2, 2)
                      /*  .addComponent(Hide)*/))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GageBorder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(label12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TypeFill))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ColorShape, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ColorFill))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Enter)
                    .addComponent(Delete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LevelMax)
                    .addComponent(LevelMin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LevelUp)
                    .addComponent(LevelDown))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        
        jPanel1.add (HideShow);
        HideShow.setLocation (190,325);
        HideShow.setSize(43,40); 
        HideShow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Hide.png"))); // NOI18N
        HideShow.setToolTipText("");
        
        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 713, Short.MAX_VALUE)
               )
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           
        );

        fileMenu.setText("Файл");
        jMenuBar1.add(fileMenu);


        setJMenuBar(jMenuBar1);
        
        

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        
        ChoiceMenu.addItemListener(new java.awt.event.ItemListener() 
        {
            public void itemStateChanged(java.awt.event.ItemEvent evt) 
            {
                ((Singleton)(jPanel3)).ChoiceMenuItemStateChanged(evt);
            }
        });
        setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
        public void windowClosing(WindowEvent evt)
        {
           autosave.stop();
           System.exit(0);
        }
});
        pack();
    }//
   /*
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new Instruments (this);
        Line = new javax.swing.JButton();
        Triangle = new javax.swing.JButton();
        Ellipse = new javax.swing.JButton();
        Rectangle = new javax.swing.JButton();
        Polygon = new javax.swing.JButton();
        Bezier = new javax.swing.JButton();
        Polyline = new javax.swing.JButton();
        Regim = new javax.swing.JButton();
        Hand = new javax.swing.JButton();
        Deformation = new javax.swing.JButton();
        typeRegim = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        HideShow = new javax.swing.JButton();
        jPanel2 = new Canvas (this);
        jPanel3 = new Singleton (this);
        choice = new java.awt.Choice();
        label1 = new java.awt.Label();
        label2 = new java.awt.Label();
        type = new java.awt.Label();
        label4 = new java.awt.Label();
        visible = new javax.swing.JCheckBox();
        name = new javax.swing.JTextField();
        label3 = new java.awt.Label();
        label5 = new java.awt.Label();
        label6 = new java.awt.Label();
        Height = new java.awt.Label();
        Xmin = new javax.swing.JTextField();
        Ymin = new javax.swing.JTextField();
        Label7 = new java.awt.Label();
        Width = new java.awt.Label();
        Xmax = new javax.swing.JTextField();
        label10 = new java.awt.Label();
        label11 = new java.awt.Label();
        Ymax = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        label7 = new java.awt.Label();
        label9 = new java.awt.Label();
        jSeparator2 = new javax.swing.JSeparator();
        TypeBorder = new javax.swing.JButton();
        ColorBorder = new javax.swing.JButton();
        GageBorder = new javax.swing.JTextField();
        label12 = new java.awt.Label();
        TypeFill = new javax.swing.JButton();
        ColorShape = new java.awt.Label();
        ColorFill = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        label8 = new java.awt.Label();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(icon.getImage());
        setLocation(new java.awt.Point(180, 30));
        setMinimumSize(new java.awt.Dimension(1200, 605));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        Line.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Line.png"))); // NOI18N

        Triangle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Triangle.png"))); // NOI18N

        Ellipse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Ellipse.png"))); // NOI18N

        Rectangle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Rectangle.png"))); // NOI18N

        Polygon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Polygon.png"))); // NOI18N

        Bezier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Bezier.png"))); // NOI18N

        Polyline.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Polyline.png"))); // NOI18N

        Regim.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        Regim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Regim.png"))); // NOI18N
        Regim.setText("Regim");

        Hand.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        Hand.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Hand.png"))); // NOI18N
        Hand.setText("Hand");

        Deformation.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        Deformation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/deformation.png"))); // NOI18N
        Deformation.setText("Deformation");

        typeRegim.setBackground(new java.awt.Color(255, 255, 255));
        typeRegim.setFont(new java.awt.Font("Comic Sans MS", 0, 16)); // NOI18N
        typeRegim.setText("Лапа");

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel3.setText("Режим:");

        HideShow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Hide.png"))); // NOI18N
        HideShow.setToolTipText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Line, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Triangle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(Rectangle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Ellipse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(Polyline, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(Bezier, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Polygon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(Deformation, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(Hand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Regim, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(jSeparator4)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(typeRegim, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(HideShow, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(Line, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Triangle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Rectangle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Ellipse, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Polyline, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Bezier, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Polygon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(typeRegim, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(HideShow)))
                .addGap(18, 18, 18)
                .addComponent(Deformation, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Hand, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Regim, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        label1.setText("Имя фигуры:");

        label2.setText("Тип фигуры:");

        type.setText("type");

        label4.setText("Видимость:");

        visible.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                visibleMouseReleased(evt);
            }
        });

        name.setText("name");

        label3.setText("Высота:");

        label5.setText("Xmin:");

        label6.setText("Ymin:");

        Height.setName("Height"); // NOI18N
        Height.setText("0000");

        Xmin.setText("0000");

        Ymin.setText("0000");

        Label7.setText("Ширина:");

        Width.setName("Hight"); // NOI18N
        Width.setText("0000");

        Xmax.setText("0000");

        label10.setText("Xmax:");

        label11.setText("Ymax:");

        Ymax.setText("0000");

        label7.setText("Тип границы:");

        label9.setText("Цвет границы:");

        TypeBorder.setText(" ");

        ColorBorder.setText(" ");

        GageBorder.setText("000");

        label12.setText("Тип заливки:");

        TypeFill.setText(" ");

        ColorShape.setText("Цвет заливки:");

        ColorFill.setText(" ");

        jButton2.setText("Применить");

        jButton4.setText("Level MAX");

        jButton5.setText("Level MIN");

        jButton6.setText("Level UP");

        jButton7.setText("Level DOWN");

        jButton3.setText("Удалить");

        label8.setText("Толщина границы:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(choice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jSeparator2)
            .addComponent(jSeparator3)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(visible)
                            .addComponent(type, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(83, 83, 83))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(name)
                        .addContainerGap())))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Height, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Xmin))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Ymin, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(label10, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Xmax))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(label11, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Ymax, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(Label7, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Width, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(label12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TypeFill, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(ColorShape, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(ColorFill, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                                .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jButton2))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(TypeBorder, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(11, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GageBorder, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(ColorBorder, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(choice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(type, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(visible))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Height, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Xmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Ymin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Width, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Xmax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Ymax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(20, 20, 20)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3))
                    .addComponent(TypeBorder, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ColorBorder))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GageBorder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(label12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TypeFill))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ColorShape, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ColorFill))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton7))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 713, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing

    private void visibleMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_visibleMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_visibleMouseReleased
*/
    /**
     * @param args the command line arguments
     */
             
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }
/*
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Bezier;
    private javax.swing.JButton ColorBorder;
    private javax.swing.JButton ColorFill;
    private java.awt.Label ColorShape;
    private javax.swing.JButton Deformation;
    private javax.swing.JButton Ellipse;
    private javax.swing.JTextField GageBorder;
    private javax.swing.JButton Hand;
    private java.awt.Label Height;
    private javax.swing.JButton HideShow;
    private java.awt.Label Label7;
    private javax.swing.JButton Line;
    private javax.swing.JButton Polygon;
    private javax.swing.JButton Polyline;
    private javax.swing.JButton Rectangle;
    private javax.swing.JButton Regim;
    private javax.swing.JButton Triangle;
    private javax.swing.JButton TypeBorder;
    private javax.swing.JButton TypeFill;
    private java.awt.Label Width;
    private javax.swing.JTextField Xmax;
    private javax.swing.JTextField Xmin;
    private javax.swing.JTextField Ymax;
    private javax.swing.JTextField Ymin;
    private java.awt.Choice choice;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private java.awt.Label label1;
    private java.awt.Label label10;
    private java.awt.Label label11;
    private java.awt.Label label12;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private java.awt.Label label5;
    private java.awt.Label label6;
    private java.awt.Label label7;
    private java.awt.Label label8;
    private java.awt.Label label9;
    private javax.swing.JTextField name;
    private java.awt.Label type;
    private javax.swing.JLabel typeRegim;
    private javax.swing.JCheckBox visible;
    // End of variables declaration//GEN-END:variables
 */
    
}
