package site.allawbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.allawbackend.entity.Bills;
import site.allawbackend.repository.BillsRepository;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final BillsRepository billsRepository;
    public String pdfUrl(Integer id) {
        Bills bills = billsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 id가 없습니다.")
        );
        return bills.getFile_link();
    }
}
