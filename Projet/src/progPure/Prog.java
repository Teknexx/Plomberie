/* Code par TekneX */
/* https://github.com/Teknexx */

package progPure;
	
public class Prog { 
	
	private int SLEEP = 100; //wait en ms pour tests (en lien avec la fonction Afficher(int sleep) de la class Tableau   (-> ligne 212)
	private long TempsCalc; //var pour le temps de calcul de l'algo
	public Pile <Case> P = new Pile<Case>(); 
	public Case casePrecedent; //variable servant au depilement
	public Tableau jeu; //notre tableau principal
	
	public static int i =0; //i et j principale, sont en static car on en a besoin dans plusieurs fonctions
	public static int j =0;
	public static int isource,jsource; //variable i et j utilent pour le placement de la source
	public int TailleMax; //sert a la sauvegarde du plus grand tableau (ici la taille maximum déjà atteinte)
	
	//constructeur
	Prog(int hauteur, int longueur) throws InterruptedException {

		jeu = new Tableau(hauteur, longueur);
	}
	
	//sert a reset tout le tableau quand on appuie sur le bouton reset (voir class Fenetre-> bouton reset)
	public void resettableau(int hauteur, int longueur) {
		for( int x= 1; x< hauteur+1; x++){
			for( int y= 1; y < longueur+1; y++){
				jeu.reset(x,y);
				this.P = new Pile<Case>();
				this.i =0;
				this.j =0;
			}
		}
		
	}
	
	//programme principal, algo de resolution
	public long resolution() throws InterruptedException {
		i = isource;
		j = jsource;
		TailleMax = 0;

	
		//debut du calcul et donc init de la var de temps de calcul
		TempsCalc = System.currentTimeMillis();
			
		int orient = 1;
		int orientprecedent=1;		

		//tant que toutes les cases ne sont pas remplies
		while (!jeu.fin(P)){

			//si source
			if(jeu.t[i][j] == 7) {
				
				int test = orient;
				test= jeu.source(i,j, orient); //on test et on donne la prochaine case

				if(test==0) { //si cette orientation est pas bonne, on teste la suivante
					orient++;
				}
				else if (test == -3) { //si toutes les orientations impossibles, on retourne en arrière (ici on arrete le programme)
					
					if(TailleMax == 0) {
						System.out.println("Impossible de remplir une seule case");
						TempsCalc = System.currentTimeMillis() - TempsCalc;
						return TempsCalc;
					}
					else {
						System.out.println("Impossible de remplir toutes les cases");
						TempsCalc = System.currentTimeMillis() - TempsCalc;
						System.out.println("Terminé en "+TempsCalc+"ms, ou "+(float)TempsCalc/1000+" s, avec "+ P.Taille() + " cases remplies");
						jeu.sauvegardeapplique(); //on applique le meilleur tableau possible
						return TempsCalc;	      // puis on l'affiche
					} 
				}
				

				else {
					P.Empile(new Case(i,j,orient,orientprecedent)); //on empile la case
					
					//on se deplace
					if(test==1)j++;
					else if(test==2)i++;
					else if(test==3)j--;
					else if(test==4)i--;
					
					orientprecedent=test;
					orient = 1;

				}
			}
					
			
			////////////////////////////////////////////////////
			//si fixe
			
			else if(jeu.t[i][j] == 21 || jeu.t[i][j] == 22 || jeu.t[i][j] == 23 || jeu.t[i][j] == 24 || jeu.t[i][j] == 25 || jeu.t[i][j] == 26){
				int test = orient;
				test = jeu.fixe(i,j,orientprecedent,orient); //on test et on donne la prochaine case
				
				if(test==0) { //si cette orientation est pas bonne, on teste la suivante
					orient++;	
				}
				else if (test == -3) { //si toutes les orientations impossibles, on retourne en arrière
					casePrecedent = P.SommetPile(); //on reprend la derniere case sauvegarde et on la depile
					P.Depile();
					i=casePrecedent.i;
					j=casePrecedent.j;
					orient=casePrecedent.orientation;
					orientprecedent=casePrecedent.orientationprecedent;
					orient++;

				}
				else {
					P.Empile(new Case(i,j,orient,orientprecedent)); //on empile la case
					
					//on se deplace
					if (orientprecedent==1) {
						if(test==4) i--;
						if(test==1) j++;
						if(test==2) i++;
					}
					else if (orientprecedent==2) {
						if(test==1) j++;
						if(test==2) i++;
						if(test==3) j--;
					}
					else if (orientprecedent==3) {
						if(test==2) i++;
						if(test==4) i--;
						if(test==3) j--;
					}
					else if (orientprecedent==4) {
						if(test==1) j++;
						if(test==4) i--;
						if(test==3) j--;
					}
					orientprecedent=test;
					orient = 1;
					
					//save si c'est le tableau le plus grand
					if(P.Taille() > TailleMax) {
						TailleMax = P.Taille();
						jeu.sauvegarde(); //on sauvegarde le tableau actuel
						jeu.TableauMax[i][j] = 8; //on remplace le dernier par la bouche d'egout (type n° 8)
					}

				}
				
				
			}
			
			////////////////////////////////////////////////////
			//sinon (mobiles) 
			//globalement le meme fonctionnement que pour un tuyau fixe
			
			else {
				int test = orient;
				test = jeu.mobile(i,j,orientprecedent,orient);
				
				if(test==0) {
					orient++;	
				}
				else if (test == -3) {
					casePrecedent = P.SommetPile();
					P.Depile();
					jeu.reset(i, j); //contrairement au fixe, on reset la case où on se trouve
					i=casePrecedent.i;
					j=casePrecedent.j;
					orient=casePrecedent.orientation;
					orientprecedent=casePrecedent.orientationprecedent;
					orient++;

				}
				else {
					P.Empile(new Case(i,j,orient,orientprecedent));
					
					if (orientprecedent==1) {
						if(test==4) i--;
						if(test==1) j++;
						if(test==2) i++;
					}
					else if (orientprecedent==2) {
						if(test==1) j++;
						if(test==2) i++;
						if(test==3) j--;
					}
					else if (orientprecedent==3) {
						if(test==2) i++;
						if(test==4) i--;
						if(test==3) j--;
					}
					else if (orientprecedent==4) {
						if(test==1) j++;
						if(test==4) i--;
						if(test==3) j--;
					}
					orientprecedent=test;
					orient = 1;
					
					//save si c'est le tableau le plus grand
					if(P.Taille() > TailleMax) {
						TailleMax = P.Taille();
						jeu.sauvegarde();
						jeu.TableauMax[i][j] = 8;
					}
				}
			}			
			//jeu.Afficher(SLEEP);     //a de-commenter pour regarder l'evolution du parcours (modifier le sleep si voulu)
		}
		
		//dernière case
		P.Empile(new Case(i,j,orient,orientprecedent)); //on empile cette derniere
		jeu.t[i][j] = 8; //on remplace la derniere case par une bouche d'égout

		//prise de la var temps de calcul
		TempsCalc = System.currentTimeMillis() - TempsCalc;
		System.out.println("Terminé en "+(float)TempsCalc/1000+" s, ou " +(float)(TempsCalc/1000)/60+  "avec "+ P.Taille() + " cases remplies"); //on affiche sur la console pour d'éventuels test
		jeu.Afficher(); //on affiche le tableau (pour les test)
		
		return TempsCalc;
	}
	
}
