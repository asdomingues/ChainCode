package chaintest;

import chaincode.ImageProcessing;
import chaincode.Coordinates;

import java.io.IOException;
import java.util.Arrays;

import junit.framework.TestCase;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * Classe para automatizar os testes dos algoritmos de processamento de imagens.
 * Criada por Allan Silva Domingues e Eduardo Garcia Misiuk em 11/04/16.
 */
public class CoordinatesTest {
	ImageProcessing image = null;

    /**
     * Inicializa a imagem que será testada, testando se ela foi realmente carregada.
     * @throws IOException Não há uma imagem com este nome.
     */
	@Before 
	public void intializeImage () throws IOException {
		image = new ImageProcessing ("Z:\\circulo.png");
		assertNotNull(image);
	}

    /**
	 * Verifica se os cálculos feitos na função getLast estão corretos.
     */
	@Test 
	public void testGetLast () {
		int[] last = new int[]{26, 35};
		assertArrayEquals(image.getLast(), last);
	}

    /**
	 * Verifica se os cálculos feitos na função getFirst estão corretos.
     */
	@Test
	public void testGetFirst () {
		int[] first = new int[]{23, 16};
		assertArrayEquals(image.getFirst(), first);
	}

    /**
     * Verifica se os cálculos feitos na função getLeftmost estão corretos.
     */
	@Test
	public void testGetLeftmost () {
		int[] leftmost = new int[]{15, 24};
		assertArrayEquals(image.getLeftmost(), leftmost);
	}

    /**
	 * Verifica se os cálculos feitos na função getRightmost estão corretos.
     */
	@Test
	public void testGetRightmost () {
		int[] rightmost = new int[]{34, 27};
		assertArrayEquals(image.getRightmost(), rightmost);
	}

    /**
     * Verifica se a função getPixelsAround () está retornando as coordenadas corretas dos pontos em volta de um
	 * certo ponto.
     */
	@Test
	public void testGetAround () {
		int[] center = new int[]{25, 25};
		int[][] around = new int[8][];
		around[0] = new int[]{26, 25};
		around[1] = new int[]{26, 26};
		around[2] = new int[]{25, 26};
		around[3] = new int[]{24, 26};
		around[4] = new int[]{24, 25};
		around[5] = new int[]{24, 24};
		around[6] = new int[]{25, 24};
		around[7] = new int[]{26, 24};
		
		assertTrue(Arrays.deepEquals(image.getPixelsAround (center), around));
	}

    /**
     * Verifica se o perímetro calculado está correto.
     */
	@Test
	public void testShapePerimeter () {
		double perimeter = 61.94112549695426;
		image.chain();
		
		assertEquals (image.shapePerimeter (), perimeter, 0.0);
	}
}
