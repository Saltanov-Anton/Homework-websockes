package com.example.websockes.service;


import com.example.websockes.models.Color;
import com.example.websockes.models.Size;
import com.example.websockes.models.SocksBatch;

public interface SocksService {

    void accept(SocksBatch socksBatch);

    int release(SocksBatch socksBatch);

    int delete(SocksBatch socksBatch);

    int getRemains(Color color, Size size, int cottonPartMin, int cottonPartMax);
}
