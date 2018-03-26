<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
						<button type="button" class="btn btn-danger">편집</button>
					</div>

				</div>
				<div class="box-body">
					<c:forEach var="item"  items="${photoList }">
						
					</c:forEach>
				</div>

				<div class="box-footer">
					<div class="text-center"></div>

				</div>
			</div>
		</div>
	</div>

</section>
<%@ include file="include/footer.jsp"%>