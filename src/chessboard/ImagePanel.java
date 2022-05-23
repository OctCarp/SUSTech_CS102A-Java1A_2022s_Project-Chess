package chessboard;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

    private Image image;

    public ImagePanel() {
        try {
            //FIXME: background image
            image = ImageIO.read(new File("./resources/images/background.png"));
            setOpaque(false);
        } catch (IOException ex) {
        }
    }

    public ImagePanel(String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException ex) {
        }
    }

    public ImagePanel(Image background) {
        image = background;
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image,0,0 ,this.getWidth(), this.getWidth(), this);
    }
}