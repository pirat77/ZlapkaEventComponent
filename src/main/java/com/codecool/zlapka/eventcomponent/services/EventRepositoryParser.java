package com.codecool.zlapka.eventcomponent.services;

import com.codecool.zlapka.eventcomponent.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class EventRepositoryParser<Event, S extends CrudRepository<Event, Long>> {

    @Autowired
    private JsonMapper jsonMapper;
    private Class<Event> tClass;
    private S repository;

    public EventRepositoryParser(Class<Event> tClass, S repository) {
        this.tClass = tClass;
        this.repository = repository;
    }

    public String get(Map<String, String> parameters) {
        if (parameters.keySet().isEmpty())
            return getAll();
        else
            return getAllByParams(parameters);
    }

    public Optional<Event> add(String jsonElement) {
        Optional<Event> optional = jsonMapper.getObjectFromJson(jsonElement, tClass);
        if (optional.isEmpty()) return optional;
        return Optional.of(repository.save(optional.get()));
    }

    public long delete(Map<String, String> parameters) {
        if (parameters.keySet().isEmpty())
            return deleteAll();
        else
            return deleteAllByParams(parameters);
    }

    private String getAll(){
        return jsonMapper.jsonRepresentation(repository.findAll());
    }

    private String getAllByParams(Map<String, String> parameters) {
        return jsonMapper.jsonRepresentation(findCommonElements(parameters));
    }

    private long deleteAll() {
        long output = repository.count();
        repository.deleteAll();
        return output;
    }

    private long deleteAllByParams(Map<String, String> parameters) {
        List<Event> elements = findCommonElements(parameters);
        if (elements.isEmpty()) return 0L;
        repository.deleteAll(elements);
        return elements.size();
    }

    private <U> List<Event> findCommonElements(Map<String, String> parameters) {
        String methodPrefix = "findBy";
        List<Method> findByMethods = Arrays.stream(repository.getClass().getMethods())
                .filter(method -> method.getName().startsWith(methodPrefix))
                .collect(Collectors.toList());
        Set<Event> foundElements = new HashSet<>();
        boolean firstIteration = true;
        for(String paramName : parameters.keySet()) {
            Optional<Method> methodOptional = findByMethods.stream()
                    .filter(m -> m.getName().equals(String.format("%s%s", methodPrefix, paramName)))
                    .findFirst();
            if (methodOptional.isEmpty()) continue;
            String paramValue = parameters.get(paramName);
            List<Event> nextElements = getElementsFromMethod(paramValue, methodOptional.get());
            if (firstIteration) foundElements.addAll(nextElements);
            else foundElements.retainAll(nextElements);
            firstIteration = false;
        }
        return new ArrayList<>(foundElements);
    }

    private <U> List<Event> getElementsFromMethod(String paramValue, Method method) {
        U parameterValue;
        if (Arrays.stream(method.getGenericParameterTypes()).findAny().get().equals(Category.class))
            parameterValue = castParameterFromGivenClass(Category.valueOf(paramValue), method);
        else if (Arrays.stream(method.getGenericParameterTypes()).findAny().get().equals(Long.class))
            parameterValue = castParameterFromGivenClass(Long.valueOf(paramValue), method);
        else parameterValue = castParameterFromGivenClass(paramValue, method);
        if (parameterValue == null) return Collections.emptyList();
        try {
            Object returnedObject = method.invoke(repository, parameterValue);
            returnedObject = insertObjectIntoListIfItIsOptional(returnedObject);
            return (List<Event>) returnedObject;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private Object insertObjectIntoListIfItIsOptional(Object possiblyOptional) {
        if (possiblyOptional instanceof Optional) {
            if (((Optional) possiblyOptional).isPresent()) return List.of(((Optional) possiblyOptional).get());
            else return Collections.emptyList();
        }
        return possiblyOptional;
    }

    private <U> U castParameterFromGivenClass(Category parameter, Method method) {
        try {
            return ((Class<U>) method.getParameterTypes()[0]).cast(parameter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private <U> U castParameterFromGivenClass(Long parameter, Method method) {
        try {
            return ((Class<U>) Long.class).cast(Long.valueOf(parameter));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private <U> U castParameterFromGivenClass(String parameter, Method method) {
        try {
            return ((Class<U>) method.getParameterTypes()[0]).cast(parameter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
