package org.vaadin.tabs.paged;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.vaadin.tabs.PagedTabs;

@Route("")
public class DemoView extends VerticalLayout {

    private int testCount = 1;

    public DemoView() {
        VerticalLayout container = new VerticalLayout();
        PagedTabs tabs = new PagedTabs(container);
        tabs.getContent().setWidthFull();

        add(new Button("Add", event -> {
            Tab tab = tabs.add("Test " + testCount, new Span("Test " + testCount), true);
            tab.getElement().getStyle().set("background-color", "lightblue");
            testCount++;
        }));

        for (int i = 1; i <= 3; i++) {
            TextField component = new TextField("Component " + i);
            Tab tab = tabs.add("Tab " + i, component);
            tabs.select(tab);
        }

        Tab closableTab = tabs.add("Closable", new Span("Close me"), true);
        tabs.setCloseListener(closableTab, tab -> Notification.show("Tab closed:" + tabs.getText(tab)));
        tabs.addSelectedChangeListener(tab -> Notification.show("Tab selected: " + tabs.getText(tab)));

        add(tabs, container);
        expand(container);
        setSizeFull();
    }

}
