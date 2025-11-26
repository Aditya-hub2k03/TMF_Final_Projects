<%@ include file="/WEB-INF/views/common/header.jsp" %>
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h3 class="text-center">Add Show</h3>
                </div>
                <div class="card-body">
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                    <form action="${pageContext.request.contextPath}/show/add/${param.theatreId}" method="post">
                        <div class="mb-3">
                            <label for="movieId" class="form-label">Movie ID</label>
                            <input type="number" class="form-control" id="movieId" name="movieId" required>
                        </div>
                        <div class="mb-3">
                            <label for="screenId" class="form-label">Screen ID</label>
                            <input type="number" class="form-control" id="screenId" name="screenId" required>
                        </div>
                        <div class="mb-3">
                            <label for="startTime" class="form-label">Start Time</label>
                            <input type="datetime-local" class="form-control" id="startTime" name="startTime" required>
                        </div>
                        <div class="mb-3">
                            <label for="endTime" class="form-label">End Time</label>
                            <input type="datetime-local" class="form-control" id="endTime" name="endTime" required>
                        </div>
                        <div class="mb-3">
                            <label for="price" class="form-label">Price</label>
                            <input type="number" class="form-control" id="price" name="price" required>
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Add Show</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
