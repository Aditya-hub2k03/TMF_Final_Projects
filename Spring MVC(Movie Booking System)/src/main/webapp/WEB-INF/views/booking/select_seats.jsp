<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="container mt-4">
    <div class="row">
        <div class="col-md-12">
            <h2 class="mb-4">Select Seats for Show</h2>

            <form action="${pageContext.request.contextPath}/booking/book" method="post">
                <input type="hidden" name="showId" value="${show.showId}">
                <input type="hidden" name="userId" value="${userId}">

                <div class="seat-layout">
                    <div class="screen">SCREEN</div>
                    <div class="seats">
                        <c:forEach var="seat" items="${seats}">
                            <div class="seat ${seat.booked ? 'booked' : 'available'}" data-seat-id="${seat.seatId}">
                                <input type="checkbox" name="seatIds" value="${seat.seatId}" ${seat.booked ? 'disabled' : ''}>
                                ${seat.seatNumber}
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <button type="submit" class="btn btn-primary mt-3">Book Selected Seats</button>
            </form>
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
        position: relative;
    }
    .seat.booked {
        background: #f44336;
    }
    .seat input[type="checkbox"] {
        position: absolute;
        opacity: 0;
        width: 100%;
        height: 100%;
        cursor: pointer;
    }
    .seat.available:hover {
        background: #45a049;
    }
</style>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
