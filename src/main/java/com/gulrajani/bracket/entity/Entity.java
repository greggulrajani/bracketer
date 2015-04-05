package com.gulrajani.bracket.entity;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public abstract class Entity {

    private static AtomicInteger nextId = new AtomicInteger();

    @JsonProperty
    private final int id;

    public Entity() {
        id = nextId.incrementAndGet();
    }

    public int getAtomicInteger() {
        return nextId.get();
    }

    @SuppressWarnings("unused")
    private void setAtomicInteger(int value) {
        nextId = new AtomicInteger(value);
    }
}
