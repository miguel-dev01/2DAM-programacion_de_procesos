package main;

import domain.MataServer;

public class Main {

	public static void main(String[] args) {
		
		final int MAX_CLIENTES =2;
		MataServer server = new MataServer(MAX_CLIENTES);
		server.iniciar();
		
	}

}
