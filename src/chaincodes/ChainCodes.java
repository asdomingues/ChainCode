package chaincodes;

import java.io.File;
import java.awt.image.BufferedImage;

public class ChainCodes {

    File inFile;
    BufferedImage image;
    
    public ChainCodes (String name) {
        inFile = new File (name);
    }
    
}