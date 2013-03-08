package org.monospace.configcreator;

import java.util.EventListener;

public interface ModelChangeListener extends EventListener {
	public void modelChanged();
}
