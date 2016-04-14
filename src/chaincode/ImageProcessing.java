package chaincode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
     * Calcula a largura do menor retângulo que contém o objeto na imagem.
     * @return Retorna o valor da largura do objeto da imagem.
     */
    public int getWidth () {
        return getRightmost ()[0] - getLeftmost()[0] + 1;
    }

    /**
     * Calcula a altura do menor retângulo que contém o objeto.
     * @return Retorna o valor da altura do objeto em pixels.
     */
    public int getHeight () {
        return getLast ()[1] - getFirst ()[1] + 1;
    }

    /**
     * Calcula o número de pontos na borda do objeto na imagem PNG.
     * @return Retorna o número de pontos da borda.
     */
    public int totalBorderPixels () { return chainCode.size (); }

    /**
     * Percorre a borda de um objeto em uma imagem PNG em escalas de cinza.
     * @return ArrayList com as direções (Integer) percorridas neste algoritmo.
     */
    public ArrayList<Integer> chain () {
        int[] first = getFirst ();
        int lastDirection = getDirection (first);
        int[] last = getNextPixel (first, lastDirection);

        chainCode.add (lastDirection);

        // Enquanto não voltar ao começo;
        while (!Arrays.equals (last, first)) {
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
