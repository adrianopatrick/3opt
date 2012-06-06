package br.uece.threeopt.heuristica.sequenciamento;

public class Job {
	
	private Maquina maquina;
	private Tarefa tarefa;
	private Integer tempo;
	
	public Maquina getMaquina() {
		return maquina;
	}
	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
	}
	public Tarefa getTarefa() {
		return tarefa;
	}
	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}
	public Integer getTempo() {
		return tempo;
	}
	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}
	
	@Override
	public String toString() {
		return "Job [maquina=" + maquina + ", tarefa=" + tarefa + ", tempo="
				+ tempo + "]";
	}
	
}
