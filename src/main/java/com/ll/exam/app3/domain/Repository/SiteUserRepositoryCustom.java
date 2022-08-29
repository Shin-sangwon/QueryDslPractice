package com.ll.exam.app3.domain.Repository;

public interface SiteUserRepositoryCustom {
    SiteUser getQslUser(Long id);
    Long getQslUserCount();

    SiteUser getQslUserOrderByIdAscOne();
}
