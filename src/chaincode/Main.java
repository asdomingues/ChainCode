package chaincode;

import java.util.Scanner;
import java.io.IOException;

/**
 * Mostra os resultados do processamento da imagem dada.
 */
public class Main {

    /**
     * Main que mostra a altura, largura, perímetro, ponto mais à esquerda acima e o Chain Code de uma imagem
     * PNG em escalas de cinza.
     * @param args argumentos passados ao programa.
     */
    public static void main (String[] args) {
        // Validação do dado recebido;
        boolean validData = false;
        // Scanner para leitura de dados;
        Scanner input = null;
        // Nome da imagem do tipo PNG em escalas de cinza a ser processada;
        String imageName = "";
        // Instância da classe ImageProcessing, para realizar os processamentos na imagem dada;
        ImageProcessing sample = null;
        // Coordenadas do início da imagem (pixel mais à esquerda acima);
        int[] coordinates;
        
        System.out.println ("Insira o nome da imagem que deseja abrir:");

        try {
	        input = new Scanner (System.in);
	        // Validação da entrada do nome/caminho da imagem;
	        while (!validData) {
	
	            // Validando a string recebida;
	            while (!validData && input.hasNextLine ()) {
	                imageName = input.nextLine ();
	                if (imageName.length () == 0) {
	                    System.err.println ("Valor inválido.");
	                    validData = false;
	                }
	                else {
	                    validData = true;
	                }
	            }
	
	            // Tentando abrir a imagem;
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
        }
        finally {
        	if (input != null) {
        		// É necessário fechar o scanner para garantir que não haja vazamentos.
        		input.close();
        	}
        }

        // Recebendo as coordenadas do primeiro pixel da imagem;
        coordinates = sample.getFirst ();

        System.out.println ("Início: " + coordinates[0] + " " + coordinates[1]);
        System.out.println ("Largura: " + sample.getWidth ());
        System.out.println ("Altura: " + sample.getHeight ());

        // Rodando o algoritmo do Chain Codes;
        sample.chain ();
        // Imprimindo o resultado do algoritmo;
        System.out.print ("Chain codes: ");
        sample.printChainCodes ();

        // N�mero total de pontos na borda e o perímetro;
        System.out.println ("Número de pontos na borda: " + sample.totalBorderPixels ());
        System.out.println ("Perímetro: " + sample.shapePerimeter ());
    }  
}
