package Mancala;
import java.util.*;
public class Mancala {
	public static void rellenarMancala(int[][] matriz) {
		for(int i=0; i<matriz.length; i++) {
			for(int j=0; j<matriz[0].length; j++) {
				matriz[i][j]=2;
			}
		}
	}
	
	public static boolean esCorrectoNombreJ1(String nombre1) {
		Scanner sc=new Scanner(System.in);
		
		if(nombre1.length()!=3) {
			System.out.println("El nombre debe contener 3 carácteres.");
			return false;
		}
		return true;
	}
	
	public static boolean esCorrectoNombreJ2(String nombre1, String nombre2) {
		Scanner sc=new Scanner(System.in);
		
		
		if(nombre2.length()!=3) {
			System.out.println("El nombre debe contener 3 carácteres.");
			return false;
		}
		
		if(nombre2.equals(nombre1)) {
			System.out.println("Los nombres deben ser diferentes.");
			return false;
		}
		return true;
	}
	
	public static void imprimirMancala(int[][] matriz, int mancala1, int mancala2, String nombre1, String nombre2) {
		System.out.println("\t"+"\t"+"\t   "+nombre1);
		System.out.println("―――――――――――――――――――――――――――――――――――――――――――――――――――――――――");
		for(int i=0; i<matriz.length; i++) {
			for(int j=0; j<matriz[0].length; j++) {
				System.out.print("\t"+matriz[i][j]);
			}
			System.out.println();
			if(i==0) {
				System.out.println(mancala1+"\t"+"―――――――――――――――――――――――――――――――――――――――――"+"\t"+mancala2);
			}
		}
		System.out.println("―――――――――――――――――――――――――――――――――――――――――――――――――――――――――");
		System.out.println("\t"+"\t"+"\t   "+nombre2);
	}

	public static int turnoJ1(int[][] matriz, int mancala1, int mancala2, String nombre1, String nombre2){
		Scanner sc=new Scanner(System.in);
		System.out.println("Turno de "+nombre1);

		System.out.println("¿Qué casa quieres mover?");
		int columna=sc.nextInt();
		
		/*
		 * Bucle para elegir una casa que no esté vacía o fuera del rango
		 */
		while (columna<=0 || columna>matriz[0].length || matriz[0][columna - 1] == 0) {
			if(columna<=0 || columna>matriz[0].length) {
				System.out.println("Casa no encontrada. Elige una columna válida.");
				
			} else if (matriz[0][columna - 1] == 0) {
				System.out.println("La casa seleccionada está vacía. Elige otra casa.");
			}
		    columna = sc.nextInt();
		}
		columna-=1;

		int contador=matriz[0][columna];
		
		/*
		 * La casa elegida se iguala a cero
		 */
		matriz[0][columna]=0;
		
		/*
		 * Bucle para rellenar las piedras hasta que el contador llegue a cero
		 */
		while(contador>0) {
			
			for(int i=0; i<matriz.length; i++) {
				if(i==0) {
					for(int j=columna-1; j>=0; j--) {
						if(contador>1) {
							matriz[i][j]+=1;
							contador--;
						}
						
						/*
						 * Comprobar si la última piedra cayó en una casa suya vacía para robarle las piedras al rival
						 */
						else if(contador==1) {
							matriz[i][j]+=1;
							contador--;
							
							if(matriz[0][j]==1 && matriz[1][j]>0) {
								imprimirMancala(matriz, mancala1, mancala2, nombre1, nombre2);
								mancala1+=matriz[1][j];
								matriz[1][j]=0;
								System.out.println();
								System.out.println("¡Las piedras de "+nombre2+" en la columna "+(j+1)+" han sido trasladadas al mancala de "+nombre1+"!");
								System.out.println();
							}
						}
					}
					columna=matriz[0].length;

					if(contador>0) {
						mancala1+=1;
						contador--;
						
					    /*
					     * Comprobar si la última piedra cayó en el mancala del jugador
					     */
						if (contador==0) {
					        imprimirMancala(matriz, mancala1, mancala2, nombre1, nombre2); 
					        System.out.println();
					        
					        if(!sinPiedrasJ1(matriz)) {
						        System.out.println("¡Has ganado un turno adicional!");
						        System.out.println();
						        mancala1 = turnoJ1(matriz, mancala1, mancala2, nombre1, nombre2);
					        }
					    }
					}
					
				} else if(i==1) {
					for(int j=0; j<matriz[0].length; j++) {
						if(contador>0) {
							matriz[i][j]+=1;
							contador--;
						}
					}
				}
			}
		}	    
		return mancala1;
	}
	
