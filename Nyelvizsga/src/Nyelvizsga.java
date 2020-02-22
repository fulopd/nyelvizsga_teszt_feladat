import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Nyelvizsga {

	public String nyelv;
	private List<Integer> ev;
	
	
	public Nyelvizsga(String nyelv, List<Integer> ev) {		
		this.nyelv = nyelv;
		this.ev = ev;
	}
	
	public int sumAllYear() {
		int sum = 0;
		for (Integer i : ev) {
			sum += i;
		}
		return sum;
	}
	
		
	public String getNyelv() {
		return nyelv;
	}
	public void setNyelv(String nyelv) {
		this.nyelv = nyelv;
	}
	public List<Integer> getEv() {
		return ev;
	}
	public void setEv(List<Integer> ev) {
		this.ev = ev;
	}

	@Override
	public String toString() {
		return "Nyelvizsga [nyelv=" + nyelv + ", ev=" + ev + "]";
	}
	
	
	
}
