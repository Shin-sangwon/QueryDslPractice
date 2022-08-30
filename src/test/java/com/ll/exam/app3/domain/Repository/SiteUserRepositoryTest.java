package com.ll.exam.app3.domain.Repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Test
    @DisplayName("모든 회원의 수 카운트")
    void t4() {
        Long count = siteUserRepository.getQslUserCount();
        assertThat(count).isGreaterThan(0);
    }

    @Test
    @DisplayName("가장 오래된 회원 1명")
    void t5() {
        SiteUser user = siteUserRepository.getQslUserOrderByIdAscOne();

        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getUsername()).isEqualTo("user1");
        assertThat(user.getEmail()).isEqualTo("user1@test.com");
        assertThat(user.getPassword()).isEqualTo("{noop}1234");

    }

    @Test
    @DisplayName("전체회원, 오래된 순")
    void t6() {
        List<SiteUser> users = siteUserRepository.getQslUsersOrderByIdAsc();

        SiteUser u1 = users.get(0);

        assertThat(u1.getId()).isEqualTo(1L);
        assertThat(u1.getUsername()).isEqualTo("user1");
        assertThat(u1.getEmail()).isEqualTo("user1@test.com");
        assertThat(u1.getPassword()).isEqualTo("{noop}1234");

        SiteUser u2 = users.get(1);

        assertThat(u2.getId()).isEqualTo(2L);
        assertThat(u2.getUsername()).isEqualTo("user2");
        assertThat(u2.getEmail()).isEqualTo("user2@test.com");
        assertThat(u2.getPassword()).isEqualTo("{noop}1234");
    }

    @Test
    @DisplayName("이름, 이메일로 검색하기")
    void t7() {
        List<SiteUser> users = siteUserRepository.getQslUsersUsingContains("user1");

        SiteUser u1 = users.get(0);

        assertThat(u1.getId()).isEqualTo(1L);
        assertThat(u1.getUsername()).isEqualTo("user1");
        assertThat(u1.getEmail()).isEqualTo("user1@test.com");
        assertThat(u1.getPassword()).isEqualTo("{noop}1234");
    }

    @Test
    @DisplayName("검색, Page 리턴, id DESC, pageSize=1, page=0")
    void t8() {
        long totalCount = siteUserRepository.count();
        int pageSize = 1; // 한 페이지에 보여줄 아이템 개수
        int totalPages = (int)Math.ceil(totalCount / (double)pageSize);
        int page = 1;
        String kw = "user";

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("id"));
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts)); // 한 페이지에 10까지 가능
        Page<SiteUser> usersPage = siteUserRepository.searchQsl(kw, pageable);

        assertThat(usersPage.getTotalPages()).isEqualTo(totalPages);
        assertThat(usersPage.getNumber()).isEqualTo(page);
        assertThat(usersPage.getSize()).isEqualTo(pageSize);

        List<SiteUser> users = usersPage.get().toList();

        assertThat(users.size()).isEqualTo(pageSize);

        SiteUser u = users.get(0);

        assertThat(u.getId()).isEqualTo(2L);
        assertThat(u.getUsername()).isEqualTo("user2");
        assertThat(u.getEmail()).isEqualTo("user2@test.com");
        assertThat(u.getPassword()).isEqualTo("{noop}1234");




        // 검색어 : user1
        // 한 페이지에 나올 수 있는 아이템 수 : 1개
        // 현재 페이지 : 1
        // 정렬 : id 역순

        // 내용 가져오는 SQL
        /*
        SELECT site_user.*
        FROM site_user
        WHERE site_user.username LIKE '%user%'
        OR site_user.email LIKE '%user%'
        ORDER BY site_user.id ASC
        LIMIT 1, 1
         */

        // 전체 개수 계산하는 SQL
        /*
        SELECT COUNT(*)
        FROM site_user
        WHERE site_user.username LIKE '%user%'
        OR site_user.email LIKE '%user%'
         */
    }

}