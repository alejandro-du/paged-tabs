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

    protected Component selected;

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
        Component component = tabsToComponents.get(tab);
        getContent().replace(selected, component);
        selected = component;
    }

    public void select(Component component) {
        select(getTab(component));
    }

    public Tab add(Component component, String caption) {
        return add(component, caption, false);
    }

    public Tab add(Component component, String caption, boolean closable) {
        HorizontalLayout tabLayout = new HorizontalLayout(new Text(caption));
        tabLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        tabLayout.setSpacing(false);
        Tab tab = new Tab(tabLayout);

        if (closable) {
            Span close = new Span(VaadinIcon.CLOSE_SMALL.create());
            tabLayout.add(close);
            close.addClickListener(e -> remove(tab));
        }

        add(component, tab);
        return tab;
    }

    public void add(Component component, Tab tab) {
        tabs.add(tab);

        VerticalLayout wrapper = new VerticalLayout(component);
        wrapper.setMargin(false);
        wrapper.setPadding(false);
        wrapper.setSizeFull();

        tabsToComponents.put(tab, wrapper);

        if (tabsToComponents.size() == 1) {
            selected = wrapper;
            select(tab);
        }
    }

    public void remove(Tab tab) {
        tabs.remove(tab);
        Component wrapper = tabsToComponents.get(tab);
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
