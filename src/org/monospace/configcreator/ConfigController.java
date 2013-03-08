package org.monospace.configcreator;

import javax.swing.JPanel;

public class ConfigController {
	private ConfigModel model;
	private ConfigView view;
	public ConfigController(ConfigModel model) {
		this.model = model;
		view = new ConfigView(this, model);
	}
	public JPanel getViewPanel() {
		return view.getViewPanel();
	}
	public void contentChanged(int index, String content) {
		model.setValue(index, content);
		model.setModified(true);
		view.setWarning(index, !model.isValid(index));
		view.updatePreview();
	}
	public void showPreview() {
		view.showPreview();
	}
}
