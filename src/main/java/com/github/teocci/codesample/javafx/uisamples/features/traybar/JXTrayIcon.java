package com.github.teocci.codesample.javafx.uisamples.features.traybar;


import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;


/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Aug-20
 */
public class JXTrayIcon extends TrayIcon
{
    private JPopupMenu menu;
    private static JDialog dialog;

    static {
        dialog = new JDialog((Frame) null);
        dialog.setUndecorated(true);
        dialog.setAlwaysOnTop(true);
    }

    private static PopupMenuListener popupListener = new PopupMenuListener()
    {
        public void popupMenuWillBecomeVisible(PopupMenuEvent e)
        {
        }

        public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
        {
            dialog.setVisible(false);
        }

        public void popupMenuCanceled(PopupMenuEvent e)
        {
            dialog.setVisible(false);
        }
    };


    public JXTrayIcon(Image image) throws UnsupportedOperationException
    {
        super(image);
        addMouseListener(new MouseAdapter()
        {
            public void mouseReleased(MouseEvent e)
            {
                showJPopupMenu(e);
            }
        });
    }

    protected void showJPopupMenu(MouseEvent e)
    {
        if (menu != null) {
            Dimension size = menu.getPreferredSize();
            showJPopupMenu(e.getX(), e.getY() - size.height);
        }
    }

    protected void showJPopupMenu(int x, int y)
    {
        dialog.setLocation(x, y);
        dialog.setVisible(true);
        menu.show(dialog.getContentPane(), 0, 0);
        // popup works only for focused windows
        dialog.toFront();
    }

    public JPopupMenu getJPopupMenu()
    {
        return menu;
    }

    public void setJPopupMenu(JPopupMenu menu)
    {
        if (this.menu != null) {
            this.menu.removePopupMenuListener(popupListener);
        }
        this.menu = menu;
        menu.addPopupMenuListener(popupListener);
    }

    private static void createGui()
    {
        try {
            JXTrayIcon tray = new JXTrayIcon(createImage());
            tray.setJPopupMenu(createJPopupMenu());
            SystemTray.getSystemTray().add(tray);
        } catch (Exception e) {
            System.err.println("TrayIcon Error");
            e.printStackTrace();
        }
    }

    static Image createImage()
    {
        BufferedImage i = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) i.getGraphics();
        g2.setColor(Color.RED);
        g2.fill(new Ellipse2D.Float(0, 0, i.getWidth(), i.getHeight()));
        g2.dispose();
        return i;
    }

    static JPopupMenu createJPopupMenu()
    {
        final JPopupMenu m = new JPopupMenu();
        m.add(new JMenuItem("Item 1"));
        m.add(new JMenuItem("Item 2"));
        JMenu submenu = new JMenu("Submenu");
        submenu.add(new JMenuItem("item 1"));
        submenu.add(new JMenuItem("item 2"));
        submenu.add(new JMenuItem("item 3"));
        m.add(submenu);
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
        m.add(exitItem);
        return m;
    }

    public static void main(String[] args) throws Exception
    {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                createGui();
            }
        });
    }
}