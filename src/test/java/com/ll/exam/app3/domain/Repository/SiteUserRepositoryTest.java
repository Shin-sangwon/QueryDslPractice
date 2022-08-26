package com.ll.exam.app3.domain.Repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SiteUserRepositoryTest {

    @Autowired
    private SiteUserRepository siteUserRepository;

    @Test
    @DisplayName("회원 생성")
    public void 회원_생성된다() {
        SiteUser siteUser1 = new SiteUser(null, "user1", "{noop}1234", "test1@google.com");
        SiteUser siteUser2 = new SiteUser(null, "user2", "{noop}1234", "test2@google.com");

        siteUserRepository.saveAll(Arrays.asList(siteUser1, siteUser2));

    }

    @Test
    @DisplayName("회원 조회")
    public void 회원_조회된다() {
        SiteUser u1 = siteUserRepository.getQslUser(1L);

        assertThat(u1.getId()).isEqualTo(1L);
        assertThat(u1.getUsername()).isEqualTo("user1");
        assertThat(u1.getPassword()).isEqualTo("{noop}1234");
        assertThat(u1.getEmail()).isEqualTo("test1@google.com");
    }

}