package org.monospace.configcreator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConfigModel {
	private ArrayList<ConfigElement> list;
	private boolean modified;
	private ModelChangeListener valueListener;
	private ModelChangeListener modifyListener;
	
	public ConfigModel() {
		list = new ArrayList<ConfigElement>();
		modified = false;
	}
	public void parseTemplate(String source) throws RuntimeException {
		ArrayList<ConfigElement> templist = new ArrayList<ConfigElement>();
		try {
			JSONArray json = new JSONArray(source);
			for (int i = 0; i < json.length(); i++) {
				JSONObject element = json.getJSONObject(i);
				String key = element.getString("key");
				String desc = key;
				int priority = 0;
				if (element.has("priority")) {
					priority = element.getInt("priority");
				}
				if (element.has("desc")) {
					desc = element.getString("desc");
				}
				ConfigElement conf = null;
				String type = element.getString("type");
				if (type.equals("text")) {
					conf = new ConfigTextElement(key, desc, priority);
					if (element.has("allowWhitespace")) {
						boolean allow = element.getBoolean("allowWhitespace");
						((ConfigTextElement) conf).setAllowWhitespace(allow);
					}
				}
				else if (type.equals("ipaddr")) {
					conf = new ConfigIPAddrElement(key, desc, priority);
				}
				else if ( type.equals("select")) {
					JSONArray options = element.getJSONArray("options");
					conf = new ConfigSelectElement(key, desc, priority, options);
				}
				else {
					throw new JSONException("");
				}
				templist.add(conf);
			}
		} catch (JSONException e) {
			throw new RuntimeException("Error parsing source");
		}
		list = templist;
	}
	public void parseTemplate(InputStream in) throws IOException, RuntimeException {
		InputStreamReader reader = new InputStreamReader(in, "UTF-8");
		BufferedReader br = new BufferedReader(reader);
		String line;
		String src = "";
		while ((line = br.readLine()) != null) {
			src += line;
		}
		br.close();
		parseTemplate(src);
	}
	public ConfigElement get(int i) {
		return list.get(i);
	}
	public int size() {
		return list.size();
	}
	public void loadFromFile(File file) throws IOException, RuntimeException {
		Scanner scanner = new Scanner(file);
		clearValue();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.length() == 0 || line.charAt(0) == '#' || line.trim().length() == 0) {
				continue;
			}
			String[] segs = line.split("=", 2);
			if (segs.length != 2) {
				continue;
			}
			String key = segs[0].trim().toUpperCase();
			String value = segs[1].trim();
			if (value.length() >= 2 && value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"') {
				value = value.substring(1, value.length() - 1);
			}
			int index = -1;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).equals(key)) {
					index = i;
					break;
				}
			} 
			if (index < 0) {
				System.err.print("Invalid: ");
				System.err.println(line);
				continue;
			}
			list.get(index).setValue(value);
			list.get(index).checkValidity();
		}
		scanner.close();
		if (valueListener != null) {
			valueListener.modelChanged();
		}
	}
	@Override
	public String toString() {
		String res = "";
		for (int i = 0; i < list.size(); i++) {
			res += list.get(i).toString();
			res += "\n";
		}
		return res;
	}
	public void writeToFile(File file) throws IOException {
		PrintWriter writer = new PrintWriter(file);
		for (int i = 0; i < list.size(); i++) {
			writer.println(list.get(i));
		}
		writer.close();
	}
	public ConfigComponent createComponent(int index) {
		return list.get(index).createComponent();
	}
	public String getValue(int index) {
		return list.get(index).getValue();
	}
	public void setValue(int index, String value) {
		list.get(index).setValue(value);
	}
	public void clearValue() {
		for (int i = 0; i < list.size(); i++) {
			list.get(i).clearValue();
		}
		if (valueListener != null) {
			valueListener.modelChanged();
		}
	}
	public boolean isValid(int i) {
		return list.get(i).isValid();
	}
	public boolean isModified() {
		return modified;
	}
	public void setModified(boolean m) {
		modified = m;
		if (modifyListener != null) {
			modifyListener.modelChanged();
		}
	}
	public void setChangeListener(ModelChangeListener listener) {
		this.valueListener = listener;
	}
	public void setModifyListener(ModelChangeListener listener) {
		this.modifyListener = listener;
	}
}
