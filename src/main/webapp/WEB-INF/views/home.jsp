<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Home</title>
<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport'>
<!-- Bootstrap 3.3.4 -->
<link
	href="${pageContext.request.contextPath }/resources/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<!-- Font Awesome Icons -->
<link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<!-- Ionicons -->
<link
	href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css"
	rel="stylesheet" type="text/css" />
<!-- Theme style -->
<link
	href="${pageContext.request.contextPath }/resources/dist/css/AdminLTE.min.css"
	rel="stylesheet" type="text/css" />
<!-- AdminLTE Skins. Choose a skin from the css/skins 
         folder instead of downloading all of them to reduce the load. -->
<link
	href="${pageContext.request.contextPath }/resources/dist/css/skins/_all-skins.min.css"
	rel="stylesheet" type="text/css" />
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<!-- jQuery 2.1.4 -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>

<!-- Bootstrap 3.3.2 JS -->
<script
	src="${pageContext.request.contextPath }/resources/bootstrap/js/bootstrap.min.js"
	type="text/javascript"></script>
<!-- FastClick -->
<script
	src='${pageContext.request.contextPath }/resources/plugins/fastclick/fastclick.min.js'></script>
<!-- AdminLTE App -->
<script
	src="${pageContext.request.contextPath }/resources/dist/js/app.min.js"
	type="text/javascript"></script>
