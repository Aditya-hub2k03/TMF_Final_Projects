<%@ include file="/WEB-INF/views/common/header.jsp" %>
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h3 class="text-center">Edit Theatre</h3>
                </div>
                <div class="card-body">
                    <c:if test="${not empty param.error}">
                        <div class="alert alert-danger">${param.error}</div>
                    </c:if>
                    <form action="${pageContext.request.contextPath}/theatre/edit/${theatre.theatreId}" method="post">
                        <div class="mb-3">
                            <label for="name" class="form-label">Name</label>
                            <input type="text" class="form-control" id="name" name="name" value="${theatre.name}" required>
                        </div>
                        <div class="mb-3">
                            <label for="address" class="form-label">Address</label>
                            <input type="text" class="form-control" id="address" name="address" value="${theatre.address}" required>
                        </div>
                        <div class="mb-3">
                            <label for="city" class="form-label">City</label>
                            <input type="text" class="form-control" id="city" name="city" value="${theatre.city}" required>
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Update Theatre</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
