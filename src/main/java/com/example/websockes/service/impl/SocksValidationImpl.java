package com.example.websockes.service.impl;

import com.example.websockes.models.Color;
import com.example.websockes.models.Size;
import com.example.websockes.models.SocksBatch;
import com.example.websockes.service.SocksValidationService;
import org.springframework.stereotype.Service;

@Service
public class SocksValidationImpl implements SocksValidationService {
    @Override
    public boolean validation(SocksBatch socksBatch) {
        return socksBatch.getSocks() != null
                && socksBatch.getQuantity() > 0
                && socksBatch.getSocks().getColor() != null
                && socksBatch.getSocks().getSize() != null
                && checkCotton(socksBatch.getSocks().getCottonPart(), socksBatch.getSocks().getCottonPart());
    }

    @Override
    public boolean validation(Color color, Size size, int cottonMin, int cottonMax) {
        return color != null && size != null
                && checkCotton(cottonMin, cottonMax);
    }

    private boolean checkCotton(int cottonMin, int cottonMax) {
        return cottonMin >= 0 && cottonMax <= 100;
    }
}
