package com.ruslanshakirov.movieschedulebot.dao;

import com.ruslanshakirov.movieschedulebot.domain.MovieSchedule;
import org.springframework.data.repository.CrudRepository;

public interface MovieScheduleDao extends CrudRepository<MovieSchedule, Integer> {
}
