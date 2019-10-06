package org.vaadin.tabs;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HasTheme;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.function.SerializableSupplier;

import java.util.HashMap;
import java.util.Map;

public class PagedTabs extends Composite<VerticalLayout> implements HasSize, HasStyle, HasTheme {

    protected final Tabs tabs;
    protected final VerticalLayout content;
    protected Component selected;

    protected final Map<Tab, SerializableSupplier<Component>> tabsToSuppliers = new HashMap<>();
    protected SerializableConsumer<Tab> tabCloseListener;

    public PagedTabs() {
        tabs = new Tabs();
        content = new VerticalLayout();

        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = tabs.getSelectedTab();
            select(selectedTab);
        });

        getContent().add(tabs, content);
    }

    public void select(Tab tab) {
        SerializableSupplier<Component> supplier = tabsToSuppliers.get(tab);

        if (supplier != null) {
            Component component = supplier.get();

            VerticalLayout wrapper = new VerticalLayout(component);
            wrapper.setMargin(false);
            wrapper.setPadding(false);
            wrapper.setSizeFull();

            content.removeAll();
            content.add(wrapper);

            tabs.setSelectedTab(tab);
            selected = wrapper;
        } else {
            content.remove(selected);
        }
    }

    public Tab add(Component component, String caption) {
        return add(component, caption, false);
    }

    public Tab add(Component component, String caption, boolean closable) {
        return add(() -> component, caption, closable);
    }

    public void add(Component component, Tab tab) {
        add(() -> component, tab);
    }

    public Tab add(SerializableSupplier<Component> componentSupplier, String caption) {
        return add(componentSupplier, caption, false);
    }

    public Tab add(SerializableSupplier<Component> componentSupplier, String caption, boolean closable) {
        HorizontalLayout tabLayout = new HorizontalLayout(new Text(caption));
        tabLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        tabLayout.setSpacing(false);
        Tab tab = new Tab(tabLayout);

        if (closable) {
            Span close = new Span(VaadinIcon.CLOSE_SMALL.create());
            tabLayout.add(close);
            close.addClickListener(e -> remove(tab));
        }

        add(componentSupplier, tab);
        return tab;
    }

    public void add(SerializableSupplier<Component> componentSupplier, Tab tab) {
        tabsToSuppliers.put(tab, componentSupplier);
        tabs.add(tab);

        if (tabsToSuppliers.size() == 1) {
            select(tab);
        }
    }

    public void remove(Tab tab) {
        tabs.remove(tab);
        tabsToSuppliers.remove(tab);
        select(tabs.getSelectedTab());

        if (tabCloseListener != null) {
            tabCloseListener.accept(tab);
        }
    }

    public void setTabCloseListener(SerializableConsumer<Tab> listener) {
        this.tabCloseListener = listener;
    }

}
