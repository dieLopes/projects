package com.diego.taskboard.validator;

public interface IValidator<T> {

    void validate(T entity);
}
