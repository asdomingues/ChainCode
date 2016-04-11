package chaincode;

import java.util.Scanner;
import java.io.IOException;

/**
 *
 */
public class Main {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean validData = false;
        String imageName = "";
        ImageProcessing sample = null;
        int[] coordinates;
        
        System.out.println("Insira o nome da imagem que deseja abrir:");

        while (!validData) {
            while (!validData && input.hasNextLine()) {
                imageName = input.nextLine();
                if (imageName.length() == 0) {
                    System.out.println("Por favor insira o nome do novo cliente.");
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
            catch(IOException e) {
                System.out.println(e);
                System.out.println("Tente novamente!");
                validData = false;
            }
        }

        coordinates = sample.getFirst();
        System.out.println("Início: " + coordinates[0] + " " + coordinates[1]);
        System.out.println("Largura: " + sample.getWidth());
        System.out.println("Altura: " + sample.getHeight());
        sample.chain();
        System.out.print("Chain codes: ");
        sample.printChainCodes();
        System.out.println("Número de pontos na borda: " + sample.totalBorderPixels());
        System.out.println("Perímetro: " + sample.shapePerimeter());
    }  
}
