package site.allawbackend.opinion.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.allawbackend.common.exception.BillMismatchException;
import site.allawbackend.common.exception.BillNotFoundException;
import site.allawbackend.common.exception.UnauthorizedUserException;
import site.allawbackend.common.exception.UserNotFoundException;
import site.allawbackend.entity.Bill;
import site.allawbackend.entity.User;
import site.allawbackend.opinion.dto.OpinionDto;
import site.allawbackend.opinion.dto.OpinionRequest;
import site.allawbackend.opinion.dto.OpinionResponse;
import site.allawbackend.opinion.entity.Opinion;
import site.allawbackend.opinion.repository.OpinionRepository;
import site.allawbackend.repository.BillRepository;
import site.allawbackend.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpinionService {

    private final UserRepository userRepository;
    private final BillRepository billRepository;
    private final OpinionRepository opinionRepository;

    private final OpinionMapper opinionMapper;

    @Transactional
    public Long createOpinion(Long userId, OpinionRequest opinionRequest) {
        User user = getUser(userId);
        Bill bill = getBill(opinionRequest.billId());
        Opinion opinion = opinionMapper.toEntity(user, bill, opinionRequest);
        Opinion savedOpinion = opinionRepository.save(opinion);
        return savedOpinion.getId();
    }

    @Transactional
    public OpinionResponse getOpinion(Long opinionId) {
        OpinionDto opinionDto = getOpinionDto(opinionId);
        return opinionMapper.toResponse(opinionDto);
    }

    @Transactional
    public List<OpinionResponse> getOpinionsByBillId(Long billId) {
        Bill bill = getBill(billId);
        List<OpinionDto> opinionDtos = getOpinionDtos(bill);
        return opinionDtos.stream()
                .map(opinionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public OpinionResponse updateOpinion(Long userId, Long opinionId, OpinionRequest opinionRequest) {
        Opinion opinion = getOpinionEntity(opinionId);
        validateUser(userId, opinion);
        validateBill(opinionRequest, opinion);
        opinion.update(opinionRequest.detail(), opinionRequest.grade());
        return opinionMapper.toResponse(opinion.toDto());
    }
    @Transactional
    public void deleteOpinion(Long userId, Long opinionId) {
        Opinion opinion = getOpinionEntity(opinionId);
        validateUser(userId, opinion);
        opinionRepository.delete(opinion);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
    private Bill getBill(Long billId) {
        return billRepository.findById(billId)
                .orElseThrow(() -> new BillNotFoundException(billId));
    }
    private Opinion getOpinionEntity(Long opinionId) {
        return opinionRepository.findById(opinionId)
                .orElseThrow(() -> new OpinionNotFoundException(opinionId));
    }

    private OpinionDto getOpinionDto(Long opinionId) {
        Opinion opinion = getOpinionEntity(opinionId);
        return opinionMapper.toDto(opinion);
    }
    private List<OpinionDto> getOpinionDtos(Bill bill) {
        List<Opinion> opinions = opinionRepository.findByBill(bill)
                .orElseThrow(() -> new BillNotFoundException(bill.getId()));
        return opinions.stream()
                .map(opinionMapper::toDto)
                .collect(Collectors.toList());
    }

    private void validateUser(Long userId, Opinion opinion) {
        if (!userId.equals(opinion.getUser().getId())) {
            throw new UnauthorizedUserException(userId);
        }
    }

    private void validateBill(OpinionRequest opinionRequest, Opinion opinion) {
        if (!opinionRequest.billId().equals(opinion.getBill().getId())) {
            throw new BillMismatchException(opinionRequest.billId());
        }
    }
}