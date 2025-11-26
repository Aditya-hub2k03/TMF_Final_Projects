<%@ include file="/WEB-INF/views/common/header.jsp" %>
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h3 class="text-center">Add Movie</h3>
                </div>
                <div class="card-body">
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                    <form action="${pageContext.request.contextPath}/movie/add" method="post">
                        <div class="mb-3">
                            <label for="title" class="form-label">Title</label>
                            <input type="text" class="form-control" id="title" name="title" required>
                        </div>
                        <div class="mb-3">
                            <label for="description" class="form-label">Description</label>
                            <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="durationMinutes" class="form-label">Duration (Minutes)</label>
                            <input type="number" class="form-control" id="durationMinutes" name="durationMinutes" required>
                        </div>
                        <div class="mb-3">
                            <label for="releaseDate" class="form-label">Release Date</label>
                            <input type="date" class="form-control" id="releaseDate" name="releaseDate" required>
                        </div>
                        <div class="mb-3">
                            <label for="imageUrl" class="form-label">Image URL</label>
                            <input type="text" class="form-control" id="imageUrl" name="imageUrl" required>
                        </div>
                        <div class="mb-3">
                            <label for="language" class="form-label">Language</label>
                            <input type="text" class="form-control" id="language" name="language" required>
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Add Movie</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
