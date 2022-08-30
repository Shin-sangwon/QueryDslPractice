package com.ll.exam.app3.domain.interestKeyword.repository;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Builder
public class InterestKeyword {
    @Id
    @EqualsAndHashCode.Include
    private String content;

}