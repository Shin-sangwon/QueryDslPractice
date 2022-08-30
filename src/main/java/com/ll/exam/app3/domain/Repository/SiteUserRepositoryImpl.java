package com.ll.exam.app3.domain.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.function.LongSupplier;

import static com.ll.exam.app3.domain.Repository.QSiteUser.siteUser;

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
        List<SiteUser> users = jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .where(siteUser.username.contains(kw), siteUser.email.contains(kw))
                .offset(pageable.getOffset()) // 몇개를 건너 뛸건지
                .limit(pageable.getPageNumber()) // 한 페이지에 몇개가 보일건지
                .orderBy(siteUser.id.asc())
                .fetch();

        LongSupplier longSupplier = () -> 2;


        return PageableExecutionUtils.getPage(users, pageable, longSupplier);
    }
}
