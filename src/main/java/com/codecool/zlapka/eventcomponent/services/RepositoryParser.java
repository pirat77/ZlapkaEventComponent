package com.codecool.zlapka.eventcomponent.services;

import com.codecool.zlapka.eventcomponent.model.Category;
import com.codecool.zlapka.eventcomponent.model.ZlapkaEntityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class RepositoryParser<T extends ZlapkaEntityModel, S extends CrudRepository<T, Long>> {

    @Autowired
    private JsonMapper jsonMapper;
    private Class<T> tClass;
    private S repository;

    public RepositoryParser() {}

    public RepositoryParser(Class<T> tClass, S repository) {
        this.tClass = tClass;
        this.repository = repository;
    }

    public String get(Map<String, String> parameters) {
        String output = "";
        if (parameters.keySet().isEmpty()) output = getAll();
        else output = getAllByParams(parameters);
        return output;
    }

    public Optional<T> add(String jsonElement) {
        Optional<T> optional = jsonMapper.getObjectFromJson(jsonElement, tClass);
        if (optional.isEmpty()) return optional;
        return Optional.of(repository.save(optional.get()));
    }

    public long delete(Map<String, String> parameters) {
        long output = 0;
        if (parameters.keySet().isEmpty()) output = deleteAll();
        else output = deleteAllByParams(parameters);
        return output;
    }

    public long replace(Map<String, String> parameters, String jsonElementsList) {
        long output = 0;
        if (parameters.keySet().isEmpty()) output = replaceAll(jsonElementsList);
        else output = replaceAllByParams(parameters, jsonElementsList);
        return output;
    }

    private String getAll(){
        List<T> allElements = new ArrayList<>();
        Iterable<T> elements = repository.findAll();
        elements.forEach(allElements::add);
        return jsonMapper.jsonRepresentation(elements);
    }

    private String getAllByParams(Map<String, String> parameters) {
        List<T> elements = findCommonElements(parameters);
        return jsonMapper.jsonRepresentation(elements);
    }

    private long deleteAll() {
        repository.deleteAll();
        return repository.count();
    }

    private long deleteAllByParams(Map<String, String> parameters) {
        List<T> elements = findCommonElements(parameters);
        if (elements.isEmpty()) return 0L;
        long before = repository.count();
        repository.deleteAll(elements);
        long after = repository.count();
        if (before - after != elements.size()) return -1L;
        return after;
    }

    private long replaceAll(String jsonElementsList) {
        List<T> elements = jsonMapper.getListOfObjectsFromJson(jsonElementsList, tClass);
        if (elements.isEmpty()) return 0L;

        repository.deleteAll();
        long currentNumberOfRows = repository.count();
        if (currentNumberOfRows != 0) return -1;

        repository.saveAll(elements);
        currentNumberOfRows = repository.count();
        if (currentNumberOfRows != elements.size()) return -1L;

        return currentNumberOfRows;
    }

    private long replaceAllByParams(Map<String, String> parameters, String jsonElementsList) {
        List<T> newElements = jsonMapper.getListOfObjectsFromJson(jsonElementsList, tClass);
        List<T> foundElements = findCommonElements(parameters);
        if (newElements.isEmpty() || foundElements.isEmpty()) return -1L;

        long before = repository.count();
        repository.deleteAll(foundElements);
        long after = repository.count();
        if (before - after != foundElements.size()) return -1L;

        before = after;
        repository.saveAll(newElements);
        after = repository.count();
        if (after - before != newElements.size()) return -1L;

        return after;
    }

    private <U> List<T> findCommonElements(Map<String, String> parameters) {
        String methodPrefix = "findBy";
        List<Method> findByMethods = Arrays.stream(repository.getClass().getMethods())
                .filter(method -> method.getName().startsWith(methodPrefix))
                .collect(Collectors.toList());
        Set<T> foundElements = new HashSet<>();
        boolean firstIteration = true;
        for(String paramName : parameters.keySet()) {
            Optional<Method> methodOptional = findByMethods.stream()
                    .filter(m -> m.getName().equals(String.format("%s%s", methodPrefix, paramName)))
                    .findFirst();
            if (methodOptional.isEmpty()) continue;
            String paramValue = parameters.get(paramName);
            List<T> nextElements = getElementsFromMethod(paramValue, methodOptional.get());
            if (firstIteration) foundElements.addAll(nextElements);
            else foundElements.retainAll(nextElements);
            firstIteration = false;
        }
        return new ArrayList<>(foundElements);
    }

    private <U> List<T> getElementsFromMethod(String paramValue, Method method) {
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
            return (List<T>) returnedObject;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private <U> Object insertObjectIntoListIfItIsOptional(Object possiblyOptional) {
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
