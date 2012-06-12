package br.uece.threeopt.heuristica.caixeiroviajante;

/**
 * @author patrick cunha
 * @version 1.0
 * A classe ponto representa cada cidade que o
 * caixeiro viajante deve visitar 
 * */
public class Ponto{
	
	private Integer id;
	private Double coordX;
	private Double coordY;
	private Boolean usado = false;
	private Boolean origem = false;
	private Integer posicao;
	
	public Ponto() {}
	
	public Ponto(Ponto ponto) {
		this.id = ponto.getId();
		this.coordX = ponto.getCoordX();
		this.coordY = ponto.getCoordY();
		this.usado = false;
		this.origem = false;
		this.posicao = ponto.getPosicao();
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getCoordX() {
		return coordX;
	}
	public void setCoordX(Double coordX) {
		this.coordX = coordX;
	}
	public Double getCoordY() {
		return coordY;
	}
	public void setCoordY(Double coordY) {
		this.coordY = coordY;
	}	
	public Boolean isUsado() {
		return usado;
	}
	public void setUsado(Boolean usado) {
		this.usado = usado;
	}	
	public Boolean isOrigem() {
		return origem;
	}
	public void setOrigem(Boolean origem) {
		this.origem = origem;
	}	
	public Integer getPosicao() {
		return posicao;
	}
	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coordX == null) ? 0 : coordX.hashCode());
		result = prime * result + ((coordY == null) ? 0 : coordY.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Ponto other = (Ponto) obj;
		if (coordX == null) {
			if (other.coordX != null)
				return false;
		} else if (!coordX.equals(other.coordX))
			return false;
		if (coordY == null) {
			if (other.coordY != null)
				return false;
		} else if (!coordY.equals(other.coordY))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return id.toString();
	}	
}
