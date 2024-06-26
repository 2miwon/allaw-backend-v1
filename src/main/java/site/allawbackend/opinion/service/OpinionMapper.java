package site.allawbackend.opinion.service;

import org.springframework.stereotype.Component;
import site.allawbackend.entity.Bill;
import site.allawbackend.entity.User;
import site.allawbackend.opinion.dto.OpinionDto;
import site.allawbackend.opinion.dto.OpinionRequest;
import site.allawbackend.opinion.dto.OpinionResponse;
import site.allawbackend.opinion.entity.Opinion;

@Component
public class OpinionMapper {

    public Opinion toEntity(User user, Bill bill, OpinionRequest opinionRequest) {
        return new Opinion(
                user,
                bill,
                opinionRequest.detail(),
                opinionRequest.grade());
    }

    public OpinionDto toDto(Opinion opinion) {
        return new OpinionDto(
                opinion.getId(),
                opinion.getUser().getName(),
                opinion.getBill().getBillNo(),
                opinion.getDetail(),
                opinion.getGrade(),
                opinion.getCreatedDate(),
                opinion.getLastModifiedDate()
        );
    }

    public OpinionResponse toResponse(OpinionDto opinionDto) {
        return new OpinionResponse(
                opinionDto.id(),
                opinionDto.userName(),
                opinionDto.billsNo(),
                opinionDto.detail(),
                opinionDto.grade(),
                opinionDto.createdDate(),
                opinionDto.lastModifiedDate()
        );
    }
}
