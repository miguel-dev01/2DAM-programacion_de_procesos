package domain;

public class Buffer {
	private char contenido;
	private boolean disponible;
	public Buffer() {
		this.contenido = ' ';
		this.disponible = false;
	}
	public char recoger(){
		if(disponible){
			disponible = false;
			return contenido;
		}
		return ('#');
	}
	public synchronized void poner(char c){
		if(!disponible) {
			contenido = c;
			disponible = true;
		}
	}
}
