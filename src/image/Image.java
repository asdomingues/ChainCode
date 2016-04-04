package image;

import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Image {

    private File inFile;
    protected BufferedImage image;
    protected int height;
    protected int width;
    protected int firstPixel;
    protected ArrayList chainCode;
    
    public Image (String name) throws IOException {
        inFile = new File (name);
        try { image = ImageIO.read (inFile); }
        catch (IOException | IllegalArgumentException ex){
            throw new IOException ("Ocorreu um erro ao ler a imagem.");
        }

        height = width = firstPixel = -1;
        chainCode = new ArrayList ();
    }

    @Override
    public String toString () {
        System.out.println ("");
    }
}