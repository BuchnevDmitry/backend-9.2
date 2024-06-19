package com.edu.rent.service;

import com.edu.rent.access.Access;
import com.edu.rent.api.mapper.ToolMapper;
import com.edu.rent.api.model.request.ToolQuantityUpdateRequest;
import com.edu.rent.api.model.response.ListToolResponse;
import com.edu.rent.api.model.response.ToolResponse;
import com.edu.rent.client.ToolClient;
import com.edu.rent.exception.BadRequestException;
import com.edu.rent.model.Rent;
import com.edu.rent.model.RentTool;
import com.edu.rent.model.Status;
import com.edu.rent.repository.RentRepository;
import com.edu.rent.service.impl.RentService;
import com.edu.rent.service.impl.StatusService;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RentServiceTest {
    @Mock
    private RentRepository rentRepository;

    @Mock
    private StatusService statusService;
    @Mock
    private Access access;

    @Mock
    private ToolMapper toolMapper;

    @Mock
    private ToolClient toolClient;

    @InjectMocks
    private RentService rentService;

    @Test
    public void testSaveRent() {
        UUID userId = UUID.randomUUID();
        Rent rent = new Rent();
        rent.setUserId(userId);
        rent.setStartDate(OffsetDateTime.now());
        rent.setEndDate(OffsetDateTime.now().plusDays(7));
        RentTool rentTool = new RentTool();
        rentTool.setToolId(UUID.randomUUID());
        rentTool.setCountTool(1L);
        rent.setTools(Collections.singleton(rentTool));

        Set<RentTool> rentToolsSet = new HashSet<>(Collections.singleton(rentTool));
        Mockito.lenient().when(toolMapper.mapRentToToolQuantity(rentTool))
            .thenReturn(new ToolQuantityUpdateRequest(rentTool.getToolId(), rentTool.getCountTool()));
        when(toolMapper.mapRentToToolQuantity(rentToolsSet)).thenReturn(Collections.singletonList(new ToolQuantityUpdateRequest(
            rentTool.getToolId(),
            rentTool.getCountTool()
        )));

        List<ToolResponse> toolResponseList = List.of(new ToolResponse(rentTool.getToolId(), 100L));
        ListToolResponse mockToolResponse = new ListToolResponse(toolResponseList, 1);
        List<UUID> ids = Collections.singletonList(rentTool.getToolId());
        Mockito.when(toolClient.fetchTools(ids)).thenReturn(mockToolResponse);

        doNothing().when(toolClient).updateQuantity(any(), eq("SUBTRACT"));
        when(rentRepository.save(any(Rent.class))).thenReturn(rent);

        Rent savedRent = rentService.save(rent, userId);

        Assertions.assertNotNull(savedRent);
        Assertions.assertEquals(userId, savedRent.getUserId());

        verify(toolClient, times(1)).updateQuantity(any(), eq("SUBTRACT"));
    }

    @Test
    public void testChangeStatusOnCancel_Success() {
        UUID rentId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Rent rent = new Rent();
        rent.setId(rentId);
        Status status = new Status();
        status.setName("AWAITING_CONFIRMATION");
        rent.setStatus(status);

        Mockito.when(rentRepository.findById(rentId)).thenReturn(Optional.of(rent));
        when(rentRepository.existsByIdAndUserId(rentId, userId)).thenReturn(true);
        Status statusCancel = new Status();
        statusCancel.setName("CANCELED");
        when(statusService.getByName("CANCELED")).thenReturn(statusCancel);

        Rent rentReturn = new Rent();
        rentReturn.setStatus(statusCancel);
        when(rentRepository.save(rent)).thenReturn(rentReturn);
        Rent result = rentService.changeStatusOnCancel(rentId, userId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("CANCELED", result.getStatus().getName());
    }

    @Test
    public void testChangeStatusOnCancel_Unsuccessful() {
        UUID rentId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Rent rent = new Rent();
        rent.setId(rentId);
        Status status = new Status();
        status.setName("COMPLETED");
        rent.setStatus(status);

        Mockito.when(rentRepository.findById(rentId)).thenReturn(Optional.of(rent));
        when(rentRepository.existsByIdAndUserId(rentId, userId)).thenReturn(true);
        Status statusCancel = new Status();
        statusCancel.setName("CANCELED");
        Mockito.lenient().when(statusService.getByName("CANCELED")).thenReturn(statusCancel);

        Rent rentReturn = new Rent();
        rentReturn.setStatus(statusCancel);
        Mockito.lenient().when(rentRepository.save(rent)).thenReturn(rentReturn);

        Assertions.assertThrows(
            BadRequestException.class,
            () -> rentService.changeStatusOnCancel(rentId, userId)
        );

    }

    @Test
    public void testChangeStatusOnReturn_Successful() {
        UUID rentId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Rent rent = new Rent();
        rent.setId(rentId);
        Status status = new Status();
        status.setName("RECEIVED");
        rent.setStatus(status);

        Mockito.when(rentRepository.findById(rentId)).thenReturn(Optional.of(rent));
        when(rentRepository.existsByIdAndUserId(rentId, userId)).thenReturn(true);
        Status statusReturn = new Status();
        statusReturn.setName("RETURN");
        Mockito.lenient().when(statusService.getByName("RETURN")).thenReturn(statusReturn);

        Rent rentReturn = new Rent();
        rentReturn.setStatus(statusReturn);
        when(rentRepository.save(rent)).thenReturn(rentReturn);
        Rent result = rentService.changeStatusOnReturn(rentId, userId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("RETURN", result.getStatus().getName());

    }

    @Test
    public void testChangeStatusOnReturn_Unsuccessful() {
        UUID rentId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Rent rent = new Rent();
        rent.setId(rentId);
        Status status = new Status();
        status.setName("RETURN");
        rent.setStatus(status);

        Mockito.when(rentRepository.findById(rentId)).thenReturn(Optional.of(rent));
        when(rentRepository.existsByIdAndUserId(rentId, userId)).thenReturn(true);
        Status statusReturn = new Status();
        statusReturn.setName("RETURN");
        Mockito.lenient().when(statusService.getByName("RETURN")).thenReturn(statusReturn);

        Rent rentReturn = new Rent();
        rentReturn.setStatus(statusReturn);
        Mockito.lenient().when(rentRepository.save(rent)).thenReturn(rentReturn);

        Assertions.assertThrows(
            BadRequestException.class,
            () -> rentService.changeStatusOnReturn(rentId, userId)
        );
    }
}
