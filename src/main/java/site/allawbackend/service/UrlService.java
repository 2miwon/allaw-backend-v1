package site.allawbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.allawbackend.entity.Bill;
import site.allawbackend.repository.BillRepository;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final BillRepository billRepository;
    public String pdfUrl(Integer id) {
        Bill bill = billRepository.findByBillNo(id).orElseThrow(
                () -> new IllegalArgumentException("해당 id가 없습니다.")
        );
        return bill.getFileLink();
    }
}
