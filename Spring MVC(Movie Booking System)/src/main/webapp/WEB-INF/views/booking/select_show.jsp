<%@ include file="/WEB-INF/views/common/header.jsp" %>
    <div class="row">
        <div class="col-md-12">
            <h2>Select Show for Movie</h2>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Theatre</th>
                            <th>Screen</th>
                            <th>Start Time</th>
                            <th>End Time</th>
                            <th>Price</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="show" items="${shows}">
                            <tr>
                                <td>${show.theatreName}</td>
                                <td>${show.screenName}</td>
                                <td>${show.startTime}</td>
                                <td>${show.endTime}</td>
                                <td>$${show.price}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/booking/select-seats/${show.showId}" class="btn btn-primary">Select Seats</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
