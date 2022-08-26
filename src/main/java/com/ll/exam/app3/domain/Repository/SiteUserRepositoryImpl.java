package com.ll.exam.app3.domain.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SiteUserRepositoryImpl implements SiteUserRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public SiteUser getQslUser(Long id){

        return jpaQueryFactory
                .select(QSiteUser.siteUser)
                .from(QSiteUser.siteUser)
                .where(QSiteUser.siteUser.id.eq(1L))
                .fetchOne();

    }

}
