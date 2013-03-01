package org.monospace.configcreator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConfigTemplate {
	public class ConfigSelectElement extends ConfigTemplateElement {
		private HashMap<String, String> options;
		
		public ConfigSelectElement(String key, String desc, int priority) {
			super(key, desc, priority);
			this.options = new HashMap<String, String>();
		}
		@Override
		public boolean validate(Config config) {
			return super.validate(config) && options.containsKey(config.getValue());
		}
		public void addOption(String value, String desc) {
			options.put(value, desc);
		}
	}
	public class ConfigTextElement extends ConfigTemplateElement {
		public ConfigTextElement(String key, String desc, int priority) {
			super(key, desc, priority);
		}
		@Override
		public boolean validate(Config config) {
			return super.validate(config);
		}
	}
	public class ConfigIPAddrElement extends ConfigTextElement {
	    private static final String IPADDRESS_PATTERN = 
	    		"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		public ConfigIPAddrElement(String key, String desc, int priority) {
			super(key, desc, priority);
		}
		@Override
		public boolean validate(Config config) {
			return super.validate(config) && config.getValue().matches(IPADDRESS_PATTERN);
		}
	}
	private ArrayList<ConfigTemplateElement> list;
	public ConfigTemplate() {
		list = new ArrayList<ConfigTemplateElement>();
	}
	/**
	 * @return the list
	 */
	public ArrayList<ConfigTemplateElement> getList() {
		return list;
	}
	public void parse(String source) throws RuntimeException {
		ArrayList<ConfigTemplateElement> templist = new ArrayList<ConfigTemplateElement>();
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
				ConfigTemplateElement conf = null;
				switch (element.getString("type")) {
				case "text":
					conf = new ConfigTextElement(key, desc, priority);
					break;
				case "ipaddr":
					conf = new ConfigIPAddrElement(key, desc, priority);
					break;
				case "select":
					JSONArray options = element.getJSONArray("options");
					ConfigSelectElement confref = new ConfigSelectElement(key, desc, priority);
					for (int j = 0; j < options.length(); j++) {
						JSONObject option = options.getJSONObject(j);
						confref.addOption(option.getString("value"), option.getString("desc"));
						conf = confref;
					}
					break;
				default:
					break;
				}
				templist.add(conf);
			}
		} catch (JSONException e) {
			throw new RuntimeException("Error parsing source");
		}
		list = templist;
	}
	public void parse(File file) throws IOException, RuntimeException {
		byte[] b = new byte[(int) file.length()];
		FileInputStream in = new FileInputStream(file);
		in.read(b);
		in.close();
		String src = new String(b);
		parse(src);
	}
	public boolean validate(Config conf) {
		int index = list.indexOf(conf);
		if (index < 0) return false;
		ConfigTemplateElement element = list.get(index);
		return element.validate(conf);
	}
	public int priority(Config conf) {
		int index = list.indexOf(conf);
		if (index < 0) return -1;
		ConfigTemplateElement element = list.get(index);
		return element.getPriority();
	}
}
