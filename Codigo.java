import java.awt.Color;

/***********************************************************************/
/*                                                                     */
/* Para jogar:                                                         */
/*                                                                     */
/*    - cima, baixo, esquerda, direita: movimentação do player.        */
/*    - control: disparo de projéteis.                                 */
/*    - ESC: para sair do jogo.                                        */
/*                                                                     */
/***********************************************************************/



//____________________________________________________________________________________________________________

//Transformação de Procedural para COO
//____________________________________________________________________________________________________________



interface IPonto2D {
	
	/*Getters*/
	
	public double getPosicaoX();
	public double getPosicaoY();
	public double getVelocidadeVX();
	public double getVelocidadeVY();
	public double getRadius();
	public int getState();
	
	/*Setters*/
	
	public void setPosicaoX(double posicao_X);
	public void setPosicaoY(double posicao_Y);
	public void setVelocidadeVX(double velocidade_VX);
	public void setVelocidadeVY(double velocidade_VY);
	public void setRadius(double radius);
	public void setState(int state);
	
}

interface IFlamable {
	
	/*Checagem da colisão*/
	
	public boolean checkCollision (IPonto2D elemento, long currentTime);
	
	/*Getters do intervalo da Explosão*/
	
	public double getExplosionStart();
	public double getExplosionEnd();
	
	
	/*Setters do intervalo da Explosão*/
	
	public void setExplosionStart(double start);
	public void setExplosionEnd(double end);
	
}

class ElementoBase implements IPonto2D {
	
	private double posicao_X;       // coordenada x
	private double posicao_Y;       // coordenada y
	private double velocidade_VX;   // velocidade no eixo x
	private double velocidade_VY;   // velocidade no eixo y
	private double radius;          // raio(tamanho)
	private int state;              // estado
	
	/*Constructor*/
	
	public ElementoBase(double posicao_X, double posicao_Y, double velocidade_VX, double velocidade_VY, double radius, int state) {
	
		setPosicaoX(posicao_X);
		setPosicaoY(posicao_Y);
		setVelocidadeVX(velocidade_VX);
		setVelocidadeVX(velocidade_VY);
		this.radius = radius;
		this.state = state;
		
	}
	
	/*metodos getters e setters*/
	
	public double getPosicaoX() {
		return posicao_X;
	}

	public double getPosicaoY() {
		return posicao_Y;
	}

	public double getVelocidadeVX() {
		return velocidade_VX;
	}

	public double getVelocidadeVY() {
		return velocidade_VY;
	}

	public double getRadius() {
		return radius;
	}

	public int getState() {
		return state;
	}

	public void setPosicaoX(double posicao_X) {
		this.posicao_X = posicao_X;
	}

	public void setPosicaoY(double posicao_Y) {
		this.posicao_Y = posicao_Y;
	}

	public void setVelocidadeVX(double velocidade_VX) {
		this.velocidade_VX = velocidade_VX;
	}

	public void setVelocidadeVY(double velocidade_VY) {
		this.velocidade_VY = velocidade_VY;
	}

	public void setRadius(double radius) {
		this.radius = radius;
		
	}

	public void setState(int state) {
		this.state = state;
	}
	
}




class Player implements IPonto2D, IFlamable {
	
	private IPonto2D ponto; //composição
	private long nextShot;
	private double explosion_start;
	private double explosion_end;
	
	/*CONSTRUTORES*/
	
	public Player (double posicao_X, double posicao_Y, double velocidade_VX, double velocidade_VY, double radius, int state, long currentTime){
		
		ponto = new ElementoBase( posicao_X, posicao_Y, velocidade_VX, velocidade_VY, radius, state);
		nextShot = currentTime;
		explosion_start = 0;
		explosion_end = 0;
	}
	
	
	
	/*metodos getters e setters da composição*/
	
	public double getPosicaoX() {
		return ponto.getPosicaoX();
	}

	public double getPosicaoY() {
		return ponto.getPosicaoY();
	}

	public double getVelocidadeVX() {
		return ponto.getVelocidadeVX();
	}

