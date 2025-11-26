<%@ include file="/WEB-INF/views/common/header.jsp" %>
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h3 class="text-center">Profile</h3>
                </div>
                <div class="card-body">
                    <c:if test="${not empty param.success}">
                        <div class="alert alert-success">Password updated successfully!</div>
                    </c:if>
                    <c:if test="${not empty param.error}">
                        <div class="alert alert-danger">${param.error}</div>
                    </c:if>
                    <form action="${pageContext.request.contextPath}/user/update-password" method="post">
                        <div class="mb-3">
                            <label for="oldPassword" class="form-label">Old Password</label>
                            <input type="password" class="form-control" id="oldPassword" name="oldPassword" required>
                        </div>
                        <div class="mb-3">
                            <label for="newPassword" class="form-label">New Password</label>
                            <input type="password" class="form-control" id="newPassword" name="newPassword" required>
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Update Password</button>
                    </form>
                </div>
                <div class="card-footer text-center">
                    <a href="${pageContext.request.contextPath}/bank/add" class="btn btn-secondary">Add Bank Details</a>
                </div>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
