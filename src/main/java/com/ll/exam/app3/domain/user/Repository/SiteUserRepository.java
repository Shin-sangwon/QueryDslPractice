package com.ll.exam.app3.domain.user.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteUserRepository extends JpaRepository<SiteUser, Long>, SiteUserRepositoryCustom {


}
