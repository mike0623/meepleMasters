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
    

</style>
</head>
<body>
	<jsp:include page="../include/header.jsp"></jsp:include>
	
		<div class="container rounded mt-5 mb-5"  >
			<div class="row">
				<div class="col-md-6 border-right" >
					<div
						class="d-flex flex-column align-items-center text-center p-3 py-5" >
						<img class="rounded-circle mt-5" width="150px"
							src="${root}/member/findMemberImg/${member.memberId}"/>
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
							<button type="button" class="btn btn-outline-secondary btn-floating btn-sm">Edit
								<i class="fas fa-magic"></i>
							  </button>
						</div>
						<div class="row mt-2">
							<div class="col-md-6">
								<label class="labels">Name</label>
                                <input type="text"
									class="form-control" value="${member.memberName}" id="name" readonly>
							</div>
						</div>
						<div class="row mt-3">
							<div class="col-md-12">
								<label class="labels">Address</label>
                                <input type="text"
									class="form-control" value="${member.memberAddress}" id="address" readonly>
							</div>
							<div class="col-md-12">
								<label class="labels">phone</label>
                                <input type="text"
									class="form-control" value="${member.memberTel}" id="tel" readonly>
							</div>
							<div class="col-md-12">
								<label class="labels">Gender</label>
                                <input type="text"
									class="form-control" value="${member.memberGender}" id="gender" readonly>
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
		if (performance.navigation.type == 2) {
			console.log("Doing reload");
			location.reload(true);
			console.log("Done with reload");
		}
		console.log("Script loaded.")
	</script>

</body>
</html>