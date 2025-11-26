package com.yogesh.service;

import com.yogesh.dao.SeatDAO;
import com.yogesh.dto.request.SeatRequest;
import com.yogesh.dto.response.SeatResponse;
import java.util.List;
import org.springframework.stereotype.Service;
@Service
public class SeatServiceImpl implements SeatService {

    private final SeatDAO seatDAO;

    public SeatServiceImpl(SeatDAO seatDAO) {
        this.seatDAO = seatDAO;
    }

    @Override
    public SeatResponse addSeat(SeatRequest request) {
        // Validate request
        if (request.getScreenId() <= 0 || request.getSeatNumber() == null) {
            throw new IllegalArgumentException("Screen ID and seat number are required.");
        }
        return seatDAO.addSeat(request);
    }

    @Override
    public SeatResponse getSeatById(int seatId) {
        if (seatId <= 0) {
            throw new IllegalArgumentException("Invalid seat ID.");
        }
        return seatDAO.getSeatById(seatId);
    }

    @Override
    public List<SeatResponse> getSeatsByScreen(int screenId) {
        return seatDAO.getSeatsByScreen(screenId);
    }


    @Override
    public boolean updateSeatStatus(int seatId, boolean isBooked) {
        if (seatId <= 0) {
            throw new IllegalArgumentException("Invalid seat ID.");
        }
        return seatDAO.updateSeatStatus(seatId, isBooked);
    }

    @Override
    public boolean deleteSeat(int seatId) {
        if (seatId <= 0) {
            throw new IllegalArgumentException("Invalid seat ID.");
        }
        return seatDAO.deleteSeat(seatId);
    }
}

