package activity;

import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class TrianguloTest {

    @Test
    public void testClass() {
        Triangulo triangulo = new Triangulo();
        assertNotNull(triangulo);
    }

    // Testes para triângulos Escaleno
    @Test
    public void testEscaleno1() {
        ByteArrayInputStream in = new ByteArrayInputStream("2\n3\n4\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);

        assertEquals("O triângulo é Escaleno.", saida);
    }

    @Test
    public void testEscaleno2() {
        ByteArrayInputStream in = new ByteArrayInputStream("2\n4\n3\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);

        assertEquals("O triângulo é Escaleno.", saida);
    }

    @Test
    public void testEscaleno3() {
        ByteArrayInputStream in = new ByteArrayInputStream("3\n2\n4\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);

        assertEquals("O triângulo é Escaleno.", saida);
    }

    @Test
    public void testEscaleno4() {
        ByteArrayInputStream in = new ByteArrayInputStream("3\n4\n2\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);

        assertEquals("O triângulo é Escaleno.", saida);
    }

    @Test
    public void testEscaleno5() {
        ByteArrayInputStream in = new ByteArrayInputStream("4\n2\n3\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);

        assertEquals("O triângulo é Escaleno.", saida);
    }

    @Test
    public void testEscaleno6() {
        ByteArrayInputStream in = new ByteArrayInputStream("4\n3\n2\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);

        assertEquals("O triângulo é Escaleno.", saida);
    }

    // Testes para triângulos Isósceles
    @Test
    public void testIsosceles1() {
        ByteArrayInputStream in = new ByteArrayInputStream("2\n2\n3\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);

        assertEquals("O triângulo é Isósceles.", saida);
    }

    @Test
    public void testIsosceles2() {
        ByteArrayInputStream in = new ByteArrayInputStream("2\n3\n2\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);

        assertEquals("O triângulo é Isósceles.", saida);
    }

    @Test
    public void testIsosceles3() {
        ByteArrayInputStream in = new ByteArrayInputStream("3\n2\n2\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);

        assertEquals("O triângulo é Isósceles.", saida);
    }

    // Testes para triângulo Equilátero
    @Test
    public void testEquilatero() {
        ByteArrayInputStream in = new ByteArrayInputStream("1\n1\n1\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);

        assertEquals("O triângulo é Equilátero.", saida);
    }

    // Testes para entradas inválidas (inválido_igual)
    @Test
    public void testInvalidoIgual1() {
        ByteArrayInputStream in = new ByteArrayInputStream("1\n2\n3\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);

        assertEquals("Os valores fornecidos não formam um triângulo.", saida);
    }

    @Test
    public void testInvalidoIgual2() {
        ByteArrayInputStream in = new ByteArrayInputStream("1\n3\n2\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);

        assertEquals("Os valores fornecidos não formam um triângulo.", saida);
    }

    @Test
    public void testInvalidoIgual3() {
        ByteArrayInputStream in = new ByteArrayInputStream("2\n1\n3\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);

        assertEquals("Os valores fornecidos não formam um triângulo.", saida);
    }

    @Test
    public void testInvalidoIgual4() {
        ByteArrayInputStream in = new ByteArrayInputStream("2\n3\n1\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);

        assertEquals("Os valores fornecidos não formam um triângulo.", saida);
    }

    @Test
    public void testInvalidoIgual5() {
        ByteArrayInputStream in = new ByteArrayInputStream("3\n1\n2\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);

        assertEquals("Os valores fornecidos não formam um triângulo.", saida);
    }

    @Test
    public void testInvalidoIgual6() {
        ByteArrayInputStream in = new ByteArrayInputStream("3\n2\n1\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);

        assertEquals("Os valores fornecidos não formam um triângulo.", saida);
    }

    // Testes para entradas inválidas (inválido_maior)
    @Test
    public void testInvalidoMaior1() {
        ByteArrayInputStream in = new ByteArrayInputStream("1\n2\n4\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);

        assertEquals("Os valores fornecidos não formam um triângulo.", saida);
    }

    @Test
    public void testInvalidoMaior2() {
        ByteArrayInputStream in = new ByteArrayInputStream("1\n4\n2\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);

        assertEquals("Os valores fornecidos não formam um triângulo.", saida);
    }

    @Test
    public void testInvalidoMaior3() {
        ByteArrayInputStream in = new ByteArrayInputStream("2\n1\n4\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);

        assertEquals("Os valores fornecidos não formam um triângulo.", saida);
    }

    @Test
    public void testInvalidoMaior4() {
        ByteArrayInputStream in = new ByteArrayInputStream("2\n4\n1\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);

        assertEquals("Os valores fornecidos não formam um triângulo.", saida);
    }

    @Test
    public void testInvalidoMaior5() {
        ByteArrayInputStream in = new ByteArrayInputStream("4\n1\n2\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);

        assertEquals("Os valores fornecidos não formam um triângulo.", saida);
    }

    @Test
    public void testInvalidoMaior6() {
        ByteArrayInputStream in = new ByteArrayInputStream("4\n2\n1\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);

        assertEquals("Os valores fornecidos não formam um triângulo.", saida);
    }

    // Testes para entradas inválidas (valor_nulo)
    @Test
    public void testValorNulo1() {
        ByteArrayInputStream in = new ByteArrayInputStream("0\n1\n2\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);

        assertEquals("Os valores fornecidos não formam um triângulo.", saida);
    }
    
    @Test
    public void testValorNulo2() {
        ByteArrayInputStream in = new ByteArrayInputStream("0\n2\n1\n".getBytes());
        System.setIn(in);
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);
        
        assertEquals("Os valores fornecidos não formam um triângulo.", saida);
    }

    @Test
    public void testValorNulo3() {
        ByteArrayInputStream in = new ByteArrayInputStream("1\n0\n2\n".getBytes());
        System.setIn(in);
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);
        
        assertEquals("Os valores fornecidos não formam um triângulo.", saida);
    }

    @Test
    public void testValorNulo4() {
        ByteArrayInputStream in = new ByteArrayInputStream("1\n2\n0\n".getBytes());
        System.setIn(in);
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);
        
        assertEquals("Os valores fornecidos não formam um triângulo.", saida);
    }

    @Test
    public void testValorNulo5() {
        ByteArrayInputStream in = new ByteArrayInputStream("2\n0\n1\n".getBytes());
        System.setIn(in);
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);
        
        assertEquals("Os valores fornecidos não formam um triângulo.", saida);
    }

    @Test
    public void testValorNulo6() {
        ByteArrayInputStream in = new ByteArrayInputStream("2\n1\n0\n".getBytes());
        System.setIn(in);
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);
        
        assertEquals("Os valores fornecidos não formam um triângulo.", saida);
    }

    // Testes para entradas inválidas (valor_negativo)
    @Test
    public void testValorNegativo1() {
        ByteArrayInputStream in = new ByteArrayInputStream("-1\n1\n2\n".getBytes());
        System.setIn(in);
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);
        
        assertEquals("Os valores fornecidos não formam um triângulo.", saida);
    }

    @Test
    public void testValorNegativo2() {
        ByteArrayInputStream in = new ByteArrayInputStream("-1\n2\n1\n".getBytes());
        System.setIn(in);
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);
        
        assertEquals("Os valores fornecidos não formam um triângulo.", saida);
    }

    @Test
    public void testValorNegativo3() {
        ByteArrayInputStream in = new ByteArrayInputStream("1\n-1\n2\n".getBytes());
        System.setIn(in);
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);
        
        assertEquals("Os valores fornecidos não formam um triângulo.", saida);
    }

    @Test
    public void testValorNegativo4() {
        ByteArrayInputStream in = new ByteArrayInputStream("1\n2\n-1\n".getBytes());
        System.setIn(in);
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);
        
        assertEquals("Os valores fornecidos não formam um triângulo.", saida);
    }

    @Test
    public void testValorNegativo5() {
        ByteArrayInputStream in = new ByteArrayInputStream("2\n-1\n1\n".getBytes());
        System.setIn(in);
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);
        
        assertEquals("Os valores fornecidos não formam um triângulo.", saida);
    }

    @Test
    public void testValorNegativo6() {
        ByteArrayInputStream in = new ByteArrayInputStream("2\n1\n-1\n".getBytes());
        System.setIn(in);
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        Triangulo.main(new String[]{});
        String saida = out.toString().trim();
        saida = saida.substring(saida.lastIndexOf(":") + 2);
        
        assertEquals("Os valores fornecidos não formam um triângulo.", saida);
    }
}