	public double getVelocidadeVY() {
		return ponto.getVelocidadeVY();
	}

	public double getRadius() {
		return ponto.getRadius();
	}

	public int getState() {
		return ponto.getState();
	}

	public void setPosicaoX(double posicao_X) {
		ponto.setPosicaoX(posicao_X);
	}

	public void setPosicaoY(double posicao_Y) {
		ponto.setPosicaoY(posicao_Y);
	}

	public void setVelocidadeVX(double velocidade_VX) {
		ponto.setVelocidadeVX(velocidade_VX);
	}

	public void setVelocidadeVY(double velocidade_VY) {
		ponto.setVelocidadeVY(velocidade_VY);
	}

	public void setRadius(double radius) {
		ponto.setRadius(radius);
		
	}

	public void setState(int state) {
		ponto.setState(state);
	}
	
	/*Getter e Setter nextShot*/
	
	public long getNextShot(){
		return nextShot;
	}
	
	public void setNextShot(long nextShot){
		this.nextShot = nextShot;
	}
	
	
	/*Getters do intervalo da Explosão*/
	
	public double getExplosionStart(){
		return explosion_start;
	}
	
	public double getExplosionEnd(){
		return explosion_end;
	}
	
	
	/*Setters do intervalo da Explosão*/
	
	public void setExplosionStart(double start){
		this.explosion_start = start;
	}
	
	public void setExplosionEnd(double end){
		this.explosion_end = end;
	}
	
	
	/*Verificação de colisão*/
	
	public boolean checkCollision (IPonto2D elemento, long currentTime){
		
		//Calculo da distancia entre o elemento e o player//
		
		double dx = elemento.getPosicaoX() - ponto.getPosicaoX();
		double dy = elemento.getPosicaoY() - ponto.getPosicaoY();
		double dist = Math.sqrt(dx * dx + dy * dy);
		
		
		//Se a distancia for de colisão, retorne verdadeiro e aplique as alterações//
		
		if(dist < (ponto.getRadius() + elemento.getRadius()) * 0.8){
			
			ponto.setState(2);
	
			this.setExplosionStart(currentTime);
			this.setExplosionEnd(currentTime + 2000);
			
			return true;
		}
		
		return false;
	}
	
}


/*Classe de Projetil do player*/

class Projectile1 extends ElementoBase{
	
	public Projectile1 (double posicao_X, double posicao_Y, int state){
		
		super(posicao_X, posicao_Y, 0.0, -1.0, 0.0, state);
	}
}


/*Classe de Projetil dos Inimigos*/

class Projectile2 extends ElementoBase{
	
	private double angle;
	
	public Projectile2 (double posicao_X, double posicao_Y, int state, double angle){
		
		super(posicao_X, posicao_Y, 0.0, -1.0, 0.0, state);
		this.angle = angle;
	}
	
	/*Getter e Setter de angle*/
	
	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}
}



/*Classe Abstrata do Enemy*/
	
abstract class Enemy implements IPonto2D, IFlamable {
	
	private IPonto2D ponto; //composição
	private double velocidade_VR;
	private double angle;
	private long nextEnemy;
	private double explosion_start;
	private double explosion_end;
	
	//*CONSTRUTORES*/
	
	
	public Enemy (double posicao_X, double posicao_Y, double velocidade_VX, double velocidade_VY, double radius, int state, double velocidade_VR, double angle, long nextEnemy){
		
		ponto = new ElementoBase( posicao_X, posicao_Y, velocidade_VX, velocidade_VY, radius, state);
		this.velocidade_VR = velocidade_VR;
		this. angle = angle;
		this.nextEnemy = nextEnemy;
		explosion_start = 0;
		explosion_end = 0;
	}
	
	
	/*metodos getters e setters da composição*/
	
	public double getPosicaoX() {
		return ponto.getPosicaoX();
	}

	public double getPosicaoY() {
		return ponto.getPosicaoY();
	}

	public double getVelocidadeVX() {
		return ponto.getVelocidadeVX();
	}

	public double getVelocidadeVY() {
		return ponto.getVelocidadeVY();
	}

