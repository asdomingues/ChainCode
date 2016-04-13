package chaincode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.*;

/**
 * Realiza o processamento de uma imagem PNG em escalas de cinza, realizando o cálculo da altura e largura da imagem
 * e roda o algoritmo Chain Code sobre a imagem.
 * Criada por Allan Silva Domingues e Eduardo Garcia Misiuk em 11/04/16.
 */
public class ImageProcessing extends Coordinates {

    /**
     * Construtor da classe.
     * @param name nome da imagem a ser carregada.
     * @throws IOException Não há uma imagem com este nome.
     */
    public ImageProcessing (String name) throws IOException { super (name); }

    /**
     * Calcula a largura de uma imagem PNG em escalas de cinza.
     * @return Retorna o valor da altura da imagem.
     */
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

    /**
     * Calcula a altura de uma imagem PNG em escalas de cinza.
     * @return Retorna o valor da altura da imagem.
     */
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

    /**
     * Calcula o número de pontos na borda da imagem PNG.
     * @return Retorna o número de pontos da borda.
     */
    public int totalBorderPixels () { return chainCode.size (); }

    /**
     * Percorre a borda de uma imagem PNG em escalas de cinza.
     * @return Array List com as direções percorridas neste algoritmo.
     */
    public ArrayList<Integer> chain () {
        int[] first = getFirst ();
        int lastDirection = getDirection (first);
        int[] last = getNextPixel (first, lastDirection);

        chainCode.add (lastDirection);

        // Enquanto não voltar ao começo;
        while (!Arrays.equals (last, first)) {
            System.out.println ("Direção: " + lastDirection);
            // Pega a direção para a qual irá;
            lastDirection = getDirection (last, lastDirection);
            // Coloca a direção dada no Array List;
            chainCode.add (lastDirection);
            // Pega o próximo pixel para o qual irá;
            last = getNextPixel (last, lastDirection);
        }

        return chainCode;
    }
}
