<%@ include file="/WEB-INF/views/common/header.jsp" %>
    <div class="row">
        <div class="col-md-12 text-center">
            <h1>Welcome to Movie Ticket Booking</h1>
            <p>Book your favorite movies now!</p>
        </div>
    </div>
    <div class="row mt-4">
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Browse Movies</h5>
                    <p class="card-text">Explore our collection of movies.</p>
                    <a href="${pageContext.request.contextPath}/movie/list" class="btn btn-primary">Browse</a>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Find Theatres</h5>
                    <p class="card-text">Find theatres near you.</p>
                    <a href="#" class="btn btn-primary">Find</a>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Book Tickets</h5>
                    <p class="card-text">Book tickets for your favorite shows.</p>
                    <a href="#" class="btn btn-primary">Book</a>
                </div>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
