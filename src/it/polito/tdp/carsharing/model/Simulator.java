package it.polito.tdp.carsharing.model;

import java.util.PriorityQueue;

import it.polito.tdp.carsharing.model.Event.EventType;

public class Simulator {
	//parametri simulazione = valori costanti o quasi, impostati all'inizio
	
	private int NC; 						//numero di auto
	private int TRAVEL_MINIMUM_TIME =60;	//UN ORA = minimo tempo di affitto auto
	private int TRAVEL_MAX_NUM = 3;			//NUMERO DI MASSIMO DI TRAVEL MIN TIME per cui l'auto può stare fuori
		
	//stato del modello
	private int autoPresenti;				//ancora in deposito
	
	//varibili di interesse = RISULTATI DELLA SIMULAZIONE, LI VORRO' LEGGERE ALLA FINE
	
	private int clientiTot=0;				//clienti tot arrivati al deposito
	private int clientiInsoddisfatti=0;		//numero di clienti non serviti
	
	//lista degli eventi
	private PriorityQueue<Event> queue;
	
	public Simulator(int nC){
		NC=nC;
		this.queue= new PriorityQueue<>();
		this.autoPresenti= this.NC;
				
	}
	
	public void addCliente(int time){
		//aggiunge i clienti nella lista degli eventi che diventeranno futuri ogni tot qnd il sim partirà
		queue.add(new Event(time, EventType.NUOVO_CLIENTE));
		
	}
	
	public void run(){
		//entra in un ciclo in cui estrae un  nuovo evento e lo elabora
		//poll estrae il primo ogg della coda e lo rimuove(cancellandolo) dalla coda
		while(!queue.isEmpty()){
			Event e = queue.poll();	//finchè non si svuota
			
			//devo elaborare l'evento ogni volta che lo estraggo = processEvent()
			
			switch(e.getType()){
			
			case NUOVO_CLIENTE:
				if(autoPresenti == 0){
					//non ho auto
					this.clientiTot++;
					this.clientiInsoddisfatti++;
					
					System.out.format("Time %d - cliente insoddisfatto\n", e.getTime());
					
				} else {
					//affitta un'auto
					this.clientiTot++;
					this.autoPresenti--;
					
					//devo ricordarmi che l'auto rientrerà dopo un certo tempo==>NUOVO EVENTO di AUTO_RESTITUITA
					int durata= this.TRAVEL_MINIMUM_TIME*(int)(1+Math.random()* this.TRAVEL_MAX_NUM);
					
					queue.add(new Event(e.getTime()+durata, EventType.AUTO_RESTITUITA));
					
					System.out.format("Time %d - auto prestata (rientra alle %d), rimanenti %d\n", e.getTime(),e.getTime()+durata, this.autoPresenti);
				}				
				break;
				
			case AUTO_RESTITUITA:
				//mi segno che l'auto è rientrata e NON GENERA ALTRI EVENTI.
				this.autoPresenti++;
				
				System.out.format("Time %d - auto rientrata, rimanenti %d\n", e.getTime(), this.autoPresenti);

				
				break;
			}
		}
		
	}

	/**
	 * @return the nC
	 */
	public int getNC() {
		return NC;
	}

	/**
	 * @param nC the nC to set
	 */
	public void setNC(int nC) {
		NC = nC;
		this.autoPresenti=nC;
	}

	/**
	 * @return the tRAVEL_MINIMUM_TIME
	 */
	public int getTRAVEL_MINIMUM_TIME() {
		return TRAVEL_MINIMUM_TIME;
	}

	/**
	 * @param tRAVEL_MINIMUM_TIME the tRAVEL_MINIMUM_TIME to set
	 */
	public void setTRAVEL_MINIMUM_TIME(int tRAVEL_MINIMUM_TIME) {
		TRAVEL_MINIMUM_TIME = tRAVEL_MINIMUM_TIME;
	}

	/**
	 * @return the tRAVEL_MAX_NUM
	 */
	public int getTRAVEL_MAX_NUM() {
		return TRAVEL_MAX_NUM;
	}

	/**
	 * @param tRAVEL_MAX_NUM the tRAVEL_MAX_NUM to set
	 */
	public void setTRAVEL_MAX_NUM(int tRAVEL_MAX_NUM) {
		TRAVEL_MAX_NUM = tRAVEL_MAX_NUM;
	}

	
	
	//sono la variabili di interesse quindi posso solo leggerle!
	/**
	 * @return the clientiTot
	 */
	public int getClientiTot() {
		return clientiTot;
	}

	/**
	 * @return the clientiInsoddisfatti
	 */
	public int getClientiInsoddisfatti() {
		return clientiInsoddisfatti;
	}
	
	
}
