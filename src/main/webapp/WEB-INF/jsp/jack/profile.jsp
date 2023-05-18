<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
			<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
				<%@ include file="../include/common_link.jsp" %>
					<!DOCTYPE html>
					<html>

					<head>
						<meta charset="UTF-8">
						<meta http-equiv="Pragma" content="no-cache" />
						<meta http-equiv="Expires" content="-1" />
						<meta http-equiv="CACHE-CONTROL" content="NO-CACHE" />
						<title>${webName}</title>
						<style>
							.container {
								position: relative;
								height: 100%;
							}

							.editIcon {
								position: absolute;
								bottom: 0;
								right: 10;
							}

							.imgLabel {
								position: relative;
								overflow: hidden;

							}



							body {
								display: grid;
								grid-template-rows: 1fr auto;
								background: #f0ebe2 !important;
							}

							.profile {
								margin-top: 50px;
							}
							
							div.list-group{
								position: absolute;
								z-index: 1;
							}
							

							

						</style>
					</head>

					<body>
						<jsp:include page="../include/header.jsp"></jsp:include>



						<div class="container rounded mt-5 mb-5">
							


							<div class="row profile">
								<div class="col-md-6 border-right">
									<div class="d-flex flex-column align-items-center text-center p-3 py-5">
										<div class="imgLabel">
											<label for="theImg">
												<img class="rounded-circle mt-5" width="200px" height="200px" style="object-fit: cover;"
													src="${root}/member/findMemberImg/${member.memberId}" id="memberImg"
													accept=".png, .jpg, .jpeg" />
												<input type="file" id="theImg" style="display: none">
												<i class="fa-regular fa-pen-to-square editIcon"></i>
											</label>
										</div>

										<span class="font-weight-bold">${member.memberName}</span>
										<span class="text-black-50">${member.memberEmail}</span>
										<span> </span>
									</div>
								</div>
								<div class="col-md-5 border-right">
									<div class="p-3 py-5">
										<div class="d-flex justify-content-between align-items-center mb-3">
											<h4 class="text-right">修改資料</h4>
											<button type="button" id="edit"
												class="btn btn-outline-secondary btn-floating btn-sm">Edit
												<i class="fas fa-magic"></i>
											</button>
											<button type="button" id="save"
												class="btn btn-outline-secondary btn-floating btn-sm d-none">Save
												<i class="fa-solid fa-check"></i>
											</button>
										</div>
										<div class="row mt-2">
											<div class="col-md-6">
												<label class="labels">姓名</label>
												<input type="text" class="form-control" value="${member.memberName}"
													id="memberName" readonly>
											</div>
										</div>
										<div class="row mt-3">
											<div class="col-md-12">
												<label class="labels">生日</label>
												<c:set var="BirthDate" value="${member.memberBirth}" />
												<fmt:formatDate value="${BirthDate}" pattern="yyyy-MM-dd"
													var="formattedDate" />
												<input type="text" class="form-control" value="${formattedDate}"
													id="memberBirth" disabled>
												<div id="memberBirth-error" class="invalid-feedback">

												</div>
											</div>
											<div class="col-md-12">
												<label class="labels">地址</label>
												<input type="text" class="form-control" value="${member.memberAddress}"
													id="memberAddress" readonly>
											</div>
											<div class="col-md-12">
												<label class="labels">電話</label>
												<input type="text" class="form-control" value="${member.memberTel}"
													id="memberTel" readonly>
											</div>
											<div class="col-md-12">
												<label class="labels">性別</label>
												<select class="form-select" id="memberGender"
													value="${member.memberGender}" aria-label="Default select example"
													disabled>
													<option value="男" selected>男</option>
													<option value="女">女</option>
													<option value="其他">其他</option>
												</select>
											</div>
										</div>

									</div>
								</div>


							</div>
						</div>





						<jsp:include page="../include/footer.jsp"></jsp:include>
						<script>
							const dbId = "${member.memberId}";
							const dbName = "${member.memberName}";
							document.querySelector("option[value=${member.memberGender}]").selected = 'selected'
						</script>
						<script type="text/javascript" src="${root}/js/jack/profile.js"></script>

					</body>

					</html>