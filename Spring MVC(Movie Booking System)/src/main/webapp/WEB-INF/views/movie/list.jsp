<%@ include file="/WEB-INF/views/common/header.jsp" %>
    <div class="row">
        <div class="col-md-12">
            <h2>Movies</h2>
            <div class="row">
                <c:forEach var="movie" items="${movies}">
                    <div class="col-md-4 mb-4">
                        <div class="card">
                            <img src="${movie.imageUrl}" class="card-img-top" alt="${movie.title}">
                            <div class="card-body">
                                <h5 class="card-title">${movie.title}</h5>
                                <p class="card-text">${movie.description}</p>
                                <p class="card-text"><small class="text-muted">${movie.language} | ${movie.durationMinutes} mins</small></p>
                                <a href="${pageContext.request.contextPath}/booking/select-show/${movie.movieId}" class="btn btn-primary">Book Tickets</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
