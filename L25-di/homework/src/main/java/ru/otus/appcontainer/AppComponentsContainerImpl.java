package ru.otus.appcontainer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

@SuppressWarnings("squid:S1068")
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    @SuppressWarnings("this-escape")
    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        try {
            Object configClassInstance = configClass.getDeclaredConstructor().newInstance();
            Method[] classMethods = configClass.getDeclaredMethods();
            List<Method> componentCreationMethods = Arrays.stream(classMethods)
                    .filter(m -> m.isAnnotationPresent(AppComponent.class))
                    .sorted(Comparator.comparing(
                            m -> m.getAnnotation(AppComponent.class).order()))
                    .toList();
            createAndAddComponentsToContext(componentCreationMethods, configClassInstance);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Unable to create ConfigClass. " + "Constructor without params not found." + e);
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException("Unable to create ConfigClass." + e);
        }
    }

    private void createAndAddComponentsToContext(List<Method> componentCreationMethods, Object configClassInstance) {
        for (Method method : componentCreationMethods) {
            try {
                Object[] args = createArgumentsArrayForMethod(method);
                Object component = method.invoke(configClassInstance, args);
                addComponent(component, method.getAnnotation(AppComponent.class).name());
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("Exception during component creating. " + e);
            }
        }
    }

    private Object[] createArgumentsArrayForMethod(Method method) {
        List<Class<?>> argTypes = createListWithArgTypesForMethod(method);
        List<Object> args = new ArrayList<>();
        argTypes.forEach(t -> args.add(getAppComponent(t)));
        return args.toArray();
    }

    private List<Class<?>> createListWithArgTypesForMethod(Method method) {
        List<Class<?>> argTypes = new ArrayList<>();
        Arrays.stream(method.getParameters()).forEach(p -> argTypes.add(p.getType()));
        return argTypes;
    }

    private void addComponent(Object component, String componentName) {
        if (appComponentsByName.containsKey(componentName)) {
            throw new RuntimeException(
                    "Error while creating component, " + "because component with such name is already added in context "
                            + component.getClass().getName());
        } else {
            appComponents.add(component);
            appComponentsByName.put(componentName, component);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        List<Object> components = appComponents.stream()
                .filter(c -> componentClass.isAssignableFrom(c.getClass()))
                .toList();
        if (components.isEmpty()) {
            throw new RuntimeException("No such component in context " + componentClass.getName());
        } else if (components.size() != 1) {
            throw new RuntimeException("Error. Duplicate components in context " + componentClass.getName());
        }
        return (C) components.getFirst();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C> C getAppComponent(String componentName) {
        if (appComponentsByName.containsKey(componentName)) {
            return (C) appComponentsByName.get(componentName);
        } else {
            throw new RuntimeException("No such component in context " + componentName);
        }
    }
}
