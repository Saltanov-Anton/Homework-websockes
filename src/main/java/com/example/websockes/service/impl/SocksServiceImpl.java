package com.example.websockes.service.impl;

import com.example.websockes.exceptions.SocksValidationException;
import com.example.websockes.models.Color;
import com.example.websockes.models.Size;
import com.example.websockes.models.Socks;
import com.example.websockes.models.SocksBatch;
import com.example.websockes.repository.SocksRepository;
import com.example.websockes.service.SocksService;
import com.example.websockes.service.SocksValidationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@AllArgsConstructor
public class SocksServiceImpl implements SocksService {

    private SocksRepository socksRepository;
    private SocksValidationService socksValidationService;

    @Override
    public void accept(SocksBatch socksBatch) {
        checkSocksBatch(socksBatch);
        socksRepository.save(socksBatch);
    }

    @Override
    public int release(SocksBatch socksBatch) {
        checkSocksBatch(socksBatch);
        return socksRepository.delete(socksBatch);
    }

    @Override
    public int delete(SocksBatch socksBatch) {
        checkSocksBatch(socksBatch);
        return socksRepository.delete(socksBatch);
    }

    @Override
    public int getRemains(Color color, Size size, int cottonPartMin, int cottonPartMax) {

        if (!socksValidationService.validation(color, size, cottonPartMin, cottonPartMax)){
            throw new SocksValidationException();
        }

        Map<Socks, Integer> socksMap = socksRepository.getAll();

        for (Map.Entry<Socks, Integer> socksEntry : socksMap.entrySet()) {
            Socks socks = socksEntry.getKey();
            if (socks.getColor().equals(color) && socks.getSize().equals(size)
                    && socks.getCottonPart() >= cottonPartMin && socks.getCottonPart() <= cottonPartMax) {
                return socksEntry.getValue();
            }
        }
        return 0;
    }

    private void checkSocksBatch(SocksBatch socksBatch) {

        if (!socksValidationService.validation(socksBatch)) {
            throw new SocksValidationException();
        }
    }
}
