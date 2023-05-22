<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>${webName}</title>
    <jsp:include page="../include/common_link.jsp" />
    <link rel="stylesheet" type="text/css" href="${root}/css/index.css" />
  </head>
  <body>
    <jsp:include page="../include/header.jsp" />
    <div class="bodyContainer">
      <div class="container px-3 my-5 clearfix">
        <div class="card shadow mb-4">
          <c:if test="${allOrder.size()==0}">
            <div class="card-header py-3">
              <h1 class="m-0 font-weight-bold">沒有訂單紀錄</h1>
            </div>
          </c:if>
          <c:if test="${allOrder.size()>0}">
            <div class="card-header py-3">
              <h1 class="m-0 font-weight-bold">訂單列表</h1>
            </div>
            <div class="card-body">
              <div class="table-responsive text-center">
                <table
                  class="table table-bordered"
                  width="100%"
                  cellspacing="0"
                >
                  <thead>
                    <tr>
                      <th>訂單日期</th>
                      <th>總金額</th>
                      <th>付款狀態</th>
                      <th>付款方式</th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach items="${allOrder}" var="order">
                      <tr>
                        <td>
                          <fmt:formatDate
                            value="${order.orderDate}"
                            pattern="yyyy-MM-dd"
                          />
                        </td>
                        <td>${order.totalPrice}</td>
                        <c:if test="${order.orderStatus=='未付款'}">
                          <td>
                            <a href="${root}/order"> ${order.orderStatus} </a>
                          </td>
                        </c:if>
                        <c:if test="${order.orderStatus=='已付款'}">
                          <td>${order.orderStatus}</td>
                        </c:if>
                        <td>${order.paymentMethod}</td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </div>
            </div>
          </c:if>
        </div>
      </div>
    </div>
    <script>
      (function init() {
        let oldOrderStatus = "${oldOrderExist}";
        if (oldOrderStatus == "舊訂單存在") {
          Swal.fire("您有訂單尚未付款");
        }
      })();
    </script>
    <jsp:include page="../include/footer.jsp" />
  </body>
</html>
