package br.uece.threeopt.heuristica.opt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.uece.threeopt.heuristica.caixeiroviajante.Celula;
import br.uece.threeopt.heuristica.caixeiroviajante.Ponto;
import br.uece.threeopt.heuristica.caixeiroviajante.Caminho;

public class ThreeOpt {
	
	public List<Caminho> caminhos = new ArrayList<Caminho>();
		
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
	
	public List<Caminho> obtemCaminhos(Celula[][] matriz)
	{
		try {
			Ponto[] cicloOriginal = obtemCicloHamiltoniano(matriz);
			Caminho melhorCaminho = new Caminho();
			melhorCaminho.setPonto(cicloOriginal);
			melhorCaminho.setDistancia(calculaDistancia(matriz, cicloOriginal));
			caminhos.add(melhorCaminho);
					
			for (int i = 0; i < Math.pow(cicloOriginal.length, 2);   i++) {
				melhorCaminho = obtemInteracoes(matriz, melhorCaminho);
				melhorCaminho = adicionaCaminho(melhorCaminho);
			}
			return caminhos;
		}
		catch (Exception e) { }
		return null;
	}
	
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
	
	public Caminho obtemInteracoes(Celula[][] matriz, Caminho caminho){
	
		Random randomico = new Random();
		int v1 = 0; int v2 = 0; int v3 = 0;
		
		v1 = randomico.nextInt(matriz.length-1);
		System.out.println(v1);
		do { v2 = randomico.nextInt(matriz.length-1); }
		while (v2 == v1 || v1 == v2 + 1 || v1 == v2 - 1 || (v1 == 0 && v2 == matriz.length-1));
		System.out.println(v2);
		do { v3 = randomico.nextInt(matriz.length-1); }
		while (v3 == v2 || v3 == v1 || v3 == v2 + 1 || v3 == v2 - 1 || v3 == v1 + 1 || v3 == v1 - 1 || (v1 == 0 && v3 == matriz.length-1) || (v2 == 0 && v3 == matriz.length-1));
		System.out.println(v3);
						
		Caminho melhorCiclo = null;
		try { melhorCiclo = caminho.clone(); }
		catch (Exception e) { }
		
		System.out.print("(0)");
		for (int j = 0; j < caminho.getPonto().length; j++) {
			System.out.print(" - " + caminho.getPonto()[j].getId());
		}
		System.out.println(" -> " + caminho.getDistancia());
		double c0 = caminho.getDistancia() 
					- (matriz[caminho.getPonto()[v1].getId()-1][caminho.getPonto()[v1+1].getId()-1].getDistancia()
					+ matriz[caminho.getPonto()[v2].getId()-1][caminho.getPonto()[v2+1].getId()-1].getDistancia()
					+ matriz[caminho.getPonto()[v3].getId()-1][caminho.getPonto()[v3+1].getId()-1].getDistancia());
		/*System.out.println (" d " + caminho.getDistancia() + " c0 " + c0 + 
							" d1 " + caminho.getPonto()[v1].getId() + " a " + caminho.getPonto()[v1+1].getId() + "  " + matriz[caminho.getPonto()[v1].getId()-1][caminho.getPonto()[v1+1].getId()-1].getDistancia() +
							" d2 " + caminho.getPonto()[v2].getId() + " a " + caminho.getPonto()[v2+1].getId() + "  " +matriz[caminho.getPonto()[v2].getId()-1][caminho.getPonto()[v2+1].getId()-1].getDistancia() +
							" d3 " + caminho.getPonto()[v3].getId() + " a " + caminho.getPonto()[v3+1].getId() + "  " + matriz[caminho.getPonto()[v3].getId()-1][caminho.getPonto()[v3+1].getId()-1].getDistancia());*/	
		
		Ponto[] ciclo = new Ponto[caminho.getPonto().length];
		double distancia = 0d;
		for (int i = 1; i <= 7; i++) {
			switch (i) {
				case 1: { ciclo = aplicaSolucao1(caminho.getPonto().clone(), v1, v2, v3); break; }
				case 2: { ciclo = aplicaSolucao2(caminho.getPonto().clone(), v1, v2, v3); break; }
				case 3: { ciclo = aplicaSolucao3(caminho.getPonto().clone(), v1, v2, v3); break; }
				case 4: { ciclo = aplicaSolucao4(caminho.getPonto().clone(), v1, v2, v3); break; }
				case 5: { ciclo = aplicaSolucao5(caminho.getPonto().clone(), v1, v2, v3); break; }
				case 6: { ciclo = aplicaSolucao6(caminho.getPonto().clone(), v1, v2, v3); break; }
				case 7: { ciclo = aplicaSolucao7(caminho.getPonto().clone(), v1, v2, v3); break; }
			}
			
			distancia = calculaDistancia(matriz, ciclo);
			//distancia = calculaDistanciaSolucao(ciclo, v1, v2, v3, matriz, c0);
			if (distancia <= melhorCiclo.getDistancia()) {
				melhorCiclo.setDistancia(distancia);
				melhorCiclo.setPonto(ciclo);
			}
			
			System.out.print("(" + i + ")");
			for (int j = 0; j < ciclo.length; j++) {
				System.out.print(" - " + ciclo[j].getId());
			}
			System.out.println(" -> " + distancia);
		}
		
		System.out.println("Melhor distância: " + melhorCiclo.getDistancia());
		return melhorCiclo;	
	}
	
