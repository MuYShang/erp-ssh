//保存当编辑的行的索引
var existEditIndex = -1;
var f_goods_name;
$(function(){
	$('#grid').datagrid({
		columns:[[
		          {field:'goodsuuid',title:'商品编号',width:100,editor:{type:'numberbox',options:{
		        	  //禁止编辑
		        	  disabled:true
		          }}},
		          {field:'goodsname',title:'商品名称',width:100,editor:{type:'numberbox',options:{
                              //禁止编辑
                              disabled:true
                          }}},
		          {field:'price',title:'价格',width:100,editor:{type:'numberbox',options:{precision:2}}},
		          {field:'num',title:'数量',width:100,editor:'numberbox'},
		          {field:'money',title:'金额',width:100,editor:{type:'numberbox',options:{precision:2,disabled:true}}},
		          {field:'-',title:'操作',width:100,formatter:function(value, row, rowIndex){
		        	  if(row.num != '合计'){
		        		  return '<a href="javascript:void(0)" onclick="deleteRow(' + rowIndex + ')">删除</a>';
		        	  }
		          }}
		          
		]],
		singleSelect:true,
		//显示编辑
		rownumbers: true,
		//显示行脚
		showFooter: true,
		toolbar: [
			{
				text: '新增',
				iconCls: 'icon-add',
				handler: function(){
					//判断是否存在编辑的行
					if(existEditIndex > -1){
						//关闭编辑
						$('#grid').datagrid('endEdit',existEditIndex);
					}
					//增加一行, row参数:{goodsuuid:'',goodsname:'',price:''}
					$('#grid').datagrid('appendRow',{num:0,money:0});
					//获取所的行记录，数组
					var rows = $('#grid').datagrid('getRows');
					
					//设置当前编辑行的索引
					existEditIndex = rows.length-1;
					//需要先设置它的编辑器，才能开启编辑状态
					$('#grid').datagrid('beginEdit',existEditIndex);
					
				}
			},'-',{
				text: '提交',
				iconCls: 'icon-save',
				handler: function(){
					//1. 存在编辑状态的行
					if(existEditIndex > -1){
						$('#grid').datagrid('endEdit',existEditIndex);
					}
					//获取所有的明细
					var rows = $('#grid').datagrid('getRows');
					if(rows.length == 0){
						return;
					}
					var formdata = $('#orderForm').serializeJSON();
					//转换成json字符串
					//给formdata加了一个json属性，key。同时再给它赋值
					formdata.json = JSON.stringify(rows);
					//formdata['json']= JSON.stringify(rows);
					$.ajax({
						url: 'orders_add',
						data: formdata,
						dataType: 'json',
						type: 'post',
						success:function(rtn){
							$.messager.alert('提示',rtn.message,'info',function(){
								if(rtn.success){
									//清空供应商
									$('#supplier').combogrid('clear');
									//清空表格
									$('#grid').datagrid('loadData',{total:0, rows:[],footer:[{num: '合计', money: 0}]});
								}
							});
						}
					});
				}
			
			}
		],
		onClickRow:function(rowIndex, rowData){
			//rowIndex：点击的行的索引值，该索引值从0开始。
			//rowData：对应于点击行的记录。
			//关闭当前可以编辑的行
			$('#grid').datagrid('endEdit',existEditIndex);
			//设置当前可编辑的索引行
			existEditIndex = rowIndex;
			$('#grid').datagrid('beginEdit',existEditIndex);
		}
	});
	
	//加行脚
	$('#grid').datagrid('reloadFooter',[{num: '合计', money: 0}]);
	
	//加载供应商下拉表格
	$('#supplier').combogrid({    
	    panelWidth:700,
	    idField:'uuid',    
	    textField:'name',    
	    url:'supplier_getList?type=1',
	    columns:[[    
			{field:'uuid',title:'编号',width:100},
			{field:'name',title:'名称',width:100},
			{field:'address',title:'联系地址',width:100},
			{field:'contact',title:'联系人',width:100},
			{field:'tele',title:'联系电话',width:100},
			{field:'email',title:'邮件地址',width:100}
	    ]]    
	});

    initGoods();


});

/**
 * 获取当前编辑行的指定编辑器
 * @param _field
 * @returns
 */
function getEditor(_field){
	return $('#grid').datagrid('getEditor',{index:existEditIndex,field:_field});
}

/**
 * 计算金额
 */
function cal(){
	//获取数量编辑器
	var numEditor = getEditor('num');
	//取得商品的数量
	var num = $(numEditor.target).val();
	
	//获取价格编辑器
    var priceEditor = getEditor('price');
    //取出进货价格
    var price = $(priceEditor.target).val();
    
    //计算金额
    var money = num * price;
    //保留2位小数
    money = money.toFixed(2);

    //获取金额编辑器
    var moneyEditor = getEditor('money');
    //设置金额
    $(moneyEditor.target).val(money);
    
    //更新表格中的数据,设置row json对象里的key对应的值
    $('#grid').datagrid('getRows')[existEditIndex].money = money;
}

/**
 * 绑定键盘的输入事件
 */
function bindGridEditor(){
	//获取数量编辑器
	var numEditor = getEditor('num');
	$(numEditor.target).bind('keyup',function(){
		//计算金额
		cal();
		//计算合计金额
		sum();
	});
	
	//绑定价格编辑器
	var priceEditor = getEditor('price');
	$(priceEditor.target).bind('keyup',function(){
		//计算金额
		cal();
		//计算合计金额
		sum();
	});
}

function initGoods() {
    var url = "goods_getList";
    $.getJSON(url,function(json) {
    	console.log(json);
    	console.log(json.rows);
        $('#goodsName').combobox({
            data: json.rows,
            valueField: 'uuid',
            textField: 'name',
            onSelect: function(goods){

                if(existEditIndex > -1){
                    //获取商品编辑编辑器
                    var goodsuuidEditor = getEditor('goodsuuid');
                    //target，指向真正使用element
                    $(goodsuuidEditor.target).val(goods.uuid);

                    var goodsnameEditor = getEditor('goodsname');
                    //target，指向真正使用element
                    $(goodsnameEditor.target).val(goods.name);

                    f_goods_name = goods.name;

                    //获取价格编辑器
                    var priceEditor = getEditor('price');
                    //设置为进货价格
                    $(priceEditor.target).val(goods.inprice);

                    //获取数量编辑器
                    var numEditor = getEditor('num');
                    //选中数量输入框
                    $(numEditor.target).select();
                    //绑定事件
                    bindGridEditor();
                    //计算金额
                    cal();
                    //计算合计金额
                    sum();
                }

            }
        });
    });
}

/**
 * 计算合计金额
 */
function sum(){	
	//获取所有行
	var rows = $('#grid').datagrid('getRows');
	var total = 0;
	//循环累计
	$.each(rows, function(i, row){
		total += parseFloat(row.money);
	});
	total = total.toFixed(2);
	
	//设置合计金额到行脚里去
	$('#grid').datagrid('reloadFooter',[{num: '合计', money: total}]);
}

/**
 * 删除行
 * @param rowIndex
 */
function deleteRow(rowIndex){
	//alert(JSON.stringify(data));
	//关闭编辑
	$('#grid').datagrid('endEdit',existEditIndex);
	//删除行
	$('#grid').datagrid('deleteRow',rowIndex);
	
	var data = $('#grid').datagrid('getData');
	//重新加载数据
	$('#grid').datagrid('loadData',data);
	//计算合计
	sum();
}