package br.uece.threeopt.heuristica.sequenciamento;

public class Job {
	
	private Integer id;
	private Integer tempo;
	private Integer atraso;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTempo() {
		return tempo;
	}
	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}
	public Integer getAtraso() {
		return atraso;
	}
	public void setAtraso(Integer atraso) {
		this.atraso = atraso;
	}
}
