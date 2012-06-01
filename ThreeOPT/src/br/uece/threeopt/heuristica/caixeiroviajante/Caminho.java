package br.uece.threeopt.heuristica.caixeiroviajante;

public class Caminho implements Cloneable{
	
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
}
