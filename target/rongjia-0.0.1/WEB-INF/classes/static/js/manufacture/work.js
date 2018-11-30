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
        var form = layui.form
        tableIns = table.render({
            elem: '#dailyWorkPlanList'
            ,url:'/manufacture/getDailyWorkPlan?workStationCode=' + workStationCode
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
                ,{field:'prodPlanId', title:'ProdPlanId',width:80, unresize: true,style:'font-size:25px;'}
                ,{field:'plandate', title:'日期',style:'font-size:25px;'}
                ,{field:'invCode', title:'产品编号',style:'font-size:25px;'}
                ,{field:'invName', title: '产品名称',style:'font-size:25px;'}
                ,{field:'invStd', title: '规格型号',style:'font-size:25px;'}
                ,{field:'procedureName', title: '工序',style:'font-size:25px;'}
                ,{field:'planHour', title:'计划加工小时',style:'font-size:25px;'}
                ,{field:'planQty', title:'计划数量',style:'font-size:25px;'}
                ,{field:'qualifiedQty', title:'合格数量',style:'font-size:25px;'}
                ,{field:'unqualifiedQty', title:'不合格数量',style:'font-size:25px;'}
                ,{field:'state', title:'状态',style:'font-size:25px;'}
                ,{fixed:'right', title:'操作',width:140,align:'center', toolbar:'#optBar',style:'font-size:25px;'}
            ]]
            ,  done: function(res, curr, count){
                $('tr').css({'background-color': '#191970', 'color': 'white','font-size':'25px'});
                $('th').css({'font-size':'25px'});
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
            ,url:'/manufacture/getWeeklyWorkPlan?workStationCode=' + workStationCode
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
                ,{field:'prodPlanId', title:'ProdPlanId',width:80, unresize: true,style:'font-size:25px;'}
                ,{field:'plandate', title:'日期',style:'font-size:25px;'}
                ,{field:'invCode', title:'产品编号',style:'font-size:25px;'}
                ,{field:'invName', title: '产品名称',style:'font-size:25px;'}
                ,{field:'invStd', title: '规格型号',style:'font-size:25px;'}
                ,{field:'procedureName', title: '工序',style:'font-size:25px;'}
                ,{field:'planHour', title:'计划加工小时',style:'font-size:25px;'}
                ,{field:'planQty', title:'计划数量',style:'font-size:25px;'}
                ,{field:'qualifiedQty', title:'合格数量',style:'font-size:25px;'}
                ,{field:'unqualifiedQty', title:'不合格数量',style:'font-size:25px;'}
                ,{field:'state', title:'状态',width:140,style:'font-size:25px;'}
            ]]
            ,  done: function(res, curr, count){
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                //console.log(res);
                //得到当前页码
                //console.log(curr);
                //得到数据总量
                //console.log(count);
                $('tr:eq(0)').css({'background-color': '#191970', 'color': 'white','font-size':'25px'});
                $('th').css({'font-size':'25px'});
                LayUIDataTable.SetJqueryObj($);
                var currentRowDataList = LayUIDataTable.ParseDataTable(function (index, currentData, rowData) {
                    console.log("当前页数据条数:" + currentRowDataList.length)
                    console.log("当前行索引：" + index);
                    console.log("触发的当前行单元格：" + currentData);
                    console.log("当前行数据：" + JSON.stringify(rowData));

                    var msg = '<div style="text-align: left"> 【当前页数据条数】' + currentRowDataList.length + '<br/>【当前行索引】' + index + '<br/>【触发的当前行单元格】' + currentData + '<br/>【当前行数据】' + JSON.stringify(rowData) + '</div>';
                    layer.msg(msg)
                })
                $.each(currentRowDataList, function (index, obj) {
                    if ('紧急'=== obj.state.value){
                        obj.state.row.css({'background-color': 'yellow', 'color': 'white'});
                    } else if ('超期'=== obj.state.value){
                        obj.state.row.css({'background-color': 'red', 'color': 'white'});
                    } else {
                        obj.state.row.css({'background-color': '#191970', 'color': 'white'});
                    }
                })
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
                $('#state').html(data.workVO.state == null ? '': data.workVO.state)
                $('#qQty').html('合格数量：'+ data.workVO.qualifiedqty == null ? '': data.workVO.qualifiedqty + '  不合格数量：' + data.workVO.unqualifiedqty == null ? '': data.workVO.unqualifiedqty)

                $('#userName').val(data.workVO.pdfPath == null ? '': data.workVO.pdfPath)
                PDFObject.embed("/business/getPDF?filePath=" + data.workVO.pdfPath, "#example1")
                layui.use('element', function() {
                    var $ = layui.jquery
                        , element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
                    element.tabChange('demo', 'startWork'); //切换到：用户管理
                })
            }else{
                //弹出错误提示
                layer.alert("<em style='color:black'>" +data.msg + "</em>",function () {
                    layer.closeAll();
                });
            }
        }
    })

}

