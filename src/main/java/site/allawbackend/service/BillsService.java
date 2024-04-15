package site.allawbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.allawbackend.repository.BillsRepository;

@Service
@RequiredArgsConstructor
public class BillsService {
    private final BillsRepository billsRepository;
    public long countAll() {
        return billsRepository.countAll();
    }

}
