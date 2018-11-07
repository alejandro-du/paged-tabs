package org.vaadin.tabs.paged;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.vaadin.tabs.PagedTabs;

@Route("")
public class DemoView extends Div {

    public DemoView() {
        PagedTabs tabs = new PagedTabs();
        add(tabs, new Button("add", e -> tabs.add(new Text("test"), "Test", true)));

        for (int i = 1; i <= 3; i++) {
            TextField component = new TextField("Component " + i);
            Tab tab = new Tab("Tab " + i);
            tabs.add(component, tab);
            tabs.select(tab);
        }

        tabs.add(new Text("Close me"), "Closable", true);
        tabs.add(new Span("Span 1"), "Tab 4");
        tabs.add(this::getTab, "Supplier");
    }

    private Component getTab() {
        return new TextField("Change value and come back");
    }

}
