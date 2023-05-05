<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="../include/common_link.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<meta http-equiv="CACHE-CONTROL" content="NO-CACHE" />
<title>${webName}</title>
<style>
    .container{
		position: relative;
		padding-bottom: 105px;
		height: 100%;
	}
	.editIcon{
		position: absolute;
		bottom: 0;
		right: 10;
	}
	.imgLabel{
		position: relative;
		overflow: hidden;
		
	}
	/* .imgLabel img{
		position: absolute;
	} */

	body{
		display: grid;
		grid-template-rows: 1fr auto;
	}
	.profile{
		margin-top: 50px;
	}
	

</style>
</head>
<body>
	<jsp:include page="../include/header.jsp"></jsp:include>
	
		<div class="container rounded mt-5 mb-5"  >
			<div class="row profile">
				<div class="col-md-6 border-right" >
					<div
						class="d-flex flex-column align-items-center text-center p-3 py-5" >
						<div class="imgLabel">
						<label for="theImg">
							<img class="rounded-circle mt-5" width="150px" 
							src="${root}/member/findMemberImg/${member.memberId}" id="memberImg" accept=".png, .jpg, .jpeg"/>
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
						<div
							class="d-flex justify-content-between align-items-center mb-3">
							<h4 class="text-right">Profile Settings</h4>
							<button type="button" id="edit" class="btn btn-outline-secondary btn-floating btn-sm">Edit
								<i class="fas fa-magic"></i>
							  </button>
							  <button type="button" id="save" class="btn btn-outline-secondary btn-floating btn-sm d-none">Save
								<i class="fa-solid fa-check"></i>
							  </button>
						</div>
						<div class="row mt-2">
							<div class="col-md-6">
								<label class="labels">Name</label>
                                <input type="text"
									class="form-control" value="${member.memberName}" id="memberName" readonly>
							</div>
						</div>
						<div class="row mt-3">
							<div class="col-md-12">
								<label class="labels">Age</label>
                                <input type="text"
									class="form-control" value="${member.memberAge}" id="memberAge" readonly>
							</div>
							<div class="col-md-12">
								<label class="labels">Address</label>
                                <input type="text"
									class="form-control" value="${member.memberAddress}" id="memberAddress" readonly>
							</div>
							<div class="col-md-12">
								<label class="labels">phone</label>
                                <input type="text"
									class="form-control" value="${member.memberTel}" id="memberTel" readonly>
							</div>
							<div class="col-md-12">
								<label class="labels">Gender</label>
								<select
								class="form-select"
								id="memberGender"
								value = "${member.memberGender}"
								aria-label="Default select example"
								disabled
							  >	
								<option value="男" selected>男</option>
								<option value="女">女</option>
								<option value="其他">其他</option>
							  </select>
							</div>
						</div>
						<!-- <div class="row mt-3">
							<div class="col-md-6">
								<label class="labels">Country</label><input type="text"
									class="form-control" placeholder="country" value="">
							</div>
							<div class="col-md-6">
								<label class="labels">State/Region</label><input type="text"
									class="form-control" value="" placeholder="state">
							</div>
						</div> -->
						<!-- <div class="mt-5 text-center">
							<button class="btn btn-primary profile-button" type="button">
                                Save Profile
                            </button>
						</div> -->
					</div>
				</div>
				<!-- <div class="col-md-4">
            <div class="p-3 py-5">
                <div class="d-flex justify-content-between align-items-center experience"><span>Edit Experience</span><span class="border px-3 p-1 add-experience"><i class="fa fa-plus"></i>&nbsp;Experience</span></div><br>
                <div class="col-md-12"><label class="labels">Experience in Designing</label><input type="text" class="form-control" placeholder="experience" value=""></div> <br>
                <div class="col-md-12"><label class="labels">Additional Details</label><input type="text" class="form-control" placeholder="additional details" value=""></div>
            </div>
        </div> -->
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