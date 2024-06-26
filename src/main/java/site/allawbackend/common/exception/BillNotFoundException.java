package site.allawbackend.common.exception;

import lombok.Getter;

@Getter
public class BillNotFoundException extends RuntimeException{
    private final Long billId;

    public BillNotFoundException(Long billId) {
        super("존재하지 않는 법안입니다.");
        this.billId = billId;
    }

}
