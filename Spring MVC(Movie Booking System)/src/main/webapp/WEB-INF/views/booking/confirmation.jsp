<%@ include file="/WEB-INF/views/common/header.jsp" %>
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h3 class="text-center">Booking Confirmation</h3>
                </div>
                <div class="card-body text-center">
                    <h4>Booking ID: ${booking.bookingId}</h4>
                    <p>Show ID: ${booking.showId}</p>
                    <p>Total Amount: $${booking.totalAmount}</p>
                    <p>Booking Time: ${booking.bookingTime}</p>
                    <a href="${pageContext.request.contextPath}/booking/download-ticket/${booking.bookingId}" class="btn btn-success mt-3">Download Ticket</a>
                </div>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