	public static int turnoJ2(int[][] matriz, int mancala1, int mancala2, String nombre1, String nombre2){
		Scanner sc=new Scanner(System.in);
		System.out.println("Turno de "+nombre2);

		System.out.println("¿Qué casa quieres mover?");
		int columna=sc.nextInt();
		
		/*
		 * Bucle para elegir una casa que no esté vacía o fuera del rango
		 */
		while (columna<=0 || columna>matriz[0].length || matriz[1][columna - 1] == 0) {
			if(columna<=0 || columna>matriz[0].length) {
				System.out.println("Casa no encontrada. Elige una columna válida.");
				
			} else if (matriz[1][columna - 1] == 0) {
				System.out.println("La casa seleccionada está vacía. Elige otra casa.");
			}
		    columna = sc.nextInt();
		}
		columna-=1;

		int contador=matriz[1][columna];
		
		/*
		 * La casa elegida se iguala a cero
		 */
		matriz[1][columna]=0;
		
		/*
		 * Bucle para rellenar las piedras hasta que el contador llegue a cero
		 */
		while(contador>0) {
			
			for(int i=1; i>=0; i--) {
				if(i==1) {
					for(int j=columna+1; j<matriz[0].length; j++) {
						if(contador>1) {
							matriz[i][j]+=1;
							contador--;
						}
						
						/*
						 * Comprobar si la última piedra cayó en una casa suya vacía para robarle las piedras al rival
						 */
						else if(contador==1) {
							matriz[i][j]+=1;
							contador--;
							
							if(matriz[1][j]==1 && matriz[0][j]>0) {
								imprimirMancala(matriz, mancala1, mancala2, nombre1, nombre2);
								mancala2+=matriz[0][j];
								matriz[0][j]=0;
								System.out.println();
								System.out.println("¡Las piedras de "+nombre1+" en la columna "+(j+1)+" han sido trasladadas al mancala de "+nombre2+"!");
								System.out.println();
							}
						}
					}
					columna=matriz[0].length;

					if(contador>0) {
						mancala2+=1;
						contador--;
						
					    /*
					     * Comprobar si la última piedra cayó en el mancala del jugador
					     */
					    if (contador==0) {
					        imprimirMancala(matriz, mancala1, mancala2, nombre1, nombre2); 
					        System.out.println();
					        
					        if(!sinPiedrasJ2(matriz)) {
						        System.out.println("¡Has ganado un turno adicional!");
						        System.out.println();
						        mancala2 = turnoJ2(matriz, mancala1, mancala2, nombre1, nombre2);
					        }
					    }
					}
					
				} else if(i==0) {
					for(int j=matriz[0].length-1; j>=0; j--) {
						if(contador>0) {
							matriz[i][j]+=1;
							contador--;
						}
					}
				} 
			}
		}
		return mancala2;
	}
	
	public static boolean sinPiedrasJ1(int[][] matriz) {
		for(int i=0; i<1; i++) {
			for(int j=0; j<matriz[0].length; j++) {
				if(matriz[0][j]!=0) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean sinPiedrasJ2(int[][] matriz) {
		for(int i=0; i<1; i++) {
			for(int j=0; j<matriz[0].length; j++) {
				if(matriz[1][j]!=0) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static int sumaDefinitiva(int[][] matriz, int mancala1, int mancala2, String nombre1, String nombre2) {
		System.out.println();
		System.out.println("Recuento de piedras:");
		if(sinPiedrasJ1(matriz)) {
			for(int i=0; i<matriz.length; i++) {
				for(int j=0; j<matriz[0].length; j++) {
					mancala2+=matriz[1][j];
					matriz[1][j]=0;
				}
			}
			imprimirMancala(matriz, mancala1, mancala2, nombre1, nombre2);
			return mancala2;

		}else if(sinPiedrasJ2(matriz)) {
			for(int i=0; i<matriz.length; i++) {
				for(int j=0; j<matriz[0].length; j++) {
					mancala1+=matriz[0][j];
					matriz[0][j]=0;
				}
			}
			imprimirMancala(matriz, mancala1, mancala2, nombre1, nombre2);
			return mancala1;
		}
		return (Integer) null;
	}
	
	public static void quienHaGanado(int mancala1, String nombre1, int mancala2, String nombre2) {
		if(mancala1>mancala2) {
			System.out.println("El ganador es "+nombre1+". ¡Enhorabuena!");
		
		}else if(mancala2>mancala1){
			System.out.println("El ganador es "+nombre2+". ¡Enhorabuena!");

		}else {
			System.out.println("!Ha habido un empate!");
		}
	}
	


	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		
		int[][] tablero=new int[2][6];
		
		String nombreJ1;
		
		do {
			System.out.println("Inserta nombre de Jugador 1 (3 carácteres):");
			nombreJ1=sc.nextLine();
		}while(!esCorrectoNombreJ1(nombreJ1));
		
		System.out.println();
		
		String nombreJ2;
		
		do {
			System.out.println("Inserta nombre de Jugador 2 (3 carácteres):");
			nombreJ2=sc.nextLine();
		}while(!esCorrectoNombreJ1(nombreJ2));
		
		System.out.println();
		
		int mancalaJ1=0;
		int mancalaJ2=0;

		rellenarMancala(tablero);
		imprimirMancala(tablero, mancalaJ1, mancalaJ2, nombreJ1, nombreJ2);

		do{
			mancalaJ1=turnoJ1(tablero, mancalaJ1, mancalaJ2, nombreJ1, nombreJ2);
			imprimirMancala(tablero, mancalaJ1, mancalaJ2, nombreJ1, nombreJ2);
			
			if(sinPiedrasJ1(tablero)) {
				System.out.println();
				System.out.println("¡Movimiento decisivo!");
				mancalaJ2=turnoJ2(tablero, mancalaJ1, mancalaJ2, nombreJ1, nombreJ2);
				imprimirMancala(tablero, mancalaJ1, mancalaJ2, nombreJ1, nombreJ2);
			}
			
			mancalaJ2=turnoJ2(tablero, mancalaJ1, mancalaJ2, nombreJ1, nombreJ2);
			imprimirMancala(tablero, mancalaJ1, mancalaJ2, nombreJ1, nombreJ2);
			
			if(sinPiedrasJ2(tablero)) {
				System.out.println();
				System.out.println("¡Movimiento decisivo!");
				mancalaJ1=turnoJ1(tablero, mancalaJ1, mancalaJ2, nombreJ1, nombreJ2);
				imprimirMancala(tablero, mancalaJ1, mancalaJ2, nombreJ1, nombreJ2);
			}
			
		}while(!sinPiedrasJ1(tablero) && !sinPiedrasJ2(tablero));
		sumaDefinitiva(tablero, mancalaJ1, mancalaJ2, nombreJ1, nombreJ2);
		System.out.println();
		quienHaGanado(mancalaJ1, nombreJ1, mancalaJ2, nombreJ2);
	}
}
