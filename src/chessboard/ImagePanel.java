package chessboard;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

    private BufferedImage image;

    public ImagePanel() {
        try {
            image = ImageIO.read(new File("./resources/images/background.png"));
        } catch (IOException ex) {
            System.out.println("no file");
        }
    }
    public ImagePanel(String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException ex) {
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.drawImage(image, 0, 0, this);
    }
}