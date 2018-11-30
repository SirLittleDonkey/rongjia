/**
 *工位看板管理
 */
var pageCurr;
var workStationCode;
$(function(){
    kaishi()

    $.post("/manufacture/getWorkStationCode", function(data){
        workStationCode = data
        $("#workStationCode").html("工位编号：" + workStationCode)
    })

    layui.use('table', function(){
        var table = layui.table
        var form = layui.form
        tableIns = table.render({
            elem: '#dailyWorkPlanList'
            ,url:'/manufacture/getDailyWorkPlanQuality'
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
                ,{field:'prodPlanId', title:'ProdPlanId',width:80, unresize: true ,style:'font-size:25px;'}
                ,{field:'planDate', title:'日期',style:'font-size:25px;'}
                ,{field:'invCode', title:'产品编号',style:'font-size:25px;'}
                ,{field:'invName', title: '产品名称',style:'font-size:25px;'}
                ,{field:'invStd', title: '规格型号',style:'font-size:25px;'}
                ,{field:'procedureName', title: '工序',style:'font-size:25px;'}
                ,{field:'planQty', title:'计划数量',style:'font-size:25px;'}
                ,{field:'realQty', title:'实际数量',style:'font-size:25px;'}
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
            if(obj.event === 'firstInspect'){
                //首检
                startFirstInspect(data, data.prodPlanId)
            }
            if(obj.event === 'firstInspectcancel'){
                //首检取消
                startFirstInspect(data, data.prodPlanId)
            }
            if(obj.event === 'endInspect'){
                //终检
                startEndInspect(data, data.prodPlanId)
            }
            if(obj.event === 'endInspectCancel'){
                //终检
                startEndInspect(data, data.prodPlanId)
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

function startFirstInspect(obj, prodPlanId){
    //回显数据
    $.get("/manufacture/getFirstInspectData", {"prodPlanId": prodPlanId}, function(data){
        if(isLogin(data)){
            if(data.msg == "ok" && data.firstInspectVO != null){
                $('#prodPlanId').val(data.firstInspectVO.prodPlanId == null ? '': data.firstInspectVO.prodPlanId)
                $('#invCode').html(data.firstInspectVO.invCode == null ? '': data.firstInspectVO.invCode)
                $('#invName').html(data.firstInspectVO.invName == null ? '': data.firstInspectVO.invName)
                $('#invStd').html(data.firstInspectVO.invStd == null ? '': data.firstInspectVO.invStd)
                PDFObject.embed("/business/getPDF?filePath=" + data.firstInspectVO.qualifiedInstructionFilePath, "#qualifiedInstruction1")
                PDFObject.embed("/business/getPDF?filePath=" + data.firstInspectVO.qualifiedDrawingFilePath, "#qualifiedDrawing1")
                layui.use('element', function() {
                    var $ = layui.jquery
                        , element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
                    element.tabChange('quality', 'startWork'); //切换到：用户管理
                })
            }else{
                //弹出错误提示
                layer.alert("<em style='color:black'>" + data.msg + "</em>",function () {
                    layer.closeAll();
                });
            }
        }
    })

}

function firstInspectSubmit(){
    layer.alert("<em style='color:black'>" + "确定首检吗？"+ "</em>",{
        closeBtn: 1,
        btn: ['确定','取消'],
        yes: function() {
            $.post("/manufacture/firstInspect",{"prodPlanId": $("#prodPlanId").val()}, function (data){
                if(isLogin(data)){
                    if(data.msg == "ok" ){
                        layer.alert("<em style='color:black'>" + "首检成功" + "</em>");
                        setTimeout(function(){
                            $(window).attr('location','/manufacture/quality')
                        }, 3000)

                    }else{
                        //弹出错误提示
                        layer.alert("<em style='color:black'>" + data.msg + "</em>",function () {
                            layer.closeAll();
                        });
                    }
                }
            })
        }
    })

}

function firstInspectSubmitCancel(){
    layer.alert("<em style='color:black'>" + "确定取消首检吗？"+ "</em>",{
        closeBtn: 1,
        btn: ['确定','取消'],
        yes: function() {
            $.post("/manufacture/firstInspectCancel",{"prodPlanId": $("#prodPlanId").val()}, function (data){
                if(isLogin(data)){
                    if(data.msg == "ok" ){
                        layer.alert("<em style='color:black'>" + "取消首检成功" + "</em>");
                        setTimeout(function(){
                            $(window).attr('location','/manufacture/quality')
                        }, 3000)

                    }else{
                        //弹出错误提示
                        layer.alert("<em style='color:black'>" + data.msg + "</em>",function () {
                            layer.closeAll();
                        });
                    }
                }
            })
        }
    })
}

function startEndInspect(obj, prodPlanId){
    if(!obj.hasInspected){
        layer.alert("<em style='color:black'>" + "请先首检后再终检" + "</em>")
    }if(!obj.hasComplete){
        layer.alert("<em style='color:black'>" + "还未完工" + "</em>")
    }else{
    //回显数据
    $.get("/manufacture/getEndInspectData", {"prodPlanId": prodPlanId}, function(data){
        if(isLogin(data)){
            if(data.msg == "ok" && data.endInspectVO != null){
                $('#prodPlanId').val(data.endInspectVO.prodPlanId == null ? '': data.endInspectVO.prodPlanId)
                $('#einvCode').html(data.endInspectVO.invCode == null ? '': data.endInspectVO.invCode)
                $('#einvName').html(data.endInspectVO.invName == null ? '': data.endInspectVO.invName)
                $('#einvStd').html(data.endInspectVO.invStd == null ? '': data.endInspectVO.invStd)
                PDFObject.embed("/business/getPDF?filePath=" + data.endInspectVO.qualifiedInstructionFilePath, "#qualifiedInstruction2")
                PDFObject.embed("/business/getPDF?filePath=" + data.endInspectVO.qualifiedDrawingFilePath, "#qualifiedDrawing2")
                layui.use('element', function() {
                    var $ = layui.jquery
                        , element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
                    element.tabChange('quality', 'endWork'); //切换到：终检
                })
            }else{
                //弹出错误提示
                layer.alert("<em style='color:black'>" + data.msg + "</em>",function () {
                    layer.closeAll();
                });
            }
        }
    })
    }

}

function endInspectSubmit(){
    if($("#FqualifiedQty").val() == ''){
        layer.alert("<em style='color:black'>" + "合格数量未填写!" + "</em>")
        return
    }
    if($("#FunqualifiedQty").val() == ''){
        layer.alert("<em style='color:black'>" + "不合格数量未填写!" + "</em>")
        return
    }
    layer.alert("<em style='color:black'>" + "确定终检吗？" + "</em>",{
        closeBtn: 1,
        btn: ['确定','取消'],
        yes: function() {
            $.post("/manufacture/endInspect",{"prodPlanId": $("#prodPlanId").val(), "FunqualifiedQty": $("#FunqualifiedQty").val(), "FqualifiedQty": $("#FqualifiedQty").val()}, function (data){
                if(isLogin(data)){
                    if(data.msg == "ok" ){
                        layer.alert("<em style='color:black'>" + "终检成功" + "</em>")
                        setTimeout(function(){
                            $(window).attr('location','/manufacture/quality')
                        }, 3000);

                    }else{
                        //弹出错误提示
                        layer.alert("<em style='color:black'>" + data.msg + "</em>",function () {
                            layer.closeAll();
                        });
                    }
                }
            })
        }
    })

}


function endInspectSubmitCancel(){
    layer.alert("<em style='color:black'>" + "确定取消终检吗？" + "</em>",{
        closeBtn: 1,
        btn: ['确定','取消'],
        yes: function() {
            $.post("/manufacture/endInspectCancel",{"prodPlanId": $("#prodPlanId").val(), "FunqualifiedQty": $("#FunqualifiedQty").val(), "FqualifiedQty": $("#FqualifiedQty").val()}, function (data){
                if(isLogin(data)){
                    if(data.msg == "ok" ){
                        layer.alert("<em style='color:black'>" + "终检取消成功" + "</em>")
                        setTimeout(function(){
                            $(window).attr('location','/manufacture/quality')
                        }, 3000);

                    }else{
                        //弹出错误提示
                        layer.alert("<em style='color:black'>" + data.msg + "</em>",function () {
                            layer.closeAll();
                        });
                    }
                }
            })
        }
    })
}



