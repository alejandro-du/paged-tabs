package org.vaadin.tabs;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

import java.util.HashMap;
import java.util.Map;

public class PagedTabs extends Composite<VerticalLayout> implements HasSize {

    protected final Tabs tabs;

    protected final Map<Tab, Component> tabsToComponents = new HashMap<>();

    public PagedTabs() {
        tabs = new Tabs();

        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = tabs.getSelectedTab();
            select(selectedTab);
        });

        getContent().add(tabs);
    }

    public void select(Tab tab) {
        tabs.setSelectedTab(tab);
        tabsToComponents.values().forEach(page -> page.setVisible(false));
        tabsToComponents.get(tab).setVisible(true);
    }

    public void select(Component component) {
        select(getTab(component));
    }

    public Tab add(Component component, String caption) {
        return add(component, caption, false);
    }

    public Tab add(Component component, String caption, boolean closable) {
        HorizontalLayout layout = new HorizontalLayout(new Text(caption));
        layout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        layout.setSpacing(false);
        Tab tab = new Tab(layout);

        if (closable) {
            Span close = new Span(VaadinIcon.CLOSE_SMALL.create());
            layout.add(close);
            close.addClickListener(e -> remove(tab));
        }

        add(component, tab);
        return tab;
    }

    public void add(Component component, Tab tab) {
        tabs.add(tab);

        Div wrapper = new Div(component);

        getContent().add(wrapper);
        tabsToComponents.put(tab, wrapper);
        wrapper.setVisible(false);

        if (tabsToComponents.size() == 1) {
            select(tab);
        }
    }

    public void remove(Tab tab) {
        tabs.remove(tab);
        Component wrapper = tabsToComponents.get(tab);
        getContent().remove(wrapper);
        tabsToComponents.remove(tab);

    }

    public void remove(Component component) {
        remove(getTab(component));
    }

    public Tab getTab(Component component) {
        for (Tab t : tabsToComponents.keySet()) {
            if (tabsToComponents.get(t).equals(component)) {
                return t;
            }
        }
        return null;
    }

}
