package site.allawbackend.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.allawbackend.entity.District;
import site.allawbackend.entity.Member;
import site.allawbackend.entity.Party;
import site.allawbackend.entity.Region;
import site.allawbackend.repository.DistrictRepository;
import site.allawbackend.repository.MemberRepository;
import site.allawbackend.repository.PartyRepository;
import site.allawbackend.repository.RegionRepository;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final DistrictRepository districtRepository;
    private final RegionRepository regionRepository;
    private final PartyRepository partyRepository;

    @GetMapping("/region/{regionId}/district")
    public ResponseEntity<List<District>> getDistrictByRegion(@PathVariable Long regionId) {
        List<District> districts = districtRepository.findByRegionId(regionId);
        return ResponseEntity.ok(districts);
    }
    
    @GetMapping("/region")
    public ResponseEntity<List<Region>> getRegions() {
        List<Region> regions = regionRepository.findAll();
        return ResponseEntity.ok(regions);
    }

    @GetMapping("/members")
    public ResponseEntity<List<Member>> getMembers() {
        List<Member> members = memberRepository.findAll();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/member/{id}")
    public ResponseEntity<Member> getMember(@PathVariable Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("no such data")
                );
        return ResponseEntity.ok(member);
    }

    @GetMapping("/district/{districtId}/member")
    public ResponseEntity<List<Member>> getMemberByDistrict(@PathVariable Long districtId) {
        List<Member> members = memberRepository.findByDistrictId(districtId);
        return ResponseEntity.ok(members);
    }

    @PostMapping("/party")
    public ResponseEntity<Party> saveParty(@RequestBody Party party) {
        Party savedParty = partyRepository.save(party);
        return ResponseEntity.ok(savedParty);
    }
    @PostMapping("/region")
    public ResponseEntity<Region> saveRegion(@RequestBody Region region) {
        Region savedRegion = regionRepository.save(region);
        return ResponseEntity.ok(savedRegion);
    }

    @PostMapping("/district")
    public ResponseEntity<DistrictForm> saveDistrict(@RequestBody DistrictForm districtRequestForm) {
        Region region = regionRepository.findByName(districtRequestForm.regionName)
                .orElseThrow(
                        () -> new NoSuchElementException("no such data"));
        District district = new District();
        district.setName(districtRequestForm.districtName);
        district.setRegion(region);

        District savedDistrict = districtRepository.save(district);
        DistrictForm districtResponseForm = new DistrictForm(savedDistrict.getRegion().getName(), savedDistrict.getName());
        return ResponseEntity.ok(districtResponseForm);
    }

    @PostMapping("/member")
    public ResponseEntity<MemberForm> saveMember(@RequestBody MemberForm memberRequestForm) {
        District district = districtRepository.findByName(memberRequestForm.districtName)
                .orElseThrow(
                        () -> new NoSuchElementException("no district data")
                );
        Party party = partyRepository.findByName(memberRequestForm.partyName)
                .orElseThrow(
                        () -> new NoSuchElementException("no party data")
                );
        Member member = memberRequestForm.formToEntity();
        member.setDistrict(district);
        member.setParty(party);

        Member savedMember = memberRepository.save(member);
        MemberForm memberResponseForm = new MemberForm(savedMember.getName(), savedMember.getDistrict().getName(), savedMember.getParty().getName(), savedMember.getFulfillmentRate(), savedMember.getParticipationRate(), savedMember.getAttendanceRate(), savedMember.getVoteRate());

        return ResponseEntity.ok(memberResponseForm);
    }

    @Data
    @AllArgsConstructor
    public static class DistrictForm {
        private String regionName;
        private String districtName;
    }

    @Data
    @AllArgsConstructor
    public static class MemberForm {
        private String name;
        private String districtName;
        private String partyName;
        private int fulfillmentRate;
        private int participationRate;
        private int attendanceRate;
        private int voteRate;

        public Member formToEntity() {
            Member member = new Member();
            member.setName(this.name);
            member.setFulfillmentRate(this.fulfillmentRate);
            member.setParticipationRate(this.participationRate);
            member.setAttendanceRate(this.attendanceRate);
            member.setVoteRate(this.voteRate);
            return member;
        }
    }



//    @PostMapping("/region/{regionId}/district")
//    public ResponseEntity<District> createDistrict(@RequestBody District district) {
//
//    }



//    @GetMapping("/members")
//    public List<Member> members() {
////        return memberService.findAll();
//    }
//
//    @GetMapping("/member/{id}")
//    public Member member(@PathVariable("id") Long id) {
////        return memberService.findById(id);
//    }
//
//    @PostMapping("/member")
//    public Member createMember(@RequestBody Member member) {
////        return memberService.save(member);
//    }


}
