package chaincode;

import java.io.IOException;
import static java.lang.Math.*;

/**
 * Created by misiuk on 11/04/16.
 */
public abstract class Coordinates extends ImageES {

    /**
     *
     * @param name
     * @throws IOException
     */
    public Coordinates (String name) throws IOException { super (name); }

    /**
     *
     * @return
     */
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

    /**
     *
     * @param start
     * @return
     */
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

    /**
     *
     * @param previousPixel
     * @param first
     * @return
     */
    protected int getDirection (int[] previousPixel, int first) {
        int nextDirection = -1;
        int x, y;
        int[][] around = getPixelsAround (previousPixel);
        boolean[] pixels = new boolean[8];
        int i, j;

        for (i = 0; i < around.length; i++) {

            x = around[i][0];
            y = around[i][1];

            // Se um ponto está fora da imagem ou é branco, é false;
            if (x < 0 || y < 0 || x >= image.getWidth () || y >= image.getHeight ()) {
                pixels[i] = false;
            }
            else if (image.getRGB (x, y) == -1) {
                pixels[i] = false;
            }
            // Caso contrário, é true;
            else {
                pixels[i] = true;
            }

        }

        for (j = 0, i = (first + 7)%8; j < pixels.length; j++, i = (i + 1)%8) {
            if (!pixels[(i+7)%8] && pixels[i]) return i;
        }

        // TODO: Exceção desta bagaça;
        return -1;
    }

    /**
     *
     * @param previousPixel
     * @return
     */
    protected int getDirection (int[] previousPixel) {
        return getDirection (previousPixel, 0);
    }

    /**
     *
     * @param previous
     * @param direction
     * @return
     */
    protected int[] getNextPixel (int[] previous, int direction) {
        int[] nextPixel = new int[2];
        int x = previous[0], y = previous[1];

        if ((direction >= 0 && direction < 2) || direction == 7) x++;
        if (direction > 0 && direction < 4) y++;
        if (direction > 2 && direction < 6) x--;
        if (direction > 4 && direction <= 7) y--;

        nextPixel[0] = x;
        nextPixel[1] = y;

        return nextPixel;
    }

    /**
     *
     * @return
     */
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
