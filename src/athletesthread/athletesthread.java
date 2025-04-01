package athletesthread;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class athletesthread extends Thread {
	
		private final int id;
		private int pts;
		private int shotpts;
		private static int fnsline =1;
		private static final Semaphore semaforo = new Semaphore(5);
		private static final Random random = new Random();
		private static athletesthread[]ranking = new athletesthread[25];
		private static int rankingIndex = 0;
		
		public athletesthread(int id) {
			this.id = id; 
			
		}
		
	private void running() throws InterruptedException{
		int d = 3000;
		int m = 20+random.nextInt(6); // m por intervalo
		int time = 30; // t por intervalo
		
		System.out.println("Atleta " + id + " iniciou a corrida.");
		while(d > 0) {
			Thread.sleep(time);
			d -= m;
		}
		synchronized(athletesthread.class) {
			pts = 260 - (fnsline * 10);
			fnsline++; 
		}
		System.out.println("Atleta " + id + " finalizou a corrida e recebeu " + pts + " pontos.");	
	}
	private void targetshots()throws InterruptedException{
		semaforo.acquire();
		System.out.println("Atleta " + id + " pegou uma arma para tiros.");
		
		pts = 0;
		for(int i = 1; i<=3; i++) {
			int shotime = 500 + random.nextInt(2501);
			Thread.sleep(shotime);
			int shotpts = random.nextInt(11);
			pts += shotpts;
			System.out.println("Atleta " + id + " realizou tiro " + i + " e obteve " + shotpts + " pontos.");
			
		}
		semaforo.release();
		System.out.println("Atleta " + id + " finalizou os tiros e seguirá para o ciclismo");
	}
	private void cycling()throws InterruptedException{
		int d = 5000;
		int m = 30 + random.nextInt(11); // m por intervalo
		int time = 40; // tempo por intervalo
		
		System.out.println("Atleta " + id + " iniciou o ciclismo.");
		while(d>0) {
			Thread.sleep(time);
			d-= m;
		}
		System.out.println("Atleta " + id + " finalizou o ciclismo.");
	}
	@Override
	public void run() {
		try {
			running();
			targetshots();
			cycling();
			
			synchronized(ranking){
				pts+=shotpts;
				ranking[rankingIndex++] = this; 
			}
		}catch(InterruptedException e) {
			Thread.currentThread().interrupt();
			
		}
	}
	public static void showranking() {
		for(int i = 0; i < ranking.length -1; i++){
			for(int j = i+1; j< ranking.length;j++) {
				if(ranking[j] != null && ranking[i].pts < ranking[j].pts) {
					athletesthread aux = ranking[i];
					ranking[i] = ranking[j];
					ranking[j] = aux;
				}
			}
		}
		
		System.out.println("\n--------------------- Ranking Final ----------------------------");
		for(int i = 0; i<ranking.length; i++) {
			if (ranking[i] != null) {
				System.out.println("atleta " + ranking[i].id+ " = " + ranking[i].pts + " pontos" );
			}
		}
	}
}
