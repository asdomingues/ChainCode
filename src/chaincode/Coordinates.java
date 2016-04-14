package chaincode;

import java.io.IOException;
import static java.lang.Math.*;

/**
 * Classe para manipulação de coordenadas de uma imagem PNG em escalas de cinza.
 * Note que o sistema de coordenadas aqui empregado não é como o matemático. O sentido
 * de crescimento do eixo Y é para baixo. Ou seja, os pontos têm ambas as coordenadas
 * positivas e possuem abscissa crescente para a direita e ordenada crescente para baixo
 * (na imagem).
 * 
 * Criado por Allan Silva Domingues e Eduardo Garcia Misiuk em 11/04/16.
 */
public abstract class Coordinates extends ImageES {

    /**
     * Inicializa uma imagem PNG em escalas de cinza com nome name.
     * @param name nome da imagem.
     * @throws IOException Não há uma imagem com este nome.
     */
    public Coordinates (String name) throws IOException { super (name); }

    /**
     * Acha o último pixel do objeto considerando o sentido do canto
     * superior esquerdo ao inferior direito. O pixel é escolhido de acordo
     * com o eixo das ordenadas primeiro e então com o das abscissas.
     * @return Coordenadas (x, y) do ponto. (-1, -1) se não existir.
     */
    public int[] getLast() {
        int x = image.getWidth() - 1, y;
        boolean found;
        int[] coordinates = new int[2];

        // Busca pelo pixel;
        for (found = false, y = image.getHeight() - 1; !found && y >= 0; y--) {
            for (x = image.getWidth() - 1; !found &&  x >= 0; x--) {
                // Pegando a cor do pixel (x, y). Caso seja preta, achou o primeiro pixel;
                found = (image.getRGB(x, y) != -1);
            }
        }

        // Quando não há nenhum pixel que não seja branco;
        if (!found) {
            coordinates[0] = coordinates[1] = -1;
        }
        // Achou um pixel não branco, mas passou em um porque a condição
        // só é verificada após o incremento;
        else {
            coordinates[0] = x+1;
            coordinates[1] = y+1;
        }

        return coordinates;
    }

    /**
     * Acha o primeiro pixel do objeto considerando o sentido do canto
     * superior esquerdo ao inferior direito. O pixel é escolhido de acordo
     * com ao eixo das ordenadas primeiro e então com o das abscissas.
     * @return Coordenadas (x, y) do ponto. (-1, -1) se não existir.
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

        // Quando não há nenhum pixel que não seja branco;
        if (!found) {
            coordinates[0] = coordinates[1] = -1;
        }
     // Achou um pixel não branco, mas passou em um porque a condição
     // só é verificada após o incremento;
        else {
            coordinates[0] = x-1;
            coordinates[1] = y-1;
        }

        return coordinates;
    }

    /**
     * Acha o ponto mais à direita do objeto. Se houver vários, o mais abaixo.
     * @return Coordenadas (x, y) do ponto. (-1, -1) se não existir.
     */
    public int[] getRightmost() {
        int x, y = image.getHeight() - 1;
        boolean found;
        int[] coordinates = new int[2];

        // Busca pelo pixel;
        for (found = false, x = image.getWidth() - 1; !found && x >= 0; x--) {
            for (y = image.getHeight() - 1; !found &&  y >= 0; y--) {
                // Pegando a cor do pixel (x, y). Caso seja preta, achou o primeiro pixel;
                found = (image.getRGB(x, y) != -1);
            }
        }

        // Quando não há nenhum pixel que não seja branco;
        if (!found) {
            coordinates[0] = coordinates[1] = -1;
        }
        // Achou um pixel não branco, mas passou em um porque a condição
        // só é verificada após o incremento;
        else {
            coordinates[0] = x+1;
            coordinates[1] = y+1;
        }

        return coordinates;
    }

