<%@ include file="/WEB-INF/views/common/header.jsp" %>
    <div class="row">
        <div class="col-md-12">
            <h2>Admin Dashboard</h2>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>User ID</th>
                            <th>Username</th>
                            <th>Email</th>
                            <th>Role</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="user" items="${users}">
                            <tr>
                                <td>${user.userId}</td>
                                <td>${user.username}</td>
                                <td>${user.email}</td>
                                <td>${user.role}</td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/admin/update-role/${user.userId}" method="post" class="d-inline">
                                        <select name="role" class="form-select form-select-sm">
                                            <option value="NORMAL_USER" ${user.role == 'NORMAL_USER' ? 'selected' : ''}>Normal User</option>
                                            <option value="THEATRE_ADMIN" ${user.role == 'THEATRE_ADMIN' ? 'selected' : ''}>Theatre Admin</option>
                                        </select>
                                        <button type="submit" class="btn btn-warning btn-sm">Update</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
