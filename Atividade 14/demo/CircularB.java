package di.demo;

import di.annotations.Inject;

public class CircularB {
    @Inject
    private CircularA a;
}