<!-- AdminLTE for demo purposes -->
<script
	src="${pageContext.request.contextPath }/resources/dist/js/demo.js"
	type="text/javascript"></script>
	<style>
		.btn-multi-div{
			padding:0px !important;
		}
		.hiddenicon{
			display: none;
		}
	</style>
	<script>
		var id_check=-1;
		var no_pw = -1;
		var no_pw2 =-1;
		$(function(){
			
			$(document).on("click","#join",function(){
				empty();
				
				if(id_check<0){
					alert("아이디 중복을 체크해주세요");
					return;
				}
				
				if(no_pw<0){
					alert("비밀번호 형식을 확인해주세요");
					return;
				}
				
				var id = $("#userid").val();
				var name =$("#name").val();
				var pw = $("#pw").val();
				var email = $("#email").val();
				var phone = $("#phone").val();
				var sendData = {id:id,name:name,pw:pw,email:email,phone:phone};
				
				$.ajax({
					url:"/photo/join",
					type:"post",
					dataType:"text",
					data:JSON.stringify(sendData),
					headers:{"Content-Type":"application/json"},
					success:function(result){
						console.log(result);
						
						if(result=="success"){
							$("#close").trigger("click");
							alert("회원가입 되었습니다.");
							location.href="/photo/";
						}
					}
				})
				
			})
			
			$("#userid").keyup(function(){
				id_check=-1;
				$("#idCheck").text("중복 체크");
				$("#idCheck").removeClass("btn-danger");
				$("#idCheck").removeClass("btn-success");
				$("#idCheck").addClass("btn-warning");
			})
			
			/*아이디 중복확인 */
			$("#idCheck").click(function(){
				
				var id = $("#userid").val();
				
				var reg =/^[A-Za-z\d]{6,15}$/;
				
				if(id.length<6){
					alert("아이디는 최소 6자리 이상입니다.");
					id_check=-1;
					return;
				}
				if(!reg.test(id)){
					alert("아이디는 6~15자\n 영어,숫자만 사용가능합니다.");
					id_check=-1;
					return;
				}
				$.ajax({
					url:"/photo/idCheck",
					type:"get",
					dataType:"text",
					data:{"id":id},
					success:function(result){
						console.log(result);
						if(result.length==0){
							alert("사용 가능한 아이디 입니다.");
							$("#idCheck").text("사용 가능");
							$("#idCheck").removeClass("btn-warning");
							$("#idCheck").removeClass("btn-danger");
							$("#idCheck").addClass("btn-success");
							id_check=1;
						}else{
							alert("이미 사용중인 아이디입니다.(사용불가)");
							$("#idCheck").text("사용 불가");
							$("#idCheck").removeClass("btn-warning");
							$("#idCheck").removeClass("btn-success");
							$("#idCheck").addClass("btn-danger");
							id_check=-1;
						}
					}
				})
			})
			
			/*닫기버튼 눌렀을때 초기화  */
			$("#close").click(function(){
				$("#f").find("input").each(function(i,obj){
					$(this).val("");
				})
				
				$("#okicon1").css("display","none");
				$("#noicon1").css("display","none");
				
				$("#okicon2").css("display","none");
				$("#noicon2").css("display","none");
				
			})
			
			/*비밀번호  */
			$("#pw").keyup(function(){
				
				if($(this).length==0){
					$("#okicon1").css("display","none");
					$("#noicon1").css("display","none");
					return;
				}
				var reg =/^(?=.*[a-z])(?=.*\d)(?=.*[~$@$!#%^&*])[A-Za-z\d~$@$!#%^&*]{8,20}$/;
				var password =$("#pw").val();
				if(reg.test(password)){
					$("#okicon1").css("display","inline");
					$("#noicon1").css("display","none");
					no_pw=1;
				}else{
					$("#okicon1").css("display","none");
					$("#noicon1").css("display","inline");
					no_pw=-1;
				}
				
			})
			
			$("#pw2").keyup(function(){
				if($(this).val()==$("#pw").val()){
					$("#okicon2").css("display","inline");
					$("#noicon2").css("display","none");
				}else{
					$("#okicon2").css("display","none");
					$("#noicon2").css("display","inline");
				}
			})
			
			/*비밀번호 안내 */
			$("#pw_info").click(function(){
				alert("8자 이상 20자 이하 길이\n 영어,숫자,특수문자(~!@#$%^&*) 가능");
			})
		})
		
		/*공백 확인하기 */
		function empty(){
			$("#f").find("input").each(function(i,obj){
				if($(this).val()==""){
					alert("공백이 존재합니다");
					return false;
				}else{
					return true;
				}
			})
		}

	</script>
</head>
<body class="login-page">
	<c:if test="${nomember !=null }">
		<script>
			alert("${nomember}");
		</script>
	</c:if>
	<div class="login-box">
		<div class="login-logo">
			<a href="#"><b>DGIT</b>Project</a>
		</div>
		<div class="login-box-body">
			<p class="login-box-msg">Sign in to start your session</p>
			<form action="login" method="post">
				<div class="form-group has-feedback">
					<input type="text" name="id" class="form-control"
						placeholder="USER ID"> <span
						class="glyphicon glyphicon-user  form-control-feedback"></span>
				</div>
				<div class="form-group has-feedback">
					<input type="password" name="pw" class="form-control"> <span
						class="glyphicon glyphicon-lock  form-control-feedback"></span>
				</div>
				<div class="row">
					<div class="col-xs-4">
						<button type="submit" class="btn btn-primary btn-block btn-flat">Sign
							In</button>
					</div>
				</div>
			</form>
			<a href="#" class="text-center" data-toggle="modal"
				data-target="#myModal">Register a new membership</a>
		</div>

	</div>
	<!-- Modal -->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">회원가입</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" id="f">
						<div class="form-group">
							<label class="control-label col-sm-2" for="userid">아이디</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="userid"
									placeholder="아이디(6~15,영어,숫자)" maxlength="15">
							</div>
							<div class="col-sm-2 btn-multi-div">
								<button type='button' class="btn btn-warning" id="idCheck">중복체크</button>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="pw">비밀번호</label>
							<div class="col-sm-9">
								<input type="password" class="form-control" id="pw"
									placeholder="비밀번호(8~20,영어,숫자,특수문자(~!@#$%^&*))" maxlength="20">
							</div>
							<div class="col-sm-1 btn-multi-div">
								<a href="#" id="pw_info"><span class="glyphicon glyphicon-question-sign"></span></a>
								<span class="glyphicon glyphicon-ok-sign hiddenicon" id="okicon1"></span>
								<span class="glyphicon glyphicon-remove-sign hiddenicon" id="noicon1"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="pw2">비밀번호 확인</label>
							<div class="col-sm-9">
								<input type="password" class="form-control" id="pw2"
									placeholder="비밀번호 확인" maxlength="20">
							</div>
							<div class="col-sm-1 btn-multi-div">
								<span class="glyphicon glyphicon-ok-sign hiddenicon" id="okicon2"></span>
								<span class="glyphicon glyphicon-remove-sign hiddenicon" id="noicon2"></span>
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-sm-2" for="name">이름</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="name"
									placeholder="이름" onkeyup="this.value=this.value.replace(/[^a-zA-Z가-힣]/g,'');">
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-sm-2" for="email">이메일</label>
							<div class="col-sm-10">
								<input type="email" class="form-control" id="email"
									placeholder="이메일" onkeyup="this.value=this.value.replace(/[^A-Za-z0-9@.]/g,'');">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="phone">전화번호</label>
							<div class="col-sm-10">
								<input type="tel" class="form-control" id="phone"
									placeholder="전화번호" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');" maxlength="11">
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-info" id="join">가입</button>
					<button type="button" class="btn btn-default" data-dismiss="modal" id="close">닫기</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
