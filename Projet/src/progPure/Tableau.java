/* Code par TekneX */
/* https://github.com/Teknexx */

package progPure;

public class Tableau {

	public int[][] t;
	private int x,y;
	private int hauteur,longueur;
	public int TableauMax [][]; //tableau pour save
	
	
	//fonctionnement des types 
	/* 0 : vide          10 = fixe         -1 : limites
	 * 1 : ║             20 = mobile
	 * 2 : ═
	 * 3 : ╗ 
	 * 4 : ╝
	 * 5 : ╔
	 * 6 : ╚
	 * 7 : source
	 * 8 : bouche d'egout (fin)
	 */
	
	//fonctionnement des orientations
	/* 1 : vers la droite
	 * 2 : vers le bas
	 * 3 : vers la gauche
	 * 4 : vers le haut
	 */
	
	
	//creaion du tableau
	Tableau(int hauteur, int longueur){
		this.hauteur = hauteur;
		this.longueur = longueur;
		
		//initialisation du tableau que l'on va utiliser et de sa sauvegarde
		t = new int[hauteur+2] [longueur+2]; 
		TableauMax  = new int[hauteur+2] [longueur+2];

		//remplissage limites tableau
		for( x= 0; x < longueur+2; x++) {
			t[0][x] = -1;
			t[hauteur+1][x] = -1;
		}
		for( x= 0; x < hauteur+2; x++) {
			t[x][0] = -1;
			t[x][longueur+1] = -1;
		}
	
	}
	
	//sauvegarde du plus grand tableau possible
	public void sauvegarde() {
		for( x= 0; x < hauteur+2; x++){
			for( y= 0; y < longueur+2; y++){
				TableauMax[x][y]=t[x][y];
			}
		}
	}
	
	//on applique la sauvegarde sur le tableau actuel
	public void sauvegardeapplique() {
		for( x= 1; x < hauteur+1; x++){
			for( y= 1; y < longueur+1; y++){
				t[x][y]= TableauMax[x][y];
			}
		}
	}
	
	//pour placer la source
	public void placementsource( int isource, int jsource) {
		t[isource][jsource]=7; //p, remplace la case par le type source
	}
	
	
	//pour placer des fixes (avec le tableau de boutons)
	public void placementfixe( int i, int j, int nombredelacase) {
		t[i][j] = nombredelacase;
	}
	
	//reset de la case (pour le retour en arrière)
	public void reset(int i,int j) {
		t[i][j]=0;
	}


	//test de la prochaine case (si la case où on est est FIXE)
	public int fixe(int i, int j, int orientationPrecedent,int orient) {
		if(orientationPrecedent == 1 ) { //si la precedente orientation etait vers la droite

			if((t[i][j]==24 &&   (t[i-1][j]==21 || t[i-1][j]==23 || t[i-1][j]==25 || t[i-1][j]==0  )   )&& (orient == 1)) return 4; //on va vers le haut
			else if((t[i][j]==22 &&   (t[i][j+1]==24 || t[i][j+1]==22 || t[i][j+1]==23 || t[i][j+1]==0 )  )&& (orient == 2)) return 1; //on va vers la droite
			else if((t[i][j]==23 &&   (t[i+1][j]==24 || t[i+1][j]==21 || t[i+1][j]==26 || t[i+1][j]==0 )  )&& (orient == 3)) return 2; //on va vers le bas
			else if (orient == 4) return -3; //effectuera un retour  
			else {
				return 0; //effectuera orient++ pour tester l'orientation suivante
			}
		}
		else if(orientationPrecedent == 2) {

			if((t[i][j]==26 &&    (t[i][j+1]==22 || t[i][j+1]==23 || t[i][j+1]==24 || t[i][j+1]==0)    )&& (orient == 1)) return 1;
			else if((t[i][j]==21 &&    (t[i+1][j]==21 || t[i+1][j]==24 || t[i+1][j]==26 || t[i+1][j]==0)   )&& (orient == 2)) return 2;
			else if((t[i][j]==24 &&   (t[i][j-1]==22 || t[i][j-1]==25 || t[i][j-1]==26 || t[i][j-1]==0)   )&& (orient == 3)) return 3;
			else if (orient == 4) return -3;
			else {
				return 0;
			}
		}
		else if(orientationPrecedent == 3) {

			if((t[i][j]==25 && 	(t[i+1][j]==21 || t[i+1][j]==24 || t[i+1][j]==26|| t[i+1][j]==0)	)&& (orient == 1)) return 2;
			else if((t[i][j]==26 &&   (t[i-1][j]==26 || t[i-1][j]==22 || t[i-1][j]==25|| t[i-1][j]==0)  )&& (orient == 2)) return 4;
			else if((t[i][j]==22 &&  (t[i][j-1]==21 || t[i][j-1]==25 || t[i][j-1]==23|| t[i][j-1]==0)   )&& (orient == 3)) return 3;
			else if (orient == 4) return -3;
			else {
				return 0;
			}
		}
		else if(orientationPrecedent == 4) {

			if((t[i][j]==25 &&  (t[i][j+1]==22 || t[i][j+1]==23 || t[i][j+1]==24 || t[i][j+1]==0)   )&& (orient == 1)) return 1;
			else if((t[i][j]==21 &&   (t[i-1][j]==23 || t[i-1][j]==21 || t[i-1][j]==25 || t[i-1][j]==0)  )&& (orient == 2)) return 4;
			else if((t[i][j]==23 &&  (t[i][j-1]==25 || t[i][j-1]==26 || t[i][j-1]==22 || t[i][j-1]==0)   )&& (orient == 3)) return 3;
			else if (orient == 4) return -3;
			else {
				return 0;
			}
		}
		System.out.println("Erreur critique fonction fixe"); //au cas où le programme plante
		System.exit(0);
		return -1;
	}



