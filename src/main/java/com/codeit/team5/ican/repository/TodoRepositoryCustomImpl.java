package com.codeit.team5.ican.repository;

import com.codeit.team5.ican.controller.dto.calendar.CalendarTodoDTO;
import com.codeit.team5.ican.controller.dto.dashboard.Jandi;
import com.codeit.team5.ican.controller.dto.dashboard.TodoStats;
import com.codeit.team5.ican.domain.QGoal;
import com.codeit.team5.ican.domain.QTodo;
import com.codeit.team5.ican.domain.Todo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.YearMonth;
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
                        todo.done.when(true).then(1).otherwise(0).sum().multiply(100.0).divide(todo.id.count())
                ))
                .from(todo)
                .where(
                        todo.user.id.eq(userId),
                        todo.date.year().eq(year)
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

    @Override
    public List<Todo> getMonthlyTodos(long userId, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = YearMonth.of(year, month).atEndOfMonth();

        QTodo todo = QTodo.todo;
        QGoal goal = QGoal.goal;

        return jpaQueryFactory
                .select(todo)
                .from(todo)
                .leftJoin(todo.goal, goal).fetchJoin() // N + 1 방지
                .where(
                        todo.user.id.eq(userId),
                        todo.date.between(startDate, endDate)
                )
                .orderBy(
                        todo.date.asc(),
                        todo.createdAt.asc()
                )
                .fetch();
    }

    @Override
    public List<Todo> getDailyTodos(long userId, LocalDate date) {
        QTodo todo = QTodo.todo;
        QGoal goal = QGoal.goal;

        return jpaQueryFactory
                .select(todo)
                .from(todo)
                .leftJoin(todo.goal, goal).fetchJoin() // N + 1 방지
                .where(
                        todo.user.id.eq(userId),
                        todo.date.eq(date)
                )
                .orderBy(
                        todo.createdAt.asc()
                )
                .fetch();
    }
}
