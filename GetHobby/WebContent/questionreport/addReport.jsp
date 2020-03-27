<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<meta name="viewport"content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	<!-- ������Ʈ �ĺ��� -->
    <link rel=" shortcut icon" href="/resources/image/logo/logo-favicon.png">
    <link rel="icon" href="/resources/image/logo/logo-favicon.png">
    
    <!-- favicon 404 ���� �Ⱥ��̰�  -->
    <link rel="icon" href="data:;base64,iVBORw0KGgo=">

    <!-- �� ��Ʈ (�������) -->
    <link href="https://fonts.googleapis.com/css?family=Nanum+Gothic:400,700,800&display=swap&subset=korean" rel="stylesheet">


    <!--��Ʈ��Ʈ�� CSS-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <!-- fontawesome cdn(�� ������ ���̺귯��) -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />



    <!-- jQuery js -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

    <!-- ��Ʈ��Ʈ�� js -->
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>


    <!-- ���� CSS -->
    <link rel="stylesheet" href="/resources/css/common.css">

    <!-- ���� �޴� CSS -->
    <link rel="stylesheet" href="/resources/css/header.css">
    <!-- ���� �޴� js -->
    <script src="/resources/javascript/header.js"></script>
	
	<!-- sweet alert -->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@9.7.2/dist/sweetalert2.all.min.js"></script>
    
    <style>
    	.report-container {
		    margin-top: 16px;
		    margin-left: -16px;
		}
		
		.report-col {
		    display: flex;
		    -webkit-box-align: center;
		    align-items: center;
		    cursor: pointer;
		    white-space: nowrap;
		    text-align: left;
		    margin-bottom: 12px;
		    border-radius: 1px;
		    flex: 1 1 0%;
		    padding: 12px 16px;
		}
		
		.report-text-non-select {
		    font-size: 16px;
		    font-weight: normal;
		    line-height: 24px;
		    letter-spacing: -0.2px;
		    color: rgb(168, 174, 179);
		    white-space: normal;
		    margin: 0px;
		    flex: 1 1 0%;
		}
		
		.report-cirlce-non-select {
		    display: inline-flex;
		    box-sizing: border-box;
		    width: 20px;
		    height: 20px;
		    margin-right: 8px;
		    flex: 0 0 auto;
		    border-width: 2px;
		    border-style: solid;
		    border-color: rgb(168, 174, 179);
		    border-image: initial;
		    border-radius: 50%;
		    cursor : pointer;
		}
		
		.report-row-div {
			display: flex;
			flex-direction: column;
		}
		
		.report-cirlce-select {
		    display: inline-flex;
		    box-sizing: border-box;
		    width: 20px;
		    height: 20px;
		    margin-right: 8px;
		    flex: 0 0 auto;
		    border-style: solid;
		    border-image: initial;
		    border-radius: 50%;
		    border-color: rgb(62, 64, 66);
		    border-width: 4px;
		}
		
		.report-check-reason-text {
			display: inline-block;
		}
    </style>
    
    <script type="text/javascript">
    	$(function(){
    		$('.report-test-button').on('click', function(){
    			var userId = $('.user-hidden').val();

    			if ( userId == null || userId == '' ) {
    				Swal.fire({
						icon : 'error',
						title : '�α����� �ʿ��մϴ�.',
						showConfirmButton : false, 
						allowOutsideClick : true,
						timer : 800
					});
    				
    				return;
    			}
    			
    			var replyText = $(this).parent().find('.reply-content').text().trim();
    			
    			var replyNo = $(this).parent().find('.reply-hidden-number').val();
    			
    			
    			if ( replyText.length <= 10 ) {
    				replyText = '(' + replyText + ')';
    			}
    			else {
    				replyText = '(' + replyText.substr(0, 10) + '...)'
    			}
    			
    			$('.report-reply-hidden-content').val( replyText );
    			
    			replyText = $('.report-reply-hidden-content').val();
    			
    			
    			$('.report-reply-number-hidden').val(replyNo);
    			
    			
    			var display = '';
    			display += '<div class="report-check-reason-text"><h6>�ش� ���' + replyText + '�� <span class="text-danger">�������� ����</span> ������ �Ű��Ͻðڽ��ϱ�?</h6></div>';
    			
    			$('.report-reply-content-check').html(display);
    			
    			$('.report-madal-total').modal('show');
    		})
    		
    		// �Ű� ���â Ŭ���� �̺�Ʈ -------------------------------------------------------
			$('.report-row-div').on('click', function(){
				$('.report-container').find('.report-cirlce').attr('class', 'report-cirlce report-cirlce-non-select');
				$('.report-container').find('.report-div').attr('class', 'report-div report-text-non-select');
				
				$(this).find('.report-cirlce').attr('class', 'report-cirlce report-cirlce-select');
				$(this).find('.report-div').attr('class', 'report-div report-text-select');
				
				var stateValue = $(this).find('.report-hidde-value').val();
				
				var buttonText = $(this).find('.report-div').text().trim();
				
				var replyContent = $('.report-reply-hidden-content').val();
				
				$('.report-hidden-input-value').val(stateValue);
				console.log( $('.report-hidden-input-value').val() + ' / ' + buttonText );
				
				var display = '';
				display += '<div class="report-check-reason-text">�ش� ���' + replyContent + '�� <span class="text-danger">' + buttonText + '</span> ������ �Ű��Ͻðڽ��ϱ�?</div>';
				$('.report-reply-content-check').html(display);
			});
			// �Ű� ���â Ŭ���� �̺�Ʈ -------------------------------------------------------
    		
			// �Ű� ���â �׳� �������� �ʱ�ȭ �̺�Ʈ -----------------------------------------------
			$('.report-madal-total').on('hidden.bs.modal', function(){
				$('.report-container').find('.report-cirlce').attr('class', 'report-cirlce report-cirlce-non-select');
				$('.report-container').find('.report-div').attr('class', 'report-div report-text-non-select');
				
				$('.report-container').find('.report-cirlce').eq(0).attr('class', 'report-cirlce report-cirlce-select');
				$('.report-container').find('.report-div').eq(0).attr('class', 'report-div report-text-select');
				
			})
			// �Ű� ���â �׳� �������� �ʱ�ȭ �̺�Ʈ -----------------------------------------------
			
			$('.modal-to-report-process-button').on('click', function(){
				var reportCode = $('.report-hidden-input-value').val();
				var replyNo = $('.report-reply-number-hidden').val();
				var report = new Object();
				report.replyNo = replyNo;
				report.reportCode = reportCode;
				
				$.ajax(
						{
							url : "/questionReport/json/addReport",
							method : "post",
							dataType : "json",
							headers : {
								"Accept" : "application/json",
								"Content-Type" : "application/json"
							},
							data : JSON.stringify(
								report		
							),
							success : function(JSONData, status) {
								console.log('JSONData.result ? : ' + JSONData.result);
								
								if (JSONData.result == 'true') {
									$('.report-madal-total').modal('hide');
									$('.report-navi-span-text').text('�Ű� ���������� �����Ǿ����ϴ�.');
									$('.report-result-modal').modal('show');
								}
								else {
									$('.report-madal-total').modal('hide');
									$('.report-navi-span-text').text('�̹� �Ű��� ����Դϴ�.');
									$('.report-result-modal').modal('show');
								}
							}
						}
				)
			})
		
			$('.report-result-modal-close-button').on('click', function(){
				$('.modal-backdrop').remove();
			})
    	})
    </script>
