<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- //easyUI语言：页面不刷新
//datagrid:做一次请求，默认带分页参数。比如--请求的参数：http://localhost:8080/item/list?（page=1&rows=30  分页信息）
//3、返回值。Json数据。
//Easyui中datagrid控件要求的数据格式为：{total:”2”,rows:[{“id”:”1”,”name”,”张三”},{“id”:”2”,”name”,”李四”}]} -->
<!-- 查询条件 start -->
<form id="searchForm" style="padding: 0;margin: 0"> 
    <table border="0" width="100%">
        <tr style="height:30px">
            <td width="5%" style="text-align:right;border:0px">用户ID：</td>
            <td width="12%" style="border:0px">
                <input class="easyui-textbox" name="id" id="id" style="width:100%"> 
            </td>   

            <td width="5%" style="text-align:right;border:0px">用户名：</td>
            <td width="12%" style="border:0px">
                <input class="easyui-textbox" name="username" id="username" style="width:100%"> 
            </td>   
         </tr>
         <tr style="height:30px">
            <td width="5%" style="text-align:right;border:0px">手机号：</td>
            <td width="12%" style="border:0px">
                <input class="easyui-textbox" name="phone" id="phone" style="width:100%"> 
            </td>   
         </tr> 
        <tr style="height:30px">
            <td colspan="8" style="text-align:center;border:0px" >
                <a href="javascript:void(0)"  class="easyui-linkbutton" iconCls="icon-search" onclick="searchCase()" style="margin-right:20px"><strong>查询</strong></a>          
                <a href="javascript:void(0)"  class="easyui-linkbutton" iconCls="icon-redo" onclick="resetSearch()" ><strong>重置</strong></a>
            </td>

        </tr>                                               
    </table>   
</form>
<!-- 查询条件 end -->
<!-- 请求用户列表list的url：/item/list -->
<table class="easyui-datagrid" id="userList" title="用户列表" 
       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/user/list',method:'get',pageSize:30,toolbar:toolbar">
    <thead>
        <tr>
        	<th data-options="field:'ck',checkbox:true"></th>
        	<th data-options="field:'id',width:60,align:'center'">用户ID</th>
            <th data-options="field:'username',width:150,align:'center'">用户名</th>
            <th data-options="field:'phone',width:150,align:'center'">注册手机号</th>
            <th data-options="field:'email',width:150,align:'center'">注册邮箱</th>
            <th data-options="field:'nickname',width:100,align:'center'">昵称</th>
            <th data-options="field:'status',width:60,align:'center',formatter:TAOTAO.formatUserStatus">状态</th>
            <th data-options="field:'created',width:130,align:'center',formatter:TAOTAO.formatDateTime">创建日期</th>
            <th data-options="field:'updated',width:130,align:'center',formatter:TAOTAO.formatDateTime">更新日期</th>
        </tr>
    </thead>
</table>
<div id="itemEditWindow" class="easyui-window" title="编辑用户" data-options="modal:true,closed:true,iconCls:'icon-save',href:'item-edit'" style="width:80%;height:80%;padding:10px;">
</div>
<script>
	//重置查询
	function resetSearch() {
	    $("#searchForm").form("clear");
	}
	//查询
	function searchCase() {
	    var param = new Object();   
	    //获取查询条件
	    var searchFormData = $("#searchForm").serializeArray();                
	    $.each(searchFormData,function(i,v){
	        param[v.name] = v.value;
	    });
		console.info(param);
	    //根据查询条件重新加载datagrid，这里会将查询的条件信息发送到后端，
	    //url是在datagrid定义时的url，只需在后端根据查询的添加获取相应信息返回即可。
	    $('#userList').datagrid("reload", param); 
	
	}
    function getSelectionsIds(){
    	var userList = $("#userList");
    	var sels = userList.datagrid("getSelections");
    	var ids = [];
    	for(var i in sels){
    		ids.push(sels[i].id);
    	}
    	ids = ids.join(",");
    	return ids;
    }
    
    var toolbar = [{
        text:'启用',
        iconCls:'icon-remove',
        handler:function(){
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中用户!');
        		return ;
        	}
        	$.messager.confirm('确认','确定启用ID为 '+ids+' 的用户吗？',function(r){
        	    if (r){
        	    	var params = {"ids":ids};
                	$.post("/rest/user/instock",params, function(data){
            			if(data.status == 200){
            				$.messager.alert('提示','启用成功!',undefined,function(){
            					$("#userList").datagrid("reload");
            				});
            			}
            		});
        	    }
        	});
        }
    },{
        text:'注销',
        iconCls:'icon-remove',
        handler:function(){
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中用户!');
        		return ;
        	}
        	$.messager.confirm('确认','确定注销ID为 '+ids+' 的用户吗？',function(r){
        	    if (r){
        	    	var params = {"ids":ids};
                	$.post("/rest/user/reshelf",params, function(data){
            			if(data.status == 200){
            				$.messager.alert('提示','注销用户成功!',undefined,function(){
            					$("#userList").datagrid("reload");
            				});
            			}
            		});
        	    }
        	});
        }
    }];
</script>