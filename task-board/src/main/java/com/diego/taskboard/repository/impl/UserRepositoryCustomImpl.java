package com.diego.taskboard.repository.impl;

import com.diego.taskboard.domain.User;
import com.diego.taskboard.repository.UserRepositoryCustom;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.MongoRegexCreator;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public UserRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<User> findByFilters(String name, String contact) {

        final Query query = new Query();
        final List<Criteria> criteria = new ArrayList<>();
        if(name != null && !name.isEmpty()) {
            criteria.add(Criteria.where("name").regex(
                    Objects.requireNonNull(MongoRegexCreator.INSTANCE.toRegularExpression(
                            name, MongoRegexCreator.MatchMode.CONTAINING)), "i"));
        }
        if(contact != null && !contact.isEmpty()) {
            criteria.add(Criteria.where("contacts.contact").regex(
                    Objects.requireNonNull(MongoRegexCreator.INSTANCE.toRegularExpression(
                            contact, MongoRegexCreator.MatchMode.CONTAINING)), "i"));
        }
        if(!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }
        return mongoTemplate.find(query, User.class);

    }
}
