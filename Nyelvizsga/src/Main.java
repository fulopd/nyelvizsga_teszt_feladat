import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
	
	private static <K, V extends Comparable<? super V>> TreeMap<K, V> rendez(Map<K, V> a) {
		TreeMap<K, V> sortByValue = new TreeMap<>(new Comparator<K>() {
			@Override
			public int compare(K o1, K o2) {
				int compare = a.get(o2).compareTo(a.get(o1));
				if (compare == 0) {
					return 1;
				} else {
					return compare;
				}
			}
		});
		sortByValue.putAll(a);
		return sortByValue;
	}
	
	

	public static List<Nyelvizsga> nyelvizsga(String file){
		
		List<Nyelvizsga> nyelvizsga = new ArrayList<Nyelvizsga>();
		
		try {
			
			List<String> beolvas = Files.readAllLines(Paths.get(file));
			for (String sor : beolvas.subList(1, beolvas.size())) {
				String[] split  = sor.split(";");
				
				List<Integer> ev = new ArrayList<Integer>();				
				for (int i = 1; i < split.length; i++) {
					ev.add(Integer.parseInt(split[i]));
				}
				
				Nyelvizsga o = new Nyelvizsga(split[0], ev);
				nyelvizsga.add(o);				
			}				
		}catch (IOException e) {
			System.err.println("Hiba a beolvasásnál!" + e);
		}
		
		return nyelvizsga;
	}
	static Scanner beolvas = new Scanner(System.in);
	
	public static void main(String[] args) {
		List<Nyelvizsga> a = nyelvizsga("sikertelen.csv");
		List<Nyelvizsga> b = nyelvizsga("sikeres.csv");
		
		
		for (Nyelvizsga nyelvizsga : a) {
			System.out.println(a);
		}
		
		Map<String, Integer> c = new TreeMap<String, Integer>();
		for (Nyelvizsga sikeres : a) {
			for (Nyelvizsga sikertelen : b) {
				c.put(sikertelen.getNyelv(), (sikeres.sumAllYear() + sikertelen.sumAllYear()));
			}
		}
		
		//c.forEach((k,v) -> System.out.println(k + "-" + v));
		c = rendez(c);
		int ii = 0;
		System.out.println("2. feladat");
		for (String nyelv : c.keySet()) {
			if (ii++ < 3) {
				System.out.println("\t"+nyelv);
			}
		}
		
		
		int evszam = 0;
		System.out.println("Kerek egy évszámot 2009 és 2017 között: ");		
	
		while(!(evszam >= 2009 && evszam <= 2017)){
			evszam = beolvas.nextInt();
			if (!(evszam >= 2009 && evszam <= 2017)) {
				System.out.println("Kerek egy évszámot 2009 és 2017 között: ");
			}
		}
		System.out.println("3. feladat: " + "\n\t vizsgálandó év: "+ evszam);
		
		
		//4. feladat
		TreeMap<String, Double> d = new TreeMap<String, Double>();
		List<String> e = new ArrayList<String>(); 
		int sumSikeres = 0;
		int sumSikertelen = 0;
		double arany = 0.0;
		
		for (int i = 0; i < a.size(); i++) {
			sumSikeres += a.get(i).getEv().get(evszam - 2009);
			sumSikertelen += a.get(i).getEv().get(evszam - 2009);
			if (sumSikeres + sumSikertelen == 0) {
				arany = 0;
				e.add(a.get(i).getNyelv());
			}else {
				arany = (sumSikertelen / (double) (sumSikeres + sumSikertelen)/100);
			}
			d.put(a.get(i).getNyelv(), arany);
			sumSikeres = 0;
			sumSikertelen = 0;	
			arany = 0.0;
			
		}
		
		d = rendez(d);
		
		System.out.println("4. feladat:\n "+ evszam + "- ben "
				+ d.firstEntry().getKey() +" nyelvből a sikertelen vizsgák aránya " 
				+ " Sikertelen vizsga aránya: "
				+ new DecimalFormat("0.00").format(d.firstEntry().getValue()) + "%");
		
		//5. feladat
		if (!e.isEmpty()) {
			for (String nyelv : e) {
				System.out.println("\t"+nyelv);				
			}
		}else {
			System.out.println("Minden nyelvből volt vizsgázó");
		}
		
		//6. feladat
		int osszes = 0;
		int nyelv = 0;
		double arany2 = 0;
		String fajlbaIr = "";
		
		for (int i = 0; i < a.size(); i++) {
			
			osszes = a.get(i).sumAllYear() + b.get(i).sumAllYear();
			nyelv = a.get(i).sumAllYear();
			arany2 = ((double) nyelv / osszes) *100;
			fajlbaIr += a.get(i).getNyelv() + ";"
						+ osszes + ";"
						+ new DecimalFormat("0.00").format(arany2) + "%;";
			
			try {
				Files.write(Paths.get("osszesites.csv"), fajlbaIr.getBytes());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

}
