/**
 *生产计划管理
 */
var pageCurr;
$(function(){
    layui.use('table', function(){
        var table = layui.table
        var form = layui.form

        tableIns = table.render({
            elem: '#prodPlanList'
            ,url:'/business/getProdPlans'
            ,method: 'post' //默认：get请求
            ,cellMinWidth: 80
            ,page: true,
            request: {
                pageName: 'page' //页码的参数名称，默认：page
                ,limitName: 'limit' //每页数据量的参数名，默认：limit
            },response:{
                statusName: 'code' //数据状态的字段名称，默认：code
                ,statusCode: 200 //成功的状态码，默认：0
                ,countName: 'totals' //数据总数的字段名称，默认：count
                ,dataName: 'list' //数据列表的字段名称，默认：data
            }
            ,cols: [[
                {type:'numbers'}
                ,{field:'id', title:'ID',width:80, unresize: true, sort: true}
                ,{field:'cusCode', title:'客户号'}
                ,{field:'cusName', title:'客户名称'}
                ,{field:'workShopCode', title: '车间号'}
                ,{field:'workShopName', title: '车间名'}
                ,{field:'workStationCode', title: '工位号'}
                ,{field:'invCode', title: '产品编号'}
                ,{field:'invName', title: '产品名称'}
                ,{field:'invStd', title: '规格型号'}
                ,{field:'procedureCode', title: '工序号'}
                ,{field:'planDate', title: '计划日期'}
                ,{field:'planQty', title: '计划数'}
                ,{field:'planHour', title:'计划加工小时数'}
                ,{fixed:'right', title:'操作',width:120,align:'center', toolbar:'#optBar'}
            ]]
            ,  done: function(res, curr, count){
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                //console.log(res);
                //得到当前页码
                //console.log(curr);
                //得到数据总量
                //console.log(count);
                pageCurr=curr;
            }
        })
        table.on('tool(prodPlanTable)',function(obj){
            var data = obj.data
            if(obj.event === 'del'){
                //删除
                delProdPlan(data, data.id)
            }else if(obj.event === 'edit'){
                //编辑
                getProdPlan(data, data.id)
            }else if(obj.event === 'recover'){
                //恢复
                recoverProdPlan(data, data.id)
            }
        })
        //监听提交
        form.on('submit(prodPlanSubmit)', function(data){
            // TODO 校验
            formSubmit(data);
            return false;
        })

    })
    //搜索框
    layui.use(['form','laydate'], function(){
        var form = layui.form ,layer = layui.layer, laydate = layui.laydate
        //TODO 数据校验
        //监听搜索框
        //日期
        laydate.render({
            elem: '#planDateStart'
        });
        laydate.render({
            elem: '#planDateEnd'
        });
        laydate.render({
            elem: '#planDate'
        });
        form.on('submit(searchSubmit)', function(data){
            //重新加载table
            load(data);
            return false;
        });
    });


})

function load(obj){
    //重新加载table
    tableIns.reload({
        where: obj.field
        , page: {
            curr: pageCurr //从当前页码开始
        }
    });
}

function addProdPlan(){
    openProdPlan(null, "添加生产计划")
}

function openProdPlan(id, title){
    if(id == null || id == ""){
        $("#id").val("");
    }
    layer.open({
        type:1,
        title: title,
        fixed:false,
        resize :false,
        shadeClose: true,
        area: ['550px'],
        content:$('#setProdPlan'),
        end:function(){
            cleanProdPlan()
        }
    });
}

function cleanProdPlan(){
    $("#id").val("")
    $("#cusCode").val("")
    $("#workshopCode").val("")
    $("#workstationCode").val("")
    $("#invCode").val("")
    $("#procedureCode").val("")
    $("#planDate").val("")
    $("#planQty").val("")
    $("#planHour").val("")
}

function formSubmit(obj){
    $.ajax({
        type: "POST",
        data: $("#prodPlanForm").serialize(),
        url: "/business/setProdPlan",
        success: function (data) {
            if(isLogin(data)){
                if (data == "ok") {
                    layer.alert("操作成功",function(){
                        layer.closeAll();
                        cleanProdPlan();
                        //$("#id").val("");
                        //加载页面
                        load(obj);
                    });
                } else {
                    layer.alert(data,function(){
                        layer.closeAll();
                        //加载load方法
                        load(obj);//自定义
                    });
                }
            }
        },
        error: function () {
            layer.alert("操作请求错误，请您稍后再试",function(){
                layer.closeAll();
                //加载load方法
                load(obj);//自定义
            });
        }
    });
}

function getProdPlan(obj, id){
    if(obj.isDel){
        layer.alert("该生产计划已经删除，不可进行编辑；</br>  如需编辑，请先<font style='font-weight:bold;' color='blue'>恢复</font>生产计划状态。");
    }else{
        //回显数据
        $.get("/business/getProdPlan", {"id": id}, function(data){
            if(isLogin(data)){
                if(data.msg == "ok" && data.prodPlanVO != null){
                    $('#id').val(data.prodPlanVO.id == null ? '': data.prodPlanVO.id)
                    $("#cusCode").val(data.prodPlanVO.cusCode == null ? '': data.prodPlanVO.cusCode)
                    $("#workshopCode").val(data.prodPlanVO.workshopCode == null ? '': data.prodPlanVO.workshopCode)
                    $("#workstationCode").val(data.prodPlanVO.workstationCode == null ? '': data.prodPlanVO.workstationCode)
                    $("#invCode").val(data.prodPlanVO.invCode == null ? '': data.prodPlanVO.invCode)
                    $("#procedureCode").val(data.prodPlanVO.procedureCode == null ? '': data.prodPlanVO.procedureCode)
                    $("#planDate").val(data.prodPlanVO.planDate == null ? '': data.prodPlanVO.planDate)
                    $("#planQty").val(data.prodPlanVO.planQty == null ? '': data.prodPlanVO.planQty)
                    $("#planHour").val(data.prodPlanVO.planHour == null ? '': data.prodPlanVO.planHour)

                    openProdPlan(id, "设置工位")
                }else{
                    //弹出错误提示
                    layer.alert(data.msg,function () {
                        layer.closeAll();
                    });
                }
            }
        })
    }
}