<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
			<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
				<%@ include file="../include/common_link.jsp" %>
					<!DOCTYPE html>
					<html>

					<head>
						<meta charset="UTF-8">
						<title>${webName}</title>
						<style type="text/css">
							/* Table css */

							.container {
								position: relative;
								height: 100%;
							}

							#productTable {
								margin-top: 100px !important;
							}

							.card-header {
								background-color: #f0d8cf;
							}

							.card-body {
								background-color: #f2f2f2;
							}

							.text-primary {
								color: #dc7e6a !important;
							}

							.table-bordered>:not(caption)>*>* {
								border-width: 0 0px;
							}

							.sorting {
								width: 200px !important;
							}

							.table td,
							.table th {
								text-align: center;
								vertical-align: middle;
							}

							a {
								color: #dc7e6a;
							}

							.button-19 {
								appearance: button;
								background-color: #da9255;
								border: solid transparent;
								border-radius: 16px;
								border-width: 0 0 4px;
								box-sizing: border-box;
								color: #FFFFFF;
								cursor: pointer;
								display: inline-block;
								font-family: din-round, sans-serif;
								font-size: 15px;
								font-weight: 700;
								letter-spacing: .8px;
								line-height: 20px;
								margin: 0;
								outline: none;
								overflow: visible;
								padding: 13px 16px;
								text-align: center;
								text-transform: uppercase;
								touch-action: manipulation;
								transform: translateZ(0);
								transition: filter .2s;
								user-select: none;
								-webkit-user-select: none;
								vertical-align: middle;
								white-space: nowrap;
								width: 100px;
							}
							.button-19:hover{
								background-color: #dab055;
							}

							/* Table css */
						</style>

					</head>

					<body>
						<jsp:include page="../include/header.jsp"></jsp:include>

						<!-- 表格參考 -->
						<!-- Begin Page Content -->
						<div class="container rounded mt-5 mb-5" id="productTable">

							<!-- Page Heading -->
							<!-- <h1 class="h3 mb-2 text-gray-800">表格</h1>
							<p class="mb-4">DataTables is a third party plugin that is used to generate the demo table
								below.
								For more information about DataTables, please visit the <a target="_blank"
									href="https://datatables.net">official DataTables documentation</a>.</p> -->

							<!-- DataTales Example -->
							<div class="card mb-4">
								<div class="card-header py-3">
									<h6 class="m-0 font-weight-bold text-primary productTitle">遊戲列表</h6>
								</div>
								<div class="card-body">
									<div class="table-responsive">
										<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
											<thead>
												<tr>
													<th>Game</th>
													<th>Name</th>
													<th></th>
												</tr>
											</thead>
											<tfoot>
												<tr>
													<th></th>
													<th></th>
													<th></th>
												</tr>
											</tfoot>
											<tbody>
												<c:forEach items="${memberProduct}" var="product">
													<tr>
														<td><img src="${root}/mall/getPhoto?pId=${product.productId}"
																width="100px" height="100px"></td>
														<td><a href="#">${product.productName}</a></td>
														<td><form action="${root}/game/createGameTable/${product.productName}/${member.memberEmail}"><button class="button-19">開始遊玩</button></form></td>
													</tr>
												</c:forEach>

											</tbody>
										</table>
									</div>
								</div>
							</div>

						</div>
						<!-- /.container-fluid -->

						
						<jsp:include page="../include/footer.jsp"></jsp:include>
					</body>

					</html>