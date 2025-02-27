package com.codeit.team5.ican.repository;

import com.codeit.team5.ican.domain.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GoalRepositoryCustomImpl implements GoalRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Goal> findGoalWithTodosAndBasketTodos(long userId, long goalId) {
        QGoal goal = QGoal.goal;
        QTodo todo = QTodo.todo;
        QBasketTodo basketTodo = QBasketTodo.basketTodo;

        Goal findGoal = jpaQueryFactory
                .selectFrom(goal)
                .leftJoin(goal.todos, todo).fetchJoin()
                .leftJoin(goal.basketTodos, basketTodo).fetchJoin()
                .where(
                        goal.user.id.eq(userId),
                        goal.goalId.eq(goalId)
                )
                .fetchOne();

        return Optional.ofNullable(findGoal);
    }
}
