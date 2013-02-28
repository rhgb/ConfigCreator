package org.monospace.configcreator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ConfigSet {
	private ArrayList<Config> list;
	
	public ConfigSet() {
		list = new ArrayList<Config>();
	}
	public void loadFromFile(File file) throws IOException,RuntimeException {
		Scanner scanner = new Scanner(file);
		list.clear();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.charAt(0) == '#' || line.trim().length() == 0) {
				continue;
			}
			Config conf = Config.parse(line);
			if (conf == null) {
				scanner.close();
				throw new RuntimeException("Parse error");
			}
			if (list.contains(conf)) {
				scanner.close();
				throw new RuntimeException("Duplicated key");
			}
		}
		scanner.close();
	}
	public void writeToFile(File file) throws IOException {
		Collections.sort(list);
		PrintWriter writer = new PrintWriter(file);
		for (int i = 0; i < list.size(); i++) {
			writer.println(list.get(i));
		}
		writer.close();
	}
}