[![Published on Vaadin  Directory](https://img.shields.io/badge/Vaadin%20Directory-published-00b4f0.svg)](https://vaadin.com/directory/component/paged-tabs)
[![Stars on vaadin.com/directory](https://img.shields.io/vaadin-directory/star/paged-tabs.svg)](https://vaadin.com/directory/component/paged-tabs)

# paged-tabs

Current Vaadin's `Tabs` component doesn't include an API to show a `Component` when a tab is clicked. You have to code the logic to show and hide components depending on the selected tab. This component frees you from implementing such logic. Here's an example:

```Java
PagedTabs tabs = new PagedTabs();
tabs.add(component, "Tab caption 1");
tabs.add(component2, "Tab caption 2");
```

You can make a tab *closable* as follows:
```Java
PagedTabs tabs = new PagedTabs();
tabs.add(new Span("Close me"), "Closeable");
```

You can use the `Tab` class as well if, for example, you want to add components to the tab itself:

```Java
Tab tab = new Tab();
tab.add(new Span("Tab caption"), new Button("Click me"));
PagedTabs tabs = new PagedTabs();
tabs.add(new Span("Tab content"), tab);
```
