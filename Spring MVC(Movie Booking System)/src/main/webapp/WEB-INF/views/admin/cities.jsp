<%@ include file="/WEB-INF/views/common/header.jsp" %>
    <div class="row">
        <div class="col-md-12">
            <h2>Cities</h2>
            <a href="${pageContext.request.contextPath}/city/add" class="btn btn-primary mb-3">Add City</a>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>City ID</th>
                            <th>Name</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="city" items="${cities}">
                            <tr>
                                <td>${city.cityId}</td>
                                <td>${city.name}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/city/edit/${city.cityId}" class="btn btn-warning btn-sm">Edit</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
