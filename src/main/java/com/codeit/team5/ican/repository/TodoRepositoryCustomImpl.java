package com.codeit.team5.ican.repository;

import com.codeit.team5.ican.controller.dto.dashboard.Jandi;
import com.codeit.team5.ican.controller.dto.dashboard.TodoStats;
import com.codeit.team5.ican.domain.QTodo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryCustomImpl implements TodoRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Jandi> getJandi(long userId, int year) {
        QTodo todo = QTodo.todo;

        return jpaQueryFactory
                .select(Projections.constructor(Jandi.class,
                        todo.date,
                        todo.id.count()
                ))
                .from(todo)
                .where(
                        todo.user.id.eq(userId),
                        todo.date.year().eq(year),
                        todo.done.isTrue()
                )
                .groupBy(todo.date)
                .orderBy(todo.date.asc())
                .fetch();
    }

    @Override
    public TodoStats getTodoStats(long userId, LocalDate date) {
        QTodo todo = QTodo.todo;

        TodoStats todoStats =  jpaQueryFactory
                .select(Projections.constructor(TodoStats.class,
                        todo.count(), //total
                        todo.done.when(true).then(1).otherwise(0).sum().coalesce(0) //completed
                ))
                .from(todo)
                .where(
                        todo.user.id.eq(userId),
                        todo.date.eq(date)
                )
                .fetchOne();

        todoStats.setDate(date);
        return todoStats;
    }
}
