package chaincode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by misiuk on 11/04/16.
 */
public abstract class ImageES {
    private File inFile;
    protected BufferedImage image;
    protected int shapeHeight;
    protected int shapeWidth;
    protected int firstPixel;
    protected ArrayList<Integer> chainCode;

    /**
     *
     * @param name
     * @throws IOException
     */
    public ImageES (String name) throws IOException {
        inFile = new File (name);
        try { image = ImageIO.read (inFile); }
        catch (IOException | IllegalArgumentException ex){
            throw new IOException ("Ocorreu um erro ao ler a imagem.");
        }

        shapeHeight = shapeWidth = firstPixel = -1;
        chainCode = new ArrayList<Integer> ();
    }
}
