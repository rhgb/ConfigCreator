package org.monospace.configcreator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class ConfigSet {
	private ArrayList<Config> list;
	private ConfigTemplate template;
	public ConfigSet(ConfigTemplate t) {
		list = new ArrayList<Config>();
		template = t;
	}
	public void loadFromFile(File file) throws IOException, RuntimeException {
		Scanner scanner = new Scanner(file);
		ArrayList<Config> tempList = new ArrayList<Config>();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.length() == 0 || line.charAt(0) == '#' || line.trim().length() == 0) {
				continue;
			}
			Config conf = Config.parse(line);
			if (conf == null || tempList.contains(conf) || !template.validate(conf)) {
				System.err.print("Invalid: ");
				System.err.println(conf);
				continue;
			}
			tempList.add(conf);
		}
		scanner.close();
		list = tempList;
	}
	public void writeToFile(File file) throws IOException {
		Comparator<Config> comp = new Comparator<Config>() {
			@Override
			public int compare(Config o1, Config o2) {
				if (o1 == null || o2 == null) throw new NullPointerException();
				int p1 = template.priority(o1);
				int p2 = template.priority(o2);
				if (p1 > p2) return -1;
				if (p1 < p2) return 1;
				return o1.getKey().compareTo(o2.getKey());
			}
		};
		Collections.sort(list, comp);
		PrintWriter writer = new PrintWriter(file);
		for (int i = 0; i < list.size(); i++) {
			writer.println(list.get(i));
		}
		writer.close();
	}
	public void clear() {
		list.clear();
	}
}