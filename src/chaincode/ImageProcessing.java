package chaincode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.*;

/**
 * Created by misiuk on 11/04/16.
 */
public class ImageProcessing extends Coordinates {

    public ImageProcessing (String name) throws IOException { super (name); }

    public int getWidth () {
        int x, y;
        int lineSum;
        int maxSum;

        for (maxSum = 0, y = 0; y < image.getHeight (); y++) {
            for (lineSum = 0, x = 0; x < image.getWidth (); x++) {
                lineSum += image.getRGB(x, y) == -1 ? 0 : 1;
            }

            maxSum = max (lineSum,maxSum);
        }

        return maxSum;
    }

    public int getHeight () {
        int x, y;
        int colSum;
        int maxSum;

        for (maxSum = 0, x = 0; x < image.getWidth(); x++) {
            for (colSum = 0, y = 0; y < image.getHeight(); y++) {
                colSum += image.getRGB(x, y) == -1 ? 0 : 1;
            }

            maxSum = max(colSum,maxSum);
        }

        return maxSum;
    }

    public int totalBorderPixels () { return chainCode.size(); }

    public ArrayList<Integer> chain () {
        int[] first = getFirst ();
        int lastDirection = getDirection (first);
        int[] last = getNextPixel (first, 0);

        chainCode.add (lastDirection);

        while (!Arrays.equals (last, first)) {
            lastDirection = getDirection (last);
            chainCode.add (lastDirection);
            last = getNextPixel (last, lastDirection);
        }

        return chainCode;
    }
}
