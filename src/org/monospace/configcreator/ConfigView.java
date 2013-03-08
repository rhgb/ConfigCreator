package org.monospace.configcreator;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;


public class ConfigView {
	private class IndexedEditListener implements EditListener {
		private final int index;
		public IndexedEditListener(int index) {
			this.index = index;
		}
		@Override
		public void contentChanged(String content) {
			controller.contentChanged(index, content);
		}
	}
	private JPanel viewPanel;
	private ConfigPreviewFrame previewFrame;
	private ConfigController controller;
	private ConfigModel model;
	private ArrayList<ConfigComponent> components;
	public ConfigView(ConfigController controller, ConfigModel mo) {
		this.controller = controller;
		this.model = mo;
		this.components = new ArrayList<ConfigComponent>(model.size());
		createView();
		bindController();
		model.setListener(new ModelChangeListener() {
			@Override
			public void modelChanged() {
				for (int i = 0; i < components.size(); i++) {
					components.get(i).setValue(model.getValue(i));
				}
			}
		});
	}
	private void createView() {
		viewPanel = new JPanel(new GridLayout(0, 3, 5, 5));
		for (int i = 0; i < model.size(); i++) {
			ConfigComponent component = model.createComponent(i);
			viewPanel.add(component.getNameLabel());
			viewPanel.add(component.getInput());
			viewPanel.add(component.getMessageLabel());
			components.add(component);
		}
		previewFrame = new ConfigPreviewFrame(model);
		previewFrame.setVisible(false);
		updatePreview();
	}
	public void showPreview() {
		previewFrame.setVisible(true);
	}
	public void updatePreview() {
		previewFrame.setContent(model.toString());
	}
	public JPanel getViewPanel() {
		return viewPanel;
	}
	public void setWarning(int index, boolean value) {
		components.get(index).setWarning(value);
	}
	private void bindController() {
		for (int i = 0; i < components.size(); i++) {
			components.get(i).addEditListener(new IndexedEditListener(i));
		}
	}
}
