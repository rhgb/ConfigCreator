package org.monospace.configcreator;

public class ConfigTemplate {
	public class TextConfig extends Config implements Validatable {
		public TextConfig(String key, String value) {
			super(key, value);
		}

		@Override
		public boolean isValid() {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
}