	//test de la prochaine case (si la case où on est est MOBILE)
	public int mobile(int i, int j, int orientationPrecedent,int orient){ //meme principe que les fixes, mais ne prend pas en compte la case actuelle ((t[i][j]==25)&& ...) n'est pas présent et on change la case ou on se situ
		
		if(orientationPrecedent == 1) {

			if((t[i-1][j]==21 || t[i-1][j]==23 || t[i-1][j]==25 || t[i-1][j]==0  )&& orient == 1) {t[i][j]= 14;return 4;} //on place un tuyaux qui va vers le haut (type 4) et on retourne le fait que l'on va vers le haut
			else if((t[i][j+1]==24 || t[i][j+1]==22 || t[i][j+1]==23 || t[i][j+1]==0 )&& orient == 2) {t[i][j]= 12; return 1;}
			else if((t[i+1][j]==24 || t[i+1][j]==21 || t[i+1][j]==26 || t[i+1][j]==0 )&& orient == 3) {t[i][j]= 13; return 2;}
			else if (orient == 4) return -3;
			return 0;
		}

		else if(orientationPrecedent == 2) {

			if((t[i][j+1]==22 || t[i][j+1]==23 || t[i][j+1]==24 || t[i][j+1]==0)&& orient == 1) {t[i][j]= 16; return 1;}
			else if((t[i+1][j]==21 || t[i+1][j]==24 || t[i+1][j]==26 || t[i+1][j]==0)&& orient == 2) {t[i][j]= 11;return 2;}
			else if((t[i][j-1]==22 || t[i][j-1]==25 || t[i][j-1]==26 || t[i][j-1]==0)&& orient == 3) {t[i][j]= 14;return 3;}
			else if (orient == 4) return -3;
			return 0;
		}

		else if(orientationPrecedent == 3) {

			if((t[i+1][j]==21 || t[i+1][j]==24 || t[i+1][j]==26|| t[i+1][j]==0)&& orient == 1){  t[i][j]= 15; return 2;}
			else if((t[i-1][j]==26 || t[i-1][j]==22 || t[i-1][j]==25|| t[i-1][j]==0)&& orient == 2){ t[i][j]= 16;return 4;}
			else if((t[i][j-1]==21 || t[i][j-1]==25 || t[i][j-1]==23|| t[i][j-1]==0)&& orient == 3){  t[i][j]= 12;return 3;}
			else if (orient == 4) return -3;
			return 0;
		}

		else if(orientationPrecedent == 4) {


			if((t[i][j+1]==22 || t[i][j+1]==23 || t[i][j+1]==24 || t[i][j+1]==0)&& orient == 1) { t[i][j]= 15; return 1;}
			else if((t[i-1][j]==23 || t[i-1][j]==21 || t[i-1][j]==25 || t[i-1][j]==0)&& orient == 2) { t[i][j]= 11;return 4;}
			else if((t[i][j-1]==25 || t[i][j-1]==26 || t[i][j-1]==22 || t[i][j-1]==0)&& orient == 3) { t[i][j]= 13; return 3;}
			else if (orient == 4) return -3;
			return 0;
		}

		System.out.println("Erreur critique fonction mobile");
		System.exit(0);
		return -1;
	}

	
	