	public double getRadius() {
		return ponto.getRadius();
	}

	public int getState() {
		return ponto.getState();
	}
	
	public void setPosicaoX(double posicao_X) {
		ponto.setPosicaoX(posicao_X);
	}

	public void setPosicaoY(double posicao_Y) {
		ponto.setPosicaoY(posicao_Y);
	}

	public void setVelocidadeVX(double velocidade_VX) {
		ponto.setVelocidadeVX(velocidade_VX);
	}

	public void setVelocidadeVY(double velocidade_VY) {
		ponto.setVelocidadeVY(velocidade_VY);
	}

	public void setRadius(double radius) {
		ponto.setRadius(radius);
		
	}

	public void setState(int state) {
		ponto.setState(state);
	}
	
	
	/*Getters dos novos atributos*/
	
	public double getVelocidadeVR(){
		return velocidade_VR;
	}
	
	public double getAngle(){
		return angle;
	}
	
	public long getNextEnemy(){
		return nextEnemy;
	}
	
	
	/*Setters dos novos atributos*/
	
	public void setVelocidadeVR(double velocidade_VR) {
		this.velocidade_VR = velocidade_VR;
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	public void setNextEnemy(long nextEnemy) {
		this.nextEnemy = nextEnemy;
	}
	
	
	/*Getters do intervalo da Explosão*/
	
	public double getExplosionStart(){
		return explosion_start;
	}
	
	public double getExplosionEnd(){
		return explosion_end;
	}
	
	
	/*Setters do intervalo da Explosão*/
	
	public void setExplosionStart(double start){
		this.explosion_start = start;
	}
	
	public void setExplosionEnd(double end){
		this.explosion_end = end;
	}
	
	
	/*Verificação de colisão*/
	
	public boolean checkCollision (IPonto2D elemento, long currentTime){
		
		//Calculo da distancia entre o elemento e o player//
		
		double dx = elemento.getPosicaoX() - ponto.getPosicaoX();
		double dy = elemento.getPosicaoY() - ponto.getPosicaoY();
		double dist = Math.sqrt(dx * dx + dy * dy);
		
		
		//Se a distancia for de colisão, retorne verdadeiro e aplique as alterações//
		
		if(dist < ponto.getRadius()){
			
			ponto.setState(2);
	
			this.setExplosionStart(currentTime);
			this.setExplosionEnd(currentTime + 500);
			
			return true;
		}
		
		return false;
	}
}



class Enemy1 extends Enemy{
	
	long nextShot;
	
	public Enemy1(double posicao_X, double posicao_Y, double velocidade_VX, double velocidade_VY, double radius, int state, double velocidade_VR, double angle, long nextEnemy, long nextShot){
		
		super(posicao_X, posicao_Y, velocidade_VX, velocidade_VY, radius, state, velocidade_VR, angle, nextEnemy);
		this.nextShot = nextShot;
	}
	
	/*Getter e Setter nextShot*/
	
	public long getNextShot(){
		return nextShot;
	}
	
	public void setNextShot(long nextShot){
		this.nextShot = nextShot;
	}
}

/* planos de fundo */
class BackGround {
	private double background_X;
	private double background_Y;
	private double speed;
	private double count;
	
	
	public BackGround(double background_X, double background_Y, double speed) {
		setBackground_X(background_X);
		setBackground_Y(background_Y);
		this.speed = speed;
		this.count = 0.0;
	}
	
	public double getBackground_X() {
		return background_X;
	}
	public void setBackground_X(double background_X) {
		this.background_X = background_X;
	}
	public double getBackground_Y() {
		return background_Y;
	}
	public void setBackground_Y(double background_Y) {
		this.background_Y = background_Y;
	}
	
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getCount() {
		return count;
	}
	public void setCount(double count) {
		this.count = count;
	}
	
}




//__________________________________________________________________

//MAIN
//__________________________________________________________________








class MainCOO{
	
	/* Constantes relacionadas aos estados que os elementos   */
	/* do jogo (player, projeteis ou inimigos) podem assumir. */
	
