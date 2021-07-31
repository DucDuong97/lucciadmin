package com.lucci.webadmin.service;

public class EntityAlreadyFinishedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EntityAlreadyFinishedException() {
        super("Entity is already finished!");
    }
}
