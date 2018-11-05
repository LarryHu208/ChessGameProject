import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JComponent;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.File;
import java.util.Random;
import java.lang.*;
import java.util.*;
import java.nio.file.Paths;
//import edu.calpoly.testy.Testy;

//import testy.*;
import edu.calpoly.spritely.AnimationFrame;
import edu.calpoly.spritely.SpriteWindow;
import edu.calpoly.spritely.Size;
import edu.calpoly.spritely.Tile;
import edu.calpoly.spritely.SolidColorTile;
import edu.calpoly.spritely.ImageTile;

public class MyGUI extends JComponent implements MyObserver {

    private static Dimension size = new Dimension(1000, 800);
    public static Random random = new Random();

    private final WinMain frame;
    private final MyModel model;


    private int modelUpdates = 0;

    private final Size gridSize = new Size(9, 9);

    public MyGUI(String name, MyModel model, Snapshot state, int color) {
        this.model = model;
        frame = new WinMain(state, color);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setLayout(new BorderLayout());
        // setPreferredSize(size);
    }

    public void startGUI() {
        // frame.add(this);
        // frame.pack();
        // frame.setVisible(true);
        try {
            frame.startX();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override public void notifyModelUpdated() {
        // Repaint the UI between 0 and 50 ms from now.  The delay
        // makes our threading/synchronization issues visible.
        repaint();

        modelUpdates++;
        try {
            frame.runOnce();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        if (modelUpdates > 9000) {
            model.detach(this);
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Snapshot state = new Snapshot();
        MyModel model = new MyModel();
        for (int i = 0; i < 2; i++) {
            MyGUI gui = new MyGUI("Love and Rockets", model, state, i);
            model.attach(gui);
            gui.startGUI();
        }
        model.run();
        System.exit(0);
    }
}
