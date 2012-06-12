package br.uece.threeopt.heuristica.caixeiroviajante;

import java.util.Arrays;

import br.uece.threeopt.heuristica.sequenciamento.Sequencia;

public class Caminho implements Cloneable, Comparable<Caminho>{
	
	private Ponto[] ponto = null;
	private Double distancia = 0d;
	
	public Ponto[] getPonto() {
		return ponto;
	}
	
	public void setPonto(Ponto[] ponto) {
		this.ponto = ponto;
	}
	
	public Double getDistancia() {
		return distancia;
	}
	
	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}
	
	@Override
	public Caminho clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (Caminho)super.clone();
	}

	@Override
	public String toString() {
		return Arrays.toString(ponto) + " -> " + distancia;
	}
	
	@Override
	public int compareTo(Caminho o) {
		return distancia.compareTo(o.getDistancia());
	}
}
