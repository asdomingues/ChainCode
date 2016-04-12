package chaincode;

import java.util.Scanner;
import java.io.IOException;

/**
 * Mostra os resultados do processamento da imagem dada.
 */
public class Main {

    /**
     * Método main que mostra a altura, largura, perímetro, ponto mais à esquerda acima e o Chain Code de uma imagem
     * PNG em escalas de cinza.
     * @param args argumentos passados ao programa.
     */
    public static void main (String[] args) {
        Scanner input = new Scanner (System.in);
        boolean validData = false;
        String imageName = "";
        ImageProcessing sample = null;
        int[] coordinates;
        
        System.out.println ("Insira o nome da imagem que deseja abrir:");

        while (!validData) {
            while (!validData && input.hasNextLine ()) {
                imageName = input.nextLine ();
                if (imageName.length () == 0) {
                    System.out.println ("Por favor insira o nome do novo cliente.");
                    validData = false;
                }
                else {
                    validData = true;
                }
            }

            try {
                sample = new ImageProcessing (imageName);
                validData = true;
            }
            catch (IOException e) {
                System.err.println (e);
                System.out.println ("Tente novamente!");
                validData = false;
            }
        }

        coordinates = sample.getFirst ();
        System.out.println ("Início: " + coordinates[0] + " " + coordinates[1]);
        System.out.println ("Largura: " + sample.getWidth ());
        System.out.println ("Altura: " + sample.getHeight ());
        sample.chain ();
        System.out.print ("Chain codes: ");
        sample.printChainCodes ();
        System.out.println ("Número de pontos na borda: " + sample.totalBorderPixels ());
        System.out.println ("Perímetro: " + sample.shapePerimeter ());
    }  
}