	public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int EXPLODING = 2;
	
	/* Espera, sem fazer nada, até que o instante de tempo atual seja */
	/* maior ou igual ao instante especificado no parâmetro "time".    */
	
	public static void busyWait(long time){
		
		while(System.currentTimeMillis() < time) Thread.yield();
	}
	
	/* Encontra e devolve o primeiro índice do  */
	/* array referente a uma posição "inativa". */
	
	public static int findFreeIndex(int [] stateArray){
		
		int i;
		
		for(i = 0; i < stateArray.length; i++){
			
			if(stateArray[i] == INACTIVE) break;
		}
		
		return i;
	}
	
	/* Encontra e devolve o conjunto de índices (a quantidade */
	/* de índices é defnida através do parâmetro "amount") do */
	/* array referente a posições "inativas".                 */ 

	public static int [] findFreeIndex(int [] stateArray, int amount){

		int i, k;
		int [] freeArray = new int[amount];

		for(i = 0; i < freeArray.length; i++) freeArray[i] = stateArray.length; 
		
		for(i = 0, k = 0; i < stateArray.length && k < amount; i++){
				
			if(stateArray[i] == INACTIVE) { 
				
				freeArray[k] = i; 
				k++;
			}
		}
		
		return freeArray;
	}
	
	
	
	/* Método principal */
	
	
	
