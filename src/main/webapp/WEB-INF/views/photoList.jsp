<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<style>
#photo {
	overflow: auto;
}

#bigPhoto {
	text-align: center;
}

#bigPhoto img {
	max-width: 570px;
	text-align: center;
	overflow: auto;
}
</style>
<script>
	$(function() {
		var index = 1;

		$("#files").change(function() {
			var file = document.getElementById("files");
			var reader = new FileReader();

			reader.onload = function(e) {
				var imgObj = $("<img>").attr("src", e.target.result);
				$("#photo").append(imgObj);
			}

			reader.readAsDataURL(file.files[0]);
			reader.onloadend = function(e) {
				if (index >= file.files.length) {
					index == 1;
					return;
				}
				reader.readAsDataURL(file.files[index]);
				index++;
			}

		})

		$("#addPoto").click(function() {
			$("#f").submit();
			alert("사진 업로드 완료");
		})

		$("#back").click(function() {
			$("#photo").empty();
			$("#files").val("");
			$("#f").find(".newfiles").remove();
		})

		$(document).on(
				"click",
				"#t tr",
				function() {
					$("#bigPhoto").empty();

					var filename = $(this).find("img").attr("data-img");
					var date = $(this).find(".date").text();
					var pno = $(this).find(".hiddenPno").val();

					var front = filename.substring(0, 12);
					var end = filename.substring(14);

					var orignalFileName = front + end;

					var img = $("<img>").attr("src",
							"displayFile?filename=" + orignalFileName);
					$("#bigPhoto").append(img);
					$("#modal-date").text(date);
					$("#deleteBtn").attr("value", pno);
				})

		$(document).on("click", "#myModalClose", function() {
			$("#bigPhoto").empty();
		})

		$("#deleteBtn").click(function() {
			var pno = $(this).val();
			if (confirm("삭제하시겠습니까?")) {
				location.href = "remove?pno=" + pno;
			} else {
				return;
			}
		})

	})
</script>
<%@ include file="include/header.jsp"%>
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box">
				<div class="box-header with-border row">
					<div class="col-md-10">
						<h3 class="box-title">PHOTO LIST</h3>
					</div>

					<div class="col-md-2">
						<button type="button" class="btn btn-primary" data-toggle="modal"
							data-target="#addModal">추가</button>
					</div>

				</div>
				<div class="box-body">
					<table class="table table-hover" id="t">
						<thead>
							<tr>
								<th colspan="3">업로드 사진</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${photoList.size()==0 }">
								<tr>
									<td colspan="3">사진이 없습니다.</td>
								</tr>
							</c:if>
							<c:if test="${photoList.size()>=0 }">
								<c:forEach var="item" items="${photoList }">
									<tr data-toggle="modal" data-target="#myModal">
										<td colspan="3"><fmt:formatDate
												value="${item.uploaddate }" pattern="yyyy-MM-dd"
												var="uploaddate" />
											<p class="date">${uploaddate }</p>
											<div>
												<img src="displayFile?filename=${item.photoName}"
													data-img="${item.photoName }">
											</div> <input type="hidden" value="${item.pno }" class="hiddenPno">
										</td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</div>

				<div class="box-footer">
					<div class="text-center">
						<ul class="pagination">
							<c:if test="${pageMaker.prev }">
								<li><a
									href="${pageContext.request.contextPath }/photoList?page=${pageMaker.startPage-1 }">&laquo;</a></li>
							</c:if>
							<c:forEach begin="${pageMaker.startPage }"
								end="${pageMaker.endPage }" var="idx">
								<li ${pageMaker.cri.page == idx? 'class=active' : '' }><a
									href="${pageContext.request.contextPath }/photoList?page=${idx }">${idx }</a></li>
							</c:forEach>
							<c:if test="${pageMaker.next }">
								<li><a
									href="${pageContext.request.contextPath }/photoList?page=${pageMaker.endPage+1 }">&raquo;</a></li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>

<div id="addModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">사진 업로드</h4>
			</div>
			<div class="modal-body">
				<div>
					<form enctype="multipart/form-data" id="f" action="upload"
						method="post">
						<input type="file" id="files" multiple="multiple" name="files">
					</form>
				</div>
				<div id="photo"></div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-success" data-dismiss="modal"
					id="addPoto">추가</button>
				<button type="button" class="btn btn-default" data-dismiss="modal"
					id="back">취소</button>
			</div>
		</div>
	</div>
</div>

<div id="myModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					id="myModalClose">&times;</button>
				<p id="modal-date"></p>
			</div>
			<div class="modal-body" id="bigPhoto"></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-danger" id="deleteBtn">삭제</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
			</div>
		</div>

	</div>
</div>
<%@ include file="include/footer.jsp"%>