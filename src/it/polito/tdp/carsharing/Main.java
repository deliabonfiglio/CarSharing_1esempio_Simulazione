package it.polito.tdp.carsharing;

import it.polito.tdp.carsharing.model.Simulator;

public class Main {

	public static void main(String[] args) {
		Simulator sim = new Simulator(10);
//imposto la creazione dei clienti, in modo che venga creato un cliente ogni 8 minuti

		for (int time = 8*60; time <=17*60; time= time +10){
			sim.addCliente(time);
		}
		
		sim.run();
		
		System.out.format("Clienti totali : %d\n", sim.getClientiTot());
		System.out.format("Clienti insoddisfatti: %d\n", sim.getClientiInsoddisfatti());		
	}

}
