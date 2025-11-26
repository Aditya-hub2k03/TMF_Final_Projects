<%@ include file="/WEB-INF/views/common/header.jsp" %>
    <div class="row">
        <div class="col-md-12">
            <h2>Manage Screens</h2>
            <a href="${pageContext.request.contextPath}/screen/add/${param.theatreId}" class="btn btn-primary mb-3">Add Screen</a>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Total Seats</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="screen" items="${screens}">
                            <tr>
                                <td>${screen.name}</td>
                                <td>${screen.totalSeats}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/show/manage/${screen.screenId}" class="btn btn-info btn-sm">Manage Shows</a>
                                    <a href="${pageContext.request.contextPath}/seat/layout/${screen.screenId}" class="btn btn-success btn-sm">View Seats</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