    /**
     * Acha o ponto mais à esquerda do objeto. Se houver vários, o mais acima.
     * @return Coordenadas (x, y) do ponto. (-1, -1) se não existir.
     */
    public int[] getLeftmost() {
        int x, y = 0;
        boolean found;
        int[] coordinates = new int[2];

        // Busca pelo pixel;
        for (found = false, x = 0; !found &&

x < image.getWidth(); x++) {
            for (y = 0; !found &&  y < image.getHeight(); y++) {
                // Pegando a cor do pixel (x, y). Caso seja preta, achou o primeiro pixel;
                found = (image.getRGB(x, y) != -1);
            }
        }

        // Quando não há nenhum pixel que não seja branco;
        if (!found) {
            coordinates[0] = coordinates[1] = -1;
        }
     // Achou um pixel não branco, mas passou em um porque a condição
     // só é verificada após o incremento;
        else {
            coordinates[0] = x-1;
            coordinates[1] = y-1;
        }

        return coordinates;
    }

    /**
     * Guarda em uma matriz 8x2 de inteiros as coordenadas dos pixels em volta do pixel dado na variável start.
     * @param start coordenadas do pixel do qual se desja conhecer os pixels adjacentes.
     * @return Matriz 8x2 com as coordenadas requeridas.
     */
    public int[][] getPixelsAround (int[] start) {
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
     * @param lastDirection Última direção para chegar ao pixel atual (é necessário para garantir
     * 						que os pixels da borda sejam descobertos em sentido horário)
     * @return	Número de 0 a 7 que corresponde à direção a ser seguida pelo algoritmo Chain Code
     * 			ou -1 se o pixel enviado não é branco, não é válido ou não possui pixels
     * 			adjacentes não brancos.
     */
    protected int getDirection (int[] previousPixel, int lastDirection) {
        // Coordenadas do ponto que será analisado;
        int x, y;
        // Pixels em volta de previousPixel;
        int[][] around = getPixelsAround (previousPixel);
        // Este vetor de boolean mostra se, na direção em que ele está indexado, há algum pixel colorido (true) ou
        // branco (false). Cada posição do vetor é uma direção;
        boolean[] pixels = new boolean[8];
        // Contadores;
        int i, j, direction = -1;

        x = previousPixel[0];
        y = previousPixel[1];
        
        // Se não tiver cor.
        if (x == -1 || y == -1 || image.getRGB(x, y) == -1) {
        	direction = -1;
        }
        else {
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
	        /* Iniciando em (2, 2).
	           Para achar a borda, pegamos a direção de onde veio o ponto e achamos três direções atrás. Nesse exemplo,
	           por ser o primeiro ponto, adotamos como 0. Ou seja, ele começará o algoritmo na direção 5, indo no sentido horário.
	           Então é feita uma verificação de se o pixel na direção indicada, a partir do usado como parímetro, possui alguma
	           cor. Se ele possuir uma cor e o da direção imediatamente anterior não o fizer, a direção a seguir é essa.
	
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
	        for (j = 0, i = (lastDirection + 5)%8; j < pixels.length && -1 == direction; j++, i = (i + 1)%8) {
	            if (!pixels[(i+7)%8] && pixels[i]) direction = i;
	        }
        }

        return direction;
    }

    /**
     * Calcula a próxima direção que se deve seguir para continuar a analisar o contorno da imagem.
     * @param previousPixel pixel em que se está.
     * @return Número de 0 a 7 que corresponde à direção a ser seguida pelo algoritmo Chain Code
     * 			ou -1 se não houver pixels não brancos adjacentes.
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
     * Calcula o perímetro da forma que há na imagem em pixels.
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
     * Imprime o array list que contém o entorno da forma, feito pelo algoritmo Chain Code.
     */
    public void printChainCodes () {
    	if (chainCode.contains(-1))
    		System.out.println("N/A");
    	
    	else 
    		for (int item : chainCode) {
    			System.out.print(item + " ");
    		}
    	
        System.out.println("");
    }


}
