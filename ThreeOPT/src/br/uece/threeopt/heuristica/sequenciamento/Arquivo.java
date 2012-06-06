package br.uece.threeopt.heuristica.sequenciamento;

public class Arquivo {
	
	private String path;
	private String nome;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return "Arquivo [path=" + path + ", nome=" + nome + "]";
	}

}
