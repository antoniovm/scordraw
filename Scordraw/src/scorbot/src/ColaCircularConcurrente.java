package scorbot.src;

import java.util.concurrent.Semaphore;

public class ColaCircularConcurrente<T> {
	
	private Object[] array;
	private int inicio, fin, elementos;
	private Semaphore elementoDisponible, espacioLibre;
	
	
	public ColaCircularConcurrente(int tamano) {
		array = new Object[elementos=tamano];
		inicio=0;
		fin=0;
		elementoDisponible = new Semaphore(0);
		espacioLibre = new Semaphore(elementos);
		
	}
	
	/**
	 * Comprueba si la cola esta vacia
	 * @return true si esta vacia, false en caso contrario
	 */
	public boolean estaVacia() {
		return elementos==0;
	}
	/**
	 * Comprueba si la cola esta llena 
	 * @return true si esta llena, false en caso contrario
	 */
	
	public boolean estaLlena() {
		return elementos==array.length;
	}
	/**
	 * Añade un objeto al final de la cola en caso de no estar llena
	 * @param Object el objeto a encolar
	 * @return true si se ha insertado correctamente, false en caso contrario
	 */
	public boolean encolar(T o) {
		try {
			espacioLibre.acquire();			//WAIT EL
		} catch (InterruptedException e) {
			return false;
		}
		if(estaLlena())return false;
		array[fin++]=o;
		
		elementoDisponible.release();		//SIGNAL ED
		return true;
		

	}
	/**
	 * Consime el primer elemento de la cola en caso de no estar vacia
	 * @return el objeto consumido si existe, o null en caso contrario
	 */
	public T consumir(){
		T o=null;
		try {
			elementoDisponible.acquire();	//WAIT ED
		} catch (InterruptedException e) {
			return o;
		}
		if(estaVacia()) return o;
		o=(T)array[inicio];
		array[inicio++]=null;
		espacioLibre.release();				//SIGNAL EL
		return (T)o;
		
		
	}
	
	
}
