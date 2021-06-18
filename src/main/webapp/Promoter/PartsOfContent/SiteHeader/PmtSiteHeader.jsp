<%-- Promoter 사이트 헤더 jsp 파일 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<nav class="navbar navbar-dark navbar-expand p-0 bg-primary">
	<div class="container">
		<ul class="navbar-nav d-none d-md-flex mr-auto">
			<li class="nav-item"><a class="nav-link" href="#">Home</a></li>
			<li class="nav-item"><a class="nav-link" href="#">Delivery</a></li>
			<li class="nav-item"><a class="nav-link" href="#">Payment</a></li>
		</ul>
		<ul class="navbar-nav">
			<li><a href="#" class="nav-link"> <i class="fa fa-comment"></i>
					Live chat
			</a></li>
			<li class="nav-item dropdown"><a href="#"
				class="nav-link dropdown-toggle" data-toggle="dropdown"
				aria-expanded="false"> English </a>
				<ul class="dropdown-menu dropdown-menu-right"
					style="max-width: 100px;">
					<li><a class="dropdown-item" href="#">Arabic</a></li>
					<li><a class="dropdown-item" href="#">Russian </a></li>
				</ul></li>
		</ul>
		<!-- list-inline //  -->
	</div>
	<!-- navbar-collapse .// -->
	<!-- container //  -->
</nav>
<!-- header-top-light.// -->

<header class="section-header">
	<section class="header-main border-bottom">
		<div class="container">
			<div class="row align-items-center">
				<div class="col-lg-3 col-sm-4 col-md-4 col-5">
					<a href="/Promoter/Index.mwav" class="brand-wrap"> <img
						class="logo" src="/Promoter/zImage/Main_logo_white.jpg">
					</a>
					<!-- brand-wrap.// -->
				</div>
				<div class="col-lg-4 col-xl-5 col-sm-8 col-md-4 d-none d-md-block">
					<form action="#" class="search-wrap">
						<div class="input-group w-100">
							<input type="text" class="form-control" style="width: 55%;"
								placeholder="Search">
							<div class="input-group-append">
								<button class="btn btn-primary" type="submit">
									<i class="fa fa-search"></i>
								</button>
							</div>
						</div>
					</form>
					<!-- search-wrap .end// -->
				</div>
				<!-- col.// -->
				<div class="col-lg-5 col-xl-4 col-sm-8 col-md-4 col-7">
					<div class="d-flex justify-content-end">
						<!-- 장바구니<a href="#" class="widget-header mr-3">
							<div class="icon">
								<i class="icon-sm rounded-circle border fa fa-shopping-cart"></i>
								<span class="notify">0</span>
							</div>
						</a> -->
						<a href="/Promoter/Goods/PmtWishList.mwav"
							class="widget-header mr-3">
							<div class="icon">
								<i class="icon-sm rounded-circle border fa fa-heart"></i> <span
									class="notify">3</span>
							</div>
						</a>
						<!-- 로그인 전 -->
						<c:if test="${sessionScope.promoter eq null }">
						<div class="widget-header icontext">
							<a href="#" class="icon icon-sm rounded-circle border"><i
								class="fa fa-user"></i></a>
							<div class="text">
								<span class="text-muted">Welcome!</span>
								<div>
									<a href="/Promoter/Facilitator/PmtLogin.mwav">Sign in</a> | <a
										href="/Promoter/Facilitator/PmtInsertForm.mwav"> Register</a>
								</div>
							</div>
						</div>
						</c:if>
						<!-- 로그인 후 -->
						<c:if test="${sessionScope.promoter ne null }">
						<div class="widget-header dropdown">
							<a href="#" data-toggle="dropdown" class="dropdown-toggle"
								data-offset="20,10">
								<div class="icon icon-sm rounded-circle border ">
									<i class="fa fa-user"></i>
								</div> <span class="sr-only">Profile actions</span>
							</a>
							<div class="dropdown-menu dropdown-menu-right">
								<a class="dropdown-item" href="/Promoter/Facilitator/PmtView.mwav">Profile setting</a> <a
									class="dropdown-item" href="#">My orders</a>
								<hr class="dropdown-divider">
								<a class="dropdown-item" href="javascript:kakaoLogout()">Log out</a>
							</div>
							<!-- dropdown-menu .// -->
						</div>
						</c:if>
							<!-- widget-header .// -->
						</div>
						<!-- widgets-wrap.// -->
					</div>
					<!-- col.// -->
				</div>
				<!-- row.// -->
			</div>
		</div>
		<!-- container.// -->
	</section>
	<!-- header-main .// -->


	<nav
		class="navbar navbar-light navbar-expand-md navbar-main border-bottom">

		<div class="container">
			<form class="d-md-none my-2">
				<div class="input-group">
					<input type="search" name="search" class="form-control"
						placeholder="Search">
					<div class="input-group-append">
						<button type="submit" class="btn btn-secondary">
							<i class="fas fa-search"></i>
						</button>
					</div>
				</div>
			</form>

			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#dropdown6" aria-expanded="true">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="navbar-collapse collapse justify-content-center"
				id="dropdown6" style="">
				<ul class="navbar-nav text-center">
					<!-- mr-auto로 class 지정시 좌측 정렬 / justify-content-center 중앙정렬 -->
					<!-- ml-5 GNB 사이 간격 -->
					<li class="nav-item ml-5"><a class="nav-link"
						href="page-deal.html">소개</a></li>
					<li class="nav-item ml-5"><a class="nav-link"
						href="page-blog.html">인플루언서</a></li>
					<li class="nav-item ml-5"><a class="nav-link"
						href="/Promoter/Goods/PmtGoodsList.mwav">상품조회</a></li>
					<li class="nav-item ml-5 dropdown"><a
						class="nav-link dropdown-toggle" data-toggle="dropdown" href=""
						aria-expanded="false">고객센터</a>
						<div class="dropdown-menu">
							<a class="dropdown-item" href="#">자주묻는질문</a> <a
								class="dropdown-item"
								href="/Promoter/CommonApps/BoardNotice/PmtNoticeList.mwav">공지사항</a>
							<div class="dropdown-divider"></div>
							<a class="dropdown-item" href="#">1:1문의</a>

						</div></li>
				</ul>
			</div>
			<!-- collapse .// -->
		</div>
		<!-- 
				<ul class="navbar-nav">
					<li class="nav-item"><a
						href="/Promoter/Goods/PmtGoodsList.mwav" class="nav-link">상품조회</a></li>
					<li class="nav-item"><a
						href="/Promoter/Goods/PmtGoodsDetail.mwav"
						class="btn btn-primary ml-md-4"><i class="fa fa-plus"></i>
							Post item </a></li>
				</ul>
 -->
	</nav>
