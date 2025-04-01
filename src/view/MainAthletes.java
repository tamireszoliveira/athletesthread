package view;

import athletesthread.athletesthread;

public class MainAthletes {
	public static void main(String[] args) {
		System.out.println("iniciando prova..");
		
		athletesthread[] atletas = new athletesthread[25];
		for(int i = 0; i< 25; i++) {
			atletas[i] = new athletesthread(i+1);
			atletas[i].start();
			
		}
		

	}
}