	public static void main(String [] args){

		/* Indica que o jogo está em execução */

		boolean running = true;

		/* variáveis usadas no controle de tempo efetuado no main loop */
		
		long delta;
		long currentTime = System.currentTimeMillis();

		/* variáveis do player */
		
		Player p1 = new Player(GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90, 0.25, 0.25, 12.0, ACTIVE, currentTime);
		
		/*int player_state = ACTIVE;						// estado
		double player_X = GameLib.WIDTH / 2;					// coordenada x
		double player_Y = GameLib.HEIGHT * 0.90;				// coordenada y
		double player_VX = 0.25;						// velocidade no eixo x
		double player_VY = 0.25;						// velocidade no eixo y
		double player_radius = 12.0;						// raio (tamanho aproximado do player)
		double player_explosion_start = 0;					// instante do início da explosão
		double player_explosion_end = 0;					// instante do final da explosão
		long player_nextShot = currentTime;					// instante a partir do qual pode haver um próximo tiro

		/* variáveis dos projéteis disparados pelo player */
		
		Projectile1 [] proj_player = new Projectile1[10];
		
		/*int [] projectile_states = new int[10];					// estados
		double [] projectile_X = new double[10];				// coordenadas x
		double [] projectile_Y = new double[10];				// coordenadas y
		double [] projectile_VX = new double[10];				// velocidades no eixo x
		double [] projectile_VY = new double[10];				// velocidades no eixo y

		/* variáveis dos inimigos tipo 1 */
		
		Enemy1 [] vet_enemy1 = new Enemy1[10];
		
		/*int [] enemy1_states = new int[10];					// estados
		double [] enemy1_X = new double[10];					// coordenadas x
		double [] enemy1_Y = new double[10];					// coordenadas y
		double [] enemy1_V = new double[10];					// velocidades
		double [] enemy1_angle = new double[10];				// ângulos (indicam direção do movimento)
		double [] enemy1_RV = new double[10];					// velocidades de rotação
		double [] enemy1_explosion_start = new double[10];			// instantes dos inícios das explosões
		double [] enemy1_explosion_end = new double[10];			// instantes dos finais da explosões
		long [] enemy1_nextShoot = new long[10];				// instantes do próximo tiro
		double enemy1_radius = 9.0;						// raio (tamanho do inimigo 1)
		long nextEnemy1 = currentTime + 2000;					// instante em que um novo inimigo 1 deve aparecer
		
		/* variáveis dos inimigos tipo 2 */
		
		/*int [] enemy2_states = new int[10];					// estados
		double [] enemy2_X = new double[10];					// coordenadas x
		double [] enemy2_Y = new double[10];					// coordenadas y
		double [] enemy2_V = new double[10];					// velocidades
		double [] enemy2_angle = new double[10];				// ângulos (indicam direção do movimento)
		double [] enemy2_RV = new double[10];					// velocidades de rotação
		double [] enemy2_explosion_start = new double[10];			// instantes dos inícios das explosões
		double [] enemy2_explosion_end = new double[10];			// instantes dos finais das explosões
		double enemy2_spawnX = GameLib.WIDTH * 0.20;				// coordenada x do próximo inimigo tipo 2 a aparecer
		int enemy2_count = 0;							// contagem de inimigos tipo 2 (usada na "formação de voo")
		double enemy2_radius = 12.0;						// raio (tamanho aproximado do inimigo 2)
		long nextEnemy2 = currentTime + 7000;					// instante em que um novo inimigo 2 deve aparecer
		
		/* variáveis dos projéteis lançados pelos inimigos (tanto tipo 1, quanto tipo 2) */
		
		Projectile1 [] proj_enemy1 = new Projectile1[100];
		
		/*int [] e_projectile_states = new int[200];				// estados
		double [] e_projectile_X = new double[200];				// coordenadas x
		double [] e_projectile_Y = new double[200];				// coordenadas y
		double [] e_projectile_VX = new double[200];				// velocidade no eixo x
		double [] e_projectile_VY = new double[200];				// velocidade no eixo y
		double e_projectile_radius = 2.0;					// raio (tamanho dos projéteis inimigos)
		
		/* estrelas que formam o fundo de primeiro plano */
		
		BackGround [] vet_background1 = new BackGround[20];
		
		/*double [] background1_X = new double[20];
		double [] background1_Y = new double[20];
		double background1_speed = 0.070;
		double background1_count = 0.0;
		
		/* estrelas que formam o fundo de segundo plano */
		
		BackGround [] vet_background2 = new BackGround[50];
		
		/*double [] background2_X = new double[50];
		double [] background2_Y = new double[50];
		double background2_speed = 0.045;
		double background2_count = 0.0;
		
		/* inicializações */
		
		/*for(int i = 0; i < projectile_states.length; i++) projectile_states[i] = INACTIVE;
		for(int i = 0; i < e_projectile_states.length; i++) e_projectile_states[i] = INACTIVE;
		for(int i = 0; i < enemy1_states.length; i++) enemy1_states[i] = INACTIVE;
		for(int i = 0; i < enemy2_states.length; i++) enemy2_states[i] = INACTIVE;
		
		for(int i = 0; i < background1_X.length; i++){
			
			background1_X[i] = Math.random() * GameLib.WIDTH;
			background1_Y[i] = Math.random() * GameLib.HEIGHT;
		}
		
		for(int i = 0; i < background2_X.length; i++){
			
			background2_X[i] = Math.random() * GameLib.WIDTH;
			background2_Y[i] = Math.random() * GameLib.HEIGHT;
		}
						
		/* iniciado interface gráfica */
		
		GameLib.initGraphics();
		//GameLib.initGraphics_SAFE_MODE();  // chame esta versão do método caso nada seja desenhado na janela do jogo.	
		
		/*************************************************************************************************/
		/*                                                                                               */
		/* Main loop do jogo                                                                             */
		/* -----------------                                                                             */
		/*                                                                                               */
		/* O main loop do jogo executa as seguintes operações:                                           */
		/*                                                                                               */
		/* 1) Verifica se há colisões e atualiza estados dos elementos conforme a necessidade.           */
		/*                                                                                               */
		/* 2) Atualiza estados dos elementos baseados no tempo que correu entre a última atualização     */
		/*    e o timestamp atual: posição e orientação, execução de disparos de projéteis, etc.         */
		/*                                                                                               */
		/* 3) Processa entrada do usuário (teclado) e atualiza estados do player conforme a necessidade. */
		/*                                                                                               */
		/* 4) Desenha a cena, a partir dos estados dos elementos.                                        */
		/*                                                                                               */
		/* 5) Espera um período de tempo (de modo que delta seja aproximadamente sempre constante).      */
		/*                                                                                               */
		/*************************************************************************************************/
		
		while(running){
		
			/* Usada para atualizar o estado dos elementos do jogo    */
			/* (player, projéteis e inimigos) "delta" indica quantos  */
			/* ms se passaram desde a última atualização.             */
			
			delta = System.currentTimeMillis() - currentTime;
			
			/* Já a variável "currentTime" nos dá o timestamp atual.  */
			
			currentTime = System.currentTimeMillis();
			
			/***************************/
			/* Verificação de colisões */
			/***************************/
			
			if (p1.getState() == ACTIVE){
				
				/* colisões player - projeteis (inimigo) */
				
				for (Projectile1 pj1 : proj_enemy1){
					
					boolean x = p1.checkCollision(pj1, currentTime);
				}
				
				/* colisões player - inimigo1 */
				
				for (Enemy1 eny1 : vet_enemy1){
					
					boolean x = p1.checkCollision(eny1, currentTime);
				}
			}
			
			/* colisões projeteis (player) - inimigos */
			
			for (Enemy1 eny1 : vet_enemy1){
				
				for (Projectile1 pj1 : proj_player){
					
					boolean x = eny1.checkCollision(pj1, currentTime);
				}
				
			}
			
			/***************************/
			/* Atualizações de estados */
			/***************************/
			
			/* projeteis (player) */
			
			for (Projectile1 pj1 : proj_player){
				
				if(pj1.getState() == ACTIVE){
					
					/* verificando se projétil saiu da tela */
					if(pj1.getPosicaoY < 0) {
						
						pj1.setState(INACTIVE);
					}
					else {
						
						pj1.setPosicaoX(getVelocidadeVX() * delta);
						pj1.setPosicaoY(getVelocidadeVY() * delta);
						
					}
				}
			}
			
			/* projeteis (inimigos) */
			
			for (Projectile1 pj1 : proj_enemy1){
				
				if(pj1.getState() == ACTIVE){
					
					/* verificando se projétil saiu da tela */
					if(pj1.getPosicaoY > GameLib.HEIGHT) {
						
						pj1.setState(INACTIVE);
					}
					else {
						
						pj1.setPosicaoX(getVelocidadeVX() * delta);
						pj1.setPosicaoY(getVelocidadeVY() * delta);
						
					}
				}
			}
			
			/* inimigos tipo 1 */
			
			for ( Enemy1 eny1 : vet_enemy1 ){
				
				if(eny1.getState() == EXPLODING){
					
					if(currentTime > eny1.getExplosionEnd()){
						
						eny1.set
			
			
			
			
			
			
			
			for(int i = 0; i < enemy1_states.length; i++){
				
				if(enemy1_states[i] == EXPLODING){
					
					if(currentTime > enemy1_explosion_end[i]){
						
						enemy1_states[i] = INACTIVE;
					}
				}
				
				if(enemy1_states[i] == ACTIVE){
					
					/* verificando se inimigo saiu da tela */
					if(enemy1_Y[i] > GameLib.HEIGHT + 10) {
						
						enemy1_states[i] = INACTIVE;
					}
					else {
					
						enemy1_X[i] += enemy1_V[i] * Math.cos(enemy1_angle[i]) * delta;
						enemy1_Y[i] += enemy1_V[i] * Math.sin(enemy1_angle[i]) * delta * (-1.0);
						enemy1_angle[i] += enemy1_RV[i] * delta;
						
						if(currentTime > enemy1_nextShoot[i] && enemy1_Y[i] < player_Y){
																							
							int free = findFreeIndex(e_projectile_states);
							
							if(free < e_projectile_states.length){
								
								e_projectile_X[free] = enemy1_X[i];
								e_projectile_Y[free] = enemy1_Y[i];
								e_projectile_VX[free] = Math.cos(enemy1_angle[i]) * 0.45;
								e_projectile_VY[free] = Math.sin(enemy1_angle[i]) * 0.45 * (-1.0);
								e_projectile_states[free] = ACTIVE;
								
								enemy1_nextShoot[i] = (long) (currentTime + 200 + Math.random() * 500);
							}
						}
					}
				}
			}
	}
}
				
			/*******************/
			/* Desenho da cena */
			/*******************/
				
	/* desenhando plano de fundo distante */
		
	GameLib.setColor(Color.DARK_GRAY);
	background2.setCount (background2.getCount() + background2.getSpeed() * delta;
	
	for (BackGround background2 : vet_background2){
	
		GameLib.fillRect(background2.getBackground_X(), (background2.getBackground_Y() + background2.getCount()) % GameLib.HEIGHT, 2, 2);
	
	}
	
	
	
	/* desenhando plano de fundo próximo */
	
	GameLib.setColor(Color.GRAY);
	background1.setCount (background1.getCount() + background1.getSpeed() * delta;
	
	for (BackGround background1 : vet_background1) {
		
		GameLib.fillRect(background1.getBackground_X(), (background1.getBackground_Y() + background1.getCount()) % GameLib.HEIGHT, 3, 3);
		
	}
		
	
				
	/* desenhando player */
	
	if(p1.getState() == EXPLODING) {	
		
		double alpha = (currentTime - p1.getExplosionStart()) / (p1.getExplosionEnd() - p1.getExplosionStart());
		GameLib.drawExplosion(p1.getPosicaoX(), p1.getPosicaoY(), alpha);
	}
	else{
		
		GameLib.setColor(Color.BLUE);
		GameLib.drawPlayer(p1.getPosicaoX(), p1.getPosicaoY(), p1.getRadius());
	}
	
		
	/* desenhando projeteis (player) */
	
	for (Projectile1 pj1 : proj_player){
	
	
		if(pj1.getState() == ACTIVE){
			
			GameLib.setColor(Color.GREEN);
			GameLib.drawLine(pj1.getPosicaoX(), pj1.getPosicaoY() - 5, pj1.getPosicaoX(), pj1.getPosicaoY() + 5);
			GameLib.drawLine(pj1.getPosicaoX() - 1, pj1.getPosicaoY() - 3, pj1.getPosicaoX() - 1, pj1.getPosicaoY() + 3);
			GameLib.drawLine(pj1.getPosicaoX() + 1, pj1.getPosicaoY() - 3, pj1.getPosicaoX() + 1, pj1.getPosicaoY() + 3);
		}
	
	
	/* desenhando projeteis (inimigos) */
	
	for (Projectile1 pj2 : proj_enemy1){
	
		if(pj2.getState() == ACTIVE) {

			GameLib.setColor(Color.RED);
			GameLib.drawCircle(pj2.getPosicaoX(), pj2.getPosicaoY(), pj2.getRadius());
		}
	
	}
	
	
		
	/* desenhando inimigos (tipo 1) */
	
	for (Enemy1 eny1 : vet_enemy1) {
	
		if(eny1.getState() == EXPLODING){
				
			double alpha = (currentTime - eny1.getExplosionStart()) / (eny1.getExplosionEnd() - eny1.getExplosionStart());
			GameLib.drawExplosion(eny1.getPosicaoX(), eny1.getPosicaoY(), alpha);
		}
			
		if(eny1.getState() == ACTIVE){
		
			GameLib.setColor(Color.CYAN);
			GameLib.drawCircle(eny1.getPosicaoX(), eny1.getPosicaoY(), eny1.getRadius());
		}
	
	}
	
	
	/* desenhando inimigos (tipo 2) */
	
	for (Enemy2 eny2 : vet_enemy2) {
	
		if(eny2.getState() == EXPLODING){
			
			double alpha = (currentTime - eny2.getExplosionStart()) / (eny2.getExplosionEnd() - eny2.getExplosionStart());
			GameLib.drawExplosion(eny2.getPosicaoX(), eny2.getPosicaoY(), alpha);
		}
		
		if(eny2.getState() == ACTIVE){
	
			GameLib.setColor(Color.MAGENTA);
			GameLib.drawDiamond(eny2.getPosicaoX(), eny2.getPosicaoY(), eny2.getRadius());
		}
	
	}
	
