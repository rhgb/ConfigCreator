package org.monospace.configcreator;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;


public class ConfigSelectElement extends ConfigElement {
	public class ConfigOption implements Comparable<ConfigOption> {
		private String value;
		private String desc;
		public ConfigOption(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}
		@Override
		public int compareTo(ConfigOption o) {
			return value.compareTo(o.getValue());
		}
		@Override
		public String toString() {
			return desc;
		}
		@Override
		public boolean equals(Object o) {
			if (o instanceof ConfigOption) {
				return ((ConfigOption) o).getValue().equals(value);
			}
			if (o instanceof String) {
				return value.equals(o);
			}
			return false;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
	private Vector<ConfigOption> options;
	
	public ConfigSelectElement(String key, String desc, int priority, JSONArray opt) {
		super(key, desc, priority);
		this.options = new Vector<ConfigOption>();
		for (int j = 0; j < opt.length(); j++) {
			JSONObject option = opt.getJSONObject(j);
			addOption(option.getString("value"), option.getString("desc"));
		}
		setValue(options.get(0).getValue());
	}
	@Override
	public boolean isValid() {
		if (super.isValid()) {
			for (int i = 0; i < options.size(); i++) {
				if (options.get(i).equals(getValue())) {
					return true;
				}
			}
		}
		return false;
	}
	public void addOption(String value, String desc) {
		options.add(new ConfigOption(value, desc));
	}
	public Vector<ConfigOption> getOptions() {
		return options;
	}
	@Override
	public ConfigComponent createComponent() {
		return new ConfigSelectComponent(this);
	}
	@Override
	public void clearValue() {
		setValue(options.get(0).getValue());
	}
}