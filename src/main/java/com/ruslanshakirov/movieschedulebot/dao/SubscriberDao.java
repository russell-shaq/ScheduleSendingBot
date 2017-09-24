package com.ruslanshakirov.movieschedulebot.dao;

import com.ruslanshakirov.movieschedulebot.domain.Subscriber;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriberDao extends CrudRepository<Subscriber, Long> {
}
