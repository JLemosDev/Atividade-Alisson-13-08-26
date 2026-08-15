package com.loja.repositorio;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RepositorioUtils {

    public static <E> List<E> filtrar(List<E> lista, Predicate<E> criterio) {
        return lista.stream()
                .filter(criterio)
                .collect(Collectors.toList());
    }

    public static <E, R> List<R> mapear(List<E> lista, Function<E, R> transformador) {
        return lista.stream()
                .map(transformador)
                .collect(Collectors.toList());
    }
}