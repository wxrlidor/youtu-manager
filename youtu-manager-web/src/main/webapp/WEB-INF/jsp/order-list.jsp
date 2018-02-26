<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- //easyUI语言：页面不刷新
//datagrid:做一次请求，默认带分页参数。比如--请求的参数：http://localhost:8080/item/list?（page=1&rows=30  分页信息）
//3、返回值。Json数据。
//Easyui中datagrid控件要求的数据格式为：{total:”2”,rows:[{“id”:”1”,”name”,”张三”},{“id”:”2”,”name”,”李四”}]} -->
<!-- 查询条件 start -->
<form id="searchForm" style="padding: 0;margin: 0"> 
    <table border="0" width="100%">
        <tr style="height:30px">
            <td width="5%" style="text-align:right;border:0px">
               订单号：
            </td>
            <td width="12%" style="border:0px">
                <input class="easyui-textbox" name="orderId" id="orderId" style="width:100%"> 
            </td>   

            <td width="5%" style="text-align:right;border:0px">
                用户ID：
            </td>
            <td width="12%" style="border:0px">
                <input class="easyui-textbox" name="userId" id="userId" style="width:100%"> 

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
<table class="easyui-datagrid" id="orderList" title="商品列表" 
       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/order/list',method:'get',pageSize:30">
    <thead>
        <tr>
        	<!-- <th data-options="field:'ck',checkbox:true"></th> -->
        	<th data-options="field:'orderId',width:150,align:'center'">订单号</th>
            <th data-options="field:'payment',width:100,align:'center'">实付金额</th>
            <th data-options="field:'paymentType',width:80,align:'center',formatter:TAOTAO.formatOrderPayType">支付类型</th>
            <th data-options="field:'post_fee',width:80,align:'center'">邮费</th>
            <th data-options="field:'status',width:110,align:'center',formatter:TAOTAO.formatOrderStatus">状态</th>
            <th data-options="field:'createTime',width:130,align:'center',formatter:TAOTAO.formatDateTime">创建日期</th>
            <th data-options="field:'updateTime',width:130,align:'center',formatter:TAOTAO.formatDateTime">更新日期</th>
            <th data-options="field:'userId',width:100,align:'center'">买家id</th>
            <th data-options="field:'buyerNick',width:150,align:'center'">买家昵称</th>
        </tr>
    </thead>
</table>
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
	    //根据查询条件重新加载datagrid，这里会将查询的条件信息发送到后端，
	    //url是在datagrid定义时的url，只需在后端根据查询的添加获取相应信息返回即可。
	    $('#orderList').datagrid("reload", param); 
	
	}
    function getSelectionsIds(){
    	var orderList = $("#orderList");
    	var sels = orderList.datagrid("getSelections");
    	var ids = [];
    	for(var i in sels){
    		ids.push(sels[i].id);
    	}
    	ids = ids.join(",");
    	return ids;
    }
    /* 
    var toolbar = [{
        text:'删除',
        iconCls:'icon-cancel',
        handler:function(){
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中商品!');
        		return ;
        	}
        	$.messager.confirm('确认','确定删除ID为 '+ids+' 的商品吗？',function(r){
        	    if (r){
        	    	var params = {"ids":ids};
                	$.post("/rest/item/delete",params, function(data){
            			if(data.status == 200){
            				$.messager.alert('提示','删除商品成功!',undefined,function(){
            					$("#orderList").datagrid("reload");
            				});
            			}
            		});
        	    }
        	});
        }
    }]; */
</script>