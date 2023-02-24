package com.example.websockes.controllers;

import com.example.websockes.dto.ResponseDto;
import com.example.websockes.models.Color;
import com.example.websockes.models.Size;
import com.example.websockes.models.SocksBatch;
import com.example.websockes.service.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/socks")
@Tag(name = "Склад носков", description = "Склад может учитывать и автоматизировать учет" +
        "товаров на складе интернет-магазина носков")
@RequiredArgsConstructor
public class SocksController {

    private final SocksService socksService;

    @PostMapping
    @Operation(summary = "Добавление носков", description = "Регистрирует приход товара на склад")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "удалось добавить приход"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "параметры запроса отсутствуют или" +
                            "имеют некорректный формат"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "произошла ошибка, не зависящая от вызывающей стороны"
            )
    }
    )
    public ResponseEntity<ResponseDto> addSocks(@RequestBody SocksBatch socksBatch) {
        socksService.accept(socksBatch);
        return ResponseEntity.ok(new ResponseDto("Партия успешно добавлена"));
    }

    @PutMapping
    @Operation(summary = "Отпуск носков", description = "Регистрирует отпуск носков со склада")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "удалось произвести отпуск носков со склада"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "товара нет на складе в нужном количестве или" +
                            "параметры запроса имеют некорректный формат"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "произошла ошибка, не зависящая от вызывающей стороны"
            )
    }
    )
    public ResponseEntity<ResponseDto> releaseSocks(@RequestBody SocksBatch socksBatch) {
        int socksCount = socksService.release(socksBatch);
        return ResponseEntity.ok(new ResponseDto("Списание в количестве - " + socksCount + "шт"));
    }

    @GetMapping
    @Operation(summary = "Посмотреть остаток", description = "Возвращает общее количество носков на складе," +
            "соответствующих переданным в параметрах критериям запроса")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "запрос выполнен, результат в теле ответа в" +
                            "виде строкового представления целого числа"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "параметры запроса отсутствуют или имеют некорректный формат"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "произошла ошибка, не зависящая от вызывающей стороны"
            )
    }
    )
    public ResponseEntity<ResponseDto> getSocks(@RequestParam Color color, @RequestParam Size size,
                                             @RequestParam int cottonPartMax, @RequestParam int cottonPartMin) {
        int socksCount = socksService.getRemains(color, size, cottonPartMin, cottonPartMax);

        return ResponseEntity.ok(new ResponseDto("Остаток - " + socksCount + "шт"));
    }

    @DeleteMapping
    @Operation(summary = "Списание товара", description = "Регистрирует списание испорченных (бракованных) носков")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "запрос выполнен, товар списан со склада"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "параметры запроса отсутствуют или имеют некорректный формат"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "произошла ошибка, не зависящая от вызывающей стороны"
            )
    }
    )
    public ResponseEntity<ResponseDto> deleteSocks(@RequestBody SocksBatch socksBatch) {
        int socksCount = socksService.delete(socksBatch);
        return ResponseEntity.ok(new ResponseDto("Списание в количестве - " + socksCount + "шт"));
    }

}
