package com.yogesh.dao;

import com.yogesh.dto.request.SeatRequest;
import com.yogesh.dto.response.SeatResponse;
import java.util.List;

public interface SeatDAO {
    SeatResponse addSeat(SeatRequest request);
    SeatResponse getSeatById(int seatId);
    List<SeatResponse> getSeatsByScreen(int screenId);
    boolean updateSeatStatus(int seatId, boolean isBooked);
    boolean deleteSeat(int seatId);
}
