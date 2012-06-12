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
import br.uece.threeopt.heuristica.sequenciamento.Job;
import br.uece.threeopt.heuristica.sequenciamento.Maquina;
import br.uece.threeopt.heuristica.sequenciamento.Tarefa;

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
		matrizTipo2,
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
			int indiceColuna = 0;
			
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
				case matrizTipo2: {
					indiceLinha = -1;
					while(arquivoLeitura.ready() && !(linha = arquivoLeitura.readLine()).trim().equals("")){
						
						StringTokenizer token = new StringTokenizer(linha, " ");
						while(token.hasMoreTokens()) {
				    		double distancia = Double.parseDouble(token.nextToken());
				    		if (distancia == 0) {
				    			indiceLinha++;
				    			indiceColuna = indiceLinha + 1;
				    			matriz[indiceLinha][indiceLinha] = obtemCelulaDiagonal(indiceLinha);
				    			if(indiceLinha < n-1)
				    				distancia = Double.parseDouble(token.nextToken());
				    			else
				    				break;
				    		}
				    		Ponto origem = new Ponto(); origem.setId(indiceLinha+1);
				    		Ponto destino = new Ponto(); destino.setId(indiceColuna+1);
				    		matriz[indiceLinha][indiceColuna] = new Celula(origem, destino, distancia);
					       	indiceColuna++;
				    	}
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
	
	public static Job[][] lerArquivoJSSP(File arquivo) {
		
		Job[][] jobs = null;

		try
		{
			BufferedReader arquivoLeitura = new BufferedReader(new FileReader(arquivo.getAbsolutePath()));
			String linha1 = arquivoLeitura.readLine();
			
			StringTokenizer token = new StringTokenizer(linha1, " ");
			int qtdeJob = Integer.parseInt(token.nextToken());
			int qtdeMaq = Integer.parseInt(token.nextToken());
			
			jobs = new Job[qtdeMaq][qtdeJob];
			int indiceLinha = 0;
			String linha;
			
			while(arquivoLeitura.ready() && !(linha = arquivoLeitura.readLine()).trim().equals("")){
				StringTokenizer tokenLinha = new StringTokenizer(linha, " ");
				while(tokenLinha.hasMoreTokens()) {
					
					int idMaquina = Integer.parseInt(tokenLinha.nextToken());
					
					Job job = new Job();
					job.setMaquina(new Maquina(idMaquina));
					job.setTarefa(new Tarefa(indiceLinha));				
					job.setTempo(Integer.parseInt(tokenLinha.nextToken()));
					jobs[idMaquina][indiceLinha] = job;
		    	} 
				indiceLinha++;
			}
			
	        arquivoLeitura.close();
	        
		} catch (FileNotFoundException  e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return jobs;
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
		if (linha.contains("matriz")) { 
			if (linha.contains("0 delimita"))
				arquivo = TipoArquivo.matrizTipo2;
			else
				arquivo = TipoArquivo.matriz;  
		}
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
    		Ponto destino = new Ponto(); destino.setId(indiceToken+1);
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
