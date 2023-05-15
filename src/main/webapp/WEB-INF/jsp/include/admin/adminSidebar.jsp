<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${adminWebName}</title>
</head>
<body>

	<!-- Sidebar -->
	<ul class="navbar-nav sidebar sidebar-dark accordion"
		id="accordionSidebar">

		<!-- Sidebar - Brand -->
		<a
			class="sidebar-brand d-flex align-items-center justify-content-center"
			href="${root}/">
			<div class="sidebar-brand-icon">
				<img src="${root}/img/favicon.png" class="iconImg">
			</div>
			<div class="sidebar-brand-text mx-3">Meeple Masters</div>
		</a>

		<!-- Divider -->
		<hr class="sidebar-divider my-0">

		<!-- Nav Item - Dashboard -->
		<li class="nav-item active"><a class="nav-link" href="${root}/admin">
				<i class="fas fa-fw fa-tachometer-alt"></i> <span>管理首頁</span>
		</a></li>

		<hr class="sidebar-divider">
		<div class="sidebar-heading">Admin</div>

		<!-- 會員系統 -->
		<li class="nav-item"><a class="nav-link collapsed" href="#"
			data-toggle="collapse" data-target="#member" aria-expanded="true"
			aria-controls="collapseTwo"> <i
				class="fa-solid fa-user sidebaricon"></i> <span>會員管理系統</span>
		</a>
			<div id="member" class="collapse" aria-labelledby="headingTwo"
				data-parent="#accordionSidebar">
				<div class="bg-white py-2 collapse-inner rounded">
					<h6 class="collapse-header">會員管理系統</h6>
					<a class="collapse-item" href="${root}/admin/memberManage">會員管理</a> <a class="collapse-item"
						href="/">會員活動</a>
				</div>
			</div></li>

		<!-- 好友系統 -->
		<li class="nav-item"><a class="nav-link collapsed" href="#"
			data-toggle="collapse" data-target="#friend" aria-expanded="true"
			aria-controls="collapseTwo"> <i
				class="fa-solid fa-user-group sidebaricon"></i> <span>好友管理系統</span>
		</a>
			<div id="friend" class="collapse" aria-labelledby="headingTwo"
				data-parent="#accordionSidebar">
				<div class="bg-white py-2 collapse-inner rounded">
					<h6 class="collapse-header">好友管理系統</h6>
					<a class="collapse-item" href="/">好友管理</a>
				</div>
			</div></li>

		<!-- 商城系統 -->
		<li class="nav-item"><a class="nav-link collapsed" href="#"
			data-toggle="collapse" data-target="#product" aria-expanded="true"
			aria-controls="collapseTwo"> <i
				class="fa-solid fa-cart-shopping sidebaricon"></i> <span>商品管理系統</span>
		</a>
			<div id="product" class="collapse" aria-labelledby="headingTwo"
				data-parent="#accordionSidebar">
				<div class="bg-white py-2 collapse-inner rounded">
					<h6 class="collapse-header">商品管理系統</h6>
					<a class="collapse-item" href="${root}/mall/adminProduct">商品管理</a> <a class="collapse-item"
						href="/">訂單管理</a>
				</div>
			</div></li>

		<!-- 遊戲系統 -->
		<li class="nav-item"><a class="nav-link collapsed" href="#"
			data-toggle="collapse" data-target="#game" aria-expanded="true"
			aria-controls="collapseTwo"> <i
				class="fa-solid fa-gamepad sidebaricon"></i> <span>遊戲管理系統</span>
		</a>
			<div id="game" class="collapse" aria-labelledby="headingTwo"
				data-parent="#accordionSidebar">
				<div class="bg-white py-2 collapse-inner rounded">
					<h6 class="collapse-header">遊戲管理系統</h6>
					<a class="collapse-item" href="/">遊戲管理</a>
				</div>
			</div></li>

		<!-- 卡片系統 -->
		<li class="nav-item"><a class="nav-link collapsed" href="#"
			data-toggle="collapse" data-target="#card" aria-expanded="true"
			aria-controls="collapseTwo"> <i
				class="fa-solid fa-wand-sparkles sidebaricon"></i> <span>卡片管理系統</span>
		</a>
			<div id="card" class="collapse" aria-labelledby="headingTwo"
				data-parent="#accordionSidebar">
				<div class="bg-white py-2 collapse-inner rounded">
					<h6 class="collapse-header">卡片管理系統</h6>
					<a class="collapse-item" href="/">卡片管理</a> <a class="collapse-item"
						href="/">交易管理</a>
				</div>
			</div></li>

		<!-- 論壇系統 -->
		<li class="nav-item"><a class="nav-link collapsed" href="#"
			data-toggle="collapse" data-target="#forum" aria-expanded="true"
			aria-controls="collapseTwo"> <i
				class="fa-regular fa-comments sidebaricon"></i> <span>論壇管理系統</span>
		</a>
			<div id="forum" class="collapse" aria-labelledby="headingTwo"
				data-parent="#accordionSidebar">
				<div class="bg-white py-2 collapse-inner rounded">
					<h6 class="collapse-header">論壇管理系統</h6>
					<a class="collapse-item" href="/">公告管理</a> <a class="collapse-item"
						href="/">貼文管理</a>
				</div>
			</div></li>

		<!-- 訂位系統 -->
		<li class="nav-item"><a class="nav-link collapsed" href="#"
			data-toggle="collapse" data-target="#booking" aria-expanded="true"
			aria-controls="collapseTwo"> <i
				class="fa-solid fa-store sidebaricon"></i> <span>預約管理系統</span>
		</a>
			<div id="booking" class="collapse" aria-labelledby="headingTwo"
				data-parent="#accordionSidebar">
				<div class="bg-white py-2 collapse-inner rounded">
					<h6 class="collapse-header">預約管理系統</h6>
					<a class="collapse-item" href="${root}/Booking/admin">預約管理</a>
				</div>
			</div></li>


	</ul>
	<!-- End of Sidebar -->

</body>
</html>