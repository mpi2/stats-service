package org.mousephenotype.cda.file.stats;

import java.util.ArrayList;
import java.util.List;

public class StatsList {

	
	private List<Stats> stats;
	
	public StatsList() {
		stats=new ArrayList<>();
	}

	public List<Stats> getStats() {
		return stats;
	}

	public void setStats(List<Stats> stats) {
		this.stats = stats;
	}

	@Override
	public String toString() {
		return "StatsList [stats=" + stats + "]";
	}
	
	
}
