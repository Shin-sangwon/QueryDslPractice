package com.ll.exam.app3.domain.user.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SiteUserRepository extends JpaRepository<SiteUser, Long>, SiteUserRepositoryCustom {

    List<SiteUser> findByInterestKeywordSet_content(String str);

}
