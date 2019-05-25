import java.util.*;

public class Mapa {
public static void main(String[] args) {
	Map <String, Map <String, Integer>> mapa = new HashMap <String,Map <String,Integer>>();
	Scanner s = new Scanner (System.in);
	String ip;
	int mensaje = 0;
	String usuario;
	System.out.println("Introdude una ip");
	ip= s.next();
	System.out.println("Introduce un mensaje de texto");
	mensaje =s.nextInt();
	System.out.println("Introduce usuario");
	usuario =s.next();
	mapa.put(ip, null).put(usuario, mensaje);
	System.out.println(mapa.get(usuario));
}
}
