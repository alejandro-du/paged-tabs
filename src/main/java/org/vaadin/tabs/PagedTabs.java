package org.vaadin.tabs;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class PagedTabs extends Composite<Tabs> {

    protected final HasComponents componentContainer;
    protected Map<Tab, Component> tabToComponent = new HashMap<>();
    protected Map<String, Tab> textToTab = new HashMap<>();
    protected Map<Tab, Consumer<Tab>> closeListeners = new HashMap<>();

    /**
     * Creates a new PagedTabs component connected to the specified container.
     * 
     * @param componentContainer The component container (e.g. VerticalLayout)
     * in which the tab contents should be displayed. This container should
     * be added to a layout outside the PagedTabs component. 
     */
    public PagedTabs(HasComponents componentContainer) {
        this.componentContainer = componentContainer;

        getContent().addSelectedChangeListener(event -> {
            if (event.getSelectedTab() != null) {
                tabToComponent.values().forEach(component ->
                        component.setVisible(false));
                tabToComponent.get(event.getSelectedTab()).setVisible(true);
            }
        });
    }

    public Tab add(String tabText, Component component) {
        return this.add(tabText, component, true);
    }

    public Tab add(String tabText, Component component, boolean closable) {
        component.setVisible(false);
        componentContainer.add(component);

        Tab tab = new Tab(tabText);

        if (closable) {
            Button closeButton = new Button(VaadinIcon.CLOSE_SMALL.create(), event -> close(tab));
            closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            tab.add(closeButton);
        }

        tabToComponent.put(tab, component);
        textToTab.put(tabText, tab);

        getContent().add(tab);
        return tab;
    }

    public void addSelectedChangeListener(Consumer<Tab> listener) {
        getContent().addSelectedChangeListener(event -> listener.accept(event.getSelectedTab()));
    }

    public void setCloseListener(Tab tab, Consumer<Tab> listener) {
        closeListeners.put(tab, listener);
    }

    public void close(Tab tab) {
        getContent().remove(tab);
        componentContainer.remove(tabToComponent.get(tab));

        tabToComponent.remove(tab);
        textToTab.remove(getText(tab));

        Consumer<Tab> closeListener = closeListeners.get(tab);
        if (closeListener != null) {
            closeListener.accept(tab);
        }
        closeListeners.remove(tab);
    }

    public void select(Tab tab) {
        tab.setVisible(true);
        getContent().setSelectedTab(tab);
    }

    public Tab get(String text) {
        return textToTab.get(text);
    }

    public Component getComponent(Tab tab) {
        return tabToComponent.get(tab);
    }

    public String getText(Tab tab) {
        String tabText = null;

        for (Map.Entry<String, Tab> entry : textToTab.entrySet()) {
            if (entry.getValue().equals(tab)) {
                tabText = entry.getKey();
                break;
            }
        }

        return tabText;
    }

    public Tab getSelectedTab() {
        return getContent().getSelectedTab();
    }

    public int count() {
        return tabToComponent.size();
    }

}
