package chaincode;

import java.io.IOException;
import static java.lang.Math.*;

/**
 * Classe para manipulação de coordenadas de uma imagem PNG em escalas de cinza.
 * Criado por Allan Silva Domingues e Eduardo Garcia Misiuk em 11/04/16.
 */
public abstract class Coordinates extends ImageES {

    /**
     * Inicializa uma imagem PNG em escalas de cinza com nome name.
     * @param name nome da imagem.
     * @throws IOException TODO
     */
    public Coordinates (String name) throws IOException { super (name); }

    /**
     * Acha o primeiro pixel da imagem (pixel mais à esquerda acima).
     * @return Coordenadas (x, y) do ponto.
     */
    public int[] getFirst() {
        int x = 0, y;
        boolean found;
        int[] coordinates = new int[2];

        // Busca pelo pixel;
        for (found = false, y = 0; !found && y < image.getHeight(); y++) {
            for (x = 0; !found &&  x < image.getWidth(); x++) {
                // Pegando a cor do pixel (x, y). Caso seja preta, achou o primeiro pixel;
                found = (image.getRGB(x, y) != -1);
            }
        }

        // Caso chegue ao fim, não há nenhum pixel que não seja branco;
        if (y == image.getHeight() || x == image.getWidth()) {
            coordinates[0] = coordinates[1] = -1;
        }
        // Caso não chegue ao fim, achou um pixel não branco;
        else {
            coordinates[0] = x-1;
            coordinates[1] = y-1;
        }

        return coordinates;
    }

    /**
     * Guarda em uma matriz 8x2 de inteiros as coordenadas dos pixels em volta do pixel dado na variável start.
     * @param start coordenadas do pixel em que se deseja saber as coordenadas em volta.
     * @return Matriz 8x2 com as coordenadas requeridas.
     */
    protected int[][] getPixelsAround (int[] start) {
        int[][] pixels = new int[8][2];
        int x, y;

        x = start[0]; y = start[1];

        /*
            Sendo (x,y) o pixel dado em start, as coordenadas em volta dele serão:
            (x-1,y-1) (x,y-1) (x+1,y-1)
            (x-1,y)   (x,y)   (x+1,y)
            (x-1,y+1) (x,y+1) (x+1,y+1)

            A forma como se armazenará elas será:
            - Coluna 0: x;
            - Coluna 1: y.
         */

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
     * Calcula a próxima direção que se deve seguir para continuar a analisar o contorno da imagem.
     * @param previousPixel pixel em que se está.
     * @param first TODO
     * @return Número de 0 a 7 que corresponde à direção a ser seguida pelo algoritmo Chain Code.
     */
    protected int getDirection (int[] previousPixel, int first) {
        // Coordenadas do ponto que será analisado;
        int x, y;
        // Pixels em volta de previousPixel;
        int[][] around = getPixelsAround (previousPixel);
        // Este vetor de boolean mostra se na direção em que ele está indexado, há algum pixel colorido (true) ou
        // branco (false). Cada posição do vetor é uma direção;
        boolean[] pixels = new boolean[8];
        // Contadores;
        int i, j;

        // Analisando as cores dos pixels em volta de previousPixel;
        for (i = 0; i < around.length; i++) {

            // Pegando a coordenada dos pontos em volta;
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

        // Para achar a borda, é feito o seguinte procedimento:
        /* TODO: vê se está boa a explicação.
           Iniciando em (2, 2).
           Para achar a borda, pegamos a direção de onde veio o ponto e achamos duas direções atrás. Como é o inicial,
           adotamos como 0. Ou seja, ele começará o algoritmo na direção 5, indo no sentido horário.

           Direções adotadas:
             5  6  7
              \ | /
           4 ------- 0
              / | \
             3  2  1

           O - outra cor
                2
           | | | | | | |
           | |O| | | | |
         2 | |O|O|O|O| |
           | |O|O|O|O| |
           | |O|O|O|O| |
           | |O|O|O|O| |
           | | | | | | |
         */
        for (j = 0, i = (first + 5)%8; j < pixels.length; j++, i = (i + 1)%8) {
            if (!pixels[(i+7)%8] && pixels[i]) return i;
        }

        // TODO: Exceção desta bagaça;
        return -1;
    }

    /**
     * Calcula a próxima direção que se deve seguir para continuar a analisar o contorno da imagem.
     * @param previousPixel pixel em que se está.
     * @return Número de 0 a 7 que corresponde à direção a ser seguida pelo algoritmo Chain Code.
     */
    protected int getDirection (int[] previousPixel) {
        return getDirection (previousPixel, 0);
    }

    /**
     * Transforma a direção em coordenada.
     * @param previous coordenada em que se estava analisando.
     * @param direction direção calculada pela função getDirection.
     * @return Coordenadas do ponto aonde aponta a direção.
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
     * Calcula o perímetro da forma que há na imagem.
     * @return Valor do perímetro, em unidade de comprimento.
     */
    public double shapePerimeter () {
        double sum = 0.0;

        if (chainCode.size() != 0) {
            for (int code : chainCode) {
                // Caso seja uma direção par, valerá 1. Caso seja uma direção ímpar, será sqrt (1^2 + 1^2) = sqrt(2);
                sum += (code % 2 == 0 ? 1 : sqrt(2));
            }
        }

        return sum;
    }

    /**
     * Imprime o Array list que contém o entorno da forma, feito pelo algoritmo Chain Code.
     */
    public void printChainCodes () {
        for (int item : chainCode) {
            System.out.print(item + " ");
        }
        System.out.println("");
    }


}
