package com.ll.exam.app3.domain.controller;

import com.ll.exam.app3.domain.Repository.SiteUser;
import com.ll.exam.app3.domain.Repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SiteUserController {
    private final SiteUserRepository siteuserRepository;

    @RequestMapping("/user/{id}")
    public SiteUser user(@PathVariable Long id) {
        return siteuserRepository.getQslUser(id);
    }
}