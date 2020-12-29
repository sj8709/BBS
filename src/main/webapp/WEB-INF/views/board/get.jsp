<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<%@include file="../includes/header.jsp" %>

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Board Read</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">Board Read Page</div>
            <!-- /.panel-heading -->
            <div class="panel-body">
            	<div class="form-group">
            		<label>Bno</label> <input class="form-control" name='bno'
            		value='<c:out value="${board.bno}"/>' readonly="readonly">
            	</div>
            	<div class="form-group">
            		<label>Title</label> <input class="form-control" name='title'
            		value='<c:out value="${board.title}"/>'  readonly="readonly">
            	</div>
            	<div class="form-group">
            		<label>TextArea</label>
            		<textarea class="form-control" rows="3" name='content' readonly="readonly">
            		<c:out value="${board.content }"/></textarea>
            	</div>
            	<div class="form-group">
            		<label>Writer</label> <input class="form-control" name='writer'
            		value='<c:out value="${board.writer}"/>'  readonly="readonly">
            	</div>
                <button data-oper='modify' class="btn btn-default"><a href="/board/modify?bno=<c:out value="${board.bno}"/>">Modify</a></button>
                <button data-oper='list' class="btn btn-info">List</button>
				<form id='operForm' action="/board/modify" method="get">
				  <input type='hidden' id='bno' name='bno' value='<c:out value="${board.bno}"/>'>
				  <input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum}"/>'>
				  <input type='hidden' name='amount' value='<c:out value="${cri.amount}"/>'>
				  <input type='hidden' name='type' value='<c:out value="${cri.type}"/>'>
				  <input type='hidden' name='keyword' value='<c:out value="${cri.keyword}"/>'>
				 </form>
             </div>
            <!-- /.panel-body -->
        </div>
        <!-- /.panel -->
    </div>
    <!-- /.col-lg-6 -->
</div>
<!-- /.row -->

<!-- 댓글 목록 처리 -->
<div class='row'>
	<div class="col-lg-12">
		<!-- /.panel -->
		<div class="panel panel-default">
			<div class="panel-heading">
	        	<i class="fa fa-comments fa-fw"></i> Reply
	        	<button id='addReplyBtn' class='btn btn-primary btn-xs pull-right'>New Reply</button>
	      	</div>      
	      
	      
			<!-- /.panel-heading -->
			<div class="panel-body">
				<ul class="chat">
					<!-- start chat content -->
					<!-- 각 li는 하나의 댓글을 의미 -->
					<!-- 수정이나 삭제시엔 반드시 'data-rno'(댓글 번호) 속성을 이용 -->
					<li class="left clearfix" data-rno='12'>
						<div>
							<div class="header">
								<strong class="primary-font">user00</strong>
								<small class="pull-right" text-muted">2018-01-01 13:13</small>
							</div>
							<p>Good jop</p>
						</div>
					</li>
				<!-- end reply -->
	    		</ul>
	    	<!-- ./ end ul -->
	  		</div>
	  		<!-- /.panel .chat-panel -->
	  		<div class="panel-footer"></div>
  		</div>
	</div>
	<!-- ./ end row -->
</div>

<!-- 댓글 추가 Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
    	<div class="modal-content">
      		<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        			<h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
      		</div>
      		<div class="modal-body">
        		<div class="form-group">
          			<label>Reply</label> 
          			<input class="form-control" name='reply' value='New Reply!!!!'>
        		</div>      
	        	<div class="form-group">
	          		<label>Replyer</label> 
	          		<input class="form-control" name='replyer' value='replyer'>
	        	</div>
	        	<div class="form-group">
	          		<label>Reply Date</label> 
	          		<input class="form-control" name='replyDate' value='2018-01-01 13:13'>
	        	</div>
        	</div>
			<div class="modal-footer">
				<button id='modalModBtn' type="button" class="btn btn-warning">Modify</button>
				<button id='modalRemoveBtn' type="button" class="btn btn-danger">Remove</button>
				<button id='modalRegisterBtn' type="button" class="btn btn-primary">Register</button>
				<button id='modalCloseBtn' type="button" class="btn btn-default">Close</button>
			</div>          
		</div>
    <!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<script type="text/javascript" src="/resources/js/reply.js">

