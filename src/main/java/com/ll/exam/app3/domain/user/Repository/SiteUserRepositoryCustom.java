package com.ll.exam.app3.domain.user.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SiteUserRepositoryCustom {
    SiteUser getQslUser(Long id);
    Long getQslUserCount();

    SiteUser getQslUserOrderByIdAscOne();

    List<SiteUser> getQslUsersOrderByIdAsc();

    List<SiteUser> getQslUsersUsingContains(String str);

    Page<SiteUser> searchQsl(String kw, Pageable pageable);
}
