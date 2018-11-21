/**
 *工位看板管理
 */
var pageCurr;
var workStationCode;
$(function(){
    $.post("/manufacture/getWorkStationCode", function(data){
        workStationCode = data
        $("#workStationCode").html("工位编号：" + workStationCode)
    })

    layui.use('table', function(){
        var table = layui.table

        tableIns = table.render({
            elem: '#dailyWorkPlanList'
            ,url:'/manufacture/getDailyWorkPlan?workStationCode=' + workStationCode
            ,method: 'post' //默认：get请求
            ,cellMinWidth: 80,
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
                ,{field:'prodPlanId', title:'ProdPlanId',width:80, unresize: true}
                ,{field:'plandate', title:'日期'}
                ,{field:'invCode', title:'产品编号'}
                ,{field:'invName', title: '产品名称'}
                ,{field:'invStd', title: '规格型号'}
                ,{field:'procedureName', title: '工序'}
                ,{field:'planHour', title:'计划加工小时'}
                ,{field:'planQty', title:'计划数量'}
                ,{field:'qualifiedQty', title:'合格数量'}
                ,{field:'unqualifiedQty', title:'不合格数量'}
                ,{field:'state', title:'状态'}
                ,{fixed:'right', title:'操作',width:140,align:'center', toolbar:'#optBar'}
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
        table.on('tool(dailyWorkPlanTable)',function(obj){
            var data = obj.data
            if(obj.event === 'start'){
                //开工
                startWork(data, data.prodPlanId)
            }
        })
    })

    layui.use('table', function(){
        var table = layui.table

        tableIns = table.render({
            elem: '#weeklyWorkPlanList'
            ,url:'/manufacture/getWeeklyWorkPlan'
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
                ,{field:'prodPlanId', title:'ProdPlanId',width:80, unresize: true}
                ,{field:'date', title:'日期'}
                ,{field:'invCode', title:'产品编号'}
                ,{field:'invName', title: '产品名称'}
                ,{field:'invStd', title: '规格型号'}
                ,{field:'procedureName', title: '工序'}
                ,{field:'planTime', title:'计划加工小时'}
                ,{field:'planQty', title:'计划数量'}
                ,{field:'qualifiedQty', title:'合格数量'}
                ,{field:'unqualifiedQty', title:'不合格数量'}
                ,{field:'state', title:'状态'}
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
    })
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

function startWork(obj, prodPlanId){
    //回显数据
    $.get("/manufacture/startWork", {"prodPlanId": prodPlanId}, function(data){
        if(isLogin(data)){
            if(data.msg == "ok" && data.workVO != null){
                $('#prodPlanId').val(data.workVO.prodPlanId == null ? '': data.workVO.prodPlanId)
                $('#userName').html(data.workVO.userName == null ? '': data.workVO.userName)
                $('#planQty').html(data.workVO.planQty == null ? '': data.workVO.planQty)
                $('#startTime').html(data.workVO.startTime == null ? '': data.workVO.startTime)
                $('#invCode').html(data.workVO.invCode == null ? '': data.workVO.invCode)
                $('#procedureName').html(data.workVO.procedureName == null ? '': data.workVO.procedureName)
                $('#realQty').html(data.workVO.realQty == null ? '': data.workVO.realQty)
                $('#userName').val(data.workVO.pdfPath == null ? '': data.workVO.pdfPath)
                PDFObject.embed("/business/getPDF?filePath=" + data.workVO.pdfPath, "#example1")
                layui.use('element', function() {
                    var $ = layui.jquery
                        , element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
                    element.tabChange('demo', 'startWork'); //切换到：用户管理
                })
            }else{
                //弹出错误提示
                layer.alert(data.msg,function () {
                    layer.closeAll();
                });
            }
        }
    })

}

