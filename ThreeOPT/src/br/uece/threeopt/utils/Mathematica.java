package br.uece.threeopt.utils;

import br.uece.threeopt.heuristica.caixeiroviajante.Celula;
import br.uece.threeopt.heuristica.caixeiroviajante.Ponto;

/**
 * @author patrick cunha
 * @version 1.0
 * Classe responsavel por encapsular qualquer calcula matematico
 * */
public class Mathematica {

	public static Celula calculaDistancia(Ponto origem, Ponto destino) {

		Double distancia = Math.sqrt(Math.pow(destino.getCoordY() - origem.getCoordY(), 2)
										+ Math.pow(destino.getCoordX() - origem.getCoordX(), 2));	

		return new Celula(origem, destino, distancia);
	}

	public static void calculaDistancia(Celula celula) {
		
		Double distancia = Math.sqrt(Math.pow(celula.getDestino().getCoordY() - celula.getOrigem().getCoordY(), 2)
				+ Math.pow(celula.getDestino().getCoordX() - celula.getOrigem().getCoordX(), 2));
		celula.setDistancia(distancia);
		
	}

}
