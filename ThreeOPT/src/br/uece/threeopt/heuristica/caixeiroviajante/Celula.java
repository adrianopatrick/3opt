package br.uece.threeopt.heuristica.caixeiroviajante;

/**
 * @author patrick cunha
 * @version 1.0 
 * classe que representa uma célula da matriz
 * de adjacencia informando a distancia entre os pontos envolvidos
 * */
public class Celula implements Comparable<Celula>{
	
	public Celula() {
	}
	
	public Celula(Ponto origem, Ponto destino, Double distancia) {
		this.origem = origem;
		this.destino = destino;
		this.distancia = distancia;
	}
	
	private Ponto origem;
	private Ponto destino;
	private Double distancia;
	private Boolean usado;
	
	public Ponto getOrigem() {
		return origem;
	}
	public void setOrigem(Ponto origem) {
		this.origem = origem;
	}
	public Ponto getDestino() {
		return destino;
	}
	public void setDestino(Ponto destino) {
		this.destino = destino;
	}
	public Double getDistancia() {
		return distancia;
	}
	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}
	public Boolean getUsado() {
		return usado;
	}
	public void setUsado(Boolean usado) {
		this.usado = usado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destino == null) ? 0 : destino.hashCode());
		result = prime * result
				+ ((distancia == null) ? 0 : distancia.hashCode());
		result = prime * result + ((origem == null) ? 0 : origem.hashCode());
		result = prime * result + ((usado == null) ? 0 : usado.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Celula other = (Celula) obj;
		if (destino == null) {
			if (other.destino != null)
				return false;
		} else if (!destino.equals(other.destino))
			return false;
		if (distancia == null) {
			if (other.distancia != null)
				return false;
		} else if (!distancia.equals(other.distancia))
			return false;
		if (origem == null) {
			if (other.origem != null)
				return false;
		} else if (!origem.equals(other.origem))
			return false;
		if (usado == null) {
			if (other.usado != null)
				return false;
		} else if (!usado.equals(other.usado))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Celula [origem=" + origem + ", destino=" + destino
				+ ", distancia=" + distancia + ", usado=" + usado + "]";
	}

	@Override
	public int compareTo(Celula celula) {
		return this.getDistancia().compareTo(celula.getDistancia());
	}
	
}
