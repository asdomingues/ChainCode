package chaincode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe abstrata criada para abrir um arquivo de imagem e armazenar informações básicas sobre ele.
 * Criada por Allan Silva Domingues e Eduardo Garcia Misiuk em 11/04/16.
 */
public abstract class ImageES {
    // Arquivo aberto com a image;
    private File inFile;
    // Imagem;
    protected BufferedImage image;
    // Altura da forma contida na imagem;
    protected int shapeHeight;
    // Largura da forma contida na imagem;
    protected int shapeWidth;
    // Array List que contém a sequência feita pelo algoritmo Chain Code;
    protected ArrayList<Integer> chainCode;

    /**
     * Abre um arquivo de imagem com o nome name.
     * @param name nome do arquivo de imagem.
     * @throws IOException A imagem não foi encontrada ou houve alguma falha na hora de abrí-la.
     */
    public ImageES (String name) throws IOException {
        // Abrindo o arquivo com nome name;
        inFile = new File (name);
        // Tentando abrir a imagem;
        try { image = ImageIO.read (inFile); }
        catch (IOException | IllegalArgumentException ex){
            throw new IOException ("Ocorreu um erro ao ler a imagem.");
        }

        // Inicializa valores de altura de largura da forma;
        shapeHeight = shapeWidth = -1;
        // Cria o Array List;
        chainCode = new ArrayList<> ();
    }
}
