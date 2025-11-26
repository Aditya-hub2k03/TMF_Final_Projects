<%@ include file="/WEB-INF/views/common/header.jsp" %>
    <div class="row">
        <div class="col-md-12">
            <h2>Manage Shows</h2>
            <a href="${pageContext.request.contextPath}/show/add/${param.theatreId}" class="btn btn-primary mb-3">Add Show</a>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Movie</th>
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
                                <td>${show.movieId}</td>
                                <td>${show.screenId}</td>
                                <td>${show.startTime}</td>
                                <td>${show.endTime}</td>
                                <td>${show.price}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/show/edit/${show.showId}" class="btn btn-warning btn-sm">Edit</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
