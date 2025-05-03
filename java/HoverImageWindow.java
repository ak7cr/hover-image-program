import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class HoverImageWindow extends JFrame {
    public HoverImageWindow() throws IOException {
        setTitle("Hover-to-Reveal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // load your images
        BufferedImage bg    = ImageIO.read(new File("images/background.jpg"));
        BufferedImage img1  = ImageIO.read(new File("images/overlay1.png"));
        BufferedImage img2  = ImageIO.read(new File("images/overlay2.png"));
        BufferedImage img3  = ImageIO.read(new File("images/overlay3.png"));
        BufferedImage img4  = ImageIO.read(new File("images/overlay4.png"));
        BufferedImage img5  = ImageIO.read(new File("images/overlay5.png"));

        // define your 5 hot-spots
        // (example positions & sizes—tweak to fit your layout)
        Rectangle[] spots = new Rectangle[] {
            new Rectangle( 50,  60, 100, 80),
            new Rectangle(200,  50, 120, 90),
            new Rectangle(370,  70,  80, 80),
            new Rectangle( 80, 200, 150,100),
            new Rectangle(300, 220, 100,120)
        };

        // associate each spot with one overlay image
        BufferedImage[] overlays = new BufferedImage[] { img1, img2, img3, img4, img5 };

        // custom panel
        JPanel panel = new JPanel() {
            int hoverIndex = -1;  // which overlay to draw, -1 → none

            {
                // listen for mouse movement
                addMouseMotionListener(new MouseMotionAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        Point p = e.getPoint();
                        hoverIndex = -1;
                        for (int i = 0; i < spots.length; i++) {
                            if (spots[i].contains(p)) {
                                hoverIndex = i;
                                break;
                            }
                        }
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // draw background
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);

                // optionally, draw outlines of spots for debugging
                // g.setColor(new Color(255,0,0,80));
                // for (Rectangle r : spots) g.fill(r);

                // draw overlay if hovering
                if (hoverIndex >= 0) {
                    Rectangle r = spots[hoverIndex];
                    BufferedImage ov = overlays[hoverIndex];
                    // scale overlay to spot size (if desired):
                    g.drawImage(ov, r.x, r.y, r.width, r.height, null);
                }
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(bg.getWidth(), bg.getHeight());
            }
        };

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new HoverImageWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
