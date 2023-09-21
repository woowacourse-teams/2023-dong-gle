package org.donggle.backend;

import org.donggle.backend.application.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/count")
public class MemberCount {
    private final MemberRepository memberRepository;

    public MemberCount(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping
    public ResponseEntity<Integer> asdasdads() {
        final int i = memberRepository.countMember();
        return ResponseEntity.ok(i);
    }

    @GetMapping("add")
    public ResponseEntity<Integer> asdasd() {
        final int i1 = memberRepository.addWritings();
        return ResponseEntity.ok(i1);
    }

    @GetMapping("published")
    public ResponseEntity<Integer> asdasdad() {
        final int pcount = memberRepository.pcount();
        return ResponseEntity.ok(pcount);
    }
}
