package com.ll.exam.app3.domain.Repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Transactional // @test + @transactional은 자동으로 롤백을 유발함. (스프링 테스트 자체 규칙)
@SpringBootTest
class SiteUserRepositoryTest {

    @Autowired
    private SiteUserRepository siteUserRepository;

    @Test
    @DisplayName("회원 생성")
    public void 회원_생성된다() {
        SiteUser siteUser3 = SiteUser.builder()
                .username("user3")
                .password("{noop}1234")
                .email("test3@google.com")
                .build();
        SiteUser siteUser4 = new SiteUser(null, "user4", "{noop}1234", "test4@google.com");

        siteUserRepository.saveAll(Arrays.asList(siteUser3, siteUser4));

    }

    @Test
    @DisplayName("회원 조회1")
    public void 회원_조회된다() {
        SiteUser u1 = siteUserRepository.getQslUser(1L);

        assertThat(u1.getId()).isEqualTo(1L);
        assertThat(u1.getUsername()).isEqualTo("user1");
        assertThat(u1.getPassword()).isEqualTo("{noop}1234");
        assertThat(u1.getEmail()).isEqualTo("user1@test.com");
    }

    @Test
    @DisplayName("회원 조회2")
    public void 회원_조회된다_2() {
        SiteUser u2 = siteUserRepository.getQslUser(2L);

        assertThat(u2.getId()).isEqualTo(2L);
        assertThat(u2.getUsername()).isEqualTo("user2");
        assertThat(u2.getPassword()).isEqualTo("{noop}1234");
        assertThat(u2.getEmail()).isEqualTo("user2@test.com");
    }

}