	public double calculaDistanciaSolucao(Ponto[] ciclo, int v1, int v2, int v3, Celula[][] matriz, double c0) {
		
		System.out.println ("Solução: c0 " + c0 + 
				"d1 " + ciclo[v1].getId() + " a " + ciclo[v1+1].getId() + " " + matriz[ciclo[v1].getId()-1][ciclo[v1+1].getId()-1].getDistancia() +
				"d2 " + ciclo[v2].getId() + " a " + ciclo[v2+1].getId() + " " + matriz[ciclo[v2].getId()-1][ciclo[v2+1].getId()-1].getDistancia() +
				"d3 " + ciclo[v3].getId() + " a " + ciclo[v3+1].getId() + " " + matriz[ciclo[v3].getId()-1][ciclo[v3+1].getId()-1].getDistancia());
		
		return c0 + matriz[ciclo[v1].getId()-1][ciclo[v1+1].getId()-1].getDistancia()
                  + matriz[ciclo[v2].getId()-1][ciclo[v2+1].getId()-1].getDistancia()
                  + matriz[ciclo[v3].getId()-1][ciclo[v3+1].getId()-1].getDistancia();
	}
	
	public double calculaDistancia(Celula[][] matriz, Ponto[] ciclo) {
		
		double distancia = 0d;
		for (int i = 0; i < ciclo.length-1; i++) {
			distancia += matriz[ciclo[i].getId()-1][ciclo[i+1].getId()-1].getDistancia();
		}
		return distancia;
	}
	
	public Ponto[] aplicaSolucao1 (Ponto[] ciclo, int v1, int v2, int v3) {
		
		Ponto aux = ciclo[v2+1];
				
		ciclo[v2+1] = ciclo[v3];
		ciclo[v3] = aux;
		
		//Fecha o ciclo
		if (v2 + 1 == ciclo.length-1 || v3 == ciclo.length - 1) { ciclo[0] = ciclo[ciclo.length-1]; }
		if (v2 + 1 == 0 || v3 == 0) { ciclo[ciclo.length-1] = ciclo[0]; }
				
		return ciclo;
	}	
	
	public Ponto[] aplicaSolucao2 (Ponto[] ciclo, int v1, int v2, int v3) {
		
		Ponto aux = ciclo[v1+1];
		
		ciclo[v1+1] = ciclo[v2];
		ciclo[v2] = aux;

		//Fecha o ciclo
		if (v1 + 1 == ciclo.length-1 || v2 == ciclo.length - 1) { ciclo[0] = ciclo[ciclo.length-1]; }
		if (v1 + 1 == 0 || v2 == 0) { ciclo[ciclo.length-1] = ciclo[0]; }
		return ciclo;
	}
	
