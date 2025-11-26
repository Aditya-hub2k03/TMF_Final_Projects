<%@ include file="/WEB-INF/views/common/header.jsp" %>
    <div class="row">
        <div class="col-md-12">
            <h2>Seat Layout</h2>
            <div class="seat-layout">
                <div class="screen">SCREEN</div>
                <div class="seats">
                    <c:forEach var="seat" items="${seats}">
                        <div class="seat ${seat.booked ? 'booked' : 'available'}" data-seat-id="${seat.seatId}">
                            ${seat.seatNumber}
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
    <style>
        .seat-layout {
            margin: 20px auto;
            width: 80%;
            text-align: center;
        }
        .screen {
            background: #ddd;
            height: 20px;
            width: 100%;
            margin: 20px 0;
            line-height: 20px;
            font-weight: bold;
        }
        .seats {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 10px;
        }
        .seat {
            width: 40px;
            height: 40px;
            background: #4CAF50;
            color: white;
            display: flex;
            justify-content: center;
            align-items: center;
            border-radius: 5px;
            cursor: pointer;
        }
        .seat.booked {
            background: #f44336;
        }
        .seat.available:hover {
            background: #45a049;
        }
    </style>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
