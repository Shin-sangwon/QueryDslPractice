package com.ll.exam.app3.domain.Repository;

import java.util.List;

public interface SiteUserRepositoryCustom {
    SiteUser getQslUser(Long id);
    Long getQslUserCount();

    SiteUser getQslUserOrderByIdAscOne();

    List<SiteUser> getQslUsersOrderByIdAsc();
}
