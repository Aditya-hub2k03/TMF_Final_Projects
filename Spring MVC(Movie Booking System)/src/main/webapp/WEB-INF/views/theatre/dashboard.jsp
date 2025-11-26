<%@ include file="/WEB-INF/views/common/header.jsp" %>
    <div class="row">
        <div class="col-md-12">
            <h2>Theatre Dashboard</h2>
            <a href="${pageContext.request.contextPath}/theatre/add" class="btn btn-primary mb-3">Add Theatre</a>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Address</th>
                            <th>City</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="theatre" items="${theatres}">
                            <tr>
                                <td>${theatre.name}</td>
                                <td>${theatre.address}</td>
                                <td>${theatre.city}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/screen/manage/${theatre.theatreId}" class="btn btn-info btn-sm">Manage Screens</a>
                                    <a href="${pageContext.request.contextPath}/show/manage/${theatre.theatreId}" class="btn btn-success btn-sm">Manage Shows</a>
                                    <a href="${pageContext.request.contextPath}/theatre/edit/${theatre.theatreId}" class="btn btn-warning btn-sm">Edit</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