</head>
<body>
	<!-- �Ű� ���â ���� -->
	<div class="modal fade report-madal-total" tabindex="-1" role="dialog" aria-labelledby="report-modal-label" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="report-modal-label">�Ű��ϱ�</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="container report-container">
						<input type="hidden" class="report-reply-number-hidden" />
						<input type="hidden" class="report-hidden-input-value" value="0" />
						<input type="hidden" class="report-reply-hidden-content" />
						<div class="row report-row-div">
							<div class="col-sm-12 report-col">
								<div color="#3e4042" class="report-cirlce report-cirlce-select"></div>
								<div color="#3e4042" class="report-div report-text-select">
									�������� ����
								</div>
								<input type="hidden" class="report-hidde-value" value="0" />
							</div>
						</div>
						<br/>
						<div class="row report-row-div">
							<div class="col-sm-12 report-col">
								<div color="#3e4042" class="report-cirlce report-cirlce-non-select"></div>
								<div color="#3e4042" class="report-div report-text-non-select">
									����
								</div>
								<input type="hidden" class="report-hidde-value" value="1" />
							</div>
						</div>
						<br/>
						<div class="row report-row-div">
							<div class="col-sm-12 report-col">
								<div color="#3e4042" class="report-cirlce report-cirlce-non-select"></div>
								<div color="#3e4042" class="report-div report-text-non-select">
									�弳
								</div>
								<input type="hidden" class="report-hidde-value" value="2" />
							</div>
						</div>
						<br/>
						<div class="row">
							<div class="col-sm-12">
								<span class="report-reply-content-check">
									
								</span>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary modal-to-report-process-button">�Ű�</button>
					<button type="button" class="btn btn-secondary" data-dismiss="modal">���</button>
				</div>
			</div>
		</div>
	</div>
	<!-- �Ű� ���â �� -->
	
	<!-- �Ű�ó�� �Ϸ� ���â ���� -->
	<div class="modal fade report-result-modal" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="report-result-modal" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title report-result-modal" id="report-result-modal">�Ű� ��� Ȯ��</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<span class="report-navi-span-text">�Ű� ���������� �����Ǿ����ϴ�.</span>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary report-result-modal-close-button" data-dismiss="modal">Ȯ��</button>
				</div>
			</div>
		</div>
	</div>
	<!-- �Ű�ó�� �Ϸ� ���â �� -->
	
	<input type="hidden" class="user-hidden" value="${sessionScope.user.userId }" />
	<div class="test-outer-div">	
		<input type="hidden" value="103" class="reply-hidden-number" />
		<div class="reply-content">
			abcfgfd fdq fwqewqefsadf fadsf wqwf d
		</div>
		<button type="button" class="btn btn-secondary report-test-button">�Ű��׽�Ʈ</button>
	</div>
</body>
</html>