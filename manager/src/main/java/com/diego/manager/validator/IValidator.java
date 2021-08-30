package com.diego.manager.validator;

public interface IValidator<T> {

    void validate(T entity);
}
