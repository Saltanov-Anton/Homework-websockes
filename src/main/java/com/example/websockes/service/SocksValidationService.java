package com.example.websockes.service;

import com.example.websockes.models.Color;
import com.example.websockes.models.Size;
import com.example.websockes.models.SocksBatch;

public interface SocksValidationService {

    boolean validation(SocksBatch socksBatch);

    boolean validation(Color color, Size size, int cottonMin, int cottonMax);

}