function qualitySubmit(){
    layer.alert("<em style='color:black'>" + '确定合格吗？' + "</em>>", {
        closeBtn: 1,
        btn: ['确定', '取消'],
        yes: function () {
            $.post("/manufacture/setquality", {"prodPlanId": $("#prodPlanId").val()}, function (data) {
                if (isLogin(data)) {
                    if (data.msg == "ok") {
                        layer.alert("<em style='color:black'>" +'发送成功！' + "</em>")
                        $('#realQty').html(data.workVO.realQty == null ? '': data.workVO.realQty)
                        $('#state').html(data.workVO.state == null ? '':  data.workVO.state)
                        $('#qQty').html('合格数量：'+ data.workVO.qualifiedqty == null ? '': data.workVO.qualifiedqty + '  不合格数量：' + data.workVO.unqualifiedqty == null ? '': data.workVO.unqualifiedqty)
                    } else {
                        //弹出错误提示
                        layer.alert("<em style='color:black'>" + data.msg + "</em>", function () {
                            layer.closeAll();
                        });
                    }
                }
            })
        }
    })
}

function unqualitySubmit(){
    layer.alert("<em style='color:black'>" +'确定不合格吗？' + "</em>", {
        closeBtn: 1,
        btn: ['确定', '取消'],
        yes: function () {
            $.post("/manufacture/setunquality", {"prodPlanId": $("#prodPlanId").val()}, function (data) {
                if (isLogin(data)) {
                    if (data.msg == "ok") {
                        layer.alert("<em style='color:black'>" +'发送成功！' + "</em>")
                        $('#realQty').html(data.workVO.realQty == null ? '': data.workVO.realQty)
                        $('#state').html(data.workVO.state == null ? '':  data.workVO.state)
                        $('#qQty').html('合格数量：'+ data.workVO.qualifiedqty == null ? '': data.workVO.qualifiedqty + '  不合格数量：' + data.workVO.unqualifiedqty == null ? '': data.workVO.unqualifiedqty)
                    } else {
                        //弹出错误提示
                        layer.alert("<em style='color:black'>" + data.msg + "</em>", function () {
                            layer.closeAll();
                        });
                    }
                }
            })
        }
    })
}

function pauseSubmit(){
    layer.alert("<em style='color:black'>" +'确定暂停吗？' + "</em>", {
        closeBtn: 1,
        btn: ['确定', '取消'],
        yes: function () {
            $.post("/manufacture/pause", {"prodPlanId": $("#prodPlanId").val()}, function (data) {
                if (isLogin(data)) {
                    if (data.msg == "ok") {
                        layer.alert("<em style='color:black'>" +'发送成功！' + "</em>")
                        setTimeout(function(){
                            $(window).attr('location','/manufacture/work')
                        }, 2000);
                    } else {
                        //弹出错误提示
                        layer.alert("<em style='color:black'>" + data.msg + "</em>", function () {
                            layer.closeAll();
                        });
                    }
                }
            })
        }
    })
}

function pauseCancelSubmit(){
    layer.alert("<em style='color:black'>" +'确定取消暂停吗？' + "</em>", {
        closeBtn: 1,
        btn: ['确定', '取消'],
        yes: function () {
            $.post("/manufacture/pauseCancel", {"prodPlanId": $("#prodPlanId").val()}, function (data) {
                if (isLogin(data)) {
                    if (data.msg == "ok") {
                        layer.alert("<em style='color:black'>" +'发送成功！' + "</em>")
                    } else {
                        //弹出错误提示
                        layer.alert("<em style='color:black'>" + data.msg + "</em>", function () {
                            layer.closeAll();
                        });
                    }
                }
            })
        }
    })
}

function completeSubmit(){
    layer.alert("<em style='color:black'>" +'确定取消暂停吗？' + "</em>", {
        closeBtn: 1,
        btn: ['确定', '取消'],
        yes: function () {
            $.post("/manufacture/complete", {"prodPlanId": $("#prodPlanId").val()}, function (data) {
                if (isLogin(data)) {
                    if (data.msg == "ok") {
                        layer.alert("<em style='color:black'>" +'发送成功！' + "</em>")
                        setTimeout(function(){
                            $(window).attr('location','/manufacture/work')
                        }, 2000);
                    } else {
                        //弹出错误提示
                        layer.alert("<em style='color:black'>" + data.msg + "</em>", function () {
                            layer.closeAll();
                        });
                    }
                }
            })
        }
    })
}
