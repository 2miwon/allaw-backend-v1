package site.allawbackend.opinion.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import site.allawbackend.common.ApiResponse;
import site.allawbackend.common.auth.dto.SessionUser;
import site.allawbackend.common.exception.UserNotAuthenticatedException;
import site.allawbackend.opinion.dto.OpinionRequest;
import site.allawbackend.opinion.dto.OpinionResponse;
import site.allawbackend.opinion.service.OpinionService;

import java.net.URI;
import java.util.List;

import static site.allawbackend.common.ApiResponse.success;


@RestController
@RequestMapping("/api/opinions")
@RequiredArgsConstructor
public class OpinionController {

    private final HttpSession httpSession;
    private final OpinionService opinionService;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createOpinion(@RequestBody OpinionRequest opinionRequest) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user == null) throw new UserNotAuthenticatedException();

        Long opinionId = opinionService.createOpinion(user.getUserId(), opinionRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(opinionId).toUri();
        return ResponseEntity.created(location).body(success(null, "Opinion created successfully"));
    }

    @GetMapping("/{opinionId}")
    public ResponseEntity<ApiResponse<OpinionResponse>> getOpinion(@PathVariable Long opinionId) {
        OpinionResponse opinionResponse = opinionService.getOpinion(opinionId);
        return ResponseEntity.ok(success(opinionResponse, "Opinion retrieved successfully"));
    }

    @GetMapping("/bill/{billId}")
    public ResponseEntity<ApiResponse<List<OpinionResponse>>> getOpinionsByBillId(@PathVariable Long billId) {
        List<OpinionResponse> opinionsByBillId = opinionService.getOpinionsByBillId(billId);
        return ResponseEntity.ok(success(opinionsByBillId, "Opinions retrieved successfully"));
    }

    @PutMapping("/{opinionId}")
    public ResponseEntity<ApiResponse<OpinionResponse>> updateOpinion(@PathVariable Long opinionId, @RequestBody OpinionRequest opinionRequest) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user == null) throw new UserNotAuthenticatedException();

        OpinionResponse opinionResponse = opinionService.updateOpinion(user.getUserId(), opinionId, opinionRequest);
        return ResponseEntity.ok(success(opinionResponse, "Opinion updated successfully"));
    }

    @DeleteMapping("/{opinionId}")
    public ResponseEntity<ApiResponse<Void>> deleteOpinion(@PathVariable Long opinionId) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user == null) throw new UserNotAuthenticatedException();

        opinionService.deleteOpinion(user.getUserId(), opinionId);
        return ResponseEntity.ok(success(null, "Opinion deleted successfully"));
    }
}
