package chaincode;

import java.io.IOException;
import static java.lang.Math.*;

/**
 * Created by misiuk on 11/04/16.
 */
public abstract class Coordinates extends ImageES {

    public Coordinates (String name) throws IOException { super (name); }

    public int[] getFirst() {
        int x = 0, y;
        boolean found;
        int[] coordinates = new int[2];

        for (found = false, y = 0; !found && y < image.getHeight(); y++) {
            for (x = 0; !found &&  x < image.getWidth(); x++) {
                found = (image.getRGB(x, y) != -1);
            }
        }

        if (y == image.getHeight() || x == image.getWidth()) {
            coordinates[0] = coordinates[1] = -1;
        } else {
            coordinates[0] = x-1;
            coordinates[1] = y-1;
        }

        return coordinates;
    }

    protected int[][] getPixelsAround (int[] start) {
        int[][] pixels = new int[8][2];
        int x, y;

        x = start[0]; y = start[1];

        pixels[0][0] = x + 1; pixels[0][1] = y;
        pixels[1][0] = x + 1; pixels[1][1] = y + 1;
        pixels[2][0] = x; pixels[2][1] = y + 1;
        pixels[3][0] = x - 1; pixels[3][1] = y + 1;
        pixels[4][0] = x - 1; pixels[4][1] = y;
        pixels[5][0] = x - 1; pixels[5][1] = y - 1;
        pixels[6][0] = x; pixels[6][1] = y - 1;
        pixels[7][0] = x + 1; pixels[7][1] = y - 1;

        return pixels;
    }

    protected boolean validCoordinates (int[] coordinates) {
        int x, y;
        x = coordinates[0]; y = coordinates[1];
        return x >= 0 && y >= 0 && x < image.getWidth() && y < image.getHeight();
    }

    protected boolean borderPixel (int[] pixel) {
        int x, y;
        boolean border = false;

        x = pixel[0]; y = pixel[1];

        if (image.getRGB(x, y) == -1)
            border = false;

        else if (x == 0 || y == 0 || x == image.getWidth() - 1
                || y == image.getHeight() - 1)

            border = true;

        else if (image.getRGB (x + 1, y) == -1)
            border = true;

        else if (image.getRGB (x + 1, y + 1) == -1)
            border = true;

        else if (image.getRGB (x, y + 1) == -1)
            border = true;

        else if (image.getRGB (x - 1, y + 1) == -1)
            border = true;

        else if (image.getRGB (x - 1, y) == -1)
            border = true;

        else if (image.getRGB (x - 1, y - 1) == -1)
            border = true;

        else if (image.getRGB (x, y - 1) == -1)
            border = true;

        else if (image.getRGB (x + 1, y - 1) == -1)
            border = true;

        return border;
    }

    protected int getDirection (int[] previousPixel, int first) {
        int nextDirection = -1, x, y;
        int[][] pixelsAround;
        int i , j;

        pixelsAround = getPixelsAround (previousPixel);

        for (j = 0, i = first; -1 == nextDirection && j < 8; j++, i = (i + 1)%8) {
            if (validCoordinates(pixelsAround[i]) && borderPixel(pixelsAround[i])) {
                x = pixelsAround[i][0]; y = pixelsAround[i][1];
                if (-1 != image.getRGB(x, y))
                    nextDirection = i;
            }
        }

        return nextDirection;
    }

    protected int getDirection (int[] previousPixel) {
        return getDirection (previousPixel, 0);
    }

    protected int[] getNextPixel (int[] previous, int direction) {
        int[] nextPixel = new int[2];
        int x = previous[0], y = previous[1];

        if (direction < 2 || direction == 7) x++;
        if (direction > 0 && direction < 4) y++;
        if (direction > 2 && direction < 6) x--;
        if (direction > 4) y--;

        nextPixel[0] = x;
        nextPixel[1] = y;

        return nextPixel;
    }

    public double shapePerimeter () {
        double sum = 0.0;

        if (chainCode.size() != 0) {
            for (int code : chainCode) {
                sum += (code % 2 == 0 ? 1 : sqrt(2));
            }
        }

        return sum;
    }

    public void printChainCodes () {
        for (int item : chainCode) {
            System.out.print(item + " ");
        }
        System.out.println("");
    }


}
