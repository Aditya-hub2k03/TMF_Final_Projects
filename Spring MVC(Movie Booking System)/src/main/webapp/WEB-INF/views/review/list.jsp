<%@ include file="/WEB-INF/views/common/header.jsp" %>
    <div class="row">
        <div class="col-md-8 offset-md-2">
            <h2>Reviews for Movie</h2>
            <c:if test="${not empty param.movieId}">
                <a href="${pageContext.request.contextPath}/review/movie/${param.movieId}" class="btn btn-primary mb-3">Refresh</a>
                <c:if test="${not empty sessionScope.user}">
                    <form action="${pageContext.request.contextPath}/review/add" method="post" class="mb-4">
                        <input type="hidden" name="movieId" value="${param.movieId}">
                        <div class="mb-3">
                            <label for="rating" class="form-label">Rating (1-5)</label>
                            <select class="form-select" id="rating" name="rating" required>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="comment" class="form-label">Comment</label>
                            <textarea class="form-control" id="comment" name="comment" rows="3" required></textarea>
                        </div>
                        <button type="submit" class="btn btn-primary">Submit Review</button>
                    </form>
                </c:if>
            </c:if>
            <div class="reviews">
                <c:forEach var="review" items="${reviews}">
                    <div class="card mb-3">
                        <div class="card-body">
                            <div class="d-flex justify-content-between">
                                <h5 class="card-title">${review.username}</h5>
                                <div class="rating">${review.rating}/5</div>
                            </div>
                            <p class="card-text">${review.comment}</p>
                            <p class="card-text"><small class="text-muted">${review.createdAt}</small></p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
