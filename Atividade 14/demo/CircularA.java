package di.demo;

import di.annotations.Inject;

public class CircularA {
    @Inject
    private CircularB b;
}
