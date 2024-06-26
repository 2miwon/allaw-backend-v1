package site.allawbackend.common.exception;

import lombok.Getter;

@Getter
public class BillMismatchException extends RuntimeException{
    private final Long billId;

    public BillMismatchException(Long billId) {
        super("법안이 일치하지 않습니다.");
        this.billId = billId;
    }
}