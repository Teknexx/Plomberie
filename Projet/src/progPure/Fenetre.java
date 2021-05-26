/* Code par TekneX */
/* https://github.com/Teknexx */

package progPure;

import java.awt.Color;
import java.awt.event.WindowEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Fenetre extends JFrame {

	private static final long serialVersionUID = 1L;

	private JButton [][] Tableau; //tableau de boutons
	private int [] tabX,tabY;
	private int i,j;
	private boolean sourcemise = false; //test si la source est placée
	private int type; //type de tuyau : 11,15,26,0,7...
	private long TempsCalcFinal; //var pour le temps de calcul de l'algo
	private int departpanneauX = 300, departpanneauY = 20;

	//icones des source, fin, fond et frame
	Icon icon0 = new ImageIcon("Tuyaux/0.png");
	Icon icon7 = new ImageIcon("Tuyaux/7.png");
	Icon icon8 = new ImageIcon("Tuyaux/8.png");
	Icon iconframe = new ImageIcon("Tuyaux/frame.png");
	//icones des mobiles
	Icon icon11 = new ImageIcon("Tuyaux/11.png");
	Icon icon12 = new ImageIcon("Tuyaux/12.png");
	Icon icon13 = new ImageIcon("Tuyaux/13.png");
	Icon icon14 = new ImageIcon("Tuyaux/14.png");
	Icon icon15 = new ImageIcon("Tuyaux/15.png");
	Icon icon16 = new ImageIcon("Tuyaux/16.png");
	//icones des fixes
	Icon icon21 = new ImageIcon("Tuyaux/21.png");
	Icon icon22 = new ImageIcon("Tuyaux/22.png");
	Icon icon23 = new ImageIcon("Tuyaux/23.png");
	Icon icon24 = new ImageIcon("Tuyaux/24.png");
	Icon icon25 = new ImageIcon("Tuyaux/25.png");
	Icon icon26 = new ImageIcon("Tuyaux/26.png");
	//icones des boutons
	Icon iconstart = new ImageIcon("Tuyaux/start.png");
	Icon iconreset = new ImageIcon("Tuyaux/reset.png");
	

	Fenetre(int hauteur, int longueur) throws InterruptedException{
		super("Projet Plomberie INF1404 2021");

		Prog programme = new Prog(hauteur,longueur);

		//options d'affichage de la fenetre
		this.setSize(50*(longueur+1)+8, 50*(hauteur+1)+150);
		this.setIconImage(((ImageIcon) iconframe).getImage());
		this.getContentPane().setBackground(new Color(33, 97, 140 ));
		this.setLayout(null);
		this.setVisible(true);
		
		
		//label pour écrire le texte
		JLabel texte = new JLabel("");
		texte.setForeground(new Color(166, 172, 175 ));
		this.add(texte);
		
		
		//bouton start
		JButton start = new JButton();
		start.setBounds((50*(longueur+1))/2-100, 50*(hauteur+1)+7 , 70, 70);
		start.setIcon(iconstart);
		start.setBorderPainted(false);
		start.addMouseListener(
				new java.awt.event.MouseAdapter() {  
					public void mousePressed(java.awt.event.MouseEvent evt) {
						if(sourcemise == true) {
							//on lance le programme
							try {
								TempsCalcFinal = programme.resolution();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							if((float)TempsCalcFinal/1000/60 < 1) { //si le temps de calcul est supérieur à 1 minutes, on l'écrit en minutes
								texte.setBounds((50*(longueur))/2-100, 50*(hauteur+1)-20, 50*(longueur), 20);
								texte.setText("Terminé en " + (float)TempsCalcFinal/1000+" s avec "+ ((programme.TailleMax)+1) + " cases remplies");
							}
							else {//sinon en secondes
								texte.setBounds((50*(longueur))/2-150, 50*(hauteur+1)-20, 50*(longueur), 20);
								texte.setText("Terminé en " + (float)TempsCalcFinal/1000/60 + " min avec "+ ((programme.TailleMax)+1) + " cases remplies");
							}
							AffichageFinal(hauteur,longueur,programme);
							programme.jeu.Afficher();
						}
						else { //si la source n'est pas mise par l'utilisateur
							texte.setBounds((50*(longueur))/2-25, 50*(hauteur+1)-20, 50*(longueur), 20);
							texte.setText("Source non placée");
						}
					}
				}
				);
		this.add(start);
		
		
		//bouton reset
		JButton reset = new JButton();
		reset.setBounds((50*(longueur+1))/2+30, 50*(hauteur+1)+7 , 70, 70);
		reset.setBorderPainted(false);
		reset.setIcon(iconreset);
		reset.addMouseListener(
				new java.awt.event.MouseAdapter() {  
					public void mousePressed(java.awt.event.MouseEvent evt) {

						//on reset le programme
						texte.setText("");
						programme.resettableau(hauteur, longueur);
						sourcemise = false;
						AffichageFinal(hauteur,longueur,programme);
					}
				}
				);
		this.add(reset);
			
		
		//creation et affichage du tableau de bouton de dimension 2
		Tableau = new JButton[hauteur][longueur];
		for( i= 0; i < hauteur; i++){
			for( j= 0; j < longueur; j++){


				Tableau[i][j] = new JButton("",icon0);
				Tableau[i][j].setBounds(50*j+20, 50*i+20, 50, 50);
				this.add(Tableau[i][j]);

				//variables servant à sauvegarder la valeure de  et j, pour les utiliser ddans les addActionListener
				int finI = i;
				int finJ = j;

				Tableau[i][j].addActionListener(e -> {					
					type = programme.jeu.t[finI+1][finJ+1];					

					if (sourcemise == false) { // si la source n'est pas mise, on la met
						sourcemise = true;
						programme.jeu.placementsource(finI+1, finJ+1);
						programme.isource = finI+1;
						programme.jsource = finJ+1;
						Tableau[finI][finJ].setIcon(icon7);

					}
					else { //sinon on fait un roulement des autres tuyaux
						if (type == 25) {type = 0;}
						else if (type == 24) {type = 26;}
						else if (type == 26) {type = 25;}
						else if(type==0) {type = 21;}
						else type++;

						programme.jeu.placementfixe(finI+1, finJ+1, type);

						//on affiche le tuyau après le clic
						if(type==21) {
							Tableau[finI][finJ].setIcon(icon21);
						}
						else if(type==22) {
							Tableau[finI][finJ].setIcon(icon22);
						}
						else if(type==23) {
							Tableau[finI][finJ].setIcon(icon23);
						}
						else if(type==24) {
							Tableau[finI][finJ].setIcon(icon24);
						}
						else if(type==25) {
							Tableau[finI][finJ].setIcon(icon25);
						}
						else if(type==26) {
							Tableau[finI][finJ].setIcon(icon26);
						}
						else if(type==0) {
							Tableau[finI][finJ].setIcon(icon0);
						}

					}					

				});

			}
		}
	}

	//fonction qui affiche actualise le tableau de bouton en fonction du tableau de la class Prog (ici jeu.t[][])
	public void AffichageFinal(int hauteur, int longueur, Prog programme) {

		for( int x= 0; x < hauteur+1; x++){
			for( int y= 0; y < longueur+1; y++){
				if(programme.jeu.t[x][y] == 11) {
					Tableau[x-1][y-1].setIcon(icon11);
				}
				else if(programme.jeu.t[x][y] == 12) {
					Tableau[x-1][y-1].setIcon(icon12);
				}
				else if(programme.jeu.t[x][y] == 13) {
					Tableau[x-1][y-1].setIcon(icon13);
				}
				else if(programme.jeu.t[x][y] == 14) {
					Tableau[x-1][y-1].setIcon(icon14);
				}
				else if(programme.jeu.t[x][y] == 15) {
					Tableau[x-1][y-1].setIcon(icon15);
				}
				else if(programme.jeu.t[x][y] == 16) {
					Tableau[x-1][y-1].setIcon(icon16);
				}
				else if(programme.jeu.t[x][y] == 8) {
					Tableau[x-1][y-1].setIcon(icon8);
				}
				else if(programme.jeu.t[x][y] == 0) {
					Tableau[x-1][y-1].setIcon(icon0);
				}

			}
		}
	}


	//on ferme le programme entier quand on ferme la fenetre
	@Override
	protected void processWindowEvent(WindowEvent e) {
		if(WindowEvent.WINDOW_CLOSING == e.getID()) {
			System.exit(0);
		}
	}

}


