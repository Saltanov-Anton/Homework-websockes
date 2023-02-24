package com.example.websockes.repository;

import com.example.websockes.models.Socks;
import com.example.websockes.models.SocksBatch;

import java.util.Map;

public interface SocksRepository {

    void save(SocksBatch socksBatch);

    int delete(SocksBatch socksBatch);

    Map<Socks, Integer> getAll();
}
