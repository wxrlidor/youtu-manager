<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link  rel="stylesheet" type="text/css" href="css/style_log.css">
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/userpanel.css">
<script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>优兔--后台系统登录</title>
</head>
<body>
<body class="login" mycollectionplug="bind">
<div class="login_m">
<div class="login_logo"><img src="images/youtu_logo.png" width="500" height="86"></div>
<div class="login_boder">
<div class="login_padding" id="login_model">
<form id="formlogin" method="post" onsubmit="return false;">
  <h2>用户名</h2>
  <label>
    <input type="text" name="username" id="username" class="txt_input txt_input2" onfocus="if (value ==&#39;请输入用户名&#39;){value =&#39;&#39;}" onblur="if (value ==&#39;&#39;){value=&#39;请输入用户名&#39;}" value="请输入用户名">
  </label>
  <h2>密码</h2>
  <label>
    <input type="password" name="password" id="userpwd" class="txt_input" >
  </label>
 </form>
  <p class="forgot"><a id="iforget" href="javascript:void(0);">忘记密码?</a></p>
  <div class="rem_sub">
  <div class="rem_sub_l">
 <!--  <input type="checkbox" name="checkbox" id="save_me">
   <label for="checkbox">Remember me</label> -->
   </div>
    <label>
      <input type="submit" class="sub_button" name="button" id="button" value="登录" style="opacity: 0.7;">
    </label>
  </div>
</div>
<!--login_padding  Sign up end-->
</div><!--login_boder end-->
</div><!--login_m end-->
 <br> <br>
<p align="center">Powered by youtu.com ©2017-2018 优兔网 备案号: 粤IPC备1XXXXX07号</p>
<script type="text/javascript">
	//加载login页时，先取得redirect变量，保存登陆完成后的回调页面
	var redirectUrl = "${redirect}";
	var LOGIN = {
			checkInput:function() {
				if ($("#username").val() == "") {
					alert("用户名不能为空");
					$("#username").focus();
					return false;
				}
				if ($("#userpwd").val() == "") {
					alert("密码不能为空");
					$("#userpwd").focus();
					return false;
				}
				return true;
			},
			doLogin:function() {
				$.post("/admin/login", $("#formlogin").serialize(),function(data){
					if (data.status == 200) {
						alert("登录成功！");
						//登陆后需要跳转的url，默认跳转到商城首页
						if (redirectUrl == "") {
							location.href = "http://localhost:8080/index";
						} else {
							location.href = redirectUrl;
						}
					} else {
						alert("登录失败，原因是：" + data.msg);
						$("#username").select();
					}
				});
			},
			login:function() {
				if (this.checkInput()) {
					this.doLogin();
				}
			}
		
	};
	$(function(){
		//绑定回车键事件
		$(document).keydown(function(event){ 
			if(event.keyCode==13){ 
			$("#button").click(); 
			} 
		});
		//绑定登陆按钮事件
		$("#button").click(function(){
			LOGIN.login();
		});
	});
</script>
</body>
</html>