[![Published on Vaadin  Directory](https://img.shields.io/badge/Vaadin%20Directory-published-00b4f0.svg)](https://vaadin.com/directory/component/paged-tabs)
[![Stars on vaadin.com/directory](https://img.shields.io/vaadin-directory/star/paged-tabs.svg)](https://vaadin.com/directory/component/paged-tabs)

# paged-tabs

The current Vaadin's `Tabs` component doesn't include an API to show a `Component` when a tab is clicked.
You have to code the logic to show and hide components depending on the selected tab.
This component frees you from implementing such logic. Here's an example:

```Java
VerticalLayout container = new VerticalLayout();
PagedTabs tabs = new PagedTabs(container);
tabs.add("Tab caption 1", component1);
tabs.add("Tab caption 2", component2);
```

Make a tab non-closable as follows:
```Java
tabs.add("Closable", new Span("Close me"), false);
```

Get a `Tab` reference:

```Java
Tab tab = tabs.add("Tab caption", component);
```

Add a selection listener:

```Java
tabs.addSelectedChangeListener(selectedTab -> {
    ...
});
```

Set a close listener:

```java
tabs.setCloseListener(closableTab, closedTab -> {
    ...
});
```

Get text by `Tab` (captions must be unique):

```java
String textOnTab = tabs.getText(tab);
```

Get `Tab` by text:

```java
Tab tab = tabs.getTab("Tab caption");
```

Get `Component` by tab:

```java
Component component = tabs.getComponent(tab);
```

Close a tab:
```java
tabs.close(tab);
```

Select a tab:

```java
tabs.select(tab);
```

Get selected tab:

```java
Tab tab = tabs.getSelectedTab();
```

Count tabs:

```java
int count = tabs.count();
```