	//si la case ou on est est la source
	public int source(int i, int j, int orient) {
			if(orient == 1 ) {
				if(t[i][j+1] == 0 || t[i][j+1] == 22 || t[i][j+1] == 23 || t[i][j+1] == 24) return 1;
				else return 0;
			}
			else if(orient == 2 ) {				
				if(t[i+1][j] ==0 || t[i+1][j] == 21 || t[i+1][j] == 24 || t[i+1][j] == 26) return 2;
				else return 0;
			}
			else if(orient == 3 ) {
				if(t[i][j-1] ==0 || t[i][j-1] == 22 || t[i][j-1] == 25 || t[i][j-1] == 26) 	return 3;
				else return 0;
			}
			else if(orient == 4 ) 	
				if(t[i-1][j] ==0 || t[i-1][j] == 21 || t[i-1][j] == 23 || t[i-1][j] == 25)	return 4;
				else return 0;
				
			else return -3;
		}


	//affichage sans sleep pour tests (besoin de passse l'encodage de Eclipse en UTF-8)
	public void Afficher() {
		for( x= 1; x< hauteur+1; x++){
			for( y= 1; y < longueur+1; y++){
				if (t[x][y] == 11 || t[x][y] == 21) System.out.print("║");
				if (t[x][y] == 12 || t[x][y] == 22) System.out.print("═");
				if (t[x][y] == 13 || t[x][y] == 23) System.out.print("╗");
				if (t[x][y] == 14 || t[x][y] == 24) System.out.print("╝");
				if (t[x][y] == 15 || t[x][y] == 25) System.out.print("╔");
				if (t[x][y] == 16 || t[x][y] == 26) System.out.print("╚");
				if (t[x][y] == 7) System.out.print("X");
				if (t[x][y] == 8) System.out.print("8");
				if (t[x][y] == -1) System.out.print("-1");
				if (t[x][y] == 0) System.out.print("0");
			}
			System.out.println("");
		}
		System.out.println("");
	}

	//affichage avec sleep pour tests (besoin de passse l'encodage de Eclipse en UTF-8)
	public void Afficher(int sleep) throws InterruptedException {
		for( x= 1; x< hauteur+1; x++){
			for( y= 1; y < longueur+1; y++){
				if (t[x][y] == 11 || t[x][y] == 21) System.out.print("║");
				if (t[x][y] == 12 || t[x][y] == 22) System.out.print("═");
				if (t[x][y] == 13 || t[x][y] == 23) System.out.print("╗");
				if (t[x][y] == 14 || t[x][y] == 24) System.out.print("╝");
				if (t[x][y] == 15 || t[x][y] == 25) System.out.print("╔");
				if (t[x][y] == 16 || t[x][y] == 26) System.out.print("╚");
				if (t[x][y] == 7) System.out.print("X");
				if (t[x][y] == 8) System.out.print("8");
				if (t[x][y] == -1) System.out.print("-1");
				if (t[x][y] == 0) System.out.print("0");
			}
			System.out.println("");
		}
		System.out.println("");

		//waiten ms entre deux affichages
		Thread.sleep(sleep);
	}

	//affichages des types (en int) pour tests
	public void AfficherNombres() {
		for( x= 0; x < hauteur+2; x++){
			for( y= 0; y < longueur+2; y++){
				System.out.print( t[x][y] + " ");
			}
			System.out.println("");
		}
	}

	//test si le tableau est rempli (retourne true si oui)
	public boolean fin(Pile P){
		return P.Taille() == (hauteur*longueur)-1;
	};

}
