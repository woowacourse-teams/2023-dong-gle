package org.donggle.backend.ui;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.service.MemberService;
import org.donggle.backend.auth.support.AuthenticationPrincipal;
import org.donggle.backend.ui.response.MemberPageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<MemberPageResponse> memberPage(@AuthenticationPrincipal final Long memberId) {
        final MemberPageResponse memberPage = memberService.findMemberPage(memberId);
        return ResponseEntity.ok(memberPage);
    }
}