</header>
<!-- header-main .// -->

<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<script type="text/javascript">
Kakao.init('b66e3d6516bdc422b77b51024332a218');

function kakaoLogout() {
	$.ajax({
		type: 'GET',
		url: '/promoter/kakaoLogout.mwav',
		success: function (data) {
			console.log(data);
			// if (data == 'OK') {
				location.reload();
			// }
		},
		fail: function(error) {
			alert(JSON.stringify(error));
		}
	});
	
	if (!Kakao.Auth.getAccessToken()) {
      alert('Not logged in.');
      return;
    }
    Kakao.Auth.logout(function() {
    });
}
</script>

<!-- recapture -->
<script src='https://www.google.com/recaptcha/api.js?onload=CaptchaCallback&render=explicit' async defer></script>

<!-- 리캡챠를 여러개 사용할때 사용한다고 함 -->
<script type="text/javascript">
	var CaptchaCallback = function() {
		$('.g-recaptcha').each(function(index, el) {
			var widgetId = grecaptcha.render(el, {
				'sitekey' : '6LcdRxoUAAAAAA4OI0FIN2bv2W0ersTRjqHJdLG-'
			});
			jQuery(this).attr('data-widget-id', widgetId);
		});
	};
</script>

<script src="/CommonLibrary/Javascript/Common.js"></script>
<script src="/CommonLibrary/Javascript/validate.js"></script>