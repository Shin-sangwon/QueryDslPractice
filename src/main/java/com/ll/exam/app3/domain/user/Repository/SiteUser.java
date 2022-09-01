package com.ll.exam.app3.domain.user.Repository;

import com.ll.exam.app3.domain.interestKeyword.repository.InterestKeyword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @ManyToMany(cascade = CascadeType.ALL)
    @Builder.Default
    private Set<InterestKeyword> interestKeywordSet = new HashSet<>();

    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<SiteUser> followers = new HashSet<>();

    // ex ) u1.follow(u2) 에서 주어는 u1, 따라서 this는 u1
    public void follow(SiteUser u2) {

        if(u2.equals(this)) {
            return;
        }

        u2.getFollowers().add(this);
    }
    public void addFollower(SiteUser follower) {
        followers.add(follower);
    }

    public void addInterestKeywordContent(String keywordContent) {
        interestKeywordSet.add(new InterestKeyword(keywordContent));
    }
}
