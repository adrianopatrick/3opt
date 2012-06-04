package br.uece.threeopt.heuristica.opt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import sun.util.calendar.BaseCalendar;
import sun.util.calendar.BaseCalendar.Date;

import br.uece.threeopt.heuristica.caixeiroviajante.Celula;
import br.uece.threeopt.heuristica.caixeiroviajante.Ponto;
import br.uece.threeopt.heuristica.caixeiroviajante.Caminho;

public class ThreeOpt {
	
	public List<Caminho> caminhos = new ArrayList<Caminho>(); 
	public static long tempoTotal = 0;
	public int naoMelhora = 0;
		
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo obtem o ciclo inicial para as demais interacoes
	 */
	public Ponto[] obtemCicloHamiltoniano(Celula[][] matriz) {
		
		//A principio o ciclo eh gerado em ordem crescente de id
		Ponto[] ciclo = new Ponto[matriz.length + 1];
		
		for (int i = 0; i < matriz.length; i++) {
			ciclo[i] = matriz[i][0].getOrigem();
		}
		
		//Fecha o ciclo
		ciclo[matriz.length] = ciclo[0];
	
		return ciclo;
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo obtem os melhores caminhos da solucao
	 */
	public List<Caminho> obtemCaminhos(Celula[][] matriz)
	{
		try {
			Ponto[] cicloOriginal = obtemCicloHamiltoniano(matriz);
			Caminho melhorCaminho = new Caminho();
			melhorCaminho.setPonto(cicloOriginal);
			melhorCaminho.setDistancia(calculaDistancia(matriz, cicloOriginal));
			caminhos.add(melhorCaminho);
			
			long tempoInicial = System.nanoTime();
			if (matriz.length <= 100) {
				int v[] = new int[3];
				for (int qtdeRepeticoes = 0; qtdeRepeticoes < matriz.length; qtdeRepeticoes++) {
					for (int i = 0; i < matriz.length; i++) { 
						for (int k = i + 2; k < matriz.length; k++) { 
							for (int m = k + 2; m < matriz.length; m++) { 
								v[0] = i; v[1] = k; v[2] = m; 
								melhorCaminho = obtemInteracoes(matriz, melhorCaminho, v);
								melhorCaminho = adicionaCaminho(melhorCaminho);
							}
						}
					}
				}
			}
			else {
				for (int i = 0; i < Math.pow(cicloOriginal.length, 2)/2 || naoMelhora < Math.pow(cicloOriginal.length, 2)/2 * 0.05;   i++) {	
					melhorCaminho = obtemInteracoes(matriz, melhorCaminho, obtemVerticesRandomicos(matriz.length-1));
					melhorCaminho = adicionaCaminho(melhorCaminho);
				}
			}
			tempoTotal = System.nanoTime() - tempoInicial;
			return caminhos;
		}
		catch (Exception e) { }
		return null;
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo adiciona os melhores caminhos a uma lista
	 */
	private Caminho adicionaCaminho(Caminho caminho)
	{
		try {
			Caminho ultimoCaminho = caminhos.get(caminhos.size()-1);
			
			if (caminho.getDistancia() < ultimoCaminho.getDistancia()) {
				caminhos.add(caminho);
				return caminho;
			}
			return ultimoCaminho;
		}
		catch (Exception e) { }
		return null;
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo obtem as interacoes a partir de um ciclo inicial
	 * @param matriz de pontos
	 * @param caminho contendo um ciclo inicial
	 * @param vertices escolhidos para serem removidos
	 */
	public Caminho obtemInteracoes(Celula[][] matriz, Caminho caminho, int[] v){
		
		System.out.println(v[0] + " [i: " + caminho.getPonto()[v[0]].getId() + " - j: " + caminho.getPonto()[v[0]+1].getId() + "]");
		System.out.println(v[1] + " [k: " + caminho.getPonto()[v[1]].getId() + " - l: " + caminho.getPonto()[v[1]+1].getId() + "]");
		System.out.println(v[2] + " [m: " + caminho.getPonto()[v[2]].getId() + " - n: " + caminho.getPonto()[v[2]+1].getId() + "]");
						
		Caminho melhorCiclo = null;
		try { melhorCiclo = caminho.clone(); }
		catch (Exception e) { }
		
		System.out.print("(0)");
		for (int j = 0; j < caminho.getPonto().length; j++) {
			System.out.print(" - " + caminho.getPonto()[j].getId());
		}
		System.out.println(" -> " + caminho.getDistancia());
		double c0 = caminho.getDistancia() 
					- (matriz[caminho.getPonto()[v[0]].getId()-1][caminho.getPonto()[v[0]+1].getId()-1].getDistancia()
					+ matriz[caminho.getPonto()[v[1]].getId()-1][caminho.getPonto()[v[1]+1].getId()-1].getDistancia()
					+ matriz[caminho.getPonto()[v[2]].getId()-1][caminho.getPonto()[v[2]+1].getId()-1].getDistancia());
		
		Caminho ciclo = new Caminho();
		ciclo.setPonto(new Ponto[caminho.getPonto().length]);
		
		for (int i = 1; i <= 7; i++) {
			switch (i) {
				case 1: { ciclo = aplicaSolucao1(caminho.getPonto(), v[0], v[1], v[2], matriz, c0); break; }
				case 2: { ciclo = aplicaSolucao2(caminho.getPonto(), v[0], v[1], v[2], matriz, c0); break; }
				case 3: { ciclo = aplicaSolucao3(caminho.getPonto(), v[0], v[1], v[2], matriz, c0); break; }
				case 4: { ciclo = aplicaSolucao4(caminho.getPonto(), v[0], v[1], v[2], matriz, c0); break; }
				case 5: { ciclo = aplicaSolucao5(caminho.getPonto(), v[0], v[1], v[2], matriz, c0); break; }
				case 6: { ciclo = aplicaSolucao6(caminho.getPonto(), v[0], v[1], v[2], matriz, c0); break; }
				case 7: { ciclo = aplicaSolucao7(caminho.getPonto(), v[0], v[1], v[2], matriz, c0); break; }
			}
			
			try {
			if (ciclo.getDistancia() < melhorCiclo.getDistancia()) {
				melhorCiclo = ciclo.clone();
				naoMelhora = 0;
			}
			else naoMelhora++;
			} catch(Exception ex) {}
			
			System.out.print("(" + i + ")");
			for (int j = 0; j < ciclo.getPonto().length; j++) {
				System.out.print(" - " + ciclo.getPonto()[j].getId());
			}
			System.out.println(" -> " + ciclo.getDistancia());
		}
		
		System.out.println("Melhor distância: " + melhorCiclo.getDistancia());
		return melhorCiclo;	
	}
			
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo obtem 3 vertices de forma randomica
	 * @param valor maximo a ser escolhido
	 */
	private int[] obtemVerticesRandomicos(int n) {
		
		Random randomico = new Random();
		int v[] = new int[3];
		v[0] = randomico.nextInt(n);
		
		do { v[1] = randomico.nextInt(n); }
		while (v[1] == v[0] || v[0] == v[1] + 1 || v[0] == v[1] - 1 || (v[0] == 0 && v[1] == n));
		
		do { v[2] = randomico.nextInt(n); }
		while (v[2] == v[1] || v[2] == v[0] || v[2] == v[1] + 1 || v[2] == v[1] - 1 || v[2] == v[0] + 1 || v[2] == v[0] - 1 || (v[0] == 0 && v[2] == n) || (v[1] == 0 && v[2] == n));
		
		Arrays.sort(v);
		
		return v;
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo calcula a distancia da solucao
	 * A distancia eh calculada a partir do custo do ciclo sem as arestas retiradas, somando com o novo posicionamento das arestas
	 * @param novo ciclo
	 * @param vertice i
	 * @param vertice k
	 * @param vertice m
	 * @param matriz de pontos
	 * @param custo do ciclo inicial sem as arestas
	 */
	public double calculaDistanciaSolucao(Ponto[] ciclo, int v1, int v2, int v3, Celula[][] matriz, double c0) {
		
		return c0 + matriz[ciclo[v1].getId()-1][ciclo[v1+1].getId()-1].getDistancia()
                  + matriz[ciclo[v2].getId()-1][ciclo[v2+1].getId()-1].getDistancia()
                  + matriz[ciclo[v3].getId()-1][ciclo[v3+1].getId()-1].getDistancia();
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo calcula a distancia do ciclo, percorrendo todos os pontos
	 * @param matriz de pontos
	 * @param ciclo formado
	 */
	public double calculaDistancia(Celula[][] matriz, Ponto[] ciclo) {
		
		double distancia = 0d;
		for (int i = 0; i < ciclo.length-1; i++) {
			distancia += matriz[ciclo[i].getId()-1][ciclo[i+1].getId()-1].getDistancia();
		}
		return distancia;
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo aplica a primeira solucao do 3-OPT
	 * @param ciclo de pontos
	 * @param vertice i
	 * @param vertice k
	 * @param vertice m
	 * @param matriz de pontos
	 * @param custo do ciclo sem as arestas
	 */
	public Caminho aplicaSolucao1 (Ponto[] ciclo, int v1, int v2, int v3, Celula[][] matriz, double c0) {
		
		Caminho novoCiclo = new Caminho();
		novoCiclo.setPonto(ciclo.clone());
		novoCiclo.getPonto()[v2 + 1] = ciclo[v3];
		for(int i = 1; i <= (v3 - v2 - 1); i++)
			novoCiclo.getPonto()[v2+1+i] = ciclo[v3-i];
		novoCiclo.getPonto()[v3+1] = ciclo[v3+1]; 
		
		novoCiclo.setDistancia(calculaDistanciaSolucao(novoCiclo.getPonto(), v1, v2, v3, matriz, c0));
			
		return novoCiclo;
	}	
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo aplica a segunda solucao do 3-OPT
	 * @param ciclo de pontos
	 * @param vertice i
	 * @param vertice k
	 * @param vertice m
	 * @param matriz de pontos
	 * @param custo do ciclo sem as arestas
	 */
	public Caminho aplicaSolucao2 (Ponto[] ciclo, int v1, int v2, int v3, Celula[][] matriz, double c0) {
		
		Caminho novoCiclo = new Caminho();
		novoCiclo.setPonto(ciclo.clone());
		novoCiclo.getPonto()[v1+2] = ciclo[v2+1];
		for(int i = 1; i <= (v2 - v1 - 1); i++)
			novoCiclo.getPonto()[v1+1+i] = ciclo[v2-i];
		novoCiclo.getPonto()[v1+1] = ciclo[v2];
		
		novoCiclo.setDistancia(calculaDistanciaSolucao(novoCiclo.getPonto(), v1, v2, v3, matriz, c0));
		
		return novoCiclo;
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo aplica a terceira solucao do 3-OPT
	 * @param ciclo de pontos
	 * @param vertice i
	 * @param vertice k
	 * @param vertice m
	 * @param matriz de pontos
	 * @param custo do ciclo sem as arestas
	 */
	public Caminho aplicaSolucao3 (Ponto[] ciclo, int v1, int v2, int v3, Celula[][] matriz, double c0) {
		
		Caminho novoCiclo = new Caminho();
		novoCiclo.setPonto(ciclo.clone());
		novoCiclo.getPonto()[v1+1] = ciclo[v2];
		for(int i = 1; i <= (v2 - v1 - 1); i++)
			novoCiclo.getPonto()[v1+1+i] = ciclo[v2-i];
		novoCiclo.getPonto()[v2] = ciclo[v1+1];
		novoCiclo.getPonto()[v2+1] = ciclo[v3];
		for(int i = 1; i <= (v3 - v2 - 1); i++)
			novoCiclo.getPonto()[v2+1+i] = ciclo[v3-i];
		novoCiclo.getPonto()[v3] = ciclo[v2+1];
		
		novoCiclo.setDistancia(calculaDistanciaSolucao(novoCiclo.getPonto(), v1, v2, v3, matriz, c0));
		
		return novoCiclo;
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo aplica a quarta solucao do 3-OPT
	 * @param ciclo de pontos
	 * @param vertice i
	 * @param vertice k
	 * @param vertice m
	 * @param matriz de pontos
	 * @param custo do ciclo sem as arestas
	 */
	public Caminho aplicaSolucao4 (Ponto[] ciclo, int v1, int v2, int v3, Celula[][] matriz, double c0) {
		
		Caminho novoCiclo = new Caminho();
		novoCiclo.setPonto(ciclo.clone());
		novoCiclo.getPonto()[v1+1] = ciclo[v2+1];
		for (int i = 1; i < v3 - v2 - 1; i++)
			novoCiclo.getPonto()[v1 + 1 + i] = ciclo[v2 + 1 + i];
		int novoV2 = v1 + v3 - v2;
		novoCiclo.getPonto()[novoV2] = ciclo[v3];
		novoCiclo.getPonto()[novoV2 + 1] = ciclo[v1+1];
		for (int i = 1; i < v2 - v1 - 1; i++)
			novoCiclo.getPonto()[novoV2 + 1 + i] = ciclo[v1+1+i];
		novoCiclo.getPonto()[v3] = ciclo[v2];
		
		novoCiclo.setDistancia(calculaDistanciaSolucao(novoCiclo.getPonto(), v1, novoV2, v3, matriz, c0));
		
		return novoCiclo;
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo aplica a quinta solucao do 3-OPT
	 * @param ciclo de pontos
	 * @param vertice i
	 * @param vertice k
	 * @param vertice m
	 * @param matriz de pontos
	 * @param custo do ciclo sem as arestas
	 */
	public Caminho aplicaSolucao5 (Ponto[] ciclo, int v1, int v2, int v3, Celula[][] matriz, double c0) {
			
		Caminho novoCiclo = new Caminho();
		novoCiclo.setPonto(ciclo.clone());
		novoCiclo.getPonto()[v1+1] = ciclo[v2+1];
		for (int i = 1; i < v3 - v2 - 1; i++)
			novoCiclo.getPonto()[v1 + 1 + i] = ciclo[v2 + 1 + i];
		int novoV2 = v3 - v2 + v1;
		novoCiclo.getPonto()[novoV2] = ciclo[v3];
		novoCiclo.getPonto()[novoV2+1] = ciclo[v2];
		for (int i = 1; i <= v2 - v1 - 1; i++)
			novoCiclo.getPonto()[novoV2 + 1 + i] = ciclo[v2-i];
		
		novoCiclo.setDistancia(calculaDistanciaSolucao(novoCiclo.getPonto(), v1, novoV2, v3, matriz, c0));
		
		return novoCiclo;
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo aplica a sexta solucao do 3-OPT
	 * @param ciclo de pontos
	 * @param vertice i
	 * @param vertice k
	 * @param vertice m
	 * @param matriz de pontos
	 * @param custo do ciclo sem as arestas
	 */
	public Caminho aplicaSolucao6 (Ponto[] ciclo, int v1, int v2, int v3, Celula[][] matriz, double c0) {
		
		Caminho novoCiclo = new Caminho();
		novoCiclo.setPonto(ciclo.clone());
		novoCiclo.getPonto()[v1+1] = ciclo[v3];
		for(int i = 1; i <= (v3 - v2 - 1); i++)
			novoCiclo.getPonto()[v1+1+i] = ciclo[v3-i];
		int novoV2 = v3 - v2 + v1;
		novoCiclo.getPonto()[novoV2+1] = ciclo[v1+1];
		for (int i = 1; i <= v2 - v1 - 1; i++)
			novoCiclo.getPonto()[novoV2 + 1 + i] = ciclo[v1 + 1 + i];
				
		novoCiclo.setDistancia(calculaDistanciaSolucao(novoCiclo.getPonto(), v1, novoV2, v3, matriz, c0));
		
		return novoCiclo;
	}
		
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo aplica a setima solucao do 3-OPT
	 * @param ciclo de pontos
	 * @param vertice i
	 * @param vertice k
	 * @param vertice m
	 * @param matriz de pontos
	 * @param custo do ciclo sem as arestas
	 */
	public Caminho aplicaSolucao7 (Ponto[] ciclo, int v1, int v2, int v3, Celula[][] matriz, double c0) {
		
		Caminho novoCiclo = new Caminho();
		novoCiclo.setPonto(ciclo.clone());
		novoCiclo.getPonto()[v1+1]=ciclo[v3];
		for(int i = 1; i <= (v3 - v2 - 1); i++)
			novoCiclo.getPonto()[v1+1+i] = ciclo[v3-i];
		int novoV2 =  v3 - v2 + v1;
		novoCiclo.getPonto()[novoV2+1]= ciclo[v2];
		for(int i = 1; i <= (v2 - v1 - 1); i++)
			novoCiclo.getPonto()[novoV2+1+i] = ciclo[v2-i];
				
		novoCiclo.setDistancia(calculaDistanciaSolucao(novoCiclo.getPonto(), v1, novoV2, v3, matriz, c0));
		
		return novoCiclo;
	}
}