package in.mcxiv.jpsd;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Supplier;

public class ImageDisplayUtility {

    static int size = 150;

    public static Runnable display(String name, Supplier<BufferedImage> image, int x, int y) {
        JFrame frame = new JFrame();
        frame.setTitle(name);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton button = new JButton(createIcon(image.get()));
        button.setToolTipText(name);
        Runnable imageUpdator = () -> button.setIcon(createIcon(image.get()));
        button.addActionListener(e -> imageUpdator.run());
        frame.add(button);

        frame.setAlwaysOnTop(true);
        frame.pack();
        frame.setLocation(frame.getWidth() * x, frame.getHeight() * y);
        frame.setVisible(true);

        return imageUpdator;
    }

    public static void display(String name, BufferedImage image, int x, int y) {
        new JFrame() {{
            setTitle(name);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            add(new JButton(createIcon(image)) {{
                setToolTipText(name);
            }});

            setAlwaysOnTop(true);
            pack();
            setLocation(getWidth() * x, getHeight() * y);
            setVisible(true);
        }};
    }

    private static ImageIcon createIcon(BufferedImage image) {
        BufferedImage show = new BufferedImage(size, size, 2);
        Graphics2D graphics = show.createGraphics();
        graphics.drawImage(image, 0, 0, size, size, null);
        graphics.dispose();
        return new ImageIcon(show);
    }

}
