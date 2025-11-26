<%@ include file="/WEB-INF/views/common/header.jsp" %>
    <div class="row">
        <div class="col-md-12">
            <h2>My Bookings</h2>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Booking ID</th>
                            <th>Show ID</th>
                            <th>Total Amount</th>
                            <th>Booking Time</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="booking" items="${bookings}">
                            <tr>
                                <td>${booking.bookingId}</td>
                                <td>${booking.showId}</td>
                                <td>$${booking.totalAmount}</td>
                                <td>${booking.bookingTime}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/booking/confirmation/${booking.bookingId}" class="btn btn-info btn-sm">View</a>
                                    <a href="${pageContext.request.contextPath}/booking/download-ticket/${booking.bookingId}" class="btn btn-success btn-sm">Download</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
