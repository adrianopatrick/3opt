package br.uece.threeopt.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import br.uece.threeopt.heuristica.caixeiroviajante.Celula;
import br.uece.threeopt.heuristica.caixeiroviajante.MatrizAdjacencias;
import br.uece.threeopt.heuristica.caixeiroviajante.Ponto;

/**
 * @author raquel silveira e paulo alberto
 * @version 1.0
 * A classe ArquivoUtils sera responsavel por ler o arquivo e indetificar seu tipo
 * De acordo com o tipo, sera gerada a matriz
 * */
public class ArquivoUtils {
	
	private static Celula[][] matriz;
		
	private enum TipoArquivo
	{
		matriz,
		ponto
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo le o arquivo e transforma na matriz simetrica
	 * @param arquivo
	 * @return celula[][] - matriz simetrica
	 */
	@SuppressWarnings("static-access")
	public Celula[][] lerArquivo(File arquivo) {
		
		Celula[][] matriz = null;
		try
		{
			BufferedReader arquivoLeitura = new BufferedReader(new FileReader(arquivo.getAbsolutePath()));
			String linha1 = arquivoLeitura.readLine();
			TipoArquivo arq = identificaTipoArquivo(linha1);
			int n = identificaQtdeNos(linha1);
			matriz = new Celula[n][n];
						
			int indiceLinha = 0;
			String linha;
			switch (arq) {
				case matriz: {
					while(arquivoLeitura.ready() && !(linha = arquivoLeitura.readLine()).trim().equals("")){
						lerArquivoMatriz(matriz, linha, indiceLinha); 
						indiceLinha++;
					}
					matriz[n-1][n-1] = obtemCelulaDiagonal(n-1);
					MatrizAdjacencias.geraMatrizAdjacenciasApartirDeMatrizIncompleta(matriz);
					break;
				}
				case ponto: {
					List<Ponto> listaPontos = new ArrayList<Ponto>();
					while(arquivoLeitura.ready() && !(linha = arquivoLeitura.readLine()).trim().equals("")) {
						listaPontos.add(indiceLinha, lerArquivoPonto(linha)); 
						indiceLinha++; 
					}
					matriz = MatrizAdjacencias.geraMatrizAdjacenciasApartirDePontos(listaPontos);
					break;
				}
			}
			
	        arquivoLeitura.close();
		}
		catch (FileNotFoundException  e) {
			// TODO: handle exception
		}
		catch (IOException e) {
			// TODO: handle exception
		}
		this.matriz = MatrizAdjacencias.clone(matriz);
		return matriz;
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo identifica o tipo de arquivo (matriz ou ponto) de acordo com a primeira linha do aquivo
	 * @param arquivoLeitura
	 * @return o tipo de arquivo (matriz ou ponto)
	 */
	private TipoArquivo identificaTipoArquivo(String linha) {
		
		TipoArquivo arquivo = null;
		if (linha.contains("matriz"))
		{ arquivo = TipoArquivo.matriz;  }
		else
		{ arquivo = TipoArquivo.ponto; }
		return arquivo;
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo identifica a quantidade de nos presentes no arquivo
	 * @param linha
	 * @return quantidade de nos
	 */
	private int identificaQtdeNos(String linha) {
		
		int qtdeNos = 0;
		StringTokenizer token = new StringTokenizer(linha, "N = Sol");
		qtdeNos = Integer.parseInt(token.nextToken());
		return qtdeNos;
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo obtem da linha os valores da matrizz
	 * @param matriz
	 * @param linha
	 * @param indiceLinha
	 */
	private void lerArquivoMatriz(Celula[][] matriz, String linha, int indiceLinha) {
		
		StringTokenizer token = new StringTokenizer(linha, " ");
		matriz[indiceLinha][indiceLinha] = obtemCelulaDiagonal(indiceLinha);
		int indiceToken = indiceLinha+1;
		while(token.hasMoreTokens()) {
    		Ponto origem = new Ponto(); origem.setId(indiceLinha+1);
    		Ponto destino = new Ponto(); destino.setId(indiceToken);
    		matriz[indiceLinha][indiceToken] = new Celula(origem, destino, Double.parseDouble(token.nextToken()));
	       	indiceToken++;
    	}
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo obtem o ponto da diagonal da matriz
	 * @param indice
	 * @return celula
	 */
	private Celula obtemCelulaDiagonal(int indice) {
		
		Ponto origem = new Ponto();
		origem.setId(indice+1);
		Ponto destino = new Ponto();
		destino.setId(indice+1);
		Celula celula = new Celula(origem, destino, 0d);
		return celula;
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo obtem da linha os valores dos pontos
	 * Primeiro valor: ID; Segundo valor: Coordenada X; Terceiro valor: Coordenada Y. 
	 * @param linha - Representa a linha do arquivo
	 * @return ponto - Representa o ponto com os valores da linha
	 */
	private Ponto lerArquivoPonto(String linha) {
		
		Ponto ponto = new Ponto();
		StringTokenizer token = new StringTokenizer(linha, " ");
		int indiceToken = 0;
    	while(token.hasMoreTokens())
    	{
    		Double valor = Double.parseDouble(token.nextToken());
    		switch (indiceToken) {
				case 0: { ponto.setId(valor.intValue()); break; }
				case 1: { ponto.setCoordX(valor); break; }
				case 2: { ponto.setCoordY(valor); break; }
    		}    		
			indiceToken++;
    	} 
    	return ponto;
	}

	public static Celula[][] getMatriz() {
		return matriz;
	}

}
