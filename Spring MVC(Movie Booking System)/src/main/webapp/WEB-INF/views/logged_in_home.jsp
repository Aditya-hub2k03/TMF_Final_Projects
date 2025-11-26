<%@ include file="/WEB-INF/views/common/header.jsp" %>
    <div class="row">
        <div class="col-md-12 text-center">
            <h1>Welcome, ${sessionScope.user.username}!</h1>
            <p>Here are your personalized recommendations.</p>
        </div>
    </div>
    <div class="row mt-4">
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">My Bookings</h5>
                    <p class="card-text">View your booked tickets.</p>
                    <a href="${pageContext.request.contextPath}/booking/my-bookings" class="btn btn-primary">View</a>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Profile</h5>
                    <p class="card-text">Update your profile and bank details.</p>
                    <a href="${pageContext.request.contextPath}/user/profile" class="btn btn-primary">View</a>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Browse Movies</h5>
                    <p class="card-text">Explore our collection of movies.</p>
                    <a href="${pageContext.request.contextPath}/movie/list" class="btn btn-primary">Browse</a>
                </div>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