</script>
<script type="text/javascript" >
	$(document).ready(function() {
		console.log("============");
		console.log("JS TEST");
		
		var bnoValue = '<c:out value="${board.bno}"/>';
		var replyUL = $(".chat");
		  
		showList(1); // showList 무조건 실행
		    
		// 페이지 번호를 파라미터로 받고, 만약 파라미터가 없는 경우에는 자동으로 1페이지가 되도록 설정
		function showList(page){
		    replyService.getList({bno:bnoValue, page:page||1 }, function(list) {
		    	var str="";
		    	if(list == null || list.length == 0){
		       		return;
		     	}
		     
		     	for (var i = 0, len = list.length || 0; i < len; i++) {
					str +="<li class='left clearfix' data-rno='"+list[i].rno+"'>";
					str +="  <div><div class='header'><strong class='primary-font'>["
					 +list[i].rno+"] "+list[i].replyer+"</strong>"; 
					str +="    <small class='pull-right text-muted'>"
					    +replyService.displayTime(list[i].replyDate)+"</small></div>";
					str +="    <p>"+list[i].reply+"</p></div></li>";
			     }
		     	replyUL.html(str); // class내의 내용 대체 html()=innerHTML
		   });//end function
		 }//end showList
		 
		 var modal = $(".modal");
		 var modalInputReply = modal.find("input[name='reply']");
		 var modalInputReplyer = modal.find("input[name='replyer']");
		 var modalInputReplyDate = modal.find("input[name='replyDate']");
		 
		 var modalModBtn = $("#modalModBtn");
		 var modalRemoveBtn = $("#modalRemoveBtn");
		 var modalRegisterBtn = $("#modalRegisterBtn");
		 
		 $("#addReplyBtn").on("click", function(e) {
			 modal.find("input").val("");
			 modalInputReplyDate.closest("div").hide();
			 //modal.find("button[id != 'modalCloseBtn']").hide();
			 
			 modalRegisterBtn.show();
			 
			 $(".modal").modal("show");
		 })
		
//  		replyService.add(
// 			{reply:"JS TEST", replyer:"tester", bno:bnoValue}
// 			,
// 			function(result){
// 				alert("RESULT: " + result);
// 			}
// 		); 
 		
//  		// 댓글 조회
// 		replyService.getList({bno:bnoValue, page:1}, function(list){
// 			for(var i=1, len=list.length||0; i<len; i++) {
// 				console.log(list[i]);
// 			}
// 		});

// 		// 댓글 삭제
// 		replyService.remove(23, function(count) {
// 			console.log(count);
			
// 			if(count === "success") {
// 				alert("REMOVED");
// 			}
// 		}, function(error) {
// 			alert("ERROR........");
// 		});
		
		
// 		// 댓글 수정
// 		replyService.update({
// 			rno : 12,
// 			bno : bnoValue,
// 			reply : "Modified Reply...."
// 		}, function(result) {
// 			alert("수정 완료......");
// 		});
		
// 		replyService.get(10, function(data) {
// 				console.log(data);
// 		})
		
		
		var operForm = $("#operForm");
		// modify 버튼 클릭시
		$("button[data-oper='modify']").on("click", function(e){
			operForm.attr("action", "/board/modify").submit();
		});
		// list 버튼 클릭시
		$("button[data-oper='list']").on("click", function(e){
			operForm.find("#bno").remove();
			operForm.attr("action" , "/board/list");
			operForm.submit();
		});
	}); 
</script>
<%@ include file="../includes/footer.jsp" %>
