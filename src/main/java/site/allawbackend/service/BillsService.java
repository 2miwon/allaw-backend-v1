package site.allawbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.allawbackend.repository.BillRepository;

@Service
@RequiredArgsConstructor
public class BillsService {
    private final BillRepository billRepository;
    public long countAll() {
        return billRepository.count();
    }

}
