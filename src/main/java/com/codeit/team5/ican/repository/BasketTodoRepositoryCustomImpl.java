package com.codeit.team5.ican.repository;

import com.codeit.team5.ican.domain.BasketTodo;
import com.codeit.team5.ican.domain.QBasketTodo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BasketTodoRepositoryCustomImpl implements BasketTodoRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<BasketTodo> findAllByUserIdOrderByCreatedAtAsc(long userId) {
        QBasketTodo basketTodo = QBasketTodo.basketTodo;

        return jpaQueryFactory
                .selectFrom(basketTodo)
                .leftJoin(basketTodo.goal).fetchJoin()
                .where(basketTodo.user.id.eq(userId)).fetchJoin()
                .orderBy(basketTodo.createdAt.asc())
                .fetch();
    }
}
