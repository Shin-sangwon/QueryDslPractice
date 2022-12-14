package com.ll.exam.app3.domain.user.Repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.ll.exam.app3.domain.interestKeyword.repository.QInterestKeyword.interestKeyword;
import static com.ll.exam.app3.domain.user.Repository.QSiteUser.siteUser;

@RequiredArgsConstructor
public class SiteUserRepositoryImpl implements SiteUserRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public SiteUser getQslUser(Long id){

        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .where(siteUser.id.eq(id))
                .fetchOne();

    }

    public Long getQslUserCount() {

        return jpaQueryFactory
                .select(siteUser.count())
                .from(siteUser)
                .fetchOne();

    }

    @Override
    public SiteUser getQslUserOrderByIdAscOne() {

        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .orderBy(siteUser.id.asc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public List<SiteUser> getQslUsersOrderByIdAsc() {

        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .orderBy(siteUser.id.asc())
                .fetch();

    }

    @Override
    public List<SiteUser> getQslUsersUsingContains(String str) {

        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .where(siteUser.username.contains(str), siteUser.email.contains(str))
                .orderBy(siteUser.id.desc())
                .fetch();
    }

    @Override
    public Page<SiteUser> searchQsl(String kw, Pageable pageable) {
        JPAQuery<SiteUser> usersQuery = jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .where(siteUser.username.contains(kw), siteUser.email.contains(kw))
                .offset(pageable.getOffset()) // ????????? ?????? ?????????
                .limit(pageable.getPageNumber());// ??? ???????????? ????????? ????????????

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(siteUser.getType(), siteUser.getMetadata());
            usersQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }

        List<SiteUser> users = usersQuery.fetch();


        // return new PageImpl<>(users, pageable, usersQuery.fetchCount()); // ????????? ?????? ??????

        return PageableExecutionUtils.getPage(users, pageable, usersQuery::fetchCount);
    }

    @Override
    public List<SiteUser> getQslUserByHabit(String habit) {
        return jpaQueryFactory
                .selectFrom(siteUser)
                .innerJoin(siteUser.interestKeywordSet, interestKeyword)
                .where(
                        interestKeyword.content.eq(habit)
                )
                .fetch();
    }


}
