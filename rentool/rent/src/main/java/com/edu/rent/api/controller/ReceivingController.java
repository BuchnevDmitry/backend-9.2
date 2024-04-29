package com.edu.rent.api.controller;

import com.edu.rent.model.ReceivingMethod;
import com.edu.rent.model.Status;
import com.edu.rent.service.impl.ReceivingService;
import com.edu.rent.service.impl.StatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/receiving-methods/")
public class ReceivingController {
    private final ReceivingService receivingService;

    @Operation(summary = "Получить все методы получения заказа")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Все методы получения заказа получены")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public List<ReceivingMethod> getMethods  () {
        List<ReceivingMethod> receivingMethods = receivingService.getAllItems();
        return receivingMethods;
    }

}