	public Ponto[] aplicaSolucao3 (Ponto[] ciclo, int v1, int v2, int v3) {
		
		Ponto aux = ciclo[v1+1];
		Ponto aux2 = ciclo[v2+1];
		
		ciclo[v1+1] = ciclo[v2];
		ciclo[v2] = aux;
		ciclo[v2+1] = ciclo[v3];
		ciclo[v3] = aux2;
		
		//Fecha o ciclo
		if (v1 + 1 == ciclo.length-1 || v2 == ciclo.length-1 || v2 + 1 == ciclo.length-1 || v3 == ciclo.length - 1) { ciclo[0] = ciclo[ciclo.length-1]; }
		if (v1 + 1 == 0 || v2 == 0 || v2 + 1 == 0 || v3 == 0) { ciclo[ciclo.length-1] = ciclo[0]; }
		return ciclo;
	}
	
	public Ponto[] aplicaSolucao4 (Ponto[] ciclo, int v1, int v2, int v3) {
		
		Ponto aux = ciclo[v1+1];
		Ponto aux2 = ciclo[v2];
		
		ciclo[v1+1] = ciclo[v2+1];
		ciclo[v2] = ciclo[v3];
		ciclo[v2+1] = aux;
		ciclo[v3] = aux2;

		//Fecha o ciclo
		if (v1+1 == ciclo.length-1 || v2 ==ciclo.length-1 ||  v2 + 1 == ciclo.length-1 || v3 == ciclo.length - 1) { ciclo[0] = ciclo[ciclo.length-1]; }
		if (v1+1 == 0 || v2 == 0 || v2 + 1 == 0 || v3 == 0) { ciclo[ciclo.length-1] = ciclo[0]; }
		return ciclo;
	}
	
	public Ponto[] aplicaSolucao5 (Ponto[] ciclo, int v1, int v2, int v3) {
			
		Ponto aux = ciclo[v2];
		Ponto aux2 = ciclo[v1+1];
		
		ciclo[v1+1] = ciclo[v2+1];
		ciclo[v2] = ciclo[v3];
		ciclo[v2+1] = aux;
		ciclo[v3] = aux2;

		//Fecha o ciclo
		if (v1 + 1 == ciclo.length-1 || v2 == ciclo.length-1 || v2 + 1 == ciclo.length-1 || v3 == ciclo.length - 1) { ciclo[0] = ciclo[ciclo.length-1]; }
		if (v1 + 1 == 0 || v2 == 0 || v2 + 1 == 0 || v3 == 0) { ciclo[ciclo.length-1] = ciclo[0]; }
		return ciclo;
	}
	
	public Ponto[] aplicaSolucao6 (Ponto[] ciclo, int v1, int v2, int v3) {
		
		Ponto aux = ciclo[v1+1];
		Ponto aux2 = ciclo[v2];
		
		ciclo[v1+1] = ciclo[v3];
		ciclo[v2] = ciclo[v2+1];
		ciclo[v2+1] = aux;
		ciclo[v3] = aux2;

		//Fecha o ciclo
		if (v1 + 1 == ciclo.length-1 || v2 == ciclo.length-1 || v2 + 1 == ciclo.length-1 || v3 == ciclo.length - 1) { ciclo[0] = ciclo[ciclo.length-1]; }
		if (v1+1 == 0 || v2 == 0 || v2 + 1 == 0 || v3 == 0) { ciclo[ciclo.length-1] = ciclo[0]; }
		return ciclo;
	}
	
	public Ponto[] aplicaSolucao7 (Ponto[] ciclo, int v1, int v2, int v3) {
		
		Ponto aux = ciclo[v2];
		Ponto aux2 = ciclo[v1+1];
		
		ciclo[v1+1] = ciclo[v3];
		ciclo[v2] = ciclo[v2+1];
		ciclo[v2+1] = aux;
		ciclo[v3] = aux2;
		
		//Fecha o ciclo
		if (v1 + 1 == ciclo.length-1 || v2 == ciclo.length-1 || v2 + 1 == ciclo.length-1 || v3 == ciclo.length - 1) { ciclo[0] = ciclo[ciclo.length-1]; }
		if (v1+1 == 0 || v2 == 0 || v2 + 1 == 0 || v3 == 0) { ciclo[ciclo.length-1] = ciclo[0]; }
		return ciclo;
	}
}